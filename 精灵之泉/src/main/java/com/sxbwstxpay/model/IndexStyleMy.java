package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by liuzhigang on 2018/9/25/025.
 *
 * @author LiuZG
 */
public class IndexStyleMy {

    /**
     * banner : []
     * cate : [{"id":"27","name":"长形","img":"http://api.jlzquan.com/Uploads/web/5ba99806d2328.png"},{"id":"28","name":"蛋形","img":"http://api.jlzquan.com/Uploads/web/5ba99819d22ae.png"},{"id":"29","name":"圆形","img":"http://api.jlzquan.com/Uploads/web/5ba998282e1cf.png"},{"id":"30","name":"方形","img":"http://api.jlzquan.com/Uploads/web/5ba99836e5321.png"},{"id":"31","name":"心形","img":"http://api.jlzquan.com/Uploads/web/5ba9984b3d513.png"},{"id":"32","name":"菱形","img":"http://api.jlzquan.com/Uploads/web/5ba9985663726.png"}]
     * data : [{"img":"http://api.jlzquan.com/Uploads/test/5ba991a2bd86e.jpg","goods_id":"5176","title":"专属风格：蛋形","des":[]}]
     * page : {"page":1,"pageTotal":1,"pageSize":10,"dataTotal":"1"}
     * status : 1
     * info : 返回成功！
     */

    private PageBean page;
    private int status;
    private String info;
    private List<BannerBean> banner;
    private List<CateBean> cate;
    private List<DataBean> data;

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

    public static class PageBean {
        /**
         * page : 1
         * pageTotal : 1
         * pageSize : 10
         * dataTotal : 1
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

    public static class CateBean {
        /**
         * id : 27
         * name : 长形
         * img : http://api.jlzquan.com/Uploads/web/5ba99806d2328.png
         */

        private String id;
        private String name;
        private String img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

    public static class DataBean {
        /**
         * img : http://api.jlzquan.com/Uploads/test/5ba991a2bd86e.jpg
         * goods_id : 5176
         * title : 专属风格：蛋形
         * des : []
         */

        private String img;
        private String goods_id;
        private String title;
        private List<DesBean> des;
        private List<ImgsBean> imgs;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
        public static class ImgsBean {
            /**
             * img : http://api.jlzquan.com/Uploads/test/5baef97dd9f99.png
             * goods_id : 5213
             */

            private String img;
            private String goods_id;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }
        }
        public static class DesBean {
            /**
             * is_seleteed : 1
             * name : 脖子短
             */

            private int is_seleteed;
            private String name;

            public int getIs_seleteed() {
                return is_seleteed;
            }

            public void setIs_seleteed(int is_seleteed) {
                this.is_seleteed = is_seleteed;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
