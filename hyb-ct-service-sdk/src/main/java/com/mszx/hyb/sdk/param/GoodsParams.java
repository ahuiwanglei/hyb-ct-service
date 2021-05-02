package com.mszx.hyb.sdk.param;

import com.mszx.hyb.sdk.util.Strings;

import java.math.BigDecimal;

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

    public Integer getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Integer goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(String goods_pic) {
        this.goods_pic = goods_pic;
    }

    public Integer getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(Integer goods_num) {
        this.goods_num = goods_num;
    }

    public BigDecimal getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(BigDecimal goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
}
