package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/26 0026.
 */
public class CartOrder {
    /**
     * cart : [{"id":"252","uid":"2","goods_id":"1","num":"6","spe_name":"规格：a123","spe_id":"3","goods_price":"100.00","goods_img":"http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png","goods_title":"测试商品","goods_money":"10.00"}]
     * shipment : 0
     * sum : 600
     * goods_money : 60
     * sumDes : (包邮产品)
     * ad : []
     * status : 1
     * info : 返回成功！
     */

    private int shipment;
    private int sum;
    private int goods_money;
    private String sumDes;
    private int status;
    private String info;
    private List<CartBean> cart;
    private List<?> ad;

    public int getShipment() {
        return shipment;
    }

    public void setShipment(int shipment) {
        this.shipment = shipment;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getGoods_money() {
        return goods_money;
    }

    public void setGoods_money(int goods_money) {
        this.goods_money = goods_money;
    }

    public String getSumDes() {
        return sumDes;
    }

    public void setSumDes(String sumDes) {
        this.sumDes = sumDes;
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

    public List<?> getAd() {
        return ad;
    }

    public void setAd(List<?> ad) {
        this.ad = ad;
    }

    public static class CartBean {
        /**
         * id : 252
         * uid : 2
         * goods_id : 1
         * num : 6
         * spe_name : 规格：a123
         * spe_id : 3
         * goods_price : 100.00
         * goods_img : http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png
         * goods_title : 测试商品
         * goods_money : 10.00
         */

        private String id;
        private String uid;
        private String goods_id;
        private String num;
        private String spe_name;
        private String spe_id;
        private String goods_price;
        private String goods_img;
        private String goods_title;
        private String goods_money;

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

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
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

        public String getGoods_money() {
            return goods_money;
        }

        public void setGoods_money(String goods_money) {
            this.goods_money = goods_money;
        }
    }
}
