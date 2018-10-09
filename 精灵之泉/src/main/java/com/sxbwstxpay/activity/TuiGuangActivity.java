package com.sxbwstxpay.activity;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.AddCar;
import com.sxbwstxpay.model.CartAddcart;
import com.sxbwstxpay.model.CartIndex;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.OrderVipbefore;
import com.sxbwstxpay.model.OrderVippay;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.VipBuyViewHolder;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;


public class TuiGuangActivity extends ZjbBaseActivity implements View.OnClickListener {

    private List<OrderVipbefore.SelectValueBean> selectValueBeanList;

    private ImageView imageImg;
    private View viewBar;
    private TextView textText1;
    final IWXAPI api = WXAPIFactory.createWXAPI(this, null);
        private BroadcastReceiver recevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(action, Constant.BROADCASTCODE.PAY_SUCCESS)) {
                if (orderVipbefore!=null){
//                    showPaySDialog(orderVipbefore);
                }
            }
        }
    };
    private WebSettings mSettings;
    private ProgressBar pb1;
    private WebView mWebView;
    private TextView textViewTitle;
    private OrderVipbefore orderVipbefore;
    private CheckBox checkXieYi;
    private int type;
    private String selectTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_guang);
        init(TuiGuangActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        type = intent.getIntExtra(Constant.INTENT_KEY.type, 0);
    }

    @Override
    protected void findID() {
        imageImg = (ImageView) findViewById(R.id.imageImg);
        viewBar = findViewById(R.id.viewBar);
        textText1 = (TextView) findViewById(R.id.textText1);
        mWebView = (WebView) findViewById(R.id.webView);
        pb1 = (ProgressBar) findViewById(R.id.progressBar2);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        checkXieYi = (CheckBox) findViewById(R.id.checkXieYi);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
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
        String url;
        if (type == 1) {
            url = Constant.HOST + Constant.Url.STORE_SETTLEDBEFORE;
        } else {
            url = Constant.HOST + Constant.Url.ORDER_VIPBEFORE;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
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
                        selectValueBeanList = orderVipbefore.getSelectValue();
                        selectTitle = orderVipbefore.getSelectTitle();
                        mWebView.loadUrl(orderVipbefore.getUrl());
                        textViewTitle.setText(orderVipbefore.getUrlTitle());
                        String text1 = orderVipbefore.getText1();
                        String text2;
                        if (TextUtils.isEmpty(orderVipbefore.getText2())){
                             text2 = "";
                        }else {
                             text2 = "(" + orderVipbefore.getText2() + ")";
                        }
                        SpannableString span = new SpannableString(text1 + text2);
                        span.setSpan(new StrikethroughSpan(), text1.length(), (text1 + text2).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        span.setSpan(new RelativeSizeSpan(0.8f), text1.length(), (text1 + text2).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textText1.setText(span);
//                        showPaySDialog(orderVipbefore);
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
                if (checkXieYi.isChecked()) {
                    showVipBuyDialog();
//                    intent.setClass(this, TuiGuangZFActivity.class);
//                    intent.putExtra(Constant.INTENT_KEY.value, orderVipbefore);
//                    intent.putExtra(Constant.INTENT_KEY.type,type);
//                    startActivity(intent);
                } else {
                    MyDialog.showTipDialog(this, "请阅读并同意《精灵之泉推广商服务协议》");
                }
                break;
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }
    private void showPaySDialog(OrderVipbefore vipbefore){
        //添加到剪切板
        ClipboardManager clipboardManager =
                (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        /**之前的应用过期的方法，clipboardManager.setText(copy);*/
        assert clipboardManager != null;
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null,vipbefore.getWechatAccount()));
        if (clipboardManager.hasPrimaryClip()){
            clipboardManager.getPrimaryClip().getItemAt(0).getText();
        }

        final MaterialDialog dialog = new MaterialDialog.Builder(TuiGuangActivity.this)
                .customView(R.layout.dialog_pays, false)
                .show();
        View customeView = dialog.getCustomView();
        ImageView imageImg = (ImageView) customeView.findViewById(R.id.imageImg);
        TextView textAccount = (TextView) customeView.findViewById(R.id.textAccount);
        TextView textDes2 = (TextView) customeView.findViewById(R.id.textDes2);
        Button buttonNext = (Button) customeView.findViewById(R.id.buttonNext);
        textDes2.setText(vipbefore.getImgDes2());
        buttonNext.setText(vipbefore.getBtnTxt());
        GlideApp.with(TuiGuangActivity.this)
                .asBitmap()
                .load(vipbefore.getImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textAccount.setText(vipbefore.getImgDes());
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(TuiGuangActivity.this, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    /**
     * VIP购买dialog
     */
    private void showVipBuyDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialog_vip_buy = inflater.inflate(R.layout.dialog_vip_buy, null);
        TextView textTitle = (TextView) dialog_vip_buy.findViewById(R.id.textTitle);
        textTitle.setText(selectTitle);
        EasyRecyclerView recyclerView = (EasyRecyclerView) dialog_vip_buy.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        final RecyclerArrayAdapter<OrderVipbefore.SelectValueBean> adapter;
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<OrderVipbefore.SelectValueBean>(TuiGuangActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_vip_buy;
                return new VipBuyViewHolder(parent, layout);
            }
        });
        adapter.clear();
        adapter.addAll(selectValueBeanList);
        final AlertDialog alertDialog1 = new AlertDialog.Builder(this, R.style.dialog)
                .setView(dialog_vip_buy)
                .create();
        alertDialog1.show();
        dialog_vip_buy.findViewById(R.id.textCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1.dismiss();
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showLoadingDialog();
                String url = Constant.HOST + Constant.Url.CART_ADDCART;
                List<String> spe_name = new ArrayList<>();
                AddCar addCar = new AddCar(userInfo.getUid(), tokenTime, "1", String.valueOf(adapter.getItem(position).getId()), "1", spe_name);
                ApiClient.postJson(TuiGuangActivity.this, url, GsonUtils.parseObject(addCar), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        cancelLoadingDialog();
                        LogUtil.LogShitou("ChanPinXQActivity-购物车新增", s + "");
                        try {
                            CartAddcart cartAddcart = GsonUtils.parseJSON(s, CartAddcart.class);
                            if (cartAddcart.getStatus() == 1) {
                                alertDialog1.dismiss();
                                Intent intent = new Intent();
                                intent.setClass(TuiGuangActivity.this, QueRenDDActivity.class);
                                List<CartIndex.CartBean> cartBeanList = new ArrayList<CartIndex.CartBean>();
                                cartBeanList.add(new CartIndex.CartBean(cartAddcart.getCartId() + ""));
                                intent.putExtra(Constant.INTENT_KEY.value, new CartIndex(cartBeanList));
                                intent.putExtra(Constant.INTENT_KEY.type, 1);
                                startActivity(intent);
                            } else if (cartAddcart.getStatus() == 3) {
                                MyDialog.showReLoginDialog(TuiGuangActivity.this);
                            } else {
                                Toast.makeText(TuiGuangActivity.this, cartAddcart.getInfo(), Toast.LENGTH_SHORT).show();
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
        });
        Window dialogWindow = alertDialog1.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogFenXiang);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
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
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.PAY_SUCCESS);
        registerReceiver(recevier, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(recevier);
    }
}
