package com.w.service.impl;

import com.w.entity.User;
import com.w.mapper.UserMapper;
import com.w.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectByPhoneNo(User user) {
        return userMapper.selectByPhoneNo(user);
    }

    @Override
    public void insertSelective(User record) {
        userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(User user) {
        return userMapper.selectByPrimaryKey(user);
    }

    @Override
    public void updateByPrimaryKeySelective(User record) {
        userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<User> test123(int pageSize, int pageIndex) {
        return userMapper.test123(pageSize, pageIndex);
    }
}
