package com.sxbwstxpay.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.luoxudong.app.threadpool.ThreadPoolHelp;
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
import java.util.List;

import okhttp3.Response;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class HuanYingActivity extends ZjbBaseNotLeftActivity implements EasyPermissions.PermissionCallbacks{
    private static final int LOCATION = 1991;
    private String isFirst = "1";
    private String lat;
    private String lng;
    private long currentTimeMillis;
    private int GPS_REQUEST_CODE = 10;
    private ImageView imageImg;
    private TextView textDaoJiShi;

    private boolean isBreak = true;
    private int daoJiShi =3;
    /**声明AMapLocationClient类对象*/
    public AMapLocationClient mLocationClient = null;
    /**声明AMapLocationClientOption对象*/
    public AMapLocationClientOption mLocationOption = null;
    /**声明定位回调监听器*/
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
                    final ACache aCache = ACache.get(HuanYingActivity.this, Constant.ACACHE.LOCATION);
                    String city = aCache.getAsString(Constant.ACACHE.CITY);
                    if (TextUtils.isEmpty(city)){
                        MyDialog.dialogFinish(HuanYingActivity.this, "定位失败");
                    }else {
                        getData();
                    }
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
                LogUtil.LogShitou("HuanYingActivity--onSuccess", s);
                try {
                    final IndexStartad indexStartad = GsonUtils.parseJSON(s, IndexStartad.class);
                    if ((System.currentTimeMillis() - currentTimeMillis) < 1000) {

                        if (indexStartad.getStatus() == 1) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                        gos(indexStartad);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else {
                            MyDialog.dialogFinish(HuanYingActivity.this, indexStartad.getInfo());
                        }
                    } else {
                        gos(indexStartad);
                    }
                } catch (Exception e) {
                    MyDialog.dialogFinish(HuanYingActivity.this, "数据出错");
                }

            }
            private void gos(final IndexStartad indexStartad) {
                ACache aCache = ACache.get(HuanYingActivity.this, Constant.ACACHE.LOCATION);
                aCache.put(Constant.ACACHE.DID, indexStartad.getDid() + "");
                aCache.put(Constant.ACACHE.CITY, indexStartad.getCityName());
                aCache.put(Constant.ACACHE.CITY_ID, indexStartad.getCityId()+"");
//                if (TextUtils.equals(isFirst, "1")) {
//                    Intent intent = new Intent(HuanYingActivity.this, YinDaoActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
                    if (indexStartad.getAdvs().size() > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RequestOptions options = new RequestOptions();
                                options.centerCrop()
                                        .placeholder(R.mipmap.welcome01)
                                        .error(R.mipmap.welcome01);
                                Glide.with(HuanYingActivity.this)
                                        .load(indexStartad.getAdvs().get(0).getImg())
                                        .apply(options)
                                        .listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                textDaoJiShi.setVisibility(View.VISIBLE);
                                                textDaoJiShi.setText(daoJiShi+"s");
                                                textDaoJiShi.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        isBreak = false;
                                                        toMainActivity();
                                                    }
                                                });
                                                imageImg.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        if (!TextUtils.isEmpty(indexStartad.getAdvs().get(0).getCode())){
                                                            isBreak = false;
//                                                            Intent intent = new Intent();
//                                                            intent.setClass(HuanYingActivity.this, MainActivity.class);
//                                                            startActivity(intent);
//                                                            finish();
                                                            toMainActivity();
                                                        }
                                                    }
                                                });
                                                ThreadPoolHelp.Builder
                                                        .cached()
                                                        .builder()
                                                        .execute(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    while(isBreak){
                                                                        Thread.sleep(1000);
                                                                        daoJiShi--;
                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                textDaoJiShi.setText(daoJiShi+"s");
                                                                            }
                                                                        });
                                                                        if (daoJiShi==0){
                                                                            isBreak=false;
                                                                            toMainActivity();
                                                                        }
                                                                    }
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        });
                                                return false;
                                            }
                                        })
                                        .transition(new DrawableTransitionOptions().crossFade(1000))
                                        .into(imageImg);
                            }
                        });
                    }
//                    else {
//                        toMainActivity();
//                    }
//                }
            }
            private void gog(IndexStartad indexStartad) {
                ACache aCache = ACache.get(HuanYingActivity.this, Constant.ACACHE.LOCATION);
                aCache.put(Constant.ACACHE.CITY, indexStartad.getCityName());
                aCache.put(Constant.ACACHE.CITY_ID, indexStartad.getCityId()+"");
//                if (TextUtils.equals(isFirst, "1")) {
//                    Intent intent = new Intent(HuanYingActivity.this, YinDaoActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
                toMainActivity();
//                }
            }

            @Override
            public void onError(Response response) {
                try {
                    MyDialog.dialogFinish(HuanYingActivity.this,"请求失败");
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huan_ying);
        if (!checkGPSIsOpen()){
            LogUtil.LogShitou("HuanYingActivity--onCreate", "GPS未开启");
            openGPSSettings();
        }else {
            LogUtil.LogShitou("HuanYingActivity--onCreate", "GPS开启");
            methodRequiresTwoPermission();
        }
        init(HuanYingActivity.class);
    }

    @AfterPermissionGranted(LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            initLocation();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "需要开启定位权限",
                    LOCATION, perms);
//            initLocation();
        }
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
        imageImg = findViewById(R.id.imageImg);
        textDaoJiShi = findViewById(R.id.textDaoJiShi);
    }

    @Override
    protected void initViews() {
        textDaoJiShi.setVisibility(View.GONE);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
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
    public void onStart() {
        super.onStart();
        isBackground =false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void toMainActivity() {
        Intent intent = new Intent();
        if (isLogin) {
            if (!TextUtils.isEmpty(paintPassword)){
                intent.setClass(HuanYingActivity.this, LockActivity.class);
                intent.putExtra(Constant.INTENT_KEY.Main,"Main");
                startActivity(intent);
                finish();
            }else {
                intent.setClass(HuanYingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            intent.setClass(HuanYingActivity.this, DengLuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, "开启定位成功", Toast.LENGTH_SHORT)
                    .show();
            initLocation();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }else {
//            methodRequiresTwoPermission();
            initLocation();
        }
    }

    /**
     * 检测GPS是否打开
     *
     * @return
     */
    private boolean checkGPSIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    /**
     * 跳转GPS设置
     */
    private void openGPSSettings() {
        if (checkGPSIsOpen()) {
            methodRequiresTwoPermission();
        } else {
            //没有打开则弹出对话框
            new AlertDialog.Builder(this)
                    .setTitle(R.string.notifyTitle)
                    .setMessage(R.string.gpsNotifyMsg)
                    // 拒绝, 退出应用
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })

                    .setPositiveButton(R.string.setting,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //跳转GPS设置界面
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivityForResult(intent, GPS_REQUEST_CODE);
                                }
                            })

                    .setCancelable(false)
                    .show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            //做需要做的事情，比如再次检测是否打开GPS了 或者定位
            openGPSSettings();
        }
    }
}
