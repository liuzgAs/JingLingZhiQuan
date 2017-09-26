package com.sxbwstxpay.model;

/**
 * Created by zhangjiebo on 2017/9/26 0026.
 */
public class CartAddcart {
    /**
     * cartId : 249
     * status : 1
     * info : 操作成功！
     */

    private int cartId;
    private int status;
    private String info;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
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
