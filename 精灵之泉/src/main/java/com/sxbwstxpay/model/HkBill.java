package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/3/10/010.
 *
 * @author ZhangJieBo
 */

public class HkBill {
    /**
     * title : 总帐单
     * titleDes : 共两期帐单计划在执行
     * tab : [{"n":"本期应还","v":"322.00"},{"n":"本期已还","v":"20.00"},{"n":"本期待还","v":"302.00"}]
     * data : [{"id":12,"title":"中国农业银行 | 尾号3699 李**","des":"执行中","orderSn":"订单号：AOL999663333","timeStr":"03.03-03.28","tab":[{"n":"保证金","v":"322.00"},{"n":"手续费","v":"20.00"},{"n":"总金额","v":"3002.00"}]},{"id":12,"title":"中国农业银行 | 尾号3699 李**","des":"执行中","orderSn":"订单号：AOL999663333","timeStr":"03.03-03.28","tab":[{"n":"保证金","v":"322.00"},{"n":"手续费","v":"20.00"},{"n":"总金额","v":"3002.00"}]},{"id":12,"title":"中国农业银行 | 尾号3699 李**","des":"执行中","orderSn":"订单号：AOL999663333","timeStr":"03.03-03.28","tab":[{"n":"保证金","v":"322.00"},{"n":"手续费","v":"20.00"},{"n":"总金额","v":"3002.00"}]}]
     * page : {"page":1,"pageTotal":0,"pageSize":10,"dataTotal":0}
     * status : 1
     * info : 返回成功！
     */

    private String title;
    private String titleDes;
    private PageBean page;
    private int status;
    private String info;
    private List<TabBean> tab;
    private List<DataBean> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleDes() {
        return titleDes;
    }

    public void setTitleDes(String titleDes) {
        this.titleDes = titleDes;
    }

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

    public List<TabBean> getTab() {
        return tab;
    }

    public void setTab(List<TabBean> tab) {
        this.tab = tab;
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
         * pageTotal : 0
         * pageSize : 10
         * dataTotal : 0
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

    public static class TabBean {
        /**
         * n : 本期应还
         * v : 322.00
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

    public static class DataBean {
        /**
         * id : 12
         * title : 中国农业银行 | 尾号3699 李**
         * des : 执行中
         * orderSn : 订单号：AOL999663333
         * timeStr : 03.03-03.28
         * tab : [{"n":"保证金","v":"322.00"},{"n":"手续费","v":"20.00"},{"n":"总金额","v":"3002.00"}]
         */

        private int id;
        private String title;
        private String des;
        private String orderSn;
        private String timeStr;
        private List<TabBeanX> tab;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getTimeStr() {
            return timeStr;
        }

        public void setTimeStr(String timeStr) {
            this.timeStr = timeStr;
        }

        public List<TabBeanX> getTab() {
            return tab;
        }

        public void setTab(List<TabBeanX> tab) {
            this.tab = tab;
        }

        public static class TabBeanX {
            /**
             * n : 保证金
             * v : 322.00
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
}
