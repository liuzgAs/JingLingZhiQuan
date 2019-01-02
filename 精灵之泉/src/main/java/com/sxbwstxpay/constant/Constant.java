package com.sxbwstxpay.constant;


import com.sxbwstxpay.util.AppUtil;

/**
 * Created by zjb on 2016/6/12.
 */
public class Constant {
    //        public static String HOST = "http://192.168.1.181/index.php?key=" + AppUtil.getMD5Time();
    public static String HOST = "https://img.jlzquan.com/index.php?key=" + AppUtil.getMD5Time();
    public static int MainActivityAlive = 0;
    public static int changeControl = 2017;//判断数据是否有改变
    public static String WXAPPID = "wxfef0031f5d8f3ed0";//微信appid
    public static String WXSCRENT = "02d7e0ca570f95630b552bd055fdd14a";//微信scrent
    public static String QQ_ID = "1106239952";//qq
    public static String QQ_KEY = "HcA9s2rpKkLO2M5w";//qq
    public static int CID = 0;//qq

    public static class Url {
        //app下载链接
        public static final String WEB_DOWN = "http://api.jlzquan.com/App/Web/down.html";
        //关于我们
        public static final String INFO_ABOUT = "http://api.jlzquan.com/index.php?g=App&m=Index&a=info&type=about";
        //联系客服
        public static final String INFO_CONTACT = "http://api.jlzquan.com/index.php?g=App&m=Index&a=info&type=contact";
        //服务入驻协议
        public static final String INFO_FW = "http://api.jlzquan.com/index.php?g=App&m=Index&a=info&id=13";
        //资质证书
        public static final String INFO_CA = "http://api.jlzquan.com/index.php?g=App&m=Index&a=info&type=ca";
        //注册协议
        public static final String INFO_POLICY = "http://api.jlzquan.com/index.php?g=App&m=Index&a=info&type=policy";
        //精灵之泉推广商服务协议
        public static final String INFO_POLICY2 = "http://api.jlzquan.com/index.php?g=App&m=Index&a=info&type=policy2";
        //办理信用卡
        public static final String INFO_CREDIT = "http://api.jlzquan.com/index.php?g=App&m=Index&a=info&type=credit";
        //了解VIP推广商
        public static final String WEB_VIP = "http://api.jlzquan.com/index.php?g=App&m=Web&a=vip";
        //用户使用协议
        public static final String INFO_POLICY3 = "http://api.jlzquan.com/index.php?g=App&m=Index&a=info&type=policy3";
        //功能介绍
        public static final String INFO_FEATURES = "http://api.jlzquan.com/index.php?g=App&m=Index&a=info&type=Features";
        //投诉
        public static final String INFO_COMPLAINT = "http://api.jlzquan.com/index.php?g=App&m=Index&a=info&type=complaint";
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
        //我的
        public static final String USER_INDEX = "&g=App&m=User&a=index";
        //我的资料
        public static final String USER_PROFILE = "&g=App&m=User&a=profile";
        //我的费率
        public static final String USER_RATE = "&g=App&m=User&a=rate";
        //我的商户
        public static final String USER_MYTEAM = "&g=App&m=User&a=myTeam";
        //我的商户列表
        public static final String USER_TEAM = "&g=App&m=User&a=team";
        //站内公告与帮助
        public static final String NEWS_INDEX = "&g=App&m=News&a=index";
        //版本判断
        public static final String INDEX_VERSION = "&g=App&m=Index&a=version";
        //修改密码
        public static final String USER_PWDSAVE = "&g=App&m=User&a=pwdSave";
        //启动广告页
        public static final String INDEX_STARTAD = "&g=App&m=Index&a=startAd";
        //我的资料保存
        public static final String USER_PROFILESAVE = "&g=App&m=User&a=profileSave";
        //VIP推广商请求
        public static final String ORDER_VIPBEFORE = "&g=App&m=Order&a=vipBefore";
        //我的推广二维码
        public static final String SHARE_INDEX = "g=App&m=Share&a=index";
        //VIP推广商支付
        public static final String ORDER_VIPPAY = "&g=App&m=Order&a=vipPay";
        //图文推广
        public static final String SHARE_SHARE_DAY = "&g=App&m=Share&a=share_day";
        //微信待收
        public static final String ORDER_WXPAY = "&g=App&m=Order&a=wxPay";
        //支付宝代收
        public static final String ORDER_ALIPAY = "&g=App&m=Order&a=aliPay";
        //省钱
        public static final String INDEX_MONEY = "&g=App&m=Index&a=money";
        //赚钱
        public static final String INDEX_MAKEMONEY = "&g=App&m=Index&a=makeMoney";
        //注销
        public static final String LOGIN_LOGOUT = "&g=App&m=Login&a=logOut";
        //限时购
        public static final String INDEX_GOODS = "&g=App&m=Index&a=goods";
        //专属穿搭
        public static final String INDEX_STYLE = "&g=App&m=Index&a=style";
        //专属美颜
        public static final String INDEX_STYLEMY = "&g=App&m=Index&a=stylemy";
        //精灵超市
        public static final String INDEX_SUPERMARKET = "&g=App&m=Index&a=supermarket";
        //城市选择
        public static final String INDEX_CITYLIST = "&g=App&m=Index&a=cityList";
        //限时购分类
        public static final String INDEX_CATE = "&g=App&m=Index&a=cate";
        //选品上架
        public static final String GOODS_INDEX = "&g=App&m=Goods&a=index";
        //商品上架
        public static final String INDEX_UPGOODS = "&g=App&m=Index&a=upGoods";
        //产品详情
        public static final String GOODS_INFO = "&g=App&m=Goods&a=info";
        //购物车新增
        public static final String CART_ADDCART = "&g=App&m=Cart&a=addCart";
        //购物车
        public static final String CART_INDEX = "&g=App&m=Cart&a=index";
        //购物车更新
        public static final String CART_UPDATECART = "&g=App&m=Cart&a=updateCart";
        //购物车删除
        public static final String CART_DELCART = "&g=App&m=Cart&a=delCart";
        //确认订单请求
        public static final String CART_ORDER = "&g=App&m=Cart&a=order";
        //地址保存
        public static final String USER_SAVEADDRESS = "&g=App&m=User&a=saveAddress";
        //地址列表
        public static final String USER_ADDRESS = "&g=App&m=User&a=address";
        //地址设为默认
        public static final String USER_ADDRESSDEFAULT = "&g=App&m=User&a=addressDefault";
        //删除地址
        public static final String USER_DELADDRESS = "&g=App&m=User&a=delAddress";
        //确认订单提交
        public static final String CART_NEWORDER = "&g=App&m=Cart&a=newOrder";
        //支付界面
        public static final String ORDER_PAY = "&g=App&m=Order&a=pay";
        //支付页显示0
        public static final String PAYBEFORE = "&g=App&m=Pay&a=payBefore";
        //支付信息请求1
        public static final String PAY = "&g=App&m=Pay&a=pay";
        //支付成功
        public static final String ORDER_PAYS = "&g=App&m=Order&a=pays";
        //商城订单
        public static final String USER_ORDER = "&g=App&m=User&a=order";
        //我发布的服务
        public static final String SKILL_MY = "&g=App&m=Skill&a=my";
        //精灵课堂
        public static final String CLASSROOMITEM_INDEX = "&g=App&m=ClassroomItem&a=index";
        //精灵课堂输密码加载视频
        public static final String CLASSROOMITEM_SUBPWD = "&g=App&m=ClassroomItem&a=subpwd";
        //订单操作
        public static final String USER_ORDERDONE = "&g=App&m=User&a=orderDone";
        //我的店铺
        public static final String STORE_MYSTORE = "&g=App&m=Store&a=myStore";
        //服务删除
        public static final String DELSKILL = "&g=App&m=Skill&a=delSkill";
        //服务暂停与启动
        public static final String ONOFF = "g=App&m=Skill&a=onOff";
        //发布服务请求
        public static final String SKILL_ADDBEFORE = "&g=App&m=Skill&a=addBefore";
        //服务提交
        public static final String SKILL_ADDAFTER = "&g=App&m=Skill&a=addAfter";
        //管理我的店铺
        public static final String STORE_GOODS = "&g=App&m=Store&a=goods";
        //商品下架
        public static final String INDEX_DOWNGOODS = "&g=App&m=Index&a=downGoods";
        //会员佣金提现
        public static final String USER_WITHDRAW = "&g=App&m=User&a=withdraw";
        //店铺信息
        public static final String STORE_STOREINFO = "&g=App&m=Store&a=storeInfo";
        //我的店铺信息保存
        public static final String STORE_STORESAVE = "&g=App&m=Store&a=storeSave";
        //测试请求
        public static final String TESTSTYLE = "&g=App&m=TestStyle&a=index";
        //获取测试结果
        public static final String TEST_RESULT = "&g=App&m=TestStyle&a=test_result";
        //图文二维码
        public static final String GOODS_EWM = "&g=App&m=Goods&a=ewm";
        //首页搜索
        public static final String INDEX_SEARCH = "&g=App&m=Index&a=search";
        //访客管理
        public static final String STORE_VIEWS = "&g=App&m=Store&a=views";
        //下载图文请求
        public static final String INDEX_ITEM = "&g=App&m=Index&a=item";
        //本地优品
        public static final String INDEX_PRODUCT = "&g=App&m=Index&a=product";
        //本地优店
        public static final String INDEX_STORE = "&g=App&m=Index&a=store";
        //本地优店店铺
        public static final String STORE_INDEX = "&g=App&m=Store&a=index";
        //查看服务店铺
        public static final String SKILL_DETAILS = "&g=App&m=Skill&a=details";
        //我的素材
        public static final String USER_ITEM = "&g=App&m=User&a=item";
        //发布素材提交
        public static final String ITEM_ADDAFTER = "&g=App&m=Item&a=addAfter";
        //商品素材
        public static final String ITEM_INDEX = "&g=App&m=Item&a=index";
        /**
         * 新通道信用卡支付短信请求
         */
        public static final String ORDER_BANKORDERBEFORE = "&g=App&m=Order&a=bankOrderBefore";
        /**
         * 新增信用卡支付验证码提交
         */
        public static final String ORDER_CARDBINDSUBMIT = "&g=App&m=Order&a=bankOrderSubmit";
        /**
         * 新通道添加银行卡短信请求
         */
        public static final String BANK_CARDBINDBEFORE = "&g=App&m=Bank&a=cardBindBefore";
        /**
         * 动宝币支付
         */
        public static final String ORDER_DBPAY = "&g=App&m=Order&a=dbPay";
        /**
         * 红包雨请求
         */
        public static final String INDEX_BONUSDOWN = "&g=App&m=Index&a=bonusDown";
        /**
         * 红包雨H5
         */
        public static final String INDEX_BONUSBEFORE = "&g=App&m=Index&a=bonusBefore";
        /**
         * 红包灵气接口
         */
        public static final String INDEX_BONUSGET = "&g=App&m=Index&a=bonusGet";
        /**
         * 优惠券
         */
        public static final String COUPON_INDEX = "/Coupon/index";
        /**
         * 优惠券
         */
        public static final String MAP_INDEX = "g=App&m=Map&a=index";
        /**
         * 服务主页
         */
        public static final String SKILL_INDEX = "g=App&m=Skill&a=index";
        /**
         * 业绩管理
         */
        public static final String STORE_AMOUNT = "g=App&m=Store&a=amount";
        /**
         * 我的收益4
         */
        public static final String INCOME4 = "g=App&m=User&a=income4";
        /**
         * 销售管理
         */
        public static final String STORE_SALESMANAGE = "g=App&m=Store&a=salesManage";
        /**
         * 商家认证请求
         */
        public static final String STORE_SETTLEDBEFORE = "g=App&m=Store&a=settledBefore";
        /**
         * 入驻商家支付
         */
        public static final String STORE_SETTLEDPAY = "g=App&m=Store&a=settledPay";
        /**
         * 商家认证请求
         */
        public static final String STORE_CARDBEFORE = "g=App&m=Store&a=cardBefore";
        /**
         * 商家认证提交
         */
        public static final String STORE_CARDADD = "g=App&m=Store&a=cardAdd";
        /**
         * 搜索店铺
         */
        public static final String MAP_SEARCHSTORE = "g=App&m=Map&a=searchStore";
        /**
         * 商户收款码
         */
        public static final String PAY_CHECKCODE = "g=App&m=Pay&a=checkCode";
        /**
         * 订单详情
         */
        public static final String USER_ORDERINFO = "g=App&m=User&a=orderInfo";
        /**
         * 申请售后请求
         */
        public static final String AFTER_ADDBEFORE = "g=App&m=After&a=addBefore";
        /**
         * 申请售后提交
         */
        public static final String AFTER_ADDSUBMIT = "g=App&m=After&a=addSubmit";
        /**
         * 我的信用卡
         */
        public static final String HK_CARDLIST = "g=App&m=Hk&a=cardList";
        /**
         * 信用卡删除
         */
        public static final String HK_CARDDEL = "g=App&m=Hk&a=cardDel";
        /**
         * 添加信用卡请求
         */
        public static final String HK_CARDADDBEFORE = "g=App&m=Hk&a=cardAddbefore";
        /**
         * 精灵出行
         */
        public static final String TRAVEL = "g=App&m=Index&a=travel";
        /**
         * 添加信用卡
         */
        public static final String HK_CARDADD = "?g=App&m=Hk&a=cardAdd";
        /**
         * 选择代还通道
         */
        public static final String HK_PAYMENT = "?g=App&m=Hk&a=payment";
        /**
         * 还款计划
         */
        public static final String HK_INDEX = "?g=App&m=Hk&a=index";
        /**
         * 还款明细
         */
        public static final String HK_DETAILS = "g=App&m=Hk&a=details";
        /**
         * 支付执行金额
         */
        public static final String HK_CONFIRM = "g=App&m=Hk&a=confirm";
        /**
         * 信用卡账单
         */
        public static final String HK_BILL = "?g=App&m=Hk&a=bill";
        /**
         * 信用卡账单详情
         */
        public static final String HK_BILLINFO = "?g=App&m=Hk&a=billInfo";
        /**
         * 收藏
         */
        public static final String COLLECT = "?g=App&m=Index&a=collect";
        /**
         * 取消收藏
         */
        public static final String CANCLECOLLECT = "?g=App&m=Index&a=cancleCollect";
        /**
         * 我的收藏（搭配与美颜）
         */
        public static final String STYLECOLLECT = "?g=App&m=User&a=styleCollect";
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
        public static final String img = "img";
        public static final String STYLE = "Style";
        public static final String value = "value";
        public static final String URL = "URL";
        public static final String EXTRAMAP = "ExtraMap";
        public static final String CITY = "city";
        public static final String CID = "cid";
        public static final String position = "position";
        public static final String BIG_IMG_POSITION = "bigImgPosition";
        public static final String BIG_IMG = "bigImg";
        public static final String shezhi = "shezhi";
        public static final String isFrist = "isFrist";
        public static final String Main = "Main";
        public static final String guanBi = "guanBi";
        public static final String isJiFen = "isJiFen";
        public static final String Store = "store";
    }

    public static class REQUEST_RESULT_CODE {
        public static final int IMG01 = 2018;
        public static final int IMG02 = 2019;
        public static final int IMG03 = 2020;
        public static final int IMG04 = 2021;
        public static final int IMG05 = 2022;
        public static final int XIN_YONG_KA = 2023;
        public static final int IMAGE_HEAD = 2024;
        public static final int IMAGE_WX = 2025;
        public static final int BaoCun = 2026;
        public static final int address = 2027;
        public static final int IMAGE_DIANZHAO = 2028;
        public static final int IMAGE_PICKER = 2029;
        public static final int TuiJian = 2030;
        public static final int HONG_BAO = 2031;
        public static final int EWM = 2032;
    }

    public static class ACACHE {
        public static final String App = "app";
        public static final String USER_INFO = "userInfo";
        public static final String TOKENTIME = "tokentime";
        public static final String FRIST = "frist";
        public static final String LOCATION = "location";
        public static final String LAT = "lat";
        public static final String DID = "did";
        public static final String LNG = "lng";
        public static final String CITY = "city";
        public static final String CITY_ID = "cityId";
        public static final String PAINT_PASSWORD= "paintPassword";
    }

    public static class BROADCASTCODE {
        public static final String NearFilter01 = "NearFilter01";
        public static final String PAY_RECEIVER = "pay_receiver";
        public static final String PAY_SUCCESS = "pay_success";
        public static final String WX_LOGIN = "wxLogin";
        public static final String WX_SHARE = "wxShare";
        public static final String SCREFRESH = "SCRefresh";
        public static final String WX_LOGIN_FAIL = "wxLoginFail";
        public static final String WX_SHARE_FAIL = "wxShareFail";
        public static final String EXTRAMAP = "extramap";
        public static final String CITY_CHOOSE = "cityChoose";
        public static final String CITY_CHOOSEYD = "cityChooseyd";
        public static final String CHANGEFW = "changefw";
        public static final String CHANGEWODE = "changewode";
        public static final String STYLE = "Style";
        public static final String ShangJia01 = "ShangJia01";
        public static final String ShangJia02 = "ShangJia02";
        public static final String address = "address";
        public static final String zhiFuGuanBi = "zhiFuGuanBi";
        public static final String ShuaXinDingDan = "ShuaXinDingDan";
        public static final String ShuaXinYongJin = "ShuaXinYongJin";
        public static final String ShuaXinWoDeDP = "ShuaXinWoDeDP";
        public static final String FenXiangZCLJ = "FenXiangZCLJ";
        public static final String GouWuCheNum = "GouWuCheNum";
        public static final String GuanBiShouKuan = "GuanBiShouKuan";
        public static final String ShaiXuan = "ShaiXuan";
        public static final String ShaiXuan01 = "ShaiXuan01";
        public static final String FenXiangXiaZaiLJ = "FenXiangXiaZaiLJ";
        public static final String VIP = "VIP";
        public static final String ShiMingTS = "ShiMingTS";
        public static final String MINE = "mine";
        public static final String SHUA_XIN_SHOW_HOU = "shua_xin_show_hou";
    }

    public static class VALUE{
        public static final float IndexBannerHeight = 452f;
    }
    public static class Config {




        //    // 为了apiKey,secretKey为您调用百度人脸在线接口的，如注册，比对等。
//    // 为了的安全，建议放在您的服务端，端把人脸传给服务器，在服务端端
//    // license为调用sdk的人脸检测功能使用，人脸识别 = 人脸检测（sdk功能）  + 人脸比对（服务端api）
        public static String apiKey = "替换为你的apiKey(ak)";
        public static String secretKey = "替换为你的secretKey(sk)";
        public static String licenseID = "sxbwstxpay-face-android";
        public static String licenseFileName = "idl-license.face-android";
        /*
         * <p>
         * 每个开发者账号只能创建一个人脸库；groupID用于标识人脸库
         * <p>
         * 人脸识别 接口 https://aip.baidubce.com/rest/2.0/face/v3/search
         * 人脸注册 接口 https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add
         */
        public static String groupID = "替换为你的人脸组groupID";

    }
}
