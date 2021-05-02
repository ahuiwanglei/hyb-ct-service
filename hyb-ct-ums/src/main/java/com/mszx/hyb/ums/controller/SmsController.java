package com.mszx.hyb.ums.controller;

import com.mszx.hyb.common.base.BaseController;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.common.util.ResultFactory;
import com.mszx.hyb.ums.param.SmsParam;
import com.mszx.hyb.ums.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ums/sms")
public class SmsController extends BaseController{

    protected static final Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Autowired
    private SmsService smsService;

    @PostMapping(value = "/sendPhone",   produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result sendPhone(@ModelAttribute SmsParam smsParam){
        return smsService.smsCode(smsParam);
    }

    @PostMapping(value = "/validateSmsCode",   produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result validateSmsCode(@ModelAttribute SmsParam smsParam){
        return smsService.validateSmsCode(smsParam);
    }

}
