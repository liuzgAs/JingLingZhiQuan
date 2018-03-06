package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/3/6/006.
 *
 * @author ZhangJieBo
 */

public class UserOrderinfo {
    /**
     * notice : 精灵之泉售后只在app内进行，请不要点击陌生链击，或向任人透露银行卡信息，谨防诈骗！
     * address : {"area":"北京-北京-东城区","address":"按摩默默","consignee":"口令君","phone":"18559666382"}
     * data : [{"goods_name":"密云农家新鲜有机西兰花花椰菜生鲜蔬菜绿菜花当日采摘 3个","goods_spe":" 申请售后功能，限在七天内可申请","img":"http://api.jlzquan.com/Uploads/attachment/goods/180301/thumb_484069001519838289.jpg","quantity":"1","goods_price":"66.00","goods_id":"4097","id":"1090","afterState":1,"confirm_time":"0","goods_money":"1.98","my":"30"}]
     * desList : [{"n":"订单金额","v":"66.00"},{"n":"运费","v":"包邮"}]
     * sumDes : 实际付款(含运费)：
     * sum : 66.00
     * orderAmount : 66.00
     * goods_money : 1.98
     * details : 订单编号：AO201803051604335
     下单时间：2018-03-05 16:04:33
     付款时间：2018-03-05 16:04:52
     付款类型：支付宝
     订单状态：待确认
     * status : 1
     * info : SUUCESS！
     */

    private String notice;
    private AddressBean address;
    private String sumDes;
    private String sum;
    private String orderAmount;
    private double goods_money;
    private String details;
    private int status;
    private String info;
    private List<DataBean> data;
    private List<DesListBean> desList;

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public String getSumDes() {
        return sumDes;
    }

    public void setSumDes(String sumDes) {
        this.sumDes = sumDes;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getGoods_money() {
        return goods_money;
    }

    public void setGoods_money(double goods_money) {
        this.goods_money = goods_money;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<DesListBean> getDesList() {
        return desList;
    }

    public void setDesList(List<DesListBean> desList) {
        this.desList = desList;
    }

    public static class AddressBean {
        /**
         * area : 北京-北京-东城区
         * address : 按摩默默
         * consignee : 口令君
         * phone : 18559666382
         */

        private String area;
        private String address;
        private String consignee;
        private String phone;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public static class DataBean {
        /**
         * goods_name : 密云农家新鲜有机西兰花花椰菜生鲜蔬菜绿菜花当日采摘 3个
         * goods_spe :  申请售后功能，限在七天内可申请
         * img : http://api.jlzquan.com/Uploads/attachment/goods/180301/thumb_484069001519838289.jpg
         * quantity : 1
         * goods_price : 66.00
         * goods_id : 4097
         * id : 1090
         * afterState : 1
         * confirm_time : 0
         * goods_money : 1.98
         * my : 30
         */

        private String goods_name;
        private String goods_spe;
        private String img;
        private String quantity;
        private String goods_price;
        private String goods_id;
        private String id;
        private int afterState;
        private String confirm_time;
        private String goods_money;
        private String my;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_spe() {
            return goods_spe;
        }

        public void setGoods_spe(String goods_spe) {
            this.goods_spe = goods_spe;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getAfterState() {
            return afterState;
        }

        public void setAfterState(int afterState) {
            this.afterState = afterState;
        }

        public String getConfirm_time() {
            return confirm_time;
        }

        public void setConfirm_time(String confirm_time) {
            this.confirm_time = confirm_time;
        }

        public String getGoods_money() {
            return goods_money;
        }

        public void setGoods_money(String goods_money) {
            this.goods_money = goods_money;
        }

        public String getMy() {
            return my;
        }

        public void setMy(String my) {
            this.my = my;
        }
    }

    public static class DesListBean {
        /**
         * n : 订单金额
         * v : 66.00
         */

        private String n;
        private String v;

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }
    }
}
