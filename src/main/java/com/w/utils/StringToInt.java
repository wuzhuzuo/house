package com.w.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class StringToInt {
       public Map chToString(String text) {
           log.info("户型查询入参{}",text);
        Map map = new HashMap();
        if (text.contains("+")) {
            text = text.replaceAll("[\u4e00-\u9fa5]", "");
            String aa = text.replaceAll(",", "|");
            Integer cc = Integer.parseInt(aa.substring(aa.length() - 2, aa.length()-1));
            log.info("户型查询转换{}",aa);
            map.put("apart", aa);
            map.put("apartFilter", cc);
        } else {
            text = text.replaceAll("[\u4e00-\u9fa5]", "");
            String aa = text.replaceAll(",", "|");
            map.put("apart", aa);
        }
        return map;
    }

    public Map chToString1(String text) {
        Map map = new HashMap();
        if (text.contains("+")) {
            String aa = text.replaceAll(",", "|");
            Integer cc = Integer.parseInt(aa.substring(aa.length() - 3, aa.length()-2));
            map.put("apart", aa);
            map.put("apartFilter", cc);
        } else {
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