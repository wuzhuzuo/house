package com.w.contorller;

import com.alibaba.fastjson.JSONObject;
import com.w.entity.User;
import com.w.service.UserService;
import com.w.utils.HttpUtil;
import com.w.utils.Result;
import com.w.utils.WXBizDataCrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:wu
 * @Date :create in 13:46 2020/4/6
 */
@Slf4j
@Api(value = "UserController", tags = "用户模块")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${appId}")
    private String appId;
    @Value("${appSecret}")
    private String appSecret;


    /**
     * 小程序授权获取手机号
     */
    @ApiOperation("获取获取手机号")
    @PostMapping("/api/user/gePhone")
    public Result getPhoneNumber(@RequestBody PhoneNo phoneNo) throws Exception {
        Result result1 = new Result();
        String params = "appid=" + appId + "&secret=" + appSecret + "&js_code=" + phoneNo.getCode() + "&grant_type=authorization_code";
        String s = HttpUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String sessionKey = jsonObject.getString("session_key");
        String openId = jsonObject.getString("openid");


        log.info("获取sessionKey", sessionKey.toString());
        Map<String, Object> map = new HashMap<>();
        log.info("获取手机号加密参数", phoneNo.toString());

        String result = WXBizDataCrypt.decryptNew(phoneNo.getEncryptedData(), sessionKey, phoneNo.getIv());
        JSONObject objet = JSONObject.parseObject(result);

        if (!StringUtils.isEmpty(objet.getString("purePhoneNumber"))) {
            log.info("解密成功，result返回", result);
            String phone = objet.getString("purePhoneNumber");
            User user = new User();
            user.setOpenId(openId);
            user.setPhone(phone);
            User user1 = userService.selectByPhoneNo(user);
            if (!StringUtils.isEmpty(user1)) {
                user.setId(user1.getId());
                userService.updateByPrimaryKeySelective(user);
            } else {
                userService.insertSelective(user);
            }
            if (!StringUtils.isEmpty(phone)) {
                result1.setSuccess(true);
                result1.setData(phone);
                result1.setMessage("获取手机号成功");
            } else {
                result1.setSuccess(false);
                result1.setMessage("添加手机号失败");
            }
        } else {
            result1.setMessage("获取手机号失败");
            result1.setSuccess(false);
        }

        return result1;

    }

    @Data
    public static class PhoneNo {
        @ApiModelProperty(value = "微信加密信息字符串", required = true)
        private String encryptedData;
        @ApiModelProperty(value = "临时code", required = true)
        private String code;
        @ApiModelProperty(value = "加密算法初始量", required = true)
        private String iv;
        @ApiModelProperty(value = "加密算法初始量", required = true)
        private String sessionKey;
        @ApiModelProperty(value = "用户id", required = true)
        private Integer id;
    }


    /*

        @ApiOperation("获取openiD")
        @PostMapping("/api/user/getOpenId")
        public Result getOpenId(@RequestBody RuCan ruCan) {
            Result result = new Result();
            Map<String, Object> map = new HashMap<>();
            log.info("获取授权参数", ruCan.getCode());
            String params = "appid=" + appId + "&secret=" + appSecret + "&js_code=" + ruCan.getCode() + "&grant_type=authorization_code";
            String s = HttpUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
            JSONObject jsonObject = JSONObject.parseObject(s);
            String session_key = jsonObject.getString("session_key");
            String openid = jsonObject.getString("openid");
            String unionid = jsonObject.getString("unionid");
            User user = new User();
            if (StringUtils.isEmpty(openid)) {
                result.setMessage("获取openID失败");
                result.setSuccess(false);
            } else if (!StringUtils.isEmpty(session_key)) {
                user.setOpenId(openid);
                userService.insertSelective(user);
                User user1 = userService.selectByPrimaryKey(user);
                map.put("id", user1.getId());
                map.put("sessionKey", session_key);
                result.setMessage("授权成功");
                result.setSuccess(true);
                result.setData(map);
            } else {
                result.setSuccess(false);
                result.setMessage("授权失败");
            }

            return result;
        }
    */
    @ApiOperation("获取获取手机号")
    @PostMapping("/test")
    public Result test() {
        Result result = new Result();
        int pageSize = 1;
        int pageIndex = 10;

        List<User> list = userService.test123(pageSize, pageIndex);
        if (list.size() <= 0 || list == null) {
            result.setMessage("meiyoushuju ");
        } else {
            result.setData(list);
        }
        return result;
    }


}
