package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.Test_result;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

import java.util.HashMap;

import okhttp3.Response;

public class TestResultActivity extends ZjbBaseActivity implements View.OnClickListener {
    private View viewBar;
    private Test_result test_result;
    private Button buttonTest;
    private ImageView imageImg;
    private int type=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        init(TestResultActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        type=getIntent().getIntExtra("type",0);
        test_result=(Test_result) getIntent().getSerializableExtra("test_result");
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        buttonTest = (Button) findViewById(R.id.buttonTest);
        imageImg = (ImageView) findViewById(R.id.imageImg);

    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        if (type==0){
            buttonTest.setText(test_result.getBtn_txt());
            GlideApp.with(this)
                    .load(test_result.getBgimg())
                    .placeholder(R.mipmap.ic_empty)
                    .into(imageImg);
        }else {
            getRez();
        }
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        buttonTest.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
            case R.id.buttonTest:
                intent.setAction(Constant.BROADCASTCODE.STYLE);
                intent.putExtra(Constant.INTENT_KEY.STYLE, test_result.getCid());
                sendBroadcast(intent);
                finish();
                break;
            default:
                break;
        }
    }
    private void getRez(){
        showLoadingDialog();
        ApiClient.post(TestResultActivity.this, getOkObjectRez(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("TestResultActivity--测试结果", "");
                try {
                    test_result = GsonUtils.parseJSON(s, Test_result.class);
                    if (test_result.getStatus() == 1) {
                        buttonTest.setText(test_result.getBtn_txt());
                        GlideApp.with(TestResultActivity.this)
                                .load(test_result.getBgimg())
                                .placeholder(R.mipmap.ic_empty)
                                .into(imageImg);
                    } else if (test_result.getStatus() == 3) {
                        MyDialog.showReLoginDialog(TestResultActivity.this);
                    } else {
                        Toast.makeText(TestResultActivity.this, test_result.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(TestResultActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(TestResultActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private OkObject getOkObjectRez() {
        String url = Constant.HOST + Constant.Url.TEST_RESULT;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("style", "");
        return new OkObject(params, url);
    }
}
