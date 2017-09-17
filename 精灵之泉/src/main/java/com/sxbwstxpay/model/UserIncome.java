package com.sxbwstxpay.model;

/**
 * Created by zhangjiebo on 2017/9/15 0015.
 */
public class UserIncome {
    /**
     * amount1 : 0
     * amount2 : 0
     * amount3 : 0
     * status : 1
     * info : 返回成功！
     */

    private double amount1;
    private double amount2;
    private double amount3;
    private int status;
    private String info;

    public double getAmount1() {
        return amount1;
    }

    public void setAmount1(double amount1) {
        this.amount1 = amount1;
    }

    public double getAmount2() {
        return amount2;
    }

    public void setAmount2(double amount2) {
        this.amount2 = amount2;
    }

    public double getAmount3() {
        return amount3;
    }

    public void setAmount3(double amount3) {
        this.amount3 = amount3;
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
