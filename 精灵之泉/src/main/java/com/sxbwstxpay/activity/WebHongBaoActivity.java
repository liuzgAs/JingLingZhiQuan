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
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.IndexBonusget;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

import java.util.HashMap;

import okhttp3.Response;


/**
 * des： 网页
 * author： zhangjiebo
 * date： 2017/7/6 0006 下午 1:40
 */
public class WebHongBaoActivity extends ZjbBaseActivity implements View.OnClickListener {

    private WebView mWebView;
    private String mUrl;
    private String title;
    private WebSettings mSettings;
    private ProgressBar pb1;
    private TextView mTv_title;
    private View viewBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_hongbao);
        init(WebHongBaoActivity.class);
    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(Constant.INTENT_KEY.URL);
        title = intent.getStringExtra(Constant.INTENT_KEY.TITLE);
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
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.btnHongBao).setOnClickListener(this);
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
        String url = Constant.HOST + Constant.Url.INDEX_BONUSGET;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        return new OkObject(params, url);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageBack:
                back();
                break;
            case R.id.btnHongBao:
                showLoadingDialog();
                ApiClient.post(WebHongBaoActivity.this, getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        cancelLoadingDialog();
                        LogUtil.LogShitou("WebHongBaoActivity--onSuccess",s+ "");
                        try {
                            IndexBonusget indexBonusget = GsonUtils.parseJSON(s, IndexBonusget.class);
                            if (indexBonusget.getStatus()==1){
                                Intent intent = new Intent();
                                intent.putExtra(Constant.INTENT_KEY.value,indexBonusget);
                                setResult(Constant.REQUEST_RESULT_CODE.HONG_BAO,intent);
                                finish();
                            }else if (indexBonusget.getStatus()==3){
                                MyDialog.showReLoginDialog(WebHongBaoActivity.this);
                            }else {
                                Toast.makeText(WebHongBaoActivity.this, indexBonusget.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(WebHongBaoActivity.this,"数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        cancelLoadingDialog();
                        Toast.makeText(WebHongBaoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
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
