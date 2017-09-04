package hudongchuangxiang.com.jinglingzhiquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseActivity;
import hudongchuangxiang.com.jinglingzhiquan.util.ScreenUtils;

public class TuiGuangYJActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_guang_yj);
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
        viewBar = findViewById(R.id.viewBar);
    }

    @Override
    protected void initViews() {
        ((TextView)findViewById(R.id.textViewTitle)).setText("推广佣金");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.buttonShouYiMX).setOnClickListener(this);
        findViewById(R.id.buttonLiJiTX).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.buttonLiJiTX:
                intent.setClass(this,LiJiTXActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonShouYiMX:
                intent.setClass(this,ShouYiMXActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
