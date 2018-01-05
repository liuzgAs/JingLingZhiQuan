package com.sxbwstxpay.model;

import java.io.Serializable;

/**
 * Created by zhangjiebo on 2017/8/22 0022.
 */
public class IndexBonusget implements Serializable{
    /**
     * message : 获取短信成功
     * statue : 1
     */

    private String info;
    private String money;
    private String des;
    private int status;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
