package com.w.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class History {
    private Integer id;

    private String openId;

    private Integer houseId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private List<House> houseList;

    private Integer browseCount;
    private Integer busId;
    private String phone;

}