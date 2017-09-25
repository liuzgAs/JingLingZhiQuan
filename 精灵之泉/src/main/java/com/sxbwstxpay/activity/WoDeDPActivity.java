package com.sxbwstxpay.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseActivity;

public class WoDeDPActivity extends ZjbBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_dp);
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

    }

    @Override
    protected void initViews() {
        ((TextView)findViewById(R.id.textViewTitle)).setText("我的店铺");
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {

    }
}
