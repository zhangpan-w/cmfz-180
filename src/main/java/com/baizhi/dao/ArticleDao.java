package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    //增
    void insert(Article article);
    //删
    void delete(String[] id);
    //改
    void update(Article article);
    //查
    List<Article> select(@Param("start")Integer start,@Param("rows")Integer rows);
    List<Article> selectAll();
    Article selectContent(String id);
    Integer selectCount();
}
