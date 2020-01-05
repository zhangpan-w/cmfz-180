package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {
    //增
    void insert(Chapter chapter);
    //删
    void delete(String[] id);
    //改
    void update(Chapter chapter);
    //查
    List<Chapter> select(@Param("start")Integer start,@Param("rows") Integer rows,@Param("albumId")String albumId);
    int selectCount();
    List<Chapter> selectAll(String albumId);
    Chapter selectById(String id);
}
