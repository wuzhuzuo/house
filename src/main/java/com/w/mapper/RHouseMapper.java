package com.w.mapper;

import com.w.entity.RHouse;

import java.util.List;

public interface RHouseMapper {
    int deleteByRhouse(Integer id);

    //通过房源编码添加经纪人跟踪
    void insertSelective(RHouse record);

    RHouse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RHouse record);

    //通过房源编码查询该房源下的经纪人
    List<RHouse> selectByList(Integer houseId);

    //通过经纪人编码查询该经纪人下的房源
    List<RHouse> selectByHouse(Integer busId);

    //删除该房源下关注的经纪人
    void deleteByHouseIdAndAgentId(RHouse rHouse);

    //通过房源编码查询该房源下的商家
    List<RHouse> selectByHouseIdAndBusId(RHouse rHouse);

    //查询去重后的商家
    List<RHouse> selectByDistinct(Integer houseId);


    //查询该商家下房源详情
    List<RHouse> selectByBusAndHouse(Integer busId, Integer type);

    //通过经纪人编码查询该经纪人下有多少房源
    List<RHouse> selectByAgentAndHouse(Integer agentId, Integer type);

    //通过商家和经纪人编码统计该经纪人下房源的数量
    Integer selectByAgentAndHouseType1(Integer busId, Integer agentId);

    Integer selectByAgentAndHouseType2(Integer busId, Integer agentId);

    Integer selectByAgentAndHouseType3(Integer busId, Integer agentId);
}