package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    //增
    void insert(Chapter chapter);
    //删
    void delete(String[] id);
    //改
    void update(Chapter chapter);

    //查
    Map<String, Object> select(Integer page, Integer rows, String albumId);
    List<Chapter> selectAll(String albumId);
    Integer selectCount();
    Chapter selectById(String id);
}
