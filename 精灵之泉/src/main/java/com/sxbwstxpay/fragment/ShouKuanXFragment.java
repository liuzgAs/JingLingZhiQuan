package com.sxbwstxpay.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ShouKuanEWMActivity;
import com.sxbwstxpay.activity.WeiXinMPMaActivity;
import com.sxbwstxpay.activity.XuanZeTDActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseFragment;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ShouKuanXFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;
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

    public ShouKuanXFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_shou_kuanx, container, false);
            init();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mInflate.getParent();
        if (parent != null) {
            parent.removeView(mInflate);
        }
        return mInflate;
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void findID() {
        mRelaTitleStatue = mInflate.findViewById(R.id.relaTitleStatue);
        textAmount = (TextView) mInflate.findViewById(R.id.textAmount);
        tabView[0] = mInflate.findViewById(R.id.viewYinLian);
        tabView[1] = mInflate.findViewById(R.id.viewZhiFuBao);
        tabView[2] = mInflate.findViewById(R.id.viewWeiXin);
        viewTabBg = mInflate.findViewById(R.id.viewTabBg);
        viewShouKuan = mInflate.findViewById(R.id.viewShouKuan);
        imageBuShouKuan = (ImageView) mInflate.findViewById(R.id.imageBuShouKuan);
        viewJianPan = mInflate.findViewById(R.id.viewJianPan);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = mRelaTitleStatue.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
        mRelaTitleStatue.setLayoutParams(layoutParams);
        mRelaTitleStatue.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);
        ViewGroup.LayoutParams layoutParams1 = viewJianPan.getLayoutParams();
        layoutParams1.height = (int) (ScreenUtils.getScreenWidth(getActivity()) * 0.655f);
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
            mInflate.findViewById(textKeyId[i]).setOnClickListener(new View.OnClickListener() {
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
        mInflate.findViewById(R.id.textKeyDian).setOnClickListener(this);
        mInflate.findViewById(R.id.textKeyDelete).setOnClickListener(this);
        mInflate.findViewById(R.id.buttonShouKuan).setOnClickListener(this);
        mInflate.findViewById(R.id.viewShouKuanMa).setOnClickListener(this);
        mInflate.findViewById(R.id.textKeyDelete).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                amount = "";
                setAmount();
                return false;
            }
        });
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
        Intent intent=new Intent();
        switch (v.getId()) {
            default:
                break;
            case R.id.viewShouKuanMa:
//                if (checkMoney()) {
//                    return;
//                }
                intent.setClass(getActivity(),ShouKuanEWMActivity.class);
                intent.putExtra(Constant.INTENT_KEY.value,amount);
                startActivity(intent);
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
                            Toast.makeText(getActivity(), "后三位数不能相同", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), "后三位数不能相同", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                showLoadingDialog();
                ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        cancelLoadingDialog();
                        LogUtil.LogShitou("ShouKuanFragment--收款前请求", "" + s);
                        try {
                            orderReceiptbefore = GsonUtils.parseJSON(s, OrderReceiptbefore.class);
                            if (orderReceiptbefore.getStatus() == 1) {
                                if (amount.length() == 0) {
                                    Toast.makeText(getContext(), "请输入金额", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (amount.length() > 1) {
                                    if (TextUtils.equals(".", amount.substring(amount.length() - 1))) {
                                        amount = amount.substring(0, amount.length() - 1);
                                    }
                                }
                                if (Double.parseDouble(amount) > 1000000) {
                                    Toast.makeText(getActivity(), "最大金额不能超过100万", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                switch (type) {
                                    case 1:
                                        if (orderReceiptbefore.getRealStatus() == 0) {
                                            MyDialog.showTipDialog(getActivity(), orderReceiptbefore.getRealTips());
                                            return;
                                        } else {
                                            Intent intent = new Intent();
                                            intent.putExtra(Constant.INTENT_KEY.amount, amount);
                                            intent.setClass(getActivity(), XuanZeTDActivity.class);
                                            startActivity(intent);
                                            break;
                                        }
                                    case 2:
                                        if (orderReceiptbefore.getAlipayStatus() == 0) {
                                            MyDialog.showTipDialog(getActivity(), orderReceiptbefore.getAlipayTips());
                                            return;
                                        } else {
                                            toZhiFuBaoSK();
                                            break;
                                        }
                                    case 3:
                                        if (orderReceiptbefore.getWechatStatus() == 0) {
                                            MyDialog.showTipDialog(getActivity(), orderReceiptbefore.getWechatTips());
                                            return;
                                        } else {
                                            toWXShouKuan();
                                            break;
                                        }
                                }
                            } else if (orderReceiptbefore.getStatus() == 3) {
                                MyDialog.showReLoginDialog(getActivity());
                            } else {
                                Toast.makeText(getActivity(), orderReceiptbefore.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "数据出错", Toast.LENGTH_SHORT).show();
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
                        ApiClient.post(getActivity(), getOkObjectZFB(), new ApiClient.CallBack() {
                            @Override
                            public void onSuccess(String s) {
                                cancelLoadingDialog();
                                LogUtil.LogShitou("ShouKuanFragment--支付宝代收", s + "");
                                try {
                                    OrderWxPay orderWxPay = GsonUtils.parseJSON(s, OrderWxPay.class);
                                    if (orderWxPay.getStatus() == 1) {
                                        Intent intent = new Intent();
                                        intent.setClass(getContext(), WeiXinMPMaActivity.class);
                                        intent.putExtra(Constant.INTENT_KEY.TITLE, "支付宝代收");
                                        intent.putExtra(Constant.INTENT_KEY.img, orderWxPay.getImg());
                                        startActivity(intent);
                                    } else if (orderWxPay.getStatus() == 3) {
                                        MyDialog.showReLoginDialog(getActivity());
                                    } else {
                                        Toast.makeText(getActivity(), orderWxPay.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), "数据出错", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Response response) {
                                cancelLoadingDialog();
                                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
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
                        ApiClient.post(getActivity(), getOkObjectWX(), new ApiClient.CallBack() {
                            @Override
                            public void onSuccess(String s) {
                                cancelLoadingDialog();
                                LogUtil.LogShitou("ShouKuanFragment--微信代收", s + "");
                                try {
                                    OrderWxPay orderWxPay = GsonUtils.parseJSON(s, OrderWxPay.class);
                                    if (orderWxPay.getStatus() == 1) {
                                        Intent intent = new Intent();
                                        intent.setClass(getContext(), WeiXinMPMaActivity.class);
                                        intent.putExtra(Constant.INTENT_KEY.TITLE, "微信代收");
                                        intent.putExtra(Constant.INTENT_KEY.img, orderWxPay.getImg());
                                        startActivity(intent);
                                    } else if (orderWxPay.getStatus() == 3) {
                                        MyDialog.showReLoginDialog(getActivity());
                                    } else {
                                        Toast.makeText(getActivity(), orderWxPay.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), "数据出错", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Response response) {
                                cancelLoadingDialog();
                                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(Response response) {
                        cancelLoadingDialog();
                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    private void setAmount() {
        SpannableString span = new SpannableString("¥" + amount);
        span.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textAmount.setText(span);
    }

}
