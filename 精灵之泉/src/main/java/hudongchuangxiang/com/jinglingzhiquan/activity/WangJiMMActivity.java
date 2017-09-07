package hudongchuangxiang.com.jinglingzhiquan.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseActivity;
import hudongchuangxiang.com.jinglingzhiquan.util.ScreenUtils;

public class WangJiMMActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wang_ji_mm);
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
        ((TextView) findViewById(R.id.textViewTitle)).setText("忘记密码");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
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
        switch (v.getId()){
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
