package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/13 0013.
 */
public class BankPayment {

    /**
     * data : [{"des":"单笔2万，单卡5万，8:00-22:00秒到","fee":"结算：2元/笔","minAmount":"500.00","name":"银联大额D+0","rate":"费率：0.50%"},{"des":"单笔5千，单卡1万，8:00-22:00秒到","fee":"结算：2元/笔","minAmount":"500.00","name":"银联小额D+0","rate":"费率：0.40%"}]
     * info : 返回成功！
     * status : 1
     */

    private String info;
    private int status;
    private List<DataBean> data;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * des : 单笔2万，单卡5万，8:00-22:00秒到
         * fee : 结算：2元/笔
         * minAmount : 500.00
         * name : 银联大额D+0
         * rate : 费率：0.50%
         */

        private String des;
        private String fee;
        private double minAmount;
        private double maxAmount;
        private String name;
        private String rate;
        private String id;

        public double getMaxAmount() {
            return maxAmount;
        }

        public void setMaxAmount(double maxAmount) {
            this.maxAmount = maxAmount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public double getMinAmount() {
            return minAmount;
        }

        public void setMinAmount(double minAmount) {
            this.minAmount = minAmount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }
    }
}
