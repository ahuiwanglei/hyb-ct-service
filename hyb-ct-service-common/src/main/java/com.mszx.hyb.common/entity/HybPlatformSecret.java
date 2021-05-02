package com.mszx.hyb.common.entity;


import lombok.Data;

@Data
public class HybPlatformSecret {
    private Integer platformid;
    private String name;
    private String code;
    private String platform_key;
    private String secret;
    private String signpwd;
    private Integer status;
    private String create_at;
    private String is_skip;
}
