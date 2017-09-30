package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/30 0030.
 */
public class StoreViews {
    /**
     * dt : [{"dateStr":"2017-09-23","dateInt":1506096000,"user":16,"view":26},{"dateStr":"2017-09-24","dateInt":1506182400,"user":16,"view":26},{"dateStr":"2017-09-25","dateInt":1506268800,"user":16,"view":26},{"dateStr":"2017-09-26","dateInt":1506355200,"user":16,"view":26},{"dateStr":"2017-09-27","dateInt":1506441600,"user":16,"view":26},{"dateStr":"2017-09-28","dateInt":1506528000,"user":16,"view":26},{"dateStr":"2017-09-29","dateInt":1506614400,"user":16,"view":26}]
     * user : 112
     * view : 182
     * dayUser : 112
     * dayView : 182
     * cate : [{"name":"分类名1","img":"http://api.jlzquan.com/Uploads/goods/59c759491bd6c.png"},{"name":"分类名2","img":"http://api.jlzquan.com/Uploads/goods/59c759491bd6c.png"},{"name":"分类名3","img":"http://api.jlzquan.com/Uploads/goods/59c759491bd6c.png"},{"name":"分类名4","img":"http://api.jlzquan.com/Uploads/goods/59c759491bd6c.png"}]
     * goods : [{"id":1,"title":"商品名称","img":"http://api.jlzquan.com/Uploads/goods/59c759491bd6c.png","user":2,"view":12},{"id":1,"title":"商品名称","img":"http://api.jlzquan.com/Uploads/goods/59c759491bd6c.png","user":2,"view":12},{"id":1,"title":"商品名称","img":"http://api.jlzquan.com/Uploads/goods/59c759491bd6c.png","user":2,"view":12}]
     * status : 1
     * info : 返回成功！
     */

    private int user;
    private int view;
    private int dayUser;
    private int dayView;
    private int status;
    private int maxHeight;
    private String info;
    private List<DtBean> dt;
    private List<CateBean> cate;
    private List<GoodsBean> goods;

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getDayUser() {
        return dayUser;
    }

    public void setDayUser(int dayUser) {
        this.dayUser = dayUser;
    }

    public int getDayView() {
        return dayView;
    }

    public void setDayView(int dayView) {
        this.dayView = dayView;
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

    public List<DtBean> getDt() {
        return dt;
    }

    public void setDt(List<DtBean> dt) {
        this.dt = dt;
    }

    public List<CateBean> getCate() {
        return cate;
    }

    public void setCate(List<CateBean> cate) {
        this.cate = cate;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class DtBean {
        /**
         * dateStr : 2017-09-23
         * dateInt : 1506096000
         * user : 16
         * view : 26
         */

        private String dateStr;
        private int dateInt;
        private int user;
        private int view;

        public String getDateStr() {
            return dateStr;
        }

        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }

        public int getDateInt() {
            return dateInt;
        }

        public void setDateInt(int dateInt) {
            this.dateInt = dateInt;
        }

        public int getUser() {
            return user;
        }

        public void setUser(int user) {
            this.user = user;
        }

        public int getView() {
            return view;
        }

        public void setView(int view) {
            this.view = view;
        }
    }

    public static class CateBean {
        /**
         * name : 分类名1
         * img : http://api.jlzquan.com/Uploads/goods/59c759491bd6c.png
         */

        private String name;
        private String img;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public static class GoodsBean {
        /**
         * id : 1
         * title : 商品名称
         * img : http://api.jlzquan.com/Uploads/goods/59c759491bd6c.png
         * user : 2
         * view : 12
         */

        private int id;
        private String title;
        private String img;
        private int user;
        private int view;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getUser() {
            return user;
        }

        public void setUser(int user) {
            this.user = user;
        }

        public int getView() {
            return view;
        }

        public void setView(int view) {
            this.view = view;
        }
    }
}
