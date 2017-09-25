package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/25 0025.
 */
public class GoodsIndex {
    /**
     * cate : [{"id":"5","name":"水果生鲜","act":1},{"id":"6","name":"零食饮料","act":0},{"id":"7","name":"日用家居","act":0},{"id":"8","name":"母婴儿童","act":0},{"id":"9","name":"本地生活","act":0}]
     * data : [{"id":"1","title":"测试商品","price":"100.00","stock_num":"8481","goods_money":"10.00","recom_img":"http://api.jlzquan.com/Uploads/goods/59c864295e0b7.png","img":"http://api.jlzquan.com/Uploads/goods/59c759491bd6c.png","act":0,"share":{"title":"赚10.00","des1":"只要你的好友通过你的分享购买此商品，你就能赚到至少","desMoney":"10.00","des2":"元利润哦~","shareImg":"http://api.jlzquan.com/Uploads/goods/59c759491bd6c.png","shareTitle":"测试商品","shareUrl":"http://api.jlzquan.com/index.php?g=App&m=Web&a=vip","shareDes":"有人@你　你有一个分享尚未点击"}}]
     * page : {"page":1,"pageTotal":1,"pageSize":10,"dataTotal":"1"}
     * status : 1
     * info : 返回成功！
     */

    private PageBean page;
    private int status;
    private String info;
    private List<CateBean> cate;
    private List<IndexDataBean> data;

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
         * id : 5
         * name : 水果生鲜
         * act : 1
         */

        private String id;
        private String name;
        private int act;

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

        public int getAct() {
            return act;
        }

        public void setAct(int act) {
            this.act = act;
        }
    }


}
