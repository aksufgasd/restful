package com.my.restful.dao;


import com.my.restful.pojo.TvSeries;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TvSeriesDao {

    /**
     * 获取电视剧集合
     * @return
     */
    @Select("select id,name,seasonCount,originRelease from tvseries")
    public List<TvSeries> getAll();

}
