package com.mszx.hyb.ums.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.common.util.ResultFactory;
import com.mszx.hyb.common.util.Strings;
import com.mszx.hyb.ums.dao.HybJpushMapper;
import com.mszx.hyb.ums.entity.PlatformJpush;
import com.mszx.hyb.ums.entity.PlatformJpushRecord;
import com.mszx.hyb.ums.entity.PlatformJpushTemplate;
import com.mszx.hyb.ums.param.JpushBusinessCode;
import com.mszx.hyb.ums.param.JpushParam;
import com.mszx.hyb.ums.param.SearchJpushRecord;
import com.mszx.hyb.ums.service.JpushService;
import com.mszx.hyb.ums.util.huaxin.SendMessageHwasin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JpushServiceImpl  implements JpushService{

    protected static final Logger logger = LoggerFactory.getLogger(JpushServiceImpl.class);

    @Autowired
    private HybJpushMapper hybJpushMapper;

    @Override
    public Result findJpushRecordList(SearchJpushRecord searchJpushRecord) {
        if(searchJpushRecord.getPageNum() == null || searchJpushRecord.getPageSize() == null){
            return ResultFactory.getErrorResult("分页信息不能为空");
        }
        Page<PlatformJpushRecord> recordPage = null;
        if(searchJpushRecord.getBusiness_code().equals(JpushBusinessCode.server_notification.getCode())){
            searchJpushRecord.setUserid(null);
            recordPage =  PageHelper.startPage(searchJpushRecord.getPageNum(), searchJpushRecord.getPageSize()).doSelectPage(() -> hybJpushMapper.findByRecordList(searchJpushRecord));
        } else if (searchJpushRecord.getBusiness_code().equals(JpushBusinessCode.all_notification.getCode())) {
            searchJpushRecord.setBusiness_code(null);
            recordPage =  PageHelper.startPage(searchJpushRecord.getPageNum(), searchJpushRecord.getPageSize()).doSelectPage(() -> hybJpushMapper.findBYRecordAll(searchJpushRecord));
        }else if(searchJpushRecord.getBusiness_code().equals(JpushBusinessCode.user_notification.getCode())){
            if(Strings.isEmpty(searchJpushRecord.getUserid())){
                return ResultFactory.getErrorResult("用户信息不能为空");
            }
            recordPage =  PageHelper.startPage(searchJpushRecord.getPageNum(), searchJpushRecord.getPageSize()).doSelectPage(() -> hybJpushMapper.findByRecordList(searchJpushRecord));
        }

        return ResultFactory.getSuccessResult(new PageInfo<PlatformJpushRecord>(recordPage));
    }

    @Override
    public Result smsNotification(JpushParam jpushParam) {
        if(Strings.isEmpty(jpushParam.getAppid())){
            return ResultFactory.getErrorResult("平台Id 不能为空");
        }
        if(Strings.isEmpty(jpushParam.getTemplate_code())){
            return ResultFactory.getErrorResult("模版编码不能为空");
        }
        if(jpushParam.getAudience_type() == 0 && Strings.isEmpty(jpushParam.getRegistration_id())){
            return ResultFactory.getErrorResult("registration_id不能为空");
        }
        if(jpushParam.getAudience_type() == 1 && Strings.isEmpty(jpushParam.getTags())){
            return ResultFactory.getErrorResult("tags 不能为空");
        }
//        if(Strings.isEmpty(jpushParam.getUserid())){
//            return ResultFactory.getErrorResult("用户主键不能为空");
//        }
        if(Strings.isEmpty(jpushParam.getBusiness_code())){
            return ResultFactory.getErrorResult("业务类型 不能为空");
        }
        PlatformJpushTemplate platformJpushTemplate = hybJpushMapper.findJpushTemplateCode(jpushParam.getTemplate_code());
        if(platformJpushTemplate == null || platformJpushTemplate.getStatus() !=  0){
            return ResultFactory.getErrorResult("模版不存在或已停用");
        }
        PlatformJpush platformJpush = hybJpushMapper.findPlatformJpushById(platformJpushTemplate.getPlatformjpushid());
        if(platformJpush == null || platformJpush.getStatus() != 0){
            return ResultFactory.getErrorResult("推送平台未配置或已停用");
        }
        boolean sendResult = sendNotificatoin(platformJpush, platformJpushTemplate, jpushParam);
        if(sendResult){
            //保存发送纪录
            PlatformJpushRecord record = new PlatformJpushRecord();
            record.setBusiness_code(jpushParam.getBusiness_code());
            record.setCreate_at(new Date());
            record.setStatus(0);
            record.setTitle(platformJpushTemplate.getTitle());
            String content = String.format(platformJpushTemplate.getContent(), jpushParam.getArgs());
            record.setContent(content);
            record.setUserid(jpushParam.getUserid());
            hybJpushMapper.saveJpushRecord(record);
            return ResultFactory.getSuccessResult("发送成功");
        }
        return ResultFactory.getErrorResult("发送失败");
    }

    private boolean sendNotificatoin(PlatformJpush platformJpush, PlatformJpushTemplate platformJpushTemplate, JpushParam jpushParam){
        JPushClient jpushClient = new JPushClient(platformJpush.getAccess_secret(), platformJpush.getAccesskey());
        PushPayload.Builder payloadBuilder = null;
        if(jpushParam.getAudience_type() == 0){
            payloadBuilder = PushPayload.newBuilder().setAudience(Audience.registrationId(jpushParam.getRegistration_id()));
        }else if(jpushParam.getAudience_type() == 1){
            payloadBuilder = PushPayload.newBuilder().setAudience(Audience.tag(jpushParam.getTags()));
        }else {
            logger.error("push Message error, audience_type 不支持");
            return false;
        }
        try {
            String content = String.format(platformJpushTemplate.getContent(), jpushParam.getArgs());
            AndroidNotification.Builder abuild = AndroidNotification.newBuilder()
                    .setAlert(content)
                    .setTitle(platformJpushTemplate.getTitle());
//            JSONObject object = new JSONObject();
//            object.put("title", platformJpushTemplate.getTitle());
//            object.put("subtitle",platformJpushTemplate.getContent());
            IosNotification.Builder  ibuild = IosNotification.newBuilder()
                    .setAlert(content)//object.toJSONString()
                    .setBadge(platformJpushTemplate.getIos_badge())
                    .setSound(platformJpushTemplate.getIos_sound());
            if(jpushParam.getExtras() != null && !jpushParam.getExtras().isEmpty()){
                abuild.addExtras(jpushParam.getExtras());
            }
            payloadBuilder.setPlatform(Platform.android_ios());
            payloadBuilder.setNotification(Notification.newBuilder()
                    .addPlatformNotification(abuild.build())
                    .addPlatformNotification(ibuild.build())
                    .build());
            payloadBuilder.setOptions(Options.newBuilder()
                    .setApnsProduction(platformJpush.getIs_production() == 1)
                    .build());
            logger.info("payloadBuilder:" + JSON.toJSONString(payloadBuilder));
            PushResult result = jpushClient.sendPush(payloadBuilder.build());
            logger.info("Got result - " + result);
            return result.msg_id >0;
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            logger.error("Connection error, should retry later", e);
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            logger.error("Should review the error, and fix the request", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
        }
        return false;
    }
}
