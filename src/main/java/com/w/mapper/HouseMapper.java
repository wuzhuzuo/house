package com.w.mapper;

import com.w.dto.QueryDto;
import com.w.entity.House;
import com.w.entity.RHouse;
import com.w.vo.ReturnHouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HouseMapper {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(House record);

    House selectByPrimaryKey(House house);

    void updateByPrimaryKeySelective(House record);


    //条件查询
    List<House> houseList(@Param("queryDto") QueryDto queryDto);

    //查询推荐房源
    List<House> selectByIsRecommend(Integer isRecommend);

    void updateIsdel(House house);

    List<House> selectByAgent(RHouse rHouse);

    List<Map> selectByType(RHouse rHouse);

    /*查询该房源下有多少经纪人*/
    List<House> selectByHouse(Integer id);

    House selectByHouseId(Integer id);

    /*查询该商户下的房源类型总数*/
    Integer selectByType1(Integer busId);

    Integer selectByType2(Integer busId);

    Integer selectByType3(Integer busId);

    List<House> selectByUser(String phone, Integer type);

    /*!--
        测试 数组条件查询的sql-->*/
    List<House> testItem(@Param("item") String price);

    //首页使用
    List<ReturnHouse> findByCountType1(@Param("city") String city);

    List<ReturnHouse> findByCountType2(@Param("city") String city);

    List<ReturnHouse> findByCountType3(@Param("city") String city);

    //排序测试
    List<ReturnHouse> queryByDescTest(@Param("id") Integer id, @Param("type") Integer type);

    //查询用户关注经纪人的房源
    List<House> selectByCollectAndAgent(Integer busId, Integer agentId, String phone);
}