package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.model.Test_result;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.ScreenUtils;

public class TestResultActivity extends ZjbBaseActivity implements View.OnClickListener {
    private View viewBar;
    private Test_result test_result;
    private Button buttonTest;
    private ImageView imageImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        init(TestResultActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        test_result=(Test_result) getIntent().getSerializableExtra("test_result");
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        buttonTest = (Button) findViewById(R.id.buttonTest);
        imageImg = (ImageView) findViewById(R.id.imageImg);

    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        buttonTest.setText(test_result.getBtn_txt());
        GlideApp.with(this)
                .load(test_result.getBgimg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        buttonTest.setOnClickListener(this);
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
                finish();
                break;
            default:
                break;
        }
    }
}
