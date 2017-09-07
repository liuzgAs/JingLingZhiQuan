package hudongchuangxiang.com.jinglingzhiquan.activity;

import android.os.Bundle;
import android.view.View;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseActivity;

public class ZhuCeActivity extends ZjbBaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
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

    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
