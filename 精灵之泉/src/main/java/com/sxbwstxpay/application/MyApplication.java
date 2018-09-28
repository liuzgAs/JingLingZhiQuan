package com.sxbwstxpay.application;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.multidex.MultiDexApplication;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.lzy.okgo.OkGo;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.interfacepage.OnPatchLister;
import com.sxbwstxpay.util.LogUtil;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.LinkedList;
import java.util.List;


/**
 * 初始化
 * Created by Administrator on 2015/12/31.
 */
public class MyApplication extends MultiDexApplication {
    private static Context context;
    private List<Activity> activityList = new LinkedList<Activity>();
    private static MyApplication instance;
    private static OnPatchLister onPatchLister;

    @Override
    public void onCreate() {
        context = this.getApplicationContext();
        super.onCreate();
        try {
            initHotfix();
        } catch (Exception e) {
        }
        ZXingLibrary.initDisplayOpinion(this);
        OkGo.init(this);
        initCloudChannel(this);
        FaceSDKManager.getInstance().initialize(this, Constant.Config.licenseID, Constant.Config.licenseFileName);

    }

    public static Context getContext() {
        return context;
    }

    /**
     * 单例模式中获取唯一的MyApplication实例
     */
    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        this.createNotificationChannel();
        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                LogUtil.LogShitou("MyApplication--onSuccess", "init cloudchannel success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                LogUtil.LogShitou("MyApplication--onFailed", "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知渠道的id
            String id = "1";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = "精灵之泉";
            // 用户可以看到的通知渠道的描述
            String description = "精灵之泉通知";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    /**添加Activity到容器中*/
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**遍历所有Activity并finish*/
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }

    public static void setOnPatchLister(OnPatchLister onPatchLister){
        MyApplication.onPatchLister =onPatchLister;
    }

    private void initHotfix() {
        String appVersion;
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion = "1.0.0";
        }

        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                //.setAesKey("0123456789123456")
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        String msg = new StringBuilder("").append("Mode:").append(mode)
                                .append(" Code:").append(code)
                                .append(" Info:").append(info)
                                .append(" HandlePatchVersion:").append(handlePatchVersion).toString();
                        LogUtil.LogShitou("MyApplication--onLoad", "" + msg);
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            LogUtil.LogShitou("MyApplication--onLoad", "补丁加载成功");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            LogUtil.LogShitou("MyApplication--onLoad", "补丁已生效");
                            onPatchLister.patchSuccess();
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                    }
                }).initialize();
    }
}
