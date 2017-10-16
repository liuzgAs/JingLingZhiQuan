package com.sxbwstxpay.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.OrderVipbefore;
import com.sxbwstxpay.model.OrderVippay;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;

import okhttp3.Response;


public class TuiGuangActivity extends ZjbBaseActivity implements View.OnClickListener {

    private ImageView imageImg;
    private View viewBar;
    private TextView textText1;
    private TextView textText2;
    final IWXAPI api = WXAPIFactory.createWXAPI(this, null);
//    private BroadcastReceiver recevier = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (TextUtils.equals(action, Constant.BROADCASTCODE.PAY_RECEIVER)) {
//                cancelLoadingDialog();
//                int error = intent.getIntExtra("error", -1);
//                if (error == 0) {
//                    Intent intent1 = new Intent();
//                    intent1.setAction(Constant.BROADCASTCODE.VIP_TUI_GUANG_SHANG);
//                    sendBroadcast(intent1);
//                    MyDialog.dialogFinish(TuiGuangActivity.this, "支付成功");
//                } else if (error == -1) {
//                    MyDialog.showTipDialog(TuiGuangActivity.this, "支付失败");
//                } else if (error == -2) {
//                    MyDialog.showTipDialog(TuiGuangActivity.this, "支付取消");
//                }
//            }
//        }
//    };
    private WebSettings mSettings;
    private ProgressBar pb1;
    private WebView mWebView;
    private TextView textViewTitle;
    private OrderVipbefore orderVipbefore;
    private CheckBox checkXieYi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_guang);
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
        imageImg = (ImageView) findViewById(R.id.imageImg);
        viewBar = findViewById(R.id.viewBar);
        textText1 = (TextView) findViewById(R.id.textText1);
        textText2 = (TextView) findViewById(R.id.textText2);
        mWebView = (WebView) findViewById(R.id.webView);
        pb1 = (ProgressBar) findViewById(R.id.progressBar2);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        checkXieYi = (CheckBox) findViewById(R.id.checkXieYi);
    }

    @Override
    protected void initViews() {
        textViewTitle.setText("成为VIP精灵推广商");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        textText2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
        mWebView.setWebViewClient(new WebViewClient());//覆盖第三方浏览器
        mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setUseWideViewPort(true);
        mSettings.setLoadWithOverviewMode(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pb1.setProgress(newProgress);
                if (newProgress == 100) {
                    pb1.setVisibility(View.GONE);
                } else {
                    pb1.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.textJiaRu).setOnClickListener(this);
        findViewById(R.id.textXieYI).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.ORDER_VIPBEFORE;
        HashMap<String, String> params = new HashMap<>();
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(TuiGuangActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("TuiGuangActivity--推广商", s + "");
                try {
                    orderVipbefore = GsonUtils.parseJSON(s, OrderVipbefore.class);
                    if (orderVipbefore.getStatus() == 1) {
                        mWebView.loadUrl(orderVipbefore.getUrl());
                        textViewTitle.setText(orderVipbefore.getUrlTitle());
                        textText1.setText(orderVipbefore.getText1());
                        textText2.setText(orderVipbefore.getText2());
                    } else if (orderVipbefore.getStatus() == 3) {
                        MyDialog.showReLoginDialog(TuiGuangActivity.this);
                    } else {
                        Toast.makeText(TuiGuangActivity.this, orderVipbefore.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(TuiGuangActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(TuiGuangActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject1() {
        String url = Constant.HOST + Constant.Url.ORDER_VIPPAY;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.textXieYI:
                intent.setClass(this, WebActivity.class);
                intent.putExtra(Constant.INTENT_KEY.TITLE, "精灵之泉推广商服务协议");
                intent.putExtra(Constant.INTENT_KEY.URL, Constant.HOST + Constant.Url.INFO_POLICY2);
                startActivity(intent);
                break;
            case R.id.textJiaRu:
                if (checkXieYi.isChecked()){
                    intent.setClass(this,TuiGuangZFActivity.class);
                    intent.putExtra(Constant.INTENT_KEY.value,orderVipbefore);
                    startActivity(intent);
                }else {
                    MyDialog.showTipDialog(this,"请阅读并同意《精灵之泉推广商服务协议》");
                }
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }

    /**
     * 检查微信版本是否支付支付或是否安装可支付的微信版本
     */
    private boolean checkIsSupportedWeachatPay() {
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        return isPaySupported;
    }

    /**
     * 微信支付
     */
    private void wechatPay(OrderVippay orderPay) {
        if (!checkIsSupportedWeachatPay()) {
            Toast.makeText(this, "您暂未安装微信或您的微信版本暂不支持支付功能\n请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
        } else {
            OrderVippay.PayBean.ConfigBean config = orderPay.getPay().getConfig();
            api.registerApp(config.getAppid());
            PayReq mPayReq = new PayReq();
            mPayReq.appId = config.getAppid();
            mPayReq.partnerId = config.getPartnerid();
            mPayReq.prepayId = config.getPrepayid();
            mPayReq.packageValue = config.getPackagevalue();
            mPayReq.nonceStr = config.getNoncestr();
            mPayReq.timeStamp = config.getTimestamp() + "";
            mPayReq.sign = config.getSign().toUpperCase();
            api.sendReq(mPayReq);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Constant.BROADCASTCODE.PAY_RECEIVER);
//        registerReceiver(recevier, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(recevier);
    }
}
