package com.w.mapper;

import com.w.entity.Business;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BusinessMapper {
    void deleteByPrimaryKey(Business business);


    void insertSelective(Business record);

    Business selectByPrimaryKey(Business business);

    void updateByPrimaryKeySelective(Business record);


    List<Business> list(Map map);

    Business selectByBusPhone(@Param("phone") String phone);
}