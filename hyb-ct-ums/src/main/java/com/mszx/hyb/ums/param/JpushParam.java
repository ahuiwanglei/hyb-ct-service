package com.mszx.hyb.ums.param;

import lombok.Data;

import java.util.Map;

@Data
public class JpushParam {
//    private String
    private String appid;
    private Integer audience_type = 0; // 0: 设备推送  1：标签推送
    private String registration_id;
    private String userid;
    private String business_code;
    private String tags;
    private String template_code; //模版编码
    private String args[];
    private Map<String, String> extras;
}
