package com.mszx.hyb.ordercenter.params;

import com.mszx.hyb.common.util.ResultFactory;
import com.mszx.hyb.common.util.Strings;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsParams {

    private Integer goodsid;
    private String goods_pic;
    private  Integer goods_num =1;
    private BigDecimal goods_price;
    private String goods_name;

    public String checkParams() {
        if(goodsid == null){
            return "goodsid不能为空";
        }
        if(Strings.isEmpty(goods_name)){
            return "goods_name 不能为空";
        }
        if(goods_price == null){
            return "goods_price 不能为空";
        }
        return null;
    }
}
