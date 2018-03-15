package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.ChaKanMingXi;
import com.sxbwstxpay.model.HkIndex;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.RiQi;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.MoneyInputFilter;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.ItemHuanKuanRQViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class HuanKuanJiHuaActivity extends ZjbBaseActivity implements View.OnClickListener {

    private String payment;
    private View viewBar;
    private TextView textDay1;
    private TextView textDay2;
    private List<RiQi> riQiList = new ArrayList<>();
    private TextView textHuanKuanRiQi;
    private String days = "";
    private int num;
    private EditText textNum;
    private EditText editHuanKuanJinE;
    private String id;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.zhiFuGuanBi:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    private int dayNum;
    private TextView textTips;
    private String url;
    private String tips;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huan_kuan_ji_hua);
        init(HuanKuanJiHuaActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        payment = intent.getStringExtra(Constant.INTENT_KEY.id);
        id = intent.getStringExtra(Constant.INTENT_KEY.value);
    }

    @Override
    protected void findID() {
        textDay1 = (TextView) findViewById(R.id.textDay1);
        textDay2 = (TextView) findViewById(R.id.textDay2);
        viewBar = findViewById(R.id.viewBar);
        textHuanKuanRiQi = (TextView) findViewById(R.id.textHuanKuanRiQi);
        textNum = (EditText) findViewById(R.id.textNum);
        editHuanKuanJinE = (EditText) findViewById(R.id.editHuanKuanJinE);
        textTips = (TextView) findViewById(R.id.textTips);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("还款计划");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        MoneyInputFilter.init(editHuanKuanJinE);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.viewHuanKuanRiQi).setOnClickListener(this);
        findViewById(R.id.btnChaKanMingXi).setOnClickListener(this);
        textTips.setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.HK_INDEX;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("id", id);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(HuanKuanJiHuaActivity.this, getOkObject(), new ApiClient.CallBack() {


            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("HuanKuanJiHuaActivity--onSuccess", s + "");
                try {
                    HkIndex hkIndex = GsonUtils.parseJSON(s, HkIndex.class);
                    if (hkIndex.getStatus() == 1) {
                        textDay1.setText(hkIndex.getDay1());
                        textDay2.setText(hkIndex.getDay2());
                        dayNum = hkIndex.getDayNum();
                        if (hkIndex.getIsEdit() == 1) {
                            textNum.setEnabled(true);
                        } else {
                            textNum.setEnabled(false);
                        }
                        tips = hkIndex.getTips();
                        url = hkIndex.getUrl();
                        List<String> hkIndexCen = hkIndex.getCen();
                        riQiList.clear();
                        for (int i = 0; i < hkIndexCen.size(); i++) {
                            RiQi riQi = new RiQi(hkIndexCen.get(i), false);
                            riQiList.add(riQi);
                        }
                    } else if (hkIndex.getStatus() == 3) {
                        MyDialog.showReLoginDialog(HuanKuanJiHuaActivity.this);
                    } else {
                        Toast.makeText(HuanKuanJiHuaActivity.this, hkIndex.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(HuanKuanJiHuaActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(HuanKuanJiHuaActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textTips:
                if (!TextUtils.isEmpty(url)) {
                    Intent intent = new Intent();
                    intent.setClass(HuanKuanJiHuaActivity.this, WebActivity.class);
                    intent.putExtra(Constant.INTENT_KEY.TITLE,tips);
                    intent.putExtra(Constant.INTENT_KEY.URL,url);
                    startActivity(intent);
                }
                break;
            case R.id.btnChaKanMingXi:
                if (TextUtils.isEmpty(days)) {
                    Toast.makeText(HuanKuanJiHuaActivity.this, "请选择还款日期", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editHuanKuanJinE.getText().toString().trim())) {
                    Toast.makeText(HuanKuanJiHuaActivity.this, "请输入还款金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                ChaKanMingXi chaKanMingXi = new ChaKanMingXi(userInfo.getUid(), editHuanKuanJinE.getText().toString().trim(), id, days, num, payment);
                Intent intent = new Intent();
                intent.setClass(this, HuanKuanMXActivity.class);
                intent.putExtra(Constant.INTENT_KEY.value, chaKanMingXi);
                startActivity(intent);
                break;
            case R.id.viewHuanKuanRiQi:
                View dialog_tu_pian = LayoutInflater.from(this).inflate(R.layout.dialog_huankuan_jihua, null);
                EasyRecyclerView recyclerViewDialog = (EasyRecyclerView) dialog_tu_pian.findViewById(R.id.recyclerView);
                final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.dialog)
                        .setView(dialog_tu_pian)
                        .create();
                alertDialog.show();
                Window dialogWindow = alertDialog.getWindow();
                dialogWindow.setGravity(Gravity.BOTTOM);
                dialogWindow.setWindowAnimations(R.style.dialogFenXiang);
                GridLayoutManager manager = new GridLayoutManager(this, 7);
                recyclerViewDialog.setLayoutManager(manager);
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                DisplayMetrics d = getResources().getDisplayMetrics(); // 获取屏幕宽、高用
                lp.width = (int) (d.widthPixels * 1); // 高度设置为屏幕的0.6
                dialogWindow.setAttributes(lp);
                SpaceDecoration spaceDecoration = new SpaceDecoration((int) DpUtils.convertDpToPixel(1f, this));
                recyclerViewDialog.addItemDecoration(spaceDecoration);
                recyclerViewDialog.setRefreshingColorResources(R.color.basic_color);
                final RecyclerArrayAdapter<RiQi> adapter;
                recyclerViewDialog.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<RiQi>(HuanKuanJiHuaActivity.this) {
                    @Override
                    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                        int layout = R.layout.item_huankuanriqi;
                        return new ItemHuanKuanRQViewHolder(parent, layout);
                    }
                });
                manager.setSpanSizeLookup(adapter.obtainGridSpanSizeLookUp(7));
                adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
                    @Override
                    public View onCreateView(ViewGroup parent) {
                        View view1 = LayoutInflater.from(HuanKuanJiHuaActivity.this).inflate(R.layout.header_huankuanriqi, null);
                        return view1;
                    }

                    @Override
                    public void onBindView(View headerView) {

                    }
                });
                adapter.addFooter(new RecyclerArrayAdapter.ItemView() {
                    @Override
                    public View onCreateView(ViewGroup parent) {
                        View view1 = LayoutInflater.from(HuanKuanJiHuaActivity.this).inflate(R.layout.footer_huankuanjihua, null);
                        view1.findViewById(R.id.textQueRen).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                                days = "";
                                num = 0;
                                for (int i = 0; i < adapter.getAllData().size(); i++) {
                                    if (adapter.getItem(i).isSelect()) {
                                        days = days + adapter.getItem(i).getRiQi() + ",";
                                        num++;
                                    }
                                }
                                num = num * dayNum;
                                if (num > 20) {
                                    num = 20;
                                }
                                days = days.substring(0, days.length() - 1);
                                textHuanKuanRiQi.setText(days);
                                textNum.setText(String.valueOf(num));
                            }
                        });
                        view1.findViewById(R.id.textQuXiao).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                        return view1;
                    }

                    @Override
                    public void onBindView(View headerView) {

                    }
                });
                adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (adapter.getItem(position).isSelect()) {
                            adapter.getItem(position).setSelect(false);
                        } else {
                            adapter.getItem(position).setSelect(true);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                adapter.clear();
                adapter.addAll(riQiList);
                break;
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.zhiFuGuanBi);
        registerReceiver(reciver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }
}
