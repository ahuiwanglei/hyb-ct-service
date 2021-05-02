package com.mszx.hyb.param;

public class ThirdLoginParam {
	private String openId; //唯一标识
	private String nickName; //昵称
	private String headImgUrl;  //头像
	private Integer loginType; // 1.wx 2.qq
	private String cpuid; //登录设备号
	private String phoneno; //手机号
	private String code; //验证码
	private String pushId;
	private String appid;
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public Integer getLoginType() {
		return loginType;
	}
	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}
	public String getCpuid() {
		return cpuid;
	}
	public void setCpuid(String cpuid) {
		this.cpuid = cpuid;
	}
	public String getPushId() {
		return pushId;
	}
	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
}
