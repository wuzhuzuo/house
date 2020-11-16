package com.w.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:wu
 * @Date :create in 11:09 2020/6/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorAreaScope {

    private Integer minFloorArea;
    private Integer maxFloorArea;
}
