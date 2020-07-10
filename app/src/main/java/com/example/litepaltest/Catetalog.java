package com.example.litepaltest;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * Created by 陈 on 2020/5/14.
 */

public class Catetalog extends DataSupport {
    private String name;
    private ArrayList<Record> records = new ArrayList<>();
    private int RecordsLen;

    public void setRecordsLen(int recordsLen) {
        this.RecordsLen = recordsLen;
    }

    public int getRecordsLen() {    // 获得 recores 列表长度，表示消费次数
        return RecordsLen;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    public void setRecords(Record recored){ // 设置记录
        this.records.add(recored);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
