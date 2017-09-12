package hudongchuangxiang.com.jinglingzhiquan.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/11 0011.
 */
public class UserCardbefore {
    /**
     * bank : [{"id":"6","name":"邮政银行"},{"id":"5","name":"支付宝"},{"id":"4","name":"农业银行"},{"id":"3","name":"建设银行"},{"id":"2","name":"中国银行"},{"id":"1","name":"工商银行"}]
     * data : {"bank":0,"bankCard":"","bankName":"","img":"","img2":"","img3":"","img4":"","imgId":0,"imgId2":0,"imgId3":0,"imgId4":0,"name":"","phone":""}
     * info : 返回成功！
     * status : 1
     * submitStatus : 1
     * tipsText :
     * verify : 0
     */

    private DataBean data;
    private String info;
    private int status;
    private int submitStatus;
    private String tipsText;
    private int verify;
    private List<BankBean> bank;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public int getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(int submitStatus) {
        this.submitStatus = submitStatus;
    }

    public String getTipsText() {
        return tipsText;
    }

    public void setTipsText(String tipsText) {
        this.tipsText = tipsText;
    }

    public int getVerify() {
        return verify;
    }

    public void setVerify(int verify) {
        this.verify = verify;
    }

    public List<BankBean> getBank() {
        return bank;
    }

    public void setBank(List<BankBean> bank) {
        this.bank = bank;
    }

    public static class DataBean {
        /**
         * bank : 0
         * bankCard :
         * bankName :
         * img :
         * img2 :
         * img3 :
         * img4 :
         * imgId : 0
         * imgId2 : 0
         * imgId3 : 0
         * imgId4 : 0
         * name :
         * phone :
         */

        private int bank;
        private String bankCard;
        private String bankName;
        private String card;
        private String img;
        private String img2;
        private String img3;
        private String img4;
        private String img5;
        private int imgId;
        private int imgId2;
        private int imgId3;
        private int imgId4;
        private int imgId5;

        public String getImg5() {
            return img5;
        }

        public void setImg5(String img5) {
            this.img5 = img5;
        }

        public int getImgId5() {
            return imgId5;
        }

        public void setImgId5(int imgId5) {
            this.imgId5 = imgId5;
        }

        private String name;
        private String phone;

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public int getBank() {
            return bank;
        }

        public void setBank(int bank) {
            this.bank = bank;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }

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

        public String getImg2() {
            return img2;
        }

        public void setImg2(String img2) {
            this.img2 = img2;
        }

        public String getImg3() {
            return img3;
        }

        public void setImg3(String img3) {
            this.img3 = img3;
        }

        public String getImg4() {
            return img4;
        }

        public void setImg4(String img4) {
            this.img4 = img4;
        }

        public int getImgId() {
            return imgId;
        }

        public void setImgId(int imgId) {
            this.imgId = imgId;
        }

        public int getImgId2() {
            return imgId2;
        }

        public void setImgId2(int imgId2) {
            this.imgId2 = imgId2;
        }

        public int getImgId3() {
            return imgId3;
        }

        public void setImgId3(int imgId3) {
            this.imgId3 = imgId3;
        }

        public int getImgId4() {
            return imgId4;
        }

        public void setImgId4(int imgId4) {
            this.imgId4 = imgId4;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public static class BankBean {
        /**
         * id : 6
         * name : 邮政银行
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
