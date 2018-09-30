package com.sxbwstxpay.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuzhigang on 2018/9/27/027.
 *
 * @author LiuZG
 */
public class SkillIndex {

    /**
     * data : [{"id":"1","lng":"118.106292","lat":"24.429742","nickName":"聚宝精灵","nickNameDes":"小薛的店2","headImg":"http://api.jlzquan.com/Uploads/goods/59f86ef0a385d.png","title":"跟拍两个小时(还有1个服务)","des":"专业摄影跟拍，你负责玩抓拍交给我！","distance":"距离：<7.45km","sid":"1"},{"id":"1","lng":"118.106292","lat":"24.429742","nickName":"聚宝精灵","nickNameDes":"小薛的店2","headImg":"http://api.jlzquan.com/Uploads/goods/59f86ef0a385d.png","title":"跟拍两个小时(还有1个服务)","des":"专业摄影跟拍，你负责玩抓拍交给我！","distance":"距离：<7.45km","sid":"1"}]
     * page : {"page":1,"pageTotal":1,"pageSize":10,"dataTotal":"2"}
     * cate : [{"id":0,"name":"全部"},{"id":"34","name":"摄影"},{"id":"35","name":"车服务"}]
     * status : 1
     * info : 返回成功！
     */

    private PageBean page;
    private int status;
    private String info;
    private List<DataBean> data;
    private List<CateBean> cate;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<CateBean> getCate() {
        return cate;
    }

    public void setCate(List<CateBean> cate) {
        this.cate = cate;
    }

    public static class PageBean {
        /**
         * page : 1
         * pageTotal : 1
         * pageSize : 10
         * dataTotal : 2
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

    public static class DataBean implements Serializable{
        /**
         * id : 1
         * lng : 118.106292
         * lat : 24.429742
         * nickName : 聚宝精灵
         * nickNameDes : 小薛的店2
         * headImg : http://api.jlzquan.com/Uploads/goods/59f86ef0a385d.png
         * title : 跟拍两个小时(还有1个服务)
         * des : 专业摄影跟拍，你负责玩抓拍交给我！
         * distance : 距离：<7.45km
         * sid : 1
         */

        private String id;
        private String lng;
        private String lat;
        private String nickName;
        private String nickNameDes;
        private String headImg;
        private String title;
        private String des;
        private String distance;
        private String sid;
        private int settled;
        public int getSettled() {
            return settled;
        }

        public void setSettled(int settled) {
            this.settled = settled;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getNickNameDes() {
            return nickNameDes;
        }

        public void setNickNameDes(String nickNameDes) {
            this.nickNameDes = nickNameDes;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }
    }

    public static class CateBean {
        /**
         * id : 0
         * name : 全部
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
