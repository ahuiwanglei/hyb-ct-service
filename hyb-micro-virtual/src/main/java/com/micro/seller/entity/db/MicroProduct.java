package com.micro.seller.entity.db;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "hyb_micro_product")
public class MicroProduct {


    //模1: 有期权, 模2：获得宝石，模4: 分享获得钻石  模8: 返现
    public enum TYPE {
        Share(1), Gem(2), Diamond(4), Money(8);
        private int value;

        private TYPE(int v) {
            this.value = v;
        }

        public int getValue() {
            return value;
        }

    }

    public enum ShareReturnAmountTYpe {
        Withdraw(0), Freeze(1);
        private int value;

        private ShareReturnAmountTYpe(int v) {
            this.value = v;
        }

        public int getValue() {
            return value;
        }

    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer product_id;

    @Column
    private String product_name;

    @Column
    private BigDecimal original_price;

    @Column
    private BigDecimal currenty_price;

    @Column
    private Integer type;

    @Column
    private BigDecimal day_gem_count;

    @Column
    private BigDecimal day_diamond_count;

    @Column
    private BigDecimal day_sign_return_amount;

    @Column
    private Integer return_sign_amount_count;

    @Column
    private BigDecimal share_buy_return_amount;

    @Column
    private Integer share_buy_return_amount_type;

    @Column
    private Integer city_count;

    @Column
    private Integer status;

    @Column
    private Date created_at;

    @Column
    private Date updated_at;

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public BigDecimal getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(BigDecimal original_price) {
        this.original_price = original_price;
    }

    public BigDecimal getCurrenty_price() {
        return currenty_price;
    }

    public void setCurrenty_price(BigDecimal currenty_price) {
        this.currenty_price = currenty_price;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getDay_gem_count() {
        return day_gem_count;
    }

    public void setDay_gem_count(BigDecimal day_gem_count) {
        this.day_gem_count = day_gem_count;
    }

    public BigDecimal getDay_diamond_count() {
        return day_diamond_count;
    }

    public void setDay_diamond_count(BigDecimal day_diamond_count) {
        this.day_diamond_count = day_diamond_count;
    }

    public BigDecimal getDay_sign_return_amount() {
        return day_sign_return_amount;
    }

    public void setDay_sign_return_amount(BigDecimal day_sign_return_amount) {
        this.day_sign_return_amount = day_sign_return_amount;
    }

    public Integer getReturn_sign_amount_count() {
        return return_sign_amount_count;
    }

    public void setReturn_sign_amount_count(Integer return_sign_amount_count) {
        this.return_sign_amount_count = return_sign_amount_count;
    }

    public BigDecimal getShare_buy_return_amount() {
        return share_buy_return_amount;
    }

    public void setShare_buy_return_amount(BigDecimal share_buy_return_amount) {
        this.share_buy_return_amount = share_buy_return_amount;
    }

    public Integer getShare_buy_return_amount_type() {
        return share_buy_return_amount_type;
    }

    public void setShare_buy_return_amount_type(Integer share_buy_return_amount_type) {
        this.share_buy_return_amount_type = share_buy_return_amount_type;
    }

    public Integer getCity_count() {
        return city_count;
    }

    public void setCity_count(Integer city_count) {
        this.city_count = city_count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
