package com.w.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class MainApartVO {
    @ApiModelProperty(value = "主力户型的面积")
    private String mainArea;
    @ApiModelProperty(value = "主力户型名称（三室一厅，两室一厅等）")
    private String apart;
    @ApiModelProperty(value = "主力户型图片地址")
    private String  picUrl;
    @ApiModelProperty(value = "状态：待售，在售，售罄")
    private String state;
}
