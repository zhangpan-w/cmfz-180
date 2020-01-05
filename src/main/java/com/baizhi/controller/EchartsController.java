package com.baizhi.controller;

import com.baizhi.vo.MapDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @RequestMapping("getData")
    public List<Integer> getData(){
        List<Integer> list = new ArrayList<>();
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        return list;
    }

    @RequestMapping("getMapData")
    public List<Map<String,Object>> getMapData(){
        List<Map<String,Object>> list = new ArrayList<>();

        List<MapDto> list1 = new ArrayList<>();
        /*list1.add(new MapDto("北京",new Random().nextInt(100)));
        list1.add(new MapDto("山西",new Random().nextInt(100)));
        list1.add(new MapDto("湖北",new Random().nextInt(100)));
        list1.add(new MapDto("天津",new Random().nextInt(100)));
        list1.add(new MapDto("吉林",new Random().nextInt(100)));
        list1.add(new MapDto("浙江",new Random().nextInt(100)));*/


        for (MapDto mapDto : list1) {
            Map<String,Object> map = new HashMap<>();
            map.put("name",mapDto.getName());
            map.put("value",mapDto.getRan());
            list.add(map);
        }

        return list;
    }
}
