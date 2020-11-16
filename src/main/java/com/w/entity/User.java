package com.w.entity;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private Integer id;

    private String openId;

    private String phone;

    private String shoucang;

    private Integer state;
    private List<House> houses;

    public User(Integer id, String openId, String phone, String shoucang, Integer state, List<House> houses) {
        this.id = id;
        this.openId = openId;
        this.phone = phone;
        this.shoucang = shoucang;
        this.state = state;
        this.houses = houses;
    }

    public User() {
    }
}