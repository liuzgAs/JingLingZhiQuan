package com.jlzquan.www.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jlzquan.www.R;
import com.jlzquan.www.base.ZjbBaseActivity;
import com.jlzquan.www.constant.Constant;
import com.jlzquan.www.util.DataCleanManager;
import com.jlzquan.www.util.ScreenUtils;
import com.jlzquan.www.util.UpgradeUtils;
import com.jlzquan.www.util.VersionUtils;

public class SheZhiActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private TextView textHuanCun;
    private TextView textBanben;

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
        textBanben.setText("V"+VersionUtils.getCurrVersionName(this));
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.viewZhangHuAQ).setOnClickListener(this);
        findViewById(R.id.viewBanBen).setOnClickListener(this);
        findViewById(R.id.viewGuanYu).setOnClickListener(this);
        findViewById(R.id.viewHuanCun).setOnClickListener(this);
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
