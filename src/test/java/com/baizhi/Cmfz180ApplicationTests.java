package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class Cmfz180ApplicationTests {
    @Autowired
    private AdminService adminService;

    /*@Test
    public void contextLoads() {
        //Admin admin = new Admin("1","admin","admin","123");
        String data = adminService.login("asdasd", "admin");
        System.out.println(data);
    }*/

}
