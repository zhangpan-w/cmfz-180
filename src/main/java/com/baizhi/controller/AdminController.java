package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //管理员注册
    @RequestMapping("insert")
    public String insert(Admin admin){
        adminService.insert(admin);
        return null;
    }

    //管理员俢改密码
    @RequestMapping("update")
    public String update(String id,String password){
        adminService.update(id, password);
        return null;
    }

    //管理员登录
    @RequestMapping("login")
    public String login(String username, String password, String code,HttpSession session){
        return adminService.login(username,password,code,session);
    }

    @RequestMapping("outPoi")
    public void outPoi() throws IOException {
        /*创建excle文件*/
        HSSFWorkbook workbook = new HSSFWorkbook();
        /*字体*/
        HSSFFont font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        font.setBold(true);
        font.setFontName("微软雅黑");

        /*设置格式*/
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        /*创建工作簿*/
        HSSFSheet sheet = workbook.createSheet("管理员表");
        /*创建行*/
        HSSFRow row = sheet.createRow(0);
        /*自定义标题行*/
        String[] titles = {"id","用户名","密码"};
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(title);
            cell.setCellStyle(cellStyle);
        }


        List<Admin> admins = adminService.select();
        for(int i=0;i<admins.size();i++){
            HSSFRow row1 = sheet.createRow(i+1);
            row1.createCell(0).setCellValue(admins.get(i).getId());
            row1.createCell(1).setCellValue(admins.get(i).getUsername());
            row1.createCell(2).setCellValue(admins.get(i).getPassword());
        }

        workbook.write(new File("D:/Test/admin.xls"));
        workbook.close();
    }

    @RequestMapping("inPoi")
    public void inPoi() throws Exception{


    }
}
