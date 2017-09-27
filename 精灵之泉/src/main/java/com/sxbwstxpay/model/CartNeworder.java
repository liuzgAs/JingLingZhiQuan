package com.sxbwstxpay.model;

/**
 * Created by zhangjiebo on 2017/9/27 0027.
 */
public class CartNeworder {
    private String info;
    private int status;
    private int oid;

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
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
