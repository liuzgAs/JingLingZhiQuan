package com.sxbwstxpay.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
public class BigImgList implements Serializable{
    private List<String> imgList;

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public BigImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
