package com.sxbwstxpay.model;

/**
 * Created by zhangjiebo on 2018/3/6/006.
 *
 * @author ZhangJieBo
 */

public class PayCheckcode {
    /**
     * ewm_img :
     * des : 收款人：精灵
     * status : 1
     * info : 返回成功！
     */

    private String ewm_img;
    private String des;
    private int status;
    private String info;

    public String getEwm_img() {
        return ewm_img;
    }

    public void setEwm_img(String ewm_img) {
        this.ewm_img = ewm_img;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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
