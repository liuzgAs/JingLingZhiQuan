package com.sxbwstxpay.model;

import java.io.Serializable;

/**
 * Created by zhangjiebo on 2018/3/10/010.
 *
 * @author ZhangJieBo
 */

public class ChaKanMingXi implements Serializable{
    private String uid;
    private String money;
    private String id;
    private String days;
    private int num;
    private String payment;

    public ChaKanMingXi(String uid, String money, String id, String days, int num, String payment) {
        this.uid = uid;
        this.money = money;
        this.id = id;
        this.days = days;
        this.num = num;
        this.payment = payment;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
