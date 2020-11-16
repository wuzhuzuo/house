package com.w.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    //带汉字的字符串转换为date格式
    public Date stringToDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse(date);
        System.out.println(date);
        return date1;
    }

    public String dateToString(Date date) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = ft.format(date);
        return date1;
    }
    public Date cnToDate(String cnDate) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date date1 = format.parse(cnDate);
        return date1;
    }
 /*
*/

}
