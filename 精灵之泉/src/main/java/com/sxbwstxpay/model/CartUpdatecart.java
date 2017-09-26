package com.sxbwstxpay.model;

/**
 * Created by zhangjiebo on 2017/9/26 0026.
 */
public class CartUpdatecart {
    /**
     * sum : 600
     * status : 1
     * info : 操作成功！
     */

    private String sum;
    private int status;
    private String info;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
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
