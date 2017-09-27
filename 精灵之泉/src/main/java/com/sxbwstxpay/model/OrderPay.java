package com.sxbwstxpay.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangjiebo on 2017/9/27 0027.
 */
public class OrderPay {

    /**
     * aliPayUrl : http://api.jlzquan.com/App/Respond/alipay.html
     * data : {"des":"ABDS201709142154062","goods_money":"本单可赚12元","itemId":"0","orderAmount":"0.01","orderSn":"ABDS201709142154062","title":"ABDS201709142154062","type":"1","uid":"1"}
     * info : 返回成功！
     * pay : {"class_name":"WxApp","config":{"appid":"wxfef0031f5d8f3ed0","ios":{"appid":"wxfef0031f5d8f3ed0","noncestr":"4p8ysnfjtbu65y56yjiqqyokd0ymhq1t","package":"Sign=Wxpay","partnerid":"1484853072","sign":"ec02fe1b21e2b8163db554d01531b594","timestamp":1506501834},"key":"f20b18c7ed7913bbf45c6ffda95c6d1a","noncestr":"4p8ysnfjtbu65y56yjiqqyokd0ymhq1t","notify_url":"http://api.jlzquan.com/App/Respond/wechat.html","package":"prepay_id=","packagevalue":"prepay_id=","partnerid":"1484853072","secret":"02d7e0ca570f95630b552bd055fdd14a","sign":"d79dd911732dd3da9feec56faf4946bf","subject":"ABDS201709142154062","timestamp":1506501834,"total_fee":0.01,"total_fee_format":0.01},"pay_info":"ABDS201709142154062","pay_money":0.01,"payment_name":"微信支付"}
     * payAli : alipay_sdk=alipay-sdk-php-20161101&app_id=2017070407644871&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22subject%22%3A+%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95%22%2C%22out_trade_no%22%3A+%22ABDS201709142154062%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fapi.jlzquan.com%2FApp%2FRespond%2Falipay.html&sign_type=RSA2&timestamp=2017-09-27+16%3A43%3A55&version=1.0&sign=M2kdIjLsv5Cagv0ROwkVWP1Du2IGT%2Fu34X3Y3BPNcYy3MmuDC8Pet%2BwENIHsMgrTNMWt91sXRtmsXRc0MxA8mjzT7uf2MzYEvlbhP1lSc5bE%2B1hPTV57dGc77KjCiPBLJEwvfHVhfEZ%2BceE9I7K6%2BzR%2FtyafShr9NnqNRfIyFkiUSUfKqHBDsGiIO7fnMRkNcG35jpmM1FpzGdbIC5ryphRF%2B8unFJHwH%2Bbyv6jGzDyL1ROmRjxUTeK7fNI5QdHHcHtTFM35Mkbm8C3fZqZ7Y2d%2F4pLkRGNIZyPXiNQfdqZF3Gb%2FPPytdJM7iKpTb3CcZh4JeFXdu6gNputGotC1HA%3D%3D
     * status : 1
     * wechatUrl : http://api.jlzquan.com/App/Respond/wechat.html
     */

    private String aliPayUrl;
    private DataBean data;
    private String info;
    private PayBean pay;
    private String payAli;
    private int status;
    private String wechatUrl;

    public String getAliPayUrl() {
        return aliPayUrl;
    }

    public void setAliPayUrl(String aliPayUrl) {
        this.aliPayUrl = aliPayUrl;
    }

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

    public PayBean getPay() {
        return pay;
    }

    public void setPay(PayBean pay) {
        this.pay = pay;
    }

    public String getPayAli() {
        return payAli;
    }

    public void setPayAli(String payAli) {
        this.payAli = payAli;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWechatUrl() {
        return wechatUrl;
    }

    public void setWechatUrl(String wechatUrl) {
        this.wechatUrl = wechatUrl;
    }

    public static class DataBean {
        /**
         * des : ABDS201709142154062
         * goods_money : 本单可赚12元
         * itemId : 0
         * orderAmount : 0.01
         * orderSn : ABDS201709142154062
         * title : ABDS201709142154062
         * type : 1
         * uid : 1
         */

        private String des;
        private String goods_money;
        private String itemId;
        private String orderAmount;
        private String orderSn;
        private String title;
        private String type;
        private String uid;

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getGoods_money() {
            return goods_money;
        }

        public void setGoods_money(String goods_money) {
            this.goods_money = goods_money;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

    public static class PayBean {
        /**
         * class_name : WxApp
         * config : {"appid":"wxfef0031f5d8f3ed0","ios":{"appid":"wxfef0031f5d8f3ed0","noncestr":"4p8ysnfjtbu65y56yjiqqyokd0ymhq1t","package":"Sign=Wxpay","partnerid":"1484853072","sign":"ec02fe1b21e2b8163db554d01531b594","timestamp":1506501834},"key":"f20b18c7ed7913bbf45c6ffda95c6d1a","noncestr":"4p8ysnfjtbu65y56yjiqqyokd0ymhq1t","notify_url":"http://api.jlzquan.com/App/Respond/wechat.html","package":"prepay_id=","packagevalue":"prepay_id=","partnerid":"1484853072","secret":"02d7e0ca570f95630b552bd055fdd14a","sign":"d79dd911732dd3da9feec56faf4946bf","subject":"ABDS201709142154062","timestamp":1506501834,"total_fee":0.01,"total_fee_format":0.01}
         * pay_info : ABDS201709142154062
         * pay_money : 0.01
         * payment_name : 微信支付
         */

        private String class_name;
        private ConfigBean config;
        private String pay_info;
        private double pay_money;
        private String payment_name;

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public ConfigBean getConfig() {
            return config;
        }

        public void setConfig(ConfigBean config) {
            this.config = config;
        }

        public String getPay_info() {
            return pay_info;
        }

        public void setPay_info(String pay_info) {
            this.pay_info = pay_info;
        }

        public double getPay_money() {
            return pay_money;
        }

        public void setPay_money(double pay_money) {
            this.pay_money = pay_money;
        }

        public String getPayment_name() {
            return payment_name;
        }

        public void setPayment_name(String payment_name) {
            this.payment_name = payment_name;
        }

        public static class ConfigBean {
            /**
             * appid : wxfef0031f5d8f3ed0
             * ios : {"appid":"wxfef0031f5d8f3ed0","noncestr":"4p8ysnfjtbu65y56yjiqqyokd0ymhq1t","package":"Sign=Wxpay","partnerid":"1484853072","sign":"ec02fe1b21e2b8163db554d01531b594","timestamp":1506501834}
             * key : f20b18c7ed7913bbf45c6ffda95c6d1a
             * noncestr : 4p8ysnfjtbu65y56yjiqqyokd0ymhq1t
             * notify_url : http://api.jlzquan.com/App/Respond/wechat.html
             * package : prepay_id=
             * packagevalue : prepay_id=
             * partnerid : 1484853072
             * secret : 02d7e0ca570f95630b552bd055fdd14a
             * sign : d79dd911732dd3da9feec56faf4946bf
             * subject : ABDS201709142154062
             * timestamp : 1506501834
             * total_fee : 0.01
             * total_fee_format : 0.01
             */

            private String appid;
            private IosBean ios;
            private String key;
            private String noncestr;
            private String notify_url;
            @SerializedName("package")
            private String packageX;
            private String packagevalue;
            private String partnerid;
            private String secret;
            private String sign;
            private String subject;
            private int timestamp;
            private double total_fee;
            private double total_fee_format;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public IosBean getIos() {
                return ios;
            }

            public void setIos(IosBean ios) {
                this.ios = ios;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getNotify_url() {
                return notify_url;
            }

            public void setNotify_url(String notify_url) {
                this.notify_url = notify_url;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getPackagevalue() {
                return packagevalue;
            }

            public void setPackagevalue(String packagevalue) {
                this.packagevalue = packagevalue;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getSecret() {
                return secret;
            }

            public void setSecret(String secret) {
                this.secret = secret;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public double getTotal_fee() {
                return total_fee;
            }

            public void setTotal_fee(double total_fee) {
                this.total_fee = total_fee;
            }

            public double getTotal_fee_format() {
                return total_fee_format;
            }

            public void setTotal_fee_format(double total_fee_format) {
                this.total_fee_format = total_fee_format;
            }

            public static class IosBean {
                /**
                 * appid : wxfef0031f5d8f3ed0
                 * noncestr : 4p8ysnfjtbu65y56yjiqqyokd0ymhq1t
                 * package : Sign=Wxpay
                 * partnerid : 1484853072
                 * sign : ec02fe1b21e2b8163db554d01531b594
                 * timestamp : 1506501834
                 */

                private String appid;
                private String noncestr;
                @SerializedName("package")
                private String packageX;
                private String partnerid;
                private String sign;
                private int timestamp;

                public String getAppid() {
                    return appid;
                }

                public void setAppid(String appid) {
                    this.appid = appid;
                }

                public String getNoncestr() {
                    return noncestr;
                }

                public void setNoncestr(String noncestr) {
                    this.noncestr = noncestr;
                }

                public String getPackageX() {
                    return packageX;
                }

                public void setPackageX(String packageX) {
                    this.packageX = packageX;
                }

                public String getPartnerid() {
                    return partnerid;
                }

                public void setPartnerid(String partnerid) {
                    this.partnerid = partnerid;
                }

                public String getSign() {
                    return sign;
                }

                public void setSign(String sign) {
                    this.sign = sign;
                }

                public int getTimestamp() {
                    return timestamp;
                }

                public void setTimestamp(int timestamp) {
                    this.timestamp = timestamp;
                }
            }
        }
    }
}
