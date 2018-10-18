package com.sxbwstxpay.viewholder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ZhiFuXActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.AliPayBean;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.Pay;
import com.sxbwstxpay.model.PayBefore;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ZhiFuXViewHolder extends BaseViewHolder<PayBefore> {

    private final TextView textJinE;
    private final TextView textDes;
    private final TextView textCreditPay;
    private final CheckBox radioCreditPay;
    private final View viewCreditPay;
    private String uid;
    private String tokenTime;
    private String oid;
    private int is_credit;
    private PayBefore data;
    final IWXAPI api = WXAPIFactory.createWXAPI(getContext(), null);
    private int payMode = 1;

    public ZhiFuXViewHolder(ViewGroup parent, @LayoutRes int res, String uid, String tokenTime, String oid) {
        super(parent, res);
        this.uid = uid;
        this.tokenTime = tokenTime;
        this.oid = oid;
        textJinE = $(R.id.textJinE);
        textDes = $(R.id.textDes);
        textCreditPay = $(R.id.textCreditPay);
        radioCreditPay = $(R.id.radioCreditPay);
        viewCreditPay = $(R.id.viewCreditPay);

        $(R.id.viewZheKou).setVisibility(View.GONE);
        $(R.id.buttonZhiFu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ZhiFuXActivity) getContext()).showLoadingDialog();
                ApiClient.post(getContext(), getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        ((ZhiFuXActivity) getContext()).cancelLoadingDialog();
                        LogUtil.LogShitou("支付界面", s);
                        try {
                            final Pay orderPay = GsonUtils.parseJSON(s, Pay.class);
                            if (orderPay.getStatus() == 1) {
                                if (orderPay.getPayState()==1){
                                    switch (payMode) {
                                        case 1:
                                            Runnable payRunnable = new Runnable() {

                                                @Override
                                                public void run() {
                                                    try {
                                                        PayTask alipay = new PayTask((ZhiFuXActivity) getContext());
                                                        Map<String, String> stringMap = alipay.payV2(orderPay.getPayAli(), true);
                                                        AliPayBean aliPayBean = GsonUtils.parseJSON(stringMap.get("result"), AliPayBean.class);
                                                        switch (aliPayBean.getAlipay_trade_app_pay_response().getCode()) {
                                                            case 10000:
                                                                ((ZhiFuXActivity) getContext()).paySuccess();
                                                                break;
                                                            case 8000:
                                                                ((ZhiFuXActivity) getContext()).paySuccess();
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
                                                    } catch (Exception e) {
                                                    }
                                                }
                                            };
                                            // 必须异步调用
                                            Thread payThread = new Thread(payRunnable);
                                            payThread.start();
                                            break;
                                        case 2:
                                            wechatPay(orderPay.getPay().getConfig());
                                            break;
                                    }
                                }else if (orderPay.getPayState()==2){
                                    Intent intent = new Intent(Constant.BROADCASTCODE.PAY_RECEIVER);
                                    intent.putExtra("error", 0);
                                    getContext().sendBroadcast(intent);
                                }
                            } else if (orderPay.getStatus() == 3) {
                                MyDialog.showReLoginDialog(getContext());
                            } else {
                                Toast.makeText(getContext(), orderPay.getInfo(), Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        Toast.makeText(getContext(), "网络出错", Toast.LENGTH_SHORT).show();
                    }

                });
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
                    case R.id.radioYinLian:
                        payMode = 3;
                        break;
                    case R.id.radioDongBaoBi:
                        payMode = 4;
                        break;
                }
            }
        });
        radioCreditPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textJinE.setText(String.valueOf(data.getCredit_after()));
                    is_credit=1;
                } else {
                    textJinE.setText(String.valueOf(data.getOrderAmount()));
                    is_credit=0;
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
        String url = Constant.HOST + Constant.Url.PAY;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("tokenTime", tokenTime);
        params.put("oid", oid + "");
        params.put("is_credit", is_credit + "");
        return new OkObject(params, url);
    }

    @Override
    public void setData(PayBefore data) {
        super.setData(data);
        this.data = data;
        if (data.getIs_credit() == 1) {
            viewCreditPay.setVisibility(View.VISIBLE);
            textCreditPay.setText(data.getCredit_pay());
        } else {
            viewCreditPay.setVisibility(View.GONE);
        }
        textJinE.setText(String.valueOf(data.getOrderAmount()));
//        textDes.setText(data.getDes());
    }

    /**
     * 微信支付
     */
    private void wechatPay(Pay.PayBean.ConfigBean config) {
        if (!checkIsSupportedWeachatPay()) {
            Toast.makeText(getContext(), "您暂未安装微信或您的微信版本暂不支持支付功能\n请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
        } else {
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
