package com.sxbwstxpay.model;

import java.io.Serializable;

/**
 * Created by zhangjiebo on 2017/9/17 0017.
 */
public class OrderVipbefore implements Serializable{
    /**
     * amount : 499
     * cutAmount : 20
     * cutDes : 使用实名认证奖励20元抵扣
     * img : http://api.jlzquan.com/index.php?g=App&m=Web&a=vip
     * info : 返回成功！
     * status : 1
     * text1 : 活动优惠499.00元
     * text2 : 原价4990.00元
     * url : http://api.jlzquan.com/index.php?g=App&m=Web&a=vip
     * urlTitle : 成为VIP精灵推广商
     */

    private double amount;
    private double cutAmount;
    private String cutDes;
    private String img;
    private String info;
    private String dbb;
    private int status;
    private String text1;
    private String text2;
    private String url;
    private String urlTitle;

    public String getDbb() {
        return dbb;
    }

    public void setDbb(String dbb) {
        this.dbb = dbb;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCutAmount() {
        return cutAmount;
    }

    public void setCutAmount(double cutAmount) {
        this.cutAmount = cutAmount;
    }

    public String getCutDes() {
        return cutDes;
    }

    public void setCutDes(String cutDes) {
        this.cutDes = cutDes;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
}
