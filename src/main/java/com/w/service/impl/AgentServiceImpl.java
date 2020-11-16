package com.w.service.impl;

import com.w.entity.Agent;
import com.w.entity.History;
import com.w.entity.House;
import com.w.entity.RHouse;
import com.w.mapper.*;
import com.w.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("agentService")
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentMapper mapper;
    @Autowired
    private RHouseMapper rHouseMapper;
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private HouseMapper houseMapper;

    @Override
    public void insertSelective(Agent record) {
        mapper.insertSelective(record);
    }

    @Override
    public void updateByPrimaryKeySelective(Agent record) {
        mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void deleteByPrimaryKey(Agent agent) {
        mapper.deleteByPrimaryKey(agent);
    }

    @Override
    public List<Agent> list(Agent agent) {
        return mapper.list(agent);
    }

    @Override
    public Agent selectByAgentId(Integer id) {
        return mapper.selectByAgentId(id);
    }

    @Override
    public Agent selectByPrimaryKey(Agent agent) {
        return mapper.selectByPrimaryKey(agent);
    }

    @Override
    public List<RHouse> selectByHouse(Integer busId) {
        return rHouseMapper.selectByHouse(busId);
    }

    @Override
    public List<RHouse> selectByBusAndHouse(Integer busId, Integer type) {
        return rHouseMapper.selectByBusAndHouse(busId, type);
    }

    @Override
    public Agent selectByPhone(String phone) {
        return mapper.selectByPhone(phone);
    }

    @Override
    public List<RHouse> selectByAgentAndHouse(Integer agentId, Integer type) {
        return rHouseMapper.selectByAgentAndHouse(agentId, type);
    }

    @Override
    public Integer selectByAgentAndHouseType1(Integer busId, Integer agentId) {
        return rHouseMapper.selectByAgentAndHouseType1(busId, agentId);
    }

    @Override
    public Integer selectByAgentAndHouseType2(Integer busId, Integer agentId) {
        return rHouseMapper.selectByAgentAndHouseType2(busId, agentId);
    }

    @Override
    public Integer selectByAgentAndHouseType3(Integer busId, Integer agentId) {
        return rHouseMapper.selectByAgentAndHouseType3(busId, agentId);
    }

    @Override
    public List<String> selectByUserAndAgent(Integer busId, Integer agentId) {
        return historyMapper.selectByUserAndAgent(busId, agentId);
    }

    @Override
    public Integer selectByAgentAndUserSum(Integer busId, Integer agentId, String phone) {
        return historyMapper.selectByAgentAndUserSum(busId, agentId, phone);
    }

    @Override
    public Integer selectByAgentAndCollect(Integer busId, Integer agentId, String phone) {
        return collectMapper.selectByAgentAndCollect(busId, agentId, phone);
    }

    @Override
    public List<History> selectByAgentAndUserAndHouse(Integer busId, Integer agentId, String phone) {
        return historyMapper.selectByAgentAndUserAndHouse(busId, agentId, phone);
    }

    @Override
    public List<House> selectByCollectAndAgent(Integer busId, Integer agentId, String phone) {
        return houseMapper.selectByCollectAndAgent(busId, agentId, phone);
    }


}
