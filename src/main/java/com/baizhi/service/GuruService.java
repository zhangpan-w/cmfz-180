package com.baizhi.service;

import com.baizhi.entity.Guru;
import com.baizhi.vo.MapDto;
import com.baizhi.vo.SevenDay;

import java.util.List;
import java.util.Map;

public interface GuruService {
    //增
    void insert(Guru guru);
    Map<String,Object> selectSeven();
    List<MapDto> selectByMonth();
}
