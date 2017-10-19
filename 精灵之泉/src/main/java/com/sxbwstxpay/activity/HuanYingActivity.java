package com.sxbwstxpay.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseNotLeftActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.IndexStartad;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.VersionUtils;

import java.util.HashMap;

import okhttp3.Response;

public class HuanYingActivity extends ZjbBaseNotLeftActivity {
    private String isFirst = "1";
    private String lat;
    private String lng;
    private long currentTimeMillis;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    final String city = aMapLocation.getCity();
                    final ACache aCache = ACache.get(HuanYingActivity.this, Constant.ACACHE.LOCATION);
                    lat = aMapLocation.getLatitude() + "";
                    lng = aMapLocation.getLongitude() + "";
                    Log.e("IndexFragment", "IndexFragment--onLocationChanged--city" + city + lat + lng);
                    aCache.put(Constant.ACACHE.LAT, lat);
                    aCache.put(Constant.ACACHE.LNG, lng);
                    aCache.put(Constant.ACACHE.CITY, city);
                    getData();
                } else {
                    MyDialog.dialogFinish(HuanYingActivity.this, "无法获取您的地理位置信息，请退出后重试");
                }
            }
        }
    };

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_STARTAD;
        HashMap<String, String> params = new HashMap<>();
        params.put("isFirst", isFirst);
        params.put("deviceId", PushServiceFactory.getCloudPushService().getDeviceId());
        params.put("deviceToken", "");
        params.put("type", "android");
        params.put("version", VersionUtils.getCurrVersionName(this));
        params.put("intro", "model=" + android.os.Build.MODEL + "sdk=" + android.os.Build.VERSION.SDK);
        params.put("mid", "");
        params.put("lat", lat);
        params.put("lng", lng);
        return new OkObject(params, url);
    }

    private void getData() {
        ApiClient.post(HuanYingActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("HuanYingActivity--onSuccess", "");
                try {
                    final IndexStartad indexStartad = GsonUtils.parseJSON(s, IndexStartad.class);
                    if ((System.currentTimeMillis() - currentTimeMillis) < 1000) {

                        if (indexStartad.getStatus() == 1) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                        go(indexStartad);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else {
                            MyDialog.dialogFinish(HuanYingActivity.this, indexStartad.getInfo());
                        }
                    } else {
                        go(indexStartad);
                    }
                } catch (Exception e) {
                    MyDialog.dialogFinish(HuanYingActivity.this, "数据出错");
                }

            }

            private void go(IndexStartad indexStartad) {
                if (TextUtils.equals(isFirst, "1")) {
                    Intent intent = new Intent(HuanYingActivity.this, YinDaoActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    toMainActivity();
                }
                ACache aCache = ACache.get(HuanYingActivity.this, Constant.ACACHE.LOCATION);
                aCache.put(Constant.ACACHE.CITY, indexStartad.getCityName());
                aCache.put(Constant.ACACHE.CITY_ID, indexStartad.getCityId()+"");
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                MyDialog.dialogFinish(HuanYingActivity.this,"请求失败");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huan_ying);
        initPermission();
        initLocation();
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
     * des： 申请权限
     * author： ZhangJieBo
     * date： 2017/7/6 0006 下午 2:41
     */
    private void initPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constant.PERMISSION.ACCESS_COARSE_LOCATION);//自定义的code
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constant.PERMISSION.ACCESS_FINE_LOCATION);//自定义的code
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constant.PERMISSION.WRITE_EXTERNAL_STORAGE);//自定义的code
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constant.PERMISSION.READ_EXTERNAL_STORAGE);//自定义的code
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    Constant.PERMISSION.READ_PHONE_STATE);//自定义的code
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},
                    Constant.PERMISSION.RECEIVE_BOOT_COMPLETED);//自定义的code
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请CALL_PHONE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    Constant.PERMISSION.CALL_PHONE);//自定义的code
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW)
                != PackageManager.PERMISSION_GRANTED) {
            //申请CALL_PHONE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},
                    Constant.PERMISSION.SYSTEM_ALERT_WINDOW);//自定义的code
        }

    }

    /**
     * des： 初始化定位参数
     * author： ZhangJieBo
     * date： 2017/7/6 0006 下午 2:41
     */
    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        currentTimeMillis = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void toMainActivity() {
        Intent intent = new Intent();
        if (isLogin) {
            intent.setClass(HuanYingActivity.this, LockActivity.class);
            startActivity(intent);
            finish();
        } else {
            intent.setClass(HuanYingActivity.this, DengLuActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
