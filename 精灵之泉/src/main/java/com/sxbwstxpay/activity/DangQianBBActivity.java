package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.util.UpgradeUtils;
import com.sxbwstxpay.util.VersionUtils;

public class DangQianBBActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private TextView textBanBen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_qian_bb);
        init(DangQianBBActivity.class);
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
        textBanBen = (TextView) findViewById(R.id.textBanBen);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("当前版本");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        textBanBen.setText("精灵之泉 v" + VersionUtils.getCurrVersionName(this));
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.viewXieYi).setOnClickListener(this);
        findViewById(R.id.viewGongNeng).setOnClickListener(this);
        findViewById(R.id.viewTouSu).setOnClickListener(this);
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.imageLogo).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //检查更新
        UpgradeUtils.checkUpgrade(this, Constant.HOST + Constant.Url.INDEX_VERSION);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.imageLogo:
                LogUtil.isPrintLog=!LogUtil.isPrintLog;
                break;
            case R.id.viewXieYi:
                intent.setClass(this, WebActivity.class);
                intent.putExtra(Constant.INTENT_KEY.TITLE, "用户使用协议");
                intent.putExtra(Constant.INTENT_KEY.URL, Constant.HOST + Constant.Url.INFO_POLICY3);
                startActivity(intent);
                break;
            case R.id.viewGongNeng:
                intent.setClass(this, WebActivity.class);
                intent.putExtra(Constant.INTENT_KEY.TITLE, "功能介绍");
                intent.putExtra(Constant.INTENT_KEY.URL, Constant.HOST + Constant.Url.INFO_FEATURES);
                startActivity(intent);
                break;
            case R.id.viewTouSu:
                intent.setClass(this, WebActivity.class);
                intent.putExtra(Constant.INTENT_KEY.TITLE, "投诉");
                intent.putExtra(Constant.INTENT_KEY.URL, Constant.HOST + Constant.Url.INFO_COMPLAINT);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }
}
