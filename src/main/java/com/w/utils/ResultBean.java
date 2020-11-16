package com.w.utils;

import lombok.Data;

@Data
public class ResultBean {
    private  int min;
    private int max;
    private int curr;

    public ResultBean(int min, int max, int curr) {
        this.min = min;
        this.max = max;
        this.curr = curr;
    }
}
