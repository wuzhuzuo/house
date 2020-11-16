package com.w.entity;

import com.w.vo.AgentDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class House extends Object {
    private Integer id;

    private Integer type;

    private String state;

    private String province;

    private String city;

    private String area;

    private String address;

    private String price;

    private String maxFloorarea;

    private String minFloorarea;

    private String floorArea;

    private String contact;

    private String phone;

    private Date openTime;

    private Date handTime;

    private String apart;

    private String remark;

    private String tags;


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

    private String picUrl;

    private String payWay;

    private String leaseWay;

    private String title;

    private Integer isdel;

    private Integer isBus;

    private Integer isSubway;


    private Date createTime;

    private Integer isFree;

    private Integer isPark;

    private String houseName;

    private Integer isMinprice;

    private Integer isMintotalprice;

    private Integer isHome;
    private Integer isRecommend;

    private Date updateTime;
    private Integer apartFilter;
    private Integer totalPriceFilter;
    private List<AgentDTO> agent;


}