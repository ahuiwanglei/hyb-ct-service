package com.mszx.hyb.ums.entity;

import lombok.Data;

@Data
public class PlatformJpush {
    private Integer platformjpushid;
    private Integer platformid;
    private String accesskey;
    private String access_secret;
    private Integer is_production;
    private Integer time_to_live;
    private Integer status;
}
