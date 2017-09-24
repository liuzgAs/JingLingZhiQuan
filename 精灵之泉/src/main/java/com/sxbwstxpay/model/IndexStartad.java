package com.sxbwstxpay.model;

/**
 * Created by zhangjiebo on 2017/9/24 0024.
 */
public class IndexStartad {
    /**
     * message : 获取短信成功
     * statue : 1
     */

    private String cityName;
    private String info;
    private int status;
    private int cityId;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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
