package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/28 0028.
 */
public class StoreMystore {
    /**
     * no : 2
     * storeNmae : 1
     * storeLogo : http://api.jlzquan.com/Uploads/avstar.png
     * num : ["123","456","789","123"]
     * share : {"shareImg":"http://api.jlzquan.com/Uploads/avstar.png","shareTitle":"店铺名","shareUrl":"http://api.jlzquan.com/index.php?g=App&m=Web&a=vip","shareDes":"有人@你　你有一个分享尚未点击"}
     * status : 1
     * info : 返回成功！
     */

    private String no;
    private String storeNmae;
    private String storeLogo;
    private ShareBean share;
    private int status;
    private String info;
    private List<String> num;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getStoreNmae() {
        return storeNmae;
    }

    public void setStoreNmae(String storeNmae) {
        this.storeNmae = storeNmae;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<String> getNum() {
        return num;
    }

    public void setNum(List<String> num) {
        this.num = num;
    }

    public static class ShareBean {
        /**
         * shareImg : http://api.jlzquan.com/Uploads/avstar.png
         * shareTitle : 店铺名
         * shareUrl : http://api.jlzquan.com/index.php?g=App&m=Web&a=vip
         * shareDes : 有人@你　你有一个分享尚未点击
         */

        private String shareImg;
        private String shareTitle;
        private String shareUrl;
        private String shareDes;

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

        public String getShareDes() {
            return shareDes;
        }

        public void setShareDes(String shareDes) {
            this.shareDes = shareDes;
        }
    }
}
