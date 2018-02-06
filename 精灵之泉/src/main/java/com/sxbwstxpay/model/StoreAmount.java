package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/2/6/006.
 *
 * @author ZhangJieBo
 */

public class StoreAmount {
    /**
     * name :
     * money : 10.49
     * nums : 0
     * data : [{"name":"13585835172","grade":"普通用户","sum":0},{"name":"18814489319","grade":"普通用户","sum":0},{"name":"袁伟东","grade":"普通用户","sum":0},{"name":"田玲","grade":"银牌消费商","sum":"0.89"},{"name":"18906978955","grade":"普通用户","sum":0},{"name":"15054537772","grade":"普通用户","sum":0},{"name":"15515609137","grade":"普通用户","sum":0},{"name":"13390641311","grade":"普通用户","sum":0},{"name":"张通","grade":"VIP消费商","sum":0},{"name":"13020200376","grade":"普通用户","sum":0}]
     * status : 1
     * info : 返回成功！
     */

    private String name;
    private String money;
    private String img;
    private int nums;
    private int status;
    private String info;
    private List<DataBean> data;

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
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
         * name : 13585835172
         * grade : 普通用户
         * sum : 0
         */

        private String name;
        private String grade;
        private String sum;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }
    }
}
