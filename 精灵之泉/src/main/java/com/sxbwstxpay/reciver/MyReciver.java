package com.sxbwstxpay.reciver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.sxbwstxpay.activity.LockActivity;
import com.sxbwstxpay.activity.ReciverActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.ExtraMap;
import com.sxbwstxpay.util.GsonUtils;

import java.util.Map;

/**
 * Created by zhangjiebo on 2017/9/16 0016.
 */
public class MyReciver extends MessageReceiver {
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
        ExtraMap extraMapBean = GsonUtils.parseJSON(extraMap, ExtraMap.class);
        extraMapBean.setTitle(title);
        extraMapBean.setSummary(summary);
        Intent intent = new Intent();
        if (Constant.MainActivityAlive == 1) {
            intent.setAction(Constant.BROADCASTCODE.EXTRAMAP);
            intent.putExtra(Constant.INTENT_KEY.EXTRAMAP, extraMapBean);
            context.sendBroadcast(intent);
        } else {
            intent.setClass(context, LockActivity.class);
            intent.putExtra(Constant.INTENT_KEY.EXTRAMAP, extraMapBean);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e("MyMessageReceiver", "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }

    @Override
    protected void onNotification(Context context, String s, String s1, Map<String, String> map) {
        super.onNotification(context, s, s1, map);
        String uid = map.get("uid");
        String code = map.get("code");
        String url = map.get("url");
        String itemId = map.get("itemId");
        ExtraMap extraMapBean = new ExtraMap(uid, code,  s,url,itemId);
        extraMapBean.setSummary(s1);
        Intent intent = new Intent();
        intent.setClass(context, ReciverActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.INTENT_KEY.EXTRAMAP, extraMapBean);
        context.startActivity(intent);
    }
}
