package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.AliPayBean;
import com.sxbwstxpay.model.HkConfirm;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.OrderPay;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class ZhiFuZXJinEActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private EasyRecyclerView recyclerView;
    private String detailsId;
    private TextView textZongJinE;
    private TextView textZhiFuSXF;
    private TextView textJieSuanSXF;
    private TextView textHeJi;
    private TextView textCreditDes;
    final IWXAPI api = WXAPIFactory.createWXAPI(this, null);
    private int payMode = 1;
    private String oid;
    private BroadcastReceiver recevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.PAY_RECEIVER:
                    cancelLoadingDialog();
                    int error = intent.getIntExtra("error", -1);
                    if (error == 0) {
                        paySuccess();
                    } else if (error == -1) {
                        MyDialog.showTipDialog(ZhiFuZXJinEActivity.this, "支付失败");
                    } else if (error == -2) {
                        MyDialog.showTipDialog(ZhiFuZXJinEActivity.this, "支付失败");
                    }
                    break;
                case Constant.BROADCASTCODE.zhiFuGuanBi:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    private OrderPay orderPay;

    /**
     * des： 支付成功提示
     * author： ZhangJieBo
     * date： 2017/7/6 0006 下午 2:34
     */
    public void paySuccess() {
        Intent intent1 = new Intent();
        intent1.setAction(Constant.BROADCASTCODE.zhiFuGuanBi);
        sendBroadcast(intent1);
        finish();
        Intent intent = new Intent();
        intent.putExtra(Constant.INTENT_KEY.id, oid);
        intent.setClass(this, ZhiFuCGActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_fu_zxjin_e);
        init(ZhiFuZXJinEActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        detailsId = intent.getStringExtra(Constant.INTENT_KEY.id);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        textZongJinE = (TextView) findViewById(R.id.textZongJinE);
        textZhiFuSXF = (TextView) findViewById(R.id.textZhiFuSXF);
        textJieSuanSXF = (TextView) findViewById(R.id.textJieSuanSXF);
        textHeJi = (TextView) findViewById(R.id.textHeJi);
        textCreditDes = (TextView) findViewById(R.id.textCreditDes);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("支付执行金额");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.btnZhiFu).setOnClickListener(this);
        findViewById(R.id.imageBack).setOnClickListener(this);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioZhiFuBao:
                        payMode = 1;
                        break;
                    case R.id.radioWeiXin:
                        payMode = 2;
                        break;
                    case R.id.radioYinLian:
                        payMode = 3;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.HK_CONFIRM;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("detailsId", detailsId);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(ZhiFuZXJinEActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ZhiFuZXJinEActivity--onSuccess", s + "");
                try {
                    HkConfirm hkConfirm = GsonUtils.parseJSON(s, HkConfirm.class);
                    if (hkConfirm.getStatus() == 1) {
                        List<HkConfirm.DataBean> dataBeanList = hkConfirm.getData();
                        textZongJinE.setText(dataBeanList.get(0).getV());
                        textZhiFuSXF.setText(dataBeanList.get(1).getV());
                        textJieSuanSXF.setText(dataBeanList.get(2).getV());
                        textHeJi.setText("¥" + hkConfirm.getSum());
                        textCreditDes.setText(hkConfirm.getCreditDes());
                        oid = hkConfirm.getOid();
                    } else if (hkConfirm.getStatus() == 3) {
                        MyDialog.showReLoginDialog(ZhiFuZXJinEActivity.this);
                    } else {
                        Toast.makeText(ZhiFuZXJinEActivity.this, hkConfirm.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ZhiFuZXJinEActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(ZhiFuZXJinEActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkZFObject() {
        String url = Constant.HOST + Constant.Url.ORDER_PAY;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("oid", oid);
        if (payMode == 3) {
            params.put("isThis", "1");
        }
        return new OkObject(params, url);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnZhiFu:
                showLoadingDialog();
                ApiClient.post(ZhiFuZXJinEActivity.this, getOkZFObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        cancelLoadingDialog();
                        LogUtil.LogShitou("ZhiFuZXJinEActivity--onSuccess", s + "");
                        try {
                            orderPay = GsonUtils.parseJSON(s, OrderPay.class);
                            if (orderPay.getStatus() == 1) {
                                switch (payMode) {
                                    case 1:
                                        Runnable payRunnable = new Runnable() {

                                            @Override
                                            public void run() {
                                                try {
                                                    PayTask alipay = new PayTask(ZhiFuZXJinEActivity.this);
                                                    Map<String, String> stringMap = alipay.payV2(orderPay.getPayAli(), true);
                                                    AliPayBean aliPayBean = GsonUtils.parseJSON(stringMap.get("result"), AliPayBean.class);
                                                    switch (aliPayBean.getAlipay_trade_app_pay_response().getCode()) {
                                                        case 10000:
                                                            paySuccess();
                                                            break;
                                                        case 8000:
                                                            paySuccess();
                                                            break;
                                                        case 4000:
                                                            MyDialog.showTipDialog(ZhiFuZXJinEActivity.this, "订单支付失败");
                                                            break;
                                                        case 5000:
                                                            MyDialog.showTipDialog(ZhiFuZXJinEActivity.this, "重复请求");
                                                            break;
                                                        case 6001:
                                                            MyDialog.showTipDialog(ZhiFuZXJinEActivity.this, "取消支付");
                                                            break;
                                                        case 6002:
                                                            MyDialog.showTipDialog(ZhiFuZXJinEActivity.this, "网络连接错误");
                                                            break;
                                                        case 6004:
                                                            MyDialog.showTipDialog(ZhiFuZXJinEActivity.this, "支付结果未知");
                                                            break;
                                                        default:
                                                            MyDialog.showTipDialog(ZhiFuZXJinEActivity.this, "支付失败");
                                                            break;
                                                    }
                                                } catch (Exception e) {
                                                }
                                            }
                                        };
                                        // 必须异步调用
                                        Thread payThread = new Thread(payRunnable);
                                        payThread.start();
                                        break;
                                    case 2:
                                        wechatPay();
                                        break;
                                    case 3:
                                        paySuccess();
                                        break;
                                    default:
                                        break;
                                }
                            } else if (orderPay.getStatus() == 3) {
                                MyDialog.showReLoginDialog(ZhiFuZXJinEActivity.this);
                            } else {
                                Toast.makeText(ZhiFuZXJinEActivity.this, orderPay.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(ZhiFuZXJinEActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        cancelLoadingDialog();
                        Toast.makeText(ZhiFuZXJinEActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 微信支付
     */
    private void wechatPay() {
        if (!checkIsSupportedWeachatPay()) {
            Toast.makeText(ZhiFuZXJinEActivity.this, "您暂未安装微信或您的微信版本暂不支持支付功能\n请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
        } else {
            OrderPay.PayBean.ConfigBean config = orderPay.getPay().getConfig();
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

    /**
     * 检查微信版本是否支付支付或是否安装可支付的微信版本
     */
    private boolean checkIsSupportedWeachatPay() {
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        return isPaySupported;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.PAY_RECEIVER);
        filter.addAction(Constant.BROADCASTCODE.zhiFuGuanBi);
        registerReceiver(recevier, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(recevier);
    }

}
