package com.mszx.hyb.entity;

import java.math.BigDecimal;
import java.util.Date;

public class HybBalanceChangeRecord {
    private Long changeId;

    private String pkregister;

    private Date orderTime;

    private BigDecimal orderMoney;

    private String orderSubject;

    private BigDecimal before_balance;

    private BigDecimal after_balance;

    private BigDecimal before_totalinterest;

    private BigDecimal after_totalinterest;

    private Integer changeType;

    private Integer biztype;

    private Integer source_type;

    private String orderid;

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

    public Integer getBiztype() {
        return biztype;
    }

    public void setBiztype(Integer biztype) {
        this.biztype = biztype;
    }

    public Long getChangeId() {
        return changeId;
    }

    public void setChangeId(Long changeId) {
        this.changeId = changeId;
    }

    public String getPkregister() {
        return pkregister;
    }

    public void setPkregister(String pkregister) {
        this.pkregister = pkregister == null ? null : pkregister.trim();
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderSubject() {
        return orderSubject;
    }

    public void setOrderSubject(String orderSubject) {
        this.orderSubject = orderSubject == null ? null : orderSubject.trim();
    }

    public BigDecimal getBefore_balance() {
        return before_balance;
    }

    public void setBefore_balance(BigDecimal before_balance) {
        this.before_balance = before_balance;
    }

    public BigDecimal getAfter_balance() {
        return after_balance;
    }

    public void setAfter_balance(BigDecimal after_balance) {
        this.after_balance = after_balance;
    }

    public BigDecimal getBefore_totalinterest() {
        return before_totalinterest;
    }

    public void setBefore_totalinterest(BigDecimal before_totalinterest) {
        this.before_totalinterest = before_totalinterest;
    }

    public BigDecimal getAfter_totalinterest() {
        return after_totalinterest;
    }

    public void setAfter_totalinterest(BigDecimal after_totalinterest) {
        this.after_totalinterest = after_totalinterest;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    @Override
    public String toString() {
        return "HybBalanceChangeRecord [changeId=" + changeId + ", pkregister="
                + pkregister + ", orderTime=" + orderTime + ", orderMoney="
                + orderMoney + ", orderSubject=" + orderSubject
                + ", before_balance=" + before_balance + ", after_balance="
                + after_balance + ", before_totalinterest="
                + before_totalinterest + ", after_totalinterest="
                + after_totalinterest + ", changeType=" + changeType + "]";
    }
}
