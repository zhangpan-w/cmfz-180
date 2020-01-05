package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;

    @Override
    public void insert(Album album){
        albumDao.insert(album);
    }

    @Override
    public void delete(String[] id) {
        albumDao.delete(id);
    }

    @Override
    public void update(Album album) {
        albumDao.update(album);
    }

    @Override
    public Map<String, Object> select(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        //查所有
        Integer start = (page - 1) * rows;
        List<Album> select = albumDao.select(start, rows);
        //查总条数
        Integer count = albumDao.selectCount();
        //查总页数
        Integer totalPage = count % rows == 0 ? count / rows : count / rows + 1;

        map.put("page", page);
        map.put("rows", select);
        map.put("records", count);
        map.put("total", totalPage);
        return map;
    }

    @Override
    public List<Album> selectAll() {
        return albumDao.selectAll();
    }

    @Override
    public Album selectById(String id) {
        return albumDao.selectById(id);
    }
}
