package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import com.jlzquan.www.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.AppUtil;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.util.StringUtil;
import okhttp3.Response;

public class WangJiMMActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private EditText[] editView = new EditText[4];
    private TextView textSms;
    private Runnable mR;
    private int[] mI;
    private String mPhone_sms;
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
        editView[0] = (EditText) findViewById(R.id.edit01);
        editView[1] = (EditText) findViewById(R.id.edit02);
        editView[2] = (EditText) findViewById(R.id.edit03);
        editView[3] = (EditText) findViewById(R.id.edit04);
        textSms = (TextView) findViewById(R.id.textSms);
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
        findViewById(R.id.buttonTiJiao).setOnClickListener(this);
        textSms.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textSms:
                sendSMS();
                break;
            case R.id.buttonTiJiao:
                if (TextUtils.isEmpty(mPhone_sms)) {
                    Toast.makeText(WangJiMMActivity.this, "请先发送验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editView[1].getText().toString().trim())) {
                    Toast.makeText(WangJiMMActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editView[2].getText().toString().trim()) || TextUtils.isEmpty(editView[3].getText().toString().trim())) {
                    Toast.makeText(WangJiMMActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.equals(editView[2].getText().toString().trim(), editView[3].getText().toString().trim())) {
                    Toast.makeText(WangJiMMActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!StringUtil.isPassword(editView[2].getText().toString().trim())) {
                    Toast.makeText(WangJiMMActivity.this, "密码太简单", Toast.LENGTH_SHORT).show();
                    return;
                }
                forgetPSW();
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }

    /**
     * des： 短信发送按钮状态
     * author： ZhangJieBo
     * date： 2017/8/22 0022 上午 10:26
     */
    private void sendSMS() {
        textSms.removeCallbacks(mR);
        boolean mobileNO = StringUtil.isMobileNO(editView[0].getText().toString().trim());
        if (mobileNO) {
            mPhone_sms = editView[0].getText().toString().trim();
            textSms.setEnabled(false);
            mI = new int[]{60};

            mR = new Runnable() {
                @Override
                public void run() {
                    textSms.setText((mI[0]--) + "秒后重发");
                    if (mI[0] == 0) {
                        textSms.setEnabled(true);
                        textSms.setText("重新发送");
                        return;
                    } else {
                    }
                    textSms.postDelayed(mR, 1000);
                }
            };
            textSms.postDelayed(mR, 0);
            getSms();
        } else {
            Toast.makeText(WangJiMMActivity.this, "输入正确的手机号", Toast.LENGTH_SHORT).show();
            editView[0].setText("");
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.LOGIN_FORGETSMS;
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", mPhone_sms);
        return new OkObject(params, url);
    }

    /**
     * des： 获取短信
     * author： ZhangJieBo
     * date： 2017/7/6 0006 下午 2:45
     */
    private void getSms() {
        showLoadingDialog();
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("WangJiMMActivity--获取短信", "" + s);
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    Toast.makeText(WangJiMMActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    if (simpleInfo.getStatus() == 1) {

                    }
                } catch (Exception e) {
                    Toast.makeText(WangJiMMActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(WangJiMMActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private OkObject getOkObject1() {
        String url = Constant.HOST + Constant.Url.LOGIN_FORGET;
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", mPhone_sms);
        params.put("userPwd", AppUtil.getMD5(AppUtil.getMD5(editView[2].getText().toString().trim()) + "ad"));
        params.put("code", editView[1].getText().toString().trim());
        return new OkObject(params, url);
    }

    /**
     * des： 忘记密码
     * author： ZhangJieBo
     * date： 2017/9/11 0011 上午 9:13
     */
    private void forgetPSW() {
        showLoadingDialog();
        ApiClient.post(this, getOkObject1(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("WangJiMMActivity--onSuccess", "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    Toast.makeText(WangJiMMActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    if (simpleInfo.getStatus() == 1) {
                        Intent intent = new Intent();
                        intent.setClass(WangJiMMActivity.this, DengLuActivity.class);
                        intent.putExtra(Constant.INTENT_KEY.PHONE, mPhone_sms);
                        startActivity(intent);
                    } else {

                    }
                } catch (Exception e) {
                    Toast.makeText(WangJiMMActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(WangJiMMActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
