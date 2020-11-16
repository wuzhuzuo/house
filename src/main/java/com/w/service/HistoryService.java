package com.w.service;

import com.w.entity.History;

import java.util.List;
import java.util.Map;

public interface HistoryService {

    void deleteByPrimaryKey(Integer id);

    void insertSelective(History record);

    List<History> selectByOpenId(History history);

    History selectByOpenIdAndHouseId(Map<String, Object> map);

    void updateByCreateTime(History history);

    //统计浏览总数
    Integer selectBySum(Integer busId);

    //统计该房源浏览总数
    Integer selectByRecordCount(Integer houseId);

    //统计该商户下浏览房源用户信息
    List<String> selectByUserList(Integer busId);

    //统计用户浏览该商家房源次数
    Integer selectByUserAndRecordCount(History history);

    //通过手机号和商家编号查询
    List<History> selectByPhoneAndBusId(History history);

    //通过houseId查询查询浏览
    List<History> selectByHouseHistoryId(Integer houseId);

    //统计该商户下有多少用户浏览
    Integer selectByCount(Integer busId);

    //统计用户浏览商家下面多少房源
    Integer selectByUserPhoneAndBusId(Integer busId, String phone);

    //查询该用户浏览该房源多少次
    History selectByDetailCount(Integer houseId, String phone);

    //统计该商户下的总浏览数

    Integer selectByBrowseCount(Integer busId);

    //统计经纪人下有浏览房源次数
    Integer selectBySumBrowseCount(Integer busId, Integer agentId);


}
