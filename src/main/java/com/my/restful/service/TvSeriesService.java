package com.my.restful.service;

import com.my.restful.dao.TvSeriesDao;
import com.my.restful.pojo.TvSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TvSeriesService {

    @Autowired
    private TvSeriesDao tvSeriesDao;

    public List<TvSeries> getAll() {
        return tvSeriesDao.getAll();
    }
}
