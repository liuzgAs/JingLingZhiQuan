package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by liuzhigang on 2018/9/26/026.
 *
 * @author LiuZG
 */
public class Skill_Addbefore {

    /**
     * notice : 严禁发布涉及到黄赌毒类服务，不允许出现有违法律或当前党政主体的言论！
     * id : 1
     * img_id : 433
     * cid : 34
     * cidName : 摄影跟拍
     * img : http://api.jlzquan.com/Uploads/attachment/201710/09/1_1507516090_1.png
     * cate : [{"id":0,"name":"请选择分类"},{"id":"34","name":"摄影跟拍"},{"id":"35","name":"二手房砍价师"}]
     * name : 跟拍两个小时
     * address : 胡里山炮台
     * price : 150/小时
     * intro : 专业摄影跟拍，你负责玩抓拍交给我！
     * cityId : 60
     * cityName : 厦门
     * contact : 小苏
     * isRealname : 0
     * tips : 您暂未进行人脸识别，请先进行人脸识别！
     * isFace : 1
     * city : [{"id":"53","pid":"4","name":"福州","type":"2","act":"1","c2":"0"},{"id":"54","pid":"4","name":"龙岩","type":"2","act":"1","c2":"0"},{"id":"55","pid":"4","name":"南平","type":"2","act":"1","c2":"0"},{"id":"56","pid":"4","name":"宁德","type":"2","act":"1","c2":"0"},{"id":"57","pid":"4","name":"莆田","type":"2","act":"1","c2":"0"},{"id":"58","pid":"4","name":"泉州","type":"2","act":"1","c2":"0"},{"id":"59","pid":"4","name":"三明","type":"2","act":"1","c2":"0"},{"id":"60","pid":"4","name":"厦门","type":"2","act":"1","c2":"0"},{"id":"61","pid":"4","name":"漳州","type":"2","act":"1","c2":"0"},{"id":"311","pid":"24","name":"西安","type":"2","act":"1","c2":"0"}]
     * status : 1
     * info : 返回成功！
     */

    private String notice;
    private String id;
    private String img_id;
    private String cid;
    private String cidName;
    private String img;
    private String name;
    private String address;
    private String price;
    private String intro;
    private String cityId;
    private String cityName;
    private String contact;
    private int isRealname;
    private String tips;
    private int isFace;
    private int status;
    private String info;
    private List<CateBean> cate;
    private List<CityBean> city;

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCidName() {
        return cidName;
    }

    public void setCidName(String cidName) {
        this.cidName = cidName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getIsRealname() {
        return isRealname;
    }

    public void setIsRealname(int isRealname) {
        this.isRealname = isRealname;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getIsFace() {
        return isFace;
    }

    public void setIsFace(int isFace) {
        this.isFace = isFace;
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

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public static class CateBean {
        /**
         * id : 0
         * name : 请选择分类
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

    public static class CityBean {
        /**
         * id : 53
         * pid : 4
         * name : 福州
         * type : 2
         * act : 1
         * c2 : 0
         */

        private String id;
        private String pid;
        private String name;
        private String type;
        private String act;
        private String c2;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public String getC2() {
            return c2;
        }

        public void setC2(String c2) {
            this.c2 = c2;
        }
    }
}
