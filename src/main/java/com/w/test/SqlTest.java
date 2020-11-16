package com.w.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:wu
 * @Date :create in 15:08 2020/6/10
 */
public class SqlTest {
    public static void main(String[] args) {
        String sql = "1000-2000";
        String sql123 = sqlprice(sql);
        System.out.println(sql123);
    }

    public static String sqlprice(String text) {
        if (text.contains("-∞")) {
            text = text.replaceAll("-∞", "0");
        }
        if (text.contains("∞")) {
            text = text.replaceAll("∞", "");
        }
        String[] arr = text.split(",");
        List<String> conditionList = new ArrayList<>();
        for (String a : arr) {
            String[] cc = a.split("-");
            String c;
            if (cc.length == 2) {
                c = cc[0] + " =< price and price <= " + cc[1];
            } else {
                c = cc[0] + " <= price ";
            }
            conditionList.add(c);
        }
        StringBuilder sb = new StringBuilder();
        if (conditionList.size() > 0) {
            for (int i = 0; i < conditionList.size(); i++) {
                sb.append(conditionList.get(i));
                if (i < conditionList.size() - 1) {
                    sb.append(" or ");
                }

            }
        }
        System.out.println("SQL拼接成：{}" + sb.toString());
        return sb.toString();
    }
    public static String sqlTotilPrice(String text) {
        if (text.contains("-∞")) {
            text = text.replaceAll("-∞", "0");
        }
        if (text.contains("∞")) {
            text = text.replaceAll("∞", "");
        }
        String[] arr = text.split(",");
        List<String> conditionList = new ArrayList<>();
        for (String a : arr) {
            String[] cc = a.split("-");
            String c;
            if (cc.length == 2) {
                c = cc[0] + " =< totil_price and totil_price <= " + cc[1];
            } else {
                c = cc[0] + " <= totil_price ";
            }
            conditionList.add(c);
        }
        StringBuilder sb = new StringBuilder();
        if (conditionList.size() > 0) {
            for (int i = 0; i < conditionList.size(); i++) {
                sb.append(conditionList.get(i));
                if (i < conditionList.size() - 1) {
                    sb.append(" or ");
                }

            }
        }
        System.out.println("SQL拼接成：totil_price{}" + sb.toString());
        return sb.toString();
    }

    public static String sqlMinFloorArea(String text) {
        if (text.contains("-∞")) {
            text = text.replaceAll("-∞", "0");
        }
        if (text.contains("∞")) {
            text = text.replaceAll("∞", "");
        }
        String[] arr = text.split(",");
        List<String> conditionList = new ArrayList<>();
        for (String a : arr) {
            String[] cc = a.split("-");
            String c;
            if (cc.length == 2) {
                c = cc[0] + " =< min_floorarea and min_floorarea <= " + cc[1];
            } else {
                c = cc[0] + " <= min_floorarea ";
            }
            conditionList.add(c);
        }
        StringBuilder sb = new StringBuilder();
        if (conditionList.size() > 0) {
            for (int i = 0; i < conditionList.size(); i++) {
                sb.append(conditionList.get(i));
                if (i < conditionList.size() - 1) {
                    sb.append(" or ");
                }

            }
        }
        System.out.println("SQL拼接成：min_floorarea{}" + sb.toString());
        return sb.toString();
    }
}
