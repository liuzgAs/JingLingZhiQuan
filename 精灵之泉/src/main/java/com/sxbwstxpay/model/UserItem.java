package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/10/26 0026.
 *
 * @author ZhangJieBo
 */
public class UserItem {
    /**
     * count : 1
     * data : [{"addTime":"2017-10-26","img":["http://api.jlzquan.com","http://api.jlzquan.com","http://api.jlzquan.com","http://api.jlzquan.com","http://api.jlzquan.com","http://api.jlzquan.com"],"imgs":["http://api.jlzquan.com/Uploads/attachment/201710/26/2_1508986527_1.png","http://api.jlzquan.com/Uploads/attachment/201710/26/2_1508986528_1.png","http://api.jlzquan.com/Uploads/attachment/201710/26/2_1508986528_2.png","http://api.jlzquan.com/Uploads/attachment/201710/26/2_1508986528_3.png","http://api.jlzquan.com/Uploads/attachment/201710/26/2_1508986529_1.png","http://api.jlzquan.com/Uploads/attachment/201710/26/2_1508986531_1.png"],"recommend":"0","title":"呵呵呵呵"}]
     * info : 返回成功！
     * page : {"dataTotal":"1","page":"1","pageSize":10,"pageTotal":1}
     * recoNum : 0
     * status : 1
     */

    private String count;
    private String info;
    private PageBean page;
    private int recoNum;
    private int status;
    private List<DataBean> data;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public int getRecoNum() {
        return recoNum;
    }

    public void setRecoNum(int recoNum) {
        this.recoNum = recoNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PageBean {
        /**
         * dataTotal : 1
         * page : 1
         * pageSize : 10
         * pageTotal : 1
         */

        private String dataTotal;
        private String page;
        private int pageSize;
        private int pageTotal;

        public String getDataTotal() {
            return dataTotal;
        }

        public void setDataTotal(String dataTotal) {
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

    public static class DataBean {
        /**
         * addTime : 2017-10-26
         * img : ["http://api.jlzquan.com","http://api.jlzquan.com","http://api.jlzquan.com","http://api.jlzquan.com","http://api.jlzquan.com","http://api.jlzquan.com"]
         * imgs : ["http://api.jlzquan.com/Uploads/attachment/201710/26/2_1508986527_1.png","http://api.jlzquan.com/Uploads/attachment/201710/26/2_1508986528_1.png","http://api.jlzquan.com/Uploads/attachment/201710/26/2_1508986528_2.png","http://api.jlzquan.com/Uploads/attachment/201710/26/2_1508986528_3.png","http://api.jlzquan.com/Uploads/attachment/201710/26/2_1508986529_1.png","http://api.jlzquan.com/Uploads/attachment/201710/26/2_1508986531_1.png"]
         * recommend : 0
         * title : 呵呵呵呵
         */

        private String addTime;
        private int recommend;
        private String title;
        private List<String> img;
        private List<String> imgs;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getRecommend() {
            return recommend;
        }

        public void setRecommend(int recommend) {
            this.recommend = recommend;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }
    }
}
