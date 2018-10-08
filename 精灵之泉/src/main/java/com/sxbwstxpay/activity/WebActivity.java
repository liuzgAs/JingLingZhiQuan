package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;


/**
 * des： 网页
 * author： zhangjiebo
 * date： 2017/7/6 0006 下午 1:40
 */
public class WebActivity extends ZjbBaseActivity implements View.OnClickListener {

    private WebView mWebView;
    private String mUrl;
    private String title;
    private WebSettings mSettings;
    private ProgressBar pb1;
    private TextView mTv_title;
    private View viewBar;
    private String cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        init(WebActivity.class);
    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(Constant.INTENT_KEY.URL);
        title = intent.getStringExtra(Constant.INTENT_KEY.TITLE);
        cid = intent.getStringExtra(Constant.INTENT_KEY.CID);
        viewBar = findViewById(R.id.viewBar);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void findID() {
        mWebView = (WebView) findViewById(R.id.webView);
        pb1 = (ProgressBar) findViewById(R.id.progressBar2);
        mTv_title = (TextView) findViewById(R.id.textViewTitle);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        if (!TextUtils.isEmpty(title)) {
            mTv_title.setText(title);
        }
        mWebView.loadUrl(mUrl);
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
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.LogShitou("url",url);
                if (url.contains("TestStyle")){
                    Intent intent = new Intent();
                    intent.setAction(Constant.BROADCASTCODE.STYLE);
                    intent.putExtra(Constant.INTENT_KEY.STYLE, cid);
                    sendBroadcast(intent);
                    finish();
                }
                return false;
            }
        });
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageBack:
                back();
                break;
        }
    }

    private void back() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }
}
