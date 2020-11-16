package com.w.contorller;

import com.w.entity.Account;
import com.w.entity.Business;
import com.w.entity.House;
import com.w.entity.RHouse;
import com.w.mapper.RHouseMapper;
import com.w.service.AccountService;
import com.w.service.BusinessService;
import com.w.service.HouseService;
import com.w.utils.Result;
import com.w.utils.ResultCode;
import com.w.vo.BusinessInset;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author:wu
 * @Date :create in 14:26 2020/3/31
 */
@Slf4j
@Api(value = "BusinessController", tags = "商家模块")
@RestController
public class BusinessController {
    @Autowired
    private BusinessService businessService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RHouseMapper rhouseMapper;
    @Autowired
    private HouseService houseService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation("添加商户信息")
    @PostMapping("/api/business/insert")
    public Result insert(@RequestBody BusinessInset businessInset) throws ParseException {
        Result result = new Result();
        Business business = new Business();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        log.info("对比之前获取code{}，缓存code{}", businessInset.getCode(), redisTemplate.opsForValue().get(businessInset.getPhone()));
        if (StringUtils.isEmpty(businessInset.getCode())) {
            result.setSuccess(false);
            result.setMessage("请输入验证码");
            return result;
        }
        if (StringUtils.isEmpty(businessInset.getPhone())) {
            result.setSuccess(false);
            result.setMessage("请填写手机号");
            return result;
        }
        if (StringUtils.isEmpty(businessInset.getPassWord())) {
            result.setSuccess(false);
            result.setMessage("请填写密码");
            return result;
        }
        if(StringUtils.isEmpty(redisTemplate.opsForValue().get(businessInset.getPhone()))){
            result.setSuccess(false);
            result.setMessage("验证码失效，请重新获取");
            return result;
        }
        if (businessInset.getCode().equals(redisTemplate.opsForValue().get(businessInset.getPhone()))) {
            Account account = new Account();
            account.setUserName(businessInset.getPhone());
            Account account1 = accountService.selectByUserName(account);
            Business business2 = businessService.selectByBusPhone(businessInset.getPhone());
            if (StringUtils.isEmpty(account1) && StringUtils.isEmpty(business2)) {
                Account account2 = new Account();
                Business business3 = new Business();
                business3.setPhone(businessInset.getPhone());
                business3.setCreatTime(df.parse(df.format(new Date())));
                businessService.insertSelective(business3);

                Business business1 = businessService.selectByPrimaryKey(business3);
                account2.setUserName(businessInset.getPhone());
                account2.setType(1);
                account2.setBusId(business1.getId());
                account2.setAgentId(business1.getId());
                account2.setPassWord(businessInset.getPassWord());
                accountService.insertSelective(account2);
                result.setMessage("添加成功");
                result.setSuccess(true);
                result.setData(business1);
            } else {
                result.setMessage("该手机号已被注册");
                result.setSuccess(false);
            }
        } else {
            result.setMessage("添加失败，code不匹配");
            result.setSuccess(false);
        }
        return result;
    }

    @ApiOperation("查询所有或条件查询")
    @PostMapping("/api/business/query")
    public Result query(@RequestBody Map map) {
        Result result = new Result();
        List<Business> list = businessService.list(map);
        log.info("canshu ", list.toString());
        if (list.size() > 0) {
            result.setData(list);
            result.setMessage("查询成功");
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setMessage("查询失败");
            result.setCode(ResultCode.PARAM_PARAMETER_ERROR.getCode());
        }
        return result;

    }


    //分页查询
    @ApiOperation("查询具体的商家信息")
    @PostMapping("/api/business/select")
    public Result select(@RequestBody BusVo busVo) {
        Result result = new Result();
        if (StringUtils.isEmpty(busVo.getId())) {
            result.setSuccess(false);
            result.setMessage("请输入商家信息");
        } else {
            Business business = new Business();
            business.setId(busVo.getId());
            Business business1 = businessService.selectByPrimaryKey(business);
            if (StringUtils.isEmpty(business1)) {
                result.setMessage("暂无商家信息");
                result.setSuccess(false);
            } else {
                result.setMessage("查询商家信息成功");
                result.setSuccess(true);
                result.setData(business1);
            }
        }

        return result;  // 返回值是 PageInfo
    }

    @ApiOperation("修改商家信息")
    @PostMapping("/api/business/update")
    public Result test(@RequestBody Business business) throws ParseException {
        Result result = new Result();
        if (StringUtils.isEmpty(business.getId())) {
            result.setSuccess(false);
            result.setMessage("请输入商户编码");
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            business.setCreatTime(df.parse(df.format(new Date())));
            businessService.updateByPrimaryKeySelective(business);
            Business business1 = businessService.selectByPrimaryKey(business);
            result.setSuccess(true);
            result.setMessage("修改成功");
            result.setData(business1);
        }
        return result;
    }

    @ApiOperation("删除商家信息（物理删除）")
    @PostMapping("/api/business/delete")
    public Result delete(@RequestBody BusVo busVo) {
        Result result = new Result();
        if (StringUtils.isEmpty(busVo.getId())) {
            result.setMessage("参数错误");
            result.setSuccess(false);
        } else {
            Integer type = null;
            List<RHouse> list = rhouseMapper.selectByBusAndHouse(busVo.getId(), type);
            list.forEach(rHouse -> {
                House house = new House();
                house.setId(rHouse.getId());
                houseService.updateIsdel(house);
            });
            Business business = new Business();
            business.setId(busVo.getId());
            businessService.deleteByPrimaryKey(business);
            result.setSuccess(true);
            result.setMessage("删除成功");
        }
        return result;
    }

    @Data
    public static class BusVo {
        private Integer id;
    }

}