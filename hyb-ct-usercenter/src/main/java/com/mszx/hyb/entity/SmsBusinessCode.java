package com.mszx.hyb.entity;

public enum SmsBusinessCode {
    Register("register"),
    SmsLogin("smsLogin"),
    UpdatePayPwd("updatepaypwd"),
    UpdateLoginPwd("updateloginpwd"),
    BindPhone("bindPhone");

    private String code;
    private SmsBusinessCode(String code){
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
