package com.w.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:wu
 * @Date :create in 10:17 2020/6/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceScope {

    private int minPrice;
    private int maxPrice;
}
