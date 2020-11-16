package com.w.contorller;

import com.alibaba.fastjson.JSONObject;
import com.w.service.SendSms;
import com.w.utils.Result;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@Api(tags = "短信验证码发送")
public class SendSmsController {
    @Autowired
    private SendSms sendSms;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/api/send")
    public Result code(@RequestBody PhoneNO phoneNO) {
        Result result = new Result();
        //调用发送方式
        String code = redisTemplate.opsForValue().get(phoneNO.getPhone());
        if (StringUtils.isEmpty(phoneNO.getPhone())) {
            result.setSuccess(false);
            result.setMessage("手机号不能为空");
            return result;
        } else if (!StringUtils.isEmpty(code)) {
            result.setSuccess(false);
            result.setMessage(phoneNO.getPhone() + "的code:" + code + "已存在");
            return result;
        } else {
            //生成随机验证码
            // code = UUID.randomUUID().toString().substring(0, 6);
            code = String.valueOf(new Random().nextInt(899999) + 100000);
            Map<String, Object> map = new HashMap<>();
            map.put("code", code);
            String isSend = sendSms.send(phoneNO.getPhone(), map);
            if (StringUtils.isEmpty(isSend)) {
                result.setSuccess(false);
                result.setMessage("发送失败");
                return result;
            }
            JSONObject jsonObject = JSONObject.parseObject(isSend);
            Map msgMap = new HashMap();
            msgMap.putAll(jsonObject);
            if (!(msgMap.get("Code") == null) && msgMap.get("Code").equals("OK")) {
                redisTemplate.opsForValue().set(phoneNO.getPhone(), code, 1, TimeUnit.MINUTES);
                Map map1 = new HashMap();
                map1.put("code", code);
                result.setSuccess(true);
                String phoneCode1 = redisTemplate.opsForValue().get(phoneNO.getPhone());
                log.info("手机验证码：{},发送时间{}", phoneCode1, new Date());
                result.setMessage("发送成功");
                result.setData(map1);
            } else {
                result.setSuccess(false);
                String errMsg = (String) msgMap.get("Message");
                if (errMsg.contains("触发天级流控")) {
                    result.setMessage("发送失败:当天发送已达到最大值");
                } else {
                    result.setMessage("发送失败:" + errMsg);
                }

            }
        }
        return result;
    }

    @Data
    static class PhoneNO {
        private String phone;
    }
}

