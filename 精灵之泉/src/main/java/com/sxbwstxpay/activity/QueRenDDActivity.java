package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.CartIndex;
import com.sxbwstxpay.model.CartOrder;
import com.sxbwstxpay.model.CartOrderUpload;
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
    private List<?> cartOrderAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_ren_dd);
        init();
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
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("确认订单");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
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

            private TextView textAdd;
            private View viewXuanZeSHDZ;
            @Override
            public View onCreateView(ViewGroup parent) {
                View header_queren_dd = LayoutInflater.from(QueRenDDActivity.this).inflate(R.layout.header_queren_dd, null);
                viewXuanZeSHDZ = header_queren_dd.findViewById(R.id.viewXuanZeSHDZ);
                textAdd = (TextView) header_queren_dd.findViewById(R.id.textAdd);
                viewXuanZeSHDZ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(QueRenDDActivity.this, XuanZeSHDZActivity.class);
                        startActivity(intent);
                    }
                });
                return header_queren_dd;
            }

            @Override
            public void onBindView(View headerView) {
                if (cartOrderAd!=null){
                    viewXuanZeSHDZ.setVisibility(View.VISIBLE);
                    textAdd.setVisibility(View.GONE);
                }else {
                    viewXuanZeSHDZ.setVisibility(View.GONE);
                    textAdd.setVisibility(View.VISIBLE);
                }
            }
        });
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {

            @Override
            public View onCreateView(ViewGroup parent) {
                View item_queren_dd = LayoutInflater.from(QueRenDDActivity.this).inflate(R.layout.item_queren_dd, null);
                return item_queren_dd;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }

    public void onRefresh() {
        List<String> cart = new ArrayList<>();
        for (int i = 0; i < cartIndex.getCart().size(); i++) {
            if (cartIndex.getCart().get(i).ischeck()){
                cart.add(cartIndex.getCart().get(i).getId());
            }
        }
        CartOrderUpload cartOrderUpload = new CartOrderUpload(cart,userInfo.getUid(),tokenTime);
        String url = Constant.HOST + Constant.Url.CART_ORDER;
        ApiClient.postJson(this, url,GsonUtils.parseObject(cartOrderUpload), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("确认订单请求", s);
                try {
                    CartOrder cartOrder = GsonUtils.parseJSON(s, CartOrder.class);
                    if (cartOrder.getStatus() == 1) {
                        cartOrderAd = cartOrder.getAd();
                        List<CartOrder.CartBean> cartOrderCart = cartOrder.getCart();
                        adapter.clear();
                        adapter.addAll(cartOrderCart);
                    } else if (cartOrder.getStatus()== 2) {
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
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.buttonTiJiao:
                intent.setClass(this,ZhiFuActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
