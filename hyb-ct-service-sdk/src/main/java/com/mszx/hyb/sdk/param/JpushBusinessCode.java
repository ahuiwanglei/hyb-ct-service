package com.mszx.hyb.sdk.param;

public enum  JpushBusinessCode {
    user_notification("user_notification"),
    server_notification("server_notification"),
    all_notification("all_notification");

    private String code;
    private JpushBusinessCode(String code){
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
