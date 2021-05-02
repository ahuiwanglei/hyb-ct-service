package com.mszx.hyb.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.mszx.hyb.common.dao.HybPlatformSecretMapper;
import com.mszx.hyb.common.entity.HybPlatformSecret;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.common.util.AES;
import com.mszx.hyb.common.util.ResultFactory;
import com.mszx.hyb.common.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.SortedMap;
import java.util.TreeMap;

public class SecretValidateInterceptor extends HandlerInterceptorAdapter {

    public Logger logger = LoggerFactory.getLogger(LogCommonInterceptor.class);

    @Autowired
    private HybPlatformSecretMapper hybPlatformSecretMapper;

    @Value("${api.sign.validate.skip:0}")
    private boolean validateSkip;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().contains("/resource/")
                || request.getRequestURI().contains("/wap/")) {
            return true;
        }
        logger.info("********************************验证Secret Sign***********************************************");
        String key = request.getHeader("platform_key");
        String secret = request.getHeader("platform_secret");
        if(Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(secret)){
            Result result = ResultFactory.getErrorResult(9001, "secret不能为空");
            printJsonObject(response, JSONObject.toJSONString(result));
            return false;
        }
        HybPlatformSecret hybPlatformSecret = hybPlatformSecretMapper.selectByPrimaryKey(key, secret);
        if(hybPlatformSecret == null){//验证未通过
            Result result = ResultFactory.getErrorResult(9001, "secret不正确");
            printJsonObject(response, JSONObject.toJSONString(result));
            return false;
        }
        if("0".equals(hybPlatformSecret.getIs_skip())){
            String sign = request.getHeader("sign");
            SortedMap<String, String> sortedMap = getStringStringSortedMap(request);
            if(sortedMap.isEmpty()){//没有参数，不必验证
                return true;
            }else{
                if(Strings.isNullOrEmpty(sign)){
                    Result result = ResultFactory.getErrorResult(9002, "sign不能为空");
                    printJsonObject(response, JSONObject.toJSONString(result));
                    return false;
                }
                String serverSign = AES.createSign(sortedMap, hybPlatformSecret.getSignpwd());
                logger.info("本地计算Sign:" + serverSign +"  sign=" + sign);
                if(!validateSkip && !serverSign.equals(sign)) {
                    Result result = ResultFactory.getErrorResult(9002, "sign错误，拉出去枪毙");
                    printJsonObject(response, JSONObject.toJSONString(result));
                    return false;
                }
            }
        }

        logger.info("********************************sign*end**********************************************");
        return true;
    }


    private SortedMap<String, String> getStringStringSortedMap(HttpServletRequest request) {
        SortedMap<String, String> sortedMap = new TreeMap<String, String>();
        Enumeration<String> params = request.getParameterNames();
        for (Enumeration e = params; e.hasMoreElements(); ) {
            String thisName = e.nextElement().toString();
            String thisValue = request.getParameter(thisName);
            if (!thisName.equals("sign") && !thisName.equals("key") && Strings.isNotNullOrEmpty(thisValue)) {
                sortedMap.put(thisName, thisValue);
            }
        }
        return sortedMap;
    }

    private void printJsonObject(HttpServletResponse response, String result){
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(result);//写回
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
