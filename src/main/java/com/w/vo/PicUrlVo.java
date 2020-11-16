package com.w.vo;

import lombok.Data;

@Data
public class PicUrlVo {
    private String imgUrl;

    @Override
    public String toString(){
            return   "{\"" + imgUrl + "\"}";
    }
}
