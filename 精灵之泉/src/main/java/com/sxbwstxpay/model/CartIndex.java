package com.sxbwstxpay.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/26 0026.
 */
public class CartIndex implements Serializable{
    public CartIndex(List<CartBean> cart) {
        this.cart = cart;
    }

    /**
     * cart : [{"id":"249","uid":"2","goods_id":"1","num":"","spe_name":"规格：a456","spe_id":"4","goods_price":"100.00","goods_img":"http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png","goods_title":"测试商品"},{"id":"252","uid":"2","goods_id":"1","num":"6","spe_name":"规格：a123","spe_id":"3","goods_price":"100.00","goods_img":"http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png","goods_title":"测试商品"}]
     * sum : 600
     * status : 1
     * info : 返回成功！
     */

    private String sum;
    private int status;
    private String info;
    private List<CartBean> cart;

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

    public List<CartBean> getCart() {
        return cart;
    }

    public void setCart(List<CartBean> cart) {
        this.cart = cart;
    }

    public static class CartBean implements Serializable{
        public CartBean(String id) {
            this.id = id;

        }

        /**
         * id : 249
         * uid : 2
         * goods_id : 1
         * num :
         * spe_name : 规格：a456
         * spe_id : 4
         * goods_price : 100.00
         * goods_img : http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png
         * goods_title : 测试商品
         */

        private String id;
        private String uid;
        private String goods_id;
        private int num;
        private String spe_name;
        private String spe_id;
        private String goods_price;
        private String goods_img;
        private String goods_title;
        private boolean isCheck =true;

        public boolean ischeck() {
            return isCheck;
        }

        public void setIscheck(boolean check) {
            isCheck = check;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getSpe_name() {
            return spe_name;
        }

        public void setSpe_name(String spe_name) {
            this.spe_name = spe_name;
        }

        public String getSpe_id() {
            return spe_id;
        }

        public void setSpe_id(String spe_id) {
            this.spe_id = spe_id;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getGoods_title() {
            return goods_title;
        }

        public void setGoods_title(String goods_title) {
            this.goods_title = goods_title;
        }
    }
}
