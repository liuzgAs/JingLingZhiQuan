package com.sxbwstxpay.model;

/**
 * Created by zhangjiebo on 2017/8/22 0022.
 */
public class IndexBonusbefore {
    /**
     * message : 获取短信成功
     * statue : 1
     */

    private String info;
    private String url;
    private String des;
    private int status;
    private int goRealName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getGoRealName() {
        return goRealName;
    }

    public void setGoRealName(int goRealName) {
        this.goRealName = goRealName;
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
