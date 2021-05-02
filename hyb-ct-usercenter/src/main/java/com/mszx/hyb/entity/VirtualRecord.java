package com.mszx.hyb.entity;

import java.math.BigDecimal;
import java.util.Date;

public class VirtualRecord {
    private String pkmembervirtualrecord;
    private String pkregister;
    private Date startdate;
    private BigDecimal amount;
    private Integer type;
    private String remark;
    private String createuser;
    private Date createtime;
    private String sourcepk;
    private String pkmuser;
    private String totalCount; //累计惠缘币
    private String virtualBalance; //剩余惠缘币
    private Integer status = 1;
    public VirtualRecord() {
    }

    public VirtualRecord(String pkmembervirtualrecord, String pkregister,
                         BigDecimal amount, Integer type, String remark, String sourcepk, String pkmuser) {
        this.pkmembervirtualrecord = pkmembervirtualrecord;
        this.pkregister = pkregister;
        this.amount = amount;
        this.type = type;
        this.remark = remark;
        this.sourcepk = sourcepk;
        this.pkmuser = pkmuser;
    }




    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getVirtualBalance() {
        return virtualBalance;
    }

    public void setVirtualBalance(String virtualBalance) {
        this.virtualBalance = virtualBalance;
    }

    public String getPkmuser() {
        return pkmuser;
    }

    public void setPkmuser(String pkmuser) {
        this.pkmuser = pkmuser;
    }

    public String getSourcepk() {
        return sourcepk;
    }

    public void setSourcepk(String sourcepk) {
        this.sourcepk = sourcepk;
    }

    public String getPkmembervirtualrecord() {
        return pkmembervirtualrecord;
    }
    public void setPkmembervirtualrecord(String pkmembervirtualrecord) {
        this.pkmembervirtualrecord = pkmembervirtualrecord;
    }
    public String getPkregister() {
        return pkregister;
    }
    public void setPkregister(String pkregister) {
        this.pkregister = pkregister;
    }
    public Date getStartdate() {
        return startdate;
    }
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getCreateuser() {
        return createuser;
    }
    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }
    public Date getCreatetime() {
        return createtime;
    }
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "VirtualRecord [pkmembervirtualrecord=" + pkmembervirtualrecord
                + ", pkregister=" + pkregister + ", startdate=" + startdate
                + ", amount=" + amount + ", type=" + type + ", remark="
                + remark + ", createuser=" + createuser + ", createtime="
                + createtime + "]";
    }
}
