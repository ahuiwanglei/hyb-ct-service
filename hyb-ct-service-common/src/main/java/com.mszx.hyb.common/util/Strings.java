package com.mszx.hyb.common.util;

import java.util.Random;

/**
 * String相关工具类
 *
 * @author fengpro@163.com
 * Copyright:(c) 2016年3月23日 下午1:39:00
 * company:民生在线
 */
public class Strings {

    public static boolean isNullOrEmpty(String source) {
        if (source == null || "".equals(source.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isNotNullOrEmpty(String source) {
        if (source == null || "".equals(source.trim())) {
            return false;
        }
        return true;
    }

    /**
     * 检测字符串是否为空(null,"","null")
     *
     * @param s
     * @return 为空则返回true，不否则返回false
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s) || "null".equals(s);
    }


    /**
     * 获取String值
     *
     * @return
     */
    public static String toStringValue(Object o) {
        if (o == null)
            return "";
        else
            return String.valueOf(o).trim();
    }

    /***
     * 截取字符串后四位，不足四位直接返回
     * 为空时 返回【0000】
     * @param source
     * @return
     */
    public static String subStringParam(String source, int num) {
        if (isNotNullOrEmpty(source)) {
            if (source.length() <= num) {
                return source;
            } else {
                return source.substring(source.length() - num, source.length());
            }
        } else {
            return "0000";
        }
    }

    public static double getDouble(String value) {
        if (isNotNullOrEmpty(value)) {
            return Double.parseDouble(value);
        }
        return 0;
    }

    /**
     * 检测是否为数字
     *
     * @param s
     * @return
     */
    public static boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * 返回随机数
     *
     * @param n 个数
     * @return
     */
    public static String random(int n) {
        if (n < 1 || n > 10) {
            throw new IllegalArgumentException("cannot random " + n + " bit number");
        }
        Random ran = new Random();
        if (n == 1) {
            return String.valueOf(ran.nextInt(10));
        }
        int bitField = 0;
        char[] chs = new char[n];
        for (int i = 0; i < n; i++) {
            while (true) {
                int k = ran.nextInt(10);
                if ((bitField & (1 << k)) == 0) {
                    bitField |= 1 << k;
                    chs[i] = (char) (k + '0');
                    break;
                }
            }
        }
        return new String(chs);
    }

    /**
     * 指定范围的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static int getRandomNum(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    //2 + order_type + 6位随机数 + 时间撮
    public static String createOrderNum(int order_type) {
        String orderNum = "";
        orderNum = orderNum + "2" + order_type;
        orderNum = orderNum + getRandomNum(100000,999999) +  System.currentTimeMillis();
        return orderNum;
    }

    public static String createCheckCode() {
        String time = ( System.currentTimeMillis()+"");
       return  getRandomNum(1000,9999)+ time.substring(time.length()-4,time.length());
    }
}
