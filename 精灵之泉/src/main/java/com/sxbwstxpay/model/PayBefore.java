package com.sxbwstxpay.model;

/**
 * Created by liuzhigang on 2018/10/18/018.
 *
 * @author LiuZG
 */
public class PayBefore {

    /**
     * orderSn : AO201810181404029
     * is_credit : 0
     * credit_amount : 0
     * credit_after : 139
     * credit_pay :
     * orderAmount : 139.00
     * status : 1
     * info : 返回成功！
     */

    private String orderSn;
    private int is_credit;
    private String credit_amount;
    private String credit_after;
    private String credit_pay;
    private String orderAmount;
    private int status;
    private String info;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public int getIs_credit() {
        return is_credit;
    }

    public void setIs_credit(int is_credit) {
        this.is_credit = is_credit;
    }

    public String getCredit_amount() {
        return credit_amount;
    }

    public void setCredit_amount(String credit_amount) {
        this.credit_amount = credit_amount;
    }

    public String getCredit_after() {
        return credit_after;
    }

    public void setCredit_after(String credit_after) {
        this.credit_after = credit_after;
    }

    public String getCredit_pay() {
        return credit_pay;
    }

    public void setCredit_pay(String credit_pay) {
        this.credit_pay = credit_pay;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
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
