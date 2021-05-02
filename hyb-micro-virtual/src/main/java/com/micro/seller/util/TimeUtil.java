package com.micro.seller.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    private static final long ONEDATE = 86400000; // 一天的时间差
    private static final long ONEHOUR = 3600000; // 一小时的时间差
    private static final long ONEMINUTE = 60000; // 一分钟的时间差


    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private final static SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");

    private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

    private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

    private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public final static String sdf_str_time = "yyyy-MM-dd HH:mm:ss";

    public final static String sdf_str_date = "yyyy-MM-dd";

    public final static String milliSecondTime = new String("yyyyMMddHHmmssSSS");

    public static String putDzDate(int time){
        Long t = new Long(time);
        return putDate(new Date((t * 1000)));
    }


    /**
     * time类型 是 yyyy-MM-dd HH:mm:ss.s 不是的请转化成这个
     * time 未与当前时间相比的时间  time  就是未来的时间
     * @param otheTime
     * @return
     */
    public static String putDate(Date otheTime) {
        String dateStr = null;
//          SimpleDateFormat dd = new SimpleDateFormat("dd");
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
//            Date otheTime = yyyyMMdd.parse(time);
        Date nowTime = new Date();
        Long time10 = nowTime.getTime();
        Long time11 = otheTime.getTime();
        long timeDiff = time10 - time11;

        if (timeDiff >= ONEDATE) {
            String someDate = someDate(timeDiff);
            return someDate == null ? yyyyMMdd.format(otheTime) : someDate;
        }
        if (timeDiff >= ONEHOUR) {
            return someHour(timeDiff);
        }
        if (timeDiff >= ONEMINUTE) {
            return someMinute(timeDiff);
        }
        return "刚刚";
    }

    public static boolean getRuleInterval(Long otheTime, Integer cycletype) {
        long nowTime = System.currentTimeMillis()/1000;
        long timeDiff = nowTime - otheTime;
        System.out.print(nowTime + " --" + otheTime + " --" + timeDiff);
        if (cycletype == 0) {
            return false;
        } else if (cycletype == 1) {
            return (timeDiff >= ONEDATE) ? true : false;
        } else if (cycletype == 2) {
            return (timeDiff >= ONEHOUR) ? true : false;
        } else if (cycletype == 3) {
            return (timeDiff >= ONEMINUTE) ? true : false;
        } else if (cycletype == 4) {
            return true;
        }
        return false;
    }

    public static Date getDzDate(int time){
        Long t = new Long(time);
        return new Date(t * 1000);
    }

    public static int getDetailDate(Date date, char type) {
        int result = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (type) {
            case 'y': result = c.get(Calendar.YEAR);break;
            case 'm': result = c.get(Calendar.MONTH) + 1;break;
            case 'd': result = c.get(Calendar.DAY_OF_MONTH);break;
                default:break;
        }
        return result;
    }

    private static String someMinute(long timeDiff) {
        int dateNum = (int) (timeDiff / ONEMINUTE);
        String dateStr;
        if (dateNum < 10) {
            dateStr = "刚刚";
        } else if (dateNum < 30) {
            dateStr = "半小时前";
        } else {
            dateStr = "一个小时前";
        }

        return dateStr;

    }

    /**
     * 判断相差小时
     *
     * @param timeDiff
     * @return
     */

    private static String someHour(long timeDiff) {
        int dateNum = (int) (timeDiff / ONEHOUR);
        String dateStr;
        if (dateNum < 12) {
            dateStr = getDate(dateNum, "小时前");
        } else {
            dateStr = "一天前";
        }
        return dateStr;
    }

    /**
     * 用于判断相差月数
     *
     * @param timeDiff
     * @return
     */
    private static String someDate(long timeDiff) {
        int dateNum = (int) (timeDiff / ONEDATE);
        String dateStr;
        if (dateNum <= 15) {
            dateStr = getDate(dateNum, "天前");
        } else if (dateNum <= 30 && dateNum > 15) {
            dateStr = "一个月前";
        } else if (dateNum < 60) {
            dateStr = "两个月前";
        } else {
            dateStr = "很久很久以前";
        }
        return dateStr;
    }

    /**
     * 组装提示
     *
     * @param index
     * @param str
     * @return
     */
    private static String getDate(int index, String str) {
        return String.format("%s%s", enNum2CnNum(String.valueOf(index)), str);
    }

    public static int getTimeStamp(){
        return (int)(new Date().getTime() / 1000);
    }

    /**
     * 数字转化成中国的一二三
     *
     * @param num
     * @return
     */
    public static String enNum2CnNum(String num) {
        String[] CnNum = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        //自己最大值 只需要 在这里添加就行了 其他代码不用改 例如到 亿
        String[] Cn10Num = new String[]{"十", "百", "千", "万"};
        String CnStr = "";

        try {
            char[] strArray = num.toCharArray();
            int length = strArray.length;
            for (int i = 0; i < strArray.length; i++) {
                if (length > 1 && ((char2Int(strArray[i])) != 0)) {
                    CnStr += (CnNum[char2Int(strArray[i])] + Cn10Num[length - 2]);
                } else {
                    CnStr += CnNum[char2Int(strArray[i])];
                }
                length--;
            }
            // 处理多个零字段
            CnStr = CnStr.replaceAll("零零*", "零");
            // 处理最后一个是零
            if (CnStr.lastIndexOf("零") + 1 == CnStr.length()) {
                CnStr = CnStr.replaceAll("零$", "");
            }
            // 处理十几
            if (CnStr.contains("十") && (CnStr.length() == 3 || CnStr.length() == 2)) {
                CnStr = CnStr.replaceAll("^一", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CnStr;

    }


    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return sdfYear.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return sdfDay.format(new Date());
    }

    /**
     * 获取YYYY-MM格式
     *
     * @return
     */
    public static String getCurrentMonth() {
        return sdfMonth.format(new Date());
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays() {
        return sdfDays.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }

    /**
     * @Title: compareDate
     * @Description: (日期比较，如果s>=e 返回true 否则返回false)
     * @param s
     * @param e
     * @return boolean
     * @throws @author
     *             luguosui
     */
    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) == null || fomatDate(e) == null) {
            return false;
        }
        return fomatDate(s).getTime() >= fomatDate(e).getTime();
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 格式化日期
     *
     * @return
     */
    public static Date fomatDate(String date, String format) {
        DateFormat fmt = new SimpleDateFormat(format);
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // long aa=0;
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24))
                    / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        // System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        return getAfterDayDateWithFormat(days, sdf_str_time);
    }

    /**
     * 得到指定日期n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayByAppoint(Integer days, String day_num) {
       return getAfterDateByAppoint(fomatDate(day_num),days, sdf_str_date);
    }

    /**
     * 得到当前日期n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDateWithFormat(String days, String format) {
        int daysInt = Integer.parseInt(days);
        return getAfterDateByAppoint(null, daysInt, format);
    }

    /**
     * 得到指定日期n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDateByAppoint(Date appointDate, int days, String format) {
        Calendar canlendar = Calendar.getInstance(); // java.util包
        if (appointDate != null) {
            canlendar.setTime(appointDate);
        }
        canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat(format);
        String dateStr = sdfd.format(date);
        return dateStr;
    }


    /**
     * 得到n天之后是周几
     *
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    /**
     * 获取UTC时间
     *
     * @return
     */
    // public static String getUTCTime(){
    // //取得本地时间
    // final Calendar cal = Calendar.getInstance();
    // //获取偏移量
    // final int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
    // //取得夏令时差
    // final int dstOffset = cal.get(Calendar.DST_OFFSET);
    // cal.add(Calendar.MILLISECOND, -(zoneOffset+dstOffset));
    //
    // return sdfTime.format(cal.getTime());
    // }
    //
    // /**
    // * 将UTC时间转换为东八区时间
    // * @param utctime
    // * @return
    // */
    // public static String getLocalTimeFromUTC(String utctime){
    // Date utcdate = null;
    // try {
    // utcdate = sdfTime.parse(utctime);
    // sdfTime.setTimeZone(TimeZone.getTimeZone("GMT-8"));
    // } catch (ParseException e) {
    // e.printStackTrace();
    // }
    // return sdfTime.format(utcdate);
    // }

    /**
     * 字符转日期
     */
    public static Date formatDate(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat();
        return sdf.parse(str);
    }

    /**
     * 日期转字符
     */
    public static String formatDate(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            return "" + format;
        }
    }

    /**
     * 时间戳转时间
     */
    public static String formatTimeStamp(Long timeStamp, String format) {
        try {
            Date date = new Date(timeStamp);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            System.out.println("时间戳转换失败");
            return "";
        }
    }


    /**
     * 获取单个的String
     *
     * @param cha
     * @return
     */
    private static Integer char2Int(char cha) {
        return Integer.valueOf(cha + "");
    }

    /**
     * 获取当前时间
     * @param format 默认‘ yyyy-MM-dd HH:mm:ss’
     * @return
     */
    public static String getCurrentTime(String format) {
        String fmt;
        if (format == null || format.equals(""))
            fmt = "yyyy-MM-dd HH:mm:ss";
        else
            fmt = format;
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(new Date());
    }


    /**
     * 当天开始时间 0点时间
     * @return
     */
    public static String currentDayStartTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return formatDate(calendar.getTime(), sdf_str_time);
    }

    /**
     * 当天结束时间 24点时间
     * @return
     */
    public static String currentDayEndTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return formatDate(cal.getTime(), sdf_str_time);
    }


    public static String getTimesMonthmorning() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND,0);
        return formatDate(c.getTime(), sdf_str_time);
    }

    // 获得本月最后一天24点时间
    public static String getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return formatDate(cal.getTime(), sdf_str_time);
    }




    public static void main(String[] args) {
//        System.out.println("====  " + putDate("2015-12-30 10:20:30.0"));
//      System.out.println(enNum2CnNum("10000"));
//        System.out.println(putDzDate(1511348851));
//        Date date = new Date();
//        System.out.println(date.getTime());1511490707389
//        System.out.println(getDetailDate(getDzDate(1513699200), 'm'));
//        String code = "474554202f66617669636f6e2e69636f20485454502f312e310d0a486f73743a2066656e6770726f2e636f6d3a383038300d0a436f6e6e656374696f6e3a206b6565702d616c6976650d0a557365722d4167656e743a204d6f7a696c6c612f352e30202857696e646f7773204e5420362e313b2057696e36343b2078363429204170706c655765624b69742f3533372e333620284b48544d4c2c206c696b65204765636b6f29204368726f6d652f36382e302e333434302e313036205361666172692f3533372e33360d0a4163636570743a20696d6167652f776562702c696d6167652f61706e672c696d6167652f2a2c2a2f2a3b713d302e380d0a526566657265723a20687474703a2f2f66656e6770726f2e636f6d3a383038302f6879620d0a4163636570742d456e636f64696e673a20677a69702c206465666c6174650d0a4163636570742d4c616e67756167653a207a682d434e2c7a683b713d302e390d0a0d0a";
//        byte[] bytes = StringBytes.hexStringToByteArray(code);
//        System.out.println(new String(bytes));
        System.out.println(currentDayStartTime());
        System.out.println(currentDayEndTime());
        System.out.println(getTimesMonthmorning());
        System.out.println(getTimesMonthnight());
    }
}
