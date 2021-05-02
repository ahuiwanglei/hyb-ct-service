package com.mszx.hyb.common.util;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mszx.hyb.common.model.CommonFinal;
import com.mszx.hyb.common.model.PageInfoResult;
import com.mszx.hyb.common.model.Result;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ResultFactory {
    public static <T> Result<T> getErrorResult(T data) {
        Result model = new Result();
        model.setMsg(CommonFinal.FAIL);
        model.setResultStatus(CommonFinal.RESULT_CODE_FAILURE);
        model.setResultData(data);
        return model;
    }

    public static <T>  Result<T> getErrorResult(Integer code, T data) {
        Result model = new Result();
        model.setMsg(CommonFinal.FAIL);
        model.setResultStatus(code);
        model.setResultData(data);
        return model;
    }

//    public static  Result getErrorResult(String msg) {
//        return getErrorResult(CommonFinal.RESULT_CODE_FAILURE, CommonFinal.FAIL);
    //wl test git commit 1
//    }

    public static <T> Result<T>  getSuccessResult(T data) {
        Result model = new Result();
        model.setMsg(CommonFinal.SUCCESS);
        model.setResultStatus(CommonFinal.RESULT_CODE_SUCCESS);
        model.setResultData(data);
        return model;
    }

    //封装分页
    public static<T> Result<PageInfoResult<T>> getSuccessResult(PageInfo<T> pageInfo){
        PageInfoResult result = new PageInfoResult();
        result.setList(pageInfo.getList());
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        return getSuccessResult(result);
    }

//    //封装分页
//    public static<T> Result<PageInfoResult<T>> getSuccessResult(Page<T> pageInfo){
//        PageInfoResult result = new PageInfoResult();
//        result.setList(pageInfo.getContent());
//        result.setPages(pageInfo.getTotalPages());
//        result.setTotal(pageInfo.getTotalElements());
//        return getSuccessResult(result);
//    }
//
//

    public static<T> Result<Map<String, T>> getSuccessResult(String key, T data){
        Result model = new Result();
        model.setMsg(CommonFinal.SUCCESS);
        model.setResultStatus(CommonFinal.RESULT_CODE_SUCCESS);
        Map<String, T> map = new HashMap<String, T>();
        map.put(key, data);
        model.setResultData(map);
        return model;
    }

    public static Result<Map<String, Integer>> getSuccessResultForSumbitData(int id){
        Result model = new Result();
        model.setMsg(CommonFinal.SUCCESS);
        model.setResultStatus(CommonFinal.RESULT_CODE_SUCCESS);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("id", id);
        model.setResultData(map);
        return model;
    }


    public static Result convertResult(String resultStr){
        if(Strings.isEmpty(resultStr))return null;
        Gson gson = new Gson();
        Type type = new TypeToken<Result<String>>() {
        }.getType();
        return gson.fromJson(resultStr, type);
    }
}
