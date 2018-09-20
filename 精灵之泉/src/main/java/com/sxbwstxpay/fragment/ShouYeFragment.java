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
import android.widget.ImageView;
import android.widget.TextView;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.ScreenUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShouYeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShouYeFragment extends ZjbBaseFragment {
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
        layoutParams.height = (int) DpUtils.convertDpToPixel(115, getActivity()) + ScreenUtils.getStatusBarHeight(getActivity());
        viewBar.setLayoutParams(layoutParams);
        viewBar.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);

        viewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager()));
        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < titles.size(); i++) {
            tablayout.getTabAt(i).setText(titles.get(i));
        }
        viewPager.setCurrentItem(0);
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
            if (position<3){
                return ZhuanShuCDFragment.newInstance("");
            }else {
                return new XianShiQGFragment();
            }
        }

        @Override
        public int getCount() {
            return titles.size();
        }
    }

}
