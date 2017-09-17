package com.sxbwstxpay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.BankCardaddbefore;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.CheckIdCard;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.util.StringUtil;
import okhttp3.Response;

public class XinZengYHKActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private List<BankCardaddbefore.DataBean> bankCardaddbeforeData;
    private TextView textBankName;
    private String id;
    private TextView buttonSms;
    private EditText editName;
    private EditText editCard;
    private EditText editBankCard;
    private EditText editPhone;
    private EditText editCode;
    private Runnable mR;
    private int[] mI;
    private String mPhone_sms;
    private String title;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xin_zeng_yhk);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        title = intent.getStringExtra(Constant.INTENT_KEY.TITLE);
        type = intent.getIntExtra(Constant.INTENT_KEY.type, 0);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        textBankName = (TextView) findViewById(R.id.textBankName);
        buttonSms = (TextView) findViewById(R.id.buttonSms);
        editName = (EditText) findViewById(R.id.editName);
        editCard = (EditText) findViewById(R.id.editCard);
        editBankCard = (EditText) findViewById(R.id.editBankCard);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editCode = (EditText) findViewById(R.id.editCode);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText(title);
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.XuanZeYH).setOnClickListener(this);
        findViewById(R.id.buttonTiJiao).setOnClickListener(this);
        buttonSms.setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.BANK_CARDADDBEFORE;
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type + "");//储蓄卡1  信用卡2
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(XinZengYHKActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("XinZengYHKActivity--银行卡添加前请求", s + "");
                try {
                    BankCardaddbefore bankCardaddbefore = GsonUtils.parseJSON(s, BankCardaddbefore.class);
                    if (bankCardaddbefore.getStatus() == 1) {
                        bankCardaddbeforeData = bankCardaddbefore.getData();
                    } else if (bankCardaddbefore.getStatus() == 3) {
                        MyDialog.showReLoginDialog(XinZengYHKActivity.this);
                    } else {
                        Toast.makeText(XinZengYHKActivity.this, bankCardaddbefore.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(XinZengYHKActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(XinZengYHKActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
            case R.id.buttonSms:
                sendSMS();
                break;
            case R.id.buttonTiJiao:
                if (TextUtils.isEmpty(editName.getText().toString().trim())) {
                    Toast.makeText(XinZengYHKActivity.this, "请输入真实姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                CheckIdCard checkIdCard = new CheckIdCard(editCard.getText().toString().trim());
                if (!checkIdCard.validate()) {
                    Toast.makeText(XinZengYHKActivity.this, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(id)) {
                    Toast.makeText(XinZengYHKActivity.this, "请选择开户银行", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editBankCard.getText().toString().trim())) {
                    Toast.makeText(XinZengYHKActivity.this, "请输入持卡人卡号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editPhone.getText().toString().trim())) {
                    Toast.makeText(XinZengYHKActivity.this, "请输入银行预留手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editCode.getText().toString().trim())) {
                    Toast.makeText(XinZengYHKActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                tiJiao();
                break;
            case R.id.XuanZeYH:
                CharSequence[] charSequence = new CharSequence[bankCardaddbeforeData.size()];
                for (int i = 0; i < bankCardaddbeforeData.size(); i++) {
                    charSequence[i] = bankCardaddbeforeData.get(i).getName();
                }
                new AlertDialog.Builder(this).setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BankCardaddbefore.DataBean bankBean = bankCardaddbeforeData.get(which);
                        textBankName.setText(bankBean.getName());
                        id = bankBean.getId();
                    }
                }).show();
                break;
        }

    }

    /**
     * des： 短信发送按钮状态
     * author： ZhangJieBo
     * date： 2017/8/22 0022 上午 10:26
     */
    private void sendSMS() {
        buttonSms.removeCallbacks(mR);
        boolean mobileNO = StringUtil.isMobileNO(editPhone.getText().toString().trim());
        if (mobileNO) {
            mPhone_sms = editPhone.getText().toString().trim();
            buttonSms.setEnabled(false);
            mI = new int[]{60};

            mR = new Runnable() {
                @Override
                public void run() {
                    buttonSms.setText((mI[0]--) + "秒后重发");
                    if (mI[0] == 0) {
                        buttonSms.setEnabled(true);
                        buttonSms.setText("重新发送");
                        return;
                    } else {

                    }
                    buttonSms.postDelayed(mR, 1000);
                }
            };
            buttonSms.postDelayed(mR, 0);
            getSms();
        } else {
            Toast.makeText(XinZengYHKActivity.this, "输入正确的手机号", Toast.LENGTH_SHORT).show();
            editPhone.setText("");
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject1() {
        String url = Constant.HOST + Constant.Url.LOGIN_BINDSMS;
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", mPhone_sms);
        return new OkObject(params, url);
    }


    /**
     * des： 获取短信
     * author： ZhangJieBo
     * date： 2017/9/11 0011 下午 4:32
     */
    private void getSms() {
        showLoadingDialog();
        ApiClient.post(XinZengYHKActivity.this, getOkObject1(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("RenZhengFragment--获取短信", "" + s);
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    Toast.makeText(XinZengYHKActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    if (simpleInfo.getStatus() == 1) {

                    }
                } catch (Exception e) {
                    Toast.makeText(XinZengYHKActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(XinZengYHKActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject2() {
        String url = Constant.HOST + Constant.Url.BANK_CARDADD;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("name", editName.getText().toString().trim());
        params.put("card", editCard.getText().toString().trim());
        params.put("phone", editPhone.getText().toString().trim());
        params.put("bankCard", editBankCard.getText().toString().trim());
        params.put("bank", id + "");
        params.put("code", editCode.getText().toString().trim());
        return new OkObject(params, url);
    }

    private void tiJiao() {
        showLoadingDialog();
        ApiClient.post(XinZengYHKActivity.this, getOkObject2(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("XinZengYHKActivity--onSuccess", "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus() == 1) {
                        setResult(Constant.REQUEST_RESULT_CODE.XIN_YONG_KA);
                        finish();
                    } else if (simpleInfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(XinZengYHKActivity.this);
                    } else {
                    }
                    Toast.makeText(XinZengYHKActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(XinZengYHKActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(XinZengYHKActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
