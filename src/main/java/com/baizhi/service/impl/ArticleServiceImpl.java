package com.baizhi.service.impl;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public void insert(Article article) {
        articleDao.insert(article);
    }

    @Override
    public void delete(String[] id) {
        articleDao.delete(id);
    }

    @Override
    public void update(Article article) {
        articleDao.update(article);
    }

    @Override
    public Map<String, Object> select(Integer page, Integer rows) {
        Integer start = (page-1)*rows;
        List<Article> select = articleDao.select(start, rows);

        Integer count = articleDao.selectCount();

        Integer total = count%rows==0?count/rows:count/rows+1;

        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",select);
        map.put("records",count);
        map.put("total",total);
        return map;
    }

    @Override
    public List<Article> selectAll() {
        return articleDao.selectAll();
    }

    @Override
    public Article selectContent(String id) {
        return articleDao.selectContent(id);
    }
}
