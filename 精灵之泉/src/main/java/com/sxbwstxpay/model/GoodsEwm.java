package com.sxbwstxpay.model;

/**
 * Created by zhangjiebo on 2017/9/29 0029.
 */
public class GoodsEwm {
    /**
     * img : http://api.jlzquan.com/Uploads/goods/59cda34bcec81.png
     * text1 : 五知人在售的产品
     * text2 : 大润谷蔓越莓曲奇饼干200g*3 抹茶零食小吃 好吃的散装休闲零食
     * text3 : 市场价1.88元
     * ewmImg : http://api.jlzquan.com/Uploads/ewm/ewn_2.jpg
     * status : 1
     * info : 返回成功！
     */

    private String img;
    private String text1;
    private String text2;
    private String text3;
    private String ewmImg;
    private int status;
    private String info;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getEwmImg() {
        return ewmImg;
    }

    public void setEwmImg(String ewmImg) {
        this.ewmImg = ewmImg;
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
