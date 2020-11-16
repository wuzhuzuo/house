package com.w.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToString {

    public static void main(String[] args) throws Exception {
     String str="2020-05-05";

    Date date = dateToString(str);
    String test1=stringtodate(date);
        System.out.println("test`11"+test1);
        String date12 = "2010年01月01日";
        Date date1 = cnToDate(date12);
        System.out.println(date1);

    }

    public static Date dateToString(String test) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(test);
        System.out.println(date);
        return date;


    }

    public static String  stringtodate(Date date) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = ft.format(date);
        System.out.println("date1" + date1);
        return  date1;
    }

    public static Date cnToDate(String cnDate) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date date1 = format.parse(cnDate);
        return date1;
    }
}
