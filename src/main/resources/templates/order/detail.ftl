<html>
    <#include  "../common/header.ftl">

    <body>
        <div id="wrapper" class="toggled">
            <#include "../common/nav.ftl">
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>订单号</th>
                                    <th>订单金额</th>
                                </tr>
                                </thead>
                                <tbody>
                                <td>${orderDTO.orderId}</td>
                                <td>${orderDTO.orderAmount}</td>
                                </tbody>
                            </table>
                        </div>

                        <div class="col-md-12 column">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>商品编号</th>
                                    <th>商品名称</th>
                                    <th>商品单价</th>
                                    <th>商品数量</th>
                                    <th>商品总额</th>
                                </tr>
                                </thead>
                                <tbody>
                        <#list orderDTO.orderDetailList as orderDetail>
                        <tr>
                            <td>${orderDetail.productId}</td>
                            <td>${orderDetail.productName}</td>
                            <td>${orderDetail.productPrice}</td>
                            <td>${orderDetail.productQuantity}</td>
                            <td>${orderDetail.productPrice * orderDetail.productQuantity}</td>
                        </tr>
                        </#list>
                                </tbody>
                            </table>
                        </div>

                        <div class="col-md-12 column">
                    <#if orderDTO.orderStatus == 0>
                        <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary">完结订单</a>
                        <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
                    </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>