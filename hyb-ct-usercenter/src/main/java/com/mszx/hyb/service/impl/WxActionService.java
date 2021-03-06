package com.mszx.hyb.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.github.binarywang.java.emoji.EmojiConverter;
import com.github.pagehelper.StringUtil;
import com.mszx.hyb.common.exception.HybCtException;
import com.mszx.hyb.common.model.CommonFinal;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.common.util.CommonUtil;
import com.mszx.hyb.common.util.ResultFactory;
import com.mszx.hyb.common.util.Strings;
import com.mszx.hyb.component.RedisLock;
import com.mszx.hyb.dao.HybIntegralRecordMapper;
import com.mszx.hyb.dao.HybRegisterMapper;
import com.mszx.hyb.dao.HybWxFanMapper;
import com.mszx.hyb.entity.HybIntegralRecord;
import com.mszx.hyb.entity.HybRegister;
import com.mszx.hyb.entity.HybWxFan;
import com.mszx.hyb.entity.HybWxUser;
import com.mszx.hyb.param.UserInfo;
import com.mszx.hyb.sdk.param.AppId;
import com.mszx.hyb.service.IWxAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WxActionService implements IWxAction {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HybWxFanMapper hybWxFanMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    HybRegisterMapper hybRegisterMapper;
    @Autowired
    private HybIntegralRecordMapper hybIntegralRecordMapper;

    @Autowired
    private UserActionService userActionService;


    @Value("${redis.lock.prename}")
    private String lockPrename;

    @Override
    public Result saveFans(HybWxFan wxFan) {
        logger.info("saveFans: " + JSON.toJSONString(wxFan));
        if (StringUtils.isEmpty(wxFan.getAppid())) {
            return ResultFactory.getErrorResult("??????????????????");
        }
        if (StringUtils.isEmpty(wxFan.getOpenid())) {
            return ResultFactory.getErrorResult("openid ????????????");
        }
        Result searchResult = findByFans(wxFan);
        logger.info("findByFans:" + JSON.toJSONString(searchResult));
        if (searchResult.getResultStatus() == CommonFinal.RESULT_CODE_SUCCESS) {
            HybWxFan hybWxFan = (HybWxFan) searchResult.getResultData();
            logger.info("hybwxFan:" + JSON.toJSONString(hybWxFan));
            if (hybWxFan != null) {
                if (StringUtil.isNotEmpty(wxFan.getNickname())) {
                    wxFan.setNickname(EmojiConverter.getInstance().toAlias(wxFan.getNickname()));
                }
                int flag = hybWxFanMapper.updateByPrimaryKeySelective(wxFan);
                logger.info("hybwxFan: -->" + flag);
                if (flag > 0) {
                    return ResultFactory.getSuccessResult(hybWxFan);
                } else {
                    return ResultFactory.getErrorResult("????????????????????????");
                }
            }
        }
        wxFan.setCreateAt(new Date());
        logger.info("wxFan :{}", wxFan);
        int flag = hybWxFanMapper.insertSelective(wxFan);
        if (flag > 0) {
            return ResultFactory.getSuccessResult(wxFan);
        }
        return ResultFactory.getErrorResult("????????????????????????");
    }

    /**
     * ??????openid pkfansid unionid????????????
     *
     * @param wxFanParams
     * @return
     */
    @Override
    public Result findByFans(HybWxFan wxFanParams) {
        HybWxFan wxFan = new HybWxFan();
        if (StringUtils.isEmpty(wxFanParams.getOpenid()) && wxFanParams.getPkfansid() == null) {
            return ResultFactory.getErrorResult("openid ??? pkfansid ??????????????????");
        }
        if (!StringUtils.isEmpty(wxFanParams.getUnionid())) {
            wxFan = hybWxFanMapper.selectByUnionid(wxFanParams.getUnionid());
            if (wxFan != null) {
                if (StringUtil.isNotEmpty(wxFan.getNickname())) {
                    wxFan.setNickname(EmojiConverter.getInstance().toUnicode(wxFan.getNickname()));
                }
                return ResultFactory.getSuccessResult(wxFan);
            }
        }
        if (wxFanParams.getPkfansid() != null) {
            wxFan = hybWxFanMapper.selectByPrimaryKey(wxFanParams.getPkfansid());
            if (wxFan != null) {
                wxFan.setNickname(EmojiConverter.getInstance().toUnicode(wxFan.getNickname()));
                return ResultFactory.getSuccessResult(wxFan);
            }
        }
        logger.info("search fans:" + wxFanParams.getOpenid());
        if (!StringUtils.isEmpty(wxFanParams.getOpenid())) {
            wxFan = hybWxFanMapper.selectByOpenId(wxFanParams.getOpenid());
            if (wxFan != null) {
                if (StringUtil.isNotEmpty(wxFan.getNickname())) {
                    wxFan.setNickname(EmojiConverter.getInstance().toUnicode(wxFan.getNickname()));
                }
                return ResultFactory.getSuccessResult(wxFan);
            }
        }

        return ResultFactory.getErrorResult("?????????????????????");
    }

    @Override
    public Result updateIntegral(HybIntegralRecord hybIntegralRecord) {

        if (hybIntegralRecord.getFansid() == null) {
            return ResultFactory.getErrorResult("??????????????????");
        }
        if (hybIntegralRecord.getOrdermoney() == null || hybIntegralRecord.getOrdermoney().compareTo(new BigDecimal("0")) < 0) {
            return ResultFactory.getErrorResult("???????????????");
        }
        if (Strings.isNullOrEmpty(hybIntegralRecord.getOrdersubject())) {
            return ResultFactory.getErrorResult("??????????????????");
        }
        RedisLock lock = new RedisLock(redisTemplate, lockPrename + "lock_integral_update" + hybIntegralRecord.getFansid());
        try {
            if (lock.lock()) {//??????
                HybWxFan hybWxFan = hybWxFanMapper.selectByPrimaryKey(hybIntegralRecord.getFansid());
                //????????????????????????
                if (hybWxFan == null) {
                    return ResultFactory.getErrorResult("???????????????");
                }
                //????????????
                hybIntegralRecord.setBeforeBalance(hybWxFan.getTotal_integral());
                if (hybIntegralRecord.getIsIncrease() == null || hybIntegralRecord.getIsIncrease() == HybIntegralRecord.IncreaseType.Add.getValue()) {
                    hybWxFan.setTotal_integral(hybWxFan.getTotal_integral().add(hybIntegralRecord.getOrdermoney()));
                } else if (hybIntegralRecord.getIsIncrease() == HybIntegralRecord.IncreaseType.Reduce.getValue()) {//??????????????????
                    if (hybWxFan.getTotal_integral().compareTo(hybIntegralRecord.getOrdermoney()) < 0) {
                        return ResultFactory.getErrorResult("???????????????????????????");
                    }
                    hybWxFan.setTotal_integral(hybWxFan.getTotal_integral().subtract(hybIntegralRecord.getOrdermoney()));
                }
                hybIntegralRecord.setAfterBalance(hybWxFan.getTotal_integral());
                logger.info("??????????????????????????? fansid=" + hybWxFan.getPkfansid() + "  ???????????????=" + hybWxFan.getTotal_integral() + " ??????????????????" + hybIntegralRecord.getAfterBalance() + " ?????????" + hybIntegralRecord.getOrdersubject());
                //??????????????????
                int flag = hybWxFanMapper.updateIntegralById(hybWxFan);
                if (flag == 1) {
                    hybIntegralRecordMapper.insertSelective(hybIntegralRecord);
                }
                Map<String, Object> maps = new HashMap<>();
                maps.put("fansid", hybWxFan.getPkfansid());
                maps.put("integral", hybWxFan.getTotal_integral());
                return ResultFactory.getSuccessResult(maps);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return ResultFactory.getErrorResult("?????????????????????????????????");
    }

    @Override
    public Result updateContact(HybWxFan wxFan) {
        if (wxFan.getPkfansid() == null) {
            return ResultFactory.getErrorResult("??????????????????");
        }
        if (Strings.isNullOrEmpty(wxFan.getContact_name())) {
            return ResultFactory.getErrorResult("???????????????????????????");
        }
        if (Strings.isNullOrEmpty(wxFan.getContact_cellphone())) {
            return ResultFactory.getErrorResult("???????????????????????????");
        }
        HybWxFan hybWxFan = new HybWxFan();
        hybWxFan.setPkfansid(wxFan.getPkfansid());
        hybWxFan.setContact_cellphone(wxFan.getContact_cellphone());
        hybWxFan.setContact_name(EmojiConverter.getInstance().toAlias(wxFan.getContact_name()));
        hybWxFanMapper.updateByPrimaryKeySelective(wxFan);
        return ResultFactory.getSuccessResult("????????????");
    }

    @Override
    public Result findUserByOpenId(HybWxUser params) {
        if (StringUtils.isEmpty(params.getOpenid())) {
            return ResultFactory.getErrorResult("openid ????????????");
        }
        if (StringUtils.isEmpty(params.getAppid())) {
            params.setAppid(AppId.Hyb.getCode());
        }
        //??????pkregisetr ???hyb_wx_fans ???????????????unionid??????
        userActionService.checkUnionId(params);
        //??????unionid ?????????hyb?????????hyb_wx_fans
        userActionService.checkWxFans(params);

        HybWxUser hybWxUser = hybWxFanMapper.selectWxUserByOpenid(params);
        if (hybWxUser == null) {
            return ResultFactory.getErrorResult("?????????");
        }
        if (Strings.isNotNullOrEmpty(params.getAccessToken())) {
            hybWxUser.setAccessToken(params.getAccessToken());
            HybWxFan hybWxFanUpdate = new HybWxFan();
            hybWxFanUpdate.setOpenid(params.getOpenid());
            hybWxFanUpdate.setAccessToken(params.getAccessToken());
            logger.info("appid: " + hybWxFanUpdate.getAppid());
            hybWxFanMapper.updateByPrimaryKeySelective(hybWxFanUpdate);
        }
        if (StringUtil.isNotEmpty(hybWxUser.getNickname())) {
            hybWxUser.setNickname(EmojiConverter.getInstance().toUnicode(hybWxUser.getNickname()));
        }
        return ResultFactory.getSuccessResult(hybWxUser);
    }

    @Transactional(readOnly = false, rollbackFor = {HybCtException.class})
    @Override
    public Result wxRegister(HybWxUser hybWxUser) throws HybCtException {
        if (StringUtils.isEmpty(hybWxUser.getOpenid())) {
            return ResultFactory.getErrorResult("openid ????????????");
        }
        if (StringUtils.isEmpty(hybWxUser.getAppid())) {
            hybWxUser.setAppid(AppId.Hyb.getCode());
        }
        HybWxFan hybWxFan = new HybWxFan();
        BeanUtils.copyProperties(hybWxUser, hybWxFan);
        hybWxFan.setNickname(hybWxUser.getNickname());

        /*********************?????????register**************************************/
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(hybWxUser, userInfo);
        userInfo.setLoginpassword(CommonUtil.get32UUID());
        userInfo.setWx_openid(hybWxUser.getUnionid());
        Result userResult = userActionService.userRegister(userInfo);
        HybRegister hybRegister;
        logger.info("hyb register result: " + JSON.toJSONString(userResult));
        if (userResult.getResultStatus() == CommonFinal.RESULT_CODE_SUCCESS) {
            hybRegister = ((HashMap<String, HybRegister>) userResult.getResultData()).get("user");
        } else {
            if (userResult.getResultStatus() == -2) {//??????????????????????????????hyb??????
                Result tempResult = userActionService.getUserInfoByPhone(hybWxUser.getPhoneno(), hybWxUser.getAppid());
                hybRegister = ((HashMap<String, HybRegister>) tempResult.getResultData()).get("user");
            } else {
                return userResult;
            }
        }
        /***************************??????fans**********************************/
        if (hybRegister != null) {
            hybWxUser.setPkregister(hybRegister.getPkregister());
            hybWxFan.setPkregister(hybRegister.getPkregister());
        }
        Result result = saveFans(hybWxFan);
        if (result.getResultStatus() == CommonFinal.RESULT_CODE_SUCCESS) {
            HybWxFan hybWxFanTemp = ((HybWxFan) result.getResultData());
            hybWxFan.setPkfansid(hybWxFanTemp.getPkfansid());
            //????????????unionid ??????????????????????????????
            if (hybWxFanTemp.getUnionid() != null && hybWxFanTemp.getUnionid().equals(hybWxUser.getUnionid())) {
                HybRegister hybRegisterUpdateUnion = new HybRegister();
                hybRegisterUpdateUnion.setPkregister(hybRegister.getPkregister());
                hybRegisterUpdateUnion.setWxOpenid(hybWxFanTemp.getUnionid());
                hybRegisterMapper.updateByPrimaryKeySelective(hybRegisterUpdateUnion);
            }
            return ResultFactory.getSuccessResult(hybWxUser);
        } else {
            return result;
        }
    }

    @Override
    public Result wxRegisterWithNoSMS(HybWxUser hybWxUser) {
        if (StringUtils.isEmpty(hybWxUser.getOpenid())) {
            return ResultFactory.getErrorResult("openid ????????????");
        }
        if (StringUtils.isEmpty(hybWxUser.getAppid())) {
            hybWxUser.setAppid(AppId.Hyb.getCode());
        }
        HybWxFan hybWxFan = new HybWxFan();
        BeanUtils.copyProperties(hybWxUser, hybWxFan);
        hybWxFan.setNickname(hybWxUser.getNickname());

        /*********************?????????register**************************************/
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(hybWxUser, userInfo);
        userInfo.setLoginpassword(CommonUtil.get32UUID());
        userInfo.setWx_openid(hybWxUser.getUnionid());
        Result userResult = userActionService.userRegisterWithNoSMS(userInfo);
        HybRegister hybRegister;
        logger.info("hyb register result: " + JSON.toJSONString(userResult));
        if (userResult.getResultStatus() == CommonFinal.RESULT_CODE_SUCCESS) {
            hybRegister = ((HashMap<String, HybRegister>) userResult.getResultData()).get("user");
        } else {
            if (userResult.getResultStatus() == -2) {//??????????????????????????????hyb??????
                Result tempResult = userActionService.getUserInfoByPhone(hybWxUser.getPhoneno(), hybWxUser.getAppid());
                hybRegister = ((HashMap<String, HybRegister>) tempResult.getResultData()).get("user");
            } else {
                return userResult;
            }
        }
        /***************************??????fans**********************************/
        if (hybRegister != null) {
            hybWxUser.setPkregister(hybRegister.getPkregister());
            hybWxFan.setPkregister(hybRegister.getPkregister());
        }
        Result result = saveFans(hybWxFan);
        if (result.getResultStatus() == CommonFinal.RESULT_CODE_SUCCESS) {
            HybWxFan hybWxFanTemp = ((HybWxFan) result.getResultData());
            hybWxFan.setPkfansid(hybWxFanTemp.getPkfansid());
            //????????????unionid ??????????????????????????????
            if (hybWxFanTemp.getUnionid() != null && hybWxFanTemp.getUnionid().equals(hybWxUser.getUnionid())) {
                HybRegister hybRegisterUpdateUnion = new HybRegister();
                hybRegisterUpdateUnion.setPkregister(hybRegister.getPkregister());
                hybRegisterUpdateUnion.setWxOpenid(hybWxFanTemp.getUnionid());
                hybRegisterMapper.updateByPrimaryKeySelective(hybRegisterUpdateUnion);
            }
            return ResultFactory.getSuccessResult(hybWxUser);
        } else {
            return result;
        }
    }


}
