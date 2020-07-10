package com.example.litepaltest;

import org.litepal.crud.DataSupport;

/**
 * Created by 陈 on 2020/5/14.
 */

public class Config extends DataSupport {
    private int id;
    private String preMoney;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreMoney() {
        return preMoney;
    }

    public void setPreMoney(String preMoney) {
        this.preMoney = preMoney;
    }

}
