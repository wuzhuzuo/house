package com.w.service;

import com.w.entity.User;

import java.util.List;

/**
 * @Author:wu
 * @Date :create in 13:45 2020/4/6
 */
public interface UserService {
    User selectByPhoneNo(User user);

    void insertSelective(User record);

    User selectByPrimaryKey(User user);
    void updateByPrimaryKeySelective(User record);

    List<User> test123(int pageSize,int pageIndex);

}
