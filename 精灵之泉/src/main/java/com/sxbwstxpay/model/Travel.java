package com.sxbwstxpay.model;

/**
 * Created by liuzhigang on 2018/10/18/018.
 *
 * @author LiuZG
 */
public class Travel {

    /**
     * urlTitle : 精灵出行
     * url : http://m.ctrip.com/html5/?allianceid=930495&sid=1504296&sourceid=xx&popup=close&autoawaken=close
     * status : 1
     * info : 返回成功！
     */

    private String urlTitle;
    private String url;
    private int status;
    private String info;

    public String getUrlTitle() {
        return urlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        this.urlTitle = urlTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
