package com.w.test;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:wu
 * @Date :create in 8:45 2020/4/1
 */
@Slf4j
public class PicTest {

  /* public static void main(String[] args) {
        String aa="a,c,d,f";

        List list= Arrays.asList(aa.split(","));
        log.info("{}",list);
        String arr="1,2,3,4";
        //int [] ints= stringConvertInt(arr);
        List ints=stringConvertInt(arr);
        System.out.println(ints.toString());

    }*/
    public    List stringConvertInt(String value) {
        int[] intArr = new int[0];
        List list=new ArrayList();
        if(isNull(value)){
            list.add(0);
            intArr = new int[0];
        }else{
            String[] valueArr = value.split(",");
            intArr = new int[valueArr.length];
            for (int i = 0; i < valueArr.length; i++) {
                intArr[i] = Integer.parseInt(valueArr[i]);
                int j= Integer.parseInt(valueArr[i]);
                list.add(j);
            }
        }
        return list;
    }

    private static boolean isNull(String param){
        if(param==null||param.isEmpty()||param.trim().equals("")) return true;
        return false;
    }



}
