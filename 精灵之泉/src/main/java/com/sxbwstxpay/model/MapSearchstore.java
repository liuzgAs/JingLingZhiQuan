package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/2/8/008.
 *
 * @author ZhangJieBo
 */

public class MapSearchstore {

    /**
     * data : [{"id":1,"lng":"118.177375","lat":"24.492775","settled":"1","nickName":"薛振宇","headImg":"http://api.jlzquan.com/Uploads/attachment/201709/17/1_1505654273_1_ad.png","title":"发现一个商家","des":"店主很懒，没留下什么签名","distance":"距离：<2.45km","sid":1}]
     * status : 1
     * info : SUUCESS
     */

    private int status;
    private String info;
    private List<MapMarkerBean> data;

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

    public List<MapMarkerBean> getData() {
        return data;
    }

    public void setData(List<MapMarkerBean> data) {
        this.data = data;
    }

}
