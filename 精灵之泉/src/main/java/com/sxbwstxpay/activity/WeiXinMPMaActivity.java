package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlzquan.www.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.util.ScreenUtils;

public class WeiXinMPMaActivity extends ZjbBaseActivity {

    private ImageView imageEWM;
    private View viewBar;
    private String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er_wei_ma);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        img = intent.getStringExtra(Constant.INTENT_KEY.img);
    }

    @Override
    protected void findID() {
        imageEWM = (ImageView) findViewById(R.id.imageEWM);
        viewBar = findViewById(R.id.viewBar);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("我的微信名片");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        Glide.with(WeiXinMPMaActivity.this)
                .load(img)
                .placeholder(R.mipmap.ic_empty)
                .into(imageEWM);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {

    }
}
