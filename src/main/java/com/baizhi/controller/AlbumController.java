package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @RequestMapping("query")
    public Map<String,Object> query(Integer page,Integer rows){
        return albumService.select(page, rows);
    }

    @RequestMapping("operation")
    public Map<String,Object> operation(String oper,Album album,String[] id){
        Map<String,Object> map = new HashMap<>();
        if ("add".equals(oper)){
            String idd = UUID.randomUUID().toString().replace("-","");
            album.setId(idd);
            album.setCounts("0");
            album.setTypes("0");
            albumService.insert(album);
            map.put("albumId",idd);
        }else if("edit".equals(oper)){
            if (album.getImg()==""){
                album.setImg(null);
                albumService.update(album);
            }else{
                albumService.update(album);
                map.put("aid",album.getId());
            }
        }else if("del".equals(oper)){
            albumService.delete(id);
        }
        return map;
    }


    @RequestMapping("upload")
    public void upload(MultipartFile img, String albumId, HttpSession session) {
        //获取文件绝对路径
        String realPath = session.getServletContext().getRealPath("/img");
        //获取文件真正的名字
        String filename = img.getOriginalFilename();

        //为文件加上时间戳
        String name = new Date().getTime()+ "_" + filename ;
        //文件上传
        try {
            img.transferTo(new File(realPath, name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //修改数据库中的图片路径名
        Album album = new Album();
        album.setId(albumId);
        album.setImg(name);
        albumService.update(album);
    }
}
