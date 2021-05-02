package com.mszx.hyb.entity;

import java.math.BigDecimal;

public class BalanceParams {
    //     1增加 2减少
    public enum ChangeType {
        Add(1), Reduce(2);

        private int value;

        private ChangeType(int v) {
            this.value = v;
        }

        public int getValue() {
            return value;
        }
    }

    //1平台余额 2电子卡余额
    public enum BizType {
        SystemBalance(1), Virtualbalance(2);

        private int value;
        private BizType(int v){
            this.value = v;
        }

        public int getValue() {
            return value;
        }
    }

    private String pkregister;
    private String pkmuser;
    private BigDecimal orderMoney;
    private String beforeTotalInterest;
    private String orderSubject;
    private Integer changeType;
    private Integer biztype;
    private Integer source_type;//来源
    private String  orderid; //订单号

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Integer getSource_type() {
        return source_type;
    }

    public void setSource_type(Integer source_type) {
        this.source_type = source_type;
    }

    public String getPkregister() {
        return pkregister;
    }

    public void setPkregister(String pkregister) {
        this.pkregister = pkregister;
    }

    public String getPkmuser() {
        return pkmuser;
    }

    public void setPkmuser(String pkmuser) {
        this.pkmuser = pkmuser;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }



    public String getBeforeTotalInterest() {
        return beforeTotalInterest;
    }

    public void setBeforeTotalInterest(String beforeTotalInterest) {
        this.beforeTotalInterest = beforeTotalInterest;
    }

    public String getOrderSubject() {
        return orderSubject;
    }

    public void setOrderSubject(String orderSubject) {
        this.orderSubject = orderSubject;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public Integer getBiztype() {
        return biztype;
    }

    public void setBiztype(Integer biztype) {
        this.biztype = biztype;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }
}
