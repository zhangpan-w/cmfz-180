package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class AtricleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("insert")
    public void insert(Article article){
        article.setId(UUID.randomUUID().toString().replace("-",""));
        article.setCreate_date(new Date());
        article.setTypes("1");
        articleService.insert(article);
    }

    @RequestMapping("update")
    public void update(Article article){
        articleService.update(article);
    }

    @RequestMapping("del")
    public void del(String oper,String[] id){
        if ("del".equals(oper)){
            articleService.delete(id);
        }
    }


    @RequestMapping("query")
    public Map<String,Object> query(Integer page,Integer rows){
        return articleService.select(page,rows);
    }
    @RequestMapping("uploadImg")
    public Map<String,Object> uploadImg(MultipartFile img, HttpSession session, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String realPath = session.getServletContext().getRealPath("/articleImg");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdir();
        }
        String originalFilename = img.getOriginalFilename();
        String name = originalFilename+"_"+ new Date().getTime();
        try {
            img.transferTo(new File(realPath,name));
            map.put("error",0);

            String scheme = request.getScheme();                   //http
            InetAddress localHost = InetAddress.getLocalHost();    //localhost
            String localhost = localHost.toString().split("/")[1];
            int serverPort = request.getServerPort();              //port
            String contextPath = request.getContextPath();         //项目名

            String url = scheme+"://"+localhost+":"+serverPort+contextPath+"/articleImg/"+name;
            map.put("url",url);
        }catch (Exception e){
            e.printStackTrace();
        }
            return map;
    }

    @RequestMapping("getImgs")
    public Map<String,Object> getImgs(HttpSession session,HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        /*拿到所有在图片空间展示的图片*/
        String realPath = session.getServletContext().getRealPath("/articleImg");
        File file = new File(realPath);
        String[] imgs = file.list();
        List<Object> list = new ArrayList<>();

        for (String s : imgs) {
            Map<String,Object> hashmap = new HashMap<>();
            hashmap.put("is_dir",false);
            hashmap.put("has_file",false);
            File file1 = new File(realPath,s);
            long length = file1.length();
            hashmap.put("filesize",length);
            hashmap.put("dirpath","");
            hashmap.put("is_photo",true);
            String extension = FilenameUtils.getExtension(s);
            hashmap.put("filetype",extension);
            hashmap.put("filename",s);
            String s1 = s.split("_")[1];
            Long aLong = Long.valueOf(s1);
            Date date = new Date(aLong);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = sdf.format(date);
            hashmap.put("datetime",format);
            list.add(hashmap);
        }
        map.put("file_list",list);
        map.put("moveup_dir_path","");
        map.put("current_dir_path","");
        try {
            String scheme = request.getScheme();
            InetAddress localHost = InetAddress.getLocalHost();
            String localhost = localHost.toString().split("/")[1];
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String url = scheme+"://"+localhost+":"+serverPort+contextPath+"/articleImg/";
            map.put("current_url",url);
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("total_count",imgs.length);
        return map;
    }
}
