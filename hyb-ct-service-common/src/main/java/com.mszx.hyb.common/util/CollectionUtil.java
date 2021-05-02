package com.mszx.hyb.common.util;

import java.util.*;

/**
 * Created by fengpro@163.com on 2017/9/26 16:49.
 */
public class CollectionUtil {
    /**
     * 创建list实例
     * @return
     */
    public static <E> List<E> newListInstance(){
        return new ArrayList<E>();
    }

    /**
     * 创建map实例
     * @return
     */
    public static <K,V> HashMap<K,V> newMapInstance(){
        return new HashMap<K,V>();
    }

    /**
     * 创建set实例
     * @return
     */
    public static <E> Set<E> newSetInstance(){
        return new HashSet<E>();
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
}
