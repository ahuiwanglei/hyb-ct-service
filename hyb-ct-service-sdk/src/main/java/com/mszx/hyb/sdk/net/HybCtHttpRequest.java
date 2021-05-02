package com.mszx.hyb.sdk.net;

import com.alibaba.fastjson.JSON;
import com.mszx.hyb.sdk.config.CSConfig;
import com.mszx.hyb.sdk.result.RequestResultFactory;
import com.mszx.hyb.sdk.util.AES;
import com.mszx.hyb.sdk.util.CSConfigException;
import com.mszx.hyb.sdk.util.Strings;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class HybCtHttpRequest implements BaseReqeust {

    private static Logger logger = LoggerFactory.getLogger(HybCtHttpRequest.class);

//    private OkHttpClient okHttpClient;

    @Override
    public Map<String, String> headers() {
        Map<String, String> maps = new HashMap<>();
        maps.put("platform_key", CSConfig.app_key);
        maps.put("platform_secret", CSConfig.app_secret);
        return maps;
    }

    @Override
    public String post(String host, String method, Map<String, String> params) {
        logger.info("host=" + host + " method=" + method);
        try {
            checkConfig(method);
        } catch (CSConfigException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }

        Map<String, String> headers = headers();
        if (params != null && !params.isEmpty()) {
            String serverSign = AES.createSign(getStringStringSortedMap(params), CSConfig.app_signpwd);
            headers.put("sign", serverSign);
        }
        try {
            HttpResponse httpResponse = HttpUtils.doPost(host, method, "", headers, null, params);
            logger.info("result=" + httpResponse.getStatusLine());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return com.alibaba.fastjson.JSON.toJSONString(RequestResultFactory.getErrorResult("请求失败,"));
    }

    private void checkConfig(String url) throws CSConfigException {
        if (Strings.isNullOrEmpty(CSConfig.app_key)) {
            throw new CSConfigException("key不能为空");
        }
        if (Strings.isNullOrEmpty(CSConfig.app_secret)) {
            throw new CSConfigException("secret不能为空");
        }
        if (Strings.isNullOrEmpty(CSConfig.app_signpwd)) {
            throw new CSConfigException("signpwd不能为空");
        }
        if (url.contains("/api/user/") && Strings.isNullOrEmpty(CSConfig.ucenter_api_host)) {
            throw new CSConfigException("用户中心地址不能为空");
        }
        if (url.contains("/api/ums/") && Strings.isNullOrEmpty(CSConfig.ums_api_host)) {
            throw new CSConfigException("通知中心地址不能为空");
        }
    }

//    public void setOkHttpClient(OkHttpClient okHttpClient) {
//        this.okHttpClient = okHttpClient;
//    }


    private SortedMap<String, String> getStringStringSortedMap(Map<String, String> maps) {
        SortedMap<String, String> sortedMap = new TreeMap<String, String>();
        for (String key : maps.keySet()) {
            String thisValue = maps.get(key);
            if (!key.equals("sign") && !key.equals("key") && Strings.isNotNullOrEmpty(thisValue)) {
                sortedMap.put(key, thisValue);
            }
        }
        return sortedMap;
    }


}
