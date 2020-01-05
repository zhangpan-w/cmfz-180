package com.baizhi.service.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    public void insert(Admin admin) {
        adminDao.insert(admin);
    }

    @Override
    public void update(String id, String password) {
        adminDao.update(id,password);
    }

    @Override
    public String login(String username, String password, String code,HttpSession session) {
        String  zcode = (String) session.getAttribute("code");
        if(!zcode.equals(code)){
            return "验证码不正确";
        }
        Admin admin = adminDao.selectByUsername(username);
        if (admin==null||!admin.getPassword().equals(password)){
            return "账号或密码不正确";
        }
        session.setAttribute("username",username);
        return null;
    }

    @Override
    public List<Admin> select() {
        return adminDao.select();
    }

    @Override
    public void insertAll(List<Admin> admins) {
        adminDao.insertAll(admins);
    }
}
