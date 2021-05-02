package com.mszx.hyb.ums.param;

import lombok.Data;

@Data
public class SmsParam {
    private String appid;
    private String phoneno;
    private String business_code;//业务标示
    private String code;
    private Integer is_invalid = 1;
    private String template_code; //模版编码
    private String args[];
    private String extras;
}
