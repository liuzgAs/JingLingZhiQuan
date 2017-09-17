package hudongchuangxiang.com.jinglingzhiquan.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/17 0017.
 */
public class UserRate {
    /**
     * data : [{"id":"2","name":"银联大额D+0","type":"0","des":"单笔2万，单卡5万，8:00-22:00秒到","rate":"费率：0.50%","fee":"结算：2元/笔","maxAmount":"2万","maxDay":"0万"},{"id":"1","name":"银联小额D+0","type":"0","des":"单笔5千，单卡1万，8:00-22:00秒到","rate":"费率：0.40%","fee":"结算：2元/笔","maxAmount":"0.5万","maxDay":"0万"}]
     * status : 1
     * info : 返回成功！
     */

    private int status;
    private String info;
    private List<DataBean> data;

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
         * id : 2
         * name : 银联大额D+0
         * type : 0
         * des : 单笔2万，单卡5万，8:00-22:00秒到
         * rate : 费率：0.50%
         * fee : 结算：2元/笔
         * maxAmount : 2万
         * maxDay : 0万
         */

        private String id;
        private String name;
        private String type;
        private String des;
        private String rate;
        private String fee;
        private String maxAmount;
        private String maxDay;
        private String nameDes;

        public String getNameDes() {
            return nameDes;
        }

        public void setNameDes(String nameDes) {
            this.nameDes = nameDes;
        }

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getMaxAmount() {
            return maxAmount;
        }

        public void setMaxAmount(String maxAmount) {
            this.maxAmount = maxAmount;
        }

        public String getMaxDay() {
            return maxDay;
        }

        public void setMaxDay(String maxDay) {
            this.maxDay = maxDay;
        }
    }
}
