package com.sxbwstxpay.activity;

import android.content.Intent;
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
import com.sxbwstxpay.base.ZjbBaseNotLeftActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.fragment.ZhangDanFragment;
import com.sxbwstxpay.util.ScreenUtils;

public class WoDeZDActivity extends ZjbBaseNotLeftActivity implements View.OnClickListener {

    private TabLayout tablayout;
    private ViewPager viewPager;
    private View viewBar;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_zd);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra(Constant.INTENT_KEY.id, 0);
    }

    @Override
    protected void findID() {
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewBar = findViewById(R.id.viewBar);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("我的账单");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewPager);
        tablayout.getTabAt(0).setText("收款");
        tablayout.getTabAt(1).setText("结算");
        tablayout.getTabAt(2).setText("分润");
        tablayout.getTabAt(3).setText("推广");
        switch (id) {
            case 0:

                break;
            case 1:
                viewPager.setCurrentItem(2);
                break;
            case 2:
                viewPager.setCurrentItem(3);
                break;
            case 3:
                viewPager.setCurrentItem(1);
                break;
        }
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
            return new ZhangDanFragment(position + 1);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
