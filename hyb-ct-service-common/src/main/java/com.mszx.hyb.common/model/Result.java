package com.mszx.hyb.common.model;

import com.mszx.hyb.common.util.JsonMapper;
import lombok.Data;

/**
 * 返回结果
 * @author fengpro@163.com
 * Copyright:(c) 2016年3月22日 下午5:20:40
 * company:民生在线
 */
@Data
public class Result<T> {
    /**
     * 结果码
     */
    private Integer resultStatus;

    /**
     * 结果信息
     */
    private String msg;

    /**
     * 结果对象
     */
    private T resultData;

    public Result() {
    }
    public Result(Integer resultStatus, String msg, T data) {
        this.resultStatus = resultStatus;
        this.msg = msg;
        this.resultData = data;
    }

    public String toJson(){
        return new JsonMapper().toJson(this);
    }
}
