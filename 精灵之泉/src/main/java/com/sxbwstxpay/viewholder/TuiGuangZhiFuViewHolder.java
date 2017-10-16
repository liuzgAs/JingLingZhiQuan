package com.sxbwstxpay.viewholder;

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
import com.sxbwstxpay.activity.TuiGuangZFActivity;
import com.sxbwstxpay.activity.ZhiFuActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.AliPayBean;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.OrderVipbefore;
import com.sxbwstxpay.model.OrderVippay;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.Arith;
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
public class TuiGuangZhiFuViewHolder extends BaseViewHolder<OrderVipbefore> {

    private final TextView textJinE;
    private final TextView textDes;
    private OrderVipbefore data;
    final IWXAPI api = WXAPIFactory.createWXAPI(getContext(), null);
    private int payMode = 1;
    private final CheckBox checkZheKou;
    private final TextView textZheKou;

    public TuiGuangZhiFuViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textJinE = $(R.id.textJinE);
        textDes = $(R.id.textDes);
        checkZheKou = $(R.id.checkZheKou);
        checkZheKou.setChecked(true);
        textZheKou = $(R.id.textZheKou);
        $(R.id.buttonZhiFu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhiFu();
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
        checkZheKou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    LogUtil.LogShitou("TuiGuangZhiFuViewHolder--onCheckedChanged", Arith.sub(data.getAmount(), data.getCutAmount())+"");
                    textJinE.setText(Arith.sub(data.getAmount(), data.getCutAmount()) + "");
                } else {
                    LogUtil.LogShitou("TuiGuangZhiFuViewHolder--onCheckedChanged", data.getAmount()+"");
                    textJinE.setText(data.getAmount() + "");
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
        String url = Constant.HOST + Constant.Url.ORDER_VIPPAY;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", ((TuiGuangZFActivity) getContext()).userInfo.getUid());
        params.put("tokenTime", ((TuiGuangZFActivity) getContext()).tokenTime);
        if (checkZheKou.isChecked()) {
            params.put("isCut", "1");
        } else {
            params.put("isCut", "0");
        }
        return new OkObject(params, url);
    }

    private void zhiFu() {
        ((TuiGuangZFActivity) getContext()).showLoadingDialog();
        ApiClient.post(getContext(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                ((TuiGuangZFActivity) getContext()).cancelLoadingDialog();
                LogUtil.LogShitou("TuiGuangZhiFuViewHolder--vip支付", s + "");
                try {
                    OrderVippay orderVippay = GsonUtils.parseJSON(s, OrderVippay.class);
                    if (orderVippay.getStatus() == 1) {
                        if (payMode==1){
                            String payAli = orderVippay.getPayAli();
                            zhiFuBao(payAli);
                        }else {
                            OrderVippay.PayBean orderVippayPay = orderVippay.getPay();
                            wechatPay(orderVippayPay);
                        }
                    } else if (orderVippay.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getContext());
                    } else {
                        Toast.makeText(getContext(), orderVippay.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                ((TuiGuangZFActivity) getContext()).cancelLoadingDialog();
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void zhiFuBao(final String payAli) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    PayTask alipay = new PayTask((ZhiFuActivity) getContext());
                    Map<String, String> stringMap = alipay.payV2(payAli, true);
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
                } catch (Exception e) {
                }
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void setData(OrderVipbefore data) {
        super.setData(data);
        this.data = data;
        textJinE.setText(Arith.sub(data.getAmount(), data.getCutAmount()) + "");
        textZheKou.setText("使用实名认证奖励"+data.getCutAmount()+"元抵扣");
    }

    /**
     * 微信支付
     */
    private void wechatPay(OrderVippay.PayBean orderVippayPay) {
        if (!checkIsSupportedWeachatPay()) {
            Toast.makeText(getContext(), "您暂未安装微信或您的微信版本暂不支持支付功能\n请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
        } else {
            OrderVippay.PayBean.ConfigBean config = orderVippayPay.getConfig();
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
