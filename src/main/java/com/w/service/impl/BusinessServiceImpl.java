package com.w.service.impl;


import com.w.entity.Business;
import com.w.mapper.BusinessMapper;
import com.w.mapper.RHouseMapper;
import com.w.service.BusinessService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author:wu
 * @Date :create in 14:24 2020/3/31
 */
@Slf4j
@Service("businessService")
public class BusinessServiceImpl implements BusinessService {


    @Autowired
    private BusinessMapper businessMapper;
    @Autowired
    private RHouseMapper rhouseMapper;

    @Override
    public void insertSelective(Business record) {
        businessMapper.insertSelective(record);

    }

    @Override
    public List<Business> list(Map map) {
        return businessMapper.list(map);
    }

    @Override
    public Business selectByPrimaryKey(Business business) {
        return businessMapper.selectByPrimaryKey(business);
    }

    @Override
    public void updateByPrimaryKeySelective(Business record) {
        businessMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void deleteByPrimaryKey(Business business) {
        businessMapper.deleteByPrimaryKey(business);
    }

    @Override
    public Business selectByBusPhone(String phone) {
        return businessMapper.selectByBusPhone(phone);
        //Business selectByBusPhone(@Param("phone") String phone);
    }


}
