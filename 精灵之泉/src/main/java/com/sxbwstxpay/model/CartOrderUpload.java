package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/26 0026.
 */
public class CartOrderUpload {
    private List<String> cart;
    private String uid;
    private String tokenTime;

    public CartOrderUpload(List<String> cart, String uid, String tokenTime) {
        this.cart = cart;
        this.uid = uid;
        this.tokenTime = tokenTime;
    }
}
