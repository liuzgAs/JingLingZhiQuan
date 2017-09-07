package hudongchuangxiang.com.jinglingzhiquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseNotLeftActivity;

public class DengLuActivity extends ZjbBaseNotLeftActivity implements View.OnClickListener {

    private TextView textWangJiMM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deng_lu);
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
        textWangJiMM = (TextView) findViewById(R.id.textWangJiMM);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {
        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.buttonZhuCe).setOnClickListener(this);
        textWangJiMM.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.textWangJiMM:
                intent.setClass(this,WangJiMMActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonZhuCe:
                intent.setClass(this,ZhuCeActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
            case R.id.buttonLogin:
                intent.setClass(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
