package com.baizhi.dao;

import com.baizhi.entity.Guru;
import com.baizhi.vo.MapDto;
import com.baizhi.vo.SevenDay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GuruDao {
    //增
    void insert(Guru guru);
    //删
    void delete(String id);
    //改
    void updae(Guru guru);
    //查
    List<Guru> select(@Param("start")Integer start,@Param("rows")Integer rows);
    List<SevenDay> selectSeven();
    List<MapDto> selectByMonth();
}
