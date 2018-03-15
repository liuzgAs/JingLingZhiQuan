package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/3/10/010.
 *
 * @author ZhangJieBo
 */

public class HkDetails {
    /**
     * des1 : 还款总金额：10085.00
     * des2 : 手续费85.00
     * data : [{"type":1,"typeDes":"还款","money":"525.00","add_time":"2017.12.23 23:12","tips":"执行成功；执行时间：2017.12.23 23:12"},{"type":0,"typeDes":"消费","money":"125.00","add_time":"2017.12.23 23:12","tips":"执行成功；执行时间：2017.12.23 23:12"},{"type":0,"typeDes":"消费","money":"400.00","add_time":"2017.12.23 23:12","tips":""}]
     * detailsId : 8
     * status : 1
     * info : 返回成功！
     */

    private String des1;
    private String des2;
    private String detailsId;
    private int status;
    private String info;
    private List<DataBean> data;

    public String getDes1() {
        return des1;
    }

    public void setDes1(String des1) {
        this.des1 = des1;
    }

    public String getDes2() {
        return des2;
    }

    public void setDes2(String des2) {
        this.des2 = des2;
    }

    public String getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(String detailsId) {
        this.detailsId = detailsId;
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

    public static class DataBean {
        /**
         * type : 1
         * typeDes : 还款
         * money : 525.00
         * add_time : 2017.12.23 23:12
         * tips : 执行成功；执行时间：2017.12.23 23:12
         */

        private int type;
        private String typeDes;
        private String money;
        private String add_time;
        private String tips;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTypeDes() {
            return typeDes;
        }

        public void setTypeDes(String typeDes) {
            this.typeDes = typeDes;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }
}
