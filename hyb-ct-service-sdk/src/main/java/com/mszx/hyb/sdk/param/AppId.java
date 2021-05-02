package com.mszx.hyb.sdk.param;

public enum  AppId {
    Hyb("hyb"),
    Shangfeng("shangfeng"),
    Majia("majia");

    private  String code;

    private AppId(String code){
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
