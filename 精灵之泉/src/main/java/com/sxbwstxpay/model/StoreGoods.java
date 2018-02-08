package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/28 0028.
 */
public class StoreGoods {
    /**
     * banner : [{"code":"","img":"http://api.jlzquan.com/Uploads/banner/59c791439368b.png","itemId":"0","title":"3","url":""},{"code":"","img":"http://api.jlzquan.com/Uploads/banner/59c7913d47228.png","itemId":"0","title":"2","url":""},{"code":"","img":"http://api.jlzquan.com/Uploads/banner/59c791355a3ba.png","itemId":"0","title":"123","url":""}]
     * data : [{"act":1,"goods_money":"2.88","id":"3","img":"http://api.jlzquan.com/Uploads/goods/59cb08c758024.jpg","price":"28.80","recom_img":"http://api.jlzquan.com/Uploads/goods/59cb08c75bd2d.jpg","share":{"des1":"只要你的好友通过你的分享购买此商品，你就能赚到至少","des2":"元利润哦~","desMoney":"2.88","shareDes":"有人@你　你有一个分享尚未点击","shareImg":"http://api.jlzquan.com/Uploads/avatar/180.png","shareTitle":"海洋蓝渐变玻璃杯便携大容量韩版随手杯女创意学生情侣简约水杯子","shareUrl":"http://api.jlzquan.com/index.php?g=App&m=Web&a=vip","title":"赚2.88"},"stock_num":"2297","title":"海洋蓝渐变玻璃杯便携大容量韩版随手杯女创意学生情侣简约水杯子","type":"2"},{"act":1,"goods_money":"0.60","id":"2","img":"http://api.jlzquan.com/Uploads/goods/59cafeb58da0b.jpg","price":"6.00","recom_img":"http://api.jlzquan.com/Uploads/goods/59cafeb59541d.jpg","share":{"des1":"只要你的好友通过你的分享购买此商品，你就能赚到至少","des2":"元利润哦~","desMoney":"0.60","shareDes":"有人@你　你有一个分享尚未点击","shareImg":"http://api.jlzquan.com/Uploads/avatar/180.png","shareTitle":"日光岩椰子饼厦门鼓浪屿特产馅饼伴手礼糕点零食办公室点心小吃","shareUrl":"http://api.jlzquan.com/index.php?g=App&m=Web&a=vip","title":"赚0.60"},"stock_num":"3227","title":"日光岩椰子饼厦门鼓浪屿特产馅饼伴手礼糕点零食办公室点心小吃","type":"2"},{"act":1,"goods_money":"10.00","id":"1","img":"http://api.jlzquan.com/Uploads/goods/59c8b4abe6837.png","price":"100.00","recom_img":"http://api.jlzquan.com/Uploads/goods/59c864295e0b7.png","share":{"des1":"只要你的好友通过你的分享购买此商品，你就能赚到至少","des2":"元利润哦~","desMoney":"10.00","shareDes":"有人@你　你有一个分享尚未点击","shareImg":"http://api.jlzquan.com/Uploads/avatar/180.png","shareTitle":"测试商品","shareUrl":"http://api.jlzquan.com/index.php?g=App&m=Web&a=vip","title":"赚10.00"},"stock_num":"8481","title":"测试商品","type":"1"}]
     * info : 返回成功！
     * page : {"dataTotal":"3","page":"1","pageSize":10,"pageTotal":1}
     * previewUrl : http://api.jlzquan.com/index.php?g=App&m=Web&a=vip
     * share : {"shareDes":"有人@你　你有一个分享尚未点击","shareImg":"http://api.jlzquan.com/Uploads/avstar.png","shareTitle":"店铺名","shareUrl":"http://api.jlzquan.com/index.php?g=App&m=Web&a=vip"}
     * status : 1
     * storeDes : 精灵店铺
     * storeLogo : http://api.jlzquan.com/Uploads/avstar.png
     * storeNmae : 暂无店铺名称
     */

    private String info;
    private PageBean page;
    private String previewUrl;
    private ShareBean share;
    private int status;
    private String storeDes;
    private String storeLogo;
    private String storeNmae;
    private String tel;
    private List<BannerBean> banner;
    private List<IndexDataBean> data;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public ShareBean getShare() {
        return share;
    }

    public void setShare(ShareBean share) {
        this.share = share;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStoreDes() {
        return storeDes;
    }

    public void setStoreDes(String storeDes) {
        this.storeDes = storeDes;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public String getStoreNmae() {
        return storeNmae;
    }

    public void setStoreNmae(String storeNmae) {
        this.storeNmae = storeNmae;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<IndexDataBean> getData() {
        return data;
    }

    public void setData(List<IndexDataBean> data) {
        this.data = data;
    }

    public static class PageBean {
        /**
         * dataTotal : 3
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

    public static class ShareBean {
        /**
         * shareDes : 有人@你　你有一个分享尚未点击
         * shareImg : http://api.jlzquan.com/Uploads/avstar.png
         * shareTitle : 店铺名
         * shareUrl : http://api.jlzquan.com/index.php?g=App&m=Web&a=vip
         */

        private String shareDes;
        private String shareImg;
        private String shareTitle;
        private String shareUrl;

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
    }

}
