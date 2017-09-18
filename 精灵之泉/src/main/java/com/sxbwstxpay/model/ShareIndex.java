package com.sxbwstxpay.model;

/**
 * Created by zhangjiebo on 2017/9/17 0017.
 */
public class ShareIndex {
    /**
     * qrcode : http://api.jlzquan.com/Uploads/avatar/qrcode.jpg
     * share_register_url : http://api.jlzquan.com/index.php?g=App&m=Web&a=index&userid=2
     * share_title : 精灵之泉＝聚合支付＋美食生活！
     * share_description : 刷卡＋购物，自用省钱，Ta用赚钱，长期躺赚！立即启动神器吧！
     * share_icon : http://api.jlzquan.com/Uploads/avatar/180.png
     * status : 1
     */

    private String qrcode;
    private String share_register_url;
    private String share_title;
    private String share_description;
    private String share_icon;
    private String info;
    private int status;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getShare_register_url() {
        return share_register_url;
    }

    public void setShare_register_url(String share_register_url) {
        this.share_register_url = share_register_url;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_description() {
        return share_description;
    }

    public void setShare_description(String share_description) {
        this.share_description = share_description;
    }

    public String getShare_icon() {
        return share_icon;
    }

    public void setShare_icon(String share_icon) {
        this.share_icon = share_icon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
