package com.mszx.hyb.ordercenter.controller;

import com.mszx.hyb.common.base.BaseController;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.ordercenter.params.OrderNotifyParams;
import com.mszx.hyb.ordercenter.params.OrderParams;
import com.mszx.hyb.ordercenter.params.SearchOrderListParams;
import com.mszx.hyb.ordercenter.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/ordercenter/")
public class OrderController extends BaseController {

    protected static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/order_add",   produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result orderAdd(@ModelAttribute OrderParams orderParams) throws SQLException {
        return orderService.orderAdd(orderParams);
    }

    @PostMapping(value = "/order/pay_notify",   produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result notifyOrder(@ModelAttribute OrderNotifyParams orderNotifyParams){
        return orderService.orderPayNotify(orderNotifyParams);
    }

    @PostMapping(value = "/order/info",   produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result orderInfo(@ModelAttribute OrderNotifyParams orderNotifyParams){
        return orderService.getOrderInfo(orderNotifyParams);
    }

    @PostMapping(value = "/order/search",   produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result searchOrderList(@ModelAttribute SearchOrderListParams searchOrderListParams){
        return orderService.searchOrderList(searchOrderListParams);
    }

    @PostMapping(value = "/order/check_consume",   produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result check_consume(@ModelAttribute OrderNotifyParams orderNotifyParams){
        return orderService.checkConsume(orderNotifyParams);
    }


}
