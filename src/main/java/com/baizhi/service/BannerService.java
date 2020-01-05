package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    //增
    void insert(Banner banner);
    //删
    void delete(String[] ids);
    //改
    void update(Banner banner);
    //查
    Map<String,Object> query(Integer page, Integer rows);
    List<Banner> selectzs();
    List<Banner> selectAll();
}
