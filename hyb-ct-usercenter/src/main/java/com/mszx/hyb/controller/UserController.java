package com.mszx.hyb.controller;

import com.mszx.hyb.common.base.BaseController;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.common.util.ResultFactory;
import com.mszx.hyb.param.ThirdLoginParam;
import com.mszx.hyb.param.UserInfo;
import com.mszx.hyb.service.IUserAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    @Autowired
    private IUserAction userAction;

    @PostMapping(value = "/findUserById", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result getUserById(@ModelAttribute UserInfo userInfo) {
        return userAction.getUserInfoByPk(userInfo.getPkregister());
    }

    @PostMapping(value = "/findUserByPhone", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result findUserByPhone(@ModelAttribute UserInfo userInfo) {
        return userAction.getUserInfoByPhone(userInfo.getPhoneno(), userInfo.getAppid());
    }

    @PostMapping(value = "/userLogin", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result userLogin(@ModelAttribute UserInfo userInfo) {
        return userAction.userLogin(userInfo);
    }

//
//    @PostMapping(value = "/authorizeThirdBindPhone", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public Result authorizeThirdBindPhone(@ModelAttribute ThirdLoginParam thirdLoginParam) {
//        return userAction.authorizeThirdBindPhone(thirdLoginParam);
//    }

    @PostMapping(value = "/authorizeThirdLogin", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result authorizeThirdLogin(@ModelAttribute ThirdLoginParam thirdLoginParam) {
        return userAction.authorizeThirdLogin(thirdLoginParam);
    }

    @PostMapping(value = "/userPasswordUpdate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result userPasswordUpdate(@ModelAttribute UserInfo userInfo) {
        return userAction.userPasswordUpdate(userInfo);
    }

    @PostMapping(value = "/updateUserLoginPwdByOldPwd", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result updateUserLoginPwdByOldPwd(@ModelAttribute UserInfo userInfo) {
        return userAction.updateUserLoginPwdByOldPwd(userInfo);
    }

    @PostMapping(value = "/updatePhonenoBySmscode", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result updatePhonenoBySmscode(@ModelAttribute UserInfo userInfo) {
        return userAction.updatePhonenoBySmscode(userInfo);
    }

    @PostMapping(value = "/userRegister", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result userRegister(@ModelAttribute UserInfo userInfo) {
        return userAction.userRegister(userInfo);
    }

    @PostMapping(value = "/updateUserPayPwd", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result updateUserPayPwd(@ModelAttribute UserInfo userInfo) {
        return userAction.updateUserPayPassword(userInfo);
    }


    @PostMapping(value = "/updateUserInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result updateUserInfo(@ModelAttribute UserInfo userInfo) {
        return userAction.updateUserInfo(userInfo);
    }

    @PostMapping(value = "/forgetPayPwd", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result forgetPayPwd(@ModelAttribute UserInfo userInfo) {
        return userAction.forgetPayPwd(userInfo);
    }



}
