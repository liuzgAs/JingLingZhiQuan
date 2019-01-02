package com.sxbwstxpay.model;

import java.io.Serializable;

public class AdvsBean implements Serializable{
    /**
     * img : http://app.uyux.vip/attachment/images/20180129/43d67922853ecb099dc861ed5709cea8.jpg
     * code :
     * item_id : 0
     * url :
     */

    private String img;
    private String code;
    private int item_id;
    private String url;
    private String title;
    private String summary;

    public AdvsBean(String img, String code, int item_id, String url, String title, String summary) {
        this.img = img;
        this.code = code;
        this.item_id = item_id;
        this.url = url;
        this.title = title;
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}