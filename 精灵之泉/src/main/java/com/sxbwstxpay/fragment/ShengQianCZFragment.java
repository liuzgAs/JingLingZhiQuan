package com.sxbwstxpay.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ChengShiXZActivity;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.IndexCitylist;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.ScreenUtils;

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
            }
        }
    };
    private String mCity;

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
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = mRelaTitleStatue.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
        mRelaTitleStatue.setLayoutParams(layoutParams);
        mRelaTitleStatue.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);
        viewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager()));
        tablayout.setupWithViewPager(viewPager);
        tablayout.getTabAt(0).setText("限时抢购");
        tablayout.getTabAt(1).setText("水果生鲜");
        tablayout.getTabAt(2).setText("零食饮料");
        tablayout.getTabAt(3).setText("日用家居");
        tablayout.getTabAt(4).setText("母婴儿童");
        tablayout.getTabAt(5).setText("本地生活");
        textCity.setText(mCity);
    }

    @Override
    protected void setListeners() {
        textCity.setOnClickListener(this);
    }


    @Override
    protected void initData() {
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new XianShiQGFragment();
            } else {
                return new XuanPinSJFragment();
            }
        }

        @Override
        public int getCount() {
            return 6;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.textCity:
                intent.setClass(getActivity(), ChengShiXZActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.CITY_CHOOSE);
        getActivity().registerReceiver(reciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(reciver);
    }

}
