package com.my.restful.controller;


import com.my.restful.pojo.TvSeries;
import com.my.restful.service.TvSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/tvseries")
public class TvSeriesController {

   @Autowired
    private TvSeriesService tvSeriesService;

    //获取全部
    @GetMapping
    @Transactional
    @PreAuthorize("hasRole('author')")
    public List<TvSeries> getAll(){
        List<TvSeries> tvSeriess = new ArrayList<>();
        tvSeriess.add(new TvSeries(1,"first",100,new Date(System.currentTimeMillis())));
        tvSeriess.add(new TvSeries(2,"secend",200,new Date(System.currentTimeMillis())));
        tvSeriess.add(new TvSeries(3,"third",300,new Date(System.currentTimeMillis())));


        //从数据查询阿
        tvSeriess = tvSeriesService.getAll();

        return tvSeriess;

    }

    //获取某个
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('reader')")
    public TvSeries getOne(@PathVariable int id){
        if(101 == id)
            return new TvSeries(101,"first",100,new Date(System.currentTimeMillis()));
        if(102 == id)
            return new TvSeries(102,"secend",100,new Date(System.currentTimeMillis()));

        return new TvSeries(404,"third",100,new Date(System.currentTimeMillis()));
    }


    //插入
    @PostMapping
    public TvSeries insertOne(@RequestBody TvSeries tvSeries){
        TvSeries pojo = new TvSeries();
        pojo.setId(tvSeries.getId());
        pojo.setOriginRelease(new Date(System.currentTimeMillis()));

        System.err.println(tvSeries);
        System.err.println(pojo);


        return pojo;
    }

    //更新
    @PutMapping("/{id}")
    public TvSeries updataOne(@PathVariable int id , @RequestBody TvSeries tvSeries){


        System.err.println(tvSeries);

        tvSeries.setId(9999);

        return tvSeries;
    }


    //删除
    @DeleteMapping("/{id}")
    public Map<String,String> deleteOne(@PathVariable int id , HttpServletRequest request , @RequestParam(value="delete_reason",required = false) String deleteReason){

        System.err.println(id);

        HashMap<String, String> map = new HashMap<>();

        map.put("id","1234");
        map.put("msg","已被删除");
        return map;
    }

    //上传图片（保存图片）
    @PostMapping("/{id}/photos")
    public void addPhoto(@PathVariable int id, @RequestParam("photo") MultipartFile imgFile){

        //保存文件代码
    }

    //返回图片
    @GetMapping(value="/{id}/icon" , produces= MediaType.IMAGE_JPEG_VALUE)
    public byte[] getIcon(@PathVariable int id){

        //获取图片路径创建返回图片代码

        byte[] bytes = new byte[1024];

        return bytes;
    }


}
