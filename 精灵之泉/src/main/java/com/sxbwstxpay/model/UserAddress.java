package com.sxbwstxpay.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/27 0027.
 */
public class UserAddress {
    /**
     * data : [{"id":"12","consignee":"小张","phone":"15872268628","address":"仪表路73号","area":"湖北省-咸宁市-崇阳县","defa":"1"}]
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

    public static class DataBean implements Serializable{
        /**
         * id : 12
         * consignee : 小张
         * phone : 15872268628
         * address : 仪表路73号
         * area : 湖北省-咸宁市-崇阳县
         * defa : 1
         */

        private String id;
        private String consignee;
        private String phone;
        private String address;
        private String area;
        private int defa;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public int getDefa() {
            return defa;
        }

        public void setDefa(int defa) {
            this.defa = defa;
        }
    }
}
