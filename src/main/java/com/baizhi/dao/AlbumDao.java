package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    //增
    void insert(Album album);
    //删
    void delete(String[] id);
    //改
    void update(Album album);
    //查
    List<Album> select(@Param("start")Integer start,@Param("rows")Integer rows);
    List<Album> selectAll();
    Album selectById(String id);
    int selectCount();
}
