package com.n.sell.service.impl;

import com.n.sell.converter.OrderMaster2OrderDTOConverter;
import com.n.sell.dto.CartDTO;
import com.n.sell.dto.OrderDTO;
import com.n.sell.entity.OrderDetail;
import com.n.sell.entity.OrderMaster;
import com.n.sell.entity.ProductInfo;
import com.n.sell.enums.OrderStatusEnum;
import com.n.sell.enums.PayStatusEnum;
import com.n.sell.enums.ResultEnum;
import com.n.sell.exception.SellException;
import com.n.sell.repository.OrderDetailRepository;
import com.n.sell.repository.OrderMasterRepository;
import com.n.sell.repository.ProductInfoRepository;
import com.n.sell.service.OrderService;
import com.n.sell.service.PayService;
import com.n.sell.service.ProductInfoService;
import com.n.sell.service.PushMessageService;
import com.n.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessageService pushMessageService;
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        String orderId = KeyUtil.genUniqueKey();

        /** 验证商品 */
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoRepository.findOne(orderDetail.getProductId());
            if(null == productInfo){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetailRepository.save(orderDetail);
        }

        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.INIT.getCode());
        orderMaster.setPayStatus(PayStatusEnum.UNPAID.getCode());
        orderMasterRepository.save(orderMaster);

        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(
                e-> new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());

        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(orderDetailList.isEmpty()){
            throw new SellException(ResultEnum.SYSTEM_ERROR);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerId, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.INIT.getCode())){
            log.error("[取消订单]失败，订单{}状态错误[{}]", orderDTO.getOrderId(),
                    orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        /* 如果已经支付成功了，先退款 */
        if(orderDTO.getPayStatus().equals(PayStatusEnum.PAYED.getCode())){
            payService.refund(orderDTO);
        }

        orderDTO.setOrderStatus(OrderStatusEnum.CLOSE.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        if(null == orderMasterRepository.save(orderMaster)){
            log.error("[取消订单]失败，订单{}更新失败", orderMaster.getOrderId());
            throw new SellException(ResultEnum.SYSTEM_ERROR);
        }

        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.INIT.getCode())){
            log.error("[完结订单]失败，订单{}状态错误[{}]", orderDTO.getOrderId(),
                    orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        if(null == orderMasterRepository.save(orderMaster)){
            log.error("[更新订单]失败，订单{}更新失败", orderMaster.getOrderId());
            throw new SellException(ResultEnum.SYSTEM_ERROR);
        }

        pushMessageService.orderStatus(orderDTO);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.INIT.getCode())){
            log.error("[支付订单]失败，订单{}状态错误[{}]", orderDTO.getOrderId(),
                    orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        if(!orderDTO.getPayStatus().equals(PayStatusEnum.UNPAID.getCode())){
            if(orderDTO.getPayStatus().equals(PayStatusEnum.PAYED.getCode()))
                return orderDTO;

            log.error("[支付订单]失败，订单{}支付状态错误[{}]", orderDTO.getOrderId(),
                    orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderDTO.setPayStatus(PayStatusEnum.PAYED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        if(null == orderMasterRepository.save(orderMaster)){
            log.error("[更新订单]失败，订单{}更新失败", orderMaster.getOrderId());
            throw new SellException(ResultEnum.SYSTEM_ERROR);
        }

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl(orderDTOList, pageable, orderMasterPage.getTotalElements());

    }
}
