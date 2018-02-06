package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/2/6/006.
 *
 * @author ZhangJieBo
 */

public class StoreSalesmanage {
    /**
     * data : [{"addTime":"2018-01-17","amount":70,"money":"0.70"},{"addTime":"2018-01-17","amount":70,"money":"2.10"},{"addTime":"2018-01-17","amount":64,"money":"1.92"},{"addTime":"2018-01-17","amount":33.8,"money":"0.68"},{"addTime":"2018-01-17","amount":12.8,"money":"0.26"},{"addTime":"2018-01-17","amount":149,"money":"1.49"},{"addTime":"2018-01-17","amount":149,"money":"4.47"},{"addTime":"2018-01-17","amount":59.4,"money":"1.78"},{"addTime":"2018-01-16","amount":23.6,"money":"0.47"},{"addTime":"2018-01-16","amount":23.6,"money":"0.24"}]
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
         * addTime : 2018-01-17
         * amount : 70
         * money : 0.70
         */

        private String addTime;
        private String amount;
        private String money;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
