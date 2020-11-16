package com.w.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.w.vo.HouseVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Agent {
    private Integer id;

    private Integer busId;

    private String agentName;

    private String phone;

    private String picUrl;

    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer state;

    private List<HouseVO> houses;
}