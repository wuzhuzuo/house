package com.w.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UpdataByAgentVo {
    private Integer id;

    private Integer busId;

    private String agentName;

    private String phone;

    private String picUrl;

    private String remark;

    private Date createTime;

    private Integer state;
}
