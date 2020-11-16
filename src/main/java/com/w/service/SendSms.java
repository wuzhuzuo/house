package com.w.service;

import java.util.Map;


public interface SendSms {

    public String send(String phone, /*String templateCode,*/ Map<String,Object> map);
}
