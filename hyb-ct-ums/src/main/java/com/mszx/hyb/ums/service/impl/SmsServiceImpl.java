package com.mszx.hyb.ums.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.StringUtil;
import com.mszx.hyb.common.model.CommonFinal;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.common.util.ResultFactory;
import com.mszx.hyb.common.util.Strings;
import com.mszx.hyb.ums.dao.HybPlatformSmsMapper;
import com.mszx.hyb.ums.entity.PlatformSms;
import com.mszx.hyb.ums.entity.PlatformSmsTemplate;
import com.mszx.hyb.ums.entity.cache.CachePhoneSms;
import com.mszx.hyb.ums.param.SmsParam;
import com.mszx.hyb.ums.service.SmsService;
import com.mszx.hyb.ums.util.RedisLock;
import com.mszx.hyb.ums.util.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {

    protected static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);


    @Autowired
    private HybPlatformSmsMapper hybPlatformSmsMapper;


    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Result validateSmsCode(SmsParam smsParam) {
        if (Strings.isEmpty(smsParam.getPhoneno())) {
            return ResultFactory.getErrorResult("手机号不能为空");
        }
        if (Strings.isEmpty(smsParam.getBusiness_code())) {
            return ResultFactory.getErrorResult("业务类型不能为空");
        }
        if (Strings.isEmpty(smsParam.getCode())) {
            return ResultFactory.getErrorResult("验证码不能为空");
        }
        String sms_cache_key = getSmsCacheKey(smsParam);
        String sms_cache_key_express = getSmsCacheExpress(smsParam);
        logger.info("validatesms key:" + sms_cache_key + "  express_key:" + sms_cache_key_express);
        CachePhoneSms cachePhoneSms = (CachePhoneSms) redisTemplate.opsForValue().get(sms_cache_key);
        if (cachePhoneSms == null) {
            logger.info("phoneon:" + smsParam.getPhoneno() +"  code="+ smsParam.getCode());
            return ResultFactory.getErrorResult("验证码已失效");
        }
        if (cachePhoneSms.getSms_code().equals(smsParam.getCode())) {
//            if (smsParam.getIs_invalid() != null && smsParam.getIs_invalid() == 1) {
//                logger.info(sms_cache_key + " 已被清理");
//                redisTemplate.delete(sms_cache_key);
//                redisTemplate.delete(sms_cache_key_express);
//            }
            return ResultFactory.getSuccessResult("验证通过");
        }
        return ResultFactory.getErrorResult("验证码错误");
    }

    @Override
    public Result smsCode(SmsParam smsParam) {
        //验证手机号，参数正确性
        if (Strings.isEmpty(smsParam.getPhoneno())) {
            return ResultFactory.getErrorResult("手机号不能为空");
        }
        if (!SmsUtil.isMobile(smsParam.getPhoneno())) {
            return ResultFactory.getErrorResult("请填写正确的手机号码");
        }
        if (Strings.isEmpty(smsParam.getTemplate_code())) {
            return ResultFactory.getErrorResult("短信模版编码不能为空");
        }
        if (Strings.isEmpty(smsParam.getBusiness_code())) {
            ResultFactory.getErrorResult("业务类型不能为空");
        }
        //验证模版是否存在
        PlatformSmsTemplate platformSmsTemplate = hybPlatformSmsMapper.findPlatformSmsTemplateByCode(smsParam.getTemplate_code());
        if (platformSmsTemplate == null || platformSmsTemplate.getStatus() != 0) {
            return ResultFactory.getErrorResult("短信模版不存在或已停用，请核对后重试");
        }
        //验证该业务类型是否已经发送过短信并在此重复发送间隔,如果在则不能发送
        String sms_cache_key = getSmsCacheKey(smsParam);
        String sms_cache_key_express = getSmsCacheExpress(smsParam);
        RedisLock lock = new RedisLock(redisTemplate, "lock_ums_sendcode" + sms_cache_key);
        try {
            if (StringUtil.isEmpty(lock.get(lock.getLockKey())) && lock.lock()) {//加锁
                CachePhoneSms cachePhoneSms = (CachePhoneSms) redisTemplate.opsForValue().get(sms_cache_key);
                if (cachePhoneSms == null) {
                    return sendCacheMessage(platformSmsTemplate, smsParam, sms_cache_key, sms_cache_key_express);
                } else {
                    Long express = (Long) redisTemplate.opsForValue().get(sms_cache_key_express);
                    if (express == null) {//发送间隔过期了
                        if (cachePhoneSms.getStatus() == 0)
                            return ResultFactory.getErrorResult("短信已经发送成功，请稍等！");
                        else
                            return sendCacheMessage(platformSmsTemplate, smsParam, sms_cache_key, sms_cache_key_express);
                    } else {
                        return ResultFactory.getErrorResult("重复发送短信需要间隔" + platformSmsTemplate.getExpireinterval() + "分钟");
                    }
                }
            }else{
                return ResultFactory.getErrorResult("发送正在发送，请稍后");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ResultFactory.getErrorResult("发送短信异常，请稍后重试");
        } finally {
            lock.unlock();
        }

    }

    /**
     * 保存到缓存并发送短信
     *
     * @param platformSmsTemplate
     * @param smsParam
     * @param sms_cache_key
     * @param sms_cache_key_express
     * @return
     */
    private Result sendCacheMessage(PlatformSmsTemplate platformSmsTemplate, SmsParam smsParam, String sms_cache_key, String sms_cache_key_express) {
        String content = String.format(platformSmsTemplate.getContent(), smsParam.getArgs());
        CachePhoneSms cachePhoneSms = new CachePhoneSms();
        cachePhoneSms.setContent(content);
        cachePhoneSms.setPhone(smsParam.getPhoneno());
        cachePhoneSms.setSms_code(smsParam.getArgs()[0]);
        cachePhoneSms.setStatus(-1);
        Result result = selectChannelAndSendSms(platformSmsTemplate, cachePhoneSms, smsParam.getArgs(), true);
        if (result.getResultStatus() == CommonFinal.RESULT_CODE_FAILURE) {
            cachePhoneSms.setStatus(0);
        } else {
            result = ResultFactory.getSuccessResult("发送成功");
            logger.info("smscode:" + sms_cache_key);
        }
        redisTemplate.opsForValue().set(sms_cache_key, cachePhoneSms, platformSmsTemplate.getExpires(), TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(sms_cache_key_express, new Date().getTime(), platformSmsTemplate.getExpireinterval(), TimeUnit.MINUTES);

        return result;
    }

    /**
     * @param platformSmsTemplate
     * @param cachePhoneSms
     * @param contentArgs
     * @param is_retry            true
     * @return
     */
    private Result selectChannelAndSendSms(PlatformSmsTemplate platformSmsTemplate, CachePhoneSms cachePhoneSms, String[] contentArgs, boolean is_retry) {
        PlatformSms platformSms = hybPlatformSmsMapper.findPlatformSmsById(platformSmsTemplate.getPlatformsmsid());
        if (platformSms == null || platformSms.getStatus() != 0) {
            return ResultFactory.getErrorResult("短信平台未配置或已停用，请配置");
        }
        boolean is_flag = false;
        logger.info("手机号：" + cachePhoneSms.getPhone() + " 通道id：" + platformSmsTemplate.getPlatformsmsid());
        if (platformSms.getChannel_type() == 0) {//阿里
            if (Strings.isEmpty(platformSmsTemplate.getOut_template_code())) {
                return ResultFactory.getErrorResult("阿里短信模版不存在");
            }
            is_flag = SmsUtil.aliSendSms(platformSms.getAccesskey(),
                    platformSms.getAccess_secret(),
                    platformSms.getSign_name(),
                    platformSmsTemplate.getOut_template_code(),
                    cachePhoneSms.getPhone(),
                    contentArgs, platformSmsTemplate.getContent());
        } else if (platformSms.getChannel_type() == 1) {//华信
            try {
                cachePhoneSms.setContent(platformSmsTemplate.getContent());
                String content = String.format(cachePhoneSms.getContent(), contentArgs);
                is_flag = SmsUtil.huaxinSendSms(platformSms.getAccesskey(), platformSms.getAccess_secret(), cachePhoneSms.getPhone(), content, platformSms.getSign_name());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return ResultFactory.getErrorResult("短信平台通道无法识别");
        }
        if (is_flag) {//发送成功，返回
            return ResultFactory.getSuccessResult("发送成功");
        } else {
            if (is_retry) {//是否需要重试
                logger.info("手机号：" + cachePhoneSms.getPhone() + " 重试：" + platformSmsTemplate.getPlatformsmsid());
                return retrySms(platformSmsTemplate, cachePhoneSms, contentArgs);
            } else {
                return ResultFactory.getErrorResult("发送失败");
            }

            // return retrySms(platformSmsTemplate, cachePhoneSms, contentArgs);


        }
    }


    /**
     * 重试其他渠道
     *
     * @param platformSmsTemplate
     * @param cachePhoneSms
     * @param contentArgs
     * @return
     */
    private Result retrySms(PlatformSmsTemplate platformSmsTemplate, CachePhoneSms cachePhoneSms, String[] contentArgs) {
        if (Strings.isNotNullOrEmpty(platformSmsTemplate.getRetry_templateid())) {
            String[] templateids = platformSmsTemplate.getRetry_templateid().split(";");
            for (int i = 0; i < templateids.length; i++) {
                int templateid = Integer.parseInt(templateids[i]);
                Result result = smsContentByTemplateid(templateid, cachePhoneSms, contentArgs);
                if (result.getResultStatus() == CommonFinal.RESULT_CODE_SUCCESS) {//如果发送成功
                    return result;
                }
            }
        }
        return ResultFactory.getErrorResult("发送失败");
    }

    private Result smsContentByTemplateid(int templateid, CachePhoneSms cachePhoneSms, String[] contentArgs) {
        PlatformSmsTemplate platformSmsTemplate = hybPlatformSmsMapper.findPlatformSmsTemplateById(templateid);
        if (platformSmsTemplate == null) {
            return ResultFactory.getErrorResult("短信模版不存在，请核对后重试");
        }
        return selectChannelAndSendSms(platformSmsTemplate, cachePhoneSms, contentArgs, false);
    }

    private String getSmsCacheKey(SmsParam smsParam) {
        return smsParam.getAppid() + "_" + smsParam.getBusiness_code() + "_" + smsParam.getPhoneno();
    }

    private String getSmsCacheExpress(SmsParam smsParam) {
        return smsParam.getAppid() + "_" + smsParam.getBusiness_code() + "_" + smsParam.getPhoneno() + "_express";
    }
}
