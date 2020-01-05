package com.baizhi.service.impl;

import com.baizhi.dao.GuruDao;
import com.baizhi.entity.Guru;
import com.baizhi.vo.MapDto;
import com.baizhi.service.GuruService;
import com.baizhi.vo.SevenDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class GuruServiceImpl implements GuruService {
    @Autowired
    private GuruDao guruDao;

    @Override
    public void insert(Guru guru) {
        guruDao.insert(guru);
    }


    @Override
    public Map<String,Object> selectSeven() {
        Map<String,Object> map = new HashMap<>();
        List<SevenDay> sevenDays = guruDao.selectSeven();
        List<String> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (SevenDay sevenDay : sevenDays) {
            list1.add(sevenDay.getDate());
            list2.add(sevenDay.getCount());
        }
        map.put("name",list1);
        map.put("value",list2);


        return map;
    }

    @Override
    public List<MapDto> selectByMonth() {
        return guruDao.selectByMonth();
    }
}
