package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/25 0025.
 */
public class IndexCate {
    /**
     * vipNum : 0
     * sort : [{"name":"综合排序","k":"sort","v":""},{"name":"价格高到低","k":"sort","v":"price_desc"},{"name":"价格低到高","k":"sort","v":"price_asc"},{"name":"佣金高到低","k":"sort","v":"money_desc"},{"name":"佣金低到高","k":"sort","v":"money_asc"}]
     * cate : [{"id":0,"name":"限时抢购"},{"id":"5","name":"水果生鲜"},{"id":"6","name":"零食饮料"},{"id":"7","name":"日用家居"},{"id":"8","name":"母婴儿童"}]
     * ios_open : 1
     * status : 1
     * info : 返回成功！
     */

    private int vipNum;
    private int ios_open;
    private int status;
    private String info;
    private List<SortBean> sort;
    private List<SortBean> sort1;
    private List<CateBean> cate;

    public List<SortBean> getSort1() {
        return sort1;
    }

    public void setSort1(List<SortBean> sort1) {
        this.sort1 = sort1;
    }

    public int getVipNum() {
        return vipNum;
    }

    public void setVipNum(int vipNum) {
        this.vipNum = vipNum;
    }

    public int getIos_open() {
        return ios_open;
    }

    public void setIos_open(int ios_open) {
        this.ios_open = ios_open;
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

    public List<SortBean> getSort() {
        return sort;
    }

    public void setSort(List<SortBean> sort) {
        this.sort = sort;
    }

    public List<CateBean> getCate() {
        return cate;
    }

    public void setCate(List<CateBean> cate) {
        this.cate = cate;
    }

    public static class SortBean {
        /**
         * name : 综合排序
         * k : sort
         * v :
         */

        private String name;
        private String k;
        private String v;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }
    }

    public static class CateBean {
        /**
         * id : 0
         * name : 限时抢购
         */

        private int id;
        private String name;
        private String jump;

        public String getJump() {
            return jump;
        }

        public void setJump(String jump) {
            this.jump = jump;
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
