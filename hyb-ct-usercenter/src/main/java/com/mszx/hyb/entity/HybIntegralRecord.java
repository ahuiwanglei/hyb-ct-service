package com.mszx.hyb.entity;

import java.math.BigDecimal;
import java.util.Date;

public class HybIntegralRecord {

    public enum IncreaseType{
        Add(0),Reduce(1);
        private int value;
        private IncreaseType(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Integer pkintegralrecord;

    private Integer fansid;

    private Date ordertime;

    private BigDecimal ordermoney;

    private String ordersubject;

    private String orderbody;

    private BigDecimal beforeBalance;

    private BigDecimal afterBalance;

    private Integer isIncrease;

    private Integer dealtype;

    public Integer getPkintegralrecord() {
        return pkintegralrecord;
    }

    public void setPkintegralrecord(Integer pkintegralrecord) {
        this.pkintegralrecord = pkintegralrecord;
    }

    public Integer getFansid() {
        return fansid;
    }

    public void setFansid(Integer fansid) {
        this.fansid = fansid;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public BigDecimal getOrdermoney() {
        return ordermoney;
    }

    public void setOrdermoney(BigDecimal ordermoney) {
        this.ordermoney = ordermoney;
    }

    public String getOrdersubject() {
        return ordersubject;
    }

    public void setOrdersubject(String ordersubject) {
        this.ordersubject = ordersubject == null ? null : ordersubject.trim();
    }

    public String getOrderbody() {
        return orderbody;
    }

    public void setOrderbody(String orderbody) {
        this.orderbody = orderbody == null ? null : orderbody.trim();
    }

    public BigDecimal getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(BigDecimal beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public BigDecimal getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(BigDecimal afterBalance) {
        this.afterBalance = afterBalance;
    }

    public Integer getIsIncrease() {
        return isIncrease;
    }

    public void setIsIncrease(Integer isIncrease) {
        this.isIncrease = isIncrease;
    }

    public Integer getDealtype() {
        return dealtype;
    }

    public void setDealtype(Integer dealtype) {
        this.dealtype = dealtype;
    }
}