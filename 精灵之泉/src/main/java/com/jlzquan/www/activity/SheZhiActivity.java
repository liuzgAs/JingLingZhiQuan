package com.jlzquan.www.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jlzquan.www.R;
import com.jlzquan.www.base.ZjbBaseActivity;
import com.jlzquan.www.constant.Constant;
import com.jlzquan.www.util.ScreenUtils;
import com.jlzquan.www.util.UpgradeUtils;

public class SheZhiActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_zhi);
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
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("设置");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.viewZhangHuAQ).setOnClickListener(this);
        findViewById(R.id.viewBanBen).setOnClickListener(this);
        findViewById(R.id.viewGuanYu).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.viewGuanYu:
                intent.setClass(this, WebActivity.class);
                intent.putExtra(Constant.INTENT_KEY.TITLE, "关于我们");
                intent.putExtra(Constant.INTENT_KEY.URL,Constant.HOST+ Constant.Url.INFO_ABOUT);
                startActivity(intent);
                break;
            case R.id.viewBanBen:
                //检查更新
                UpgradeUtils.checkUpgrade(SheZhiActivity.this, Constant.HOST + Constant.Url.INDEX_VERSION);
                break;
            case R.id.viewZhangHuAQ:
                intent.setClass(this, ZhangHuAQActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
