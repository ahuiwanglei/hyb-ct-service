package com.mszx.hyb.entity;

public class HybRegister {
    private String pkregister;

    private String cpuid;

    private String phoneno;

    private String email;

    private String loginpassword;

    private String paypassword;

    private String position;

    private String registerdate;

    private String locktime;

    private String nickname;

    private Integer sex;

    private String birthday;

    private Integer uid;

    private String wxOpenid;

    private String qqOpenid;

    private String bgpic;

    private String appid;

    public String getPkregister() {
        return pkregister;
    }

    public void setPkregister(String pkregister) {
        this.pkregister = pkregister == null ? null : pkregister.trim();
    }

    public String getCpuid() {
        return cpuid;
    }

    public void setCpuid(String cpuid) {
        this.cpuid = cpuid == null ? null : cpuid.trim();
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno == null ? null : phoneno.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getLoginpassword() {
        return loginpassword;
    }

    public void setLoginpassword(String loginpassword) {
        this.loginpassword = loginpassword == null ? null : loginpassword.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getRegisterdate() {
        return registerdate;
    }

    public void setRegisterdate(String registerdate) {
        this.registerdate = registerdate == null ? null : registerdate.trim();
    }

    public String getLocktime() {
        return locktime;
    }

    public void setLocktime(String locktime) {
        this.locktime = locktime == null ? null : locktime.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid == null ? null : wxOpenid.trim();
    }

    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid == null ? null : qqOpenid.trim();
    }

    public String getBgpic() {
        return bgpic;
    }

    public void setBgpic(String bgpic) {
        this.bgpic = bgpic == null ? null : bgpic.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getPaypassword() {
        return paypassword;
    }

    public void setPaypassword(String paypassword) {
        this.paypassword = paypassword == null ? null : paypassword.trim();;
    }
}