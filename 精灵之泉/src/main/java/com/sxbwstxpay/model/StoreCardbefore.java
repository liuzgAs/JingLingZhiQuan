package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/2/6/006.
 *
 * @author ZhangJieBo
 */

public class StoreCardbefore {
    /**
     * tipsText :
     * submitStatus : 0
     * verify : 1
     * data : {"cate":"439","cateName":"","name":"薛振宇","phone":"15860026753","contact":"350322198704270510","address":"6227001842670044475","intro":"41","area":"","imgId":"435","imgId2":"438","imgId3":"437","img":"http://api.jlzquan.com/Uploads/attachment/201710/09/1_1507516570_1.png","img2":"http://api.jlzquan.com/Uploads/attachment/201710/09/1_1507516571_2.png","img3":"http://api.jlzquan.com/Uploads/attachment/201710/09/1_1507516571_1.png"}
     * cate : [{"id":"5","name":"水果生鲜"},{"id":"6","name":"零食饮料"},{"id":"7","name":"日用家居"},{"id":"8","name":"母婴儿童"},{"id":"12","name":"护肤美妆"},{"id":"10","name":"动宝特选"},{"id":"11","name":"本地优店"}]
     * status : 1
     * info : 返回成功！
     */

    private String tipsText;
    private int submitStatus;
    private int verify;
    private DataBean data;
    private int status;
    private String info;
    private List<CateBean> cate;

    public String getTipsText() {
        return tipsText;
    }

    public void setTipsText(String tipsText) {
        this.tipsText = tipsText;
    }

    public int getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(int submitStatus) {
        this.submitStatus = submitStatus;
    }

    public int getVerify() {
        return verify;
    }

    public void setVerify(int verify) {
        this.verify = verify;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public List<CateBean> getCate() {
        return cate;
    }

    public void setCate(List<CateBean> cate) {
        this.cate = cate;
    }

    public static class DataBean {
        /**
         * cate : 439
         * cateName :
         * name : 薛振宇
         * phone : 15860026753
         * contact : 350322198704270510
         * address : 6227001842670044475
         * intro : 41
         * area :
         * imgId : 435
         * imgId2 : 438
         * imgId3 : 437
         * img : http://api.jlzquan.com/Uploads/attachment/201710/09/1_1507516570_1.png
         * img2 : http://api.jlzquan.com/Uploads/attachment/201710/09/1_1507516571_2.png
         * img3 : http://api.jlzquan.com/Uploads/attachment/201710/09/1_1507516571_1.png
         */

        private String cate;
        private String cateName;
        private String name;
        private String phone;
        private String contact;
        private String address;
        private String intro;
        private String area;
        private String imgId;
        private String imgId2;
        private String imgId3;
        private String imgId4;
        private String img;
        private String img2;
        private String img3;
        private String img4;

        public String getImgId4() {
            return imgId4;
        }

        public void setImgId4(String imgId4) {
            this.imgId4 = imgId4;
        }

        public String getImg4() {
            return img4;
        }

        public void setImg4(String img4) {
            this.img4 = img4;
        }

        public String getCate() {
            return cate;
        }

        public void setCate(String cate) {
            this.cate = cate;
        }

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getImgId() {
            return imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }

        public String getImgId2() {
            return imgId2;
        }

        public void setImgId2(String imgId2) {
            this.imgId2 = imgId2;
        }

        public String getImgId3() {
            return imgId3;
        }

        public void setImgId3(String imgId3) {
            this.imgId3 = imgId3;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImg2() {
            return img2;
        }

        public void setImg2(String img2) {
            this.img2 = img2;
        }

        public String getImg3() {
            return img3;
        }

        public void setImg3(String img3) {
            this.img3 = img3;
        }
    }

    public static class CateBean {
        /**
         * id : 5
         * name : 水果生鲜
         */

        private String id;
        private String name;

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
    }
}
