package com.w.service;

import com.w.entity.Collect;
import com.w.entity.House;

import java.util.List;

public interface CollectService {

    void insertSelective(Collect record);

    void deleteByPrimaryKey(Integer id);

    List<House> selectByUser(String phone,Integer type);

    void updateByState(Collect collect);
    //查看用户是否关注该房源
    Collect selectByCollectHouse (Collect collect);

    //统计该商户有多少用户关注
    Integer selectByBusCount(Integer busId);

    //统计该商户下关注用户的信息
    List<String> selectByUserAndCollect(Collect collect);

    //统计用户关注该商家下的房源
    Integer selectByUserCount(Collect collect);

   /* //统计该房源被关注的总数*/
    List<Collect> selectByHouseCollect(Collect collect);

    //查询该房源被关注的
    List<Collect> selectByHouseCollectId(Integer houseId);

    //统计该房源有多少用户关注
    Integer queryByHouseCollect(Integer houseId);


}
