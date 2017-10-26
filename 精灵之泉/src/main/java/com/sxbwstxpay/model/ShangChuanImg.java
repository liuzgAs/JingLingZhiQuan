package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
public class ShangChuanImg {

    private String title;
    private String uid;
    private String id;
    private String tokenTime;
    private List<String> img;

    public ShangChuanImg(String title, String id, String uid, String tokenTime, List<String> img) {
        this.title = title;
        this.id = id;
        this.uid = uid;
        this.tokenTime = tokenTime;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(String tokenTime) {
        this.tokenTime = tokenTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }
}
