package com.w.service.impl;

import com.w.entity.Account;
import com.w.mapper.AccountMapper;
import com.w.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:wu
 * @Date :create in 12:29 2020/3/31
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {


    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<Account> list(Account account) {
        return accountMapper.list(account);
    }

    @Override
    public void insertSelective(Account record) {
        accountMapper.insertSelective(record);
    }

    @Override
    public Account selectByUserName(Account account) {
        return accountMapper.selectByUserName(account);
    }

    @Override
    public void updateByPrimaryKeySelective(Account record) {
        accountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Account selectByUserNameAndPassword(Account account) {
        return accountMapper.selectByUserNameAndPassword(account);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        accountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Account selectByBusIdAndAgentId(Account account) {
        return accountMapper.selectByBusIdAndAgentId(account);
    }

    @Override
    public void updateByUserName(Account account) {
        accountMapper.updateByUserName(account);
    }

}
