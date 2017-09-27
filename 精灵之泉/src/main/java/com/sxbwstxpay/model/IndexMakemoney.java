package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/21 0021.
 */
public class IndexMakemoney {
    /**
     * list : [{"title":"成为VIP精灵推广商，费率直降、在线收款+购物、自用省钱、分享赚钱","des":"邀请好友赚现金邀请好友赚现金邀请好友"},{"title":"分享注册链接，佣金大家赚","des":"邀请好友赚现金邀请好友赚现金邀请好友"},{"title":"分享个人二维码","des":"邀请好友赚现金邀请好友赚现金邀请好友"},{"title":"每日一键推广图文，轻松赚佣金","des":"邀请好友赚现金邀请好友赚现金邀请好友"}]
     * img : http://api.jlzquan.com
     * url : http://sxb.wstxpay.com/Mall/Html/101572/Default.htm?time=1505986717
     * status : 1
     * info : 返回成功！
     */

    private String img;
    private String url;
    private int status;
    private int grade;
    private String info;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * title : 成为VIP精灵推广商，费率直降、在线收款+购物、自用省钱、分享赚钱
         * des : 邀请好友赚现金邀请好友赚现金邀请好友
         */

        private String title;
        private String des;

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
    }
}
