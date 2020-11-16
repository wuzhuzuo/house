package com.w.service;

import com.w.entity.Business;

import java.util.List;
import java.util.Map;

/**
 * @Author:wu
 * @Date :create in 14:23 2020/3/31
 */
public interface BusinessService {


    void insertSelective(Business record);

    List<Business> list(Map map);

    Business selectByPrimaryKey(Business business);

    void updateByPrimaryKeySelective(Business record);


    void deleteByPrimaryKey(Business business);

    /*

        List<RHouse> queryByDetil(Map<String,Object> queryMap);
    */
    Business selectByBusPhone(String phone);

}
