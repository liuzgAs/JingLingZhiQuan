package hudongchuangxiang.com.jinglingzhiquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseActivity;
import hudongchuangxiang.com.jinglingzhiquan.constant.Constant;
import hudongchuangxiang.com.jinglingzhiquan.model.OkObject;
import hudongchuangxiang.com.jinglingzhiquan.util.ApiClient;
import hudongchuangxiang.com.jinglingzhiquan.util.LogUtil;
import hudongchuangxiang.com.jinglingzhiquan.util.ScreenUtils;
import okhttp3.Response;

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

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_INCOME;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid",userInfo.getUid());
        params.put("tokenTime",tokenTime);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(WoDeSYActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("WoDeSYActivity--我的收益", ""+s);
                try {
//                    BankCardlist bankCardlist = GsonUtils.parseJSON(s, BankCardlist.class);
//                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
//                    if (simpleInfo.getStatus()==1){
//                    }else if (simpleInfo.getStatus()==2){
//                        MyDialog.showReLoginDialog(WoDeSYActivity.this);
//                    }else {
//                        Toast.makeText(WoDeSYActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
//                    }
                } catch (Exception e) {
                    Toast.makeText(WoDeSYActivity.this,"数据出错", Toast.LENGTH_SHORT).show();
                }
            }
        
            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(WoDeSYActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
        
        
        SpannableString span = new SpannableString("¥" + "0.00");
        span.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textFenRun.setText(span);
        SpannableString span1 = new SpannableString("¥" + "0.00");
        span1.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textYongJin.setText(span1);
        SpannableString span2 = new SpannableString("¥" + "0.00");
        span2.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textFanYong.setText(span2);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.viewFenRun:
                intent.setClass(this, TuiGuangYJActivity.class);
                intent.putExtra(Constant.INTENT_KEY.id,1);
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
