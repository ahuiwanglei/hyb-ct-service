package com.mszx.hyb.ums.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PlatformJpushRecord {
    private Integer jpushrecordid;
    private String userid;
    private String business_code;
    private String title;
    private String content;
    private Integer status;
    private Date create_at;
}
