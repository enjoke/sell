<html>
    <#include  "../common/header.ftl">
    <body>
        <div id="wrapper" class="toggled">
            <#include "../common/nav.ftl">

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <table class="table table-condensed table-bordered">
                                <thead>
                                <tr>
                                    <th>订单号</th>
                                    <th>用户名</th>
                                    <th>手机号</th>
                                    <th>地址</th>
                                    <th>订单金额</th>
                                    <th>订单状态</th>
                                    <th>支付状态</th>
                                    <th>创建时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                    <#list orderDTOPage.content as orderDTO>
                    <tr>
                        <td>${orderDTO.orderId}</td>
                        <td>${orderDTO.buyerName}</td>
                        <td>${orderDTO.buyerPhone}</td>
                        <td>${orderDTO.buyerAddress}</td>
                        <td>${orderDTO.orderAmount}</td>
                        <td>${orderDTO.getOrderStatusEnum().getMsg()}</td>
                        <td>${orderDTO.getPayStatusEnum().getMsg()}</td>
                        <td>${orderDTO.createTime}</td>
                        <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                        <td>
                            <#if orderDTO.getOrderStatusEnum().getCode() == 0>
                                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                            <#else>

                            </#if>
                        </td>
                    </tr>
                    </#list>
                                </tbody>
                            </table>
                        </div>
                        <div class="col-md-12 column">
                            <ul class="pagination pull-right" >
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>

                    <#list 1..orderDTOPage.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>

                    </#list>
                    <#if currentPage gte orderDTOPage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                    </#if>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
<script>
    var websocket = null;
    if('WebSocket' in window){
        websocket = new WebSocket('ws://');

        websocket.onopen = function (event) {

        }

        websocket.onclose = function (event) {

        }

        websocket.onmessage = function (event) {

        }

        websocket.onerror = function (event) {
        }

        websocket.onbeforeunload = function (event) {

        }
    }
</script>
</html>