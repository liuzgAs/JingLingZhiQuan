package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/26 0026.
 */
public class GoodsInfo {
    /**
     * ad : {"banner":["http://api.jlzquan.com/Uploads/attachment/goods/170924/thumb_333415001506236695.jpg","http://api.jlzquan.com/Uploads/attachment/goods/170924/thumb_817784001506236695.png"],"des":[{"name":"a123","id":"3"},{"name":"a456","id":"4"}],"title":"测试商品","id":"1","price":"100.00","countdown":70,"goods_money":"10.00","num":9408,"stock_num":"8481","sale_add":996,"intro":"测试商品测试商品测试商品","share":{"title":"赚10.00","des1":"只要你的好友通过你的分享购买此商品，你就能赚到至少","desMoney":"10.00","des2":"元利润哦~","shareImg":"http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png","shareTitle":"测试商品","shareUrl":"http://api.jlzquan.com/index.php?g=App&m=Web&a=vip","shareDes":"有人@你　你有一个分享尚未点击"},"imgs":[{"img":"http://api.jlzquan.com/ueditor/php/upload/image/20170918/1505720300708174.jpeg","w":1920,"h":516},{"img":"http://api.jlzquan.com/ueditor/php/upload/image/20170918/1505706373431522.jpeg","w":1920,"h":516}],"imgs2":[{"img":"http://api.jlzquan.com/ueditor/php/upload/image/20170918/1505720300708174.jpeg","w":1920,"h":516},{"img":"http://api.jlzquan.com/ueditor/php/upload/image/20170918/1505706373431522.jpeg","w":1920,"h":516}]}
     * recomm : [{"title":"测试商品","id":"1","price":"100.00","img":"http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png"},{"title":"测试商品","id":"1","price":"100.00","img":"http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png"}]
     * status : 1
     * info : 返回成功！
     */

    private AdBean ad;
    private int status;
    private String info;
    private List<RecommBean> recomm;

    public AdBean getAd() {
        return ad;
    }

    public void setAd(AdBean ad) {
        this.ad = ad;
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

    public List<RecommBean> getRecomm() {
        return recomm;
    }

    public void setRecomm(List<RecommBean> recomm) {
        this.recomm = recomm;
    }

    public static class AdBean {
        /**
         * banner : ["http://api.jlzquan.com/Uploads/attachment/goods/170924/thumb_333415001506236695.jpg","http://api.jlzquan.com/Uploads/attachment/goods/170924/thumb_817784001506236695.png"]
         * des : [{"name":"a123","id":"3"},{"name":"a456","id":"4"}]
         * title : 测试商品
         * id : 1
         * price : 100.00
         * countdown : 70
         * goods_money : 10.00
         * num : 9408
         * stock_num : 8481
         * sale_add : 996
         * intro : 测试商品测试商品测试商品
         * share : {"title":"赚10.00","des1":"只要你的好友通过你的分享购买此商品，你就能赚到至少","desMoney":"10.00","des2":"元利润哦~","shareImg":"http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png","shareTitle":"测试商品","shareUrl":"http://api.jlzquan.com/index.php?g=App&m=Web&a=vip","shareDes":"有人@你　你有一个分享尚未点击"}
         * imgs : [{"img":"http://api.jlzquan.com/ueditor/php/upload/image/20170918/1505720300708174.jpeg","w":1920,"h":516},{"img":"http://api.jlzquan.com/ueditor/php/upload/image/20170918/1505706373431522.jpeg","w":1920,"h":516}]
         * imgs2 : [{"img":"http://api.jlzquan.com/ueditor/php/upload/image/20170918/1505720300708174.jpeg","w":1920,"h":516},{"img":"http://api.jlzquan.com/ueditor/php/upload/image/20170918/1505706373431522.jpeg","w":1920,"h":516}]
         */

        private String title;
        private String id;
        private String price;
        private String countdownDes;
        private int countdown;
        private String goods_money;
        private int num;
        private int stock_num;
        private int sale_add;
        private String intro;
        private String item_num;
        private String img;
        private ShareBean share;
        private List<String> banner;
        private List<DesBean> des;
        private List<ImgsBean> imgs;
        private List<ImgsBean> imgs2;
        private List<SizeStrBean> size_str;

        public String getItem_num() {
            return item_num;
        }

        public void setItem_num(String item_num) {
            this.item_num = item_num;
        }

        public String getCountdownDes() {
            return countdownDes;
        }

        public void setCountdownDes(String countdownDes) {
            this.countdownDes = countdownDes;
        }

        public List<SizeStrBean> getSize_str() {
            return size_str;
        }

        public void setSize_str(List<SizeStrBean> size_str) {
            this.size_str = size_str;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getCountdown() {
            return countdown;
        }

        public void setCountdown(int countdown) {
            this.countdown = countdown;
        }

        public String getGoods_money() {
            return goods_money;
        }

        public void setGoods_money(String goods_money) {
            this.goods_money = goods_money;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getStock_num() {
            return stock_num;
        }

        public void setStock_num(int stock_num) {
            this.stock_num = stock_num;
        }

        public int getSale_add() {
            return sale_add;
        }

        public void setSale_add(int sale_add) {
            this.sale_add = sale_add;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public ShareBean getShare() {
            return share;
        }

        public void setShare(ShareBean share) {
            this.share = share;
        }

        public List<String> getBanner() {
            return banner;
        }

        public void setBanner(List<String> banner) {
            this.banner = banner;
        }

        public List<DesBean> getDes() {
            return des;
        }

        public void setDes(List<DesBean> des) {
            this.des = des;
        }

        public List<ImgsBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsBean> imgs) {
            this.imgs = imgs;
        }

        public List<ImgsBean> getImgs2() {
            return imgs2;
        }

        public void setImgs2(List<ImgsBean> imgs2) {
            this.imgs2 = imgs2;
        }

        public static class SizeStrBean {
            /**
             * content : ["红色","绿色","金色"]
             * name : 颜色
             */

            private String name;
            private List<String> content;
            private List<Boolean> isSelect;

            public List<Boolean> getIsSelect() {
                return isSelect;
            }

            public void setIsSelect(List<Boolean> isSelect) {
                this.isSelect = isSelect;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getContent() {
                return content;
            }

            public void setContent(List<String> content) {
                this.content = content;
            }
        }
        public static class DesBean {
            /**
             * name : a123
             * id : 3
             */

            private String name;
            private String id;
            private boolean isSelect =false;

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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class ImgsBean {
            /**
             * img : http://api.jlzquan.com/ueditor/php/upload/image/20170918/1505720300708174.jpeg
             * w : 1920
             * h : 516
             */

            private String img;
            private int w;
            private int h;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getW() {
                return w;
            }

            public void setW(int w) {
                this.w = w;
            }

            public int getH() {
                return h;
            }

            public void setH(int h) {
                this.h = h;
            }
        }

    }

}
