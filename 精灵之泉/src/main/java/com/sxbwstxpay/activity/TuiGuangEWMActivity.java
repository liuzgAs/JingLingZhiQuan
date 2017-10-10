package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.ShareIndex;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.FileUtil;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Response;

public class TuiGuangEWMActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private ImageView imageImg;
    private IWXAPI api = WXAPIFactory.createWXAPI(TuiGuangEWMActivity.this, Constant.WXAPPID, true);
    private Tencent mTencent;
    private ShareIndex shareIndex;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.WX_SHARE:
                    MyDialog.showTipDialog(TuiGuangEWMActivity.this, "分享成功");
                    break;
                case Constant.BROADCASTCODE.WX_SHARE_FAIL:
                    MyDialog.showTipDialog(TuiGuangEWMActivity.this, "取消分享");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_guang_ewm);
        mTencent = Tencent.createInstance(Constant.QQ_ID, this.getApplicationContext());
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
        imageImg = (ImageView) findViewById(R.id.imageImg);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("推广二维码");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.imageShare).setOnClickListener(this);
        findViewById(R.id.buttonBaoCun).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.SHARE_INDEX;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(TuiGuangEWMActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("TuiGuangEWMActivity--我的推广二维码", s + "");
                try {
                    shareIndex = GsonUtils.parseJSON(s, ShareIndex.class);
                    if (shareIndex.getStatus() == 1) {
                        Glide.with(TuiGuangEWMActivity.this)
                                .load(shareIndex.getQrcode())
                                .asBitmap()
                                .placeholder(R.mipmap.ic_empty03)
                                .into(imageImg);
                    } else if (shareIndex.getStatus() == 3) {
                        MyDialog.showReLoginDialog(TuiGuangEWMActivity.this);
                    } else {
                        Toast.makeText(TuiGuangEWMActivity.this, shareIndex.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(TuiGuangEWMActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(TuiGuangEWMActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTencent.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonBaoCun:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            imageImg.setDrawingCacheEnabled(true);
                            Bitmap bm = imageImg.getDrawingCache();
                            FileUtil.saveMyBitmap(TuiGuangEWMActivity.this, "分享二维码" + System.currentTimeMillis(), bm);
                            imageImg.setDrawingCacheEnabled(false);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TuiGuangEWMActivity.this, "图片保存在\"/sdcard/精灵之泉/\"目录下", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.imageShare:
                MyDialog.share01(this, api, mTencent, "TuiGuangEWMActivity", shareIndex.getShare_register_url(), shareIndex.getShare_title(), shareIndex.getShare_description(), shareIndex.getShare_icon());
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE);
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE_FAIL);
        registerReceiver(reciver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }
}
