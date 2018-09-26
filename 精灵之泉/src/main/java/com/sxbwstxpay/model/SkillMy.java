package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by liuzhigang on 2018/9/26/026.
 *
 * @author LiuZG
 */
public class SkillMy {

    /**
     * data : [{"id":"8","name":"跟拍两个小时","des":"120/小时","img":"http://api.jlzquan.com/Uploads/attachment/201710/09/1_1507516090_1.png","state":"10","closeText":"暂停服务","isDel":0,"isEdit":1,"isClose":1},{"id":"2","name":"跟拍两个小时","des":"120/小时","img":"http://api.jlzquan.com/Uploads/attachment/201709/17/13_1505609983_4.png","state":"20","closeText":"暂停服务","isDel":1,"isEdit":1,"isClose":0}]
     * page : {"page":1,"pageTotal":1,"pageSize":10,"dataTotal":"2"}
     * status : 1
     * info : 返回成功！
     */

    private PageBean page;
    private int status;
    private String info;
    private List<DataBean> data;

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

    public static class DataBean {
        /**
         * id : 8
         * name : 跟拍两个小时
         * des : 120/小时
         * img : http://api.jlzquan.com/Uploads/attachment/201710/09/1_1507516090_1.png
         * state : 10
         * closeText : 暂停服务
         * isDel : 0
         * isEdit : 1
         * isClose : 1
         */

        private String id;
        private String name;
        private String des;
        private String img;
        private String state;
        private String closeText;
        private int isDel;
        private int isEdit;
        private int isClose;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCloseText() {
            return closeText;
        }

        public void setCloseText(String closeText) {
            this.closeText = closeText;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }

        public int getIsEdit() {
            return isEdit;
        }

        public void setIsEdit(int isEdit) {
            this.isEdit = isEdit;
        }

        public int getIsClose() {
            return isClose;
        }

        public void setIsClose(int isClose) {
            this.isClose = isClose;
        }
    }
}
