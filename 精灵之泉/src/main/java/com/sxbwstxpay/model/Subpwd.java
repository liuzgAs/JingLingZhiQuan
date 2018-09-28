package com.sxbwstxpay.model;

/**
 * Created by liuzhigang on 2018/9/28/028.
 *
 * @author LiuZG
 */
public class Subpwd {

    /**
     * video_url :
     * status : 0
     * info : 操作失败！
     */

    private String video_url;
    private int status;
    private String info;

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
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
