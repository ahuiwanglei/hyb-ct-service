package com.mszx.hyb.sdk.result;

public class ReqeustResult<T> {
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

    public ReqeustResult() {
    }

    public ReqeustResult(Integer resultStatus, String msg, T data) {
        this.resultStatus = resultStatus;
        this.msg = msg;
        this.resultData = data;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }
}
