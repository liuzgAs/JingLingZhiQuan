package com.sxbwstxpay.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liuzhigang on 2018/10/18/018.
 *
 * @author LiuZG
 */
public class Pay {

    /**
     * data : {"id":"135227","orderSn":"201810181010281","orderAmount":"0.01","itemId":"0","type":"20","uid":"1","baknId":"0","response":"","status":"10","ip":"223.104.6.59","des":"201810181010281","title":"201810181010281","goods_money":"本单可赚12元"}
     * pay : {"pay_info":"201810181010281","payment_name":"微信支付","pay_money":0.01,"class_name":"WxApp","config":{"appid":"wxfef0031f5d8f3ed0","noncestr":"rs886qk23fjcr10gfam6ubhruyy228e9","package":"prepay_id=wx18101029180703adee71e6e83011561172","partnerid":"1484853072","prepayid":"wx18101029180703adee71e6e83011561172","timestamp":1539828629,"sign":"2e82f66e94751d3be0e62c2d14c9e9c1","ios":{"appid":"wxfef0031f5d8f3ed0","noncestr":"rs886qk23fjcr10gfam6ubhruyy228e9","package":"Sign=Wxpay","partnerid":"1484853072","prepayid":"wx18101029180703adee71e6e83011561172","timestamp":1539828629,"sign":"a1cc9eba51925b4ab1e5e3c9d161cd45"},"packagevalue":"prepay_id=wx18101029180703adee71e6e83011561172","subject":"201810181010281","body":null,"total_fee":0.01,"total_fee_format":0.01,"out_trade_no":null,"notify_url":"http://api.jlzquan.com/App/Respond/wechat.html","key":"f20b18c7ed7913bbf45c6ffda95c6d1a","secret":"02d7e0ca570f95630b552bd055fdd14a"}}
     * payAli : alipay_sdk=alipay-sdk-php-20161101&app_id=2017070407644871&biz_content=%7B%22body%22%3A%22201810181010281%22%2C%22subject%22%3A+%22201810181010281%22%2C%22out_trade_no%22%3A+%22201810181010281%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fapi.jlzquan.com%2FApp%2FRespond%2Falipay.html&sign_type=RSA2&timestamp=2018-10-18+10%3A10%3A29&version=1.0&sign=L%2BFP5jN9B2%2FM1ajftqlbOmZpxXl7XX68QS9UC9%2Bd6CTntpmB3fBe8f%2Fv%2F7JTm5JTd7Y5pX9w8e5Fj5pPmIJrfGyP5TKAEHC6T5BtqSy%2B%2Btx5EItoPpyOKknry1np92ghYR8I5xnbqsSJcVl0hJSboFkU7pLlsX2HdKEeoV0O8tf44knuhBVurddaB1K11vg8T7InqZmFivgK54fkksAayqfKooUwbA0BrCfoww42OYhLCBaH3LNKZUQKDOgsNcZbVJTbyt1er2TWOy2X4UYRQFodBJI8xS4V1qdhECGpVB%2F8UKHbkpX40BeMRIbrpo%2FniRa9cMdlcnMe8p6iLadrsw%3D%3D
     * payState : 1
     * payDes : 支付发起成功！
     * status : 1
     * info : 返回成功！
     */

    private DataBean data;
    private PayBean pay;
    private String payAli;
    private int payState;
    private String payDes;
    private int status;
    private String info;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public int getPayState() {
        return payState;
    }

    public void setPayState(int payState) {
        this.payState = payState;
    }

    public String getPayDes() {
        return payDes;
    }

    public void setPayDes(String payDes) {
        this.payDes = payDes;
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

    public static class DataBean {
        /**
         * id : 135227
         * orderSn : 201810181010281
         * orderAmount : 0.01
         * itemId : 0
         * type : 20
         * uid : 1
         * baknId : 0
         * response :
         * status : 10
         * ip : 223.104.6.59
         * des : 201810181010281
         * title : 201810181010281
         * goods_money : 本单可赚12元
         */

        private String id;
        private String orderSn;
        private String orderAmount;
        private String itemId;
        private String type;
        private String uid;
        private String baknId;
        private String response;
        private String status;
        private String ip;
        private String des;
        private String title;
        private String goods_money;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
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

        public String getBaknId() {
            return baknId;
        }

        public void setBaknId(String baknId) {
            this.baknId = baknId;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGoods_money() {
            return goods_money;
        }

        public void setGoods_money(String goods_money) {
            this.goods_money = goods_money;
        }
    }

    public static class PayBean {
        /**
         * pay_info : 201810181010281
         * payment_name : 微信支付
         * pay_money : 0.01
         * class_name : WxApp
         * config : {"appid":"wxfef0031f5d8f3ed0","noncestr":"rs886qk23fjcr10gfam6ubhruyy228e9","package":"prepay_id=wx18101029180703adee71e6e83011561172","partnerid":"1484853072","prepayid":"wx18101029180703adee71e6e83011561172","timestamp":1539828629,"sign":"2e82f66e94751d3be0e62c2d14c9e9c1","ios":{"appid":"wxfef0031f5d8f3ed0","noncestr":"rs886qk23fjcr10gfam6ubhruyy228e9","package":"Sign=Wxpay","partnerid":"1484853072","prepayid":"wx18101029180703adee71e6e83011561172","timestamp":1539828629,"sign":"a1cc9eba51925b4ab1e5e3c9d161cd45"},"packagevalue":"prepay_id=wx18101029180703adee71e6e83011561172","subject":"201810181010281","body":null,"total_fee":0.01,"total_fee_format":0.01,"out_trade_no":null,"notify_url":"http://api.jlzquan.com/App/Respond/wechat.html","key":"f20b18c7ed7913bbf45c6ffda95c6d1a","secret":"02d7e0ca570f95630b552bd055fdd14a"}
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
             * noncestr : rs886qk23fjcr10gfam6ubhruyy228e9
             * package : prepay_id=wx18101029180703adee71e6e83011561172
             * partnerid : 1484853072
             * prepayid : wx18101029180703adee71e6e83011561172
             * timestamp : 1539828629
             * sign : 2e82f66e94751d3be0e62c2d14c9e9c1
             * ios : {"appid":"wxfef0031f5d8f3ed0","noncestr":"rs886qk23fjcr10gfam6ubhruyy228e9","package":"Sign=Wxpay","partnerid":"1484853072","prepayid":"wx18101029180703adee71e6e83011561172","timestamp":1539828629,"sign":"a1cc9eba51925b4ab1e5e3c9d161cd45"}
             * packagevalue : prepay_id=wx18101029180703adee71e6e83011561172
             * subject : 201810181010281
             * body : null
             * total_fee : 0.01
             * total_fee_format : 0.01
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
                 * noncestr : rs886qk23fjcr10gfam6ubhruyy228e9
                 * package : Sign=Wxpay
                 * partnerid : 1484853072
                 * prepayid : wx18101029180703adee71e6e83011561172
                 * timestamp : 1539828629
                 * sign : a1cc9eba51925b4ab1e5e3c9d161cd45
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
