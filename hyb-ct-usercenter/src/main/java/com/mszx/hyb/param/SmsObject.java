package com.mszx.hyb.param;

import java.util.Date;

public class SmsObject {
	private String phone; //手机号码+APPID表示共同组成key
	private String smsCode;
	private Date sendTime;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	

}
