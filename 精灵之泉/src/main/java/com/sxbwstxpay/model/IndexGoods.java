package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/25 0025.
 */
public class IndexGoods {
    /**
     * banner : [{"code":"","img":"http://api.jlzquan.com/Uploads/banner/59c791439368b.png","itemId":"0","title":"3","url":""},{"code":"","img":"http://api.jlzquan.com/Uploads/banner/59c7913d47228.png","itemId":"0","title":"2","url":""},{"code":"","img":"http://api.jlzquan.com/Uploads/banner/59c791355a3ba.png","itemId":"0","title":"123","url":""}]
     * cate : [{"id":0,"name":"限时抢购"},{"id":"5","name":"水果生鲜"},{"id":"6","name":"零食饮料"},{"id":"7","name":"日用家居"},{"id":"8","name":"母婴儿童"},{"id":"9","name":"本地生活"}]
     * data : [{"act":0,"goods_money":"10.00","id":"1","img":"http://api.jlzquan.com","price":"100.00","recom_img":"http://api.jlzquan.com/Uploads/goods/59c75e4306bf8.jpg","share":{"des1":"只要你的好友通过你的分享购买此商品，你就能赚到至少","des2":"元利润哦~","desMoney":"10.00","shareDes":"有人@你　你有一个分享尚未点击","shareImg":"http://api.jlzquan.com","shareTitle":"测试商品","shareUrl":"http://api.jlzquan.com/index.php?g=App&m=Web&a=vip","title":"赚10.00"},"stock_num":"8481","title":"测试商品"},{"act":0,"goods_money":"10.00","id":"1","img":"http://api.jlzquan.com","price":"100.00","recom_img":"http://api.jlzquan.com/Uploads/goods/59c75e4306bf8.jpg","share":{"des1":"只要你的好友通过你的分享购买此商品，你就能赚到至少","des2":"元利润哦~","desMoney":"10.00","shareDes":"有人@你　你有一个分享尚未点击","shareImg":"http://api.jlzquan.com","shareTitle":"测试商品","shareUrl":"http://api.jlzquan.com/index.php?g=App&m=Web&a=vip","title":"赚10.00"},"stock_num":"8481","title":"测试商品"}]
     * img :
     * info : 返回成功！
     * page : {"dataTotal":1,"page":"1","pageSize":10,"pageTotal":1}
     * status : 1
     * times : [{"act":1,"des":"抢购中","id":"4","times":"09:00"},{"act":0,"des":"预热中","id":"5","times":"14:00"},{"act":0,"des":"预热中","id":"6","times":"19:00"},{"act":0,"des":"预热中","id":"7","times":"23:00"}]
     */

    private String img;
    private String info;
    private PageBean page;
    private int status;
    private List<BannerBean> banner;
    private List<CateBean> cate;
    private List<IndexDataBean> data;
    private List<TimesBean> times;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

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

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<CateBean> getCate() {
        return cate;
    }

    public void setCate(List<CateBean> cate) {
        this.cate = cate;
    }

    public List<IndexDataBean> getData() {
        return data;
    }

    public void setData(List<IndexDataBean> data) {
        this.data = data;
    }

    public List<TimesBean> getTimes() {
        return times;
    }

    public void setTimes(List<TimesBean> times) {
        this.times = times;
    }

    public static class PageBean {
        /**
         * dataTotal : 1
         * page : 1
         * pageSize : 10
         * pageTotal : 1
         */

        private int dataTotal;
        private String page;
        private int pageSize;
        private int pageTotal;

        public int getDataTotal() {
            return dataTotal;
        }

        public void setDataTotal(int dataTotal) {
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

    public static class BannerBean {
        /**
         * code :
         * img : http://api.jlzquan.com/Uploads/banner/59c791439368b.png
         * itemId : 0
         * title : 3
         * url :
         */

        private String code;
        private String img;
        private String itemId;
        private String title;
        private String url;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
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

    public static class TimesBean {
        /**
         * act : 1
         * des : 抢购中
         * id : 4
         * times : 09:00
         */

        private int act;
        private String des;
        private String id;
        private String times;

        public int getAct() {
            return act;
        }

        public void setAct(int act) {
            this.act = act;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }
    }
}
