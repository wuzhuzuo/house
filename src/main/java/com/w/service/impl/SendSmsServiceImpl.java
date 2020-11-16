package com.w.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.w.service.SendSms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SendSmsServiceImpl implements SendSms {

    @Override
    public String send(String phone, /*String templateCode,*/ Map<String, Object> code) {

        //连接阿里云

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G5RmwNTeUUhb2DjgZHd", "RCOEjYZqT0Dj1HS5Mg5z4S565yxksB");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "码上找房");
       // request.putQueryParameter("TemplateCode", "SMS_197115033");
         request.putQueryParameter("TemplateCode","SMS_199791849");

        //构建短信验证码
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(code));
        log.info("获取短信手机号发送的参数，手机号{},code{}", phone, JSONObject.toJSONString(code));
        log.info("获取到发送短信的参数{}", request.toString());
        try {
            // SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            CommonResponse response = client.getCommonResponse(request);

            JSONObject jsonObject = JSONObject.parseObject(response.getData());
            Map map = new HashMap();
            map.putAll(jsonObject);
            System.out.println("测试打印code：" + map.get("Code"));

            log.info("相应参数：{}", response.getData());
            log.info("是否发送成功{}", response.getHttpResponse().isSuccess());
            return response.getData();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
