package com.mszx.hyb.sdk.net;

import java.util.Map;

public interface BaseReqeust {

    public static final int default_timeout = 60 * 1000;
    public Map<String, String> headers();

    public String post(String host, String method, Map<String, String> params);
}
