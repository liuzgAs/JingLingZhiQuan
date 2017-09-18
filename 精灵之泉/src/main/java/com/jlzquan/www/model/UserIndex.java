package com.jlzquan.www.model;

/**
 * Created by zhangjiebo on 2017/9/16 0016.
 */
public class UserIndex {
    /**
     * grade : 0
     * gradeName : 普通用户
     * headImg : http://api.jlzquan.com/Uploads/avstar.png
     * info : 返回成功！
     * nickName :
     * status : 1
     * vipTime :
     */

    private int grade;
    private String gradeName;
    private String headImg;
    private String info;
    private String nickName;
    private int status;
    private String vipTime;
    private String txName;

    public String getTxName() {
        return txName;
    }

    public void setTxName(String txName) {
        this.txName = txName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVipTime() {
        return vipTime;
    }

    public void setVipTime(String vipTime) {
        this.vipTime = vipTime;
    }
}
