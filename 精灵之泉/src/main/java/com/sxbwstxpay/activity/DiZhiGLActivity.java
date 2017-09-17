package com.sxbwstxpay.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.provider.DataProvider;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.DiZhiGLViewHolder;

public class DiZhiGLActivity extends ZjbBaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Integer> adapter;
    private Handler handler = new Handler();
    private int page = 1;
    private View include;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_di_zhi_gl);
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
        include = findViewById(R.id.include);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("地址管理");
        ViewGroup.LayoutParams layoutParams = include.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        include.setLayoutParams(layoutParams);
        initRecycler();
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) DpUtils.convertDpToPixel(10, this), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Integer>(DiZhiGLActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_dzgl;
                return new DiZhiGLViewHolder(parent, layout);
            }

            @Override
            public int getViewType(int position) {
                Integer item = getItem(position);
                return item;
            }
        });

        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View view = new View(DiZhiGLActivity.this);
                view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) DpUtils.convertDpToPixel(10, DiZhiGLActivity.this)));
                return view;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addAll(DataProvider.getPersonList(page));
                        page++;
                    }
                }, 500);
            }

            @Override
            public void onMoreClick() {

            }
        });
        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {

            }

            @Override
            public void onNoMoreClick() {
            }
        });
        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
        recyclerView.setRefreshListener(this);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.textXinZengDZ).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        page = 1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                adapter.addAll(DataProvider.getPersonList(page));
                page++;
            }
        }, 500);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.textXinZengDZ:
                intent.setClass(this,XinZengDZActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
