package com.sxbwstxpay.fragment;


import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ChengShiXZActivity;
import com.sxbwstxpay.activity.MainActivity;
import com.sxbwstxpay.activity.SouSuoActivity;
import com.sxbwstxpay.activity.WebHongBaoActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ToLoginActivity;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.IndexBonusbefore;
import com.sxbwstxpay.model.IndexBonusdown;
import com.sxbwstxpay.model.IndexBonusget;
import com.sxbwstxpay.model.IndexCitylist;
import com.sxbwstxpay.model.IndexDataBean;
import com.sxbwstxpay.model.IndexGoods;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.util.TabsIndicator;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import okhttp3.Response;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShouYeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShouYeFragment extends ZjbBaseFragment implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private View mInflate;
    private TextView textCity;
    private ImageView imgeEWM;
    private View viewBar;
    private TabLayout tablayout;
    private ViewPager viewPager;
    private ImageView imageHongBaoDialog;
    private ArrayList<String> titles=new ArrayList<>();
    private Dialog mDialog;
    private String mCity;
    private String lat;
    private String lng;
    private String cityId;
    private boolean isHongBaoShow = false;
    private BroadcastReceiver reciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.CITY_CHOOSE:
                    IndexCitylist.CityEntity.ListEntity cityBean = (IndexCitylist.CityEntity.ListEntity) intent.getSerializableExtra(Constant.INTENT_KEY.CITY);
                    cityId = cityBean.getId();
                    final ACache aCache = ACache.get(getActivity(), Constant.ACACHE.LOCATION);
                    aCache.put(Constant.ACACHE.CITY_ID, cityId);
                    textCity.setText(cityBean.getName());
                    initData();
                    break;
                default:
                    break;
            }
        }
    };
    public ShouYeFragment() {
    }

    public static ShouYeFragment newInstance(String param1) {
        ShouYeFragment fragment = new ShouYeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_shou_ye, container, false);
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
            mCity = cityAcache;
            lat = aCache.getAsString(Constant.ACACHE.LAT);
            lng = aCache.getAsString(Constant.ACACHE.LNG);
            cityId = aCache.getAsString(Constant.ACACHE.CITY_ID);
        }
        titles.clear();
        titles.add("专属潮搭");
        titles.add("专属美颜");
        titles.add("精灵超市");
        titles.add("限时抢购");

    }

    @Override
    protected void findID() {
        textCity=mInflate.findViewById(R.id.textCity);
        imgeEWM=mInflate.findViewById(R.id.imgeEWM);
        viewBar=mInflate.findViewById(R.id.viewBar);
        tablayout=mInflate.findViewById(R.id.tablayout);
        viewPager=mInflate.findViewById(R.id.viewPager);
        imageHongBaoDialog=mInflate.findViewById(R.id.imageHongBaoDialog);

    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) DpUtils.convertDpToPixel(120, getActivity()) + ScreenUtils.getStatusBarHeight(getActivity());
        viewBar.setLayoutParams(layoutParams);
        viewBar.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);

        viewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager()));
        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.post(new Runnable() {
            @Override
            public void run() {
                TabsIndicator.setIndicator(tablayout, 18, 18);
            }
        });
        for (int i = 0; i < titles.size(); i++) {
            tablayout.getTabAt(i).setText(titles.get(i));
        }
        viewPager.setCurrentItem(0);
    }

    @Override
    protected void setListeners() {
        imgeEWM.setOnClickListener(this);
        mInflate.findViewById(R.id.textSouSuo).setOnClickListener(this);
        textCity.setOnClickListener(this);
        imageHongBaoDialog.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        xianShiQiangGou();
    }
    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getXianShiQGOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_GOODS;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("p", "1");
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("id", "0");
        params.put("cityId", cityId);
        return new OkObject(params, url);
    }
    private void xianShiQiangGou() {
        ApiClient.post(getActivity(), getXianShiQGOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("限时购", s);
                try {
                    IndexGoods indexGoods = GsonUtils.parseJSON(s, IndexGoods.class);
                    if (indexGoods.getStatus() == 1) {
                        List<IndexDataBean> indexGoodsData = indexGoods.getData();
                        if (!TextUtils.isEmpty(indexGoods.getTipsContent())){
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("提示")
                                    .setMessage(indexGoods.getTipsContent())
                                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                    } else if (indexGoods.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        Toast.makeText(getActivity(),indexGoods.getInfo(),Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"数据出错",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                Toast.makeText(getActivity(),"网络出错",Toast.LENGTH_SHORT).show();
            }

        });
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.imgeEWM:
                //初始化权限
                methodRequiresTwoPermission();
                break;
            case R.id.imageHongBaoDialog:
                hongBaoQingQing();
                break;
            case R.id.textSouSuo:
                intent.putExtra(Constant.INTENT_KEY.type, 0);
                intent.setClass(getActivity(), SouSuoActivity.class);
                startActivity(intent);
                break;
            case R.id.textCity:
                intent.setClass(getActivity(), ChengShiXZActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_RESULT_CODE.HONG_BAO && resultCode == Constant.REQUEST_RESULT_CODE.HONG_BAO) {
            IndexBonusget indexBonusget = (IndexBonusget) data.getSerializableExtra(Constant.INTENT_KEY.value);
            showHongBaoDialog(indexBonusget);
        }
        if (requestCode == Constant.REQUEST_RESULT_CODE.EWM && resultCode == Constant.REQUEST_RESULT_CODE.EWM) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    LogUtil.LogShitou("XianShiQGFragment--onActivityResult", ""+result);
                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0){
                return ZhuanShuCDFragment.newInstance("");
            }
            else if (position==1){
                return ZhuanShuMYFragment.newInstance("");
            }
            else if (position==2){
                return JingLingCSFragment.newInstance("");
            }
            else {
                return new XianShiQGXFragment();
            }
        }

        @Override
        public int getCount() {
            return titles.size();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.CITY_CHOOSE);
        getActivity().registerReceiver(reciver, filter);
        if (!isHongBaoShow) {
            hongBaoQingQing();
            isHongBaoShow = true;
        }
    }
    private static final int CAMERA = 1991;

    @AfterPermissionGranted(CAMERA)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            // Already have permission, do the thing
            ewm();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "需要开启定位权限",
                    CAMERA, perms);
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
            ewm();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }else {
            methodRequiresTwoPermission();
        }
    }
    /**
     * 扫描二维码
     */
    private void ewm() {
        ((MainActivity) getActivity()).isChoosePic = true;
        Intent intent = new Intent();
        intent.setClass(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, Constant.REQUEST_RESULT_CODE.EWM);
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).isChoosePic = false;
    }

    /**
     * 抢到红包
     *
     * @param indexBonusget
     */
    private void showHongBaoDialog(IndexBonusget indexBonusget) {
        int screenWidth = ScreenUtils.getScreenWidth(getActivity());
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_hongbaolq, null);
        TextView texthongBao = (TextView) inflate.findViewById(R.id.texthongBao);

        texthongBao.setText(indexBonusget.getMoney() + "元");
        View linearhongBao = inflate.findViewById(R.id.linearhongBao);
        ViewGroup.LayoutParams layoutParams = linearhongBao.getLayoutParams();
        int width = screenWidth - (int) DpUtils.convertDpToPixel(40 * 2, getActivity());
        layoutParams.width = width;
        layoutParams.height = (int) (width * 1.15f);
        linearhongBao.setLayoutParams(layoutParams);
        final Dialog mDialog = new Dialog(getActivity(), R.style.mydialog);
        mDialog.setContentView(inflate);
        mDialog.show();
        inflate.findViewById(R.id.viewhongBao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        Toast.makeText(getActivity(), indexBonusget.getDes(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 红包请求
     */
    private void hongBaoQingQing() {

        showLoadingDialog();
        ApiClient.post(getActivity(), getHongBaoQQOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ShengQianCZFragment--onSuccess", s + "");
                try {
                    IndexBonusdown indexBonusdown = GsonUtils.parseJSON(s, IndexBonusdown.class);
                    if (indexBonusdown.getStatus() == 1) {
                        if (indexBonusdown.getDown() == 1) {
                            hongBaoDialog();
                            imageHongBaoDialog.setVisibility(View.VISIBLE);
                        } else {
                            imageHongBaoDialog.setVisibility(View.GONE);
                        }
                    } else if (indexBonusdown.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getContext());
                    } else {
                        Toast.makeText(getActivity(), indexBonusdown.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getHongBaoQQOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_BONUSDOWN;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }
    /**
     * 红包弹窗
     */
    @SuppressLint("WrongConstant")
    private void hongBaoDialog() {
        if (mDialog == null) {
            int screenWidth = ScreenUtils.getScreenWidth(getActivity());
            int screenHeight = ScreenUtils.getScreenHeight(getActivity());


            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_hongbao, null);
            RelativeLayout relaHongBao = (RelativeLayout) inflate.findViewById(R.id.relaHongBao);


            for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.hongbao002);
                int anInt = new Random().nextInt(40);
                float hongBaoSize = DpUtils.convertDpToPixel((anInt + 60), getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) hongBaoSize, (int) hongBaoSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams.rightMargin = new Random().nextInt(screenWidth) - (int) hongBaoSize;
                layoutParams.topMargin = -(int) hongBaoSize;
                relaHongBao.addView(imageView, layoutParams);
                PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("translationY", screenHeight + hongBaoSize);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holder02);
                int duration = new Random().nextInt(3000) + 2000;
                int delay = new Random().nextInt(2500);
                animator.setDuration(duration);
                animator.setStartDelay(delay);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.INFINITE);
                animator.start();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qiangHongBao();
                    }
                });
            }
            for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.hongbao002);
                int anInt = new Random().nextInt(40);
                float hongBaoSize = DpUtils.convertDpToPixel((anInt + 60), getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) hongBaoSize, (int) hongBaoSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                layoutParams.leftMargin = new Random().nextInt(screenWidth) - (int) hongBaoSize;
                layoutParams.topMargin = -(int) hongBaoSize;
                relaHongBao.addView(imageView, layoutParams);
                PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("translationY", screenHeight + hongBaoSize);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holder02);
                int duration = new Random().nextInt(3000) + 2000;
                int delay = new Random().nextInt(2500);
                animator.setDuration(duration);
                animator.setStartDelay(delay);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.INFINITE);
                animator.start();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qiangHongBao();
                    }
                });
            }

            for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.hongbao001);
                int anInt = new Random().nextInt(40);
                float hongBaoSize = DpUtils.convertDpToPixel((anInt + 80), getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) hongBaoSize, (int) hongBaoSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams.rightMargin = new Random().nextInt(screenWidth) - (int) hongBaoSize;
                layoutParams.topMargin = -(int) hongBaoSize;
                relaHongBao.addView(imageView, layoutParams);
                PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("translationY", screenHeight + hongBaoSize);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holder02);
                int duration = new Random().nextInt(5000) + 3000;
                int delay = new Random().nextInt(4000);
                animator.setDuration(duration);
                animator.setStartDelay(delay);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.INFINITE);
                animator.start();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qiangHongBao();
                    }
                });
            }
            for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.hongbao001);
                int anInt = new Random().nextInt(40);
                float hongBaoSize = DpUtils.convertDpToPixel((anInt + 80), getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) hongBaoSize, (int) hongBaoSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                layoutParams.leftMargin = new Random().nextInt(screenWidth) - (int) hongBaoSize;
                layoutParams.topMargin = -(int) hongBaoSize;
                relaHongBao.addView(imageView, layoutParams);
                PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("translationY", screenHeight + hongBaoSize);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holder02);
                int duration = new Random().nextInt(5000) + 3000;
                int delay = new Random().nextInt(4000);
                animator.setDuration(duration);
                animator.setStartDelay(delay);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.INFINITE);
                animator.start();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qiangHongBao();
                    }
                });
            }
            for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.hongbao003);
                int anInt = new Random().nextInt(40);
                float hongBaoSize = DpUtils.convertDpToPixel((anInt + 80), getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) hongBaoSize, (int) hongBaoSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams.rightMargin = new Random().nextInt(screenWidth) - (int) hongBaoSize;
                layoutParams.topMargin = -(int) hongBaoSize;
                relaHongBao.addView(imageView, layoutParams);
                PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("translationY", screenHeight + hongBaoSize);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holder02);
                int duration = new Random().nextInt(5000) + 3000;
                int delay = new Random().nextInt(4000);
                animator.setDuration(duration);
                animator.setStartDelay(delay);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.INFINITE);
                animator.start();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qiangHongBao();
                    }
                });
            }

            for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.hongbao003);
                int anInt = new Random().nextInt(40);
                float hongBaoSize = DpUtils.convertDpToPixel((anInt + 80), getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) hongBaoSize, (int) hongBaoSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                layoutParams.leftMargin = new Random().nextInt(screenWidth) - (int) hongBaoSize;
                layoutParams.topMargin = -(int) hongBaoSize;
                relaHongBao.addView(imageView, layoutParams);
                PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("translationY", screenHeight + hongBaoSize);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holder02);
                int duration = new Random().nextInt(5000) + 3000;
                int delay = new Random().nextInt(4000);
                animator.setDuration(duration);
                animator.setStartDelay(delay);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.INFINITE);
                animator.start();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qiangHongBao();
                    }
                });
            }


            mDialog = new Dialog(getActivity(), R.style.mydialog);
            mDialog.setContentView(inflate);
            mDialog.show();
        } else {
            mDialog.show();
        }
    }
    /**
     * 抢红包
     */
    private void qiangHongBao() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        if (!isLogin) {
            ToLoginActivity.toLoginActivity(getActivity());
            return;
        }
        showLoadingDialog();
        ApiClient.post(getActivity(), getQiangHongBAoOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ShengQianCZFragment--onSuccess", s + "");
                try {
                    IndexBonusbefore indexBonusbefore = GsonUtils.parseJSON(s, IndexBonusbefore.class);
                    if (indexBonusbefore.getStatus() == 1) {
                        if (indexBonusbefore.getGoRealName() == 1) {
                            MyDialog.showTipDialog(getActivity(), indexBonusbefore.getDes());
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("提示")
                                    .setMessage(indexBonusbefore.getDes())
                                    .setNegativeButton("否", null)
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            ((MainActivity) getActivity()).mTabHost.setCurrentTab(3);
                                        }
                                    })
                                    .show();
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), WebHongBaoActivity.class);
                            intent.putExtra(Constant.INTENT_KEY.TITLE, "领取红包");
                            intent.putExtra(Constant.INTENT_KEY.URL, indexBonusbefore.getUrl());
                            startActivityForResult(intent, Constant.REQUEST_RESULT_CODE.HONG_BAO);
                        }
                    } else if (indexBonusbefore.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        Toast.makeText(getActivity(), indexBonusbefore.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getQiangHongBAoOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_BONUSBEFORE;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }
}
