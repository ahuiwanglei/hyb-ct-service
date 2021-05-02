package com.mszx.hyb.controller;

import com.mszx.hyb.common.base.BaseController;
import com.mszx.hyb.common.exception.HybCtException;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.entity.HybIntegralRecord;
import com.mszx.hyb.entity.HybWxFan;
import com.mszx.hyb.entity.HybWxUser;
import com.mszx.hyb.param.UserInfo;
import com.mszx.hyb.service.IWxAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wx")
public class WxController extends BaseController {

    @Autowired
    private IWxAction wxAction;

    @PostMapping(value = "/fans/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result saveFans(@ModelAttribute HybWxFan wxFan) {
        return wxAction.saveFans(wxFan);
    }


    @PostMapping(value = "/fans/info", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result findFans(@ModelAttribute HybWxFan wxFan) {
        return wxAction.findByFans(wxFan);
    }

    @PostMapping(value = "/findUserByOpenId", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result getUserByOpenid(@ModelAttribute HybWxUser hybWxUser) {
        return wxAction.findUserByOpenId(hybWxUser);
    }


    /**
     * 检测是否已经注册，
     * 如果已经注册-->是否绑定～
     * 如果未注册---> 注册
     * @param hybWxUser
     * @return
     */
    @PostMapping(value = "/wxRegister", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result wxRegister(@ModelAttribute HybWxUser hybWxUser) throws HybCtException {
        return wxAction.wxRegister(hybWxUser);
    }
    @PostMapping(value = "/wxRegisterWithNoSMS", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result wxRegisterWithNoSMS(@ModelAttribute HybWxUser hybWxUser) throws HybCtException {
        return wxAction.wxRegisterWithNoSMS(hybWxUser);
    }


    @PostMapping(value = "/fans/integral", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result fansIntegral(HybIntegralRecord hybIntegralRecord){
        return wxAction.updateIntegral(hybIntegralRecord);
    }

    @PostMapping(value = "/fans/updateContact", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result updateContact(@ModelAttribute HybWxFan wxFan) {
        return wxAction.updateContact(wxFan);
    }
}
