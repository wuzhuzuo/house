package com.w.mapper;

import com.w.dto.QueryDto;
import com.w.entity.House;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:wu
 * @Date :create in 10:15 2020/6/11
 */
public interface TestMapper {

    public List<House> find(@Param("dto") QueryDto queryDto);
}
