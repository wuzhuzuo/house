package com.w.service;

import com.w.entity.Account;


import java.util.List;

/**
 * @Author:wu
 * @Date :create in 12:27 2020/3/31
 */

public interface AccountService {
    List<Account> list(Account account);

    void insertSelective(Account record);

    Account selectByUserName(Account account);

    void updateByPrimaryKeySelective(Account record);

    Account selectByUserNameAndPassword(Account account);

    void deleteByPrimaryKey(Integer id);

    Account  selectByBusIdAndAgentId(Account account);
    //根据用户名（即手机号） 修改密码
    void updateByUserName(Account account);
}
