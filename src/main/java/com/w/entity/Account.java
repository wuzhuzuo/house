package com.w.entity;

import lombok.Data;

@Data
public class Account {
    private Integer id;
    private Integer busId;
    private Integer agentId;
    private String userName;
    private String passWord;
    private Integer type;


}
