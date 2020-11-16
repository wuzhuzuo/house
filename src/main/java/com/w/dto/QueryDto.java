package com.w.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author:wu
 * @Date :create in 10:27 2020/6/11
 */

@Data
@ApiModel

public class QueryDto {
    @ApiModelProperty(value = "是否是推荐房源1：是0否")
    private Integer isRecommend;
    @ApiModelProperty(value = "类型（1：新房2：二手房3：租房）", name = "type", required = true)
    private Integer type;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "地区）")
    private String area;
    @ApiModelProperty(value = "状态（在售、未售、售罄）")
    private String state;
    @ApiModelProperty(value = "户型（一居室两居室）")
    private String apart;
    @ApiModelProperty(value = "开盘开始时间")
    private String beginTime;
    @ApiModelProperty(value = "开盘结束时间")
    private String endTime;
    @ApiModelProperty(value = "房屋配置", example = "卫生间")
    private String deploy;
    @ApiModelProperty(value = "装修（精装、简装等）")
    private String renovation;
    @ApiModelProperty(value = "朝向")
    private String direction;
    @ApiModelProperty(value = "支付方式（押一付三等）")
    private String payWay;
    @ApiModelProperty(value = "出租方式（整租、合租）")
    private String leaseWay;
    @ApiModelProperty(value = "房屋类型（住宅，商业，别墅等）")
    private String title;
    @ApiModelProperty(value = "是否靠近公交站 （1：是；0：否）")
    private Integer isBus;
    @ApiModelProperty(value = "是否有停车位（1：是；0：否）")
    private Integer isPark;
    @ApiModelProperty(value = "是否是低单价：1：是，0否")
    private Integer isMinprice;
    @ApiModelProperty(value = "是否是低总价：1：是；0：否")
    private Integer isMintotalprice;
    @ApiModelProperty(value = "最大和最小单价")
    private List<PriceScope> priceScopes;
    @ApiModelProperty(value = "最大和最小总价")
    private List<TotalPriceScope> totalPriceScopes;
    @ApiModelProperty(value = "最大和最小实际面积")
    private List<AreaScope> areaScopes;
    @ApiModelProperty(value = "最大和最小面积范围")
    private List<FloorAreaScope> floorAreaScopes;
    @ApiModelProperty(value = "页码", required = true)
    private Integer pageIndex;
    @ApiModelProperty(value = "每页条数", required = true)
    private Integer pageSize;
    @ApiModelProperty(value = "小区名称")
    private String houseName;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "排序字段")
    private String sortKey;
    @ApiModelProperty(value = "排序方式")
    private String sortValue;
    @ApiModelProperty(value = "看房 随时 电话")
    private String seeHouse;
    @ApiModelProperty(value = "居室查询字段", hidden = true)
    private Integer apartFilter;
    @ApiModelProperty(value = "类型，小区，别墅")
    private String quality;

    @ApiModelProperty(value = "是否是现房")
    private Integer isHome;
    @ApiModelProperty(value = "低楼层，中楼层")
    private String floors;

}
