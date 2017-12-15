package com.sxbwstxpay.model;

/**
 * Created by zhangjiebo on 2017/12/15 0015.
 *
 * @author ZhangJieBo
 */

public class BankBankorderbefore {
    /**
     * message : 获取短信成功
     * statue : 1
     */

    private String info;
    private String oid;
    private int status;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
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
