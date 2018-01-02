package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/27 0027.
 */
public class CartNeworderUpload {
    private List<String> cart;
    private String uid;
    private String aid;
    private String tokenTime;
    private String orderAmount;
    private String payMsg;
    private int useScore;

    public CartNeworderUpload(List<String> cart, String uid, String aid, String tokenTime,String orderAmount,String payMsg,int useScore) {
        this.cart = cart;
        this.uid = uid;
        this.aid = aid;
        this.tokenTime = tokenTime;
        this.orderAmount = orderAmount;
        this.payMsg = payMsg;
        this.useScore = useScore;
    }
}
