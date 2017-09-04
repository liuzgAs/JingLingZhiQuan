package hudongchuangxiang.com.jinglingzhiquan.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseNotLeftActivity;
import hudongchuangxiang.com.jinglingzhiquan.fragment.ZhiJieTJYHFragment;
import hudongchuangxiang.com.jinglingzhiquan.util.ScreenUtils;

public class WoDeSHActivity extends ZjbBaseNotLeftActivity implements View.OnClickListener {

    private View viewBar;
    private TabLayout tablayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_sh);
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
        ((TextView)findViewById(R.id.textViewTitle)).setText("我的商户");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewPager);
        tablayout.getTabAt(0).setText("直接推荐用户");
        tablayout.getTabAt(1).setText("间接推荐用户");
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
        switch (v.getId()){
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
            return new ZhiJieTJYHFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
