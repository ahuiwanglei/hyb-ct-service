package com.mszx.hyb.sdk.user;

import com.mszx.hyb.sdk.config.CSConfig;
import com.mszx.hyb.sdk.param.JpushBusinessCode;
import com.mszx.hyb.sdk.param.SmsBusinessCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UmsClient extends BaseClient{
    private UmsClient() {
        super();
    }

    private static UmsClient umsClient;

    public static UmsClient getInstance() {
        if (umsClient == null) {
            umsClient = new UmsClient();
        }
        return umsClient;
    }

    @Override
    public String host() {
        return CSConfig.ums_api_host;
    }

    /**
     *
     * @param appid
     * @param phone
     * @param business_code {@link com.mszx.hyb.sdk.param.SmsBusinessCode}
     * @param template_code
     * @param code 验证码
     * @return
     */
    public String smsCodePhone(String appid, String phone, String business_code,String template_code, String code){
        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("phoneno", phone);
        params.put("business_code", business_code);
        params.put("template_code", template_code);
        params.put("args", code);
        return request.post(host(),"/api/ums/sms/sendPhone", params);
    }

    /**
     *
     * @param appid
     * @param phone
     * @param business_code
     * @param template_code  {@link com.mszx.hyb.sdk.param.SmsBusinessCode}
     * @param args 短信内容参数
     * @return
     */
    public String smsContentPhone(String appid, String phone, String business_code, String template_code, List<String> args){
        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("phoneno", phone);
        params.put("business_code", business_code);
        params.put("template_code", template_code);
        if(args == null && args.size() >0){
            for(int i=0;i< args.size();i++){
                params.put("args", args.get(i));
            }
        }
        return request.post(host(),"/api/ums/sms/sendPhone", params);
    }

    /**
     * 验证后 验证码自动过期
     * @param appid
     * @param phone
     * @param business_code {@link com.mszx.hyb.sdk.param.SmsBusinessCode}
     * @param code
     * @return
     */
    public String validateSmsCode(String appid, String phone, String business_code,String code){
        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("phoneno", phone);
        params.put("business_code", business_code);
        params.put("is_invalid", "1");
        params.put("code", code);
        return request.post(host(),"/api/ums/sms/validateSmsCode", params);
    }

    /** is_invalid 0：未过期，
     * @param appid
     * @param phone
     * @param business_code {@link com.mszx.hyb.sdk.param.SmsBusinessCode}
     * @param code
     * @return
     */
    public String validateSmsCodeIsInvalid(String appid, String phone, String business_code,String code,Integer is_invalid){
        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("phoneno", phone);
        params.put("business_code", business_code);
        params.put("code", code);
        params.put("is_invalid", ""+is_invalid);
        return request.post(host(),"/api/ums/sms/validateSmsCode", params);
    }

    /**
     *
     * @param appid
     * @param audience_type 0 : regid 1: tags
     * @param reg_or_tags
     * @param content_args 内容参数
     * @param userid  用户ID
     * @param business_code 业务
     * @param template_code 模版编码
     * @param extras
     * @return
     */
    public String sendNotification(String appid, String template_code, int audience_type, String reg_or_tags ,
                                   String[] content_args, String userid, JpushBusinessCode business_code,
                                   Map<String, String> extras){
        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("template_code", template_code);
        params.put("audience_type", audience_type+"");
        if(audience_type == 0){
            params.put("registration_id", reg_or_tags);
        }else if(audience_type == 1){
            params.put("tags", reg_or_tags);
        }
        params.put("userid",userid);
        if(content_args != null && content_args.length >0){
            for (int i=0;i< content_args.length;i++){
                params.put("args[" +i + "]",content_args[i]);
            }
        }
        if(extras != null && !extras.isEmpty()){
            for (Map.Entry<String, String> entry : extras.entrySet()) {
                params.put("extras[" + entry.getKey() + "]",entry.getValue());
            }
        }
        params.put("business_code",business_code.getCode());
        return request.post(host(),"/api/ums/push/sendNotification", params);
    }

    /**
     *
     * @param appid
     * @param businessCode
     * @param userid
     * @param pageNum
     * @param pageSize
     * @return
     */
    public String getJpushRecordList(String appid,JpushBusinessCode businessCode,  String userid, Integer pageNum, Integer pageSize){
        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("userid", userid);
        params.put("business_code",businessCode.getCode());
        params.put("pageNum", pageNum+"");
        params.put("pageSize",pageSize+"");
        return request.post(host(),"/api/ums/push/getRecordList", params);
    }

    public static void main(String args[]) {
        CSConfig.newBuild().host("http://47.96.105.168:9094/hyb-ct-usercenter","http://47.96.105.168:9099/hyb-ct-ums").key("ct_mobile_request_key").secret("00004e7891684060b980cd2bbad7a4e1").signpwd("B632B014A338A6CD1638BE148582DB1C");
        System.out.println(UmsClient.getInstance().smsCodePhone("hyb","18362915512", SmsBusinessCode.Register.getCode(), "shangfeng_register","123456"));
//        System.out.println(UserClient.getInstance().getUserInfoById("0000be84cfed4589b09c976c065fc0c1"));
//        System.out.println(UmsClient.getInstance().getJpushRecordList("shangfeng", JpushBusinessCode.all_notification, "0000be84cfed4589b09c976c065fc0c1",1,10));
//        Map<String, String> extras = new HashMap<>();
//        extras.put("orderid", "1");
//        extras.put("type","2");
//        System.out.println(UmsClient.getInstance().sendNotification("shangfeng", "scan_code_pay", 1, "13270713503", new String[]{"中石油", "1.01"}, "d055d59008f22ef7fed43490582ff48f", JpushBusinessCode.user_notification, extras));
    }
}
