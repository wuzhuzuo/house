package com.w.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserHouseVO {
    private Integer id;
    private String houseName;
    private String title;
    private String apart;
    private String floorArea;
    private  String phone;
    private String minfloorArea;
    private String maxfloorArea;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    private String state;
    private Integer type;
    private String totalPrice;
    private Integer record;
    private String[] picUrl;
    private String price;
    private Integer isRecommend;
}
