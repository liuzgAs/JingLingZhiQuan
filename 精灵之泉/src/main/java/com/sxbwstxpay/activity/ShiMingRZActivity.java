package com.sxbwstxpay.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.fragment.RenZhengFragment;

public class ShiMingRZActivity extends ZjbBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shi_ming_rz);
        init(ShiMingRZActivity.class);
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
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RenZhengFragment fragment1 = new RenZhengFragment();
        transaction.add(R.id.fragment, fragment1);
        transaction.commit();
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {

    }
}
