package com.mszx.hyb;

import com.github.binarywang.java.emoji.EmojiConverter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Test {
    public static void main(String[] args) {
//        for (int j = 0; j < 10; j++) {
//            System.out.println("************************" +j+ "********************************************");
//            BigDecimal total_discount = new BigDecimal("8");
//            int diff_people = 4;
//            int total_people = diff_people;
//            BigDecimal diff = new BigDecimal("8");
//            for (int i = 0; i < total_people-1; i++) {
//                BigDecimal average = diff.divide(new BigDecimal(diff_people), 2, BigDecimal.ROUND_HALF_DOWN);
//                BigDecimal random = random(average.divide(new BigDecimal("2")).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
//                BigDecimal current = average.add(random);
//
//                diff = diff.subtract(current);
//                System.out.println("第" + (i + 1) + "个人价格：" + current.setScale(2,BigDecimal.ROUND_HALF_DOWN));
////            System.out.println(random +" " + average.add(random));
//                diff_people = diff_people - 1;
//            }
//            System.out.println("第5个人价格：" + diff);
//        }
        String name = "夜神月";
        String p = EmojiConverter.getInstance().toUnicode(name);
        System.out.println(EmojiConverter.getInstance().toAlias((name)));
        System.out.println(EmojiConverter.getInstance().toAlias(p));
    }


    public static BigDecimal random(double d) {
        double a = d * Math.random();
        String b = new DecimalFormat("00.000").format(a);
        BigDecimal c = new BigDecimal(b).multiply(new BigDecimal((Math.random() > 0.5 ? 1 : -1)));
        return c;
    }


    public static int bytes2int(byte[] b) {
        int mask = 255;
        boolean temp = false;
        int res = 0;

        for (int i = 0; i < 4; ++i) {
            res <<= 8;
            int temp1 = b[i] & mask;
            res |= temp1;
        }

        return res;
    }

    private static String deletePreZero(String s) {
        while (s.startsWith("0")) {
            s = s.substring(1, s.length());
        }
        return s;
    }

    public static byte[] hexStringToByte(String hex) {
        int len = hex.length() / 2;
        byte[] result = new byte[len];
        char[] achar = hex.toUpperCase().toCharArray();

        for (int i = 0; i < len; ++i) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }

        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

}
