package com.mszx.hyb.ums.entity;

import lombok.Data;

@Data
public class PlatformSmsTemplate {
    private Integer smstemplateid;
    private Integer platformsmsid;
    private String template_code;
    private String content;
    private String out_template_code;
    private Integer status;
    private Integer expires;
    private Integer expireinterval;
    private String retry_templateid;
    private String create_at;
}
