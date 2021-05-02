package com.mszx.hyb.sdk.config;

public class CSConfig {

    public static String ucenter_api_host = "";
    public static String ums_api_host = "";
    public static String order_api_host = "";
    public static String app_key = "";
    public static String app_secret = "";
    public static String app_signpwd = "";

    public static CSConfig newBuild() {
        return new CSConfig();
    }
    /**
     * 用户中心接口地址
     * @param host
     * @return
     */
    public CSConfig host(String host) {
        ucenter_api_host = host;
        return this;
    }

    public CSConfig host(String host, String ums_host) {
        ucenter_api_host = host;
        ums_api_host = ums_host;
        return this;
    }

    public CSConfig host(String host, String ums_host, String order_host) {
        ucenter_api_host = host;
        ums_api_host = ums_host;
        order_api_host = order_host;
        return this;
    }

    public CSConfig key(String key) {
        app_key = key;
        return this;
    }

    public CSConfig secret(String secret) {
        app_secret = secret;
        return this;
    }

    public CSConfig signpwd(String signpwd) {
        app_signpwd = signpwd;
        return this;
    }


}
