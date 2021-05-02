package com.micro.seller.entity.db;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "hyb_micro_balance")
public class MicroBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer micro_balance_id;

    @Column
    private String pkregister;

    @Column
    private BigDecimal gem_balance;

    @Column
    private BigDecimal diamond_balance;

    @Column
    private BigDecimal withdraw_balance;

    @Column
    private BigDecimal recommend_balance;

    @Column
    private Integer status;

    @Column
    private BigDecimal eveyday_increment_gem_base;

    @Column
    private BigDecimal eveyday_increment_diamond_base;

    @Column
    private Date updated_at;

    @Column
    private Date created_at;

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Integer getMicro_balance_id() {
        return micro_balance_id;
    }

    public void setMicro_balance_id(Integer micro_balance_id) {
        this.micro_balance_id = micro_balance_id;
    }

    public String getPkregister() {
        return pkregister;
    }

    public void setPkregister(String pkregister) {
        this.pkregister = pkregister;
    }

    public BigDecimal getGem_balance() {
        return gem_balance;
    }

    public void setGem_balance(BigDecimal gem_balance) {
        this.gem_balance = gem_balance;
    }

    public BigDecimal getDiamond_balance() {
        return diamond_balance;
    }

    public void setDiamond_balance(BigDecimal diamond_balance) {
        this.diamond_balance = diamond_balance;
    }

    public BigDecimal getWithdraw_balance() {
        return withdraw_balance;
    }

    public void setWithdraw_balance(BigDecimal withdraw_balance) {
        this.withdraw_balance = withdraw_balance;
    }

    public BigDecimal getRecommend_balance() {
        return recommend_balance;
    }

    public void setRecommend_balance(BigDecimal recommend_balance) {
        this.recommend_balance = recommend_balance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getEveyday_increment_gem_base() {
        return eveyday_increment_gem_base;
    }

    public void setEveyday_increment_gem_base(BigDecimal eveyday_increment_gem_base) {
        this.eveyday_increment_gem_base = eveyday_increment_gem_base;
    }

    public BigDecimal getEveyday_increment_diamond_base() {
        return eveyday_increment_diamond_base;
    }

    public void setEveyday_increment_diamond_base(BigDecimal eveyday_increment_diamond_base) {
        this.eveyday_increment_diamond_base = eveyday_increment_diamond_base;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
