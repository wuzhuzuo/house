package com.w.vo;

import lombok.Data;

@Data
public class AgentDTO {

    private Integer id;

    private Integer busId;

    private String agentName;

    private String phone;

    private String picUrl;

    private String remark;
  /*  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;*/

    private Integer state;
}
