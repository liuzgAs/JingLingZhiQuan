package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/3/6/006.
 *
 * @author ZhangJieBo
 */

public class AfterAddbefore {

    /**
     * data : {"goods_name":"创意可爱动物兔鹿角眼镜架子展示架收纳家居工艺装饰品小摆件桌面","goods_spe":"规格：千寻鹿眼镜架","img":"http://api.jlzquan.com/Uploads/attachment/goods/180304/thumb_897075001520124257.png","quantity":"1","goods_price":"58.00","goods_money":"1.74","goods_id":"4123","id":"1095","my":"30"}
     * type : [{"id":1,"title":"退货"},{"id":2,"title":"换货"},{"id":3,"title":"维修"},{"id":4,"title":"补发"}]
     * reason : [{"id":1,"title":"与所购商品描述不符"},{"id":2,"title":"质量有问题"}]
     * maxNum : 1
     * maxMoney : 56.26
     * url : http://api.jlzquan.com/index.php?g=App&m=Index&a=info&type=about
     * urlTitle : 最大退款金额！
     * status : 1
     * info : SUUCESS！
     */

    private DataBean data;
    private int maxNum;
    private double maxMoney;
    private String url;
    private String urlTitle;
    private int status;
    private String info;
    private List<TypeBean> type;
    private List<ReasonBean> reason;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(double maxMoney) {
        this.maxMoney = maxMoney;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlTitle() {
        return urlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        this.urlTitle = urlTitle;
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

    public List<ReasonBean> getReason() {
        return reason;
    }

    public void setReason(List<ReasonBean> reason) {
        this.reason = reason;
    }

    public static class DataBean {
        /**
         * goods_name : 创意可爱动物兔鹿角眼镜架子展示架收纳家居工艺装饰品小摆件桌面
         * goods_spe : 规格：千寻鹿眼镜架
         * img : http://api.jlzquan.com/Uploads/attachment/goods/180304/thumb_897075001520124257.png
         * quantity : 1
         * goods_price : 58.00
         * goods_money : 1.74
         * goods_id : 4123
         * id : 1095
         * my : 30
         */

        private String goods_name;
        private String goods_spe;
        private String img;
        private String quantity;
        private String goods_price;
        private String goods_money;
        private String goods_id;
        private String id;
        private String my;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_spe() {
            return goods_spe;
        }

        public void setGoods_spe(String goods_spe) {
            this.goods_spe = goods_spe;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
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

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMy() {
            return my;
        }

        public void setMy(String my) {
            this.my = my;
        }
    }

    public static class TypeBean {
        /**
         * id : 1
         * title : 退货
         */

        private int id;
        private String title;

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
    }

    public static class ReasonBean {
        /**
         * id : 1
         * title : 与所购商品描述不符
         */

        private int id;
        private String title;

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
    }
}
