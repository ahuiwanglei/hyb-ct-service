package com.mszx.hyb.sdk.result;


import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class RequestResultFactory {
    private static Logger log = Logger.getLogger(RequestResultFactory.class);
    /**
     * json返回标志-成功
     */
    public static final int RESULT_CODE_SUCCESS = 0;// 成功
    /**
     * json返回标志-失败
     */
    public static final int RESULT_CODE_FAILURE = -1;// 失败
    /**
     * 成功
     */
    public static final String SUCCESS = "SUCCESS";
    /**
     * 失败
     */
    public static final String FAIL = "FAIL";

    /**
     * 暂无数据
     */
    public static final String NODATA = "暂无数据";

    public static <T> ReqeustResult<T> getErrorResult(T data) {
        ReqeustResult model = new ReqeustResult();
        model.setMsg(FAIL);
        model.setResultStatus(RESULT_CODE_FAILURE);
        model.setResultData(data);
        return model;
    }

    public static <T>  ReqeustResult<T> getErrorResult(Integer code, T data) {
        ReqeustResult model = new ReqeustResult();
        model.setMsg(FAIL);
        model.setResultStatus(code);
        model.setResultData(data);
        return model;
    }

    public static  ReqeustResult getErrorResult(String msg) {
        return getErrorResult(RESULT_CODE_FAILURE, msg);
    }

    public static <T> ReqeustResult<T>  getSuccessResult(T data) {
        ReqeustResult model = new ReqeustResult();
        model.setMsg(SUCCESS);
        model.setResultStatus(RESULT_CODE_SUCCESS);
        model.setResultData(data);
        return model;
    }


//

    public static<T> ReqeustResult<Map<String, T>> getSuccessResult(String key, T data){
        ReqeustResult model = new ReqeustResult();
        model.setMsg(SUCCESS);
        model.setResultStatus(RESULT_CODE_SUCCESS);
        Map<String, T> map = new HashMap<String, T>();
        map.put(key, data);
        model.setResultData(map);
        return model;
    }

    public static ReqeustResult<Map<String, Integer>> getSuccessResultForSumbitData(int id){
        ReqeustResult model = new ReqeustResult();
        model.setMsg(SUCCESS);
        model.setResultStatus(RESULT_CODE_SUCCESS);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("id", id);
        model.setResultData(map);
        return model;
    }
}
