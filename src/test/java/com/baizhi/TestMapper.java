package com.baizhi;

import com.baizhi.dao.GuruDao;
import com.baizhi.vo.SevenDay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class TestMapper {
    @Autowired
    private GuruDao guruDao;

    @Test
    public void test(){
        List<SevenDay> sevenDays = guruDao.selectSeven();
        for (SevenDay sevenDay : sevenDays) {
            System.out.println(sevenDay+"----------");
        }
    }
}
