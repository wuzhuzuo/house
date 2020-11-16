package com.w.contorller;

import com.w.entity.Account;
import com.w.service.AccountService;
import com.w.utils.Result;
import com.w.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:wu
 * @Date :create in 12:31 2020/3/31
 */
@Slf4j
@RestController
@Api(value = "account", tags = "登录接口模块")
public class AccountContorller {

    @Autowired
    private AccountService accountService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //登录
    @ApiOperation(value = "登录接口")
    @PostMapping("/api/login")
    public Result login(@RequestBody LoginVO loginVO) {
        Result result = new Result();
        log.info("登录参数：" + loginVO.toString());

        if (StringUtils.isEmpty(loginVO.getPassWord()) || StringUtils.isEmpty(loginVO.getUserName())) {
            result.setSuccess(false);
            result.setMessage("用户名或密码不能为空");
        } else {
            Account account = new Account();
            account.setUserName(loginVO.getUserName());
            account.setPassWord(loginVO.getPassWord());
            account.setType(loginVO.getType());
            if (!StringUtils.isEmpty(loginVO.getUserName()) || StringUtils.isEmpty(loginVO.getPassWord()) || StringUtils.isEmpty(loginVO.getType())) {
                Account accounts = accountService.selectByUserNameAndPassword(account);
                if (accounts != null) {
                    result.setSuccess(true);
                    result.setMessage("登陆成功");
                    result.setData(accounts);
                } else {
                    result.setSuccess(false);
                    result.setMessage("暂无用户登录信息");
                }
            } else {
                result.setSuccess(false);
                result.setMessage("用户名或密码或类型传入错误");
            }
        }
        return result;
    }

    @Data
    public static class LoginVO {
        private String userName;
        private String passWord;
        private Integer type;
    }

    //增加
    @ApiOperation("添加用户名和密码")
    @PostMapping("/api/account/insert")
    public Result insert(@RequestBody InsertVO insertVO) {
        Result result = new Result();
        log.info("插入参数：" + insertVO.toString());
        if (StringUtils.isEmpty(insertVO.getPassWord()) || StringUtils.isEmpty(insertVO.getUserName())) {
            result.setCode(ResultCode.FAILED.getCode());
            result.setMessage("用户名或密码不能为空");
            result.setSuccess(false);
            return result;
        }
        if (StringUtils.isEmpty(insertVO.getBusId()) || StringUtils.isEmpty(insertVO.getType())) {
            result.setCode(ResultCode.FAILED.getCode());
            result.setMessage("商户编号或类型不能为空");
            result.setSuccess(false);
            return result;
        } else {
            Account account = new Account();
            account.setPassWord(insertVO.getPassWord());
            account.setUserName(insertVO.getUserName());
            account.setBusId(insertVO.getBusId());
            account.setType(insertVO.getType());
            Account account1 = accountService.selectByUserName(account);
            if (account1 == null) {
                Account account3 = new Account();
                account3.setBusId(account.getBusId());
                if (StringUtils.isEmpty(account.getAgentId())) {
                    account3.setAgentId(account.getBusId());
                } else {
                    account3.setAgentId(account.getAgentId());
                }
                accountService.insertSelective(account3);
                Account account2 = accountService.selectByUserNameAndPassword(account);
                result.setMessage("添加成功");
                result.setSuccess(true);
                result.setData(account2);
            } else {
                result.setMessage("已注册");
                result.setSuccess(false);
                result.setCode(ResultCode.PARAM_PARAMETER_ERROR.getCode());
            }
        }
        return result;
    }

    @Data
    public static class InsertVO {
        private Integer busId;
        private String userName;
        private String passWord;
        private Integer type;
    }

    //查询
    @ApiOperation("查询商户下的所有经纪人或单个经纪人的用户名和密码")
    @PostMapping("/api/acount/select")
    public Result query(@RequestBody Account account) {
        Result result = new Result();
        log.info("查询登录用户", account.toString());
        if (StringUtils.isEmpty(account.getBusId())) {
            result.setSuccess(false);
            result.setMessage("输入信息错误");
            return result;
        } else {
            List<Account> list = accountService.list(account);
            result.setSuccess(true);
            result.setMessage("成功");
            result.setData(list);
        }
        return result;
    }


    @ApiOperation("修改商户或经济人的密码和用户名")
    //修改商户或经济人的密码和用户名
    @PostMapping("/api/account/update")
    public Result update(@RequestBody UpdateAccount updateAccount) {
        Result result = new Result();
        Account account = new Account();
        account.setUserName(updateAccount.getPhone());
        if (StringUtils.isEmpty(updateAccount.getPhone())) {
            result.setSuccess(false);
            result.setMessage("手机不能为空");
            return result;
        } else if (StringUtils.isEmpty(updateAccount.getCode())) {
            result.setSuccess(false);
            result.setMessage("请输入验证码");
            return result;
        } else if (StringUtils.isEmpty(updateAccount.getPassword())) {
            result.setSuccess(false);
            result.setMessage("请输入新密码");
            return result;
        } else if (StringUtils.isEmpty(redisTemplate.opsForValue().get(updateAccount.getPhone()))) {
            result.setSuccess(false);
            result.setMessage("验证码失效，请重新获取");
            return result;
        } else if (redisTemplate.opsForValue().get(updateAccount.getPhone()).equals(updateAccount.getCode())) {
            Account account1 = accountService.selectByUserName(account);
            if (StringUtils.isEmpty(account1)) {
                result.setSuccess(false);
                result.setMessage("该手机号还未注册，请注册");
                return result;
            } else {
                Account account2 = new Account();
                account2.setUserName(updateAccount.getPhone());
                account2.setPassWord(updateAccount.getPassword());
                accountService.updateByUserName(account2);
                result.setSuccess(true);
                result.setMessage("密码修改成功");
            }
        }else{
            result.setSuccess(false);
            result.setMessage("验证码输入错误");
        }

        return result;
    }


    @Data
    public static class AccountVO {
        private String passWord;
        private Integer busId;
        private Integer agentId;
        private Integer type;
    }


    @Data
    public static class UpdateAccount {

        private String password;
        private String code;
        private String phone;
    }
}
