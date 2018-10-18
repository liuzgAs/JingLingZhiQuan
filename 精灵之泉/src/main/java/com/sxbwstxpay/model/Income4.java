package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by liuzhigang on 2018/10/18/018.
 *
 * @author LiuZG
 */
public class Income4 {

    /**
     * data : [{"name":"10-18 10:23:02","des":"使用抵用金抵扣","money":"-28.50"},{"name":"10-18 10:10:00","des":"12121","money":"-4500.00"},{"name":"10-18 10:09:21","des":"使用抵用金全额抵扣","money":"-119.00"},{"name":"10-18 09:50:21","des":"使用抵用金全额抵扣","money":"-358.00"},{"name":"10-18 09:49:53","des":"222","money":"5000.00"},{"name":"10-17 14:28:47","des":"aa","money":"3.00"},{"name":"10-17 14:25:31","des":"test2","money":"2.00"},{"name":"10-17 14:24:46","des":"扣","money":"-0.50"},{"name":"10-17 14:20:37","des":"test","money":"1.00"}]
     * status : 1
     * info : 返回成功！
     */

    private int status;
    private String info;
    private List<DataBean> data;

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

    public static class DataBean {
        /**
         * name : 10-18 10:23:02
         * des : 使用抵用金抵扣
         * money : -28.50
         */

        private String name;
        private String des;
        private String money;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
