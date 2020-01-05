package com.baizhi.dao;

import com.baizhi.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AdminDao {

    //增
    void insert(Admin admin);
    //修改密码
    void update(@Param("id") String id, @Param("password") String password);
    //根据用户名查
    Admin selectByUsername(String username);
    //查所有
    List<Admin> select();
    //批量插入
    void insertAll(List<Admin> admins);

}
