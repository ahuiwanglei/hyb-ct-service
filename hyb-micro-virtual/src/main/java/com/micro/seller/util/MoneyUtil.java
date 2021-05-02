package com.micro.seller.util;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author
 * @desc 金额转换
 * @Copyright: (c) 2016年4月9日 下午5:21:03
 * @company: 民生在线
 */
public class MoneyUtil {
    private static final BigDecimal PRICE_DEFAULT_NULL = new BigDecimal(0); // 金额为空时,默认0
    private static final String PRICE_FRACTION_ONE = "0.0"; // 保留1位小数
    private static final String PRICE_FRACTION_TWO = "0.00"; // 保留2位小数
    private static final String PRICE_FRACTION_THREE = "0.000"; // 保留3位小数
    private static final String PRICE_FRACTION_FOUR = "0.0000"; // 保留4位小数
    private static final String PRICE_NO_FRACTION = "0"; // 不存在小数点


    /**
     * Object转BigDecimal
     *
     * @param o
     * @return
     */
    public static BigDecimal objectToBigDecimalNoException(Object o) {
        BigDecimal result = null;
        try {
            if (o == null)
                result = PRICE_DEFAULT_NULL;
            else
                result = new BigDecimal(String.valueOf(o));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 保留1位小数
     *
     * @param price
     * @return
     */
    public static BigDecimal getFractionOneNoException(BigDecimal price) {
        BigDecimal result = PRICE_DEFAULT_NULL;
        try {
            DecimalFormat df = new DecimalFormat(PRICE_FRACTION_ONE);
            df.setRoundingMode(RoundingMode.FLOOR);
            result = new BigDecimal(df.format(price));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 保留2位小数
     *
     * @param price
     * @return
     */
    public static BigDecimal getFractionTwoNoException(BigDecimal price) {
        BigDecimal result = PRICE_DEFAULT_NULL;
        try {
            DecimalFormat df = new DecimalFormat(PRICE_FRACTION_TWO);
            df.setRoundingMode(RoundingMode.FLOOR);
            result = new BigDecimal(df.format(price));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String getFractionTwoNoExceptionDelZero(BigDecimal price) {
        return getFractionTwoNoException(price).stripTrailingZeros().toPlainString();
    }


    /**
     * 保留3位小数
     *
     * @param price
     * @return
     */
    public static BigDecimal getFractionThreeNoException(BigDecimal price) {
        BigDecimal result = PRICE_DEFAULT_NULL;
        try {
            DecimalFormat df = new DecimalFormat(PRICE_FRACTION_THREE);
            df.setRoundingMode(RoundingMode.FLOOR);
            result = new BigDecimal(df.format(price));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 保留2位小数:四舍五入
     *
     * @param price
     * @return
     */
    public static BigDecimal getFractionTwoHalfUpNoException(BigDecimal price) {
        BigDecimal result = PRICE_DEFAULT_NULL;
        try {
            DecimalFormat df = new DecimalFormat(PRICE_FRACTION_TWO);
            df.setRoundingMode(RoundingMode.HALF_UP);
            result = new BigDecimal(df.format(price));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 返回BigDecimal类型(1位小数)
     *
     * @param price
     * @return
     */
    public static BigDecimal getFractionOneBD(BigDecimal price) {
        BigDecimal result = new BigDecimal(0);
        try {
            DecimalFormat df = new DecimalFormat(PRICE_FRACTION_ONE);
            df.setRoundingMode(RoundingMode.FLOOR);
            result = new BigDecimal(df.format(price));
        } catch (Exception e) {
            System.out.println("price:" + price);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * BigDecimal转换为整数
     *
     * @param price
     * @return
     */
    public static int getNoFractionUpInt(BigDecimal price) {
        int result = 0;
        try {
            DecimalFormat df = new DecimalFormat(PRICE_NO_FRACTION);
            df.setRoundingMode(RoundingMode.UP);
            result = Integer.valueOf(String.valueOf(df.format(price)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static BigDecimal getNoFraction(BigDecimal price) {
        BigDecimal result = new BigDecimal(0);
        try {
            DecimalFormat df = new DecimalFormat(PRICE_NO_FRACTION);
            df.setRoundingMode(RoundingMode.FLOOR);
            result = new BigDecimal(df.format(price));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 是否金额为0
     *
     * @param price
     * @return
     */
    public static boolean isMoneyNotZero(String price) {
        if (price == null || "".equals(price.trim()) || "".equals(price))
            return false;
        else
            return true;
    }

    /**
     * 是否金额为0
     *
     * @param price
     * @return
     */
    public static boolean isMoneyNotZero(BigDecimal price) {
        if (price == null || price.compareTo(new BigDecimal(0)) == 0)
            return false;
        else
            return true;
    }

    /**
     * 是否>0 且<=1
     *
     * @param value
     * @return
     */
    public static boolean isValueBetweenZeroAndOne(BigDecimal value) {
        if (value != null && value.compareTo(new BigDecimal(0)) == 1 && value.compareTo(new BigDecimal(1)) < 1)
            return true;
        else
            return false;
    }

    /**
     * 是否大于0
     *
     * @param value
     * @return true 大于0, false 不大于0
     */
    public static boolean isMorethanZero(BigDecimal value) {
        if (value != null && value.compareTo(new BigDecimal("0")) == 1)
            return true;
        else
            return false;
    }

    /**
     * 是否为金额,且大于0
     *
     * @param o
     * @return
     */
    public static boolean isMoneyMorethanZero(Object o) {
        boolean flag = false;
        try {
            BigDecimal money = new BigDecimal(String.valueOf(o));
            if (money.compareTo(new BigDecimal("0")) == 1)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 比较前面金额是否大于后面金额:前面大于后面返回true,否则返回false
     *
     * @param amount_before
     * @return
     */
    public static boolean isAmountBeforeMorethanAfter(BigDecimal amount_before, BigDecimal amount_after) {
        if (amount_before.compareTo(amount_after) == 1)
            return true;
        else
            return false;
    }

    /**
     * 比较前面金额是否大于等于后面金额:前面大于后面返回true,否则返回false
     *
     * @param amount_before
     * @return
     */
    public static boolean isAmountBeforeMorethanOrSameAfter(BigDecimal amount_before, BigDecimal amount_after) {
        if (amount_before.compareTo(amount_after) == -1)
            return false;
        else
            return true;
    }

    /**
     * 根据年化率计算总收益
     * @param amount
     * @param days
     * @param rate  计算每天的收益 最后在乘以天数
     * @return
     */
    public static BigDecimal getIncome(BigDecimal amount, Integer days, BigDecimal rate) {
        BigDecimal incomemoney = new BigDecimal("0.0");
        BigDecimal day = new BigDecimal(days);
        incomemoney = rate.multiply(new BigDecimal(1));
        incomemoney = incomemoney.multiply(amount);
        incomemoney = incomemoney.divide(new BigDecimal("365.0"), 3, BigDecimal.ROUND_HALF_EVEN);
        incomemoney = incomemoney.divide(new BigDecimal("100.0"), 3, BigDecimal.ROUND_HALF_EVEN);
        incomemoney = incomemoney.setScale(3, BigDecimal.ROUND_DOWN);
        return MoneyUtil.getFractionTwoNoException(incomemoney.multiply(day));
    }


    public static final void main(String[] args){
        BigDecimal a = getIncome(new BigDecimal(5000000), 1, new BigDecimal(3));
        System.out.println(a);
    }
}
