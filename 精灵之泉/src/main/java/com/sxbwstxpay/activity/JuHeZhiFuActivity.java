package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.OrderReceiptbefore;
import com.sxbwstxpay.model.OrderWxPay;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class JuHeZhiFuActivity extends ZjbBaseActivity implements View.OnClickListener {
    private View mRelaTitleStatue;
    private TextView textAmount;
    private View[] tabView = new View[3];
    private View viewTabBg;
    private int[] textKeyId = new int[]{
            R.id.textKey00,
            R.id.textKey01,
            R.id.textKey02,
            R.id.textKey03,
            R.id.textKey04,
            R.id.textKey05,
            R.id.textKey06,
            R.id.textKey07,
            R.id.textKey08,
            R.id.textKey09,
    };
    private String amount = "";
    private OrderReceiptbefore orderReceiptbefore;
    private int type = 1;
    private View viewShouKuan;
    private ImageView imageBuShouKuan;
    private View viewJianPan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_he_zhi_fu);
        init(JuHeZhiFuActivity.class);
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void findID() {
        mRelaTitleStatue = findViewById(R.id.relaTitleStatue);
        textAmount = (TextView) findViewById(R.id.textAmount);
        tabView[0] = findViewById(R.id.viewYinLian);
        tabView[1] = findViewById(R.id.viewZhiFuBao);
        tabView[2] = findViewById(R.id.viewWeiXin);
        viewTabBg = findViewById(R.id.viewTabBg);
        viewShouKuan = findViewById(R.id.viewShouKuan);
        imageBuShouKuan = (ImageView) findViewById(R.id.imageBuShouKuan);
        viewJianPan = findViewById(R.id.viewJianPan);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = mRelaTitleStatue.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(JuHeZhiFuActivity.this));
        mRelaTitleStatue.setLayoutParams(layoutParams);
        mRelaTitleStatue.setPadding(0, ScreenUtils.getStatusBarHeight(JuHeZhiFuActivity.this), 0, 0);
        ViewGroup.LayoutParams layoutParams1 = viewJianPan.getLayoutParams();
        layoutParams1.height = (int) (ScreenUtils.getScreenWidth(JuHeZhiFuActivity.this) * 0.655f);
        viewJianPan.setLayoutParams(layoutParams1);
        SpannableString span = new SpannableString("¥");
        span.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textAmount.setText(span);
    }

    @Override
    protected void setListeners() {
        for (int i = 0; i < tabView.length; i++) {
            tabView[i].setOnClickListener(this);
        }
        for (int i = 0; i < textKeyId.length; i++) {
            final int finalI = i;
            findViewById(textKeyId[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (amount.length() < 9) {
                        amount = amount + finalI;
                        LogUtil.LogShitou("ShouKuanFragment--onClick", "");
                        checkAmount();
                    }
                }
            });
        }
        findViewById(R.id.textKeyDian).setOnClickListener(this);
        findViewById(R.id.textKeyDelete).setOnClickListener(this);
        findViewById(R.id.buttonShouKuan).setOnClickListener(this);
        findViewById(R.id.textKeyDelete).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                amount = "";
                setAmount();
                return false;
            }
        });
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    private void checkAmount() {
        if (StringUtil.isAmount(amount)) {
            setAmount();
        } else {
            amount = amount.substring(0, amount.length() - 1);
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.ORDER_RECEIPTBEFORE;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
            case R.id.textKeyDelete:
                if (amount.length() > 0) {
                    amount = amount.substring(0, amount.length() - 1);
                    setAmount();
                }
                break;
            case R.id.textKeyDian:
                if (amount.length() < 9 && StringUtil.isAmount(amount)) {
                    amount = amount + ".";
                    checkAmount();
                }
                break;
            case R.id.viewYinLian:
                type = 1;
//                imageBuShouKuan.setVisibility(View.GONE);
//                viewShouKuan.setVisibility(View.VISIBLE);
                viewTabBg.setBackgroundResource(R.mipmap.mingxitab1);
                break;
            case R.id.viewZhiFuBao:
                type = 2;
//                imageBuShouKuan.setVisibility(View.VISIBLE);
//                viewShouKuan.setVisibility(View.GONE);
                viewTabBg.setBackgroundResource(R.mipmap.mingxitab2);
                break;
            case R.id.viewWeiXin:
                type = 3;
//                imageBuShouKuan.setVisibility(View.VISIBLE);
//                viewShouKuan.setVisibility(View.GONE);
                viewTabBg.setBackgroundResource(R.mipmap.mingxitab3);
                break;
            case R.id.buttonShouKuan:
                if (amount.contains(".")) {
                    if (amount.length() >= 4) {
                        String amountSub;
                        LogUtil.LogShitou("ShouKuanFragment--onClick1", "" + amount.substring(amount.length() - 1));
                        LogUtil.LogShitou("ShouKuanFragment--onClick2", "" + amount.substring(amount.length() - 2));
                        if (TextUtils.equals(amount.substring(amount.length() - 2), "00")) {
                            amountSub = amount.substring(amount.length() - 6, amount.length() - 1);
                        } else if (TextUtils.equals(amount.substring(amount.length() - 1), "0")) {
                            amountSub = amount.substring(amount.length() - 5, amount.length() - 2);
                        } else {
                            amountSub = amount.substring(amount.length() - 4);
                        }
                        LogUtil.LogShitou("ShouKuanFragment--amountsubstring", "" + amountSub);
                        String[] split = amountSub.split("");
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < split.length; i++) {
                            if (!TextUtils.equals(".", split[i])) {
                                list.add(split[i]);
                            }
                        }
                        if (Integer.parseInt(list.get(1)) == Integer.parseInt(list.get(2))
                                && Integer.parseInt(list.get(1)) == Integer.parseInt(list.get(3))
                                && Integer.parseInt(list.get(2)) == Integer.parseInt(list.get(3))) {
                            Toast.makeText(JuHeZhiFuActivity.this, "后三位数不能相同", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                } else {
                    if (amount.length() >= 3) {
                        String amountSub = amount.substring(amount.length() - 3);
                        LogUtil.LogShitou("ShouKuanFragment--amountsubstring", "" + amountSub);
                        String[] split = amountSub.split("");
                        if (Integer.parseInt(split[1]) == Integer.parseInt(split[2])
                                && Integer.parseInt(split[1]) == Integer.parseInt(split[3])
                                && Integer.parseInt(split[2]) == Integer.parseInt(split[3])) {
                            Toast.makeText(JuHeZhiFuActivity.this, "后三位数不能相同", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                showLoadingDialog();
                ApiClient.post(JuHeZhiFuActivity.this, getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        cancelLoadingDialog();
                        LogUtil.LogShitou("ShouKuanFragment--收款前请求", "" + s);
                        try {
                            orderReceiptbefore = GsonUtils.parseJSON(s, OrderReceiptbefore.class);
                            if (orderReceiptbefore.getStatus() == 1) {
                                if (amount.length() == 0) {
                                    Toast.makeText(JuHeZhiFuActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (amount.length() > 1) {
                                    if (TextUtils.equals(".", amount.substring(amount.length() - 1))) {
                                        amount = amount.substring(0, amount.length() - 1);
                                    }
                                }
                                if (Double.parseDouble(amount) > 1000000) {
                                    Toast.makeText(JuHeZhiFuActivity.this, "最大金额不能超过100万", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                switch (type) {
                                    case 1:
                                        if (orderReceiptbefore.getRealStatus() == 0) {
                                            MyDialog.showTipDialog(JuHeZhiFuActivity.this, orderReceiptbefore.getRealTips());
                                            return;
                                        } else {
                                            Intent intent = new Intent();
                                            intent.putExtra(Constant.INTENT_KEY.amount, amount);
                                            intent.setClass(JuHeZhiFuActivity.this, XuanZeTDActivity.class);
                                            startActivity(intent);
                                            break;
                                        }
                                    case 2:
                                        if (orderReceiptbefore.getAlipayStatus() == 0) {
                                            MyDialog.showTipDialog(JuHeZhiFuActivity.this, orderReceiptbefore.getAlipayTips());
                                            return;
                                        } else {
                                            toZhiFuBaoSK();
                                            break;
                                        }
                                    case 3:
                                        if (orderReceiptbefore.getWechatStatus() == 0) {
                                            MyDialog.showTipDialog(JuHeZhiFuActivity.this, orderReceiptbefore.getWechatTips());
                                            return;
                                        } else {
                                            toWXShouKuan();
                                            break;
                                        }
                                    default:
                                        break;
                                }
                            } else if (orderReceiptbefore.getStatus() == 3) {
                                MyDialog.showReLoginDialog(JuHeZhiFuActivity.this);
                            } else {
                                Toast.makeText(JuHeZhiFuActivity.this, orderReceiptbefore.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(JuHeZhiFuActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    /**
                     * des： 网络请求参数
                     * author： ZhangJieBo
                     * date： 2017/8/28 0028 上午 9:55
                     */
                    private OkObject getOkObjectZFB() {
                        String url = Constant.HOST + Constant.Url.ORDER_ALIPAY;
                        HashMap<String, String> params = new HashMap<>();
                        params.put("uid", userInfo.getUid());
                        params.put("tokenTime", tokenTime);
                        return new OkObject(params, url);
                    }

                    private void toZhiFuBaoSK() {
                        showLoadingDialog();
                        ApiClient.post(JuHeZhiFuActivity.this, getOkObjectZFB(), new ApiClient.CallBack() {
                            @Override
                            public void onSuccess(String s) {
                                cancelLoadingDialog();
                                LogUtil.LogShitou("ShouKuanFragment--支付宝代收", s + "");
                                try {
                                    OrderWxPay orderWxPay = GsonUtils.parseJSON(s, OrderWxPay.class);
                                    if (orderWxPay.getStatus() == 1) {
                                        Intent intent = new Intent();
                                        intent.setClass(JuHeZhiFuActivity.this, WeiXinMPMaActivity.class);
                                        intent.putExtra(Constant.INTENT_KEY.TITLE, "支付宝代收");
                                        intent.putExtra(Constant.INTENT_KEY.img, orderWxPay.getImg());
                                        startActivity(intent);
                                    } else if (orderWxPay.getStatus() == 3) {
                                        MyDialog.showReLoginDialog(JuHeZhiFuActivity.this);
                                    } else {
                                        Toast.makeText(JuHeZhiFuActivity.this, orderWxPay.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(JuHeZhiFuActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Response response) {
                                cancelLoadingDialog();
                                Toast.makeText(JuHeZhiFuActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    /**
                     * des： 网络请求参数
                     * author： ZhangJieBo
                     * date： 2017/8/28 0028 上午 9:55
                     */
                    private OkObject getOkObjectWX() {
                        String url = Constant.HOST + Constant.Url.ORDER_WXPAY;
                        HashMap<String, String> params = new HashMap<>();
                        params.put("uid", userInfo.getUid());
                        params.put("tokenTime", tokenTime);
                        params.put("orderAmount", amount);
                        return new OkObject(params, url);
                    }

                    private void toWXShouKuan() {
                        showLoadingDialog();
                        ApiClient.post(JuHeZhiFuActivity.this, getOkObjectWX(), new ApiClient.CallBack() {
                            @Override
                            public void onSuccess(String s) {
                                cancelLoadingDialog();
                                LogUtil.LogShitou("ShouKuanFragment--微信代收", s + "");
                                try {
                                    OrderWxPay orderWxPay = GsonUtils.parseJSON(s, OrderWxPay.class);
                                    if (orderWxPay.getStatus() == 1) {
                                        Intent intent = new Intent();
                                        intent.setClass(JuHeZhiFuActivity.this, WeiXinMPMaActivity.class);
                                        intent.putExtra(Constant.INTENT_KEY.TITLE, "微信代收");
                                        intent.putExtra(Constant.INTENT_KEY.img, orderWxPay.getImg());
                                        startActivity(intent);
                                    } else if (orderWxPay.getStatus() == 3) {
                                        MyDialog.showReLoginDialog(JuHeZhiFuActivity.this);
                                    } else {
                                        Toast.makeText(JuHeZhiFuActivity.this, orderWxPay.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(JuHeZhiFuActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Response response) {
                                cancelLoadingDialog();
                                Toast.makeText(JuHeZhiFuActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(Response response) {
                        cancelLoadingDialog();
                        Toast.makeText(JuHeZhiFuActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                break;
        }
    }

    private void setAmount() {
        SpannableString span = new SpannableString("¥" + amount);
        span.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textAmount.setText(span);
    }
}
