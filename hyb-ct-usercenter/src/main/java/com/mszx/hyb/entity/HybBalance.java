package com.mszx.hyb.entity;

import java.math.BigDecimal;
import java.util.Date;

public class HybBalance {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String pkbalance;
    /**
     * 用户主键
     */
    private String pkregister;
    /**
     * 商户主键/系统主键
     */
    private String pkmuser;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * (new) 红包 默认余额 0.00
     */
    private BigDecimal wealmoney;

    /**
     * 冻结金额
     */
    private BigDecimal freezebalance;


    private BigDecimal investbalance;

    private BigDecimal balance_gift;


    public BigDecimal getBalance_gift() {
        return balance_gift;
    }
    public void setBalance_gift(BigDecimal balance_gift) {
        this.balance_gift = balance_gift;
    }
    public BigDecimal getInvestbalance() {
        return investbalance;
    }
    public void setInvestbalance(BigDecimal investbalance) {
        this.investbalance = investbalance;
    }
    public BigDecimal getFreezebalance() {
        return freezebalance;
    }
    public void setFreezebalance(BigDecimal freezebalance) {
        this.freezebalance = freezebalance;
    }

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户余额总利息
     */
    private BigDecimal totalinterest;

    /**
     * 用户余额昨日利息
     */
    private BigDecimal interest;

    private Integer point; //用户积分

    private BigDecimal virtualbalance;//虚拟金额

    private Integer growthvalue; //成长值

    private String grade; //等级

    private BigDecimal discount; //折扣

    private Integer virtual_count; // 虚拟余额使用次数

    private BigDecimal halfstorebalance; //五折店余额

    private Integer version_code; //更新版本号

    private BigDecimal card_gift; //礼品金

    public BigDecimal getCard_gift() {
        return card_gift;
    }

    public void setCard_gift(BigDecimal card_gift) {
        this.card_gift = card_gift;
    }

    public Integer getVersion_code() {
        return version_code;
    }
    public void setVersion_code(Integer version_code) {
        this.version_code = version_code;
    }
    public BigDecimal getHalfstorebalance() {
        return halfstorebalance;
    }
    public void setHalfstorebalance(BigDecimal halfstorebalance) {
        this.halfstorebalance = halfstorebalance;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public Integer getGrowthvalue() {
        return growthvalue;
    }
    public void setGrowthvalue(Integer growthvalue) {
        this.growthvalue = growthvalue;
    }
    public Integer getPoint() {
        return point;
    }
    public void setPoint(Integer point) {
        this.point = point;
    }
    public BigDecimal getVirtualbalance() {
        return virtualbalance;
    }
    public void setVirtualbalance(BigDecimal virtualbalance) {
        this.virtualbalance = virtualbalance;
    }
    public String getPkbalance() {
        return pkbalance;
    }
    public void setPkbalance(String pkbalance) {
        this.pkbalance = pkbalance;
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
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public BigDecimal getWealmoney() {
        return wealmoney;
    }
    public void setWealmoney(BigDecimal wealmoney) {
        this.wealmoney = wealmoney;
    }
    public Date getCreatetime() {
        return createtime;
    }
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public BigDecimal getTotalinterest() {
        return totalinterest;
    }
    public void setTotalinterest(BigDecimal totalinterest) {
        this.totalinterest = totalinterest;
    }
    public BigDecimal getInterest() {
        return interest;
    }
    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }
    public BigDecimal getDiscount() {
        return discount;
    }
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    public Integer getVirtual_count() {
        return virtual_count;
    }
    public void setVirtual_count(Integer virtual_count) {
        this.virtual_count = virtual_count;
    }

    @Override
    public String toString() {
        return "HybBalance{" +
                "pkbalance='" + pkbalance + '\'' +
                ", pkregister='" + pkregister + '\'' +
                ", pkmuser='" + pkmuser + '\'' +
                ", balance=" + balance +
                ", wealmoney=" + wealmoney +
                ", freezebalance=" + freezebalance +
                ", investbalance=" + investbalance +
                ", balance_gift=" + balance_gift +
                ", createtime=" + createtime +
                ", remark='" + remark + '\'' +
                ", totalinterest=" + totalinterest +
                ", interest=" + interest +
                ", point=" + point +
                ", virtualbalance=" + virtualbalance +
                ", growthvalue=" + growthvalue +
                ", grade='" + grade + '\'' +
                ", discount=" + discount +
                ", virtual_count=" + virtual_count +
                ", halfstorebalance=" + halfstorebalance +
                ", version_code=" + version_code +
                ", card_gift=" + card_gift +
                '}';
    }
}
