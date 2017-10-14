package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/10/14 0014.
 */
public class AddCar {
    private String uid;
    private String tokenTime;
    private String num;
    private String id;
    private List<String> spe_name;

    public AddCar(String uid, String tokenTime, String num, String id, List<String> spe_name) {
        this.uid = uid;
        this.tokenTime = tokenTime;
        this.num = num;
        this.id = id;
        this.spe_name = spe_name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(String tokenTime) {
        this.tokenTime = tokenTime;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getSpe_name() {
        return spe_name;
    }

    public void setSpe_name(List<String> spe_name) {
        this.spe_name = spe_name;
    }
}
