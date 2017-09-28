package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ZhiFuActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.model.AliPayBean;
import com.sxbwstxpay.model.OrderPay;
import com.sxbwstxpay.util.GsonUtils;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ZhiFuViewHolder extends BaseViewHolder<OrderPay> {

    private final TextView textJinE;
    private final TextView textDes;
    private OrderPay data;
    final IWXAPI api = WXAPIFactory.createWXAPI(getContext(), null);
    private int payMode = 1;

    public ZhiFuViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textJinE = $(R.id.textJinE);
        textDes = $(R.id.textDes);
        $(R.id.buttonZhiFu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (payMode) {
                    case 1:
                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask alipay = new PayTask((ZhiFuActivity) getContext());
                                Map<String, String> stringMap = alipay.payV2(data.getPayAli(), true);
                                stringMap.get("result");
                                AliPayBean aliPayBean = GsonUtils.parseJSON(stringMap.get("result"), AliPayBean.class);
                                switch (aliPayBean.getAlipay_trade_app_pay_response().getCode()) {
                                    case 10000:
                                        ((ZhiFuActivity) getContext()).paySuccess();
                                        break;
                                    case 8000:
                                        ((ZhiFuActivity) getContext()).paySuccess();
                                        break;
                                    case 4000:
                                        MyDialog.showTipDialog(getContext(), "订单支付失败");
                                        break;
                                    case 5000:
                                        MyDialog.showTipDialog(getContext(), "重复请求");
                                        break;
                                    case 6001:
                                        MyDialog.showTipDialog(getContext(), "取消支付");
                                        break;
                                    case 6002:
                                        MyDialog.showTipDialog(getContext(), "网络连接错误");
                                        break;
                                    case 6004:
                                        MyDialog.showTipDialog(getContext(), "支付结果未知");
                                        break;
                                    default:
                                        MyDialog.showTipDialog(getContext(), "支付失败");
                                        break;
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
                }
            }
        });
        RadioGroup radioGroup = $(R.id.radioGroup);
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
                }
            }
        });
    }

    @Override
    public void setData(OrderPay data) {
        super.setData(data);
        this.data = data;
        textJinE.setText(data.getData().getOrderAmount());
        textDes.setText(data.getData().getDes());
    }

    /**
     * 微信支付
     */
    private void wechatPay() {
        if (!checkIsSupportedWeachatPay()) {
            Toast.makeText(getContext(), "您暂未安装微信或您的微信版本暂不支持支付功能\n请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
        } else {
            OrderPay.PayBean.ConfigBean config = data.getPay().getConfig();
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

}
