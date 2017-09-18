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

import com.jlzquan.www.R;
import com.sxbwstxpay.base.ZjbBaseNotLeftActivity;
import com.sxbwstxpay.fragment.GuanLiYHKFragment;
import com.sxbwstxpay.util.ScreenUtils;

public class GuanLiYHKActivity extends ZjbBaseNotLeftActivity implements View.OnClickListener {
    private View viewBar;
    private TabLayout tablayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_li_yhk);
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
        ((TextView) findViewById(R.id.textViewTitle)).setText("管理银行卡");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewPager);
        tablayout.getTabAt(0).setText("储蓄卡管理");
        tablayout.getTabAt(1).setText("信用卡管理");
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
            switch (position) {
                case 0:
                    return new GuanLiYHKFragment(1);
                case 1:
                    return new GuanLiYHKFragment(2);
                default:
                    return new GuanLiYHKFragment(1);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
