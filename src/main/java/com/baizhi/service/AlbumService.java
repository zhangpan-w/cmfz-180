package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    //增
    void insert(Album album);
    //删
    void delete(String[] id);
    //改
    void update(Album album);
    //查
    Map<String,Object> select(Integer page, Integer rows);
    List<Album> selectAll();
    Album selectById(String id);
}
