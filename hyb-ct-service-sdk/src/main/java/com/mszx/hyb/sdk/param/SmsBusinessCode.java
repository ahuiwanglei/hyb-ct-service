package com.mszx.hyb.sdk.param;

public enum SmsBusinessCode {
    Register("register"),
    SmsLogin("smsLogin"),
    UpdatePayPwd("updatepaypwd"),
    UpdateLoginPwd("updateloginpwd"),
    BindPhone("bindPhone"),
    CrowdfundingChangeAccountCardnum("cfChangeAccountCardnum");;

    private String code;
    private SmsBusinessCode(String code){
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
