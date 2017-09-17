package com.sxbwstxpay.model;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
public class OkObject {
    private HashMap<String, String> params;
    private String url;
    private String json;

    public String getJson() {
        JSONObject jsonObject = new JSONObject(params);
        return jsonObject.toString();
    }

    public void setJson(String json) {
        this.json = json;
    }

    public OkObject(HashMap<String, String> params, String url) {
        this.params = params;
        this.url = url;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
