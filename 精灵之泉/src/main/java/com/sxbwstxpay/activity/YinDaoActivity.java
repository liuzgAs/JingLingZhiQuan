package com.sxbwstxpay.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.rd.PageIndicatorView;
import com.rd.animation.AnimationType;

import com.sxbwstxpay.R;
import com.sxbwstxpay.fragment.GuideFragment;

public class YinDaoActivity extends FragmentActivity {

    private ViewPager mMyPager;
    private PageIndicatorView mPageIndicatorView;
    private int[] imgs = new int[]{
            R.mipmap.welcome01,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yin_dao);
        mMyPager = (ViewPager) findViewById(R.id.myPager);
        mPageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        int blue = getResources().getColor(R.color.basic_color);
        mPageIndicatorView.setSelectedColor(blue);
        mPageIndicatorView.setUnselectedColor(Color.WHITE);
        mPageIndicatorView.setAnimationType(AnimationType.WORM);
        mPageIndicatorView.setCount(imgs.length);
        mMyPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    /**
     * des： 引导adapter
     * author： ZhangJieBo
     * date： 2017/7/6 0006 下午 2:26
     */
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new GuideFragment(imgs[position], imgs.length - 1, position);
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }
}
