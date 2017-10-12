package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/21 0021.
 */
public class IndexMakemoney {
    /**
     * grade : 1
     * img : http://api.jlzquan.com/Uploads/banner/59cdd84a532e6.jpg
     * info : 返回成功！
     * list : [{"act":1,"des":"邀请好友赚现金邀请好友赚现金邀请好友","jump":1,"title":"成为VIP精灵推广商，费率直降、在线收款+购物、自用省钱、分享赚钱"},{"act":1,"des":"邀请好友赚现金邀请好友赚现金邀请好友","jump":2,"title":"分享注册链接，佣金大家赚"},{"act":1,"des":"邀请好友赚现金邀请好友赚现金邀请好友","jump":3,"title":"分享个人二维码"},{"act":1,"des":"邀请好友赚现金邀请好友赚现金邀请好友","jump":4,"title":"每日一键推广图文，轻松赚佣金"}]
     * status : 1
     * url : http://api.jlzquan.com/Mobile/Index/index/sid/1
     */

    private int grade;
    private String img;
    private String info;
    private int status;
    private String url;
    private List<ListBean> list;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * act : 1
         * des : 邀请好友赚现金邀请好友赚现金邀请好友
         * jump : 1
         * title : 成为VIP精灵推广商，费率直降、在线收款+购物、自用省钱、分享赚钱
         */

        private int act;
        private String des;
        private int jump;
        private String title;

        public int getAct() {
            return act;
        }

        public void setAct(int act) {
            this.act = act;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public int getJump() {
            return jump;
        }

        public void setJump(int jump) {
            this.jump = jump;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
