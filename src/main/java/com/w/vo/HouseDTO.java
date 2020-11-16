package com.w.vo;

import lombok.Data;

import java.util.List;

@Data
public class HouseDTO {

    private Integer id;

    private Integer type;

    private String state;

    private String province;

    private String city;

    private String area;

    private String address;

    private String price;
    private String maxfloorArea;
    private String minfloorArea;
    private String floorArea;

    private String contact;

    private String phone;

    private String openTime;

    private String handTime;

    private String apart;

    private String remark;

    private String tags;

    private List<MainApartVO> mainApart;

    private String totalPrice;

    private String direction;
    private String deploy;//房屋配置
    private String floors;//低楼层、中楼层、高楼层

    private String quality;
    private String houseYear;

    private String seeHouse;

    private String mainFloor;

    private String actualFloor;

    private String renovation;

    private String[] picUrl;

    private String payWay;

    private String leaseWay;

    private String title;

    private Integer isdel;

    private Integer isBus;

    private Integer isSubway;


    private Integer isFree;

    private Integer isPark;

    private String houseName;

    private Integer isMinprice;

    private Integer isMintotalprice;

    private Integer isHome;
    private Integer busId;
    private List<AgentDTO> agent;
    private Integer isRecommend;
}
