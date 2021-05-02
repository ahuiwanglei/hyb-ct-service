package com.mszx.hyb.ordercenter.dao;

import com.github.pagehelper.PageInfo;
import com.mszx.hyb.ordercenter.entity.HybGoods;
import com.mszx.hyb.ordercenter.entity.HybOrder;
import com.mszx.hyb.ordercenter.params.SearchOrderListParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HybOrderMapper {

    int insertOrder(HybOrder order) ;

    void insertGoods(List<HybGoods> goodsList);

    HybOrder findOrderByOrdernum(@Param("orderNum") String orderNum);

    int updatePayNotify(HybOrder hybOrder);

    List<HybGoods> findOrderGoods(@Param("orderid") Integer orderid);

    List<HybOrder> findOrderList(SearchOrderListParams searchOrderListParams);

    HybOrder findOrderByOrderInfo(HybOrder hybOrder);

    int updateCheckConsume(HybOrder hybOrder);
}
