package com.my.restful.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class TvSeries {

    private int id;
    private String name;
    private int seasonCount;
    private Date originRelease;

    public TvSeries() {
    }

    public TvSeries(int id, String name, int seasonCount, Date originRelease) {
        this.id = id;
        this.name = name;
        this.seasonCount = seasonCount;
        this.originRelease = originRelease;
    }

    @Override
    public String toString() {
        return "TvSeriesDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", seasonCount=" + seasonCount +
                ", originRelease=" + originRelease +
                '}';
    }
}
