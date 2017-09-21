package com.sxbwstxpay.model;

/**
 * Created by zhangjiebo on 2017/8/22 0022.
 */
public class OrderWxPay {
    /**
     * message : 获取短信成功
     * statue : 1
     */

    private String info;
    private int status;
    private String img;

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
}
