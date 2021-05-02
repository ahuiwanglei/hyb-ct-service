package com.mszx.hyb.sdk.user;

import com.mszx.hyb.sdk.config.CSConfig;

import java.util.HashMap;
import java.util.Map;

public class MobileTestClient extends BaseClient{
    @Override
    public String host() {
        return "http://localhost:9094/api/";
    }


    public String getUser(String pkregister){
        Map<String, String> params = new HashMap<>();
        params.put("pkregister", pkregister);
        return request.post(host(),"user/info", params);
    }

    public static void main(String args[]){
        CSConfig.newBuild().host("http://47.96.105.168:9097/hyb-ct-usercenter/","http://47.96.105.168:9099/hyb-ct-ums/","http://47.96.105.168:9098/hyb-ct-ordercenter/").key("ct_mobile_request_key").secret("00004e7891684060b980cd2bbad7a4e1").signpwd("B632B014A338A6CD1638BE148582DB1C");
        MobileTestClient mobileTestClient = new MobileTestClient();
        for (int i=0;i< 10000;i++) {
//
            System.out.println(mobileTestClient.getUser("7a30cbe6695a4e239fe727fc1241889a"));
            try {
                Thread.sleep(32000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }


}
