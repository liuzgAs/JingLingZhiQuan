package com.jlzquan.www.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.jlzquan.www.R;
import com.jlzquan.www.base.ZjbBaseNotLeftActivity;
import com.jlzquan.www.constant.Constant;
import com.jlzquan.www.model.OkObject;
import com.jlzquan.www.model.UserInfo;
import com.jlzquan.www.util.ACache;
import com.jlzquan.www.util.ApiClient;
import com.jlzquan.www.util.AppUtil;
import com.jlzquan.www.util.GsonUtils;
import com.jlzquan.www.util.LogUtil;
import com.jlzquan.www.util.StringUtil;

import java.util.HashMap;

import okhttp3.Response;

public class DengLuActivity extends ZjbBaseNotLeftActivity implements View.OnClickListener {

    private TextView textWangJiMM;
    private EditText editPhone;
    private EditText editPsw;
    private ImageView imageXieYi;
    private boolean isCheck = true;
    private View line01;
    private View line02;

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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String phone = intent.getStringExtra(Constant.INTENT_KEY.PHONE);
        editPhone.setText(phone);
        editPsw.requestFocus();
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        textWangJiMM = (TextView) findViewById(R.id.textWangJiMM);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editPsw = (EditText) findViewById(R.id.editPsw);
        imageXieYi = (ImageView) findViewById(R.id.imageXieYi);
        line01 = findViewById(R.id.line01);
        line02 = findViewById(R.id.line02);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {
        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.buttonZhuCe).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.textXieYi).setOnClickListener(this);
        textWangJiMM.setOnClickListener(this);
        imageXieYi.setOnClickListener(this);
        editPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    line01.setBackgroundColor(getResources().getColor(R.color.basic_color));
                } else {
                    line02.setBackgroundColor(getResources().getColor(R.color.text_gray));
                }
            }
        });
        editPsw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    line02.setBackgroundColor(getResources().getColor(R.color.basic_color));
                } else {
                    line01.setBackgroundColor(getResources().getColor(R.color.text_gray));
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject(String tokenTime) {
        String url = Constant.HOST + Constant.Url.LOGIN_INDEX;
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", editPhone.getText().toString().trim());
        params.put("userPwd", AppUtil.getMD5(AppUtil.getMD5(editPsw.getText().toString().trim()) + "ad"));
        params.put("deviceId", PushServiceFactory.getCloudPushService().getDeviceId());
        params.put("deviceToken", "");
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.textXieYi:
                intent.setClass(this, WebActivity.class);
                intent.putExtra(Constant.INTENT_KEY.TITLE, "注册协议");
                intent.putExtra(Constant.INTENT_KEY.URL,Constant.HOST+ Constant.Url.INFO_POLICY);
                startActivity(intent);
                break;
            case R.id.imageXieYi:
                isCheck = !isCheck;
                if (isCheck) {
                    imageXieYi.setImageResource(R.mipmap.duigou);
                } else {
                    imageXieYi.setImageResource(R.mipmap.uncheck);
                }
                break;
            case R.id.textWangJiMM:
                intent.setClass(this, WangJiMMActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonZhuCe:
                intent.setClass(this, ZhuCeActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
            case R.id.buttonLogin:
//                intent.setClass(DengLuActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
                if (!StringUtil.isMobileNO(editPhone.getText().toString().trim())) {
                    Toast.makeText(DengLuActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editPsw.getText().toString().trim())) {
                    Toast.makeText(DengLuActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isCheck) {
                    Toast.makeText(DengLuActivity.this, "阅读并同意《精灵之泉协议》", Toast.LENGTH_SHORT).show();
                    return;
                }
                login();
                break;
        }
    }


    /**
     * des： 登录
     * author： ZhangJieBo
     * date： 2017/9/8 0008 下午 5:27
     */
    private void login() {
        showLoadingDialog();
        final String tokenTime = System.currentTimeMillis() + "";
        ApiClient.post(this, getOkObject(tokenTime), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("DengLuActivity--登录返回", "" + s);
                cancelLoadingDialog();
                try {
                    loginSuccess(s, tokenTime);
                } catch (Exception e) {
                    Toast.makeText(DengLuActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(DengLuActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * des： 登录成功处理
     * author： ZhangJieBo
     * date： 2017/9/8 0008 下午 5:27
     */
    private void loginSuccess(String s, String tokenTime) {
        final UserInfo userInfo = GsonUtils.parseJSON(s, UserInfo.class);
        if (userInfo.getStatus() == 1) {
            ACache aCache = ACache.get(DengLuActivity.this, Constant.ACACHE.App);
            aCache.put(Constant.ACACHE.USER_INFO, userInfo);
            aCache.put(Constant.ACACHE.TOKENTIME, tokenTime);
            Constant.changeControl++;
            Intent intent = new Intent();
            intent.setClass(DengLuActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        Toast.makeText(DengLuActivity.this, userInfo.getInfo(), Toast.LENGTH_SHORT).show();
    }
}
