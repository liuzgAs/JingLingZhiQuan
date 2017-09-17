package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseNotLeftActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.VersionUtils;

import java.util.HashMap;

import okhttp3.Response;

public class HuanYingActivity extends ZjbBaseNotLeftActivity {
    private String isFirst = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huan_ying);
        init();
    }

    @Override
    protected void initSP() {
        ACache aCache = ACache.get(HuanYingActivity.this, Constant.ACACHE.FRIST);
        String frist = aCache.getAsString(Constant.ACACHE.FRIST);
        if (frist != null) {
            isFirst = frist;
        }
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_STARTAD;
        HashMap<String, String> params = new HashMap<>();
        params.put("isFirst",isFirst);
        params.put("deviceId", PushServiceFactory.getCloudPushService().getDeviceId());
        params.put("deviceToken", "");
        params.put("type", "android");
        params.put("version", VersionUtils.getCurrVersionName(this));
        params.put("intro", "model=" + android.os.Build.MODEL + "sdk=" + android.os.Build.VERSION.SDK);
        params.put("mid", "");
        return new OkObject(params, url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApiClient.post(HuanYingActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("HuanYingActivity--onSuccess", "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus()==1){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                    if (TextUtils.equals(isFirst, "1")) {
                                        Intent intent = new Intent(HuanYingActivity.this, YinDaoActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        toMainActivity();
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }else if (simpleInfo.getStatus()==2){
                       MyDialog.dialogFinish(HuanYingActivity.this,"0.0");
                    }else {
                        MyDialog.dialogFinish(HuanYingActivity.this,simpleInfo.getInfo());
                    }
                } catch (Exception e) {
                    MyDialog.dialogFinish(HuanYingActivity.this,"数据出错");
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(HuanYingActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toMainActivity() {
        Intent intent = new Intent();
        if (isLogin) {
            intent.setClass(HuanYingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            intent.setClass(HuanYingActivity.this, DengLuActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
