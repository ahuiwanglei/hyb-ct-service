package com.mszx.hyb.service;

import com.mszx.hyb.common.exception.HybCtException;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.entity.HybIntegralRecord;
import com.mszx.hyb.entity.HybWxFan;
import com.mszx.hyb.entity.HybWxUser;
import com.mszx.hyb.param.UserInfo;

public interface IWxAction {
    Result saveFans(HybWxFan wxFan);

    Result findByFans(HybWxFan wxFan);

    Result updateIntegral(HybIntegralRecord hybIntegralRecord);

    Result updateContact(HybWxFan wxFan);

    Result findUserByOpenId(HybWxUser hybWxUser);

    Result wxRegister(HybWxUser hybWxUser) throws HybCtException;

    Result wxRegisterWithNoSMS(HybWxUser hybWxUser);
}
