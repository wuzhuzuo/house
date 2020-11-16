package com.w.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Collect {
    private Integer id;

    private String openId;

    private Integer houseId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date followTime;

    private Integer state;
    private String phone;
    private Integer busId;


}