package com.sxbwstxpay.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.PayCheckcode;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.FileUtil;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Response;

public class ShouKuanEWMActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private String money;
    private ImageView imageEWM;
    private TextView textName;
    private View viewShouKuanEWM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_kuan_ewm);
        init(ShouKuanEWMActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        money = intent.getStringExtra(Constant.INTENT_KEY.value);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        imageEWM = (ImageView) findViewById(R.id.imageEWM);
        textName = (TextView) findViewById(R.id.textName);
        viewShouKuanEWM = findViewById(R.id.viewShouKuanEWM);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("收款二维码");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.btnBaoCunXC).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.PAY_CHECKCODE;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("money", money);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(ShouKuanEWMActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ShouKuanEWMActivity--onSuccess", s + "");
                try {
                    PayCheckcode payCheckcode = GsonUtils.parseJSON(s, PayCheckcode.class);
                    if (payCheckcode.getStatus() == 1) {
                        GlideApp.with(ShouKuanEWMActivity.this)
                                .asBitmap()
                                .centerCrop()
                                .transform(new RoundedCorners((int) DpUtils.convertDpToPixel(10, ShouKuanEWMActivity.this)))
                                .load(payCheckcode.getEwm_img())
                                .into(imageEWM);
                        textName.setText(payCheckcode.getDes());
                    } else if (payCheckcode.getStatus() == 3) {
                        MyDialog.showReLoginDialog(ShouKuanEWMActivity.this);
                    } else {
                        Toast.makeText(ShouKuanEWMActivity.this, payCheckcode.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ShouKuanEWMActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(ShouKuanEWMActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBaoCunXC:
                viewShouKuanEWM.setDrawingCacheEnabled(true);
                viewShouKuanEWM.buildDrawingCache();  //启用DrawingCache并创建位图
                final Bitmap bitmap = Bitmap.createBitmap(viewShouKuanEWM.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FileUtil.saveMyBitmap(ShouKuanEWMActivity.this, "收款二维码" + System.currentTimeMillis(), bitmap);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    Toast toast = new Toast(ShouKuanEWMActivity.this);
//                                    ImageView imageView = new ImageView(ShouKuanEWMActivity.this);
//                                    imageView.setImageBitmap(bitmap);
//                                    imageView.setScaleX(0.6f);
//                                    imageView.setScaleY(0.6f);
//                                    toast.setView(imageView);
//                                    toast.setGravity(Gravity.CENTER, 0, 0);
//                                    toast.setDuration(Toast.LENGTH_SHORT);
//                                    toast.show();
                                    Toast.makeText(ShouKuanEWMActivity.this, "图片保存在\"/sdcard/精灵之泉/\"目录下", Toast.LENGTH_SHORT).show();
                                }
                            });
                            if (bitmap!=null){
                                bitmap.recycle();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }
}
