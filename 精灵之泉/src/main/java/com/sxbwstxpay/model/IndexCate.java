package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/25 0025.
 */
public class IndexCate {
    /**
     * cate : [{"id":0,"name":"限时抢购"},{"id":"5","name":"水果生鲜"},{"id":"6","name":"零食饮料"},{"id":"7","name":"日用家居"},{"id":"8","name":"母婴儿童"},{"id":"9","name":"本地生活"}]
     * status : 1
     * info : 返回成功！
     */

    private int status;
    private String info;
    private String vipNum;

    public String getVipNum() {
        return vipNum;
    }

    public void setVipNum(String vipNum) {
        this.vipNum = vipNum;
    }

    private List<CateBean> cate;

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

    public List<CateBean> getCate() {
        return cate;
    }

    public void setCate(List<CateBean> cate) {
        this.cate = cate;
    }

    public static class CateBean {
        /**
         * id : 0
         * name : 限时抢购
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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
