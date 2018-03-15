package com.sxbwstxpay.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/3/10/010.
 *
 * @author ZhangJieBo
 */

public class HkConfirm {

    /**
     * data : [{"n":"支付总金额","v":"￥322"},{"n":"支付手续费","v":"￥20"},{"n":"结算手续费","v":"￥2"}]
     * sum : 322
     * oid : 322
     * sumDes : 合计
     * creditDes : 请确保信用卡里还有相应的额度
     * status : 1
     * info : 返回成功！
     */

    private String sum;
    private String oid;
    private String sumDes;
    private String creditDes;
    private int status;
    private String info;
    private List<DataBean> data;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getSumDes() {
        return sumDes;
    }

    public void setSumDes(String sumDes) {
        this.sumDes = sumDes;
    }

    public String getCreditDes() {
        return creditDes;
    }

    public void setCreditDes(String creditDes) {
        this.creditDes = creditDes;
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
         * n : 支付总金额
         * v : ￥322
         */

        private String n;
        private String v;

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }
    }
}
