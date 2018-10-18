package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.UserOrderinfo;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.DDXQFooteViewHolder;
import com.sxbwstxpay.viewholder.DingDanXQViewHolder;

import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class DingDanXQActivity extends ZjbBaseActivity implements View.OnClickListener {
    private View viewBar;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<UserOrderinfo.DataBean> adapter;
    private String id;
    private TextView textTip;
    private UserOrderinfo.AddressBean address;
    private List<UserOrderinfo.DesListBean> desList;
    private String sum;
    private String sumDes;
    private String details;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.SHUA_XIN_SHOW_HOU:
                    initData();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_dan_xq);
        init(DingDanXQActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        id = intent.getStringExtra(Constant.INTENT_KEY.id);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        textTip = (TextView) findViewById(R.id.textTip);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("订单详情");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        initRecycle();
    }

    private void initRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.marginTop), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        int red = getResources().getColor(R.color.basic_color);
        recyclerView.setRefreshingColor(red);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<UserOrderinfo.DataBean>(DingDanXQActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_dingdan_xiangqing;
                return new DingDanXQViewHolder(parent, layout);
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private TextView textAddress;
            private TextView textViewNamePhone;

            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(DingDanXQActivity.this).inflate(R.layout.header_dingdanxiangqing, null);
                textViewNamePhone = (TextView) view.findViewById(R.id.textViewNamePhone);
                textAddress = (TextView) view.findViewById(R.id.textAddress);
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                if (address != null) {
                    textViewNamePhone.setText(address.getConsignee() + "\u3000\u3000" + address.getPhone());
                    textAddress.setText(address.getAddress());
                }
            }
        });
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {

            private TextView textDetails;
            private TextView textSumDes;
            private TextView textSum;
            private RecyclerArrayAdapter<UserOrderinfo.DesListBean> adapterFoot;
            private EasyRecyclerView recyclerViewFoot;

            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(DingDanXQActivity.this).inflate(R.layout.footer_dingdanxiangqing, null);
                recyclerViewFoot = (EasyRecyclerView) view.findViewById(R.id.recyclerView);
                initRecyclerFoot();
                textSum = (TextView) view.findViewById(R.id.textSum);
                textSumDes = (TextView) view.findViewById(R.id.textSumDes);
                textDetails = (TextView) view.findViewById(R.id.textDetails);
                return view;
            }

            /**
             * 初始化recyclerview
             */
            private void initRecyclerFoot() {
                recyclerViewFoot.setLayoutManager(new LinearLayoutManager(DingDanXQActivity.this));
                recyclerViewFoot.setRefreshingColorResources(R.color.basic_color);
                recyclerViewFoot.setAdapterWithProgress(adapterFoot = new RecyclerArrayAdapter<UserOrderinfo.DesListBean>(DingDanXQActivity.this) {
                    @Override
                    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                        int layout = R.layout.item_dingdanxiangqing_foot;
                        return new DDXQFooteViewHolder(parent, layout);
                    }
                });
            }

            @Override
            public void onBindView(View headerView) {
                if (desList != null) {
                    adapterFoot.clear();
                    adapterFoot.addAll(desList);
                }
                textSum.setText(sum);
                textSumDes.setText(sumDes);
                textDetails.setText(details);
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.id,adapter.getItem(position).getGoods_id());
                intent.setClass(DingDanXQActivity.this, ChanPinXQActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
               startActivity(intent);
            }
        });
    }


    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_ORDERINFO;
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
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("", s);
                try {
                    UserOrderinfo userOrderinfo = GsonUtils.parseJSON(s, UserOrderinfo.class);
                    if (userOrderinfo.getStatus() == 1) {
                        address = userOrderinfo.getAddress();
                        desList = userOrderinfo.getDesList();
                        textTip.setText(userOrderinfo.getNotice());
                        sum = userOrderinfo.getSum();
                        sumDes = userOrderinfo.getSumDes();
                        details = userOrderinfo.getDetails();
                        List<UserOrderinfo.DataBean> dataBeanList = userOrderinfo.getData();
                        adapter.clear();
                        adapter.addAll(dataBeanList);
                    } else if (userOrderinfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(DingDanXQActivity.this);
                    } else {
                        showError(userOrderinfo.getInfo());
                    }
                } catch (Exception e) {
                    showError("数据出错");
                }
            }

            @Override
            public void onError(Response response) {
                showError("网络出错");
            }

            /**
             * 错误显示
             * @param msg
             */
            private void showError(String msg) {
                try {
                    View viewLoader = LayoutInflater.from(DingDanXQActivity.this).inflate(R.layout.view_loaderror, null);
                    TextView textMsg = (TextView) viewLoader.findViewById(R.id.textMsg);
                    textMsg.setText(msg);
                    viewLoader.findViewById(R.id.buttonReLoad).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recyclerView.showProgress();
                            initData();
                        }
                    });
                    recyclerView.setErrorView(viewLoader);
                    recyclerView.showError();
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        filter.addAction(Constant.BROADCASTCODE.SHUA_XIN_SHOW_HOU);
        registerReceiver(reciver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }
}
