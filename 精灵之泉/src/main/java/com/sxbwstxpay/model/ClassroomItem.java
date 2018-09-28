package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by liuzhigang on 2018/9/28/028.
 *
 * @author LiuZG
 */
public class ClassroomItem {

    /**
     * pwdBgTxt : 请联系客服获取！
     * data : [{"id":"3","title":"11","img":"http://api.jlzquan.com/Uploads/test/5bac925c0be02.png","num":"333","des":"今日观看次数22","is_pwd":0}]
     * status : 1
     * info : 返回成功！
     */

    private String pwdBgTxt;
    private int status;
    private String info;
    private List<DataBean> data;

    public String getPwdBgTxt() {
        return pwdBgTxt;
    }

    public void setPwdBgTxt(String pwdBgTxt) {
        this.pwdBgTxt = pwdBgTxt;
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

    public static class DataBean {
        /**
         * id : 3
         * title : 11
         * img : http://api.jlzquan.com/Uploads/test/5bac925c0be02.png
         * num : 333
         * des : 今日观看次数22
         * is_pwd : 0
         */

        private String id;
        private String title;
        private String img;
        private String num;
        private String des;
        private int is_pwd;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public int getIs_pwd() {
            return is_pwd;
        }

        public void setIs_pwd(int is_pwd) {
            this.is_pwd = is_pwd;
        }
    }
}
