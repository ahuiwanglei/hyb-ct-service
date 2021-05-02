package com.mszx.hyb.ordercenter.params;

import lombok.Data;

@Data
public class SearchOrderListParams {
    private String pkmuser_name;
    private Integer order_type =0;
    private Integer pageNum;
    private Integer pageSize;

}
