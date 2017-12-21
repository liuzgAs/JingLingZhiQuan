package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxbwstxpay.R;
import com.sxbwstxpay.application.MyApplication;
import com.sxbwstxpay.base.ToLoginActivity;
import com.sxbwstxpay.base.ZjbBaseNotLeftActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.Lock9View;
import com.sxbwstxpay.model.ExtraMap;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.StringUtil;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class LockActivity extends ZjbBaseNotLeftActivity implements View.OnClickListener {
    private Lock9View mLock9View;
    private TextView mTextView_tip;
    private String mPassword;
    private int paintCount = 0;
    private int paintCount01 = 0;
    private boolean isFrist = false;
    private String mPaintPassword;
    private ExtraMap extramap;
    private ImageView imageTouXiang;
    private TextView textPhone;
    private TextView textWangJiMM;
    private TextView textSkip;
    private String isFristAQ;
    private TextView textOhter;
    private String shezhi;
    private String Main;
    private String guanBi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        init(LockActivity.class);
    }

    @Override
    protected void initSP() {
        ACache aCache = ACache.get(LockActivity.this, Constant.ACACHE.App);
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
        isFristAQ = intent.getStringExtra(Constant.INTENT_KEY.isFrist);
        shezhi = intent.getStringExtra(Constant.INTENT_KEY.shezhi);
        Main = intent.getStringExtra(Constant.INTENT_KEY.Main);
        guanBi = intent.getStringExtra(Constant.INTENT_KEY.guanBi);
    }

    @Override
    protected void findID() {
        mLock9View = (Lock9View) findViewById(R.id.lock9View);
        mTextView_tip = (TextView) findViewById(R.id.textView_tip);
        imageTouXiang = (ImageView) findViewById(R.id.imageTouXiang);
        textPhone = (TextView) findViewById(R.id.textPhone);
        textWangJiMM = (TextView) findViewById(R.id.textWangJiMM);
        textSkip = (TextView) findViewById(R.id.textSkip);
        textOhter = (TextView) findViewById(R.id.textOhter);
    }

    @Override
    protected void initViews() {
        if (userInfo != null) {
            if (!TextUtils.isEmpty(userInfo.getHeadImg())) {
                Glide.with(LockActivity.this)
                        .load(userInfo.getHeadImg())
                        .bitmapTransform(new CropCircleTransformation(this))
                        .placeholder(R.mipmap.ic_empty01)
                        .into(imageTouXiang);
            }
            textPhone.setText(StringUtil.hidePhone(userInfo.getUserName()));
        }
        if (isFrist) {
            textWangJiMM.setVisibility(View.INVISIBLE);
            textSkip.setVisibility(View.VISIBLE);
        } else {
            textWangJiMM.setVisibility(View.VISIBLE);
            textSkip.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(isFristAQ)) {
            textSkip.setVisibility(View.GONE);
            textOhter.setVisibility(View.GONE);
            mTextView_tip.setText("请验证手势密码");
        }
        if (!TextUtils.isEmpty(shezhi)) {
            isFrist = true;
//            mTextView_tip.setText("请验证手势密码");
            textSkip.setVisibility(View.GONE);
            textOhter.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setListeners() {
        mLock9View.setCallBack(new Lock9View.CallBack() {

            @Override
            public void onFinish(String password) {
                if (password.length() >= 4 && password.length() <= 9) {

                    if (isFrist) {
                        if (paintCount == 0) {
                            mPassword = password;
                            mTextView_tip.setText("请再次绘制");
                            paintCount++;
                        } else if (paintCount == 1) {
                            if (password.equals(mPassword)) {
                                Log.e("onFinish", "成功设置手势密码");
                                ACache aCache = ACache.get(LockActivity.this, Constant.ACACHE.App);
                                aCache.put(Constant.ACACHE.PAINT_PASSWORD, mPassword);
                                startToMainAvtivity();
                            } else {
                                paintCount = 0;
                                mTextView_tip.setText("两次不一样，重新绘制");
                            }
                        }

                    } else {
                        if (password.equals(mPaintPassword)) {
                            if (!TextUtils.isEmpty(isFristAQ)) {
                                mTextView_tip.setText("设置手势密码");
                                isFrist = true;
                            } else {
                                if (!TextUtils.isEmpty(guanBi)) {
                                    ACache aCache = ACache.get(LockActivity.this, Constant.ACACHE.App);
                                    aCache.put(Constant.ACACHE.PAINT_PASSWORD, "");
                                    startToMainAvtivity();
                                } else {
                                    startToMainAvtivity();
                                }
                            }
                        } else {
                            paintCount01++;
                            switch (paintCount01) {
                                case 1:
                                    mTextView_tip.setText("错误，还有四次输入机会");
                                    break;
                                case 2:
                                    mTextView_tip.setText("错误，还有三次输入机会");
                                    break;
                                case 3:
                                    mTextView_tip.setText("错误，还有两次输入机会");
                                    break;
                                case 4:
                                    mTextView_tip.setText("错误，还有一次输入机会");
                                    break;
                                case 5:
                                    ToLoginActivity.toLoginActivity(LockActivity.this);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                } else if (password.length() > 9) {
                    mTextView_tip.setText("连接的点过多，请重新绘制");
                } else {
                    mTextView_tip.setText("请连接至少四个点");
                }
            }
        });
        textWangJiMM.setOnClickListener(this);
        textSkip.setOnClickListener(this);
        textOhter.setOnClickListener(this);
    }

    private void startToMainAvtivity() {
        if (!TextUtils.isEmpty(Main)) {
            Intent intent = new Intent();
            intent.setClass(LockActivity.this, MainActivity.class);
            if (extramap != null) {
                intent.putExtra(Constant.INTENT_KEY.EXTRAMAP, extramap);
            }
            startActivity(intent);
        }
        finish();
    }


    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(isFristAQ)) {
            if (textSkip.getVisibility()==View.VISIBLE){
                startToMainAvtivity();
            }else {
                finish();
            }
        } else {
            MyApplication.getInstance().exit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textSkip:
                startToMainAvtivity();
                break;
            case R.id.textWangJiMM:
                ToLoginActivity.toLoginActivity(this);
                break;
            case R.id.textOhter:
                ToLoginActivity.toLoginActivity(this);
                break;
        }
    }
}
