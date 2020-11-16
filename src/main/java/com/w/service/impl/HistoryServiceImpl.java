package com.w.service.impl;

import com.w.entity.History;
import com.w.mapper.HistoryMapper;
import com.w.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryMapper historyMapper;


    @Override
    public History selectByOpenIdAndHouseId(Map<String, Object> map) {
        return historyMapper.selectByOpenIdAndHouseId(map);
    }

    @Override
    public void updateByCreateTime(History history) {
        historyMapper.updateByCreateTime(history);
    }

    @Override
    public Integer selectBySum(Integer busId) {
        return historyMapper.selectBySum(busId);
    }

    @Override
    public Integer selectByRecordCount(Integer houseId) {
        return historyMapper.selectByRecordCount(houseId);
    }

    @Override
    public List<String> selectByUserList(Integer busId) {
        return historyMapper.selectByUserList(busId);
    }

    @Override
    public Integer selectByUserAndRecordCount(History history) {
        return historyMapper.selectByUserAndRecordCount(history);
    }

    @Override
    public List<History> selectByPhoneAndBusId(History history) {
        return historyMapper.selectByPhoneAndBusId(history);
    }

    @Override
    public List<History> selectByHouseHistoryId(Integer houseId) {
        return historyMapper.selectByHouseHistoryId(houseId);
    }

    @Override
    public Integer selectByCount(Integer busId) {
        return historyMapper.selectByCount(busId);
    }

    @Override
    public Integer selectByUserPhoneAndBusId(Integer busId, String phone) {
        return historyMapper.selectByUserPhoneAndBusId(busId, phone);
    }

    @Override
    public History selectByDetailCount(Integer houseId, String phone) {
        return historyMapper.selectByDetailCount(houseId, phone);
    }

    @Override
    public Integer selectByBrowseCount(Integer busId) {
        return historyMapper.selectByBrowseCount(busId);
    }

    @Override
    public Integer selectBySumBrowseCount(Integer busId, Integer agentId) {
        return historyMapper.selectBySumBrowseCount(busId, agentId);
    }


    @Override
    public void deleteByPrimaryKey(Integer id) {
        historyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(History record) {
        historyMapper.insertSelective(record);
    }

    @Override
    public List<History> selectByOpenId(History history) {
        return historyMapper.selectByOpenId(history);
    }
}
