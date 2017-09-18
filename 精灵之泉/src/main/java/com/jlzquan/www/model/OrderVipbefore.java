package com.jlzquan.www.model;

/**
 * Created by zhangjiebo on 2017/9/17 0017.
 */
public class OrderVipbefore {
    /**
     * img : http://api.jlzquan.com/Uploads/news/59be31162b24d.jpg
     * text1 : 活动优惠399.00元
     * text2 : 原价3990.00元
     * status : 1
     * info : 返回成功！
     */

    private String url;
    private String urlTitle;
    private String text1;
    private String text2;
    private int status;
    private String info;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlTitle() {
        return urlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        this.urlTitle = urlTitle;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
