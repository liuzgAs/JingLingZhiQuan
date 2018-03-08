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
import com.sxbwstxpay.fragment.GuanLiYHKXFragment;
import com.sxbwstxpay.fragment.XinYongKaZDFragment;
import com.sxbwstxpay.util.ScreenUtils;

public class ZhiNengHKActivity extends ZjbBaseActivity implements View.OnClickListener {
    private View viewBar;
    private TabLayout tablayout;
    private ViewPager viewPager;
    private TextView textShanChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_neng_hk);
        init(ZhiNengHKActivity.class);
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
        textShanChu = (TextView) findViewById(R.id.textShanChu);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("智能还款");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewPager);
        tablayout.getTabAt(0).setText("信用卡管理");
        tablayout.getTabAt(1).setText("信用卡账单");
        textShanChu.setVisibility(View.VISIBLE);
        textShanChu.postDelayed(new Runnable() {
            @Override
            public void run() {
                textShanChu.setVisibility(View.GONE);
            }
        }, 2000);
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
            default:
                break;
        }
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new GuanLiYHKXFragment();
                case 1:
                    return new XinYongKaZDFragment();
                default:
                    return new GuanLiYHKXFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
