package com.baizhi.service.impl;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterDao chapterDao;

    @Override
    public void insert(Chapter chapter) {
        chapterDao.insert(chapter);
    }

    @Override
    public void delete(String[] id) {
        chapterDao.delete(id);
    }

    @Override
    public void update(Chapter chapter) {
        chapterDao.update(chapter);
    }

    @Override
    public Map<String, Object> select(Integer page, Integer rows,String albumId) {
        Map<String,Object> map = new HashMap<>();
        //查所有
        Integer start = (page-1)*rows;
        List<Chapter> select = chapterDao.select(start,rows,albumId);
        //查总条数
        Integer count = chapterDao.selectCount();
        //查总页数
        Integer total = count%rows==0?count/rows:count/rows+1;

        map.put("page",page);
        map.put("rows",select);
        map.put("records",count);
        map.put("total",total);
        return map;
    }

    @Override
    public List<Chapter> selectAll(String albumId) {
        return chapterDao.selectAll(albumId);
    }

    @Override
    public Integer selectCount() {
        return chapterDao.selectCount();
    }

    @Override
    public Chapter selectById(String id) {
        return chapterDao.selectById(id);
    }
}
