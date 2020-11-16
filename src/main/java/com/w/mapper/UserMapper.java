package com.w.mapper;

import com.w.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);


    void insertSelective(User record);

    User selectByPrimaryKey(User user);

    void updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByPhoneNo(User user);

    List<User> test123(@Param("pageSize") int pageSize, @Param("pageIndex") int pageIndex);
}