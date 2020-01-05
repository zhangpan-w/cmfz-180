package com.baizhi.service.impl;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    public void insert(Banner banner) {
        bannerDao.insert(banner);
    }

    @Override
    public void delete(String[] ids) {
        bannerDao.delete(ids);
    }

    @Override
    public void update(Banner banner) {

        if (banner.getImg() == "") {
            banner.setImg(null);
            bannerDao.update(banner);
        }
        bannerDao.update(banner);
    }

    @Override
    public Map<String, Object> query(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        //查所有
        Integer start = (page - 1) * rows;
        List<Banner> select = bannerDao.select(start, rows);
        //查总条数
        Integer count = bannerDao.selectCount();
        //查总页数
        Integer totalPage = count % rows == 0 ? count / rows : count / rows + 1;

        map.put("page", page);
        map.put("rows", select);
        map.put("records", count);
        map.put("total", totalPage);
        return map;
    }

    @Override
    public List<Banner> selectzs() {
        return bannerDao.selectzs();
    }

    @Override
    public List<Banner> selectAll() {
        return bannerDao.selectAll();
    }
}
