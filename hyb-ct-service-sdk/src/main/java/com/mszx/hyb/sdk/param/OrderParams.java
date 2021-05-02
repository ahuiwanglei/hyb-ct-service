package com.mszx.hyb.sdk.param;

import com.mszx.hyb.sdk.util.Strings;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderParams {


    /**
     * 核销状态
     */
    public enum CheckConsumeStatus {
        NoCheck(0), Checked(1);
        private int value;

        private CheckConsumeStatus(int status) {
            this.value = status;
        }

        public int getValue() {
            return value;
        }
    }


    private String appid;
    private String pkmuser;
    private String pkmuser_name;
    private String userid;
    private Integer order_type = 0;
    private String order_title;
    private BigDecimal discounted_amount = new BigDecimal(0);
    private BigDecimal amount;
    private BigDecimal real_pay_amount;
    private Integer pay_type;
    private BigDecimal offline_pay_amount = new BigDecimal("0");
    private String barcode = "";
    /***
     * 关联ID
     */
    private Integer outid;

    private String check_code;
    private Integer check_consume_status;
    private String check_consume_user;
    private Integer check_consume_source;
    /***
     * 支付中心 订单号
     */
    private String ordernum;
    /**
     * 支付宝，微信的支付ID
     */
    private String outOrderId;

    List<GoodsParams> goodsList;

    GoodsReceiptInfoParams goodsReceiptInfo;

    private Date update_at;
    private Date create_at;

    public String checkParams() {
        String result = null;
        if (Strings.isEmpty(appid)) {
            result = "appid 不能为空";
        }
        if (Strings.isEmpty(pkmuser)) {
            result = "pkmuser 不能为空";
        }
        if (Strings.isEmpty(pkmuser_name)) {
            result = "pkmuser_name 不能为空";
        }
        if (Strings.isEmpty(userid)) {
            result = "userid 不能为空";
        }
        if (Strings.isEmpty(order_title)) {
            result = "order_title 不能为空";
        }
        if (amount == null) {
            result = "amount 不能为空";
        }
        if (amount == null) {
            result = "real_pay_amount 不能为空";
        }
//        if(goodsList == null || goodsList.size() == 0){
//            result = "商品不能为空";
//        }
        if (goodsList != null) {
            for (int i = 0; i < goodsList.size(); i++) {
                result = goodsList.get(i).checkParams();
                if (Strings.isNotNullOrEmpty(result)) {
                    break;
                }
            }
        }
        return result;

    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPkmuser() {
        return pkmuser;
    }

    public void setPkmuser(String pkmuser) {
        this.pkmuser = pkmuser;
    }

    public String getPkmuser_name() {
        return pkmuser_name;
    }

    public void setPkmuser_name(String pkmuser_name) {
        this.pkmuser_name = pkmuser_name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getOrder_type() {
        return order_type;
    }

    public void setOrder_type(Integer order_type) {
        this.order_type = order_type;
    }

    public String getOrder_title() {
        return order_title;
    }

    public void setOrder_title(String order_title) {
        this.order_title = order_title;
    }

    public BigDecimal getDiscounted_amount() {
        return discounted_amount;
    }

    public void setDiscounted_amount(BigDecimal discounted_amount) {
        this.discounted_amount = discounted_amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getReal_pay_amount() {
        return real_pay_amount;
    }

    public void setReal_pay_amount(BigDecimal real_pay_amount) {
        this.real_pay_amount = real_pay_amount;
    }

    public Integer getPay_type() {
        return pay_type;
    }

    public void setPay_type(Integer pay_type) {
        this.pay_type = pay_type;
    }

    public BigDecimal getOffline_pay_amount() {
        return offline_pay_amount;
    }

    public void setOffline_pay_amount(BigDecimal offline_pay_amount) {
        this.offline_pay_amount = offline_pay_amount;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getOutid() {
        return outid;
    }

    public void setOutid(Integer outid) {
        this.outid = outid;
    }

    public String getCheck_code() {
        return check_code;
    }

    public void setCheck_code(String check_code) {
        this.check_code = check_code;
    }

    public Integer getCheck_consume_status() {
        return check_consume_status;
    }

    public void setCheck_consume_status(Integer check_consume_status) {
        this.check_consume_status = check_consume_status;
    }

    public String getCheck_consume_user() {
        return check_consume_user;
    }

    public void setCheck_consume_user(String check_consume_user) {
        this.check_consume_user = check_consume_user;
    }

    public Integer getCheck_consume_source() {
        return check_consume_source;
    }

    public void setCheck_consume_source(Integer check_consume_source) {
        this.check_consume_source = check_consume_source;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }

    public List<GoodsParams> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsParams> goodsList) {
        this.goodsList = goodsList;
    }

    public GoodsReceiptInfoParams getGoodsReceiptInfo() {
        return goodsReceiptInfo;
    }

    public void setGoodsReceiptInfo(GoodsReceiptInfoParams goodsReceiptInfo) {
        this.goodsReceiptInfo = goodsReceiptInfo;
    }

    public Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }
}
