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
    private List<DataBean> data;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
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

    public static class DataBean {
        /**
         * act : 0
         * goods_money : 10.00
         * id : 1
         * img : http://api.jlzquan.com
         * price : 100.00
         * recom_img : http://api.jlzquan.com/Uploads/goods/59c75e4306bf8.jpg
         * share : {"des1":"只要你的好友通过你的分享购买此商品，你就能赚到至少","des2":"元利润哦~","desMoney":"10.00","shareDes":"有人@你　你有一个分享尚未点击","shareImg":"http://api.jlzquan.com","shareTitle":"测试商品","shareUrl":"http://api.jlzquan.com/index.php?g=App&m=Web&a=vip","title":"赚10.00"}
         * stock_num : 8481
         * title : 测试商品
         */

        private int act;
        private String goods_money;
        private String id;
        private String img;
        private String price;
        private String recom_img;
        private ShareBean share;
        private String stock_num;
        private String title;

        public int getAct() {
            return act;
        }

        public void setAct(int act) {
            this.act = act;
        }

        public String getGoods_money() {
            return goods_money;
        }

        public void setGoods_money(String goods_money) {
            this.goods_money = goods_money;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRecom_img() {
            return recom_img;
        }

        public void setRecom_img(String recom_img) {
            this.recom_img = recom_img;
        }

        public ShareBean getShare() {
            return share;
        }

        public void setShare(ShareBean share) {
            this.share = share;
        }

        public String getStock_num() {
            return stock_num;
        }

        public void setStock_num(String stock_num) {
            this.stock_num = stock_num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public static class ShareBean {
            /**
             * des1 : 只要你的好友通过你的分享购买此商品，你就能赚到至少
             * des2 : 元利润哦~
             * desMoney : 10.00
             * shareDes : 有人@你　你有一个分享尚未点击
             * shareImg : http://api.jlzquan.com
             * shareTitle : 测试商品
             * shareUrl : http://api.jlzquan.com/index.php?g=App&m=Web&a=vip
             * title : 赚10.00
             */

            private String des1;
            private String des2;
            private String desMoney;
            private String shareDes;
            private String shareImg;
            private String shareTitle;
            private String shareUrl;
            private String title;

            public String getDes1() {
                return des1;
            }

            public void setDes1(String des1) {
                this.des1 = des1;
            }

            public String getDes2() {
                return des2;
            }

            public void setDes2(String des2) {
                this.des2 = des2;
            }

            public String getDesMoney() {
                return desMoney;
            }

            public void setDesMoney(String desMoney) {
                this.desMoney = desMoney;
            }

            public String getShareDes() {
                return shareDes;
            }

            public void setShareDes(String shareDes) {
                this.shareDes = shareDes;
            }

            public String getShareImg() {
                return shareImg;
            }

            public void setShareImg(String shareImg) {
                this.shareImg = shareImg;
            }

            public String getShareTitle() {
                return shareTitle;
            }

            public void setShareTitle(String shareTitle) {
                this.shareTitle = shareTitle;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
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
