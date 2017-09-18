package com.jlzquan.www.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/18 0018.
 */
public class ShareShareDay {

    /**
     * listData : [{"Downloads":12423,"date":"2017-09-18 11:26:42","share_contents":"精灵之泉＝聚合支付＋美食生活！刷卡＋购物，自用省钱，Ta用赚钱，长期躺赚！立即启动神器吧","share_images":["http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg"]},{"Downloads":12423,"date":"2017-09-18 12:00:02","share_contents":"精灵之泉＝聚合支付＋美食生活！刷卡＋购物，自用省钱，Ta用赚钱，长期躺赚！立即启动神器吧","share_images":["http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg"]},{"Downloads":12423,"date":"2017-09-15 21:43:01","share_contents":"精灵之泉＝聚合支付＋美食生活！刷卡＋购物，自用省钱，Ta用赚钱，长期躺赚！立即启动神器吧","share_images":["http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg"]},{"Downloads":12423,"date":"2017-09-18 01:37:41","share_contents":"精灵之泉＝聚合支付＋美食生活！刷卡＋购物，自用省钱，Ta用赚钱，长期躺赚！立即启动神器吧","share_images":["http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg"]}]
     * status : 1
     */

    private int status;
    private List<ListDataBean> listData;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

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

    public static class ListDataBean {
        /**
         * Downloads : 12423
         * date : 2017-09-18 11:26:42
         * share_contents : 精灵之泉＝聚合支付＋美食生活！刷卡＋购物，自用省钱，Ta用赚钱，长期躺赚！立即启动神器吧
         * share_images : ["http://api.jlzquan.com/Uploads/avatar/aaa_03.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_05.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_07.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_12.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_13.jpg","http://api.jlzquan.com/Uploads/avatar/aaa_14.jpg"]
         */

        private int Downloads;
        private String date;
        private String share_contents;
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

        public List<String> getShare_images() {
            return share_images;
        }

        public void setShare_images(List<String> share_images) {
            this.share_images = share_images;
        }
    }
}
