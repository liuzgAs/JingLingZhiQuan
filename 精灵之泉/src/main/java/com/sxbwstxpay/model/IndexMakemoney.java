package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/21 0021.
 */
public class IndexMakemoney {

    /**
     * img : http://api.jlzquan.com/Uploads/banner/5b8557109aed3.jpg
     * banner : []
     * list : [{"title":"分享注册链接，佣金大家赚","des":"分享注册链接，佣金大家赚","act":1,"jump":"registered_url"},{"title":"分享个人二维码","des":"分享个人二维码","act":1,"jump":"code"},{"title":"分享App下载链接","des":"只下载APP，不能绑定上下级","act":1,"jump":"app_url"},{"title":"每日一键推广图文，轻松赚佣金","des":"每日一键推广图文，轻松赚佣金","act":1,"jump":"picture"}]
     * grade : 1
     * tipsText : 需实名认证后才可分享转发！
     * url :
     * notice : [{"n":"17:09 恭喜 王XX加入精灵VIP"},{"n":"17:09 恭喜 王XX加入精灵VIP"}]
     * status : 1
     * info : 返回成功！
     */

    private String img;
    private int grade;
    private String tipsText;
    private String url;
    private int status;
    private String info;
    private List<?> banner;
    private List<ListBean> list;
    private List<NoticeBean> notice;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getTipsText() {
        return tipsText;
    }

    public void setTipsText(String tipsText) {
        this.tipsText = tipsText;
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

    public List<?> getBanner() {
        return banner;
    }

    public void setBanner(List<?> banner) {
        this.banner = banner;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<NoticeBean> getNotice() {
        return notice;
    }

    public void setNotice(List<NoticeBean> notice) {
        this.notice = notice;
    }

    public static class ListBean {
        /**
         * title : 分享注册链接，佣金大家赚
         * des : 分享注册链接，佣金大家赚
         * act : 1
         * jump : registered_url
         */

        private String title;
        private String des;
        private int act;
        private String jump;

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

        public int getAct() {
            return act;
        }

        public void setAct(int act) {
            this.act = act;
        }

        public String getJump() {
            return jump;
        }

        public void setJump(String jump) {
            this.jump = jump;
        }
    }

    public static class NoticeBean {
        /**
         * n : 17:09 恭喜 王XX加入精灵VIP
         */

        private String n;

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }
    }
}
