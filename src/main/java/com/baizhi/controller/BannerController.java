package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping("operation")
    public Map<String, Object> operation(String oper, Banner banner,String[] id) {
        Map<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            String idd = UUID.randomUUID().toString().replace("-", "");
            map.put("bannerId", idd);
            banner.setId(idd);
            bannerService.insert(banner);

        } else if ("edit".equals(oper)) {
            if (banner.getImg() != "") {
                map.put("bid", banner.getId());
                bannerService.update(banner);
            }else {
                bannerService.update(banner);
                map.put("bid",null);
            }
        } else if ("del".equals(oper)) {
                bannerService.delete(id);
        }
        return map;
    }

    @RequestMapping("query")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> query(Integer page, Integer rows) {
        return bannerService.query(page, rows);
    }

    @RequestMapping("upload")
    public void upload(MultipartFile img, String bannerId, HttpSession session) {
        //获取文件绝对路径
        String realPath = session.getServletContext().getRealPath("/img");
        //获取文件真正的名字
        String filename = img.getOriginalFilename();

        //为文件加上时间戳
        String name =  new Date().getTime()+"_"+filename;
        //文件上传
        try {
            img.transferTo(new File(realPath, name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //修改数据库中的图片路径名
        Banner banner = new Banner();
        banner.setId(bannerId);
        banner.setImg(name);
        bannerService.update(banner);
    }

    @RequestMapping("selectzs")
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> selectzs(){
        return bannerService.selectzs();
    }

    @RequestMapping("outPoi")
    public void outPoi() throws Exception {
        List<Banner> list =bannerService.selectAll();
        System.out.println(list);
        for (Banner banner : list) {
            banner.setImg("D:\\JavaEE\\EndProject\\cmfz-180\\src\\main\\webapp\\img\\"+banner.getImg());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图列表","轮播图"),Banner.class,list);
        workbook.write(new FileOutputStream("D:/Test/test3.xls"));
        workbook.close();
    }


}
