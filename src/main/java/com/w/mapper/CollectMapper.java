package com.w.mapper;

import com.w.entity.Collect;

import java.util.List;

public interface CollectMapper {
    void deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    void insertSelective(Collect record);

    Collect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    //取消关注
    void updateByState(Collect collect);

    //查看用户是否关注该房源
    Collect selectByCollectHouse(Collect collect);

    //统计该商户有多少用户关注
    Integer selectByBusCount(Integer busId);

    //统计该商户下关注用户的信息
    List<String> selectByUserAndCollect(Collect collect);

    //统计用户关注该商家下的房源
    Integer selectByUserCount(Collect collect);

    //统计该房源被关注的总数
    List<Collect> selectByHouseCollect(Collect collect);

    //查询该房源被关注的
    List<Collect> selectByHouseCollectId(Integer houseId);

    //统计该房源有多少用户关注
    Integer queryByHouseCollect(Integer houseId);


    /*用户收藏了该经纪人下的几套房源*/
    Integer selectByAgentAndCollect(Integer busId, Integer agentId, String phone);


}