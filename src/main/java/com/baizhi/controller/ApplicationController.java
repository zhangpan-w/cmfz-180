package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.BannerService;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApplicationController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("first_page")
    public Map<String,Object> FirstPage(String type){
        Map<String,Object> map = new HashMap<>();
            List<Map<String,Object>> list1 = new ArrayList<>();
            Map<String,Object> map1 = new HashMap<>();
            List<Banner> banners = bannerService.selectAll();
            for (Banner banner : banners) {
                map1.put("thumbnail",banner.getImg());
                map1.put("desc",banner.getTitle());
                map1.put("id",banner.getId());
                list1.add(map1);
            }

            List<Map<String,Object>> list2 = new ArrayList<>();
            Map<String,Object> map2 = new HashMap<>();
            List<Album> albums = albumService.selectAll();
            for (Album album : albums) {
                map2.put("thumbnail",album.getImg());
                map2.put("title",album.getTitle());
                map2.put("author",album.getAuthor());
                map2.put("type",album.getTypes());
                map2.put("set count",album.getCounts());
                map2.put("create_date",album.getCreate_date());
                list2.add(map2);
            }
            List<Map<String,Object>> list3 = new ArrayList<>();
            Map<String,Object> map3 = new HashMap<>();
            List<Article> articles = articleService.selectAll();
            for (Article article : articles) {
                map3.put("title",article.getTitle());
                map3.put("author",article.getAuthor());
                map3.put("type",article.getTypes());
                map3.put("create_date",article.getCreate_date());
                list3.add(map3);
            }

        if ("all".equals(type)) {
            map.put("header", list1);
            map.put("body", list2);
            map.put("artical", list3);
        }else if ("wen".equals(type)) {
            map.put("album", list2);
        } else if ("si".equals(type)){
            map.put("artical",list3);
        }
        return map;
    }

    @RequestMapping("wen")
    public Map<String,Object> wen(String id,String uid){
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> list1 = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        Album album = albumService.selectById(id);
            map1.put("thumbnail",album.getImg());
            map1.put("title",album.getTitle());
            map1.put("score",album.getScore());
            map1.put("author",album.getAuthor());
            map1.put("set count",album.getCounts());
            map1.put("brief",album.getBrief());
            map1.put("create_date",album.getCreate_date());
            list1.add(map1);
            map.put("introduction",list1);
        List<Map<String,Object>> list2 = new ArrayList<>();
        Map<String,Object> map2 = new HashMap<>();
        List<Chapter> chapters = chapterService.selectAll(id);
        for (Chapter chapter : chapters) {
            map2.put("title",chapter.getTitle());
            map2.put("download url",chapter.getSrc());
            map2.put("size",chapter.getSizes());
            map2.put("duration",chapter.getDuration());
            list2.add(map2);
        }
        map.put("list",list2);

        return map;
        }
    }

