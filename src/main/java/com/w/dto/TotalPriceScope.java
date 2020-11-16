package com.w.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:wu
 * @Date :create in 11:00 2020/6/11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TotalPriceScope {
    private int minTotalPrice;
    private int maxTotalPrice;
}
