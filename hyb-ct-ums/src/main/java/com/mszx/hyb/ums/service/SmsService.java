package com.mszx.hyb.ums.service;

import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.ums.param.SmsParam;

public interface SmsService {
    Result smsCode(SmsParam smsParam);

    Result validateSmsCode(SmsParam smsParam);
}
