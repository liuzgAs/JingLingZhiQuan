package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.model.UserIncomeMx;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.MoneyInputFilter;
import com.sxbwstxpay.util.ScreenUtils;

import java.util.HashMap;

import okhttp3.Response;

public class TXActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private EditText editJinE;
    private UserIncomeMx userIncomeMx;
    private TextView textKeTiXian;
    private TextView textDes;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_li_ji_tx);
        init(TXActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        userIncomeMx = (UserIncomeMx) intent.getSerializableExtra(Constant.INTENT_KEY.value);
        id = intent.getIntExtra(Constant.INTENT_KEY.id, 0);
    }

    @Override
    protected void findID() {
        editJinE = (EditText) findViewById(R.id.editJinE);
        textKeTiXian = (TextView) findViewById(R.id.textKeTiXian);
        textDes = (TextView) findViewById(R.id.textDes);
        viewBar = findViewById(R.id.viewBar);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("提现");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        editJinE.setText(userIncomeMx.getAmount() + "");
        editJinE.setSelection((userIncomeMx.getAmount() + "").length());
        textKeTiXian.setText("可提现额度" + userIncomeMx.getAmount());
        textDes.setText(userIncomeMx.getDes());
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.textQuanBuTX).setOnClickListener(this);
        findViewById(R.id.buttonTiXian).setOnClickListener(this);
        MoneyInputFilter.init(editJinE);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonTiXian:
                if (TextUtils.equals(editJinE.getText().toString().trim(), "")) {
                    Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.equals(editJinE.getText().toString().trim(), "0.")) {
                    Toast.makeText(this, "请输入正确的金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Double.parseDouble(editJinE.getText().toString().trim()) >= userIncomeMx.getAmount()) {
                    Toast.makeText(this, "可提现额度为" + userIncomeMx.getAmount(), Toast.LENGTH_SHORT).show();
                    editJinE.setText(userIncomeMx.getAmount() + "");
                    editJinE.setSelection((userIncomeMx.getAmount() + "").length());
                    return;
                }
                tiXian();
                break;
            case R.id.textQuanBuTX:
                try {
                    editJinE.setText(userIncomeMx.getAmount() + "");
                    editJinE.setSelection((userIncomeMx.getAmount() + "").length());
                } catch (Exception e) {
                }
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_WITHDRAW;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        switch (id) {
            case 1:
                params.put("type", "income1");
                break;
            case 2:
                params.put("type", "income2");
                break;
            case 3:
                params.put("type", "income3");
                break;
        }
        params.put("money", editJinE.getText().toString().trim());
        return new OkObject(params, url);
    }

    /**
     * des： 提现请求
     * author： ZhangJieBo
     * date： 2017/9/28 0028 下午 3:53
     */
    private void tiXian() {
        showLoadingDialog();
        ApiClient.post(TXActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("TXActivity--提现",s+ "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus() == 1) {
                        Intent intent = new Intent();
                        intent.setAction(Constant.BROADCASTCODE.ShuaXinYongJin);
                        sendBroadcast(intent);
                        MyDialog.dialogFinish(TXActivity.this,"申请提现成功");
                    } else if (simpleInfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(TXActivity.this);
                    } else {
                        Toast.makeText(TXActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(TXActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(TXActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
