package com.sxbwstxpay.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/15 0015.
 */
public class ExtraMap implements Serializable{
    /**
     * uid : 1
     * code : app_i
     * _ALIYUN_NOTIFICATION_ID_ : 501796
     */

    private String uid;
    private String code;
    private String title;
    private String summary;
    private String url;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ExtraMap(String uid, String code, String title,String url) {
        this.uid = uid;
        this.code = code;
        this.title = title;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String _ALIYUN_NOTIFICATION_ID_;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String get_ALIYUN_NOTIFICATION_ID_() {
        return _ALIYUN_NOTIFICATION_ID_;
    }

    public void set_ALIYUN_NOTIFICATION_ID_(String _ALIYUN_NOTIFICATION_ID_) {
        this._ALIYUN_NOTIFICATION_ID_ = _ALIYUN_NOTIFICATION_ID_;
    }
}
