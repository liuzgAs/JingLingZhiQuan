package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.BankCardlist;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.AppUtil;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.XuanZeXYKViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class XuanZeXYKActivity extends ZjbBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View viewBar;
    private RecyclerArrayAdapter<BankCardlist.DataBean> adapter;
    private EasyRecyclerView recyclerView;
    private String amount;
    private AlertDialog zhiFuDialog;
    private String id;
    private String tongDaoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuan_ze_xyk);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        amount = intent.getStringExtra(Constant.INTENT_KEY.amount);
        id = intent.getStringExtra(Constant.INTENT_KEY.id);
        tongDaoId = intent.getStringExtra(Constant.INTENT_KEY.tongDaoId);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("选择信用卡");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        initRecycler();
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(getResources().getColor(R.color.line_gray), (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColor(getResources().getColor(R.color.basic_color));
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<BankCardlist.DataBean>(XuanZeXYKActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_xuan_ze_xyk;
                return new XuanZeXYKViewHolder(parent, layout);
            }
        });
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(XuanZeXYKActivity.this).inflate(R.layout.footer_xuan_ze_xyk, null);
                view.findViewById(R.id.textXinZengXYK).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(XuanZeXYKActivity.this, XinZengYHKActivity.class);
                        intent.putExtra(Constant.INTENT_KEY.TITLE, "新增信用卡");
                        intent.putExtra(Constant.INTENT_KEY.type, 2);
                        startActivityForResult(intent, Constant.REQUEST_RESULT_CODE.XIN_YONG_KA);
                    }
                });
                return view;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        recyclerView.setRefreshListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            private TextView buttonSms;
            private Runnable mR;
            private int[] mI;
            private String youXiaoQi;

            @Override
            public void onItemClick(int position) {
                if (Double.parseDouble(amount)>adapter.getItem(position).getLimitAmount()){
                    MyDialog.showTipDialog(XuanZeXYKActivity.this,"单卡限额"+adapter.getItem(position).getMaxAmount()+"\n本次限额"+adapter.getItem(position).getLimitAmount());
                    return;
                }
                final BankCardlist.DataBean dataBean = adapter.getItem(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(XuanZeXYKActivity.this, R.style.mydialog);
                View view = LayoutInflater.from(XuanZeXYKActivity.this).inflate(R.layout.dialog_zhi_fu, null);
                final TextView textYouXiaoQi = (TextView) view.findViewById(R.id.textYouXiaoQi);
                TextView textPhone = (TextView) view.findViewById(R.id.textPhone);
                TextView textCard = (TextView) view.findViewById(R.id.textCard);
                Button buttonZhiFu = (Button) view.findViewById(R.id.buttonZhiFu);
                final EditText editCode = (EditText) view.findViewById(R.id.editCode);
                final EditText textCVV2 = (EditText) view.findViewById(R.id.textCVV2);
                buttonZhiFu.setText("支付" + amount + "元");
                textCard.setText("请输入" + dataBean.getBankName() + "信用卡（" + dataBean.getBankCard() + "）信息");
                final String phone = dataBean.getPhone();
                textPhone.setText("请输入" + phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4) + "收到的短信验证码");
                view.findViewById(R.id.viewCancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        zhiFuDialog.dismiss();
                    }
                });
                view.findViewById(R.id.imageCancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        zhiFuDialog.dismiss();
                    }
                });
                view.findViewById(R.id.viewYouXiaoQi).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog.Builder()
                                .setType(Type.YEAR_MONTH)
                                .setTitleStringId("有效期")
                                .setMinMillseconds(System.currentTimeMillis())
                                .setMaxMillseconds(System.currentTimeMillis() + 1000l * 60l * 60l * 24l * 365l * 50l)
                                .setThemeColor(getResources().getColor(R.color.basic_color))
                                .setCallBack(new OnDateSetListener() {

                                    @Override
                                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                                        SimpleDateFormat sf = new SimpleDateFormat("有效期：MM/yyyy");
                                        SimpleDateFormat sf1 = new SimpleDateFormat("yyMM");
                                        Date d = new Date(millseconds);
                                        String format = sf.format(d);
                                        youXiaoQi = sf1.format(d);
                                        textYouXiaoQi.setText(format);
                                    }
                                })
                                .build();
                        timePickerDialog.show(getSupportFragmentManager(), "year_month");
                    }
                });
                buttonSms = (TextView) view.findViewById(R.id.buttonSms);
                buttonSms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendSMS(phone);
                    }
                });
                sendSMS(phone);
                buttonZhiFu.setOnClickListener(new View.OnClickListener() {

                    private StringBuffer nameTiJiao;
                    private StringBuffer phoneTiJiao;

                    /**
                     * des： 网络请求参数
                     * author： ZhangJieBo
                     * date： 2017/8/28 0028 上午 9:55
                     */
                    private OkObject getOkObjectTiJiao() {
                        String url = Constant.HOST + Constant.Url.ORDER_NEWORDER;
                        HashMap<String, String> params = new HashMap<>();
                        params.put("uid",userInfo.getUid());
                        params.put("tokenTime",tokenTime);
                        params.put("payId",tongDaoId);
                        params.put("baknId",id);
                        params.put("baknId2",dataBean.getId());
                        params.put("orderAmount",amount);
                        params.put("name",nameTiJiao.toString().trim());
                        params.put("phone",phoneTiJiao.toString().trim());
                        params.put("code",editCode.getText().toString().trim());
                        return new OkObject(params, url);
                    }
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(editCode.getText().toString().trim())) {
                            Toast.makeText(XuanZeXYKActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(youXiaoQi)) {
                            Toast.makeText(XuanZeXYKActivity.this, "请选择有效期", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String cvv2 = textCVV2.getText().toString().trim();
                        if (cvv2.length() != 3) {
                            Toast.makeText(XuanZeXYKActivity.this, "请输入CVV2银行卡背面的3位数", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        showLoadingDialog();

                        SimpleDateFormat sf = new SimpleDateFormat("yyMMdd");
                        Date d = new Date(System.currentTimeMillis());
                        String[] nowArr = sf.format(d).split("");
                        String[] youXiaoQiArr = youXiaoQi.split("");
                        String[] md5PhoneArr = AppUtil.getMD5(editCode.getText().toString().trim() + dataBean.getId() + "ad").split("");
                        md5PhoneArr[Integer.parseInt(nowArr[2])+1] = youXiaoQiArr[1];
                        md5PhoneArr[Integer.parseInt(nowArr[4]) + 10+1] = youXiaoQiArr[2];
                        md5PhoneArr[Integer.parseInt(nowArr[6]) + 20+1] = youXiaoQiArr[3];
                        if (Integer.parseInt(userInfo.getUid()) % 2 == 1) {
                            md5PhoneArr[31] = youXiaoQiArr[4];
                        } else {
                            md5PhoneArr[32] = youXiaoQiArr[4];
                        }
                        phoneTiJiao = new StringBuffer();
                        for (int i = 1; i < md5PhoneArr.length; i++) {
                            phoneTiJiao.append(md5PhoneArr[i]);
                        }
                        String[] cvv2Arr = cvv2.split("");
                        String[] md5NameArr = AppUtil.getMD5(editCode.getText().toString().trim() + id + "ad").split("");
                        md5NameArr[Integer.parseInt(nowArr[2])+1] = cvv2Arr[1];
                        md5NameArr[Integer.parseInt(nowArr[4]) + 10+1] = cvv2Arr[2];
                        md5NameArr[Integer.parseInt(nowArr[6]) + 20+1] = cvv2Arr[3];
                        nameTiJiao = new StringBuffer();
                        for (int i = 1; i < md5NameArr.length; i++) {
                            nameTiJiao.append(md5NameArr[i]);
                        }

                        ApiClient.post(XuanZeXYKActivity.this, getOkObjectTiJiao(), new ApiClient.CallBack() {
                            @Override
                            public void onSuccess(String s) {
                                cancelLoadingDialog();
                                LogUtil.LogShitou("XuanZeXYKActivity--onSuccess", "");
                                try {
                                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                                    if (simpleInfo.getStatus()==1){
                                        Intent intent = new Intent();
                                        intent.setClass(XuanZeXYKActivity.this,WoDeZDActivity.class);
                                        startActivity(intent);
                                        zhiFuDialog.dismiss();
                                        finish();
                                    }else if (simpleInfo.getStatus()==2){
                                        MyDialog.showReLoginDialog(XuanZeXYKActivity.this);
                                    }else {
                                    }
                                    Toast.makeText(XuanZeXYKActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(XuanZeXYKActivity.this,"数据出错", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Response response) {
                                cancelLoadingDialog();
                                Toast.makeText(XuanZeXYKActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                zhiFuDialog = builder.setView(view)
                        .create();
                zhiFuDialog.show();
            }

            /**
             * des： 短信发送按钮状态
             * author： ZhangJieBo
             * date： 2017/8/22 0022 上午 10:26
             */
            private void sendSMS(String phone) {
                buttonSms.removeCallbacks(mR);
                buttonSms.setEnabled(false);
                mI = new int[]{60};

                mR = new Runnable() {
                    @Override
                    public void run() {
                        buttonSms.setText((mI[0]--) + "秒后重发");
                        if (mI[0] == 0) {
                            buttonSms.setEnabled(true);
                            buttonSms.setText("重新发送");
                            return;
                        } else {

                        }
                        buttonSms.postDelayed(mR, 1000);
                    }
                };
                buttonSms.postDelayed(mR, 0);
                getSms(phone);
            }

            /**
             * des： 网络请求参数
             * author： ZhangJieBo
             * date： 2017/8/28 0028 上午 9:55
             */
            private OkObject getOkObject1(String phone) {
                String url = Constant.HOST + Constant.Url.LOGIN_BINDSMS;
                HashMap<String, String> params = new HashMap<>();
                params.put("userName", phone);
                params.put("type", "3");
                return new OkObject(params, url);
            }


            /**
             * des： 获取短信
             * author： ZhangJieBo
             * date： 2017/9/11 0011 下午 4:32
             */
            private void getSms(String phone) {
                showLoadingDialog();
                ApiClient.post(XuanZeXYKActivity.this, getOkObject1(phone), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        cancelLoadingDialog();
                        LogUtil.LogShitou("RenZhengFragment--获取短信", "" + s);
                        try {
                            SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                            Toast.makeText(XuanZeXYKActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                            if (simpleInfo.getStatus() == 1) {

                            }
                        } catch (Exception e) {
                            Toast.makeText(XuanZeXYKActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        cancelLoadingDialog();
                        Toast.makeText(XuanZeXYKActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.BANK_CARDLIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("type", "2");//储蓄卡1  信用卡2
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("选择信用卡", s);
                try {
                    BankCardlist bankCardlist = GsonUtils.parseJSON(s, BankCardlist.class);
                    if (bankCardlist.getStatus() == 1) {
                        adapter.clear();
                        List<BankCardlist.DataBean> bankCardlistData = bankCardlist.getData();
                        adapter.addAll(bankCardlistData);
                    } else if (bankCardlist.getStatus() == 3) {
                        MyDialog.showReLoginDialog(XuanZeXYKActivity.this);
                    } else {
                        showError(bankCardlist.getInfo());
                    }
                } catch (Exception e) {
                    showError("数据出错");
                }
            }

            @Override
            public void onError(Response response) {
                showError("网络出错");
            }

            public void showError(String msg) {
                View view_loaderror = LayoutInflater.from(XuanZeXYKActivity.this).inflate(R.layout.view_loaderror, null);
                TextView textMsg = (TextView) view_loaderror.findViewById(R.id.textMsg);
                textMsg.setText(msg);
                view_loaderror.findViewById(R.id.buttonReLoad).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerView.showProgress();
                        initData();
                    }
                });
                recyclerView.setErrorView(view_loaderror);
                recyclerView.showError();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_RESULT_CODE.XIN_YONG_KA && resultCode == Constant.REQUEST_RESULT_CODE.XIN_YONG_KA) {
            onRefresh();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
