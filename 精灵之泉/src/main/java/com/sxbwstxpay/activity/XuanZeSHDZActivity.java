package com.sxbwstxpay.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sxbwstxpay.R;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.provider.DataProvider;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.XuanZeSHDZViewHolder;

public class XuanZeSHDZActivity extends ZjbBaseActivity implements View.OnClickListener {

    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Integer> adapter;
    private Handler handler = new Handler();
    private View viewBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuan_ze_shdz);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("选择收货地址");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        initRecycle();
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.textXinZengSHDZ).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    private void initRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.marginTop), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        int red = getResources().getColor(R.color.basic_color);
        recyclerView.setRefreshingColor(red);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Integer>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_xuanze_shdz;
                return new XuanZeSHDZViewHolder(parent, layout);
            }

            @Override
            public int getViewType(int position) {
                Integer item = getItem(position);
                return item;
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                adapter.addAll(DataProvider.getPersonList(1));
            }
        }, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textXinZengSHDZ:
                Intent intent = new Intent();
                intent.setClass(this,XinZengDZActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
