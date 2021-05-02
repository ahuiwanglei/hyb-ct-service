package com.micro.seller.entity.db;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "hyb_balancechange_record")
public class HybBalanceChangeRecord {

    /**
     * biztype 类型 1平台余额 2电子卡余额
     */
    public enum BIZTYPE {
        PlatformBalance(1), VirtualBalance(2);
        private int value;

        private BIZTYPE(int v) {
            this.value = v;
        }

        public int getValue() {
            return value;
        }

    }

    //  changeType 类型 1增加 2减少
    public enum CHANGETYPE {
        Add(0), Reduce(1);
        private int value;

        private CHANGETYPE(int v) {
            this.value = v;
        }

        public int getValue() {
            return value;
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer changeId;


    @Column
    private String pkregister;

    @Column
    private Date orderTime;

    @Column
    private BigDecimal orderMoney;

    @Column
    private String orderSubject;

    @Column
    BigDecimal before_balance;

    @Column
    BigDecimal after_balance;

    @Column
    BigDecimal before_totalinterest;

    @Column
    BigDecimal after_totalinterest;

    @Column
    Integer changeType;

    @Column
    Integer biztype;

    @Column
    Integer source_type;

    @Column
    String orderid;

    public Integer getChangeId() {
        return changeId;
    }

    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
    }

    public String getPkregister() {
        return pkregister;
    }

    public void setPkregister(String pkregister) {
        this.pkregister = pkregister;
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
        this.orderSubject = orderSubject;
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

    public Integer getBiztype() {
        return biztype;
    }

    public void setBiztype(Integer biztype) {
        this.biztype = biztype;
    }

    public Integer getSource_type() {
        return source_type;
    }

    public void setSource_type(Integer source_type) {
        this.source_type = source_type;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}
