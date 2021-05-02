package com.mszx.hyb.sdk.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mszx.hyb.sdk.config.CSConfig;
import com.mszx.hyb.sdk.param.*;
import com.mszx.hyb.sdk.result.ReqeustResult;
import com.mszx.hyb.sdk.result.RequestResultFactory;
import com.mszx.hyb.sdk.util.CSSDKException;
import com.mszx.hyb.sdk.util.EntityToMap;
import com.mszx.hyb.sdk.util.Strings;

import java.util.HashMap;
import java.util.Map;

public class UserClient extends BaseClient {

    private UserClient() {
        super();
    }

    @Override
    public String host() {
        return CSConfig.ucenter_api_host;
    }

    private static UserClient userClient;

    public static UserClient getInstance() {
        if (userClient == null) {
            userClient = new UserClient();
        }
        return userClient;
    }

    /**
     * @param pkregister 用户主键
     * @return 用户信息
     */
    public String getUserInfoById(String pkregister) {
        Map<String, String> params = new HashMap<>();
        params.put("pkregister", pkregister);
        return request.post(host(), "/api/user/findUserById", params);
    }

    /**
     * @param phone 用户手机号
     * @return 用户信息
     */
    public String getUserInfoByPhone(String phone, String appid) {
        Map<String, String> params = new HashMap<>();
        params.put("phoneno", phone);
        params.put("appid", appid);
        return request.post(host(), "/api/user/findUserByPhone", params);
    }


    /**
     * @param phone
     * @param loginpassword
     * @param appid         系统编号 {@link com.mszx.hyb.sdk.param.AppId}
     * @param cpuid
     * @param pushid
     * @return 用户信息
     * @see com.mszx.hyb.sdk.param.AppId
     */
    public String userLogin(String phone, String loginpassword, String appid, String cpuid, String pushid) {
        Map<String, String> params = new HashMap<>();
        params.put("phoneno", phone);
        params.put("loginpassword", loginpassword);
        params.put("appid", appid);
        params.put("cpuid", cpuid);
        params.put("pushId", pushid);
        return request.post(host(), "/api/user/userLogin", params);
    }


    /**
     * @param phone     手机号
     * @param smscode   验证码
     * @param openId    第三方唯一标示码
     * @param loginType 1.wx 2.qq
     * @param appid     系统编号 {@link com.mszx.hyb.sdk.param.AppId}
     * @return 用户信息
     * @see com.mszx.hyb.sdk.param.AppId
     */
    public String authorizeThirdBindPhone(String phone, String smscode, String openId, String loginType, String appid) {
        Map<String, String> params = new HashMap<>();
        params.put("phoneno", phone);
        params.put("code", smscode);
        params.put("appid", appid);
        params.put("openId", openId);
        params.put("loginType", loginType);
        return request.post(host(), "/api/user/authorizeThirdBindPhone", params);
    }

    /**
     * @param openId    第三方唯一标示码
     * @param loginType 1.wx 2.qq
     * @param appID     系统编号 {@link com.mszx.hyb.sdk.param.AppId}
     * @return 用户信息
     * @see com.mszx.hyb.sdk.param.AppId
     */
    public String authorizeThirdLogin(String openId, String loginType, String appID,
                                      String phoneno, String code, String cpuid,
                                      String pushid, String nickname, String headimg) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", appID);
        params.put("openId", openId);
        params.put("loginType", loginType);
        params.put("phoneno", phoneno);
        params.put("code", code);
        params.put("cpuid", cpuid);
        params.put("pushId", pushid);
        params.put("nickName", nickname);
        params.put("headImgUrl", headimg);
        return request.post(host(), "/api/user/authorizeThirdLogin", params);
    }

    /**
     * @param phone         手机号
     * @param loginpassword 登录密码（加密后）
     * @param smscode       短信验证码
     * @param appid         系统编号 {@link com.mszx.hyb.sdk.param.AppId}
     * @return 更新结果
     * @see com.mszx.hyb.sdk.param.AppId
     */
    public String userPasswordUpdate(String phone, String loginpassword, String smscode, String appid) {
        Map<String, String> params = new HashMap<>();
        params.put("phoneno", phone);
        params.put("loginpassword", loginpassword);
        params.put("code", smscode);
        params.put("appid", appid);
        return request.post(host(), "/api/user/userPasswordUpdate", params);
    }

    /**
     * 使用旧密码修改新密码
     *
     * @param phone
     * @param loginpassword
     * @param newpassword
     * @param appid
     * @return
     */
    public String updateUserLoginPwdByOldpwd(String phone, String loginpassword, String newpassword, String appid) {
        Map<String, String> params = new HashMap<>();
        params.put("phoneno", phone);
        params.put("loginpassword", loginpassword);
        params.put("newpassword", newpassword);
        params.put("appid", appid);
        return request.post(host(), "/api/user/updateUserLoginPwdByOldPwd", params);
    }

    /**
     * @param phone      现在的手机号
     * @param newphoneno 新手机号
     * @param code       验证码
     * @param appid
     * @return
     */
    public String updatePhonenoBySmscode(String phone, String newphoneno, String code, String appid) {
        Map<String, String> params = new HashMap<>();
        params.put("phoneno", phone);
        params.put("code", code);
        params.put("newphoneno", newphoneno);
        params.put("appid", appid);
        return request.post(host(), "/api/user/updatePhonenoBySmscode", params);
    }

    /**
     * @param phone         手机号
     * @param loginpassword 登录密码（加密后）
     * @param smscode       短信验证码
     * @param appid         系统编号
     * @return 用户信息
     */
    public String userRegister(String phone, String loginpassword, String smscode, String appid) {
        Map<String, String> params = new HashMap<>();
        params.put("phoneno", phone);
        params.put("loginpassword", loginpassword);
        params.put("code", smscode);
        params.put("appid", appid);
        return request.post(host(), "/api/user/userRegister", params);
    }

    /**
     * 修改支付密码
     *
     * @param phone
     * @param paypassword
     * @param newpaypassword
     * @param appid
     * @return 修改结果
     */
    public String updatePayPassword(String phone, String paypassword, String newpaypassword, String appid) {
        Map<String, String> params = new HashMap<>();
        params.put("phoneno", phone);
        params.put("paypassword", paypassword);
        params.put("newpaypassword", newpaypassword);
        params.put("appid", appid);
        return request.post(host(), "/api/user/updateUserPayPwd", params);
    }

    /**
     * 修改用户信息
     *
     * @param userInfo pkregister appid 必填
     * @return
     */
    public String updateUserInfo(UserInfo userInfo) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", userInfo.getAppid());
        params.put("pkregister", userInfo.getPkregister());
        if (Strings.isNotNullOrEmpty(userInfo.getHeadimg())) {
            params.put("headimg", userInfo.getHeadimg());
        }
        if (Strings.isNotNullOrEmpty(userInfo.getNickname())) {
            params.put("nickname", userInfo.getNickname());
        }
        return request.post(host(), "/api/user/updateUserInfo", params);
    }

    /**
     * 忘记支付密码，重置密码
     *
     * @param appid
     * @param phoneno
     * @param code
     * @param paypassword
     * @return
     */
    public String forgetPayPwd(String appid, String phoneno, String code, String paypassword) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("phoneno", phoneno);
        params.put("code", code);
        params.put("paypassword", paypassword);
        return request.post(host(), "/api/user/forgetPayPwd", params);
    }

    /**
     * @param wxFan
     * @return 更新或新增粉丝
     */
    public String saveWxFans(HybWxFan wxFan) {
        Map<String, String> params = EntityToMap.ConvertObjToMap(wxFan);
        return request.post(host(), "/api/wx/fans/save", params);
    }

    /**
     * @param wxFan
     * @return 查询粉丝
     */
    public String findFansByOpenIdOrPkfansid(HybWxFan wxFan) {
        Map<String, String> params = EntityToMap.ConvertObjToMap(wxFan);
        return request.post(host(), "/api/wx/fans/info", params);
    }

    public String updateFansIntegral(HybWxIntegral hybWxIntegral) {
        Map<String, String> params = EntityToMap.ConvertObjToMap(hybWxIntegral);
        return request.post(host(), "/api/wx/fans/integral", params);
    }

    public String updateWxFansContact(HybWxFan hybWxFan) {
        Map<String, String> params = EntityToMap.ConvertObjToMap(hybWxFan);
        return request.post(host(), "/api/wx/fans/updateContact", params);
    }

    public String updateBalance(BalanceParams balanceParams) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", balanceParams.getAppid());
        params.put("pkregister", balanceParams.getPkregister());
        params.put("pkmuser", balanceParams.getPkmuser());
        params.put("orderMoney", balanceParams.getOrderMoney() + "");
        params.put("beforeTotalInterest", "0");
        params.put("orderSubject", balanceParams.getOrderSubject());
        params.put("changeType", balanceParams.getChangeType() + "");
        params.put("biztype", balanceParams.getBiztype() + "");
        params.put("source_type", balanceParams.getSource_type() + "");
        params.put("orderid", balanceParams.getOrderid());
        return request.post(host(), "/api/user_balance/updateBalance", params);
    }


    /**
     * openid,
     * session_key
     *
     * @param hybWxUser
     * @return
     * @throws CSSDKException
     */
    public HybWxUser miniProgramLogin(HybWxUser hybWxUser) throws CSSDKException {
        Map<String, String> params = EntityToMap.ConvertObjToMap(hybWxUser);
        String result = request.post(host(), "/api/wx/findUserByOpenId", params);
        System.out.println(result);
        ReqeustResult<String> commonResult = JSON.parseObject(result, new TypeReference<ReqeustResult<String>>() {
        });
        if (commonResult.getResultStatus() != RequestResultFactory.RESULT_CODE_SUCCESS) {
            throw new CSSDKException(commonResult.getResultStatus(), commonResult.getMsg());
        } else {
            ReqeustResult<HybWxUser> hybWxUserReqeustResult = JSON.parseObject(result, new TypeReference<ReqeustResult<HybWxUser>>() {
            });
            return hybWxUserReqeustResult.getResultData();
        }
    }

    /**
     * openid,
     * session_key
     *
     * @param hybWxUser
     * @return
     * @throws CSSDKException
     */
    public HybWxUser miniProgramLoginWithNoSMS(HybWxUser hybWxUser) throws CSSDKException {
        Map<String, String> params = EntityToMap.ConvertObjToMap(hybWxUser);
        String result = request.post(host(), "/api/wx/findUserByOpenIdWithNoSMS", params);
        System.out.println(result);
        ReqeustResult<String> commonResult = JSON.parseObject(result, new TypeReference<ReqeustResult<String>>() {
        });
        if (commonResult.getResultStatus() != RequestResultFactory.RESULT_CODE_SUCCESS) {
            throw new CSSDKException(commonResult.getResultStatus(), commonResult.getMsg());
        } else {
            ReqeustResult<HybWxUser> hybWxUserReqeustResult = JSON.parseObject(result, new TypeReference<ReqeustResult<HybWxUser>>() {
            });
            return hybWxUserReqeustResult.getResultData();
        }
    }

    /**
     * code 验证码
     * nickname 昵称
     * 。。。
     *
     * @param hybWxUser
     * @return
     */
    public HybWxUser miniProgramRegister(HybWxUser hybWxUser) throws CSSDKException {
        Map<String, String> params = EntityToMap.ConvertObjToMap(hybWxUser);
        String result = request.post(host(), "/api/wx/wxRegister", params);
        ReqeustResult<HybWxUser> hybWxUserReqeustResult = JSON.parseObject(result, new TypeReference<ReqeustResult<HybWxUser>>() {
        });
        if (hybWxUserReqeustResult.getResultStatus() != RequestResultFactory.RESULT_CODE_SUCCESS) {
            return hybWxUserReqeustResult.getResultData();
        } else {
            throw new CSSDKException(hybWxUserReqeustResult.getResultStatus(), hybWxUserReqeustResult.getMsg());
        }
    }


    public static void main(String args[]) {
        CSConfig.newBuild().host("http://123.57.232.188:19097/hyb-ct-usercenter/", "http://123.57.232.188:19099/hyb-ct-ums/", "http://123.57.232.188:19098/hyb-ct-ordercenter/").key("ct_mobile_request_key").secret("00004e7891684060b980cd2bbad7a4e1").signpwd("B632B014A338A6CD1638BE148582DB1C");
//            System.out.println("**********************用户信息1****************************************");
//            System.out.println(UserClient.getInstance().getUserInfoById("0000be84cfed4589b09c976c065fc0c1"));
//            System.out.println("**********************用户信息2**************************************");
//            System.out.println(UserClient.getInstance().getUserInfoByPhone("18362915512", AppId.Hyb.getCode()));
//            System.out.println("*********************登录*****************************************");
//            System.out.println(UserClient.getInstance().userLogin("18362915512", "6dd3d1890fcc3a3ec0f54bad63e8ab8a", AppId.Hyb.getCode(), "222", "12312"));

//        System.out.println(UmsClient.getInstance().smsCodePhone("hyb", "18362915512", SmsBusinessCode.Register.getCode(),
//                "hyb_register", "123456"));


        HybWxUser hybWxUser = new HybWxUser();
        hybWxUser.setOpenid("oVWCf0ScnQrfmTPz26aO78-0p2Y5");
        hybWxUser.setAccessToken("sdfasdfasd");
        try {
            HybWxUser hybWxUser1 = UserClient.getInstance().miniProgramLogin(hybWxUser);
            System.out.println(JSON.toJSONString(hybWxUser1));
        } catch (CSSDKException e) {
            e.printStackTrace();
        }

//        CheckConsumeParams params = new CheckConsumeParams();
//        params.setCheck_code("1234567");
//        params.setCheck_consume_user("wl");
//        params.setOrderNum("229433521528080911874");
//        params.setPkmuser("b019ebca21184c41ba7c2d852b161f80");
//        System.out.println(OrderClient.getInstance().checkConsume(params));
//        SearchOrderListParams params = new SearchOrderListParams();
//        params.setAppid("hyb");
//        params.setPageNum(1);
//        params.setPageSize(2);
//        params.setPkmuser_name("");
//        System.out.println(OrderClient.getInstance().searchOrderInfo(params));
//        String rest = UserClient.getInstance().updateUserLoginPwdByOldpwd("18362915512", "123", "121", "hyb");
//        System.out.println(rest);
//        UmsClient.getInstance().smsCodePhone("hyb", "18362915512", "register", "hyb_code","123456");
//        System.out.println(UmsClient.getInstance().getJpushRecordList("hyb", JpushBusinessCode.all_notification, "0000be84cfed4589b09c976c065fc0c1", 1,1));

//        for (int i = 0; i < 5; i++) {
//        UserInfo userInfo = new UserInfo();
//        userInfo.setAppid("shangfeng");
//        userInfo.setPkregister("7a30cbe6695a4e239fe727fc1241889a");
//        userInfo.setNickname("wanglei");
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    HybWxIntegral hybWxIntegral = new HybWxIntegral();
//                    hybWxIntegral.setFansid(1);
//                    System.out.println(UserClient.getInstance().updateFansIntegral(hybWxIntegral));
//                }
//            }).start();

//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

//        BalanceParams balanceParams = new BalanceParams();
//        balanceParams.setAppid("shangfeng");
//        balanceParams.setPkregister("7a30cbe6695a4e239fe727fc1241889a");
//        balanceParams.setPkmuser("");
//        balanceParams.setChangeType(BalanceParams.ChangeType.Add.getValue());
//        balanceParams.setBiztype(BalanceParams.BizType.SystemBalance.getValue());
//        balanceParams.setOrderMoney(new BigDecimal("100"));
//        balanceParams.setOrderSubject("分享收益");
//        System.out.println(UserClient.getInstance().updateBalance(balanceParams));

    }
}
