package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/28 0028.
 */
public class UserOrder {
    /**
     * type : [{"n":"全部","v":"","act":1},{"n":"待付款","v":"10","act":0},{"n":"待确认","v":"20","act":0},{"n":"已完成","v":"40","act":0},{"n":"已取消","v":"0","act":0}]
     * list : [{"id":"276","orderSn":"订单号：AO201709271905238","status":"10","orderAmount":"100.00","og":[{"goods_id":"1","quantity":"0","goods_img":"http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png","goods_name":"测试商品","goods_price":"100.00","goods_money":"10.00","goods_desc":"a456"}],"status_text":"待支付","goods_money":0,"sum":0,"sumDes":"（包邮）","is_cancle":1,"is_pay":1,"is_confirm":0,"is_del":0,"saleState":1},{"id":"275","orderSn":"订单号：AO201709271850453","status":"10","orderAmount":"100.00","og":[{"goods_id":"1","quantity":"0","goods_img":"http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png","goods_name":"测试商品","goods_price":"100.00","goods_money":"10.00","goods_desc":"a456"}],"status_text":"待支付","goods_money":0,"sum":0,"sumDes":"（包邮）","is_cancle":1,"is_pay":1,"is_confirm":0,"is_del":0,"saleState":1},{"id":"262","orderSn":"订单号：AO201709271759077","status":"20","orderAmount":"100.00","og":[{"goods_id":"1","quantity":"0","goods_img":"http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png","goods_name":"测试商品","goods_price":"100.00","goods_money":"10.00","goods_desc":"a456"}],"status_text":"待发货","goods_money":0,"sum":0,"sumDes":"（包邮）","is_cancle":0,"is_pay":0,"is_confirm":1,"is_del":0,"saleState":1}]
     * page : {"page":1,"pageTotal":3,"pageSize":3,"dataTotal":"8"}
     * status : 1
     * info : SUUCESS！
     */

    private PageBean page;
    private int status;
    private String info;
    private List<TypeBean> type;
    private List<ListBean> list;

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

    public List<TypeBean> getType() {
        return type;
    }

    public void setType(List<TypeBean> type) {
        this.type = type;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class PageBean {
        /**
         * page : 1
         * pageTotal : 3
         * pageSize : 3
         * dataTotal : 8
         */

        private int page;
        private int pageTotal;
        private int pageSize;
        private String dataTotal;

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

        public String getDataTotal() {
            return dataTotal;
        }

        public void setDataTotal(String dataTotal) {
            this.dataTotal = dataTotal;
        }
    }

    public static class TypeBean {
        /**
         * n : 全部
         * v :
         * act : 1
         */

        private String n;
        private String v;
        private int act;

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

        public int getAct() {
            return act;
        }

        public void setAct(int act) {
            this.act = act;
        }
    }

    public static class ListBean {
        /**
         * id : 276
         * orderSn : 订单号：AO201709271905238
         * status : 10
         * orderAmount : 100.00
         * og : [{"goods_id":"1","quantity":"0","goods_img":"http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png","goods_name":"测试商品","goods_price":"100.00","goods_money":"10.00","goods_desc":"a456"}]
         * status_text : 待支付
         * goods_money : 0
         * sum : 0
         * sumDes : （包邮）
         * is_cancle : 1
         * is_pay : 1
         * is_confirm : 0
         * is_del : 0
         * saleState : 1
         */

        private String id;
        private String orderSn;
        private String status;
        private String orderAmount;
        private String status_text;
        private int goods_money;
        private double sum;
        private String sumDes;
        private int is_cancle;
        private int is_pay;
        private int is_confirm;
        private int is_del;
        private int saleState;
        private List<OgBean> og;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public int getGoods_money() {
            return goods_money;
        }

        public void setGoods_money(int goods_money) {
            this.goods_money = goods_money;
        }

        public double getSum() {
            return sum;
        }

        public void setSum(double sum) {
            this.sum = sum;
        }

        public String getSumDes() {
            return sumDes;
        }

        public void setSumDes(String sumDes) {
            this.sumDes = sumDes;
        }

        public int getIs_cancle() {
            return is_cancle;
        }

        public void setIs_cancle(int is_cancle) {
            this.is_cancle = is_cancle;
        }

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }

        public int getIs_confirm() {
            return is_confirm;
        }

        public void setIs_confirm(int is_confirm) {
            this.is_confirm = is_confirm;
        }

        public int getIs_del() {
            return is_del;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public int getSaleState() {
            return saleState;
        }

        public void setSaleState(int saleState) {
            this.saleState = saleState;
        }

        public List<OgBean> getOg() {
            return og;
        }

        public void setOg(List<OgBean> og) {
            this.og = og;
        }

        public static class OgBean {
            /**
             * goods_id : 1
             * quantity : 0
             * goods_img : http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png
             * goods_name : 测试商品
             * goods_price : 100.00
             * goods_money : 10.00
             * goods_desc : a456
             */

            private String goods_id;
            private String quantity;
            private String goods_img;
            private String goods_name;
            private String goods_price;
            private String goods_money;
            private String goods_desc;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getGoods_money() {
                return goods_money;
            }

            public void setGoods_money(String goods_money) {
                this.goods_money = goods_money;
            }

            public String getGoods_desc() {
                return goods_desc;
            }

            public void setGoods_desc(String goods_desc) {
                this.goods_desc = goods_desc;
            }
        }
    }
}
