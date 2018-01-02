package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.UserIncomeMx;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

import java.util.HashMap;

import okhttp3.Response;

public class TuiGuangYJActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private int id;
    private View viewDaiShowFY;
    private TextView textLeiJi;
    private TextView textDaiShouFY;
    private TextView textJinE;
    private UserIncomeMx userIncomeMx;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.ShuaXinYongJin:
                    initData();
                    break;
            }
        }
    };
    private String dbb;
    private Button buttonShouYiMX;
    private Button buttonLiJiTX;
    private TextView textDes;
    private ImageView imageView49;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_guang_yj);
        init(TuiGuangYJActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra(Constant.INTENT_KEY.id, 0);
        dbb = intent.getStringExtra(Constant.INTENT_KEY.value);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        viewDaiShowFY = findViewById(R.id.viewDaiShowFY);
        textLeiJi = (TextView) findViewById(R.id.textLeiJi);
        textDaiShouFY = (TextView) findViewById(R.id.textDaiShouFY);
        textJinE = (TextView) findViewById(R.id.textJinE);
        textDes = (TextView) findViewById(R.id.textDes);
        buttonShouYiMX = (Button) findViewById(R.id.buttonShouYiMX);
        buttonLiJiTX = (Button) findViewById(R.id.buttonLiJiTX);
        imageView49 = (ImageView) findViewById(R.id.imageView49);
    }

    @Override
    protected void initViews() {
        switch (id) {
            case 1:
                ((TextView) findViewById(R.id.textViewTitle)).setText("我的分润");
                viewDaiShowFY.setVisibility(View.GONE);
                break;
            case 2:
                ((TextView) findViewById(R.id.textViewTitle)).setText("推广佣金");
                viewDaiShowFY.setVisibility(View.GONE);
                break;
            case 3:
                ((TextView) findViewById(R.id.textViewTitle)).setText("平台返佣");
                viewDaiShowFY.setVisibility(View.VISIBLE);
                break;
            case 4:
                ((TextView) findViewById(R.id.textViewTitle)).setText("动宝积分");
                viewDaiShowFY.setVisibility(View.GONE);
                textLeiJi.setVisibility(View.INVISIBLE);
                buttonShouYiMX.setVisibility(View.GONE);
                buttonLiJiTX.setText("立即兑换");
                textDes.setText("现有积分");
                imageView49.setImageResource(R.mipmap.jifenimg);
                break;
            default:
                break;
        }
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        buttonShouYiMX.setOnClickListener(this);
        buttonLiJiTX.setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = "";
        switch (id) {
            case 1:
                url = Constant.HOST + Constant.Url.USER_INCOME1;
                break;
            case 2:
                url = Constant.HOST + Constant.Url.USER_INCOME2;
                break;
            case 3:
                url = Constant.HOST + Constant.Url.USER_INCOME3;
                break;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        if (id != 4) {
            showLoadingDialog();
            ApiClient.post(TuiGuangYJActivity.this, getOkObject(), new ApiClient.CallBack() {
                @Override
                public void onSuccess(String s) {
                    cancelLoadingDialog();
                    LogUtil.LogShitou("TuiGuangYJActivity--收益", "" + s);
                    try {
                        userIncomeMx = GsonUtils.parseJSON(s, UserIncomeMx.class);
                        if (userIncomeMx.getStatus() == 1) {
                            textJinE.setText(userIncomeMx.getAmount() + "");
                            switch (id) {
                                case 1:
                                    textLeiJi.setText("累计我的分润¥" + userIncomeMx.getAmount1());
                                    break;
                                case 2:
                                    textLeiJi.setText("累计推广佣金¥" + userIncomeMx.getAmount1());
                                    break;
                                case 3:
                                    textLeiJi.setText("累计平台返佣¥" + userIncomeMx.getAmount1());
                                    textDaiShouFY.setText("¥" + userIncomeMx.getAmount2());
                                    break;
                            }
                        } else if (userIncomeMx.getStatus() == 3) {
                            MyDialog.showReLoginDialog(TuiGuangYJActivity.this);
                        } else {
                            Toast.makeText(TuiGuangYJActivity.this, userIncomeMx.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(TuiGuangYJActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Response response) {
                    cancelLoadingDialog();
                    Toast.makeText(TuiGuangYJActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.buttonLiJiTX:
                if (id == 4) {
                    intent.setClass(this,MainActivity.class);
                    intent.putExtra(Constant.INTENT_KEY.isJiFen,true);
                    startActivity(intent);
                } else {
                    intent.putExtra(Constant.INTENT_KEY.id, id);
                    intent.putExtra(Constant.INTENT_KEY.value, userIncomeMx);
                    intent.setClass(this, TXActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.buttonShouYiMX:
                intent.putExtra(Constant.INTENT_KEY.id, id);
                intent.setClass(this, WoDeZDActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        registerReceiver(reciver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }
}
