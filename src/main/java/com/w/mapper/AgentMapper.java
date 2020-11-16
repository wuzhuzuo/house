package com.w.mapper;

import com.w.entity.Agent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AgentMapper {
    void deleteByPrimaryKey(Agent agent);


    void insertSelective(Agent record);

    Agent selectByPrimaryKey(Agent agent);

    void updateByPrimaryKeySelective(Agent record);

    List<Agent> list(Agent agent);


    Agent selectByAgentId(Integer id);

    //通过手机号查询经纪人 改手机号允许注册一次经纪人
    Agent selectByPhone(@Param("phone") String phone);



}