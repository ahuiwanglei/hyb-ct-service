package com.mszx.hyb.entity;

import java.util.Date;

public class HybRegisterLogin {
    private String loginid;

    private String pkregister;

    private String appid;

    private Date logintime;

    private String cpuid;

    private String pushid;

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid == null ? null : loginid.trim();
    }

    public String getPkregister() {
        return pkregister;
    }

    public void setPkregister(String pkregister) {
        this.pkregister = pkregister == null ? null : pkregister.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    public String getCpuid() {
        return cpuid;
    }

    public void setCpuid(String cpuid) {
        this.cpuid = cpuid == null ? null : cpuid.trim();
    }

    public String getPushid() {
        return pushid;
    }

    public void setPushid(String pushid) {
        this.pushid = pushid == null ? null : pushid.trim();
    }
}