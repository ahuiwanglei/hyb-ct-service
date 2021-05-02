package com.mszx.hyb.ordercenter.service.impl;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszx.hyb.common.model.CommonFinal;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.common.util.ResultFactory;
import com.mszx.hyb.common.util.Strings;
import com.mszx.hyb.ordercenter.dao.HybOrderMapper;
import com.mszx.hyb.ordercenter.entity.HybGoods;
import com.mszx.hyb.ordercenter.entity.HybOrder;
import com.mszx.hyb.ordercenter.params.GoodsParams;
import com.mszx.hyb.ordercenter.params.OrderNotifyParams;
import com.mszx.hyb.ordercenter.params.OrderParams;
import com.mszx.hyb.ordercenter.params.SearchOrderListParams;
import com.mszx.hyb.ordercenter.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private HybOrderMapper hybOrderMapper;

    @Override
    public Result orderPayNotify(OrderNotifyParams orderNotifyParams) {
        if (Strings.isEmpty(orderNotifyParams.getOrderNum())) {
            return ResultFactory.getErrorResult("订单编码不能为空");
        }
        if (orderNotifyParams.getPay_type() == null) {
            return ResultFactory.getErrorResult("支付方式不能为空");
        }
        HybOrder hybOrder = hybOrderMapper.findOrderByOrdernum(orderNotifyParams.getOrderNum());
        if (hybOrder == null) {
            return ResultFactory.getErrorResult("订单不存在");
        }
        hybOrder.setReal_pay_amount(orderNotifyParams.getReal_pay_amount());
        hybOrder.setPay_type(orderNotifyParams.getPay_type());
        hybOrder.setPay_at(new Date());
        hybOrder.setUpdate_at(new Date());
        hybOrder.setOutOrderId(orderNotifyParams.getOutOrderId());
        if(orderNotifyParams.getOutid() != null){
            hybOrder.setOutid(orderNotifyParams.getOutid());
        }
//        if (hybOrder.getOrder_type() == HybOrder.ORDERTYPE.CardTicket.getValue()) {
        hybOrder.setOrder_status(HybOrder.STATUS.Finish.getValue());
//        }
        int flag = hybOrderMapper.updatePayNotify(hybOrder);
        if (flag > 0) {
            return ResultFactory.getSuccessResult("更新成功");
        } else {
            return ResultFactory.getErrorResult("更新失败");
        }
    }


    @Override
    public Result getOrderInfo(OrderNotifyParams orderNotifyParams) {
        if (Strings.isEmpty(orderNotifyParams.getOrderNum())) {
            return ResultFactory.getErrorResult("订单编码不能为空");
        }
        HybOrder hybOrder = hybOrderMapper.findOrderByOrdernum(orderNotifyParams.getOrderNum());
        if (hybOrder == null)
            return ResultFactory.getErrorResult("订单不存在");
        List<HybGoods> hybGoods = hybOrderMapper.findOrderGoods(hybOrder.getOrderid());
        hybOrder.setGoodsList(hybGoods);
        return ResultFactory.getSuccessResult(hybOrder);
    }

    @Override
    @Transactional(readOnly = false)
    public Result orderAdd(OrderParams orderParams) throws SQLException {
        Result ob = orderParams.checkParams();
        if (ob.getResultStatus() == CommonFinal.RESULT_CODE_FAILURE) {
            return ob;
        }
        HybOrder order = new HybOrder();
        copyOrderProperties(orderParams, order);
        order.setOrdernum(Strings.createOrderNum(order.getOrder_type()));
        order.setCheck_code(Strings.createCheckCode());
        int flag = hybOrderMapper.insertOrder(order);
        if (flag > 0) {
            List<HybGoods> goodsList = new ArrayList<>();
            if (orderParams.getGoodsList() != null) {
                orderParams.getGoodsList().forEach(goodsParams -> goodsList.add(convertHybGoods(goodsParams, order.getOrderid())));
                hybOrderMapper.insertGoods(goodsList);
            }
            return ResultFactory.getSuccessResult("orderNum", order.getOrdernum());
        } else {
            throw new SQLException("订单创建失败");
        }
    }

    @Override
    public Result searchOrderList(SearchOrderListParams searchOrderListParams) {
        PageInfo<HybOrder> pageInfo = PageHelper.startPage(searchOrderListParams.getPageNum(), searchOrderListParams.getPageSize()).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                hybOrderMapper.findOrderList(searchOrderListParams);
            }
        });
        return ResultFactory.getSuccessResult(pageInfo);
    }

    private HybGoods convertHybGoods(GoodsParams goodsParams, Integer orderid) {
        HybGoods goods = new HybGoods();
        goods.setCreate_at(new Date());
        goods.setGoodsid(goodsParams.getGoodsid());
        goods.setGoods_name(goodsParams.getGoods_name());
        goods.setGoods_num(goodsParams.getGoods_num());
        goods.setGoods_pic(goodsParams.getGoods_pic());
        goods.setGoods_price(goodsParams.getGoods_price());
        goods.setUpdate_at(new Date());
        goods.setOrderid(orderid);
        return goods;
    }

    private void copyOrderProperties(OrderParams orderParams, HybOrder order) {
        order.setAppid(orderParams.getAppid());
        order.setPkmuser(orderParams.getPkmuser());
        order.setPkmuser_name(orderParams.getPkmuser_name());
        order.setUserid(orderParams.getUserid());
        order.setOrder_type(orderParams.getOrder_type());
        order.setOrder_title(orderParams.getOrder_title());
        order.setOrder_status(HybOrder.STATUS.PreOrder.getValue());
        order.setAmount(orderParams.getAmount());
        order.setPay_type(orderParams.getPay_type());
        order.setOffline_pay_amount(orderParams.getOffline_pay_amount());
        order.setDiscounted_amount(orderParams.getDiscounted_amount());
        order.setAmount(orderParams.getAmount());
        order.setReal_pay_amount(orderParams.getReal_pay_amount());
        order.setUpdate_at(new Date());
        order.setCreate_at(new Date());
        if(orderParams.getOutid() != null && orderParams.getOutid() >0){
            order.setOutid(orderParams.getOutid());
        }
        order.setBarcode(orderParams.getBarcode());
    }


    @Override
    public Result checkConsume(OrderNotifyParams orderNotifyParams) {
        if (orderNotifyParams.getCheck_consume_source() == null) {
            return ResultFactory.getErrorResult("核销来源不能为空");
        }
        if (Strings.isEmpty(orderNotifyParams.getPkmuser())) {
            return ResultFactory.getErrorResult("商家不能为空");
        }
        if (Strings.isEmpty(orderNotifyParams.getCheck_consume_user())) {
            return ResultFactory.getErrorResult("核销人不能为空");
        }
        if (Strings.isEmpty(orderNotifyParams.getCheck_code())) {
            return ResultFactory.getErrorResult("核销码不能为空");
        }
        HybOrder hybOrder = new HybOrder();
        hybOrder.setPkmuser(orderNotifyParams.getPkmuser());
//        hybOrder.setOrdernum(orderNotifyParams.getOrderNum());
        hybOrder.setCheck_code(orderNotifyParams.getCheck_code());
        HybOrder hybOrder1 = hybOrderMapper.findOrderByOrderInfo(hybOrder);
        if (hybOrder1 == null) {
            return ResultFactory.getErrorResult("核销码不正确");
        } else {
            if(hybOrder1.getCheck_consume_status() == HybOrder.CheckConsumeStatus.Checked.getValue() ){
                return ResultFactory.getErrorResult("已经核销过了，不用重复核销");
            }
            hybOrder1.setCheck_consume_status(HybOrder.CheckConsumeStatus.Checked.getValue());
            hybOrder1.setCheck_consume_time(new Date());
            hybOrder1.setCheck_consume_source(orderNotifyParams.getCheck_consume_source());
            hybOrder1.setCheck_consume_user(orderNotifyParams.getCheck_consume_user());
            int flag = hybOrderMapper.updateCheckConsume(hybOrder1);
            if (flag > 0) {
                return ResultFactory.getSuccessResult(hybOrder1);
            } else {
                return ResultFactory.getErrorResult("核销失败，请重试");
            }

        }

    }
}
