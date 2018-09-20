package com.sxbwstxpay.model;

/**
 * Created by liuzhigang on 2018/9/17/017.
 *
 * @author LiuZG
 */
public class WoDe {

    /**
     * title :
     * res : 0
     */

    private String title;
    private int res;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public WoDe(String title, int res) {
        this.title = title;
        this.res = res;
    }
}
