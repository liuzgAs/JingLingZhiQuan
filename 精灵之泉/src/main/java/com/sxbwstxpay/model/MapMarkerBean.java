package com.sxbwstxpay.model;

import java.io.Serializable;

public class MapMarkerBean implements Serializable{
        /**
         * des : 店主很懒，没留下什么签名
         * headImg : http://api.jlzquan.com/Uploads/attachment/201709/17/2_1505638944_1_ad.png
         * id : 2
         * lat : 距离：<0
         * lng : 118.193911
         * nickName : 小姐姐
         * sid : 1
         * title : 发现一个商家
         */

        private String des;
        private String headImg;
        private String id;
        private String lat;
        private String lng;
        private String nickName;
        private String distance;
        private int sid;
        private int settled;
        private String title;

        public int getSettled() {
            return settled;
        }

        public void setSettled(int settled) {
            this.settled = settled;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }