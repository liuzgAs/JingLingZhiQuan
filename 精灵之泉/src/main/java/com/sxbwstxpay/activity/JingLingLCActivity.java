package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.util.ScreenUtils;

public class JingLingLCActivity extends ZjbBaseActivity implements View.OnClickListener {
    private View viewBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jing_ling_lc);
        init(JingLingLCActivity.class);
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
        ((TextView) findViewById(R.id.textViewTitle)).setText("精灵理财");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.viewJuHeZF).setOnClickListener(this);
        findViewById(R.id.viewZhiNengHK).setOnClickListener(this);
        findViewById(R.id.viewWoDeFL).setOnClickListener(this);
        findViewById(R.id.viewGuanLiYHK).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.viewJuHeZF:
                intent.setClass(this, JuHeZhiFuActivity.class);
                startActivity(intent);
                break;
            case R.id.viewZhiNengHK:
                intent.setClass(this, ZhiNengHKActivity.class);
                startActivity(intent);
                break;
            case R.id.viewWoDeFL:
                intent.setClass(this, FeiLvActivity.class);
                startActivity(intent);
                break;
            case R.id.viewGuanLiYHK:
                intent.setClass(this, GuanLiYHKActivity.class);
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
