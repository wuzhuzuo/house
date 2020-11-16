package com.w.test;

import org.springframework.util.StringUtils;

public class NumUtils {

    public static void main(String[] args) {
        String num = "十五";
        System.out.println(oldHouse(num));
        System.out.println(oldHouse(num.substring(0, 1)));
        String aa = "1/2/3/4/6/8/101";
        System.out.println(newHouse(aa));
        String test = "二";
        System.out.println("ifelse判断" + oldHouse(test));

        String chinese="二十一室";
        String jiequ=chinese.substring(0,chinese.indexOf("室"));
        System.out.println("截取"+jiequ);
    }

    public static  int newHouse(String text) {

        String  num =text.substring(text.lastIndexOf("/")+1);
        int bb = Integer.parseInt(num);
        return bb;
    }


    public static Integer oldHouse(String text) {
        int i = 0;
        if (!StringUtils.isEmpty(text)) {
            if (text.equals("一") || text.equals("1")) {
                i = 1;
                return i;
            }
            if (text.equals("二") || text.equals("2")) {
                i = 2;
                return i;
            }
            if (text.equals("三") || text.equals("3")) {
                i = 3;
                return i;
            }
            if (text.equals("四") || text.equals("4")) {
                i = 4;
                return i;
            }
            if (text.equals("五") || text.equals("5")) {
                i = 5;
                return i;
            }
            if (text.equals("六") || text.equals("6")) {
                i = 6;
                return i;
            }
            if (text.equals("七") || text.equals("7")) {
                i = 7;
                return i;
            }
            if (text.equals("八") || text.equals("8")) {
                i = 8;
                return i;
            }
            if (text.equals("九") || text.equals("9")) {
                i = 9;
                return i;
            }
            if (text.equals("十") || text.equals("10")) {
                i = 10;
                return i;
            }
            if (text.equals("十一") || text.equals("11")) {
                i = 11;
                return i;
            }
            if (text.equals("十二") || text.equals("12")) {
                i = 12;
                return i;
            }

        }
        return i;
    }

}
