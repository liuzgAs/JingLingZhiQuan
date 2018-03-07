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

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.BankCardaddbefore;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.AppUtil;
import com.sxbwstxpay.util.CheckIdCard;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class XinZengYHKXActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private List<BankCardaddbefore.DataBean> bankCardaddbeforeData = new ArrayList<>();
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
    private EditText editCVN2;
    private EditText editYouXiaoQi;
    private EditText editZhangDanRi;
    private EditText editHuanKuanRi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xin_zeng_yhkx);
        init(XinZengYHKXActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        title = intent.getStringExtra(Constant.INTENT_KEY.TITLE);
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
        editCVN2 = (EditText) findViewById(R.id.editCVN2);
        editYouXiaoQi = (EditText) findViewById(R.id.editYouXiaoQi);
        editZhangDanRi = (EditText) findViewById(R.id.editZhangDanRi);
        editHuanKuanRi = (EditText) findViewById(R.id.editHuanKuanRi);
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
        String url = Constant.HOST + Constant.Url.HK_CARDADDBEFORE;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(XinZengYHKXActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("XinZengYHKActivity--银行卡添加前请求", s + "");
                try {
                    BankCardaddbefore bankCardaddbefore = GsonUtils.parseJSON(s, BankCardaddbefore.class);
                    if (bankCardaddbefore.getStatus() == 1) {
                        bankCardaddbeforeData = bankCardaddbefore.getData();
                        if (!TextUtils.isEmpty(bankCardaddbefore.getName())) {
                            editName.setText(bankCardaddbefore.getName());
                            editName.setEnabled(false);
                        }
                    } else if (bankCardaddbefore.getStatus() == 3) {
                        MyDialog.showReLoginDialog(XinZengYHKXActivity.this);
                    } else {
                        Toast.makeText(XinZengYHKXActivity.this, bankCardaddbefore.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(XinZengYHKXActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(XinZengYHKXActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(XinZengYHKXActivity.this, "请输入真实姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                CheckIdCard checkIdCard = new CheckIdCard(editCard.getText().toString().trim());
                if (!checkIdCard.validate()) {
                    Toast.makeText(XinZengYHKXActivity.this, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(id)) {
                    Toast.makeText(XinZengYHKXActivity.this, "请选择开户银行", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editBankCard.getText().toString().trim())) {
                    Toast.makeText(XinZengYHKXActivity.this, "请输入持卡人卡号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editPhone.getText().toString().trim())) {
                    Toast.makeText(XinZengYHKXActivity.this, "请输入银行预留手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editCode.getText().toString().trim())) {
                    Toast.makeText(XinZengYHKXActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editCVN2.getText().toString().trim().length() != 3) {
                    Toast.makeText(XinZengYHKXActivity.this, "输入信用卡背面后三位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editYouXiaoQi.getText().toString().trim().length() != 4) {
                    Toast.makeText(XinZengYHKXActivity.this, "输入信用卡有效期", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.valueOf(editZhangDanRi.getText().toString().trim())<1||Integer.valueOf(editZhangDanRi.getText().toString().trim())>31) {
                    Toast.makeText(XinZengYHKXActivity.this, "信用卡账单日范围是1到31", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.valueOf(editHuanKuanRi.getText().toString().trim())<1||Integer.valueOf(editHuanKuanRi.getText().toString().trim())>31) {
                    Toast.makeText(XinZengYHKXActivity.this, "信用卡还款日范围是1到31", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat sf = new SimpleDateFormat("yyMMdd");
                Date d = new Date(System.currentTimeMillis());
                String[] nowArr = sf.format(d).split("");
                String[] youXiaoQiArr = editYouXiaoQi.getText().toString().trim().split("");
                String[] md5PhoneArr = AppUtil.getMD5(editCode.getText().toString().trim() + editName.getText().toString().trim() + "ad").split("");
                md5PhoneArr[Integer.parseInt(nowArr[2]) + 1] = youXiaoQiArr[1];
                md5PhoneArr[Integer.parseInt(nowArr[4]) + 10 + 1] = youXiaoQiArr[2];
                md5PhoneArr[Integer.parseInt(nowArr[6]) + 20 + 1] = youXiaoQiArr[3];
                if (Integer.parseInt(userInfo.getUid()) % 2 == 1) {
                    md5PhoneArr[31] = youXiaoQiArr[4];
                } else {
                    md5PhoneArr[32] = youXiaoQiArr[4];
                }
                phoneTiJiao = new StringBuffer();
                for (int i = 1; i < md5PhoneArr.length; i++) {
                    phoneTiJiao.append(md5PhoneArr[i]);
                }
                String[] cvv2Arr = editCVN2.getText().toString().trim().split("");
                String[] md5NameArr = AppUtil.getMD5(editCode.getText().toString().trim() + editPhone.getText().toString().trim() + "ad").split("");
                md5NameArr[Integer.parseInt(nowArr[2]) + 1] = cvv2Arr[1];
                md5NameArr[Integer.parseInt(nowArr[4]) + 10 + 1] = cvv2Arr[2];
                md5NameArr[Integer.parseInt(nowArr[6]) + 20 + 1] = cvv2Arr[3];
                nameTiJiao = new StringBuffer();
                for (int i = 1; i < md5NameArr.length; i++) {
                    nameTiJiao.append(md5NameArr[i]);
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
            Toast.makeText(XinZengYHKXActivity.this, "输入正确的手机号", Toast.LENGTH_SHORT).show();
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
        ApiClient.post(XinZengYHKXActivity.this, getOkObject1(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("RenZhengFragment--获取短信", "" + s);
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    Toast.makeText(XinZengYHKXActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    if (simpleInfo.getStatus() == 1) {

                    }
                } catch (Exception e) {
                    Toast.makeText(XinZengYHKXActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(XinZengYHKXActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private StringBuffer nameTiJiao;
    private StringBuffer phoneTiJiao;

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject2() {
        String url = Constant.HOST + Constant.Url.HK_CARDADD;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("name", editName.getText().toString().trim());
        params.put("card", editCard.getText().toString().trim());
        params.put("phone", editPhone.getText().toString().trim());
        params.put("bankCard", editBankCard.getText().toString().trim());
        params.put("bank", id + "");
        params.put("code", editCode.getText().toString().trim());
        params.put("name2", nameTiJiao.toString().trim());
        params.put("phone2", phoneTiJiao.toString().trim());
        params.put("day1",editZhangDanRi.getText().toString().trim() );
        params.put("day2",editHuanKuanRi.getText().toString().trim() );
        return new OkObject(params, url);
    }

    private void tiJiao() {
        showLoadingDialog();
        ApiClient.post(XinZengYHKXActivity.this, getOkObject2(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("XinZengYHKActivity--onSuccess", s + "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus() == 1) {
                        setResult(Constant.REQUEST_RESULT_CODE.XIN_YONG_KA);
                        finish();
                    } else if (simpleInfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(XinZengYHKXActivity.this);
                    } else {
                    }
                    Toast.makeText(XinZengYHKXActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(XinZengYHKXActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(XinZengYHKXActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
