package com.mszx.hyb.ums.entity;

import lombok.Data;

@Data
public class PlatformSms {
    private Integer platformsmsid;
    private Integer platformid;
    private Integer channel_type;
    private String accesskey;
    private String access_secret;
    private String sign_name;
    private Integer status;
    private String create_at;
}
