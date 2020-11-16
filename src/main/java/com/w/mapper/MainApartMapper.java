package com.w.mapper;

import com.w.entity.MainApart;

import java.util.List;

public interface MainApartMapper {
    void deleteByMainApart(Integer id);

   /* void insert(MainApart record);*/

    void insertByMainApart(MainApart record);

    MainApart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MainApart record);

    int updateByPrimaryKeyWithBLOBs(MainApart record);

    int updateByPrimaryKey(MainApart record);

    List<MainApart> selectByMainApartAndhouseId(Integer id);
}