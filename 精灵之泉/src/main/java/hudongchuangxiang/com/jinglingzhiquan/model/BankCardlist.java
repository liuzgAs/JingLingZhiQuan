package hudongchuangxiang.com.jinglingzhiquan.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/13 0013.
 */
public class BankCardlist {
    /**
     * data : [{"bank":"银行单笔限额100000元","bankCard":"4475","id":"1","name":"薛振宇","type":"储蓄卡"}]
     * info : 返回成功！
     * status : 1
     */

    private String info;
    private int status;
    private List<DataBean> data;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * bank : 银行单笔限额100000元
         * bankCard : 4475
         * id : 1
         * name : 薛振宇
         * type : 储蓄卡
         */

        private String bank;
        private String bankName;
        private String bankCard;
        private String id;
        private String name;
        private String type;
        private String img;

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
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
    }
}
