package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.application.MyApplication;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.util.DataCleanManager;
import com.sxbwstxpay.util.PermissionPageUtils;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.util.VersionUtils;

public class SheZhiActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private TextView textHuanCun;
    private TextView textBanben;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_zhi);
        init(SheZhiActivity.class);
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
        textHuanCun = (TextView) findViewById(R.id.textHuanCun);
        textBanben = (TextView) findViewById(R.id.textBanben);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("设置");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        textHuanCun.setText(getSize());
        textBanben.setText("v"+VersionUtils.getCurrVersionName(this));
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.viewZhangHuAQ).setOnClickListener(this);
        findViewById(R.id.viewBanBen).setOnClickListener(this);
        findViewById(R.id.viewGuanYu).setOnClickListener(this);
        findViewById(R.id.viewHuanCun).setOnClickListener(this);
        findViewById(R.id.viewBangZhuZX).setOnClickListener(this);
        findViewById(R.id.viewDingWei).setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.viewHuanCun:
                DataCleanManager.clearAllCache(this);
                textHuanCun.setText(getSize());
                Toast.makeText(this,"缓存清除完毕", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewGuanYu:
                intent.setClass(this, WebActivity.class);
                intent.putExtra(Constant.INTENT_KEY.TITLE, "关于我们");
                intent.putExtra(Constant.INTENT_KEY.URL,Constant.HOST+ Constant.Url.INFO_ABOUT);
                startActivity(intent);
                break;
            case R.id.viewBanBen:
                intent.setClass(this,DangQianBBActivity.class);
                startActivity(intent);
                break;
            case R.id.viewZhangHuAQ:
                intent.setClass(this, ZhangHuAQActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
            case R.id.viewBangZhuZX:
                intent.setClass(this, BangZhuZXActivity.class);
                startActivity(intent);
                break;
            case R.id.viewDingWei:
                new PermissionPageUtils(MyApplication.getContext()).jumpPermissionPage();
                break;
        }
    }

    /**
     * -------------获取缓存大小-----------------
     */
    private String getSize() {
        String totalCacheSize = null;
        try {
            totalCacheSize = DataCleanManager.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCacheSize;
    }
}
