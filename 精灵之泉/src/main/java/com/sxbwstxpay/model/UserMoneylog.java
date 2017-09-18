package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/16 0016.
 */
public class UserMoneylog {
    /**
     * data : [{"orderSn":"ABDF201709150954381","title":"事件：收款到储蓄卡","text":"结","money":"-2.99","blance":"0.00","toMoney":"3.00","paymen":"银联小额D+0","rate":"0.01%","fee":"￥0"},{"orderSn":"ABDS201709150954349","title":"事件：结算金额","text":"结","money":"3.00","blance":"3.00","toMoney":"3.00","paymen":"","rate":"","fee":""},{"orderSn":"ABDS201709142154505","title":"事件：收款到储蓄卡","text":"结","money":"-0.02","blance":"0.00","toMoney":"0.02","paymen":"银联小额D+0","rate":"[\"0.40\",\"0.36\",\"0.33\",\"0.30\",\"0.30\"]%","fee":"￥[\"2\",\"2\",\"2\",\"2\",\"2\"]"},{"orderSn":"ABDS201709142154062","title":"事件：结算金额","text":"结","money":"0.02","blance":"0.02","toMoney":"0.02","paymen":"","rate":"","fee":""}]
     * page : {"page":1,"pageTotal":1,"pageSize":10,"dataTotal":"4"}
     * type : [{"name":"收款","id":1,"act":1},{"name":"结算","id":2,"act":0},{"name":"分润","id":3,"act":0},{"name":"推广","id":4,"act":0}]
     * status : 1
     * info : 返回成功！
     */

    private PageBean page;
    private int status;
    private String info;
    private List<DataBean> data;
    private List<TypeBean> type;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
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

    public List<TypeBean> getType() {
        return type;
    }

    public void setType(List<TypeBean> type) {
        this.type = type;
    }

    public static class PageBean {
        /**
         * page : 1
         * pageTotal : 1
         * pageSize : 10
         * dataTotal : 4
         */

        private int page;
        private int pageTotal;
        private int pageSize;
        private String dataTotal;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal) {
            this.pageTotal = pageTotal;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public String getDataTotal() {
            return dataTotal;
        }

        public void setDataTotal(String dataTotal) {
            this.dataTotal = dataTotal;
        }
    }

    public static class DataBean {
        /**
         * orderSn : ABDF201709150954381
         * title : 事件：收款到储蓄卡
         * text : 结
         * money : -2.99
         * blance : 0.00
         * toMoney : 3.00
         * paymen : 银联小额D+0
         * rate : 0.01%
         * fee : ￥0
         */

        private String orderSn;
        private String title;
        private String text;
        private String money;
        private String blance;
        private String toMoney;
        private String paymen;
        private String rate;
        private String fee;
        private String date;
        private boolean isZhanKai =false;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public boolean isZhanKai() {
            return isZhanKai;
        }

        public void setZhanKai(boolean zhanKai) {
            isZhanKai = zhanKai;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getBlance() {
            return blance;
        }

        public void setBlance(String blance) {
            this.blance = blance;
        }

        public String getToMoney() {
            return toMoney;
        }

        public void setToMoney(String toMoney) {
            this.toMoney = toMoney;
        }

        public String getPaymen() {
            return paymen;
        }

        public void setPaymen(String paymen) {
            this.paymen = paymen;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }
    }

    public static class TypeBean {
        /**
         * name : 收款
         * id : 1
         * act : 1
         */

        private String name;
        private int id;
        private int act;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAct() {
            return act;
        }

        public void setAct(int act) {
            this.act = act;
        }
    }
}
