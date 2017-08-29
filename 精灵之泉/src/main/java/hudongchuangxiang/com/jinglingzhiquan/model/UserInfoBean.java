package hudongchuangxiang.com.jinglingzhiquan.model;

import java.io.Serializable;

public class UserInfoBean implements Serializable {
    /**
     * city : 厦门
     * cityCode : 1
     * headImg : http://img2.gao7.com/files/appleimage/7AC/7AC60546-B6F3-486C-AE3B-7BFD5B32D6C0.jpg
     * uid : 1
     * userName : 蚂蚁
     */

    private String city;
    private int cityCode;
    private String headImg;
    private int uid;
    private String userName;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}