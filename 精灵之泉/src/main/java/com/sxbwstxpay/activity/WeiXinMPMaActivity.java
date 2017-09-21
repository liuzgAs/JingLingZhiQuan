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
    private String title;

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
        title = intent.getStringExtra(Constant.INTENT_KEY.TITLE);
        img = intent.getStringExtra(Constant.INTENT_KEY.img);
    }

    @Override
    protected void findID() {
        imageEWM = (ImageView) findViewById(R.id.imageEWM);
        viewBar = findViewById(R.id.viewBar);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText(title);
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams1 = imageEWM.getLayoutParams();
        layoutParams1.width = (int) (ScreenUtils.getScreenWidth(this)*0.6f);
        layoutParams1.height = (int) (ScreenUtils.getScreenWidth(this)*0.6f);
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
