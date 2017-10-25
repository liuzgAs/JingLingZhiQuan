package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.util.ScreenUtils;

public class ZhangHuAQActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private TextView textShouShiMM;
    private View viewGuanBiSSMM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhang_hu_aq);
        init(ZhangHuAQActivity.class);
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
        textShouShiMM = (TextView) findViewById(R.id.textShouShiMM);
        viewGuanBiSSMM = findViewById(R.id.viewGuanBiSSMM);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("账户安全");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        if (TextUtils.isEmpty(paintPassword)) {
            textShouShiMM.setText("设置手势密码");
        } else {
            textShouShiMM.setText("修改手势密码");
        }
        if (TextUtils.isEmpty(paintPassword)) {
            viewGuanBiSSMM.setVisibility(View.GONE);
        } else {
            viewGuanBiSSMM.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.viewXiuGaiMM).setOnClickListener(this);
        findViewById(R.id.viewShouShiMM).setOnClickListener(this);
        viewGuanBiSSMM.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.viewGuanBiSSMM:
                intent.putExtra(Constant.INTENT_KEY.guanBi, "guanBi");
                intent.putExtra(Constant.INTENT_KEY.Main, "Main");
                intent.setClass(this, LockActivity.class);
                startActivity(intent);
                break;
            case R.id.viewShouShiMM:
                if (TextUtils.isEmpty(paintPassword)) {
                    intent.putExtra(Constant.INTENT_KEY.shezhi, "shezhi");
                } else {
                    intent.putExtra(Constant.INTENT_KEY.isFrist, "isFrist");
                }
                intent.putExtra(Constant.INTENT_KEY.Main, "Main");
                intent.setClass(this, LockActivity.class);
                startActivity(intent);
                break;
            case R.id.viewXiuGaiMM:
                intent.setClass(this, XiuGaiMMActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
