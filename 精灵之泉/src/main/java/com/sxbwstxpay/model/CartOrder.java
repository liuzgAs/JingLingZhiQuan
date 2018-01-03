package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/26 0026.
 */
public class CartOrder {

    /**
     * ad : {"address":"魔境仙踪","area":"湖南省-长沙市-岳麓区","consignee":"老乡","id":"14","phone":"15852696589"}
     * cart : [{"goods_id":"1","goods_img":"http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png","goods_money":"10.00","goods_price":"100.00","goods_title":"测试商品","id":"252","num":"6","spe_id":"3","spe_name":"规格：a123","uid":"2"}]
     * goods_money : 60
     * info : 返回成功！
     * shipment : 0
     * status : 1
     * sum : 600
     * sumDes : (包邮产品)
     */

    private AdBean ad;
    private double goods_money;
    private String info;
    private int shipment;
    private int status;
    private int is_address;
    private int is_dbb;
    private int isScore;
    private String sum;
    private String dbbText;
    private String scoreAfter;
    private String moneyAfter;
    private String sumDes;
    private List<CartBean> cart;

    public int getIsScore() {
        return isScore;
    }

    public void setIsScore(int isScore) {
        this.isScore = isScore;
    }

    public int getIs_dbb() {
        return is_dbb;
    }

    public void setIs_dbb(int is_dbb) {
        this.is_dbb = is_dbb;
    }

    public String getDbbText() {
        return dbbText;
    }

    public void setDbbText(String dbbText) {
        this.dbbText = dbbText;
    }

    public String getScoreAfter() {
        return scoreAfter;
    }

    public void setScoreAfter(String scoreAfter) {
        this.scoreAfter = scoreAfter;
    }

    public String getMoneyAfter() {
        return moneyAfter;
    }

    public void setMoneyAfter(String moneyAfter) {
        this.moneyAfter = moneyAfter;
    }

    public int getIs_address() {
        return is_address;
    }

    public void setIs_address(int is_address) {
        this.is_address = is_address;
    }

    public AdBean getAd() {
        return ad;
    }

    public void setAd(AdBean ad) {
        this.ad = ad;
    }

    public double getGoods_money() {
        return goods_money;
    }

    public void setGoods_money(double goods_money) {
        this.goods_money = goods_money;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getShipment() {
        return shipment;
    }

    public void setShipment(int shipment) {
        this.shipment = shipment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getSumDes() {
        return sumDes;
    }

    public void setSumDes(String sumDes) {
        this.sumDes = sumDes;
    }

    public List<CartBean> getCart() {
        return cart;
    }

    public void setCart(List<CartBean> cart) {
        this.cart = cart;
    }

    public static class AdBean {
        /**
         * address : 魔境仙踪
         * area : 湖南省-长沙市-岳麓区
         * consignee : 老乡
         * id : 14
         * phone : 15852696589
         */

        private String address;
        private String area;
        private String consignee;
        private String id;
        private String phone;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public static class CartBean {
        /**
         * goods_id : 1
         * goods_img : http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png
         * goods_money : 10.00
         * goods_price : 100.00
         * goods_title : 测试商品
         * id : 252
         * num : 6
         * spe_id : 3
         * spe_name : 规格：a123
         * uid : 2
         */

        private String goods_id;
        private String goods_img;
        private String goods_money;
        private String goods_price;
        private String goods_title;
        private String id;
        private String num;
        private String spe_id;
        private String spe_name;
        private String uid;
        private String goods_score;
        private int is_dbb;
        private int isScore;

        public int getIsScore() {
            return isScore;
        }

        public void setIsScore(int isScore) {
            this.isScore = isScore;
        }

        public int getIs_dbb() {
            return is_dbb;
        }

        public void setIs_dbb(int is_dbb) {
            this.is_dbb = is_dbb;
        }

        public String getGoods_score() {
            return goods_score;
        }

        public void setGoods_score(String goods_score) {
            this.goods_score = goods_score;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getGoods_money() {
            return goods_money;
        }

        public void setGoods_money(String goods_money) {
            this.goods_money = goods_money;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_title() {
            return goods_title;
        }

        public void setGoods_title(String goods_title) {
            this.goods_title = goods_title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getSpe_id() {
            return spe_id;
        }

        public void setSpe_id(String spe_id) {
            this.spe_id = spe_id;
        }

        public String getSpe_name() {
            return spe_name;
        }

        public void setSpe_name(String spe_name) {
            this.spe_name = spe_name;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
