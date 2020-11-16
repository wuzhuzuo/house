package com.w.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class InsertHouseVo {


    private String maxfloorArea;
    private String minfloorArea;
    private Integer id;
    @ApiModelProperty(value = "标题", name = "type", required = true)
    private String title;
    @ApiModelProperty(value = "房源配置", name = "type", required = true)
    private String deploy;
    @ApiModelProperty(value = "低楼层、中楼层、高楼层", name = "type", required = true)
    private String floors;
    @ApiModelProperty(value = "类型（1：新房2：二手房3：租房）", name = "type", required = true)
    private Integer type;
    @ApiModelProperty(value = "在售、未售等", example = "在售")
    private String state;
    @ApiModelProperty(value = "省份", example = "安徽省")
    private String province;
    @ApiModelProperty(value = "城市", example = "合肥市")
    private String city;
    @ApiModelProperty(value = "地区", example = "xxx区")
    private String area;
    @ApiModelProperty(value = "详细地址", example = "盈港东路明珠路交叉口")
    private String address;
    @ApiModelProperty(value = "单价", example = "10000")
    private String price;
    @ApiModelProperty(value = "实际面积", example = "53.68")
    private String floorArea;
    @ApiModelProperty(value = "联系人", example = "老彭")
    private String contact;
    @ApiModelProperty(value = "联系电话", example = "13100000001")
    private String phone;
    @ApiModelProperty(value = "开盘时间", example = "2020-05-01")
    private String openTime;
    @ApiModelProperty(value = "交盘时间", example = "2020-10-01")
    private String handTime;
    @ApiModelProperty(value = "户型", example = "一室")
    private String apart;
    @ApiModelProperty(value = "描述", example = "大阳台，采光好")
    private String remark;
    @ApiModelProperty(value = "标签", example = "暖气，空调，网线")
    private String tags;
    // @ApiModelProperty(value = "主力户型", example = "两居室")
    private List<MainApartVO> mainApart;
    @ApiModelProperty(value = "总价", example = "200")
    private String totalPrice;
    @ApiModelProperty(value = "朝向", example = "东")
    private String direction;
    @ApiModelProperty(value = "楼房年份", example = "2015")
    private String houseYear;
    @ApiModelProperty(value = "随时看房", example = "电话")
    private String seeHouse;
    @ApiModelProperty(value = "总楼层", example = "20")
    private String mainFloor;
    @ApiModelProperty(value = "实际楼层", example = "13")
    private String actualFloor;
    @ApiModelProperty(value = "装修", example = "精装")
    private String renovation;
    //  @ApiModelProperty(value = "图片路径", example = "{xxxx.jpg}")
    private String[] picUrl;
    @ApiModelProperty(value = "支付方式", example = "押一付三")
    private String payWay;
    @ApiModelProperty(value = "出租方式", example = "整租")
    private String leaseWay;
    @ApiModelProperty(value = "房子类型：小区，别墅，商户等", example = "小区")
    private String quality;
    @ApiModelProperty(value = "是否靠近公交站1：是0否", example = "1")
    private Integer isBus;
    @ApiModelProperty(value = "是否靠近地铁1：是0否", example = "1")
    private Integer isSubway;

    @ApiModelProperty(value = "是否优惠1：是0否", example = "1")
    private Integer isFree;
    @ApiModelProperty(value = "是否有停车位1：是0否", example = "1")
    private Integer isPark;
    @ApiModelProperty(value = "小区名称或楼盘名称", example = "恒大二期")
    private String houseName;
    @ApiModelProperty(value = "是否是低单价1：是0否", example = "1")
    private Integer isMinprice;
    @ApiModelProperty(value = "是否是低总价1：是0否", example = "1")
    private Integer isMintotalprice;
    @ApiModelProperty(value = "是否是现房1：是0否", example = "1")
    private Integer isHome;
  /*  @ApiModelProperty(value = "商家编码", example = "1")
    private Integer busId;*/
    @ApiModelProperty(value = "添加房源关注商家")
    private List<AgentDTO> agent;
    @ApiModelProperty(value = "是否是推荐房源1：是否")
    private Integer isRecommend;

    @Override
    public String toString() {
        return "House [id=" + id + ", houseName=" + houseName + ", type=" + type + ", state=" + state + ", province="
                + province + ", city=" + city + ", area=" + area + ", address=" + address + ", price=" + price
                + ", floorArea=" + floorArea
                + ", contact=" + contact + ", phone=" + phone + ", openTime=" + openTime + ", handTime=" + handTime
                + ", apart=" + apart + ", remark=" + remark + ", tags=" + tags + ", mainApart=" + mainApart
                + ", totalPrice=" + totalPrice + ", direction=" + direction + ", houseYear=" + houseYear + ", seeHouse="
                + seeHouse + ", mainFloor=" + mainFloor + ", actualFloor=" + actualFloor + ", renovation=" + renovation
                + ", picUrl=" + picUrl + ", payWay=" + payWay + ", leaseWay=" + leaseWay + ", quality=" + quality
                + ", isBus=" + isBus + ", isSubway=" + isSubway
                + ", isFree=" + isFree + ", isPark=" + isPark + ", isMinprice=" + isMinprice + ", isMintotalprice="
                + isMintotalprice + ", isHome=" + isHome + ", deploy=" + deploy + ", title=" + title + ", floors="
                + floors + ",isRecommend" + isRecommend + "]";

    }
}
