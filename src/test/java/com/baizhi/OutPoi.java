package com.baizhi;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Banner;
import com.baizhi.service.AdminService;
import com.baizhi.service.BannerService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class OutPoi {

    @Autowired
    private AdminService adminService;
    @Autowired
    private BannerService bannerService;

    @Test
    public void test1(){
        /*1.创建 excle 文件*/
        HSSFWorkbook workbook = new HSSFWorkbook();

        /*2. 创建工作簿*/
        HSSFSheet sheet = workbook.createSheet("sheet1");

        /*3. 创建行0：代表的是第一行*/
        HSSFRow row = sheet.createRow(0);

        /*4. 创建 单元格*/
        HSSFCell cell = row.createCell(0);
        HSSFCell cell1 = row.createCell(1);
        HSSFCell cell2 = row.createCell(2);

        /*5. 单元格设置*/
        cell.setCellValue("Hello");
        cell1.setCellValue(true);
        cell2.setCellValue(10.0);

        /*6. 指定 excle 输出的位置一级文件名*/
        try{
            workbook.write(new File("D:/Test/test1.xls"));
            workbook.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void test2() throws IOException {
        /*1.excle 文件*/
        HSSFWorkbook workbook = new HSSFWorkbook();
        /*创建字体*/
        HSSFFont font = workbook.createFont();
        /*加粗*/
        font.setBold(true);
        /*红色*/
        font.setColor(Font.COLOR_RED);
        /*字体*/
        font.setFontName("楷体");
        font.setFontHeightInPoints((short)20);

        /*自定义日期类型*/
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        /*单元格的样式*/
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        cellStyle.setDataFormat(format);
        cellStyle.setFont(font);
        /*居中*/
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        /*2 工作簿*/
        HSSFSheet sheet = workbook.createSheet();
        sheet.setColumnWidth(0,30*256);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell =row.createCell(0);
        cell.setCellValue(new Date());
        cell.setCellStyle(cellStyle);
        workbook.write(new File("D:/Test/test2.xls"));
        workbook.close();

    }

    @Test
    public void test3() throws IOException {
        /*创建excle文件 并读入对应的excle*/
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("D:/Test/adminin.xls"));
        /*获取sheet工作簿*/
        HSSFSheet sheet = workbook.createSheet("管理员插入表");
        /*获取最后一行下标*/
        int lastRowNum = sheet.getLastRowNum();
        List<Admin> list = new ArrayList<>();
        for (int i = 1; i <= lastRowNum; i++) {
            String id = sheet.getRow(i).getCell(0).getStringCellValue();
            String username = sheet.getRow(i).getCell(1).getStringCellValue();
            String password = sheet.getRow(i).getCell(2).getStringCellValue();
            Admin admin = new Admin(id,username,password,"9");
            list.add(admin);
        }

        adminService.insertAll(list);
    }

    @Test
    public void test4(){
        List<Banner> list =bannerService.selectAll();
        System.out.println(list);
    }
}
