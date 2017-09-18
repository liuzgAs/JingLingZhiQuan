package com.jlzquan.www.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jlzquan.www.R;
import com.jlzquan.www.base.MyDialog;
import com.jlzquan.www.base.ToLoginActivity;
import com.jlzquan.www.base.ZjbBaseActivity;
import com.jlzquan.www.constant.Constant;
import com.jlzquan.www.model.OkObject;
import com.jlzquan.www.model.SimpleInfo;
import com.jlzquan.www.util.ApiClient;
import com.jlzquan.www.util.AppUtil;
import com.jlzquan.www.util.GsonUtils;
import com.jlzquan.www.util.LogUtil;
import com.jlzquan.www.util.ScreenUtils;
import com.jlzquan.www.util.StringUtil;

import java.util.HashMap;

import okhttp3.Response;

public class XiuGaiMMActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private EditText editUserPwd;
    private EditText editNewuserPwd01;
    private EditText editNewuserPwd02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai_mm);
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
        editUserPwd = (EditText) findViewById(R.id.editUserPwd);
        editNewuserPwd01 = (EditText) findViewById(R.id.editNewuserPwd01);
        editNewuserPwd02 = (EditText) findViewById(R.id.editNewuserPwd02);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("修改密码");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.buttonTiJiao).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_PWDSAVE;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("userPwd", AppUtil.getMD5(AppUtil.getMD5(editUserPwd.getText().toString().trim()) + "ad"));
        params.put("newuserPwd", AppUtil.getMD5(AppUtil.getMD5(editNewuserPwd01.getText().toString().trim()) + "ad"));
        return new OkObject(params, url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
            case R.id.buttonTiJiao:
                if (TextUtils.isEmpty(editUserPwd.getText().toString().trim())) {
                    Toast.makeText(XiuGaiMMActivity.this, "请输入旧密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editNewuserPwd01.getText().toString().trim())) {
                    Toast.makeText(XiuGaiMMActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editNewuserPwd02.getText().toString().trim())) {
                    Toast.makeText(XiuGaiMMActivity.this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.equals(editNewuserPwd01.getText().toString().trim(), editNewuserPwd02.getText().toString().trim())) {
                    Toast.makeText(XiuGaiMMActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    editNewuserPwd01.setText("");
                    editNewuserPwd02.setText("");
                    return;
                }
                if (!StringUtil.isPassword(editNewuserPwd01.getText().toString().trim())) {
                    Toast.makeText(XiuGaiMMActivity.this, "密码太简单", Toast.LENGTH_SHORT).show();
                    return;
                }
                showLoadingDialog();
                ApiClient.post(XiuGaiMMActivity.this, getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        cancelLoadingDialog();
                        LogUtil.LogShitou("XiuGaiMMActivity--修改密码", s + "");
                        try {
                            SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                            if (simpleInfo.getStatus() == 1) {
                                ToLoginActivity.toLoginActivity(XiuGaiMMActivity.this);
                            } else if (simpleInfo.getStatus() == 3) {
                                MyDialog.showReLoginDialog(XiuGaiMMActivity.this);
                            } else {
                            }
                            Toast.makeText(XiuGaiMMActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(XiuGaiMMActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        cancelLoadingDialog();
                        Toast.makeText(XiuGaiMMActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
