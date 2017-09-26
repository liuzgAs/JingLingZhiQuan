package com.sxbwstxpay.model;

public class ShareBean {
    /**
     * title : 赚10.00
     * des1 : 只要你的好友通过你的分享购买此商品，你就能赚到至少
     * desMoney : 10.00
     * des2 : 元利润哦~
     * shareImg : http://api.jlzquan.com/Uploads/goods/59c759491bd6c.png
     * shareTitle : 测试商品
     * shareUrl : http://api.jlzquan.com/index.php?g=App&m=Web&a=vip
     * shareDes : 有人@你　你有一个分享尚未点击
     */

    private String title;
    private String des1;
    private String desMoney;
    private String des2;
    private String shareImg;
    private String shareTitle;
    private String shareUrl;
    private String shareDes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes1() {
        return des1;
    }

    public void setDes1(String des1) {
        this.des1 = des1;
    }

    public String getDesMoney() {
        return desMoney;
    }

    public void setDesMoney(String desMoney) {
        this.desMoney = desMoney;
    }

    public String getDes2() {
        return des2;
    }

    public void setDes2(String des2) {
        this.des2 = des2;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareDes() {
        return shareDes;
    }

    public void setShareDes(String shareDes) {
        this.shareDes = shareDes;
    }
}