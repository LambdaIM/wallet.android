package com.lambda.wallet.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringUtils {
    /**
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
     * 小数点后面保留6位,如果是0就直接取0
     * 如果小数点后面不够6位 根据长度动态保留位数
     * 直接筛掉小数点后面的0。
     *
     * @param str 需要处理的字符串
     * @return 处理完之后的字符串 string
     */
    public static String addComma(String str) {
        DecimalFormat decimalFormat;
        String pattern = ",###.";
        if (str.contains(".")) {
            if (str.split("\\.")[0].equals("0") && str.split("\\.")[1].length() == 0) {
                decimalFormat = new DecimalFormat("0");
            } else if (str.split("\\.")[0].equals("0") && str.split("\\.")[1].length() != 0) {
                decimalFormat = new DecimalFormat("0.000000");
            } else if (!str.split("\\.")[0].equals("0") && str.split("\\.")[1].length() < 6) {
                for (int i = 0; i < str.split("\\.")[1].length(); i++) {
                    pattern += "0";
                }
                decimalFormat = new DecimalFormat(pattern);
            } else {
                decimalFormat = new DecimalFormat(",###.000000");
            }
        } else {
            decimalFormat = new DecimalFormat(",###");
        }
        return RegexUtil.subZeroAndDot(decimalFormat.format(Double.parseDouble(str)));
    }


    /**
     * 小数点后四位不足补0
     *
     * @param str   the str
     * @param scale the scale
     * @return the string
     */
    public static String addZero(String str, int scale) {
        DecimalFormat decimalFormat;
        String pattern = "###.";
        for (int i = 0; i < scale; i++) {
            pattern += "0";
        }
        if (str.split("\\.")[0].equals("0")) {
            pattern = "0.";
            for (int i = 0; i < scale; i++) {
                pattern += "0";
            }
            decimalFormat = new DecimalFormat(pattern);
        } else if (scale == 0) {//如果要求数值为整数，则不保存小数点后面数值
            pattern = "###";
            decimalFormat = new DecimalFormat(pattern);
        } else {
            decimalFormat = new DecimalFormat(pattern);
        }
        return decimalFormat.format(Double.parseDouble(str));
    }

    /**
     * 去除小数点后面的0
     *
     * @param str 需要处理的字符串
     * @return 处理完之后的字符串 string
     */
    public static String deletzero(String str) {
        DecimalFormat decimalFormat;
        String pattern = "###.";
        if (!str.contains(".")){
            return str;
        }
        if (str.split("\\.")[0].equals("0")) {
            decimalFormat = new DecimalFormat("0.000000");
        } else if (str.split("\\.")[1].length() <= 6) {
            for (int i = 0; i < str.split("\\.")[1].length(); i++) {
                pattern += "0";
            }
            decimalFormat = new DecimalFormat(pattern);
        } else {
            decimalFormat = new DecimalFormat("###.000000");
        }
        return RegexUtil.subZeroAndDot(decimalFormat.format(Double.parseDouble(str)));
    }

    /**
     * String sort string [ ].
     * 字符串数组排序按照字母进行
     *
     * @param s the s
     * @return the string [ ]
     */
    public static String[] stringSort(String[] s) {
        List<String> list = new ArrayList<String>(s.length);
        for (int i = 0; i < s.length; i++) {
            list.add(s[i]);
        }
        Collections.sort(list);
        return list.toArray(s);
    }

    /**
     * Get string string.
     * s是需要删除某个子串的字符串s1是需要删除的子串
     *
     * @param s  the s
     * @param s1 the s 1
     * @return the string
     */
    public static String getString(String s, String s1) {
        int postion = s.indexOf(s1);
        int length = s1.length();
        int Length = s.length();
        String newString = s.substring(0, postion) + s.substring(postion + length, Length);
        return newString;//返回已经删除好的字符串
    }

    /**
     * Lambda address string.
     *
     * @param address the address
     * @return the string
     */
    public static String lambdaAddress(String address) {
        try {
            String one = address.substring(0, 10);
            String two = address.substring(address.length() - 6, address.length());
            String newString = one + "..." + two;
            return newString;//返回已经删除好的字符串
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String lambdaToken(String token) {
        try {
            String token1 = token.replace("u","");
            return token1.toUpperCase();//返回已经删除好的字符串
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}