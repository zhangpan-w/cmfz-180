package com.baizhi.service;

import com.baizhi.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public interface AdminService {
    //增
    void insert(Admin admin);
    //修改密码
    void update(@Param("id") String id, @Param("password") String password);
    //管理员登录
    String login(String username, String password, String code, HttpSession session);
    //查所有
    List<Admin> select();
    //批量插入
    void insertAll(List<Admin> admins);

}
