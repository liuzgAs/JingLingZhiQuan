package com.sxbwstxpay.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Tip;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.GuanLiWDDPXActivity;
import com.sxbwstxpay.activity.SearchLocationActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SkillIndex;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.PcateViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class YouDianXFragment extends ZjbBaseFragment implements LocationSource, AMapLocationListener, View.OnClickListener {


    private View mInflate;
    MapView mMapView = null;
    AMap aMap;
    private View viewBar;
    List<Marker> markerList = new ArrayList<>();
    private View textBar;
    private TextView textCity;
    private boolean isFrist = true;
    private List<SkillIndex.DataBean> dataBeanList;
    private String city;
    private TextView textAddress;
    private int mapLV = 15;
    private LatLng myLatLng;
    private ImageView image0001;
    private int cid=0;
    private String cityId;

    public YouDianXFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_you_dianx, container, false);
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
        final ACache aCache = ACache.get(getActivity(), Constant.ACACHE.LOCATION);
        String cityAcache = aCache.getAsString(Constant.ACACHE.CITY);
        if (cityAcache != null) {
            cityId = aCache.getAsString(Constant.ACACHE.CITY_ID);
        }
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
        textAddress = (TextView) mInflate.findViewById(R.id.textAddress);
        image0001 = (ImageView) mInflate.findViewById(R.id.image0001);
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
        CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(mapLV);
        aMap.moveCamera(mCameraUpdate);
        setPopwindow();
    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.imageReLocation).setOnClickListener(this);
        mInflate.findViewById(R.id.viewSearch).setOnClickListener(this);
        image0001.setOnClickListener(this);
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String title = marker.getTitle();
                showYouDianDialog(title);
                LogUtil.LogShitou("YouDianFragment--onMarkerClick", "" + title);
                return false;
            }
        });
//        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
//            @Override
//            public void onCameraChange(CameraPosition cameraPosition) {
//
//            }
//
//            @Override
//            public void onCameraChangeFinish(CameraPosition cameraPosition) {
//                LatLng target = cameraPosition.target;
//                getStore(target);
//            }
//        });
    }

    /**
     * 优店dialog
     *
     * @param id
     */
    private void showYouDianDialog(String id) {
        LogUtil.LogShitou("YouDianFragment--showYouDianDialog", id + "");
        LogUtil.LogShitou("YouDianFragment--showYouDianDialog", Integer.parseInt(id) + "");
        final SkillIndex.DataBean dataBean = dataBeanList.get(Integer.parseInt(id));
        LogUtil.LogShitou("YouDianFragment--showYouDianDialog", "00000000");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_youdianx, null);
        final Dialog dialog = new Dialog(getActivity(), R.style.dialogx);
        dialog.setContentView(view);
        view.findViewById(R.id.textJinDian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.id, String.valueOf(dataBean.getSid()));
                intent.putExtra(Constant.INTENT_KEY.type, 1);
                intent.setClass(getActivity(), GuanLiWDDPXActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        LogUtil.LogShitou("YouDianFragment--showYouDianDialog", "11111111111");
        TextView textTitle = (TextView) view.findViewById(R.id.textTitle);
        TextView textNickName = (TextView) view.findViewById(R.id.textNickName);
        TextView textDistance = (TextView) view.findViewById(R.id.textDistance);
        TextView textDes = (TextView) view.findViewById(R.id.textDes);
        TextView textNickDes = (TextView) view.findViewById(R.id.textNickDes);
        ImageView imageImg = (ImageView) view.findViewById(R.id.imageImg);
        GlideApp.with(getActivity())
                .load(dataBean.getHeadImg())
                .centerCrop()
                .circleCrop()
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textTitle.setText(dataBean.getTitle());
        textNickName.setText(dataBean.getNickName());
        textDistance.setText(dataBean.getDistance());
        textNickDes.setText(dataBean.getNickNameDes());
        textDes.setText(dataBean.getDes());
        LogUtil.LogShitou("YouDianFragment--showYouDianDialog", "2222222222");
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getActivity().getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.95); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    @Override
    protected void initData() {

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
                city = aMapLocation.getCity();
                textCity.setText(city);
                LogUtil.LogShitou("YouDianFragment--onLocationChanged", "" + new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()).toString());
                myLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(myLatLng, mapLV);
                aMap.animateCamera(cameraUpdate, 500, null);
                getStore(myLatLng);
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject(LatLng latLng) {
        String url = Constant.HOST + Constant.Url.SKILL_INDEX;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("lat", String.valueOf(latLng.latitude));
        params.put("lng", String.valueOf(latLng.longitude));
        params.put("cid", cid+"");
        params.put("cityId", cityId+"");
        return new OkObject(params, url);
    }

    private void getStore(LatLng latLng) {
//        showLoadingDialog();
        ApiClient.post(getActivity(), getOkObject(latLng), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
//                cancelLoadingDialog();
                LogUtil.LogShitou("YouDianFragment--onSuccess", s + "");
                try {
                    SkillIndex mapIndex = GsonUtils.parseJSON(s, SkillIndex.class);
                    if (mapIndex.getStatus() == 1) {
                        dataBeanList = mapIndex.getData();
                        popAdapter.clear();
                        popAdapter.addAll(mapIndex.getCate());
                        shouMarker();
                    } else if (mapIndex.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        Toast.makeText(getActivity(), mapIndex.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
//                cancelLoadingDialog();
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_RESULT_CODE.address && resultCode == Constant.REQUEST_RESULT_CODE.address) {
            Tip tip = data.getParcelableExtra(Constant.INTENT_KEY.value);
            SkillIndex.DataBean mapMarkerBean = (SkillIndex.DataBean) data.getSerializableExtra(Constant.INTENT_KEY.Store);
            if (tip != null) {
                textAddress.setText(tip.getName());
                LatLonPoint point = tip.getPoint();
                LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, mapLV);
//            aMap.moveCamera(cameraUpdate);
                aMap.animateCamera(cameraUpdate, 500, null);
                getStore(latLng);
            }

            if (mapMarkerBean != null) {
                textAddress.setText(mapMarkerBean.getNickName());
                LatLng latLng = new LatLng(Double.parseDouble(mapMarkerBean.getLat()), Double.parseDouble(mapMarkerBean.getLng()));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, mapLV);
//            aMap.moveCamera(cameraUpdate);
                aMap.animateCamera(cameraUpdate, 500, null);
                dataBeanList = new ArrayList<>();
                dataBeanList.add(mapMarkerBean);
                shouMarker();
            }
        }
    }

    private void shouMarker() {
        for (int i = 0; i < markerList.size(); i++) {
            markerList.get(i).remove();
        }
        markerList.clear();
        for (int i = 0; i < dataBeanList.size(); i++) {
            final MarkerOptions markerOption = new MarkerOptions();
            markerOption.infoWindowEnable(false);
            markerOption.title(String.valueOf(i));
            markerOption.position(new LatLng(Double.parseDouble(dataBeanList.get(i).getLat()), Double.parseDouble(dataBeanList.get(i).getLng())));
            if (dataBeanList.get(i).getSettled() == 1) {
                final View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_marker_shop, null);
                final ImageView imageImg = (ImageView) view.findViewById(R.id.imageImg);
                GlideApp.with(getActivity())
                        .asBitmap()
                        .load(dataBeanList.get(i).getHeadImg())
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
            } else {
                final View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_marker, null);
                final ImageView imageImg = (ImageView) view.findViewById(R.id.imageImg);
                GlideApp.with(getActivity())
                        .asBitmap()
                        .load(dataBeanList.get(i).getHeadImg())
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewSearch:
                Intent intent = new Intent();
                intent.setClass(getActivity(), SearchLocationActivity.class);
                intent.putExtra(Constant.INTENT_KEY.CITY, city);
                intent.putExtra(Constant.INTENT_KEY.position, myLatLng);
                startActivityForResult(intent, Constant.REQUEST_RESULT_CODE.address);
                break;
            case R.id.imageReLocation:
                textAddress.setText("");
                mlocationClient.startLocation();
                break;
            case R.id.image0001:
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                } else {
                    // 设置PopupWindow 显示的形式 底部或者下拉等
                    // 在某个位置显示
                    viewBar.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));
                    mPopupWindow.showAsDropDown(image0001);
                    // 作为下拉视图显示
                    // mPopupWindow.showAsDropDown(mPopView, Gravity.CENTER, 200, 300);
                }
                break;
            default:
                break;
        }
    }

    private View mPopView;
    private PopupWindow mPopupWindow;
    private RecyclerArrayAdapter<SkillIndex.CateBean> popAdapter;

    private void setPopwindow(){
        mPopView = getLayoutInflater().inflate(R.layout.popwindow_pcate, null);
        // 将转换的View放置到 新建一个popuwindow对象中
        mPopupWindow = new PopupWindow(mPopView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        // 点击popuwindow外让其消失
        EasyRecyclerView recyclePacate=mPopView.findViewById(R.id.recyclePacate);
        initPopRecycler(recyclePacate);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                viewBar.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.transparent));
            }
        });
    }

    private void initPopRecycler(EasyRecyclerView recyclePacate) {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        recyclePacate.setLayoutManager(manager);
        SpaceDecoration spaceDecoration =new SpaceDecoration((int) DpUtils.convertDpToPixel(10f, getActivity()));
//        recyclerView.addItemDecoration(itemDecoration1);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclePacate.addItemDecoration(spaceDecoration);
        recyclePacate.setAdapter(popAdapter = new RecyclerArrayAdapter<SkillIndex.CateBean>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_pcate;
                return new PcateViewHolder(parent, layout);
            }
        });
        manager.setSpanSizeLookup(popAdapter.obtainGridSpanSizeLookUp(1));
        popAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mPopupWindow.dismiss();
                viewBar.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.transparent));
                cid=popAdapter.getItem(position).getId();
                Constant.CID=popAdapter.getItem(position).getId();
                getStore(myLatLng);
            }
        });
    }
}
