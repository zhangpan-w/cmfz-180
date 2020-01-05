package com.baizhi.service;

import com.baizhi.entity.Article;
import org.omg.CORBA.ObjectHelper;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    //增
    void insert(Article article);
    //删
    void delete(String[] id);
    //改
    void update(Article article);
    //查
    Map<String,Object> select(Integer page,Integer rows);
    List<Article> selectAll();
    Article selectContent(String id);
}
