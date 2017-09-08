package hudongchuangxiang.com.jinglingzhiquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseActivity;
import hudongchuangxiang.com.jinglingzhiquan.util.ScreenUtils;

public class WoDeSYActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private TextView textFenRun;
    private TextView textYongJin;
    private TextView textFanYong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_sy);
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
        textFenRun = (TextView) findViewById(R.id.textFenRun);
        textYongJin = (TextView) findViewById(R.id.textYongJin);
        textFanYong = (TextView) findViewById(R.id.textFanYong);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("我的收益");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.viewFenRun).setOnClickListener(this);
        findViewById(R.id.viewYongJin).setOnClickListener(this);
        findViewById(R.id.viewFanYong).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        SpannableString span = new SpannableString("¥" + "51513.00");
        span.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textFenRun.setText(span);
        SpannableString span1 = new SpannableString("¥" + "588.00");
        span.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textYongJin.setText(span1);
        SpannableString span2 = new SpannableString("¥" + "588.00");
        span.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textFanYong.setText(span2);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.viewFenRun:
                intent.setClass(this, TuiGuangYJActivity.class);
                startActivity(intent);
                break;
            case R.id.viewYongJin:
                intent.setClass(this, TuiGuangYJActivity.class);
                startActivity(intent);
                break;
            case R.id.viewFanYong:
                intent.setClass(this, TuiGuangYJActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
