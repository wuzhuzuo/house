package com.w.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:wu
 * @Date :create in 15:54 2020/6/10
 */
public class StringTest {

    public static void main(String[] args) {
        String part="1室,2室,3室+";
        chToString(part);
        System.out.println("房型不为1的："+chToString(part));

        System.out.println("+++++++++++++");
        System.out.println("房型为1的："+ chToString1(part));
    }
    public static Map chToString(String text) {
        Map map = new HashMap();
        if (text.contains("+")) {
            String aa = text.replaceAll(",", "|");
            System.out.println(aa);
            Integer cc = Integer.parseInt(aa.substring(aa.length() - 3, aa.length()-2));
            map.put("apart", aa);
            map.put("apartFilter", cc);

        } else {
            String aa = text.replaceAll(",", "|");
            map.put("apart", aa);
        }
        return map;
    }
    public static Map chToString1(String text) {
        Map map = new HashMap();
        if (text.contains("+")) {
             text = text.replaceAll("[\u4e00-\u9fa5]", "");
            String aa = text.replaceAll(",", "|");
            System.out.println(aa);
            Integer cc = Integer.parseInt(aa.substring(aa.length() - 2, aa.length()-1));
            map.put("apart", aa);
            map.put("apartFilter", cc);

        } else {
            text = text.replaceAll("[\u4e00-\u9fa5]", "");
            String aa = text.replaceAll(",", "|");
            map.put("apart", aa);
        }
        return map;
    }

    public   Integer lastNum(String text) {
        String num = text.substring(text.lastIndexOf("/") + 1);
        int bb = Integer.parseInt(num);
        return bb;
    }

    public Integer firstNum(String text) {


        Integer num = Integer.parseInt(text.substring(0, text.indexOf("室")));
        return num;
    }
}
