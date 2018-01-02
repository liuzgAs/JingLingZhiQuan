package com.sxbwstxpay.model;

import java.io.Serializable;

public class IndexDataBean implements Serializable{
    /**
     * id : 1
     * title : 测试商品
     * price : 100.00
     * stock_num : 8481
     * goods_money : 10.00
     * recom_img : http://api.jlzquan.com/Uploads/goods/59c864295e0b7.png
     * img : http://api.jlzquan.com/Uploads/goods/59c759491bd6c.png
     * act : 0
     * share : {"title":"赚10.00","des1":"只要你的好友通过你的分享购买此商品，你就能赚到至少","desMoney":"10.00","des2":"元利润哦~","shareImg":"http://api.jlzquan.com/Uploads/goods/59c759491bd6c.png","shareTitle":"测试商品","shareUrl":"http://api.jlzquan.com/index.php?g=App&m=Web&a=vip","shareDes":"有人@你　你有一个分享尚未点击"}
     */

    private String id;
    private String title;
    private String price;
    private String stock_num;
    private String goods_money;
    private String score;
    private String recom_img;
    private String img;
    private int act;
    private ShareBean share;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock_num() {
        return stock_num;
    }

    public void setStock_num(String stock_num) {
        this.stock_num = stock_num;
    }

    public String getGoods_money() {
        return goods_money;
    }

    public void setGoods_money(String goods_money) {
        this.goods_money = goods_money;
    }

    public String getRecom_img() {
        return recom_img;
    }

    public void setRecom_img(String recom_img) {
        this.recom_img = recom_img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getAct() {
        return act;
    }

    public void setAct(int act) {
        this.act = act;
    }

    public ShareBean getShare() {
        return share;
    }

    public void setShare(ShareBean share) {
        this.share = share;
    }

}