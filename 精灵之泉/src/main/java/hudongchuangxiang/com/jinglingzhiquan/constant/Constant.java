package hudongchuangxiang.com.jinglingzhiquan.constant;


import hudongchuangxiang.com.jinglingzhiquan.util.AppUtil;

/**
 * Created by zjb on 2016/6/12.
 */
public class Constant {
    public static String HOST = "http://192.168.1.181/index.php?key=" + AppUtil.getMD5Time();

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
    }

    public static class REQUEST_RESULT_CODE {
        public static final int CHOOSE_CITY = 2017;
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
