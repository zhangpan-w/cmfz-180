package com.baizhi;

import com.baizhi.entity.Guru;
import com.baizhi.service.GuruService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class TestOperation {
    @Autowired
    private GuruService guruService;

    @Test
    public void test() throws Exception {
        Guru guru = new Guru("14","","","","",new Date(),"","");
        guruService.insert(guru);
        Map<String, Object> map = guruService.selectSeven();
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io","BC-b121a256bfeb4d0e874c71b5a744d092");
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(map);
        goEasy.publish("goeasy_7",s);
    }
}
