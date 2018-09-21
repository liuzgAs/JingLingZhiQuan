package com.sxbwstxpay.model;

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
    private int is_db;
    private int is_btn;
    private int is_test;
    private String vipTime;
    private String btnText;
    private String btnUrl;
    private String txName;
    private String style_text;
    private String storeTips;
    private String dbb;
    public int getIs_test() {
        return is_test;
    }

    public void setIs_test(int is_test) {
        this.is_test = is_test;
    }
    public int getIs_db() {
        return is_db;
    }

    public void setIs_db(int is_db) {
        this.is_db = is_db;
    }

    public int getIs_btn() {
        return is_btn;
    }

    public void setIs_btn(int is_btn) {
        this.is_btn = is_btn;
    }
    public String getStyle_text() {
        return style_text;
    }

    public void setStyle_text(String style_text) {
        this.style_text = style_text;
    }
    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }

    public String getBtnUrl() {
        return btnUrl;
    }

    public void setBtnUrl(String btnUrl) {
        this.btnUrl = btnUrl;
    }

    public String getDbb() {
        return dbb;
    }

    public void setDbb(String dbb) {
        this.dbb = dbb;
    }

    public String getStoreTips() {
        return storeTips;
    }

    public void setStoreTips(String storeTips) {
        this.storeTips = storeTips;
    }

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
