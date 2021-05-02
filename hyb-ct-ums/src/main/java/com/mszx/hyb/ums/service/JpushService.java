package com.mszx.hyb.ums.service;

import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.ums.param.JpushParam;
import com.mszx.hyb.ums.param.SearchJpushRecord;
import com.mszx.hyb.ums.param.SmsParam;

public interface JpushService {
    Result smsNotification(JpushParam jpushParam);

    Result findJpushRecordList(SearchJpushRecord searchJpushRecord);
}
