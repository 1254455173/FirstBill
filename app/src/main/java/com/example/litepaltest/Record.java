package com.example.litepaltest;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by 陈 on 2020/5/14.
 */

public class Record extends DataSupport {
    private int id;
    private float spend;
    private String comment;
    private Catetalog catetalgo;     // 一条记录对应一个分类
    private int day;

    private Date date;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Catetalog getCatetalgo() {
        return catetalgo;
    }

    public void setCatetalgo(Catetalog catetalgo) {
        this.catetalgo = catetalgo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSpend() {
        return spend;
    }

    public void setSpend(float spend) {
        this.spend = spend;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
