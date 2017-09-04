package hudongchuangxiang.com.jinglingzhiquan.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.adapter.ShouYiFragmentAdapter;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseNotLeftActivity;
import hudongchuangxiang.com.jinglingzhiquan.util.ScreenUtils;

public class ShouYiMXActivity extends ZjbBaseNotLeftActivity implements View.OnClickListener {

    private View viewBar;
    private ViewPager viewPager;
    private int[] viewId = new int[]{
            R.id.viewShouYi1,
            R.id.viewShouYi2,
            R.id.viewShouYi3,
            R.id.viewShouYi4
    };
    private View[] viewTab = new View[4];
    private View viewTabBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_yi_mx);
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
        viewBar = findViewById(R.id.viewBar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        for (int i = 0; i < viewTab.length; i++) {
            viewTab[i] = findViewById(viewId[i]);
        }
        viewTabBg = findViewById(R.id.viewTabBg);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("收益明细");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        viewPager.setAdapter(new ShouYiFragmentAdapter(getSupportFragmentManager()));
    }


    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        for (int i = 0; i < viewTab.length; i++) {
            viewTab[i].setOnClickListener(this);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewShouYi1:
                viewPager.setCurrentItem(0);
                viewTabBg.setBackgroundResource(R.mipmap.mingxitab1);
                break;
            case R.id.viewShouYi2:
                viewPager.setCurrentItem(1);
                viewTabBg.setBackgroundResource(R.mipmap.mingxitab2);
                break;
            case R.id.viewShouYi3:
                viewPager.setCurrentItem(2);
                viewTabBg.setBackgroundResource(R.mipmap.mingxitab3);
                break;
            case R.id.viewShouYi4:
                viewPager.setCurrentItem(3);
                viewTabBg.setBackgroundResource(R.mipmap.mingxitab4);
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
