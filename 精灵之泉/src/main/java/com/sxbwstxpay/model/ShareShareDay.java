package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/18 0018.
 */
public class ShareShareDay {
    /**
     * listData : [{"Downloads":12423,"date":"2017-10-14 19:13:32","imgs":["http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg"],"share_contents":"精灵之泉＝聚合支付＋美食生活！刷卡＋购物，自用省钱，Ta用赚钱，长期躺赚！立即启动神器吧","share_images":["http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg"]},{"Downloads":12423,"date":"2017-10-14 19:46:52","imgs":["http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg"],"share_contents":"精灵之泉＝聚合支付＋美食生活！刷卡＋购物，自用省钱，Ta用赚钱，长期躺赚！立即启动神器吧","share_images":["http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg"]},{"Downloads":12423,"date":"2017-10-12 05:29:51","imgs":["http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg"],"share_contents":"精灵之泉＝聚合支付＋美食生活！刷卡＋购物，自用省钱，Ta用赚钱，长期躺赚！立即启动神器吧","share_images":["http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg"]},{"Downloads":12423,"date":"2017-10-14 09:24:31","imgs":["http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg"],"share_contents":"精灵之泉＝聚合支付＋美食生活！刷卡＋购物，自用省钱，Ta用赚钱，长期躺赚！立即启动神器吧","share_images":["http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg"]}]
     * status : 1
     */

    private int status;
    private String info;
    private List<ListDataBean> listData;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ListDataBean> getListData() {
        return listData;
    }

    public void setListData(List<ListDataBean> listData) {
        this.listData = listData;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static class ListDataBean {
        /**
         * Downloads : 12423
         * date : 2017-10-14 19:13:32
         * imgs : ["http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg"]
         * share_contents : 精灵之泉＝聚合支付＋美食生活！刷卡＋购物，自用省钱，Ta用赚钱，长期躺赚！立即启动神器吧
         * share_images : ["http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg"]
         */

        private int Downloads;
        private String date;
        private String share_contents;
        private List<String> imgs;
        private List<String> share_images;

        public int getDownloads() {
            return Downloads;
        }

        public void setDownloads(int Downloads) {
            this.Downloads = Downloads;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getShare_contents() {
            return share_contents;
        }

        public void setShare_contents(String share_contents) {
            this.share_contents = share_contents;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public List<String> getShare_images() {
            return share_images;
        }

        public void setShare_images(List<String> share_images) {
            this.share_images = share_images;
        }
    }
}
