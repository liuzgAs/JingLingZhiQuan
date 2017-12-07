package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.UserIncome;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

import java.util.HashMap;

import okhttp3.Response;

public class WoDeSYActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private TextView textFenRun;
    private TextView textYongJin;
    private TextView textFanYong;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case Constant.BROADCASTCODE.ShuaXinYongJin:
                    initData();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_sy);
        init(WoDeSYActivity.class);
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
        SpannableString span = new SpannableString("¥" + "0.0");
        span.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textFenRun.setText(span);
        SpannableString span1 = new SpannableString("¥" + "0.0");
        span1.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textYongJin.setText(span1);
        SpannableString span2 = new SpannableString("¥" + "0.0");
        span2.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textFanYong.setText(span2);
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
        try {
            params.put("uid",userInfo.getUid());
        } catch (Exception e) {
            finish();
        }
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
                    UserIncome userIncome = GsonUtils.parseJSON(s, UserIncome.class);
                    if (userIncome.getStatus()==1){
                        SpannableString span = new SpannableString("¥" + userIncome.getAmount1());
                        span.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textFenRun.setText(span);
                        SpannableString span1 = new SpannableString("¥" + userIncome.getAmount2());
                        span1.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textYongJin.setText(span1);
                        SpannableString span2 = new SpannableString("¥" + userIncome.getAmount3());
                        span2.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textFanYong.setText(span2);
                    }else if (userIncome.getStatus()==3){
                        MyDialog.showReLoginDialog(WoDeSYActivity.this);
                    }else {
                        Toast.makeText(WoDeSYActivity.this, userIncome.getInfo(), Toast.LENGTH_SHORT).show();
                    }
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
                intent.putExtra(Constant.INTENT_KEY.id,2);
                startActivity(intent);
                break;
            case R.id.viewFanYong:
                intent.setClass(this, TuiGuangYJActivity.class);
                intent.putExtra(Constant.INTENT_KEY.id,3);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        registerReceiver(reciver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }
}
