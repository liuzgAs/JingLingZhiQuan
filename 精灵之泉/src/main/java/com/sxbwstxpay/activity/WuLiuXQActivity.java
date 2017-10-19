package com.sxbwstxpay.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.model.RecommBean;
import com.sxbwstxpay.provider.DataProvider;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.ChanPinXQViewHolder;
import com.sxbwstxpay.viewholder.MyBaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class WuLiuXQActivity extends ZjbBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private View viewBar;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<RecommBean> adapter;
    private int page = 1;
    private List<Integer> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wu_liu_xq);
        init(WuLiuXQActivity.class);
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
        ((TextView) findViewById(R.id.textViewTitle)).setText("物流详情");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        initRecycler();
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<RecommBean>(WuLiuXQActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_chan_pin_xq;
                return new ChanPinXQViewHolder(parent, layout);
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            private RecyclerArrayAdapter<Integer> adapterWuLiu;
            private EasyRecyclerView recyclerViewWuLiu;



            @Override
            public View onCreateView(ViewGroup parent) {
                View header_wuliu = LayoutInflater.from(WuLiuXQActivity.this).inflate(R.layout.header_wuliu, null);
                recyclerViewWuLiu = (EasyRecyclerView) header_wuliu.findViewById(R.id.recyclerView);
                recyclerViewWuLiu.setLayoutManager(new LinearLayoutManager(WuLiuXQActivity.this));
                recyclerViewWuLiu.setAdapterWithProgress(adapterWuLiu = new RecyclerArrayAdapter<Integer>(WuLiuXQActivity.this) {
                    @Override
                    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                        int layout = R.layout.item_wuliu00;
                        return new MyBaseViewHolder(parent, layout);
                    }
                });
                return header_wuliu;
            }

            @Override
            public void onBindView(View headerView) {
                adapterWuLiu.clear();
                adapterWuLiu.addAll(DataProvider.getPersonList(1));
            }


        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        List<RecommBean> list = new ArrayList<>();
        personList = DataProvider.getPersonList(1);
        adapter.clear();
        adapter.addAll(list);
    }

    class MyAdapter extends BaseAdapter {
        class ViewHolder {
//            public TextView title;
        }
        @Override
        public int getCount() {
            return personList.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(WuLiuXQActivity.this).inflate(R.layout.item_wuliu00, null);
//                holder.title = (TextView) convertView.findViewById(R.id.title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//            holder.title.setText("Hello");
            return convertView;
        }
    }
}
