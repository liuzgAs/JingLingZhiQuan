package hudongchuangxiang.com.jinglingzhiquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseNotLeftActivity;
import hudongchuangxiang.com.jinglingzhiquan.constant.Constant;
import hudongchuangxiang.com.jinglingzhiquan.util.ACache;

public class HuanYingActivity extends ZjbBaseNotLeftActivity {
    private String isFirst = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huan_ying);
        init();
    }

    @Override
    protected void initSP() {
        ACache aCache = ACache.get(HuanYingActivity.this, Constant.ACACHE.FRIST);
        String frist = aCache.getAsString(Constant.ACACHE.FRIST);
        if (frist != null) {
            isFirst = frist;
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if (TextUtils.equals(isFirst, "1")) {
                        Intent intent = new Intent(HuanYingActivity.this, YinDaoActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        toMainActivity();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void toMainActivity() {
        Intent intent = new Intent();
        if (isLogin) {
            intent.setClass(HuanYingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            intent.setClass(HuanYingActivity.this, DengLuActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
