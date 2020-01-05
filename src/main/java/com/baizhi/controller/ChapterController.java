package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;

    @RequestMapping("query")
    public Map<String,Object> query(Integer page, Integer rows,String albumId){
        return chapterService.select(page,rows,albumId);
    }

    @RequestMapping("operation")
    public Map<String,Object> operation(String oper, Chapter chapter, String[] id){
        Map<String,Object> map = new HashMap<>();
        if ("add".equals(oper)){
            String idd = UUID.randomUUID().toString().replace("-","");
            chapter.setId(idd);
            chapterService.insert(chapter);
            map.put("cid",idd);
        }else if("edit".equals(oper)){
            if (chapter.getSrc()==""){
                chapter.setSrc(null);
                chapterService.update(chapter);
                map.put("cid",null);
            }else{
                chapterService.update(chapter);
                map.put("cid",chapter.getId());
            }
            chapterService.update(chapter);
        }else if("del".equals(oper)){
            Chapter selectById = chapterService.selectById(id[0]);
            chapterService.delete(id);

            /*计算Album的counts*/
            Integer count = chapterService.selectCount();
            Album album = new Album();
            album.setId(selectById.getAlbum_id());
            album.setCounts(String.valueOf(count));
            albumService.update(album);
        }
        return map;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile src, String chapterId, HttpSession session,String albumId) {
        //获取文件绝对路径
        String realPath = session.getServletContext().getRealPath("/music");
        //获取文件真正的名字
        String filename = src.getOriginalFilename();

        //为文件加上时间戳
        String name = new Date().getTime()+ "_" +filename  ;
        //文件上传
        try {
            src.transferTo(new File(realPath, name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //修改数据库中的图片路径名
        Encoder encoder = new Encoder();
        long ls = 0;
        MultimediaInfo m;
        try {
            m = encoder.getInfo(new File(realPath,name));  //这里传入的是文件对象
            ls = m.getDuration()/1000;  //得到一个long类型的时长

        } catch (Exception e) {
            System.out.println("获取音频时长有误：" + e.getMessage());
        }

        /*将数字转化为两位小数*/
        double len = new File(realPath, name).length()/1024.0/1024;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format = decimalFormat.format(new BigDecimal(len));

        /*计算Album的counts*/
        Integer count = chapterService.selectCount();
        Album album = new Album();
        album.setId(albumId);
        album.setCounts(String.valueOf(count));
        albumService.update(album);

        Chapter chapter = new Chapter();
        chapter.setDuration(String.valueOf(ls/60)+"分"+String.valueOf(ls%60)+"秒");
        chapter.setSizes(format+"MB");
        chapter.setAlbum_id(albumId);
        chapter.setId(chapterId);
        chapter.setSrc(name);
        chapterService.update(chapter);
    }

    //文件下载
    @RequestMapping("download")
    public void download(String src, HttpServletResponse response,HttpSession session){
        try {
            /*通过相对路径获取绝对路径*/
            String encode = URLEncoder.encode(src, "UTF-8");
            response.setHeader("content-disposition","attachment;fileName="+encode);
            String realPath = session.getServletContext().getRealPath("/music");
            File file = new File(realPath,src);
            /*检查文件是否存在*/
            if (file.exists()){
                FileInputStream stream = null;
                stream = new FileInputStream(file);
                /*通过响应输出流给Client打印数据*/
                ServletOutputStream outputStream = response.getOutputStream();
                /*文件传输*/
                byte[] bytes = new byte[1024];
                while (true){
                    int i = stream.read(bytes, 0, bytes.length);
                    if (i==-1) break;
                    outputStream.write(bytes,0,i);
                }
                stream.close();
                outputStream.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
