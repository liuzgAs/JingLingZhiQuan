package com.jlzquan.www.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangjiebo on 2017/9/17 0017.
 */
public class OrderVippay {
    /**
     * wechatUrl : http://api.jlzquan.com/App/Respond/wechat.html
     * pay : {"pay_info":"AVIP201709172058109","payment_name":"微信支付","pay_money":399,"class_name":"WxApp","config":{"appid":"wxfef0031f5d8f3ed0","noncestr":"yh3ryp6q21pg1peo9tzjb7gk1hhfttu4","package":"prepay_id=wx2017091720580949dda055b10607671400","partnerid":"1484853072","prepayid":"wx2017091720580949dda055b10607671400","timestamp":1505653090,"sign":"d72e423d33c050c8d65ff89644bbe271","ios":{"appid":"wxfef0031f5d8f3ed0","noncestr":"yh3ryp6q21pg1peo9tzjb7gk1hhfttu4","package":"Sign=Wxpay","partnerid":"1484853072","prepayid":"wx2017091720580949dda055b10607671400","timestamp":1505653090,"sign":"c9d047b6cb2d84b19b638937a4925d5a"},"packagevalue":"prepay_id=wx2017091720580949dda055b10607671400","subject":"AVIP201709172058109","body":null,"total_fee":399,"total_fee_format":399,"out_trade_no":null,"notify_url":"http://api.jlzquan.com/App/Respond/wechat.html","key":"f20b18c7ed7913bbf45c6ffda95c6d1a","secret":"02d7e0ca570f95630b552bd055fdd14a"}}
     * status : 1
     * info : 返回成功！
     */

    private String wechatUrl;
    private PayBean pay;
    private int status;
    private String info;

    public String getWechatUrl() {
        return wechatUrl;
    }

    public void setWechatUrl(String wechatUrl) {
        this.wechatUrl = wechatUrl;
    }

    public PayBean getPay() {
        return pay;
    }

    public void setPay(PayBean pay) {
        this.pay = pay;
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

    public static class PayBean {
        /**
         * pay_info : AVIP201709172058109
         * payment_name : 微信支付
         * pay_money : 399
         * class_name : WxApp
         * config : {"appid":"wxfef0031f5d8f3ed0","noncestr":"yh3ryp6q21pg1peo9tzjb7gk1hhfttu4","package":"prepay_id=wx2017091720580949dda055b10607671400","partnerid":"1484853072","prepayid":"wx2017091720580949dda055b10607671400","timestamp":1505653090,"sign":"d72e423d33c050c8d65ff89644bbe271","ios":{"appid":"wxfef0031f5d8f3ed0","noncestr":"yh3ryp6q21pg1peo9tzjb7gk1hhfttu4","package":"Sign=Wxpay","partnerid":"1484853072","prepayid":"wx2017091720580949dda055b10607671400","timestamp":1505653090,"sign":"c9d047b6cb2d84b19b638937a4925d5a"},"packagevalue":"prepay_id=wx2017091720580949dda055b10607671400","subject":"AVIP201709172058109","body":null,"total_fee":399,"total_fee_format":399,"out_trade_no":null,"notify_url":"http://api.jlzquan.com/App/Respond/wechat.html","key":"f20b18c7ed7913bbf45c6ffda95c6d1a","secret":"02d7e0ca570f95630b552bd055fdd14a"}
         */

        private String pay_info;
        private String payment_name;
        private int pay_money;
        private String class_name;
        private ConfigBean config;

        public String getPay_info() {
            return pay_info;
        }

        public void setPay_info(String pay_info) {
            this.pay_info = pay_info;
        }

        public String getPayment_name() {
            return payment_name;
        }

        public void setPayment_name(String payment_name) {
            this.payment_name = payment_name;
        }

        public int getPay_money() {
            return pay_money;
        }

        public void setPay_money(int pay_money) {
            this.pay_money = pay_money;
        }

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

        public static class ConfigBean {
            /**
             * appid : wxfef0031f5d8f3ed0
             * noncestr : yh3ryp6q21pg1peo9tzjb7gk1hhfttu4
             * package : prepay_id=wx2017091720580949dda055b10607671400
             * partnerid : 1484853072
             * prepayid : wx2017091720580949dda055b10607671400
             * timestamp : 1505653090
             * sign : d72e423d33c050c8d65ff89644bbe271
             * ios : {"appid":"wxfef0031f5d8f3ed0","noncestr":"yh3ryp6q21pg1peo9tzjb7gk1hhfttu4","package":"Sign=Wxpay","partnerid":"1484853072","prepayid":"wx2017091720580949dda055b10607671400","timestamp":1505653090,"sign":"c9d047b6cb2d84b19b638937a4925d5a"}
             * packagevalue : prepay_id=wx2017091720580949dda055b10607671400
             * subject : AVIP201709172058109
             * body : null
             * total_fee : 399
             * total_fee_format : 399
             * out_trade_no : null
             * notify_url : http://api.jlzquan.com/App/Respond/wechat.html
             * key : f20b18c7ed7913bbf45c6ffda95c6d1a
             * secret : 02d7e0ca570f95630b552bd055fdd14a
             */

            private String appid;
            private String noncestr;
            @SerializedName("package")
            private String packageX;
            private String partnerid;
            private String prepayid;
            private int timestamp;
            private String sign;
            private IosBean ios;
            private String packagevalue;
            private String subject;
            private Object body;
            private int total_fee;
            private int total_fee_format;
            private Object out_trade_no;
            private String notify_url;
            private String key;
            private String secret;

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

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public IosBean getIos() {
                return ios;
            }

            public void setIos(IosBean ios) {
                this.ios = ios;
            }

            public String getPackagevalue() {
                return packagevalue;
            }

            public void setPackagevalue(String packagevalue) {
                this.packagevalue = packagevalue;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public Object getBody() {
                return body;
            }

            public void setBody(Object body) {
                this.body = body;
            }

            public int getTotal_fee() {
                return total_fee;
            }

            public void setTotal_fee(int total_fee) {
                this.total_fee = total_fee;
            }

            public int getTotal_fee_format() {
                return total_fee_format;
            }

            public void setTotal_fee_format(int total_fee_format) {
                this.total_fee_format = total_fee_format;
            }

            public Object getOut_trade_no() {
                return out_trade_no;
            }

            public void setOut_trade_no(Object out_trade_no) {
                this.out_trade_no = out_trade_no;
            }

            public String getNotify_url() {
                return notify_url;
            }

            public void setNotify_url(String notify_url) {
                this.notify_url = notify_url;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getSecret() {
                return secret;
            }

            public void setSecret(String secret) {
                this.secret = secret;
            }

            public static class IosBean {
                /**
                 * appid : wxfef0031f5d8f3ed0
                 * noncestr : yh3ryp6q21pg1peo9tzjb7gk1hhfttu4
                 * package : Sign=Wxpay
                 * partnerid : 1484853072
                 * prepayid : wx2017091720580949dda055b10607671400
                 * timestamp : 1505653090
                 * sign : c9d047b6cb2d84b19b638937a4925d5a
                 */

                private String appid;
                private String noncestr;
                @SerializedName("package")
                private String packageX;
                private String partnerid;
                private String prepayid;
                private int timestamp;
                private String sign;

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

                public String getPrepayid() {
                    return prepayid;
                }

                public void setPrepayid(String prepayid) {
                    this.prepayid = prepayid;
                }

                public int getTimestamp() {
                    return timestamp;
                }

                public void setTimestamp(int timestamp) {
                    this.timestamp = timestamp;
                }

                public String getSign() {
                    return sign;
                }

                public void setSign(String sign) {
                    this.sign = sign;
                }
            }
        }
    }
}
