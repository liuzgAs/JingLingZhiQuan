package com.sxbwstxpay.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jlzquan.www.R;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.util.ScreenUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShengQianCZFragment extends ZjbBaseFragment {
    private View mInflate;
    private View mRelaTitleStatue;
    private TabLayout tablayout;
    private ViewPager viewPager;

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

    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        mRelaTitleStatue = mInflate.findViewById(R.id.relaTitleStatue);
        tablayout = (TabLayout) mInflate.findViewById(R.id.tablayout);
        viewPager = (ViewPager) mInflate.findViewById(R.id.viewPager);
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
    }

    @Override
    protected void setListeners() {

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

}
