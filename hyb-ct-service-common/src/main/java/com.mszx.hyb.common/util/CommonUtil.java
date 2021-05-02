package com.mszx.hyb.common.util;

import java.util.*;

/**
 * Created by fengpro@163.com on 2017/9/26 16:09.
 */
public class CommonUtil {
    /**
     * 得到一个UUID
     * @return
     * @throws Exception
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String get32UUID()
    {
        return MD5.getMD5Str(UUID.randomUUID().toString().replace("-", "")+ System.currentTimeMillis()).toLowerCase();
    }
    /**
     * 判断list是否为空
     * @param list
     * @return
     */
    public static <E> boolean listIsNullOrEmpty(List<E> list){
        if(list == null || list.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * 创建list实例
     * @return
     */
    public static <E> List<E> listNewInstance(){
        return new ArrayList<E>();
    }

    /**
     * 创建map实例
     * @return
     */
    public static <K,V> HashMap<K,V> hashMapNewInstance(){
        return new HashMap<K,V>();
    }

    /**
     * 创建Set实例
     * @return
     */
    public static <K> HashSet<K> hashSetNewInstance(){
        return new HashSet<K>();
    }
}
