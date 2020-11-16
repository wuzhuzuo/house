package com.w.mapper;

import com.w.entity.Account;

import java.util.List;

public interface AccountMapper {
    int deleteByPrimaryKey(Integer id);


    void insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(Account record);


    Account selectByUserNameAndPassword(Account account);

    Account selectByUserName(Account account);

    List<Account> list(Account account);

    Account selectByBusIdAndAgentId(Account account);

    //根据用户名（即手机号） 修改密码
    void updateByUserName(Account account);

}