package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.util.ScreenUtils;

public class CeShiSYActivity extends ZjbBaseActivity implements View.OnClickListener {
    private View viewBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ce_shi_sy);
        init(CeShiSYActivity.class);
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
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.buttonTest).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
            case R.id.buttonTest:
                intent.setClass(this, CeShiActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
