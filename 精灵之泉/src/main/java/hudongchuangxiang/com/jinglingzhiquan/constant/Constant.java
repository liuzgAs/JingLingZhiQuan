package hudongchuangxiang.com.jinglingzhiquan.constant;


import hudongchuangxiang.com.jinglingzhiquan.util.AppUtil;

/**
 * Created by zjb on 2016/6/12.
 */
public class Constant {
    //    public static String HOST = "http://192.168.1.181/index.php?key=" + AppUtil.getMD5Time();
    public static String HOST = "http://api.jlzquan.com/index.php?key=" + AppUtil.getMD5Time();

    public static int changeControl = 2017;//判断数据是否有改变

    public static class Url {
        //登录
        public static final String LOGIN_INDEX = "&g=App&m=Login&a=index";
        //注册验证码
        public static final String LOGIN_REGSMS = "&g=App&m=Login&a=regSms";
        //注册
        public static final String LOGIN_REGISTER = "&g=App&m=Login&a=register";
        //忘记密码验证码
        public static final String LOGIN_FORGETSMS = "&g=App&m=Login&a=forgetSms";
        //忘记密码
        public static final String LOGIN_FORGET = "&g=App&m=Login&a=forget";
        //会员身份认证请求
        public static final String USER_CARDBEFORE = "&g=App&m=User&a=cardBefore";
        //实名认证时验证码
        public static final String LOGIN_BINDSMS = "&g=App&m=Login&a=bindSms";
        //实名认证下一步
        public static final String LOGIN_BINDNEXT = "&g=App&m=Login&a=bindNext";
        //图片单个上传
        public static final String RESPOND_APPIMGADD = "&g=App&m=Respond&a=appImgAdd";
        //会员身份认证提交
        public static final String USER_CARDADD = "&g=App&m=User&a=cardAdd";
        //选择支付通道
        public static final String BANK_PAYMENT = "&g=App&m=Bank&a=payment";
        //选择银行卡
        public static final String BANK_CARDLIST = "&g=App&m=Bank&a=cardList";
        //银行卡添加前请求
        public static final String BANK_CARDADDBEFORE = "&g=App&m=Bank&a=cardAddbefore";
        //银行卡添加提交
        public static final String BANK_CARDADD = "&g=App&m=Bank&a=cardAdd";
        //代收代付提交
        public static final String ORDER_NEWORDER = "&g=App&m=Order&a=neworder";
        //赚钱
        public static final String INDEX_MAKEMONEY = "&g=App&m=Index&a=makeMoney";
        //会员我的分润
        public static final String USER_INCOME1 = "&g=App&m=User&a=income1";
        //会员推广佣金
        public static final String USER_INCOME2 = "&g=App&m=User&a=income2";
        //会员平台返佣
        public static final String USER_INCOME3 = "&g=App&m=User&a=income3";
        //会员收益
        public static final String USER_INCOME = "&g=App&m=User&a=income";
        //收款前请求
        public static final String ORDER_RECEIPTBEFORE = "&g=App&m=Order&a=receiptBefore";
        //会员我的账单
        public static final String USER_MONEYLOG = "&g=App&m=User&a=moneyLog";
        //银行卡删除
        public static final String BANK_CARDDEL = "&g=App&m=Bank&a=cardDel";
    }

    public static class PERMISSION {
        public static final int ACCESS_COARSE_LOCATION = 0;
        public static final int ACCESS_FINE_LOCATION = 1;
        public static final int WRITE_EXTERNAL_STORAGE = 2;
        public static final int READ_EXTERNAL_STORAGE = 3;
        public static final int READ_PHONE_STATE = 4;
        public static final int PERMISSION_READ_SMS = 5;
        public static final int RECEIVE_BOOT_COMPLETED = 6;
        public static final int RECEIVE_WRITE_SETTINGS = 7;
        public static final int RECEIVE_VIBRATE = 8;
        public static final int RECEIVE_DISABLE_KEYGUARD = 9;
        public static final int CALL_PHONE = 10;
        public static final int SYSTEM_ALERT_WINDOW = 11;
        public static final int PERMISSION_WRITE_EXTERNAL_STORAGE_SSENGJI = 12;
        public static final int CAMERA = 13;
        public static final int MOUNT_UNMOUNT_FILESYSTEMS = 14;
        public static final int READ_CONTACTS = 15;

    }

    public static class INTENT_KEY {
        public static final String TITLE = "title";
        public static final String PHONE = "phone";
        public static final String amount = "amount";
        public static final String type = "type";
        public static final String id = "id";
        public static final String tongDaoId = "tongDaoId";
    }

    public static class REQUEST_RESULT_CODE {
        public static final int CHOOSE_CITY = 2017;
        public static final int IMG01 = 2018;
        public static final int IMG02 = 2019;
        public static final int IMG03 = 2020;
        public static final int IMG04 = 2021;
        public static final int IMG05 = 2022;
        public static final int XIN_YONG_KA = 2023;
    }

    public static class ACACHE {
        public static final String App = "app";
        public static final String USER_INFO = "userInfo";
        public static final String TOKENTIME = "tokentime";
        public static final String FRIST = "frist";
    }

    public static class BROADCASTCODE {
        public static final String NearFilter01 = "NearFilter01";
    }

}
