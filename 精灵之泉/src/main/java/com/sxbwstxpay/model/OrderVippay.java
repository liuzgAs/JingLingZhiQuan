package com.sxbwstxpay.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangjiebo on 2017/9/17 0017.
 */
public class OrderVippay {
    /**
     * wechatUrl : http://api.jlzquan.com/App/Respond/wechat.html
     * pay : {"pay_info":"AVIP201710161611116","payment_name":"微信支付","pay_money":479,"class_name":"WxApp","config":{"appid":"wxfef0031f5d8f3ed0","noncestr":"cw1q1guyhwam9w9t9z6xu0tpgqzhhvcb","package":"prepay_id=wx20171016161112aaed4a635f0042565194","partnerid":"1484853072","prepayid":"wx20171016161112aaed4a635f0042565194","timestamp":1508141472,"sign":"65a85a3c0a56d51503eb18a43790abfd","ios":{"appid":"wxfef0031f5d8f3ed0","noncestr":"cw1q1guyhwam9w9t9z6xu0tpgqzhhvcb","package":"Sign=Wxpay","partnerid":"1484853072","prepayid":"wx20171016161112aaed4a635f0042565194","timestamp":1508141472,"sign":"9dffa1fb47b077b119a79cbcceabeb93"},"packagevalue":"prepay_id=wx20171016161112aaed4a635f0042565194","subject":"AVIP201710161611116","body":null,"total_fee":479,"total_fee_format":479,"out_trade_no":null,"notify_url":"http://api.jlzquan.com/App/Respond/wechat.html","key":"f20b18c7ed7913bbf45c6ffda95c6d1a","secret":"02d7e0ca570f95630b552bd055fdd14a"}}
     * payAli : alipay_sdk=alipay-sdk-php-20161101&app_id=2017070407644871&biz_content=%7B%22body%22%3A%22%22%2C%22subject%22%3A+%22%22%2C%22out_trade_no%22%3A+%22%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fapi.jlzquan.com%2FApp%2FRespond%2Falipay.html&sign_type=RSA2&timestamp=2017-10-16+16%3A11%3A12&version=1.0&sign=su8ZQzu%2BDW0W%2BxHI5uuIPsfjy5TDI0xm786SLWex6wUU58%2BN0ZsTMOEpxGsGqBpwN9RQt3soZH5bFaP3wMm9DkN0cbiQG7wCUQ5YgrA1QHe2cw1hfMZ6f%2B1rrBojEMmfCGpnjVDD3mpD93FJKVgkQdtBU558%2BT1bXOJs1erviCXMChvV05nFLzFkFhMdrp1p9S2Drn49TAbF4pNcwQoO8JGiMZpzQr%2Fq2f%2F9smvpTlGDUU%2FGMpy%2B0afDJ8hZmJ7CZ3cqwG09slVMrtvfZZjhlCLdVgapGRa5BwTDZRR4fA5Dri80vhVQENBgTPlq5tVwwxFitd9m4o5K1SXingoizQ%3D%3D
     * status : 1
     * info : 返回成功！
     */

    private String wechatUrl;
    private PayBean pay;
    private String payAli;
    private int status;
    private String info;
    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static class PayBean {
        /**
         * pay_info : AVIP201710161611116
         * payment_name : 微信支付
         * pay_money : 479
         * class_name : WxApp
         * config : {"appid":"wxfef0031f5d8f3ed0","noncestr":"cw1q1guyhwam9w9t9z6xu0tpgqzhhvcb","package":"prepay_id=wx20171016161112aaed4a635f0042565194","partnerid":"1484853072","prepayid":"wx20171016161112aaed4a635f0042565194","timestamp":1508141472,"sign":"65a85a3c0a56d51503eb18a43790abfd","ios":{"appid":"wxfef0031f5d8f3ed0","noncestr":"cw1q1guyhwam9w9t9z6xu0tpgqzhhvcb","package":"Sign=Wxpay","partnerid":"1484853072","prepayid":"wx20171016161112aaed4a635f0042565194","timestamp":1508141472,"sign":"9dffa1fb47b077b119a79cbcceabeb93"},"packagevalue":"prepay_id=wx20171016161112aaed4a635f0042565194","subject":"AVIP201710161611116","body":null,"total_fee":479,"total_fee_format":479,"out_trade_no":null,"notify_url":"http://api.jlzquan.com/App/Respond/wechat.html","key":"f20b18c7ed7913bbf45c6ffda95c6d1a","secret":"02d7e0ca570f95630b552bd055fdd14a"}
         */

        private String pay_info;
        private String payment_name;
        private double pay_money;
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

        public double getPay_money() {
            return pay_money;
        }

        public void setPay_money(double pay_money) {
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
             * noncestr : cw1q1guyhwam9w9t9z6xu0tpgqzhhvcb
             * package : prepay_id=wx20171016161112aaed4a635f0042565194
             * partnerid : 1484853072
             * prepayid : wx20171016161112aaed4a635f0042565194
             * timestamp : 1508141472
             * sign : 65a85a3c0a56d51503eb18a43790abfd
             * ios : {"appid":"wxfef0031f5d8f3ed0","noncestr":"cw1q1guyhwam9w9t9z6xu0tpgqzhhvcb","package":"Sign=Wxpay","partnerid":"1484853072","prepayid":"wx20171016161112aaed4a635f0042565194","timestamp":1508141472,"sign":"9dffa1fb47b077b119a79cbcceabeb93"}
             * packagevalue : prepay_id=wx20171016161112aaed4a635f0042565194
             * subject : AVIP201710161611116
             * body : null
             * total_fee : 479
             * total_fee_format : 479
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
            private double total_fee;
            private double total_fee_format;
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
                 * noncestr : cw1q1guyhwam9w9t9z6xu0tpgqzhhvcb
                 * package : Sign=Wxpay
                 * partnerid : 1484853072
                 * prepayid : wx20171016161112aaed4a635f0042565194
                 * timestamp : 1508141472
                 * sign : 9dffa1fb47b077b119a79cbcceabeb93
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
