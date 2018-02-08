package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/30/030.
 *
 * @author ZhangJieBo
 */

public class MapIndex {
    /**
     * data : [{"des":"店主很懒，没留下什么签名","headImg":"http://api.jlzquan.com/Uploads/attachment/201709/17/2_1505638944_1_ad.png","id":"2","lat":"距离：<0","lng":"118.193911","nickName":"小姐姐","sid":1,"title":"发现一个商家"},{"des":"店主很懒，没留下什么签名","headImg":"http://api.jlzquan.com/Uploads/attachment/201709/17/1_1505654273_1_ad.png","id":"1","lat":"距离：<2.68km","lng":"118.167687","nickName":"薛振宇","sid":1,"title":"发现一个商家"},{"des":"店主很懒，没留下什么签名","headImg":"http://api.jlzquan.com/Uploads/avstar.png","id":"224","lat":"距离：<3.99km","lng":"118.154751","nickName":"王春","sid":1,"title":"发现一个商家"},{"des":"店主很懒，没留下什么签名","headImg":"http://api.jlzquan.com/Uploads/avstar.png","id":"2976","lat":"距离：<5.74km","lng":"118.141582","nickName":"13950084079","sid":1,"title":"发现一个商家"}]
     * info : SUUCESS
     * status : 1
     */

    private String info;
    private int status;
    private List<MapMarkerBean> data;

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

    public List<MapMarkerBean> getData() {
        return data;
    }

    public void setData(List<MapMarkerBean> data) {
        this.data = data;
    }


}
