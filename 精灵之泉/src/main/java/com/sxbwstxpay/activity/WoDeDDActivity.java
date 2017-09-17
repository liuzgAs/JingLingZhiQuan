package com.sxbwstxpay.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.fragment.DingDanFragment;
import com.sxbwstxpay.util.ScreenUtils;

public class WoDeDDActivity extends ZjbBaseActivity implements View.OnClickListener{
    private TabLayout tablayout;
    private ViewPager viewPager;
    private View viewBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shang_cheng_dd);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
    }

    @Override
    protected void findID() {
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewBar = findViewById(R.id.viewBar);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("我的订单");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.getTabAt(0).setText("全部订单");
        tablayout.getTabAt(1).setText("待付款");
        tablayout.getTabAt(2).setText("待发货");
        tablayout.getTabAt(3).setText("待收货");
        tablayout.getTabAt(4).setText("退换货");
        tablayout.getTabAt(5).setText("已完成");
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
        }
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new DingDanFragment();
        }

        @Override
        public int getCount() {
            return 6;
        }
    }
}
