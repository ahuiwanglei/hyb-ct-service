package com.mszx.hyb.ums.entity.cache;

import lombok.Data;

import java.util.Date;

@Data
public class CachePhoneSms {

    private String phone;
    private String content;
    private String sms_code;
    private Integer status; //0代表成功，-1代表失败
}
