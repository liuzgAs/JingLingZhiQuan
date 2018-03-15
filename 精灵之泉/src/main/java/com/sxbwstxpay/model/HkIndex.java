package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/3/8/008.
 *
 * @author ZhangJieBo
 */

public class HkIndex {
    /**
     * day1 : 帐单日：3日
     * day2 : 还款日：28日
     * cen : ["0","0","0","0","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28"]
     * status : 1
     * info : 返回成功！
     */

    private String day1;
    private String day2;
    private int status;
    private int dayNum;
    private int isEdit;
    private String info;
    private String tips;
    private String url;
    private List<String> cen;

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public int getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(int isEdit) {
        this.isEdit = isEdit;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDay1() {
        return day1;
    }

    public void setDay1(String day1) {
        this.day1 = day1;
    }

    public String getDay2() {
        return day2;
    }

    public void setDay2(String day2) {
        this.day2 = day2;
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

    public List<String> getCen() {
        return cen;
    }

    public void setCen(List<String> cen) {
        this.cen = cen;
    }
}
