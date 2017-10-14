package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/16 0016.
 */
public class UserMoneylog {
    /**
     * data : [[{"left":"","right":"2017-10-13 18:15"},{"left":"实名认证","right":"20.00"}],[{"left":"","right":"2017-10-13 15:16"},{"left":"实名认证","right":"20.00"}],[{"left":"","right":"2017-10-12 10:55"},{"left":"实名认证","right":"20.00"}],[{"left":"","right":"2017-10-11 17:20"},{"left":"实名认证","right":"20.00"}],[{"left":"","right":"2017-10-09 14:17"},{"left":"购物金额","right":"0.01"},{"left":"返佣金额","right":"0.04"},{"left":"购买人姓名","right":"小张"},{"left":"","right":"ABDS201709141858348"}],[{"left":"","right":"2017-10-09 14:17"},{"left":"购物金额","right":"0.01"},{"left":"返佣金额","right":"0.04"},{"left":"购买人姓名","right":"小张"},{"left":"","right":"ABDS201709141858348"}],[{"left":"","right":"2017-10-09 14:17"},{"left":"购物金额","right":"0.01"},{"left":"返佣金额","right":"0.04"},{"left":"购买人姓名","right":"小张"},{"left":"","right":"ABDS201709141858348"}],[{"left":"","right":"2017-10-09 14:17"},{"left":"购物金额","right":"0.01"},{"left":"返佣金额","right":"0.04"},{"left":"购买人姓名","right":"小张"},{"left":"","right":"ABDS201709141858348"}],[{"left":"","right":"2017-10-09 14:17"},{"left":"购物金额","right":"0.01"},{"left":"返佣金额","right":"0.04"},{"left":"购买人姓名","right":"小张"},{"left":"","right":"ABDS201709141858348"}],[{"left":"","right":"2017-10-09 14:17"},{"left":"购物金额","right":"0.01"},{"left":"返佣金额","right":"0.04"},{"left":"购买人姓名","right":"小张"},{"left":"","right":"ABDS201709141858348"}]]
     * info : 返回成功！
     * page : {"dataTotal":"174","page":"1","pageSize":10,"pageTotal":18}
     * status : 1
     * type : [{"act":0,"id":1,"name":"收款"},{"act":1,"id":2,"name":"返佣"},{"act":0,"id":3,"name":"分润"},{"act":0,"id":4,"name":"推广"}]
     */

    private String info;
    private PageBean page;
    private int status;
    private List<List<DataBean>> data;
    private List<TypeBean> type;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
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
         * dataTotal : 174
         * page : 1
         * pageSize : 10
         * pageTotal : 18
         */

        private String dataTotal;
        private String page;
        private int pageSize;
        private int pageTotal;

        public String getDataTotal() {
            return dataTotal;
        }

        public void setDataTotal(String dataTotal) {
            this.dataTotal = dataTotal;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal) {
            this.pageTotal = pageTotal;
        }
    }

    public static class DataBean {
        /**
         * left :
         * right : 2017-10-13 18:15
         */

        private String left;
        private String right;

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }
    }

    public static class TypeBean {
        /**
         * act : 0
         * id : 1
         * name : 收款
         */

        private int act;
        private int id;
        private String name;

        public int getAct() {
            return act;
        }

        public void setAct(int act) {
            this.act = act;
        }

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
