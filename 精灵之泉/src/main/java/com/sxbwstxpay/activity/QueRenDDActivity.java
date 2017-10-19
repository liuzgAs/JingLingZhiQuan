package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.CartIndex;
import com.sxbwstxpay.model.CartNeworder;
import com.sxbwstxpay.model.CartNeworderUpload;
import com.sxbwstxpay.model.CartOrder;
import com.sxbwstxpay.model.CartOrderUpload;
import com.sxbwstxpay.model.UserAddress;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.QueRenDDViewHolder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class QueRenDDActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<CartOrder.CartBean> adapter;
    private CartIndex cartIndex;
    private CartOrder.AdBean cartOrderAd;
    private View viewTiJiao;
    private CartOrderUpload cartOrderUpload;
    private CartOrder cartOrder;
    private EditText editPayMsg;
    private TextView textSum;
    private TextView textYunFei;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.zhiFuGuanBi:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_ren_dd);
        init(QueRenDDActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        cartIndex = (CartIndex) intent.getSerializableExtra(Constant.INTENT_KEY.value);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        viewTiJiao = findViewById(R.id.viewTiJiao);
        textSum = (TextView) findViewById(R.id.textSum);
        textYunFei = (TextView) findViewById(R.id.textYunFei);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("确认订单");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        viewTiJiao.setVisibility(View.GONE);
        initRecycle();
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.buttonTiJiao).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    private void initRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        int red = getResources().getColor(R.color.basic_color);
        recyclerView.setRefreshingColor(red);
        recyclerView.getSwipeToRefresh().setProgressViewOffset(true, 30, 220);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<CartOrder.CartBean>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.view_dingdan;
                return new QueRenDDViewHolder(parent, layout);
            }

        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private TextView textAreaAddress;
            private TextView textPhone;
            private TextView textConsignee;
            private TextView textAdd;
            private View viewXuanZeSHDZ;

            @Override
            public View onCreateView(ViewGroup parent) {
                View header_queren_dd = LayoutInflater.from(QueRenDDActivity.this).inflate(R.layout.header_queren_dd, null);
                viewXuanZeSHDZ = header_queren_dd.findViewById(R.id.viewXuanZeSHDZ);
                textAdd = (TextView) header_queren_dd.findViewById(R.id.textAdd);
                textConsignee = (TextView) header_queren_dd.findViewById(R.id.textConsignee);
                textPhone = (TextView) header_queren_dd.findViewById(R.id.textPhone);
                textAreaAddress = (TextView) header_queren_dd.findViewById(R.id.textAreaAddress);
                viewXuanZeSHDZ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(QueRenDDActivity.this, XuanZeSHDZActivity.class);
                        startActivityForResult(intent, Constant.REQUEST_RESULT_CODE.address);
                    }
                });
                textAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(QueRenDDActivity.this, XuanZeSHDZActivity.class);
                        startActivityForResult(intent, Constant.REQUEST_RESULT_CODE.address);
                    }
                });
                return header_queren_dd;
            }

            @Override
            public void onBindView(View headerView) {
                if (cartOrderAd != null) {
                    viewXuanZeSHDZ.setVisibility(View.VISIBLE);
                    textAdd.setVisibility(View.GONE);
                    textConsignee.setText("收件人：" + cartOrderAd.getConsignee());
                    textPhone.setText(cartOrderAd.getPhone());
                    textAreaAddress.setText("收货地址：" + cartOrderAd.getArea() + "-" + cartOrderAd.getAddress());
                } else {
                    viewXuanZeSHDZ.setVisibility(View.GONE);
                    textAdd.setVisibility(View.VISIBLE);
                }
            }
        });
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {

            private TextView textGoods_money;
            private TextView textSum;

            @Override
            public View onCreateView(ViewGroup parent) {
                View item_queren_dd = LayoutInflater.from(QueRenDDActivity.this).inflate(R.layout.item_queren_dd, null);
                editPayMsg = (EditText) item_queren_dd.findViewById(R.id.editPayMsg);
                textSum = (TextView) item_queren_dd.findViewById(R.id.textSum);
                textGoods_money = (TextView) item_queren_dd.findViewById(R.id.textGoods_money);
                return item_queren_dd;
            }

            @Override
            public void onBindView(View headerView) {
                if (cartOrder != null) {
                    textSum.setText("¥" + cartOrder.getSum());
                    textGoods_money.setText("¥" + cartOrder.getGoods_money());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_RESULT_CODE.address && resultCode == Constant.REQUEST_RESULT_CODE.address) {
            UserAddress.DataBean dataBean = (UserAddress.DataBean) data.getSerializableExtra(Constant.INTENT_KEY.value);
            cartOrderAd.setConsignee(dataBean.getConsignee());
            cartOrderAd.setId(dataBean.getId());
            cartOrderAd.setAddress(dataBean.getAddress());
            cartOrderAd.setArea(dataBean.getArea());
            cartOrderAd.setPhone(dataBean.getPhone());
            adapter.notifyDataSetChanged();
        }
    }

    public void onRefresh() {
        List<String> cart = new ArrayList<>();
        for (int i = 0; i < cartIndex.getCart().size(); i++) {
            if (cartIndex.getCart().get(i).ischeck()) {
                cart.add(cartIndex.getCart().get(i).getId());
            }
        }
        cartOrderUpload = new CartOrderUpload(cart, userInfo.getUid(), tokenTime);
        String url = Constant.HOST + Constant.Url.CART_ORDER;
        ApiClient.postJson(this, url, GsonUtils.parseObject(cartOrderUpload), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("确认订单请求", s);
                try {
                    cartOrder = GsonUtils.parseJSON(s, CartOrder.class);
                    if (cartOrder.getStatus() == 1) {
                        cartOrderAd = cartOrder.getAd();
                        List<CartOrder.CartBean> cartOrderCart = cartOrder.getCart();
                        adapter.clear();
                        adapter.addAll(cartOrderCart);
                        viewTiJiao.setVisibility(View.VISIBLE);
                        textSum.setText("¥" + cartOrder.getSum());
                        textYunFei.setText(cartOrder.getSumDes());

                    } else if (cartOrder.getStatus() == 3) {
                        MyDialog.showReLoginDialog(QueRenDDActivity.this);
                    } else {
                        showError(cartOrder.getInfo());
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
                View view_loaderror = LayoutInflater.from(QueRenDDActivity.this).inflate(R.layout.view_loaderror, null);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonTiJiao:
                if (cartOrderAd == null) {
                    Toast.makeText(QueRenDDActivity.this, "请选择收货地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                tiJiao();
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }


    private void tiJiao() {
        showLoadingDialog();
        CartNeworderUpload cartNeworderUpload = new CartNeworderUpload(cartOrderUpload.getCart(), userInfo.getUid(), cartOrderAd.getId(), tokenTime, cartOrder.getSum(), editPayMsg.getText().toString().trim());
        ApiClient.postJson(QueRenDDActivity.this, Constant.HOST + Constant.Url.CART_NEWORDER, GsonUtils.parseObject(cartNeworderUpload), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("QueRenDDActivity--确认提交订单", s + "");
                try {
                    CartNeworder cartNeworder = GsonUtils.parseJSON(s, CartNeworder.class);
                    if (cartNeworder.getStatus() == 1) {
                        Intent intent1 = new Intent();
                        intent1.setAction(Constant.BROADCASTCODE.zhiFuGuanBi);
                        sendBroadcast(intent1);
                        //刷新购物车小红点
                        Intent intent2 = new Intent();
                        intent2.setAction(Constant.BROADCASTCODE.GouWuCheNum);
                        sendBroadcast(intent2);
                        Intent intent = new Intent();
                        intent.setClass(QueRenDDActivity.this, ZhiFuActivity.class);
                        intent.putExtra(Constant.INTENT_KEY.id, cartNeworder.getOid());
                        startActivity(intent);
                    } else if (cartNeworder.getStatus() == 2) {
                        MyDialog.showReLoginDialog(QueRenDDActivity.this);
                    } else {
                        Toast.makeText(QueRenDDActivity.this, cartNeworder.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(QueRenDDActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(QueRenDDActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
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
