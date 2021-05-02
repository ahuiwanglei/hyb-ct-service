package com.mszx.hyb.ordercenter.service;

import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.ordercenter.params.OrderNotifyParams;
import com.mszx.hyb.ordercenter.params.OrderParams;
import com.mszx.hyb.ordercenter.params.SearchOrderListParams;

import java.sql.SQLException;

public interface OrderService {
    Result orderAdd(OrderParams orderParams) throws SQLException;

    Result orderPayNotify(OrderNotifyParams orderNotifyParams);

    Result getOrderInfo(OrderNotifyParams orderNotifyParams);

    Result searchOrderList(SearchOrderListParams searchOrderListParams);

    Result checkConsume(OrderNotifyParams orderNotifyParams);

}
