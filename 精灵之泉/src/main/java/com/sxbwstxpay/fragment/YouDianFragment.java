package com.sxbwstxpay.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.GuanLiWDDPActivity;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class YouDianFragment extends ZjbBaseFragment implements LocationSource, AMapLocationListener, View.OnClickListener {


    private View mInflate;
    MapView mMapView = null;
    AMap aMap;
    private View viewBar;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();
    private View textBar;
    private TextView textCity;

    public YouDianFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_you_dian, container, false);
            //获取地图控件引用
            mMapView = (MapView) mInflate.findViewById(R.id.map);
            //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
            mMapView.onCreate(savedInstanceState);
            init();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mInflate.getParent();
        if (parent != null) {
            parent.removeView(mInflate);
        }
        return mInflate;
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void findID() {
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        viewBar = mInflate.findViewById(R.id.viewBar);
        textBar = mInflate.findViewById(R.id.textBar);
        textCity = (TextView) mInflate.findViewById(R.id.textCity);
    }

    OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;

    @Override
    protected void initViews() {
//        viewBar.setPadding(0, (int)ScreenUtils.getStatusBarHeight(getActivity()),0,0);
        ViewGroup.LayoutParams layoutParams = textBar.getLayoutParams();
        layoutParams.height = ScreenUtils.getStatusBarHeight(getActivity());
        textBar.setLayoutParams(layoutParams);
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(17);
        aMap.animateCamera(mCameraUpdate, 300, null);
    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.imageReLocation).setOnClickListener(this);
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showYouDianDialog(marker.getId());
                return false;
            }
        });
    }

    /**
     * 优店dialog
     *
     * @param id
     */
    private void showYouDianDialog(String id) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_youdian, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.dialog).setView(view).create();
        alertDialog.show();
        view.findViewById(R.id.textJiXu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.textJinDian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.id, "1");
                intent.putExtra(Constant.INTENT_KEY.type, 1);
                intent.setClass(getActivity(), GuanLiWDDPActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        Window dialogWindow = alertDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getActivity().getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    @Override
    protected void initData() {
        latLngList.clear();
        latLngList.add(new LatLng(24.490663, 118.195661));
        latLngList.add(new LatLng(24.489862, 118.195516));
        latLngList.add(new LatLng(24.491542, 118.198107));
        latLngList.add(new LatLng(24.492923, 118.194749));
        markerList.clear();
        for (int i = 0; i < latLngList.size(); i++) {
            final MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(latLngList.get(i));
            final View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_marker, null);
            final ImageView imageImg = (ImageView) view.findViewById(R.id.imageImg);
            GlideApp.with(getActivity())
                    .asBitmap()
                    .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517284812033&di=43583b08e918ffe4ac0e0ba2c2b62a36&imgtype=0&src=http%3A%2F%2Fk1.jsqq.net%2Fuploads%2Fallimg%2F160311%2F5-1603111331390-L.jpg")
                    .centerCrop()
                    .placeholder(R.mipmap.ic_empty)
                    .dontAnimate()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            imageImg.setImageBitmap(resource);
                            markerOption.icon(BitmapDescriptorFactory.fromView(view));
                            markerList.add(aMap.addMarker(markerOption));
                        }
                    });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(getActivity());
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //获取一次定位结果：
            //该方法默认为false。
            mLocationOption.setOnceLocation(true);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
            showLoadingDialog();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                textCity.setText(aMapLocation.getAddress());
                cancelLoadingDialog();
                LogUtil.LogShitou("YouDianFragment--onLocationChanged", "" + new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()).toString());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 17);
                aMap.moveCamera(cameraUpdate);
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageReLocation:
                showLoadingDialog();
                LogUtil.LogShitou("YouDianFragment--onClick", "重新定位");
                mlocationClient.startLocation();
                break;
            default:
                break;
        }
    }
}
