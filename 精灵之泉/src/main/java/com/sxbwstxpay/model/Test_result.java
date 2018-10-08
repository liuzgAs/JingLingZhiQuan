package com.sxbwstxpay.model;

import java.io.Serializable;

/**
 * Created by liuzhigang on 2018/9/20/020.
 *
 * @author LiuZG
 */
public class Test_result implements Serializable{

    /**
     * cid : 17
     * style : 端庄范
     * bgimg : http://api.jlzquan.com/Uploads/web/5ba48e08ae863.jpg
     * btn_txt : 查看专属我的风格搭配
     * status : 1
     * info : 返回成功！
     */

    private String cid;
    private String url;
    private String style;
    private String bgimg;
    private String btn_txt;
    private int status;
    private String info;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getBgimg() {
        return bgimg;
    }

    public void setBgimg(String bgimg) {
        this.bgimg = bgimg;
    }

    public String getBtn_txt() {
        return btn_txt;
    }

    public void setBtn_txt(String btn_txt) {
        this.btn_txt = btn_txt;
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
