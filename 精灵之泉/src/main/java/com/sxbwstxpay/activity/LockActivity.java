package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.widget.TextView;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.Lock9View;
import com.sxbwstxpay.model.ExtraMap;
import com.sxbwstxpay.util.ACache;

public class LockActivity extends ZjbBaseActivity {
    private Lock9View mLock9View;
    private TextView mTextView_tip;
    private String mPassword;
    private int paintCount = 0;
    private boolean isFrist = false;
    private String mPaintPassword;
    private ExtraMap extramap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        init();
    }

    @Override
    protected void initSP() {
        ACache aCache = ACache.get(LockActivity.this,Constant.ACACHE.App);
        mPaintPassword = aCache.getAsString(Constant.ACACHE.PAINT_PASSWORD);
        if (mPaintPassword == null) {
            isFrist = true;
        } else {
            isFrist = false;
        }
    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        extramap = (ExtraMap) intent.getSerializableExtra(Constant.INTENT_KEY.EXTRAMAP);
    }

    @Override
    protected void findID() {
        mLock9View = (Lock9View) findViewById(R.id.lock9View);
        mTextView_tip = (TextView) findViewById(R.id.textView_tip);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {
        mLock9View.setCallBack(new Lock9View.CallBack() {
            @Override
            public void onFinish(String password) {
                if (isFrist) {
                    if (password.length() >= 4 && password.length() <= 6) {
                        if (paintCount == 0) {
                            mPassword = password;
                            mTextView_tip.setText("请再次绘制");
                            paintCount++;
                        } else if (paintCount == 1) {
                            if (password.equals(mPassword)) {
                                Log.e("onFinish", "成功设置手势密码");
                                ACache aCache = ACache.get(LockActivity.this,Constant.ACACHE.App);
                                aCache.put(Constant.ACACHE.PAINT_PASSWORD, mPassword);
                                startToMainAvtivity();
                            } else {
                                paintCount = 0;
                                mTextView_tip.setText("两次不一样，重新绘制");
                            }
                        }

                    } else if (password.length() > 9) {
                        mTextView_tip.setText("连接的点过多，请重新绘制");
                    } else {
                        mTextView_tip.setText("请连接至少四个点");
                    }
                } else {
                    if (password.equals(mPaintPassword)) {
                        startToMainAvtivity();
                    } else {
                        mTextView_tip.setText("错误，请重新绘制");
                    }
                }
            }
        });
    }

    private void startToMainAvtivity() {
        Intent intent = new Intent();
        intent.setClass(LockActivity.this, MainActivity.class);
        if (extramap!=null){
            intent.putExtra(Constant.INTENT_KEY.EXTRAMAP, extramap);
        }
        startActivity(intent);
        finish();
    }


    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
