package com.w.service;

import com.w.dto.QueryDto;
import com.w.entity.House;
import com.w.entity.MainApart;
import com.w.entity.RHouse;
import com.w.vo.ReturnHouse;

import java.util.List;

/**
 * @Author:wu
 * @Date :create in 14:33 2020/4/6
 */
public interface HouseService {

    int insertSelective(House record);

    void updateByPrimaryKeySelective(House record);


    List<House> houseList(QueryDto queryDto);

    void updateIsdel(House house);

    House selectByPrimaryKey(House house);

    List<RHouse> selectByList(Integer houseId);

    House selectByHouseId(Integer id);

    /*查询该商户下的房源类型总数*/
    Integer selectByType1(Integer busId);

    Integer selectByType2(Integer busId);

    Integer selectByType3(Integer busId);

    //删除关注该房源的经纪人
    void deleteByHouseIdAndAgentId(RHouse rHouse);

    //通过房源编码添加经纪人跟踪
    void insertByAgent(RHouse record);

    //通过房源编码查询该房源下的商家
    List<RHouse> selectByHouseIdAndBusId(RHouse rHouse);

    //通过商家和房源删除商家关注的房源
    int deleteByRhouse(Integer id);

    //查询去重后的商家
    List<RHouse> selectByDistinct(Integer houseId);

    //查询该房源的主力户型
    List<MainApart> selectByMainApartAndhouseId(Integer id);

    //新增主力户型
    void insertByMainApart(MainApart record);

    //删除主力户型
    void deleteByMainApart(Integer id);

    //通过经纪人编码查询该经纪人下的房源
    List<RHouse> selectByHouse(Integer agentId);

    //查询推荐房源
    List<House> selectByIsRecommend(Integer isRecommend);


    /*!--
    测试 数组条件查询的sql-->*/
    List<House> testItem(String price);

    //首页使用
    List<ReturnHouse> findByCountType1(String city);

    List<ReturnHouse> findByCountType2(String city);

    List<ReturnHouse> findByCountType3(String city);

    //排序测试
    List<ReturnHouse> queryByDescTest(Integer id,Integer type);
}
