package com.w.service;

import com.w.entity.Agent;
import com.w.entity.History;
import com.w.entity.House;
import com.w.entity.RHouse;

import java.util.List;

public interface AgentService {

    void insertSelective(Agent agent);

    void updateByPrimaryKeySelective(Agent record);

    void deleteByPrimaryKey(Agent agent);

    List<Agent> list(Agent agent);

    Agent selectByAgentId(Integer id);

    Agent selectByPrimaryKey(Agent agent);

    List<RHouse> selectByHouse(Integer busId);


    //查询该商家下房源详情
    List<RHouse> selectByBusAndHouse(Integer busId, Integer type);
    //通过手机号查询经纪人 改手机号允许注册一次经纪人
    Agent selectByPhone(String phone);


    //通过经纪人编码查询该经纪人下有多少房源
    List<RHouse>  selectByAgentAndHouse(Integer agentId, Integer type);

    //通过商家和经纪人编码统计该经纪人下房源的数量
    Integer selectByAgentAndHouseType1(Integer busId, Integer agentId);

    Integer selectByAgentAndHouseType2(Integer busId, Integer agentId);

    Integer selectByAgentAndHouseType3(Integer busId, Integer agentId);

    //查询用户浏览该经纪人下的房源
    List<String> selectByUserAndAgent(Integer busId,Integer agentId);

    //统计用户浏览该经纪人下的房源的次数
    Integer selectByAgentAndUserSum(Integer busId,Integer agentId,String phone);


    /*用户收藏了该经纪人下的几套房源*/
    Integer selectByAgentAndCollect(Integer busId, Integer agentId, String phone);


    /* <!--查询用户浏览该经纪人下的哪些房源-->*/
    List<History> selectByAgentAndUserAndHouse(Integer busId, Integer agentId, String phone);

    //查询用户关注经纪人的房源
    List<House> selectByCollectAndAgent(Integer busId, Integer agentId, String phone);

}
