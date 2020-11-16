package com.w.service.impl;

import com.w.entity.Collect;
import com.w.entity.House;
import com.w.mapper.CollectMapper;
import com.w.mapper.HouseMapper;
import com.w.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("collectService")
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private HouseMapper houseMapper;

    @Override
    public void insertSelective(Collect collect) {
        collectMapper.insertSelective(collect);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        collectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<House> selectByUser(String phone,Integer type) {
        return houseMapper.selectByUser(phone,type);
    }

    @Override
    public void updateByState(Collect collect) {
        collectMapper.updateByState(collect);
    }

    @Override
    public Collect selectByCollectHouse(Collect collect) {
        return collectMapper.selectByCollectHouse(collect);
    }

    @Override
    public Integer selectByBusCount(Integer busId) {
        return collectMapper.selectByBusCount(busId);
    }

    @Override
    public List<String> selectByUserAndCollect(Collect collect) {
        return collectMapper.selectByUserAndCollect(collect);
    }

    @Override
    public Integer selectByUserCount(Collect collect) {
        return collectMapper.selectByUserCount(collect);
    }

    @Override
    public List<Collect> selectByHouseCollect(Collect collect) {
        return collectMapper.selectByHouseCollect(collect);
    }

    @Override
    public List<Collect> selectByHouseCollectId(Integer houseId) {
        return collectMapper.selectByHouseCollectId(houseId);
    }

    @Override
    public Integer queryByHouseCollect(Integer houseId) {
        return collectMapper.queryByHouseCollect(houseId);
    }

}
