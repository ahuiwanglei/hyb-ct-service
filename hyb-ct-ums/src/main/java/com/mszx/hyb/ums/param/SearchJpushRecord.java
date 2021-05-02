package com.mszx.hyb.ums.param;

import lombok.Data;

@Data
public class SearchJpushRecord {
    private String userid;
    private String business_code;
    private Integer pageSize;
    private Integer pageNum;
}
