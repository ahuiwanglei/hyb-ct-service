package com.mszx.hyb.ums.entity;

import lombok.Data;

@Data
public class PlatformJpushTemplate {
    private Integer jpushtemplateid;
    private Integer platformjpushid;
    private String template_code;
    private String title;
    private String content;
    private Integer ios_badge;
    private String ios_sound;
    private Integer status;
}
