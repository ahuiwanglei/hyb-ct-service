package com.mszx.hyb.sdk.param;


public class SearchOrderListParams {
    private String appid;
    private String pkmuser_name;
    private Integer order_type =0;
    private Integer pageNum;
    private Integer pageSize;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPkmuser_name() {
        return pkmuser_name;
    }

    public void setPkmuser_name(String pkmuser_name) {
        this.pkmuser_name = pkmuser_name;
    }

    public Integer getOrder_type() {
        return order_type;
    }

    public void setOrder_type(Integer order_type) {
        this.order_type = order_type;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
