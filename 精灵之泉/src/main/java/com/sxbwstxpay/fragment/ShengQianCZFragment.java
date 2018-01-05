package com.sxbwstxpay.fragment;


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
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ChengShiXZActivity;
import com.sxbwstxpay.activity.GouWuCActivity;
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
import com.sxbwstxpay.model.IndexCate;
import com.sxbwstxpay.model.IndexCitylist;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import okhttp3.Response;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShengQianCZFragment extends ZjbBaseFragment implements View.OnClickListener {
    private View mInflate;
    private View mRelaTitleStatue;
    private TabLayout tablayout;
    private ViewPager viewPager;
    private TextView textCity;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.CITY_CHOOSE:
                    IndexCitylist.CityEntity.ListEntity cityBean = (IndexCitylist.CityEntity.ListEntity) intent.getSerializableExtra(Constant.INTENT_KEY.CITY);
                    textCity.setText(cityBean.getName());
                    break;
                case Constant.BROADCASTCODE.GouWuCheNum:
                    LogUtil.LogShitou("ShengQianCZFragment--onReceive", "刷新购物车");
                    gouWuCheNum(true);
                    break;
                default:
                    break;
            }
        }
    };
    private String mCity;
    private List<IndexCate.CateBean> indexCateCate;
    private Badge badge;
    private View viewVip;
    private ImageView imageShaiXuan;
    private int positionX;
    private View viewShaiXuan;
    private boolean isShaiXuan = false;
    private ListView listViewShaiXuan;
    private MyAdapter myAdapter;
    private MyAdapter myAdapter01;
    private List<IndexCate.SortBean> indexCateSort;
    private List<IndexCate.SortBean> indexCateSort1;
    private ListView listViewShaiXuan01;
    private View viewTabLayout;
    private XianShiQGFragment xianShiQGFragment;
    private int indexBannerHeight;
    private int indexBannerTabHeight;
    private int jiFenWeiZHi;
    private float tabTranYDistance;
    private Dialog mDialog;
    private ImageView imageHongBaoDialog;
    private boolean isHongBaoShow = false;

    public ShengQianCZFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_sheng_qian, container, false);
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
    protected void initSP() {
        final ACache aCache = ACache.get(getActivity(), Constant.ACACHE.LOCATION);
        String cityAcache = aCache.getAsString(Constant.ACACHE.CITY);
        if (cityAcache != null) {
            mCity = cityAcache;
        }
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        mRelaTitleStatue = mInflate.findViewById(R.id.relaTitleStatue);
        tablayout = (TabLayout) mInflate.findViewById(R.id.tablayout);
        viewPager = (ViewPager) mInflate.findViewById(R.id.viewPager);
        textCity = (TextView) mInflate.findViewById(R.id.textCity);
        viewVip = mInflate.findViewById(R.id.viewVip);
        badge = new QBadgeView(getActivity())
                .setBadgeTextColor(Color.WHITE)
                .setBadgeTextSize(8f, true)
                .setBadgeBackgroundColor(getResources().getColor(R.color.red))
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(3f, 3f, true);
        imageShaiXuan = (ImageView) mInflate.findViewById(R.id.imageShaiXuan);
        viewShaiXuan = mInflate.findViewById(R.id.viewShaiXuan);
        listViewShaiXuan = (ListView) mInflate.findViewById(R.id.listViewShaiXuan);
        listViewShaiXuan01 = (ListView) mInflate.findViewById(R.id.listViewShaiXuan01);
        viewTabLayout = mInflate.findViewById(R.id.viewTabLayout);
        imageHongBaoDialog = (ImageView) mInflate.findViewById(R.id.imageHongBaoDialog);
    }

    @Override
    protected void initViews() {
        int screenWidth = ScreenUtils.getScreenWidth(getActivity());
        indexBannerHeight = (int) ((float) screenWidth * Constant.VALUE.IndexBannerHeight / 1080f) + (int) DpUtils.convertDpToPixel(5, getActivity());
        indexBannerTabHeight = (int) ((float) screenWidth * Constant.VALUE.IndexBannerHeight / 1080f) + (int) DpUtils.convertDpToPixel(45, getActivity());
        ViewGroup.LayoutParams layoutParams = mRelaTitleStatue.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
        mRelaTitleStatue.setLayoutParams(layoutParams);
        mRelaTitleStatue.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);
        textCity.setText(mCity);
        imageShaiXuan.setVisibility(View.GONE);
        viewShaiXuan.setVisibility(View.GONE);
        xianShiQGFragment = new XianShiQGFragment();
    }

    @Override
    protected void setListeners() {
        textCity.setOnClickListener(this);
        viewVip.setOnClickListener(this);
        mInflate.findViewById(R.id.textSouSuo).setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                positionX = position;
                switch (indexCateCate.get(position).getJump()) {
                    case "time":
                        viewTabLayout.setTranslationY(tabTranYDistance);
                        imageShaiXuan.setVisibility(View.GONE);
                        break;
                    /**
                     * 积分商城
                     */
                    case "score":
                        viewTabLayout.setTranslationY(0);
                        imageShaiXuan.setVisibility(View.GONE);
                        listViewShaiXuan.setVisibility(View.GONE);
                        listViewShaiXuan01.setVisibility(View.GONE);
                        break;
                    case "product":
                        viewTabLayout.setTranslationY(0);
                        imageShaiXuan.setVisibility(View.VISIBLE);
                        listViewShaiXuan.setVisibility(View.GONE);
                        listViewShaiXuan01.setVisibility(View.VISIBLE);
                        break;
                    case "store":
                        viewTabLayout.setTranslationY(0);
                        imageShaiXuan.setVisibility(View.VISIBLE);
                        listViewShaiXuan.setVisibility(View.GONE);
                        listViewShaiXuan01.setVisibility(View.VISIBLE);
                        break;
                    default:
                        viewTabLayout.setTranslationY(0);
                        imageShaiXuan.setVisibility(View.VISIBLE);
                        listViewShaiXuan.setVisibility(View.VISIBLE);
                        listViewShaiXuan01.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        imageShaiXuan.setOnClickListener(this);
        listViewShaiXuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isShaiXuan = false;
                viewShaiXuan.setVisibility(View.GONE);
                for (int i = 0; i < indexCateSort.size(); i++) {
                    indexCateSort.get(i).setSelect(false);
                }
                indexCateSort.get(position).setSelect(true);
                myAdapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.setAction(Constant.BROADCASTCODE.ShaiXuan);
                intent.putExtra(Constant.INTENT_KEY.value, indexCateSort.get(position).getV());
                getActivity().sendBroadcast(intent);
            }
        });
        listViewShaiXuan01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isShaiXuan = false;
                viewShaiXuan.setVisibility(View.GONE);
                for (int i = 0; i < indexCateSort1.size(); i++) {
                    indexCateSort1.get(i).setSelect(false);
                }
                indexCateSort1.get(position).setSelect(true);
                myAdapter01.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.setAction(Constant.BROADCASTCODE.ShaiXuan01);
                intent.putExtra(Constant.INTENT_KEY.value, indexCateSort1.get(position).getV());
                getActivity().sendBroadcast(intent);
            }
        });
        viewShaiXuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShaiXuan = false;
                viewShaiXuan.setVisibility(View.GONE);
            }
        });
        xianShiQGFragment.setOnScrollListener(new XianShiQGFragment.OnScrollListener() {
            @Override
            public void scroll(int distance) {
                if (distance >= indexBannerHeight && distance <= indexBannerTabHeight) {
                    tabTranYDistance = indexBannerTabHeight - distance - DpUtils.convertDpToPixel(40f, getContext());
                } else if (distance >= 0 && distance < indexBannerHeight) {
                    tabTranYDistance = 0;
                } else {
                    tabTranYDistance = DpUtils.convertDpToPixel(-40f, getContext());
                }
                viewTabLayout.setTranslationY(tabTranYDistance);
            }
        });
        imageHongBaoDialog.setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_CATE;
        HashMap<String, String> params = new HashMap<>();
        try {
            params.put("uid", userInfo.getUid());
        } catch (Exception e) {
        }
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        gouWuCheNum(false);
    }

    private void gouWuCheNum(final boolean isRefresh) {
        ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ShengQianCZFragment--限时购分类", s + "");
                try {
                    IndexCate indexCate = GsonUtils.parseJSON(s, IndexCate.class);
                    if (indexCate.getStatus() == 1) {
                        if (isRefresh) {
                            badge.setBadgeNumber(indexCate.getVipNum()).bindTarget(viewVip);
                            return;
                        }
                        indexCateSort = indexCate.getSort();
                        for (int i = 0; i < indexCateSort.size(); i++) {
                            indexCateSort.get(i).setSelect(false);
                        }
                        indexCateSort.get(0).setSelect(true);
                        myAdapter = new MyAdapter(indexCateSort);
                        listViewShaiXuan.setAdapter(myAdapter);

                        indexCateSort1 = indexCate.getSort1();
                        for (int i = 0; i < indexCateSort1.size(); i++) {
                            indexCateSort1.get(i).setSelect(false);
                        }
                        indexCateSort1.get(0).setSelect(true);
                        myAdapter01 = new MyAdapter(indexCateSort1);
                        listViewShaiXuan01.setAdapter(myAdapter01);

                        badge.setBadgeNumber(indexCate.getVipNum()).bindTarget(viewVip);
                        indexCateCate = indexCate.getCate();
                        viewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager()));
                        tablayout.setupWithViewPager(viewPager);
                        tablayout.removeAllTabs();
                        LogUtil.LogShitou("ShengQianCZFragment--indexCate.getVipNum()", "" + indexCate.getVipNum());
                        for (int i = 0; i < indexCateCate.size(); i++) {
                            if (TextUtils.equals(indexCateCate.get(i).getJump(), "score")) {
                                jiFenWeiZHi = i;
                            }
                            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_tablayout, null);
                            TextView textTitle = (TextView) view.findViewById(R.id.textTitle);
                            textTitle.setText(indexCateCate.get(i).getName());
                            if (i == 0) {
                                tablayout.addTab(tablayout.newTab().setCustomView(view), true);
                            } else {
                                tablayout.addTab(tablayout.newTab().setCustomView(view), false);
                            }
                        }
                    } else if (indexCate.getStatus() == 2) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        Toast.makeText(getActivity(), indexCate.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    try {
                        Toast.makeText(getActivity(), "数据出错", Toast.LENGTH_SHORT).show();
                    } catch (Exception e1) {
                    }
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (indexCateCate.get(position).getJump()) {
                case "time":
                    return xianShiQGFragment;
                case "product":
                    return new BenDiYDFragment(0);
                case "store":
                    return new BenDiYDFragment(1);
                default:
                    IndexCate.CateBean cateBean = indexCateCate.get(position);
                    return new XuanPinSJFragment(position, cateBean);
            }
        }

        @Override
        public int getCount() {
            return indexCateCate.size();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.imageHongBaoDialog:
                hongBaoQingQing();
                break;
            case R.id.imageShaiXuan:
                isShaiXuan = !isShaiXuan;
                if (isShaiXuan) {
                    viewShaiXuan.setVisibility(View.VISIBLE);
                } else {
                    viewShaiXuan.setVisibility(View.GONE);
                }
                break;
            case R.id.textSouSuo:
                intent.putExtra(Constant.INTENT_KEY.type, 0);
                intent.setClass(getActivity(), SouSuoActivity.class);
                startActivity(intent);
                break;
            case R.id.viewVip:
                intent.setClass(getActivity(), GouWuCActivity.class);
                startActivity(intent);
                break;
            case R.id.textCity:
                intent.setClass(getActivity(), ChengShiXZActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.CITY_CHOOSE);
        filter.addAction(Constant.BROADCASTCODE.GouWuCheNum);
        getActivity().registerReceiver(reciver, filter);
        if (((MainActivity) getActivity()).isJiFen) {
            tablayout.setScrollPosition(jiFenWeiZHi, 0, false);
            tablayout.getTabAt(jiFenWeiZHi).select();
            ((MainActivity) getActivity()).isJiFen = false;
        }
        if (!isHongBaoShow) {
            hongBaoQingQing();
            isHongBaoShow = true;
        }
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
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT );
                layoutParams.rightMargin = new Random().nextInt(screenWidth)-(int) hongBaoSize;
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
            }  for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.hongbao002);
                int anInt = new Random().nextInt(40);
                float hongBaoSize = DpUtils.convertDpToPixel((anInt + 60), getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) hongBaoSize, (int) hongBaoSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT );
                layoutParams.leftMargin = new Random().nextInt(screenWidth)-(int) hongBaoSize;
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
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT );
                layoutParams.rightMargin =  new Random().nextInt(screenWidth)-(int) hongBaoSize;
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
            } for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.hongbao001);
                int anInt = new Random().nextInt(40);
                float hongBaoSize = DpUtils.convertDpToPixel((anInt + 80), getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) hongBaoSize, (int) hongBaoSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT );
                layoutParams.leftMargin=  new Random().nextInt(screenWidth)-(int) hongBaoSize;
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
                layoutParams.rightMargin =  new Random().nextInt(screenWidth)-(int) hongBaoSize;
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
                layoutParams.leftMargin =  new Random().nextInt(screenWidth)-(int) hongBaoSize;
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

//            float hongbao62 = DpUtils.convertDpToPixel(62f, getActivity());
//            float hongbao85 = DpUtils.convertDpToPixel(85f, getActivity());
//            float widthF09 = 0.3f;
//            float widthF08 = 1f;
//
//            ImageView imageHongBao09 = new ImageView(getActivity());
//            imageHongBao09.setImageResource(R.mipmap.hongbao02);
//            RelativeLayout.LayoutParams layoutParams09 = new RelativeLayout.LayoutParams((int) hongbao62, (int) hongbao62);
//            layoutParams09.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
//            layoutParams09.rightMargin = -(int) hongbao62;
//            layoutParams09.topMargin = (int) (screenHeight * (1 - widthF09));
//            relaHongBao.addView(imageHongBao09, layoutParams09);
//            PropertyValuesHolder holder0901 = PropertyValuesHolder.ofFloat("translationX", -screenHeight * widthF09);
//            PropertyValuesHolder holder0902 = PropertyValuesHolder.ofFloat("translationY", (screenHeight * widthF09) * 1.64f);
//            ObjectAnimator animator09 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao09, holder0901, holder0902);
//            animator09.setDuration(6000);
//            animator09.setRepeatCount(ValueAnimator.INFINITE);
//            animator09.setRepeatMode(ValueAnimator.INFINITE);
//            animator09.start();
//            imageHongBao09.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao0801 = new ImageView(getActivity());
//            imageHongBao0801.setImageResource(R.mipmap.hongbao04);
//            RelativeLayout.LayoutParams layoutParams08 = new RelativeLayout.LayoutParams((int) hongbao85, (int) hongbao85);
//            layoutParams08.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
//            layoutParams08.rightMargin = -(int) hongbao85 * 3;
//            float hongbao07 = DpUtils.convertDpToPixel(42f, getActivity());
//            float widthF07 = 0.3f;
//            layoutParams08.topMargin = -(int) hongbao85 * 3;
//            relaHongBao.addView(imageHongBao0801, layoutParams08);
//            PropertyValuesHolder holder0801 = PropertyValuesHolder.ofFloat("translationX", -screenWidth * widthF08 - hongbao85 * 3);
//            PropertyValuesHolder holder0802 = PropertyValuesHolder.ofFloat("translationY", (screenWidth * widthF08 + hongbao85 * 3) * 1.64f);
//            ObjectAnimator animator0801 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao0801, holder0801, holder0802);
//            animator0801.setDuration(6500);
//            animator0801.setStartDelay(1900);
//            animator0801.setRepeatCount(ValueAnimator.INFINITE);
//            animator0801.setRepeatMode(ValueAnimator.INFINITE);
//            animator0801.start();
//            imageHongBao0801.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao08 = new ImageView(getActivity());
//            imageHongBao08.setImageResource(R.mipmap.hongbao04);
//            relaHongBao.addView(imageHongBao08, layoutParams08);
//            ObjectAnimator animator08 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao08, holder0801, holder0802);
//            animator08.setDuration(6000);
//            animator08.setRepeatCount(ValueAnimator.INFINITE);
//            animator08.setRepeatMode(ValueAnimator.INFINITE);
//            animator08.start();
//            imageHongBao08.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao07 = new ImageView(getActivity());
//            imageHongBao07.setImageResource(R.mipmap.hongbao01);
//            RelativeLayout.LayoutParams layoutParams07 = new RelativeLayout.LayoutParams((int) hongbao07, (int) hongbao07);
//            layoutParams07.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
//            layoutParams07.rightMargin = -(int) hongbao07;
//            layoutParams07.topMargin = (int) (screenHeight * (1 - widthF07));
//            relaHongBao.addView(imageHongBao07, layoutParams07);
//            PropertyValuesHolder holder0701 = PropertyValuesHolder.ofFloat("translationX", -screenHeight * widthF07);
//            PropertyValuesHolder holder0702 = PropertyValuesHolder.ofFloat("translationY", (screenHeight * widthF07) * 1.64f);
//            ObjectAnimator animator07 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao07, holder0701, holder0702);
//            animator07.setDuration(6500);
//            animator07.setRepeatCount(ValueAnimator.INFINITE);
//            animator07.setRepeatMode(ValueAnimator.INFINITE);
//            animator07.start();
//            imageHongBao07.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao0601 = new ImageView(getActivity());
//            float hongbao06 = DpUtils.convertDpToPixel(42f, getActivity());
//            imageHongBao0601.setImageResource(R.mipmap.hongbao03);
//            RelativeLayout.LayoutParams layoutParams06 = new RelativeLayout.LayoutParams((int) hongbao06, (int) hongbao06);
//            float widthF06 = 1f;
//            layoutParams06.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
//            layoutParams06.rightMargin = -(int) hongbao06 * 3;
//            layoutParams06.topMargin = -(int) hongbao06;
//            relaHongBao.addView(imageHongBao0601, layoutParams06);
//            PropertyValuesHolder holder0601 = PropertyValuesHolder.ofFloat("translationX", -screenWidth * widthF06 - hongbao06 * 3);
//            PropertyValuesHolder holder0602 = PropertyValuesHolder.ofFloat("translationY", (screenWidth * widthF06 + hongbao06 * 3) * 1.64f);
//            ObjectAnimator animator06 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao0601, holder0601, holder0602);
//            animator06.setDuration(3000);
//            animator06.setStartDelay(1150);
//            animator06.setRepeatCount(ValueAnimator.INFINITE);
//            animator06.setRepeatMode(ValueAnimator.INFINITE);
//            animator06.start();
//            imageHongBao0601.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao06 = new ImageView(getActivity());
//            imageHongBao06.setImageResource(R.mipmap.hongbao03);
//            relaHongBao.addView(imageHongBao06, layoutParams06);
//            ObjectAnimator animator0601 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao06, holder0601, holder0602);
//            animator0601.setDuration(3500);
//            animator0601.setRepeatCount(ValueAnimator.INFINITE);
//            animator0601.setRepeatMode(ValueAnimator.INFINITE);
//            animator0601.start();
//            imageHongBao06.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao0502 = new ImageView(getActivity());
//            float hongbao05 = DpUtils.convertDpToPixel(62f, getActivity());
//            imageHongBao0502.setImageResource(R.mipmap.hongbao03);
//            RelativeLayout.LayoutParams layoutParams05 = new RelativeLayout.LayoutParams((int) hongbao05, (int) hongbao05);
//            float widthF05 = 0.8f;
//            layoutParams05.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
//            layoutParams05.leftMargin = (int) (screenWidth * widthF05);
//            layoutParams05.topMargin = -(int) hongbao05;
//            relaHongBao.addView(imageHongBao0502, layoutParams05);
//            PropertyValuesHolder holder0501 = PropertyValuesHolder.ofFloat("translationX", -screenWidth * widthF05 - hongbao05);
//            PropertyValuesHolder holder0502 = PropertyValuesHolder.ofFloat("translationY", (screenWidth * widthF05 + hongbao05) * 1.64f);
//            ObjectAnimator animator0501 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao0502, holder0501, holder0502);
//            animator0501.setDuration(6800);
//            animator06.setStartDelay(3200);
//            animator0501.setRepeatCount(ValueAnimator.INFINITE);
//            animator0501.setRepeatMode(ValueAnimator.INFINITE);
//            animator0501.start();
//            imageHongBao0502.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao0503 = new ImageView(getActivity());
//            imageHongBao0503.setImageResource(R.mipmap.hongbao03);
//            relaHongBao.addView(imageHongBao0503, layoutParams05);
//            ObjectAnimator animator05 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao0503, holder0501, holder0502);
//            animator05.setDuration(3500);
//            animator05.setRepeatCount(ValueAnimator.INFINITE);
//            animator05.setRepeatMode(ValueAnimator.INFINITE);
//            animator05.start();
//            imageHongBao0503.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao0403 = new ImageView(getActivity());
//            float hongbao04 = DpUtils.convertDpToPixel(85f, getActivity());
//            imageHongBao0403.setImageResource(R.mipmap.hongbao04);
//            RelativeLayout.LayoutParams layoutParams04 = new RelativeLayout.LayoutParams((int) hongbao04, (int) hongbao04);
//            float widthF04 = 1f;
//            layoutParams04.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
//            layoutParams04.rightMargin = -(int) hongbao04;
//            layoutParams04.topMargin = -(int) hongbao04;
//            relaHongBao.addView(imageHongBao0403, layoutParams04);
//            PropertyValuesHolder holder0401 = PropertyValuesHolder.ofFloat("translationX", -screenWidth * widthF04 - hongbao04);
//            PropertyValuesHolder holder0402 = PropertyValuesHolder.ofFloat("translationY", (screenWidth * widthF04 + hongbao04) * 1.64f);
//            ObjectAnimator animator0401 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao0403, holder0401, holder0402);
//            animator0401.setDuration(4600);
//            animator0401.setStartDelay(3100);
//            animator0401.setRepeatCount(ValueAnimator.INFINITE);
//            animator0401.setRepeatMode(ValueAnimator.INFINITE);
//            animator0401.start();
//            imageHongBao0403.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao04 = new ImageView(getActivity());
//            imageHongBao04.setImageResource(R.mipmap.hongbao04);
//            relaHongBao.addView(imageHongBao04, layoutParams04);
//            ObjectAnimator animator04 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao04, holder0401, holder0402);
//            animator04.setDuration(6000);
//            animator04.setRepeatCount(ValueAnimator.INFINITE);
//            animator04.setRepeatMode(ValueAnimator.INFINITE);
//            animator04.start();
//            imageHongBao04.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//
//            ImageView imageHongBao0301 = new ImageView(getActivity());
//            float hongbao03 = DpUtils.convertDpToPixel(62f, getActivity());
//            imageHongBao0301.setImageResource(R.mipmap.hongbao03);
//            RelativeLayout.LayoutParams layoutParams03 = new RelativeLayout.LayoutParams((int) hongbao03, (int) hongbao03);
//            float widthF03 = 1f;
//            layoutParams03.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
//            layoutParams03.rightMargin = -(int) hongbao03;
//            layoutParams03.topMargin = -(int) hongbao03;
//            relaHongBao.addView(imageHongBao0301, layoutParams03);
//            PropertyValuesHolder holder0301 = PropertyValuesHolder.ofFloat("translationX", -screenWidth * widthF03 - hongbao03);
//            PropertyValuesHolder holder0302 = PropertyValuesHolder.ofFloat("translationY", (screenWidth * widthF03 + hongbao03) * 1.64f);
//            ObjectAnimator animator0301 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao0301, holder0301, holder0302);
//            animator0301.setDuration(3000);
//            animator0301.setStartDelay(1200);
//            animator0301.setRepeatCount(ValueAnimator.INFINITE);
//            animator0301.setRepeatMode(ValueAnimator.INFINITE);
//            animator0301.start();
//            imageHongBao0301.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao03 = new ImageView(getActivity());
//            imageHongBao03.setImageResource(R.mipmap.hongbao03);
//            relaHongBao.addView(imageHongBao03, layoutParams03);
//            ObjectAnimator animator03 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao03, holder0301, holder0302);
//            animator03.setDuration(3500);
//            animator03.setRepeatCount(ValueAnimator.INFINITE);
//            animator03.setRepeatMode(ValueAnimator.INFINITE);
//            animator03.start();
//            imageHongBao03.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao02 = new ImageView(getActivity());
//            imageHongBao02.setImageResource(R.mipmap.hongbao02);
//            float hongbao02 = DpUtils.convertDpToPixel(60f, getActivity());
//            RelativeLayout.LayoutParams layoutParams02 = new RelativeLayout.LayoutParams((int) hongbao02, (int) hongbao02);
//            float widthF02 = 0.40f;
//            layoutParams02.leftMargin = (int) (screenWidth * widthF02);
//            layoutParams02.topMargin = -(int) hongbao02;
//            relaHongBao.addView(imageHongBao02, layoutParams02);
//            PropertyValuesHolder holder0201 = PropertyValuesHolder.ofFloat("translationX", -screenWidth * widthF02 - hongbao02);
//            PropertyValuesHolder holder0202 = PropertyValuesHolder.ofFloat("translationY", (screenWidth * widthF02 + hongbao02) * 1.64f);
//            ObjectAnimator animator02 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao02, holder0201, holder0202);
//            animator02.setDuration(3500);
//            animator02.setRepeatCount(ValueAnimator.INFINITE);
//            animator02.setRepeatMode(ValueAnimator.INFINITE);
//            animator02.start();
//            imageHongBao02.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao01 = new ImageView(getActivity());
//            imageHongBao01.setImageResource(R.mipmap.hongbao01);
//            float hongbao01 = DpUtils.convertDpToPixel(40f, getActivity());
//            RelativeLayout.LayoutParams layoutParams01 = new RelativeLayout.LayoutParams((int) hongbao01, (int) hongbao01);
//            float widthF01 = 0.40f;
//            layoutParams01.leftMargin = (int) (screenWidth * widthF01);
//            layoutParams01.topMargin = -(int) hongbao01;
//            relaHongBao.addView(imageHongBao01, layoutParams01);
//            PropertyValuesHolder holder0101 = PropertyValuesHolder.ofFloat("translationX", -screenWidth * widthF01 - hongbao01);
//            PropertyValuesHolder holder0102 = PropertyValuesHolder.ofFloat("translationY", (screenWidth * widthF01 + hongbao01) * 1.64f);
//            ObjectAnimator animator01 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao01, holder0101, holder0102);
//            animator01.setDuration(3000);
//            animator01.setRepeatCount(ValueAnimator.INFINITE);
//            animator01.setRepeatMode(ValueAnimator.INFINITE);
//            animator01.setInterpolator(new DecelerateInterpolator());
//            animator01.start();
//            imageHongBao01.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });

            mDialog = new Dialog(getActivity(), R.style.mydialog);
            mDialog.setContentView(inflate);
            mDialog.show();
        } else {
            mDialog.show();
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Constant.REQUEST_RESULT_CODE.HONG_BAO&&resultCode==Constant.REQUEST_RESULT_CODE.HONG_BAO){
            IndexBonusget indexBonusget= (IndexBonusget) data.getSerializableExtra(Constant.INTENT_KEY.value);
            showHongBaoDialog(indexBonusget);
        }
    }

    /**
     * 抢到红包
     * @param indexBonusget
     */
    private void showHongBaoDialog(IndexBonusget indexBonusget) {
        int screenWidth = ScreenUtils.getScreenWidth(getActivity());
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_hongbaolq, null);
        TextView texthongBao = (TextView) inflate.findViewById(R.id.texthongBao);

        texthongBao.setText(indexBonusget.getMoney()+"元");
        View linearhongBao = inflate.findViewById(R.id.linearhongBao);
        ViewGroup.LayoutParams layoutParams = linearhongBao.getLayoutParams();
        int width = screenWidth-(int) DpUtils.convertDpToPixel(40*2,getActivity());
        layoutParams.width = width;
        layoutParams.height = (int)(width*1.15f);
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
                            startActivityForResult(intent,Constant.REQUEST_RESULT_CODE.HONG_BAO);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(reciver);
    }

    class MyAdapter extends BaseAdapter {
        List<IndexCate.SortBean> indexCateSortX;

        public MyAdapter(List<IndexCate.SortBean> indexCateSort) {
            this.indexCateSortX = indexCateSort;
        }

        class ViewHolder {
            public TextView textName;
        }

        @Override
        public int getCount() {
            return (indexCateSortX.size());
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_shai_xuan, null);
                holder.textName = (TextView) convertView.findViewById(R.id.textName);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            int light_black = getActivity().getResources().getColor(R.color.light_black);
            int basic_color = getActivity().getResources().getColor(R.color.basic_color);
            if (indexCateSortX.get(position).isSelect()) {
                holder.textName.setTextColor(basic_color);
            } else {
                holder.textName.setTextColor(light_black);
            }
            holder.textName.setText(indexCateSortX.get(position).getName());
            return convertView;
        }
    }
}
