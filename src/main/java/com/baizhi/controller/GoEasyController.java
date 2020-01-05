package com.baizhi.controller;

import com.baizhi.entity.Guru;
import com.baizhi.vo.MapDto;
import com.baizhi.service.GuruService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goEasy")
public class GoEasyController {
    @Autowired
    private GuruService guruService;

    @RequestMapping("action")
    public Map<String,Object> action(){
        return guruService.selectSeven();
    }

    @RequestMapping("monthselect")
    public Map<String,Object> monthselect(){
        Map<String,Object> map = new HashMap<>();
        List<String> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<MapDto> list = guruService.selectByMonth();
        for (int i=0; i<12;i++){
            boolean f =true;
            list1.add((i+1)+"月");

            for (int j = 0; j < list.size(); j++) {
                if ((i+1)==list.get(j).getName()){
                    list2.add(list.get(j).getRan());
                    f = false;
                }
            }
            if (f){
                list2.add(0);
            }
            map.put("name",list1);
            map.put("value",list2);
        }

        return map;
    }

    public void insert(Guru guru){
        guruService.insert(guru);
        guruService.selectSeven();
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io","BC-b121a256bfeb4d0e874c71b5a744d092");
        goEasy.publish("goeasy_7","");
    }
}
