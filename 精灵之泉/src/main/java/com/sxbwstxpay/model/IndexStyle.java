package com.sxbwstxpay.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuzhigang on 2018/9/25/025.
 *
 * @author LiuZG
 */
public class IndexStyle {

    /**
     * banner : []
     * cate : [{"id":"17","name":"端庄范","img":"http://api.jlzquan.com/Uploads/web/5ba49c290c479.png"},{"id":"18","name":"淑女范","img":"http://api.jlzquan.com/Uploads/web/5ba49c3c4946b.png"},{"id":0,"name":"风格测试","img":"http://api.jlzquan.com/Uploads/test/666666.png"},{"id":"19","name":"甜美范","img":"http://api.jlzquan.com/Uploads/web/5ba49c1e41b7f.png"},{"id":"20","name":"异域范","img":"http://api.jlzquan.com/Uploads/web/5ba49c7f8dd5e.png"},{"id":"21","name":"浪漫范","img":"http://api.jlzquan.com/Uploads/web/5ba49c923240f.png"},{"id":"22","name":"中性范","img":"http://api.jlzquan.com/Uploads/web/5ba49ca6d2705.png"},{"id":"23","name":"清新范","img":"http://api.jlzquan.com/Uploads/web/5ba49cbdc324e.png"},{"id":"24","name":"潮流范","img":"http://api.jlzquan.com/Uploads/web/5ba49cde5c18a.png"},{"id":"25","name":"大气范","img":"http://api.jlzquan.com/Uploads/web/5ba49cf11f082.png"}]
     * data : [{"img":"http://api.jlzquan.com/Uploads/test/5ba4bc619b0ec.jpg","goods_id":"5174","title":"专属风格：甜美范","des":[{"is_seleteed":1,"name":"脖子短"},{"is_seleteed":1,"name":"背厚手臂粗"},{"is_seleteed":1,"name":"腰粗"},{"is_seleteed":0,"name":"臀大腿粗"},{"is_seleteed":0,"name":"腿短"},{"is_seleteed":0,"name":"矮个"}]}]
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

    public static class CateBean implements Serializable{
        /**
         * id : 17
         * name : 端庄范
         * img : http://api.jlzquan.com/Uploads/web/5ba49c290c479.png
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
         * img : http://api.jlzquan.com/Uploads/test/5ba4bc619b0ec.jpg
         * goods_id : 5174
         * title : 专属风格：甜美范
         * des : [{"is_seleteed":1,"name":"脖子短"},{"is_seleteed":1,"name":"背厚手臂粗"},{"is_seleteed":1,"name":"腰粗"},{"is_seleteed":0,"name":"臀大腿粗"},{"is_seleteed":0,"name":"腿短"},{"is_seleteed":0,"name":"矮个"}]
         */

        private String img;
        private String goods_id;
        private String title;
        private List<DesBean> des;

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
