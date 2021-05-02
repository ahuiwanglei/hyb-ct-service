package com.mszx.hyb.service;

import com.alibaba.fastjson.JSONObject;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.entity.HybRegister;
import com.mszx.hyb.entity.HybWxUser;
import com.mszx.hyb.param.ThirdLoginParam;
import com.mszx.hyb.param.UserInfo;

import java.util.List;


public interface IUserAction {
    Result getUserInfoByPk(String pkregister);

    Result getUserInfoByPhone(String phone, String appID);

    /*
     * @param phone 用户手机号
     * @param appID APP表示符号
     */
//    Result userSendMessageSMS(String phone, String appID, String sendType, List<String> param_list);
//
//    Result userSendYzmSMS(String phone, String appID, String sendType);

//    Result userValidateYzm(String phone, String appID, String code,String businessType);

    Result userLogin(UserInfo userInfo);

    Result userRegister(UserInfo userInfo);

    Result userPasswordUpdate(UserInfo userInfo);

    Result userLogout(String pkregister);

    Result authorizeThirdLogin(ThirdLoginParam params);

//    Result authorizeThirdBindPhone(ThirdLoginParam params);

    Result updateUserPayPassword(UserInfo userInfo);

    Result updateUserLoginPwdByOldPwd(UserInfo userInfo);

    Result updatePhonenoBySmscode(UserInfo userInfo);

    Result updateUserInfo(UserInfo userInfo);

    Result forgetPayPwd(UserInfo userInfo);

    public void checkWxFans(HybWxUser params);

    Result userRegisterWithNoSMS(UserInfo userInfo);
}
