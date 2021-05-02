package com.mszx.hyb.sdk.user;

import com.mszx.hyb.sdk.config.CSConfig;
import com.mszx.hyb.sdk.exception.ParameterException;
import com.mszx.hyb.sdk.param.CheckConsumeParams;
import com.mszx.hyb.sdk.param.OrderParams;
import com.mszx.hyb.sdk.param.SearchOrderListParams;
import com.mszx.hyb.sdk.util.EntityToMap;
import com.mszx.hyb.sdk.util.Strings;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OrderClient extends BaseClient {

    private static OrderClient orderClient;

    public static OrderClient getInstance() {
        if (orderClient == null) {
            orderClient = new OrderClient();
        }
        return orderClient;
    }

    @Override
    public String host() {
        return CSConfig.order_api_host;
    }

    /**
     * @param orderParams 订单信息
     *                    必填 appid，pkmuser，pkmuser_name，userid，order_title，goodsList
     * @return
     */
    public String orderAdd(OrderParams orderParams) throws ParameterException {
        String checkResult = orderParams.checkParams();
        if (checkResult != null) {
            throw new ParameterException(checkResult);
        }
        Map<String, String> params = new HashMap<>();
        params.put("appid", orderParams.getAppid());
        params.put("pkmuser", orderParams.getPkmuser());
        params.put("pkmuser_name", orderParams.getPkmuser_name());
        params.put("userid", orderParams.getUserid());
        params.put("order_title", orderParams.getOrder_title());
        params.put("pay_type", orderParams.getPay_type() + "");
        params.put("order_type", orderParams.getOrder_type() + "");
        params.put("real_pay_amount", orderParams.getReal_pay_amount() + "");
        params.put("amount", orderParams.getAmount() + "");
        params.put("discounted_amount", orderParams.getDiscounted_amount() + "");
        params.put("offline_pay_amount", orderParams.getOffline_pay_amount() + "");
        params.put("barcode", orderParams.getBarcode());
        params.put("outid", orderParams.getOutid() + "");
        if (orderParams.getGoodsList() != null) {
            for (int i = 0; i < orderParams.getGoodsList().size(); i++) {
                params.put("goodsList[" + i + "].goodsid", orderParams.getGoodsList().get(i).getGoodsid() + "");
                if (Strings.isNotNullOrEmpty(orderParams.getGoodsList().get(i).getGoods_pic())) {
                    params.put("goodsList[" + i + "].goods_pic", orderParams.getGoodsList().get(i).getGoods_pic());
                }
                params.put("goodsList[" + i + "].goods_name", orderParams.getGoodsList().get(i).getGoods_name());
                params.put("goodsList[" + i + "].goods_price", orderParams.getGoodsList().get(i).getGoods_price() + "");
                params.put("goodsList[" + i + "].goods_num", orderParams.getGoodsList().get(i).getGoods_num() + "");
            }
        }
        return request.post(host(), "/api/ordercenter/order_add", params);
    }


    /**
     * @param appid
     * @param orderNum        订单号
     * @param pay_type        支付类型
     * @param real_pay_amount 实际支付金额
     * @return
     */
    public String payNotify(String appid, String orderNum, int pay_type, BigDecimal real_pay_amount) {
       return payNotify(appid, orderNum, pay_type, real_pay_amount, "");
    }


    /**
     * @param appid
     * @param orderNum        订单号
     * @param pay_type        支付类型
     * @param real_pay_amount 实际支付金额
     * @return
     */
    public String payNotify(String appid, String orderNum, int pay_type, BigDecimal real_pay_amount,String  outOrderId) {
        return payNotify(appid, orderNum, pay_type, real_pay_amount, outOrderId, null);
    }

    /**
     * @param appid
     * @param orderNum        订单号
     * @param pay_type        支付类型
     * @param real_pay_amount 实际支付金额
     * @return
     */
    public String payNotify(String appid, String orderNum, int pay_type, BigDecimal real_pay_amount,String  outOrderId, Integer outid) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("orderNum", orderNum);
        params.put("pay_type", pay_type + "");
        params.put("real_pay_amount", real_pay_amount + "");
        params.put("outOrderId", outOrderId);
        if(outid != null){
            params.put("outid", outid+"");
        }
        return request.post(host(), "/api/ordercenter/order/pay_notify", params);
    }

    public String orderInfo(String appid, String orderNum) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("orderNum", orderNum);
        return request.post(host(), "/api/ordercenter/order/info", params);
    }


    /**
     * @param searchOrderListParams
     * @return
     */
    public String searchOrderInfo(SearchOrderListParams searchOrderListParams) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", searchOrderListParams.getAppid());
        params.put("pkmuser_name", searchOrderListParams.getPkmuser_name());
        params.put("order_type", searchOrderListParams.getOrder_type() + "");
        params.put("pageNum", searchOrderListParams.getPageNum() + "");
        params.put("pageSize", searchOrderListParams.getPageSize() + "");
        return request.post(host(), "/api/ordercenter/order/search", params);
    }

    /**
     * 核销
     *
     * @param checkConsumeParams
     * check_code  核销码
     * check_consume_user  核销人员
     * check_consume_source  核销来源
     * pkmuser  商家
     * @return
     */
    public String checkConsume(CheckConsumeParams checkConsumeParams) {
        Map<String, String> params = EntityToMap.ConvertObjToMap(checkConsumeParams);
        return request.post(host(), "/api/ordercenter/order/check_consume", params);
    }


}
