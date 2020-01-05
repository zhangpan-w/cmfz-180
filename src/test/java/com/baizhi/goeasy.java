package com.baizhi;

import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class goeasy {
    @Test
    public void test1(){
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io","BC-b121a256bfeb4d0e874c71b5a744d092");
        goEasy.publish("180_channel","我来到你的城市");
    }

    @Test
    public void test2(){
        List<Object> list = new ArrayList<>();
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io","BC-b121a256bfeb4d0e874c71b5a744d092");
        goEasy.publish("echarts",list.toString());
    }
}
