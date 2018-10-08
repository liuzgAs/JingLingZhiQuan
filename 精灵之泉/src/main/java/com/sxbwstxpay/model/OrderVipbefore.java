package com.sxbwstxpay.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/17 0017.
 */
public class OrderVipbefore implements Serializable{

    /**
     * img :
     * url : http://api.jlzquan.com/index.php?g=App&m=Web&a=vip1
     * urlTitle : 成为VIP精灵推广商
     * text1 : 买【魔幻产品1盒】598元（原价898元）,就送精灵VIP
     * text2 :
     * amount : 598
     * selectTitle : 请选择
     * selectValue : [{"name":"魔幻宝盒","id":"5068"},{"name":"精灵神仙水","id":"5340"},{"name":"精灵元素粉","id":"5341"}]
     * cutAmount : 0
     * cutDes : 使用实名认证奖励0元抵扣
     * dbb :
     * dbUrl :
     * imgDes : 精灵客服微信帐号：666666
     * wechatAccount : 66666
     * status : 1
     * info : 返回成功！
     */

    private String img;
    private String url;
    private String urlTitle;
    private String text1;
    private String text2;
    private double amount;
    private String selectTitle;
    private double cutAmount;
    private String cutDes;
    private String dbb;
    private String dbUrl;
    private String imgDes;
    private String wechatAccount;
    private int status;
    private String info;
    private List<SelectValueBean> selectValue;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlTitle() {
        return urlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        this.urlTitle = urlTitle;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSelectTitle() {
        return selectTitle;
    }

    public void setSelectTitle(String selectTitle) {
        this.selectTitle = selectTitle;
    }

    public double getCutAmount() {
        return cutAmount;
    }

    public void setCutAmount(double cutAmount) {
        this.cutAmount = cutAmount;
    }

    public String getCutDes() {
        return cutDes;
    }

    public void setCutDes(String cutDes) {
        this.cutDes = cutDes;
    }

    public String getDbb() {
        return dbb;
    }

    public void setDbb(String dbb) {
        this.dbb = dbb;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getImgDes() {
        return imgDes;
    }

    public void setImgDes(String imgDes) {
        this.imgDes = imgDes;
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
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

    public List<SelectValueBean> getSelectValue() {
        return selectValue;
    }

    public void setSelectValue(List<SelectValueBean> selectValue) {
        this.selectValue = selectValue;
    }

    public static class SelectValueBean {
        /**
         * name : 魔幻宝盒
         * id : 5068
         */

        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
