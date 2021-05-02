package com.mszx.hyb.service.impl;

import com.github.binarywang.java.emoji.EmojiConverter;
import com.github.pagehelper.StringUtil;
import com.mszx.hyb.common.model.CommonFinal;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.common.util.CommonUtil;
import com.mszx.hyb.common.util.ResultFactory;
import com.mszx.hyb.common.util.Strings;
import com.mszx.hyb.dao.HybRegisterLoginMapper;
import com.mszx.hyb.dao.HybRegisterMapper;
import com.mszx.hyb.dao.HybWxFanMapper;
import com.mszx.hyb.entity.*;
import com.mszx.hyb.param.ThirdLoginParam;
import com.mszx.hyb.param.UserInfo;
import com.mszx.hyb.sdk.user.UmsClient;
import com.mszx.hyb.service.IBalanceAction;
import com.mszx.hyb.service.IUserAction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserActionService implements IUserAction {
    private static Logger logger = LoggerFactory.getLogger(UserActionService.class);

    @Autowired
    HybRegisterMapper hybRegisterMapper;
    @Autowired
    HybRegisterLoginMapper hybRegisterLoginMapper;

    @Autowired
    HybWxFanMapper hybWxFanMapper;

    @Autowired
    IBalanceAction balanceAction;

    public Result getUserInfoByPk(String pkregister) {
        if (Strings.isEmpty(pkregister)) {
            return ResultFactory.getErrorResult("用户主键不能为空");
        }
        HybRegister hybRegister = hybRegisterMapper.selectByPrimaryKey(pkregister);
        if (hybRegister == null) {
            return ResultFactory.getErrorResult("用户不存在，请检查");
        }
        if(StringUtil.isNotEmpty(hybRegister.getNickname())){
            hybRegister.setNickname(EmojiConverter.getInstance().toUnicode(hybRegister.getNickname()));
        }
        return ResultFactory.getSuccessResult("user", hybRegister);
    }

    public Result getUserInfoByPhone(String phone, String appID) {
        if (Strings.isEmpty(phone)) {
            return ResultFactory.getErrorResult("手机号不能为空");
        }
        if (Strings.isEmpty(appID)) {
            return ResultFactory.getErrorResult("平台不能为空");
        }
        HybRegister hybRegister = hybRegisterMapper.selectByPhoneKey(phone, appID);
        if (hybRegister == null) {
            return ResultFactory.getErrorResult("用户不存在，请检查");
        }
        if(StringUtil.isNotEmpty(hybRegister.getNickname())){
            hybRegister.setNickname(EmojiConverter.getInstance().toUnicode(hybRegister.getNickname()));
        }
        return ResultFactory.getSuccessResult("user", hybRegister);
    }


    // 手机号和密码登录
    public Result userLogin(UserInfo userInfo) {
        // TODO Auto-generated method stub
        if (Strings.isNullOrEmpty(userInfo.getPhoneno())) {
            return ResultFactory.getErrorResult("登录号不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getLoginpassword())) {
            return ResultFactory.getErrorResult("请输入密码");
        }
        if (Strings.isNullOrEmpty(userInfo.getCpuid())) // 默认hyb
        {
            return ResultFactory.getErrorResult("cpuid不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getAppid())) // 默认hyb
        {
            return ResultFactory.getErrorResult("平台不能为空");
        }

        HybRegister hybRegister = hybRegisterMapper.selectByPhoneKey(userInfo.getPhoneno(), userInfo.getAppid());
        if (hybRegister == null) {
            return ResultFactory.getErrorResult("用户不存在，请检查");
        }

        // 判断密码是否正确
        if (!hybRegister.getLoginpassword().equals(userInfo.getLoginpassword())) {
            return ResultFactory.getErrorResult("用户密码错误，请重试");
        }
        if (hybRegister.getLocktime() != null) {
            return ResultFactory.getErrorResult("用户账号锁定，不能登录APP，请联系客服");
        }
        if(StringUtil.isNotEmpty(hybRegister.getNickname())){
            hybRegister.setNickname(EmojiConverter.getInstance().toUnicode(hybRegister.getNickname()));
        }

        // 处理登录信息
        HybRegisterLogin hybRegisterLogin = hybRegisterLoginMapper.selectByPkregisterKey(hybRegister.getPkregister());
        if (hybRegisterLogin == null) {
            // add a record
            HybRegisterLogin hybRegisterLoginNew = new HybRegisterLogin();
            hybRegisterLoginNew.setAppid(userInfo.getAppid());
            hybRegisterLoginNew.setCpuid(userInfo.getCpuid());
            hybRegisterLoginNew.setLoginid(CommonUtil.get32UUID());
            hybRegisterLoginNew.setLogintime(new Date());
            hybRegisterLoginNew.setPkregister(hybRegister.getPkregister());
            hybRegisterLoginNew.setPushid(userInfo.getPushId());
            try {
                hybRegisterLoginMapper.insertSelective(hybRegisterLoginNew);
            } catch (Exception e) {
                return ResultFactory.getErrorResult("app参数错误，指定参数为空");
            }
        } else {
            // update a record
            hybRegisterLogin.setPushid(userInfo.getPushId());
            hybRegisterLogin.setLogintime(new Date());
            try {
                hybRegisterLoginMapper.updateByPrimaryKeySelective(hybRegisterLogin);
            } catch (Exception e) {
                return ResultFactory.getErrorResult("app参数错误，指定参数为空");
            }
        }
        return ResultFactory.getSuccessResult("user", hybRegister);

    }

    // 普通注册 通过验证码注册

    @Transactional(readOnly = false, propagation= Propagation.REQUIRES_NEW)
    @Override
    public Result userRegister(UserInfo userInfo) {
        if (Strings.isNullOrEmpty(userInfo.getPhoneno())) {
            return ResultFactory.getErrorResult("手机号不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getLoginpassword())) {
            return ResultFactory.getErrorResult("请输入密码");
        }
        if (Strings.isNullOrEmpty(userInfo.getCode())) {
            return ResultFactory.getErrorResult("请输入验证码");
        }
        if (Strings.isNullOrEmpty(userInfo.getAppid())) // 默认hyb
        {
            return ResultFactory.getErrorResult("平台不能为空");
        }
        // 判断验证码
        Result ob = ResultFactory.convertResult(UmsClient.getInstance().validateSmsCode(userInfo.getAppid(), userInfo.getPhoneno(), SmsBusinessCode.Register.getCode(), userInfo.getCode()));
        if (ob == null) {
            logger.info("ums 未启动");
            return ResultFactory.getErrorResult("ums 未启动");
        }
        if (ob.getResultStatus() == CommonFinal.RESULT_CODE_FAILURE) {
            return ob;
        }
        if(StringUtils.isNotEmpty(userInfo.getWx_openid())){
           HybRegister hybRegister2 = hybRegisterMapper.selectByWeixinKey(userInfo.getWx_openid(), userInfo.getAppid());
           if(hybRegister2 != null){
               return ResultFactory.getErrorResult("该微信已被注册");
           }
        }
        // 判断手机号是否已经存在
        HybRegister hybRegister = hybRegisterMapper.selectByPhoneKey(userInfo.getPhoneno(), userInfo.getAppid());
        if (hybRegister != null) {
            if(StringUtil.isEmpty(hybRegister.getWxOpenid()) && StringUtil.isNotEmpty(userInfo.getWx_openid())){
                HybRegister updateHybRegister = new HybRegister();
                updateHybRegister.setPkregister(hybRegister.getPkregister());
                updateHybRegister.setWxOpenid(userInfo.getWx_openid());
                hybRegisterMapper.updateByPrimaryKeySelective(updateHybRegister);
            }
            logger.info("手机号已经存在，请您登录，如果忘记密码，请选择找回密码");
            return ResultFactory.getErrorResult(-2,"手机号已经存在，请您登录，如果忘记密码，请选择找回密码");
        }
        HybRegister hybRegisterNew = new HybRegister();
        hybRegisterNew.setAppid(userInfo.getAppid());
        hybRegisterNew.setLoginpassword(userInfo.getLoginpassword());
        hybRegisterNew.setPhoneno(userInfo.getPhoneno());
        hybRegisterNew.setPkregister(CommonUtil.get32UUID());
        hybRegisterNew.setRegisterdate(String.valueOf(System.currentTimeMillis()));
        if(StringUtils.isNotEmpty(userInfo.getNickname())){
            hybRegisterNew.setNickname(EmojiConverter.getInstance().toAlias(userInfo.getNickname()));
        }
        hybRegisterNew.setWxOpenid(userInfo.getWx_openid());
        hybRegisterMapper.insertSelective(hybRegisterNew);
        // todo 创建资金账号
        Result result = balanceAction.createBalance(hybRegisterNew);
        if (result.getResultStatus() == CommonFinal.RESULT_CODE_SUCCESS) {
            logger.info("创建资金成功：" + hybRegisterNew.getPhoneno());
        } else {
            logger.info("创建资金失败" + hybRegisterNew.getPhoneno());
        }
        if(StringUtil.isNotEmpty(hybRegisterNew.getNickname())){
            hybRegisterNew.setNickname(EmojiConverter.getInstance().toUnicode(hybRegisterNew.getNickname()));
        }
        return ResultFactory.getSuccessResult("user", hybRegisterNew);

    }

    // 忘记密码，更新密码
    public Result userPasswordUpdate(UserInfo userInfo) {
        if (Strings.isNullOrEmpty(userInfo.getPhoneno())) {
            return ResultFactory.getErrorResult("手机号不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getLoginpassword())) {
            return ResultFactory.getErrorResult("请输入密码");
        }
        if (Strings.isNullOrEmpty(userInfo.getCode())) {
            return ResultFactory.getErrorResult("请输入验证码");
        }
        if (Strings.isNullOrEmpty(userInfo.getAppid())) // 默认hyb
        {
            return ResultFactory.getErrorResult("平台不能为空");
        }
        // 判断验证码
        String smsResult = UmsClient.getInstance().validateSmsCode(userInfo.getAppid(), userInfo.getPhoneno(), SmsBusinessCode.UpdateLoginPwd.getCode(), userInfo.getCode());
        Result<String> ob = ResultFactory.convertResult(smsResult);
        if (ob == null) {
            return ResultFactory.getErrorResult("ums 未启动");
        }
        if (ob.getResultStatus() == CommonFinal.RESULT_CODE_FAILURE) {
            return ob;
        }
        // 判断手机号是否已经存在
        HybRegister hybRegister = hybRegisterMapper.selectByPhoneKey(userInfo.getPhoneno(), userInfo.getAppid());
        if (hybRegister == null) {
            return ResultFactory.getErrorResult("用户未注册，无需找回密码");
        }
        hybRegister.setLoginpassword(userInfo.getLoginpassword());
        hybRegisterMapper.updateByPrimaryKeySelective(hybRegister);
        if(StringUtil.isNotEmpty(hybRegister.getNickname())){
            hybRegister.setNickname(EmojiConverter.getInstance().toUnicode(hybRegister.getNickname()));
        }
        return ResultFactory.getSuccessResult("user", hybRegister);
    }

    @Override
    public Result updateUserLoginPwdByOldPwd(UserInfo userInfo) {
        if (Strings.isNullOrEmpty(userInfo.getAppid())) // 默认hyb
        {
            return ResultFactory.getErrorResult("平台不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getPhoneno())) // 默认hyb
        {
            return ResultFactory.getErrorResult("手机号不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getLoginpassword())) // 默认hyb
        {
            return ResultFactory.getErrorResult("旧密码不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getNewpassword())) // 默认hyb
        {
            return ResultFactory.getErrorResult("新密码不能为空");
        }
        HybRegister hybRegister = hybRegisterMapper.selectByPhoneKey(userInfo.getPhoneno(), userInfo.getAppid());
        if (hybRegister == null) {
            return ResultFactory.getErrorResult("用户未注册");
        }
        if (hybRegister.getLoginpassword().equals(userInfo.getLoginpassword())) {
            hybRegister.setLoginpassword(userInfo.getNewpassword());
            int flag = hybRegisterMapper.updateByPrimaryKeySelective(hybRegister);
            if (flag > 0) {
                if(StringUtil.isNotEmpty(hybRegister.getNickname())){
                    hybRegister.setNickname(EmojiConverter.getInstance().toUnicode(hybRegister.getNickname()));
                }
                return ResultFactory.getSuccessResult("user", hybRegister);
            } else {
                ResultFactory.getErrorResult("修改失败，请重试");
            }
        }
        return ResultFactory.getErrorResult("密码不正确");
    }

    //登出
    public Result userLogout(String pkregister) {
        // TODO Auto-generated method stub
        return null;
    }


    //第三方授权登录
    public Result authorizeThirdLogin(ThirdLoginParam params) {
        // TODO Auto-generated method stub
        if (Strings.isNullOrEmpty(params.getOpenId())) {
            return ResultFactory.getErrorResult("用户ID未获取");
        }
        if (params.getLoginType() == null) {
            return ResultFactory.getErrorResult("用户授权类型不存在，请指定微信或者QQ");
        }

        if (Strings.isNullOrEmpty(params.getAppid())) // 默认hyb
        {
            return ResultFactory.getErrorResult("平台不能为空");
        }
        //判断是否已经绑定过了
        String wxOpenid = "";
        String qqOpenid = "";
        HybRegister register = new HybRegister();
        if (params.getLoginType() == 1) { //wx
            register = hybRegisterMapper.selectByWeixinKey(params.getOpenId(), params.getAppid());
            wxOpenid = params.getOpenId();
        } else if (params.getLoginType() == 2) { //qq
            register = hybRegisterMapper.selectByQQKey(params.getOpenId(), params.getAppid());
            qqOpenid = params.getOpenId();
        }
        if (Strings.isNullOrEmpty(params.getCpuid())) {
            return ResultFactory.getErrorResult("设备号不能为空");
        }
        if (Strings.isNullOrEmpty(params.getPushId())) {
            return ResultFactory.getErrorResult("推送设备不能为空");
        }
        if (register != null) {
            Result x = saveRegisterLogin(params, register);
            if (x != null) return x;
            return ResultFactory.getSuccessResult("user", register);
        } else {
            if (Strings.isNullOrEmpty(params.getPhoneno())) {
                return ResultFactory.getErrorResult(2001, "请先绑定手机号");
            }
            if(Strings.isNullOrEmpty(params.getCode())){
                return ResultFactory.getErrorResult("验证码不能为空");
            }
        }

//        HybRegister extHybRegister = hybRegisterMapper.selectByPhoneKey(params.getPhoneno(), params.getAppid());
//        if (extHybRegister != null) {
//            return ResultFactory.getErrorResult("你绑定的手机号已存在，请直接登录");
//        }
        Result<String> ob = ResultFactory.convertResult(UmsClient.getInstance().validateSmsCode(params.getAppid(), params.getPhoneno(), SmsBusinessCode.BindPhone.getCode(), params.getCode()));
        if (ob == null) {
            return ResultFactory.getErrorResult("ums 未启动");
        }
        if (ob.getResultStatus() == CommonFinal.RESULT_CODE_FAILURE) {
            return ob;
        }


        HybRegister hybRegister = hybRegisterMapper.selectByPhoneKey(params.getPhoneno(), params.getAppid());
        if (hybRegister == null) {
            register = new HybRegister();
            if (params.getLoginType() == 1) {
                register.setWxOpenid(wxOpenid);
            } else {
                register.setQqOpenid(qqOpenid);
            }
            register.setAppid(params.getAppid());
            register.setLoginpassword("88888888");
            register.setPhoneno(params.getPhoneno());
            register.setCpuid(params.getCpuid());
            register.setNickname(params.getNickName());
            register.setPosition(params.getHeadImgUrl());
            register.setPkregister(CommonUtil.get32UUID());
            register.setRegisterdate(String.valueOf(System.currentTimeMillis()));
            hybRegisterMapper.insertSelective(register);
            // todo 创建资金账号
            Result result = balanceAction.createBalance(register);
            if (result.getResultStatus() == CommonFinal.RESULT_CODE_SUCCESS) {
                logger.info("创建资金成功：" + register.getPhoneno());
            } else {
                logger.info("创建资金失败" + register.getPhoneno());
            }
        } else {
            if (params.getLoginType() == 1) {
                hybRegister.setWxOpenid(wxOpenid);
            } else
                hybRegister.setQqOpenid(qqOpenid);
            hybRegister.setNickname(params.getNickName());
            hybRegister.setPosition(params.getHeadImgUrl());
            hybRegisterMapper.updateByPrimaryKeySelective(hybRegister);
            register = hybRegister;
        }
        Result x = saveRegisterLogin(params, register);
        if (x != null) return x;


        return ResultFactory.getSuccessResult("user", register);
    }

    private Result saveRegisterLogin(ThirdLoginParam params, HybRegister register) {
        //直接登录
        //更新登录数据
        HybRegisterLogin hybRegisterLogin = hybRegisterLoginMapper.selectByPkregisterKey(register.getPkregister());
        if (hybRegisterLogin == null) {
            // add a record
            HybRegisterLogin hybRegisterLoginNew = new HybRegisterLogin();
            hybRegisterLoginNew.setAppid(params.getAppid());
            hybRegisterLoginNew.setCpuid(params.getCpuid());
            hybRegisterLoginNew.setLoginid(CommonUtil.get32UUID());
            hybRegisterLoginNew.setLogintime(new Date());
            hybRegisterLoginNew.setPkregister(register.getPkregister());
            hybRegisterLoginNew.setPushid(params.getPushId());
            try {
                hybRegisterLoginMapper.insertSelective(hybRegisterLoginNew);
            } catch (Exception e) {
                return ResultFactory.getErrorResult("app参数错误，指定参数为空");
            }
        } else {
            hybRegisterLogin.setPushid(params.getPushId());
            hybRegisterLogin.setLogintime(new Date());
            try {
                hybRegisterLoginMapper.updateByPrimaryKeySelective(hybRegisterLogin);
            } catch (Exception e) {
                return ResultFactory.getErrorResult("app参数提价错误，指定参数为空");
            }
        }
        return null;
    }

//    //第三方授权登录绑定手机号码
//    public Result authorizeThirdBindPhone(ThirdLoginParam params) {
//        // TODO Auto-generated method stub
//        if (Strings.isNullOrEmpty(params.getOpenId())) {
//            return ResultFactory.getErrorResult("用户ID未获取");
//        }
//        if (params.getLoginType() == null) {
//            return ResultFactory.getErrorResult("用户授权类型不存在，请指定微信或者QQ");
//        }
//
//        if (Strings.isNullOrEmpty(params.getAppid())) // 默认hyb
//        {
//            return ResultFactory.getErrorResult("平台不能为空");
//        }
//        if (Strings.isNullOrEmpty(params.getPhoneno())) {
//            return ResultFactory.getErrorResult("手机号不能为空");
//        }
//        HybRegister extHybRegister = hybRegisterMapper.selectByPhoneKey(params.getPhoneno(), params.getAppid());
//        if (extHybRegister != null) {
//            return ResultFactory.getErrorResult("你绑定的手机号已存在，请直接登录");
//        }
//        Result<String> ob = ResultFactory.convertResult(UmsClient.getInstance().validateSmsCode(params.getAppid(), params.getPhoneno(), SmsBusinessCode.BindPhone.getCode(), params.getCode()));
//        if (ob == null) {
//            return ResultFactory.getErrorResult("ums 未启动");
//        }
//        if (ob.getResultStatus() == CommonFinal.RESULT_CODE_FAILURE) {
//            return ob;
//        }
//        //判断
//        String wxOpenid = "";
//        String qqOpenid = "";
//        HybRegister register = new HybRegister();
//        if (params.getLoginType() == 1) { //wx
//            register = hybRegisterMapper.selectByWeixinKey(params.getOpenId(), params.getAppid());
//            wxOpenid = params.getOpenId();
//        } else if (params.getLoginType() == 2) { //qq
//            register = hybRegisterMapper.selectByQQKey(params.getOpenId(), params.getAppid());
//            qqOpenid = params.getOpenId();
//        }
//        if (register == null) {
//            HybRegister hybRegister = hybRegisterMapper.selectByPhoneKey(params.getPhoneno(), params.getAppid());
//            if (hybRegister == null) {
//                if (params.getLoginType() == 1) {
//                    register.setWxOpenid(wxOpenid);
//                } else {
//                    register.setQqOpenid(qqOpenid);
//                }
//                register.setAppid(params.getAppid());
//                register.setLoginpassword("88888888");
//                register.setPhoneno(params.getPhoneno());
//                register.setPkregister(CommonUtil.get32UUID());
//                hybRegisterMapper.insertSelective(register);
//                // todo 创建资金账号
//               Result result = balanceAction.createBalance(register);
//                if(result.getResultStatus() == CommonFinal.RESULT_CODE_SUCCESS){
//                    logger.info("创建资金成功：" + hybRegister.getPhoneno());
//                }else{
//                    logger.info("创建资金失败"+ hybRegister.getPhoneno());
//                }
//            } else {
//                if (params.getLoginType() == 1) {
//                    hybRegister.setWxOpenid(wxOpenid);
//                } else
//                    hybRegister.setQqOpenid(qqOpenid);
//                hybRegisterMapper.updateByPrimaryKeySelective(hybRegister);
//                register = hybRegister;
//            }
//        } else {
//            register.setPhoneno(params.getPhoneno());
//            hybRegisterMapper.updateByPrimaryKeySelective(register);
//            //更新用户手机信息
//        }
//        //直接登录
//        //更新登录数据
//        HybRegisterLogin hybRegisterLogin = hybRegisterLoginMapper.selectByPkregisterKey(register.getPkregister());
//        if (hybRegisterLogin == null) {
//            // add a record
//            HybRegisterLogin hybRegisterLoginNew = new HybRegisterLogin();
//            hybRegisterLoginNew.setAppid(params.getAppid());
//            hybRegisterLoginNew.setCpuid(params.getCpuid());
//            hybRegisterLoginNew.setLoginid(CommonUtil.get32UUID());
//            hybRegisterLoginNew.setLogintime(new Date());
//            hybRegisterLoginNew.setPkregister(register.getPkregister());
//            hybRegisterLoginNew.setPushid(params.getPushId());
//            try {
//                hybRegisterLoginMapper.insertSelective(hybRegisterLoginNew);
//            } catch (Exception e) {
//                return ResultFactory.getErrorResult("app参数错误，指定参数为空");
//            }
//        } else {
//            hybRegisterLogin.setPushid(params.getPushId());
//            hybRegisterLogin.setLogintime(new Date());
//            try {
//                hybRegisterLoginMapper.updateByPrimaryKeySelective(hybRegisterLogin);
//            } catch (Exception e) {
//                return ResultFactory.getErrorResult("app参数提价错误，指定参数为空");
//            }
//        }
//        return ResultFactory.getSuccessResult("user", register);
//    }


    @Override
    public Result updateUserPayPassword(UserInfo userInfo) {
        if (Strings.isNullOrEmpty(userInfo.getAppid())) // 默认hyb
        {
            return ResultFactory.getErrorResult("平台不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getPhoneno())) {
            return ResultFactory.getErrorResult("手机号不能为空");
        }
        HybRegister hybRegister = hybRegisterMapper.selectByPhoneKey(userInfo.getPhoneno(), userInfo.getAppid());
        if (hybRegister == null) {
            return ResultFactory.getErrorResult("用户未注册，请先注册");
        }
        if (Strings.isEmpty(hybRegister.getPaypassword())) {
            if (Strings.isNullOrEmpty(userInfo.getPaypassword())) {
                return ResultFactory.getErrorResult("支付密码不能为空");
            }
        }
        if (Strings.isNullOrEmpty(userInfo.getPaypassword())) {
            return ResultFactory.getErrorResult("支付密码不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getNewpaypassword())) {
            return ResultFactory.getErrorResult("新设置的支付密码不能为空");
        }
        if (Strings.isEmpty(hybRegister.getPaypassword()) || hybRegister.getPaypassword().equals(userInfo.getPaypassword())) {
            HybRegister hybRegister1 = new HybRegister();
            hybRegister1.setPkregister(hybRegister.getPkregister());
            hybRegister1.setPaypassword(userInfo.getNewpaypassword());
            hybRegister.setPaypassword(userInfo.getNewpaypassword());
            hybRegisterMapper.updateByPrimaryKeySelective(hybRegister1);
            return ResultFactory.getSuccessResult("user", hybRegister);
        }
        return ResultFactory.getErrorResult("修改失败，请重试");
    }

    @Override
    public Result updatePhonenoBySmscode(UserInfo userInfo) {
        if (Strings.isNullOrEmpty(userInfo.getAppid())) // 默认hyb
        {
            return ResultFactory.getErrorResult("平台不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getPhoneno())) {
            return ResultFactory.getErrorResult("手机号不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getNewphoneno())) {
            return ResultFactory.getErrorResult("新手机号不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getCode())) {
            return ResultFactory.getErrorResult("验证码不能为空");
        }
        // 判断验证码
        Result ob = ResultFactory.convertResult(UmsClient.getInstance().validateSmsCode(userInfo.getAppid(), userInfo.getNewphoneno(), SmsBusinessCode.BindPhone.getCode(), userInfo.getCode()));
        if (ob == null) {
            return ResultFactory.getErrorResult("ums 未启动");
        }
        if (ob.getResultStatus() == CommonFinal.RESULT_CODE_FAILURE) {
            return ob;
        }
        HybRegister hybRegister = hybRegisterMapper.selectByPhoneKey(userInfo.getPhoneno(), userInfo.getAppid());
        HybRegister hybRegister1 = hybRegisterMapper.selectByPhoneKey(userInfo.getNewphoneno(), userInfo.getAppid());
        if (hybRegister == null) {
            return ResultFactory.getErrorResult("用户未注册，请先注册");
        }
        if (hybRegister1 != null) {
            return ResultFactory.getErrorResult("此手机号已注册");
        }
//        hybRegister.setPhoneno(userInfo.getNewphoneno());
        logger.info("用户: " + hybRegister.getPkregister() + " 原手机号：" + hybRegister.getPhoneno() + " 修改为：" + userInfo.getNewphoneno());
        HybRegister register = new HybRegister();
        register.setPhoneno(userInfo.getNewphoneno());
        register.setPkregister(hybRegister.getPkregister());
        int flag = hybRegisterMapper.updateByPrimaryKeySelective(register);
        if (flag > 0) {
            hybRegister.setPhoneno(userInfo.getNewphoneno());
            return ResultFactory.getSuccessResult("user", hybRegister);
        }
        return ResultFactory.getErrorResult("修改失败，请重试");
    }

    @Override
    public Result updateUserInfo(UserInfo userInfo) {
        if (Strings.isEmpty(userInfo.getPkregister())) {
            return ResultFactory.getErrorResult("用户主键不能为空");
        }
        HybRegister hybRegister = hybRegisterMapper.selectByPrimaryKey(userInfo.getPkregister());
        if (hybRegister == null) {
            return ResultFactory.getErrorResult("用户不存在，请先注册");
        }
        if (Strings.isNotNullOrEmpty(userInfo.getNickname())) {
            hybRegister.setNickname(userInfo.getNickname());
        }
        if (Strings.isNotNullOrEmpty(userInfo.getEmail())) {
            hybRegister.setEmail(userInfo.getEmail());
        }
        if (Strings.isNotNullOrEmpty(userInfo.getHeadimg())) {
            hybRegister.setPosition(userInfo.getHeadimg());
        }
        int flag = hybRegisterMapper.updateByPrimaryKeySelective(hybRegister);
        if (flag > 0) {
            return ResultFactory.getSuccessResult("user", hybRegister);
        } else {
            return ResultFactory.getErrorResult("修改失败，请重试");
        }
    }

    @Override
    public Result forgetPayPwd(UserInfo userInfo) {
        if (Strings.isNullOrEmpty(userInfo.getAppid())) // 默认hyb
        {
            return ResultFactory.getErrorResult("平台不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getPhoneno())) {
            return ResultFactory.getErrorResult("手机号不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getCode())) {
            return ResultFactory.getErrorResult("验证码不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getPaypassword())) {
            return ResultFactory.getErrorResult("支付密码不能为空");
        }
        // 判断验证码
        Result ob = ResultFactory.convertResult(UmsClient.getInstance().validateSmsCode(userInfo.getAppid(), userInfo.getPhoneno(), SmsBusinessCode.UpdatePayPwd.getCode(), userInfo.getCode()));
        if (ob == null) {
            return ResultFactory.getErrorResult("ums 未启动");
        }
        if (ob.getResultStatus() == CommonFinal.RESULT_CODE_FAILURE) {
            return ob;
        }
        HybRegister hybRegister = hybRegisterMapper.selectByPhoneKey(userInfo.getPhoneno(), userInfo.getAppid());
        if (hybRegister == null) {
            return ResultFactory.getErrorResult("用户未注册，请先注册");
        }
        HybRegister hybRegister1 = new HybRegister();
        hybRegister1.setPkregister(hybRegister.getPkregister());
        hybRegister1.setPaypassword(userInfo.getPaypassword());
        int flag = hybRegisterMapper.updateByPrimaryKeySelective(hybRegister1);
        if (flag > 0) {
            return ResultFactory.getSuccessResult("user", hybRegister);
        } else {
            return ResultFactory.getErrorResult("修改失败，请重试");
        }
    }

    @Transactional(readOnly = false, propagation= Propagation.REQUIRES_NEW)
    public void checkWxFans(HybWxUser params) {
        logger.info("checkfans:  openid=" + params.getOpenid() );
        logger.info("checkfans:  unionid=" + params.getUnionid() );
        logger.info("checkfans:  appid=" + params.getAppid() );
      HybRegister hybRegister =  hybRegisterMapper.selectByWeixinKey(params.getUnionid(),params.getAppid());
      if(hybRegister == null){
          hybRegister = hybRegisterMapper.selectByWeixinKey(params.getOpenid(), params.getAppid());
      }
      logger.info("用户是否存在.." + (hybRegister != null));
        HybWxFan hybWxFan =  hybWxFanMapper.selectByUnionid(params.getUnionid());
        if(hybWxFan == null){
            hybWxFan = hybWxFanMapper.selectByOpenId(params.getOpenid());
        }
        logger.info("用户fans是否存在.." + (hybWxFan != null));
        if (hybWxFan == null) {
            //创建wxfan
            hybWxFan = new HybWxFan();
            hybWxFan.setOpenid(params.getOpenid());
            hybWxFan.setUnionid(params.getUnionid());
            hybWxFan.setAccessToken(params.getAccessToken());
            if (StringUtils.isNotEmpty(params.getAppid())) {
                hybWxFan.setAppid(params.getAppid());
            } else {
                hybWxFan.setAppid("hyb");
            }
            if (hybRegister != null) {
                hybWxFan.setPkregister(hybRegister.getPkregister());
                hybWxFan.setNickname(hybRegister.getNickname());
                hybWxFan.setHeadimgurl(hybRegister.getPosition());
            }
            hybWxFan.setCreateAt(new Date());
            logger.info("用户appid：" + hybWxFan.getAppid());
            hybWxFanMapper.insertSelective(hybWxFan);
            logger.info("用户fans 重新创建");
        } else if (hybWxFan != null && StringUtils.isNotEmpty(hybWxFan.getPkregister())) {
            HybWxFan hybWxFanUpdate = new HybWxFan();
            hybWxFanUpdate.setPkregister(hybRegister.getPkregister());
            hybWxFanUpdate.setPkfansid(hybWxFan.getPkfansid());
            hybWxFanUpdate.setNickname(hybWxFan.getNickname());
            hybWxFanMapper.updateByPrimaryKeySelective(hybWxFanUpdate);
        }
    }
    @Transactional(readOnly = false, propagation= Propagation.REQUIRES_NEW)
    @Override
    public Result userRegisterWithNoSMS(UserInfo userInfo) {
        if (Strings.isNullOrEmpty(userInfo.getPhoneno())) {
            return ResultFactory.getErrorResult("手机号不能为空");
        }
        if (Strings.isNullOrEmpty(userInfo.getLoginpassword())) {
            return ResultFactory.getErrorResult("请输入密码");
        }

        if (Strings.isNullOrEmpty(userInfo.getAppid())) // 默认hyb
        {
            return ResultFactory.getErrorResult("平台不能为空");
        }

//        if(StringUtils.isNotEmpty(userInfo.getWx_openid())){
//           HybRegister hybRegister2 = hybRegisterMapper.selectByWeixinKey(userInfo.getWx_openid(), userInfo.getAppid());
//           if(hybRegister2 != null && !hybRegister2.getPhoneno().equals(userInfo.getPhoneno())){
//               return ResultFactory.getErrorResult("该微信已经绑定手机号");
//           }
//        }
        // 判断手机号是否已经存在
        HybRegister hybRegister = hybRegisterMapper.selectByPhoneKey(userInfo.getPhoneno(), userInfo.getAppid());
        if (hybRegister != null) {
            if(StringUtil.isEmpty(hybRegister.getWxOpenid()) && StringUtil.isNotEmpty(userInfo.getWx_openid())){
                HybRegister updateHybRegister = new HybRegister();
                updateHybRegister.setPkregister(hybRegister.getPkregister());
                updateHybRegister.setWxOpenid(userInfo.getWx_openid());
                hybRegisterMapper.updateByPrimaryKeySelective(updateHybRegister);
            }
            logger.info("手机号已经存在，请您登录，如果忘记密码，请选择找回密码");
            return ResultFactory.getErrorResult(-2,"手机号已经存在，请您登录，如果忘记密码，请选择找回密码");
        }
        HybRegister hybRegisterNew = new HybRegister();
        hybRegisterNew.setAppid(userInfo.getAppid());
        hybRegisterNew.setLoginpassword(userInfo.getLoginpassword());
        hybRegisterNew.setPhoneno(userInfo.getPhoneno());
        hybRegisterNew.setPkregister(CommonUtil.get32UUID());
        hybRegisterNew.setRegisterdate(String.valueOf(System.currentTimeMillis()));
        hybRegisterNew.setNickname(EmojiConverter.getInstance().toAlias(userInfo.getNickname()));
        hybRegisterNew.setWxOpenid(userInfo.getWx_openid());
        hybRegisterMapper.insertSelective(hybRegisterNew);
        // todo 创建资金账号
        Result result = balanceAction.createBalance(hybRegisterNew);
        if (result.getResultStatus() == CommonFinal.RESULT_CODE_SUCCESS) {
            logger.info("创建资金成功：" + hybRegisterNew.getPhoneno());
        } else {
            logger.info("创建资金失败" + hybRegisterNew.getPhoneno());
        }
        if(StringUtil.isNotEmpty(hybRegisterNew.getNickname())){
            hybRegisterNew.setNickname(EmojiConverter.getInstance().toUnicode(hybRegisterNew.getNickname()));
        }
        return ResultFactory.getSuccessResult("user", hybRegisterNew);
    }

    @Transactional(readOnly = false, propagation= Propagation.REQUIRES_NEW)
    public void checkUnionId(HybWxUser params) {
        logger.info("checkunionid:  openid=" + params.getOpenid() );
        logger.info("checkunionid:  unionid=" + params.getUnionid() );
        logger.info("checkunionid:  appid=" + params.getAppid() );
        HybRegister hybRegister =  hybRegisterMapper.selectByWeixinKey(params.getUnionid(),params.getAppid());
        logger.info("checkunionid 用户是否存在.." + (hybRegister != null));
        if(hybRegister == null){
            hybRegister = hybRegisterMapper.selectByWeixinKey(params.getOpenid(), params.getAppid());
        }
        if(hybRegister == null){
            //修复当wxfans不为空的时候 存在pkregister， 并且hyb_register 中的wx_openid 为空。两条数据未关联上
            HybWxFan hybWxFan =  hybWxFanMapper.selectByUnionid(params.getUnionid());
            if(hybWxFan == null){
                hybWxFan = hybWxFanMapper.selectByOpenId(params.getOpenid());
            }
            if(hybWxFan != null && StringUtils.isNotEmpty(hybWxFan.getPkregister())){
                hybRegister = hybRegisterMapper.selectByPrimaryKey(hybWxFan.getPkregister());
                if(hybRegister!= null && StringUtils.isEmpty(hybRegister.getWxOpenid())){
                    HybRegister updateHybRegister = new HybRegister();
                    updateHybRegister.setWxOpenid(params.getUnionid());
                    updateHybRegister.setPkregister(hybWxFan.getPkregister());
                    hybRegisterMapper.updateByPrimaryKeySelective(updateHybRegister);
                }
            }
        }
        if(hybRegister == null){
            logger.info("用户是否存在.." + (hybRegister != null));
            return;
        }
        params.setPkregister(hybRegister.getPkregister());
        if(StringUtils.isEmpty(params.getPkregister())){
            logger.info("用户是否存在.." + (hybRegister != null));
            return;
        }
        logger.info("用户是否存在.." + (hybRegister != null) + " pkregister:" + hybRegister.getPkregister());
        int flag = hybWxFanMapper.updateUnionidByPkregister(params);
        logger.info("更新结果："+ flag);
    }
}
