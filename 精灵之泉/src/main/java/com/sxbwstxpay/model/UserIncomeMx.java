package com.sxbwstxpay.model;

import java.io.Serializable;

/**
 * Created by zhangjiebo on 2017/9/16 0016.
 */
public class UserIncomeMx implements Serializable{
    /**
     * amount : 0
     * amount1 : 0
     * status : 1
     * info : 返回成功！
     */

    private double amount;
    private double amount1;
    private double amount2;
    private double status;
    private String des;
    private double min;

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public double getAmount2() {
        return amount2;
    }

    public void setAmount2(double amount2) {
        this.amount2 = amount2;
    }

    private String info;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount1() {
        return amount1;
    }

    public void setAmount1(double amount1) {
        this.amount1 = amount1;
    }

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
