package com.w.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
@ApiModel
@Data
public class HouseVO {

    private Integer id;

    private String type;

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

    @ApiModelProperty(value = "开盘时间",example = "2020年01月01日")
    private String openTime;
    @ApiModelProperty(value = "交盘时间",example = "2020年10月01日")
    private String handTime;

    private String apart;

    private String remark;

    private String tags;

    private List<MainApartVO> mainApart;

    private String totalPrice;

    private String direction;

    private String houseYear;

    private String seeHouse;

    private String mainFloor;

    private String actualFloor;

    private String renovation;

    private String[] picUrl;

    private String payWay;

    private String leaseWay;

    private String title;

    private String isdel;

    private Integer isBus;

    private Integer isSubway;

    private Date createTime;

    private Integer isFree;

    private Integer isPark;

    private String houseName;

    private Integer isMinprice;

    private Integer isMintotalprice;

    private Integer isHome;
    private List<UpdataByAgentVo> agency;
}
