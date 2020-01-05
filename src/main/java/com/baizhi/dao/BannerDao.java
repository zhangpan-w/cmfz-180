package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    //增
    void insert(Banner banner);
    //删
    void delete(String[] ids);
    //改
    void update(Banner banner);
    //查
    List<Banner> select(@Param("start")Integer start, @Param("rows")Integer rows);
    List<Banner> selectzs();
    List<Banner> selectAll();
    Integer selectCount();
}
