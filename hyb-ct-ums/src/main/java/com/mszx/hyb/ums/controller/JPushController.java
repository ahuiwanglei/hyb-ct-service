package com.mszx.hyb.ums.controller;

import com.mszx.hyb.common.base.BaseController;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.ums.param.JpushParam;
import com.mszx.hyb.ums.param.SearchJpushRecord;
import com.mszx.hyb.ums.param.SmsParam;
import com.mszx.hyb.ums.service.JpushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ums/push")
public class JPushController extends BaseController{

    @Autowired
    private JpushService jpushService;

    @PostMapping(value = "/sendNotification",   produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result sendNotification(@ModelAttribute JpushParam jpushParam){
        return jpushService.smsNotification(jpushParam);
    }

    @PostMapping(value = "/getRecordList",   produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result getRecordList(SearchJpushRecord searchJpushRecord){
        return jpushService.findJpushRecordList(searchJpushRecord);
    }

}
