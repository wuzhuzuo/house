package com.w.service.impl;

import com.w.dto.QueryDto;
import com.w.entity.House;
import com.w.entity.MainApart;
import com.w.entity.RHouse;
import com.w.mapper.HouseMapper;
import com.w.mapper.MainApartMapper;
import com.w.mapper.RHouseMapper;
import com.w.service.HouseService;
import com.w.vo.ReturnHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:wu
 * @Date :create in 14:34 2020/4/6
 */
@Service("houseService")
public class HouseServiceImpl implements HouseService {


    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private RHouseMapper rHouseMapper;

    @Autowired
    private MainApartMapper mainApartMapper;

    @Override
    public int insertSelective(House record) {

        return houseMapper.insertSelective(record);
    }

    @Override
    public void updateByPrimaryKeySelective(House record) {
        houseMapper.updateByPrimaryKeySelective(record);
    }


    @Override
    public List<House> houseList(QueryDto queryDto) {
        return houseMapper.houseList(queryDto);
    }


    @Override
    public void updateIsdel(House house) {
        houseMapper.updateIsdel(house);
    }

    @Override
    public House selectByPrimaryKey(House house) {
        return houseMapper.selectByPrimaryKey(house);
    }

    @Override
    public List<RHouse> selectByList(Integer houseId) {
        return rHouseMapper.selectByList(houseId);
    }

    @Override
    public House selectByHouseId(Integer id) {
        return houseMapper.selectByHouseId(id);
    }

    /*<!--根据房屋类型统计数据（type1:新房，2:二手房，3:租房）-->*/
    @Override
    public Integer selectByType1(Integer busId) {
        return houseMapper.selectByType1(busId);
    }

    @Override
    public Integer selectByType2(Integer busId) {
        return houseMapper.selectByType2(busId);
    }

    @Override
    public Integer selectByType3(Integer busId) {
        return houseMapper.selectByType3(busId);
    }

    @Override
    public void deleteByHouseIdAndAgentId(RHouse rHouse) {
        rHouseMapper.deleteByHouseIdAndAgentId(rHouse);
    }

    //添加该房源下经纪人跟踪
    @Override
    public void insertByAgent(RHouse record) {
        rHouseMapper.insertSelective(record);
    }

    @Override
    public List<RHouse> selectByHouseIdAndBusId(RHouse rHouse) {
        return rHouseMapper.selectByHouseIdAndBusId(rHouse);
    }

    @Override
    public int deleteByRhouse(Integer id) {
        return rHouseMapper.deleteByRhouse(id);
    }

    //查询去重后的商家
    @Override
    public List<RHouse> selectByDistinct(Integer houseId) {
        return rHouseMapper.selectByDistinct(houseId);
    }

    @Override
    public List<MainApart> selectByMainApartAndhouseId(Integer id) {
        return mainApartMapper.selectByMainApartAndhouseId(id);
    }

    @Override
    public void insertByMainApart(MainApart record) {
        mainApartMapper.insertByMainApart(record);
    }

    @Override
    public void deleteByMainApart(Integer id) {
        mainApartMapper.deleteByMainApart(id);
        ;
    }

    @Override
    public List<RHouse> selectByHouse(Integer agentId) {
        return rHouseMapper.selectByHouse(agentId);
    }

    @Override
    public List<House> selectByIsRecommend(Integer isRecommend) {
        return houseMapper.selectByIsRecommend(isRecommend);
    }

    @Override
    public List<House> testItem(String price) {
        return houseMapper.testItem(price);
    }

    @Override
    public List<ReturnHouse> findByCountType1(String city) {
        return houseMapper.findByCountType1(city);
    }

    @Override
    public List<ReturnHouse> findByCountType2(String city) {
        return houseMapper.findByCountType2(city);
    }

    @Override
    public List<ReturnHouse> findByCountType3(String city) {
        return houseMapper.findByCountType3(city);
    }

    @Override
    public List<ReturnHouse> queryByDescTest(Integer id, Integer type) {
        return houseMapper.queryByDescTest(id, type);
    }


}
