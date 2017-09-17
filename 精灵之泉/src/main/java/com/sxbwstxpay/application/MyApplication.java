package com.sxbwstxpay.application;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.lzy.okgo.OkGo;

import com.sxbwstxpay.util.LogUtil;


/**
 * 初始化
 * Created by Administrator on 2015/12/31.
 */
public class MyApplication extends MultiDexApplication {
    private static Context context;

    @Override
    public void onCreate() {
        context = this.getApplicationContext();
        super.onCreate();
        OkGo.init(this);
        initCloudChannel(this);
    }


    public static Context getContext() {
        return context;
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
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
}
