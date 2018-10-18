package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by liuzhigang on 2018/10/18/018.
 *
 * @author LiuZG
 */
public class Ssdfasd {

    /**
     * type : [{"n":"全部","v":"","act":1},{"n":"待付款","v":"10","act":0},{"n":"待确认","v":"20","act":0},{"n":"已完成","v":"40","act":0},{"n":"已取消","v":"0","act":0}]
     * list : [{"id":"116572","orderSn":"订单号：AO201809141706022","status":"0","orderAmount":"58.00","uid":"725","og":[{"pid":"1","superior":"20","goods_id":"5126","quantity":"1","goods_img":"http://api.jlzquan.com/Uploads/attachment/goods/180906/thumb_089980001536222235.png","goods_name":"包邮 桂格早餐燕麦片牛奶味/红枣味/紫薯味3口味组合装540g*3盒","goods_price":"58.00","goods_money":0.58,"goods_desc":"","my":"0","express_no":"","express_time":"0","express_id":"0","goods_spe":"","is_ship":0,"ship_url":"http://api.jlzquan.com/index.php?g=App&m=User&a=expressQuery&express_no=&express_id=0","ship_title":"订单物流详情"}],"status_text":"已取消","goods_money":"0.58","des":[{"n":"合计：","v":"￥58"},{"n":"赚：","v":"￥0.58"}],"sum":58,"sumDes":"（包邮）","is_cancle":0,"is_pay":0,"is_confirm":0,"is_del":0,"is_ship":0,"saleState":1,"ship_url":"http://api.jlzquan.com/index.php?g=App&m=User&a=expressQuery&express_no=&express_id=0","ship_title":"订单物流详情"},{"id":"51733","orderSn":"订单号：AO201805181547116","status":"0","orderAmount":"129.80","uid":"1","og":[{"pid":"6","superior":"10","goods_id":"4699","quantity":"1","goods_img":"http://api.jlzquan.com/Uploads/attachment/goods/180511/thumb_409458001526051835.jpg","goods_name":"【上臣】18年新货正品薄壳手剥巴西松子500g独立包装坚果特产零食","goods_price":"129.80","goods_money":"1.95","goods_desc":"","my":"30","express_no":"","express_time":"0","express_id":"0","goods_spe":"","is_ship":0,"ship_url":"http://api.jlzquan.com/index.php?g=App&m=User&a=expressQuery&express_no=&express_id=0","ship_title":"订单物流详情"}],"status_text":"已取消","goods_money":"2.53","des":[{"n":"合计：","v":"￥129.8"},{"n":"赚：","v":"￥2.53"}],"sum":129.8,"sumDes":"（包邮）","is_cancle":0,"is_pay":0,"is_confirm":0,"is_del":1,"is_ship":0,"saleState":0,"ship_url":"http://api.jlzquan.com/index.php?g=App&m=User&a=expressQuery&express_no=&express_id=0","ship_title":"订单物流详情"},{"id":"15949","orderSn":"订单号：AO201802261343116","status":"0","orderAmount":"148.00","uid":"1","og":[{"pid":"2","superior":"0","goods_id":"4033","quantity":"1","goods_img":"http://api.jlzquan.com/Uploads/attachment/goods/180130/thumb_713343001517293679.png","goods_name":"柔诺雅秋季纯棉纱布睡衣女长袖加大码宽松休闲甜美可爱家居服女冬","goods_price":"148.00","goods_money":"4.44","goods_desc":"S","my":"30","express_no":"","express_time":"0","express_id":"0","goods_spe":"S","is_ship":0,"ship_url":"http://api.jlzquan.com/index.php?g=App&m=User&a=expressQuery&express_no=&express_id=0","ship_title":"订单物流详情"}],"status_text":"已取消","goods_money":"6.97","des":[{"n":"合计：","v":"￥148"},{"n":"赚：","v":"￥6.97"}],"sum":148,"sumDes":"（包邮）","is_cancle":0,"is_pay":0,"is_confirm":0,"is_del":1,"is_ship":0,"saleState":0,"ship_url":"http://api.jlzquan.com/index.php?g=App&m=User&a=expressQuery&express_no=&express_id=0","ship_title":"订单物流详情"}]
     * page : {"page":"1","pageTotal":20,"pageSize":3,"dataTotal":"60"}
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
         * pageTotal : 20
         * pageSize : 3
         * dataTotal : 60
         */

        private String page;
        private int pageTotal;
        private int pageSize;
        private String dataTotal;

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
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
         * id : 116572
         * orderSn : 订单号：AO201809141706022
         * status : 0
         * orderAmount : 58.00
         * uid : 725
         * og : [{"pid":"1","superior":"20","goods_id":"5126","quantity":"1","goods_img":"http://api.jlzquan.com/Uploads/attachment/goods/180906/thumb_089980001536222235.png","goods_name":"包邮 桂格早餐燕麦片牛奶味/红枣味/紫薯味3口味组合装540g*3盒","goods_price":"58.00","goods_money":0.58,"goods_desc":"","my":"0","express_no":"","express_time":"0","express_id":"0","goods_spe":"","is_ship":0,"ship_url":"http://api.jlzquan.com/index.php?g=App&m=User&a=expressQuery&express_no=&express_id=0","ship_title":"订单物流详情"}]
         * status_text : 已取消
         * goods_money : 0.58
         * des : [{"n":"合计：","v":"￥58"},{"n":"赚：","v":"￥0.58"}]
         * sum : 58
         * sumDes : （包邮）
         * is_cancle : 0
         * is_pay : 0
         * is_confirm : 0
         * is_del : 0
         * is_ship : 0
         * saleState : 1
         * ship_url : http://api.jlzquan.com/index.php?g=App&m=User&a=expressQuery&express_no=&express_id=0
         * ship_title : 订单物流详情
         */

        private String id;
        private String orderSn;
        private String status;
        private String orderAmount;
        private String uid;
        private String status_text;
        private String goods_money;
        private int sum;
        private String sumDes;
        private int is_cancle;
        private int is_pay;
        private int is_confirm;
        private int is_del;
        private int is_ship;
        private int saleState;
        private String ship_url;
        private String ship_title;
        private List<OgBean> og;
        private List<DesBean> des;

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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public String getGoods_money() {
            return goods_money;
        }

        public void setGoods_money(String goods_money) {
            this.goods_money = goods_money;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
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

        public int getIs_ship() {
            return is_ship;
        }

        public void setIs_ship(int is_ship) {
            this.is_ship = is_ship;
        }

        public int getSaleState() {
            return saleState;
        }

        public void setSaleState(int saleState) {
            this.saleState = saleState;
        }

        public String getShip_url() {
            return ship_url;
        }

        public void setShip_url(String ship_url) {
            this.ship_url = ship_url;
        }

        public String getShip_title() {
            return ship_title;
        }

        public void setShip_title(String ship_title) {
            this.ship_title = ship_title;
        }

        public List<OgBean> getOg() {
            return og;
        }

        public void setOg(List<OgBean> og) {
            this.og = og;
        }

        public List<DesBean> getDes() {
            return des;
        }

        public void setDes(List<DesBean> des) {
            this.des = des;
        }

        public static class OgBean {
            /**
             * pid : 1
             * superior : 20
             * goods_id : 5126
             * quantity : 1
             * goods_img : http://api.jlzquan.com/Uploads/attachment/goods/180906/thumb_089980001536222235.png
             * goods_name : 包邮 桂格早餐燕麦片牛奶味/红枣味/紫薯味3口味组合装540g*3盒
             * goods_price : 58.00
             * goods_money : 0.58
             * goods_desc :
             * my : 0
             * express_no :
             * express_time : 0
             * express_id : 0
             * goods_spe :
             * is_ship : 0
             * ship_url : http://api.jlzquan.com/index.php?g=App&m=User&a=expressQuery&express_no=&express_id=0
             * ship_title : 订单物流详情
             */

            private String pid;
            private String superior;
            private String goods_id;
            private String quantity;
            private String goods_img;
            private String goods_name;
            private String goods_price;
            private double goods_money;
            private String goods_desc;
            private String my;
            private String express_no;
            private String express_time;
            private String express_id;
            private String goods_spe;
            private int is_ship;
            private String ship_url;
            private String ship_title;

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getSuperior() {
                return superior;
            }

            public void setSuperior(String superior) {
                this.superior = superior;
            }

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

            public double getGoods_money() {
                return goods_money;
            }

            public void setGoods_money(double goods_money) {
                this.goods_money = goods_money;
            }

            public String getGoods_desc() {
                return goods_desc;
            }

            public void setGoods_desc(String goods_desc) {
                this.goods_desc = goods_desc;
            }

            public String getMy() {
                return my;
            }

            public void setMy(String my) {
                this.my = my;
            }

            public String getExpress_no() {
                return express_no;
            }

            public void setExpress_no(String express_no) {
                this.express_no = express_no;
            }

            public String getExpress_time() {
                return express_time;
            }

            public void setExpress_time(String express_time) {
                this.express_time = express_time;
            }

            public String getExpress_id() {
                return express_id;
            }

            public void setExpress_id(String express_id) {
                this.express_id = express_id;
            }

            public String getGoods_spe() {
                return goods_spe;
            }

            public void setGoods_spe(String goods_spe) {
                this.goods_spe = goods_spe;
            }

            public int getIs_ship() {
                return is_ship;
            }

            public void setIs_ship(int is_ship) {
                this.is_ship = is_ship;
            }

            public String getShip_url() {
                return ship_url;
            }

            public void setShip_url(String ship_url) {
                this.ship_url = ship_url;
            }

            public String getShip_title() {
                return ship_title;
            }

            public void setShip_title(String ship_title) {
                this.ship_title = ship_title;
            }
        }

        public static class DesBean {
            /**
             * n : 合计：
             * v : ￥58
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
