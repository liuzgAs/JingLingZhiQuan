package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/25/025.
 *
 * @author ZhangJieBo
 */

public class CouponIndex {
    /**
     * data : [{"name":"满500减50","money":"50.00","e_time":"有效期至：2019.01.24","useState":1,"des":"全品类通用","moneyDes":"立即使用"},{"name":"满500减50","money":"50.00","e_time":"有效期至：2019.01.24","useState":1,"des":"全品类通用","moneyDes":"立即使用"},{"name":"满500减50","money":"50.00","e_time":"有效期至：2019.01.24","useState":1,"des":"全品类通用","moneyDes":"立即使用"},{"name":"满500减50","money":"50.00","e_time":"有效期至：2019.01.24","useState":1,"des":"全品类通用","moneyDes":"立即使用"},{"name":"5元优惠卷","money":"5.00","e_time":"有效期至：2018.04.24","useState":1,"des":"全品类通用","moneyDes":"立即使用"},{"name":"5元优惠卷","money":"5.00","e_time":"有效期至：2018.04.24","useState":1,"des":"全品类通用","moneyDes":"立即使用"}]
     * page : {"page":1,"pageTotal":1,"pageSize":10,"dataTotal":6}
     * status : 1
     * info : 返回成功！
     */

    private PageBean page;
    private int status;
    private String info;
    private List<DataBean> data;

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

    public static class PageBean {
        /**
         * page : 1
         * pageTotal : 1
         * pageSize : 10
         * dataTotal : 6
         */

        private int page;
        private int pageTotal;
        private int pageSize;
        private int dataTotal;

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

        public int getDataTotal() {
            return dataTotal;
        }

        public void setDataTotal(int dataTotal) {
            this.dataTotal = dataTotal;
        }
    }

    public static class DataBean {
        /**
         * name : 满500减50
         * money : 50.00
         * e_time : 有效期至：2019.01.24
         * useState : 1
         * des : 全品类通用
         * moneyDes : 立即使用
         */

        private String name;
        private String money;
        private String e_time;
        private int useState;
        private String des;
        private String moneyDes;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getE_time() {
            return e_time;
        }

        public void setE_time(String e_time) {
            this.e_time = e_time;
        }

        public int getUseState() {
            return useState;
        }

        public void setUseState(int useState) {
            this.useState = useState;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getMoneyDes() {
            return moneyDes;
        }

        public void setMoneyDes(String moneyDes) {
            this.moneyDes = moneyDes;
        }
    }
}
