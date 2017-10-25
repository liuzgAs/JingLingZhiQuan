package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/10/25 0025.
 *
 * @author ZhangJieBo
 */
public class IndexStore {

    /**
     * banner : [{"code":"","img":"http://api.jlzquan.com/Uploads/banner/59cdd53532637.jpg","itemId":"0","title":"3","url":""},{"code":"","img":"http://api.jlzquan.com/Uploads/banner/59cdd7893c984.jpg","itemId":"0","title":"2","url":""},{"code":"","img":"http://api.jlzquan.com/Uploads/banner/59cdd9957ca02.jpg","itemId":"0","title":"4","url":""},{"code":"","img":"http://api.jlzquan.com/Uploads/banner/59cdda14193ed.jpg","itemId":"0","title":"5","url":""}]
     * data : [{"id":"149","img":"http://api.jlzquan.com/Uploads/attachment/201710/19/149_1508389396_1.png","name":"海豚小铺"},{"id":"298","img":"http://api.jlzquan.com/Uploads/attachment/201710/18/298_1508317429_1.png","name":"莲记茶庄"},{"id":"23","img":"http://api.jlzquan.com/Uploads/attachment/201710/24/23_1508813868_1.png","name":"定制生活，点亮财富"},{"id":"421","img":"http://api.jlzquan.com/Uploads/attachment/201710/25/421_1508917649_1.png","name":"昀峰精品店"}]
     * info : 返回成功！
     * page : {"dataTotal":"4","page":1,"pageSize":10,"pageTotal":1}
     * status : 1
     */

    private String info;
    private PageBean page;
    private int status;
    private List<BannerBean> banner;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PageBean {
        /**
         * dataTotal : 4
         * page : 1
         * pageSize : 10
         * pageTotal : 1
         */

        private String dataTotal;
        private int page;
        private int pageSize;
        private int pageTotal;

        public String getDataTotal() {
            return dataTotal;
        }

        public void setDataTotal(String dataTotal) {
            this.dataTotal = dataTotal;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
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
         * img : http://api.jlzquan.com/Uploads/banner/59cdd53532637.jpg
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

    public static class DataBean {
        /**
         * id : 149
         * img : http://api.jlzquan.com/Uploads/attachment/201710/19/149_1508389396_1.png
         * name : 海豚小铺
         */

        private String id;
        private String img;
        private String name;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
