package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/14 0014.
 */
public class BankCardaddbefore {

    /**
     * data : [{"id":"6","name":"邮政银行"},{"id":"4","name":"农业银行"},{"id":"3","name":"建设银行"},{"id":"2","name":"中国银行"},{"id":"1","name":"工商银行"}]
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
         * id : 6
         * name : 邮政银行
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
