package com.mszx.hyb.entity;

import java.math.BigDecimal;
import java.util.Date;

public class HybWxFan {
    private Integer pkfansid;

    private Integer subscribe;

    private String openid;

    private String nickname;

    private String sexdesc;

    private Integer sex;

    private String language;

    private String city;

    private String province;

    private String country;

    private String headimgurl;

    private String subscribetime;

    private String unionid;

    private String accessToken;

    private String refreshToken;

    private String remark;

    private String appid;

    private String pkregister;

    private Date createAt;

    private BigDecimal total_integral;

    private String contact_name;

    private String contact_cellphone;

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_cellphone() {
        return contact_cellphone;
    }

    public void setContact_cellphone(String contact_cellphone) {
        this.contact_cellphone = contact_cellphone;
    }

    public BigDecimal getTotal_integral() {
        return total_integral;
    }

    public void setTotal_integral(BigDecimal total_integral) {
        this.total_integral = total_integral;
    }

    public Integer getPkfansid() {
        return pkfansid;
    }

    public void setPkfansid(Integer pkfansid) {
        this.pkfansid = pkfansid;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getSexdesc() {
        return sexdesc;
    }

    public void setSexdesc(String sexdesc) {
        this.sexdesc = sexdesc == null ? null : sexdesc.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl == null ? null : headimgurl.trim();
    }

    public String getSubscribetime() {
        return subscribetime;
    }

    public void setSubscribetime(String subscribetime) {
        this.subscribetime = subscribetime == null ? null : subscribetime.trim();
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid == null ? null : unionid.trim();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? null : refreshToken.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getPkregister() {
        return pkregister;
    }

    public void setPkregister(String pkregister) {
        this.pkregister = pkregister == null ? null : pkregister.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}