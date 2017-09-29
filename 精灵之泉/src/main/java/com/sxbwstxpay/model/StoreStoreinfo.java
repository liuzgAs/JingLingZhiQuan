package com.sxbwstxpay.model;

/**
 * Created by zhangjiebo on 2017/9/29 0029.
 */
public class StoreStoreinfo {
    /**
     * name : 1
     * intro :
     * logo : http://api.jlzquan.com/Uploads/avstar.png
     * banner : http://api.jlzquan.com/Uploads/avstar.png
     * wx :
     * status : 1
     * info : 返回成功！
     */

    private String name;
    private String intro;
    private String logo;
    private String banner;
    private String wx;
    private int status;
    private String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
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
