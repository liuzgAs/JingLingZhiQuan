package com.jlzquan.www.model;

/**
 * Created by zhangjiebo on 2017/9/17 0017.
 */
public class UserProfile {
    /**
     * mobile : 15871105320
     * nickName : 15871105320
     * headImg : http://api.jlzquan.com/Uploads/avstar.png
     * birthday : 1970-01-01
     * sex : 女
     * area : null
     * wx : 
     * status : 1
     * info : 返回成功！
     */

    private String mobile;
    private String nickName;
    private String headImg;
    private String birthday;
    private String sex;
    private String area;
    private String wx;
    private int status;
    private String info;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
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
}
