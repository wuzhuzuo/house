package com.w.test;

import java.util.List;

public class ListToString {
    public String listToString(List list,char separator){
       StringBuilder sb=new StringBuilder();
       for(int i=0;i<list.size();i++){
           sb.append(list.get(i));
           if(i<list.size()-1){
               sb.append(separator);
           }
       }
        return  sb.toString();
    }


}
