package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseNotLeftActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.ExtraMap;

public class ReciverActivity extends ZjbBaseNotLeftActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciver);
        init(ReciverActivity.class);
        TextView textTitle = (TextView) findViewById(R.id.textTitle);
        TextView textDes = (TextView) findViewById(R.id.textDes);
        TextView textCancle = (TextView) findViewById(R.id.textCancle);
        TextView textSure = (TextView) findViewById(R.id.textSure);

        final Intent intent = getIntent();
        final ExtraMap extraMapBean = (ExtraMap) intent.getSerializableExtra(Constant.INTENT_KEY.EXTRAMAP);
        textTitle.setText(extraMapBean.getTitle());
        textDes.setText(extraMapBean.getSummary());
        textCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(ReciverActivity.this, MainActivity.class);
                intent1.putExtra(Constant.INTENT_KEY.EXTRAMAP, extraMapBean);
                startActivity(intent1);
                finish();
            }
        });
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

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {

    }
}
