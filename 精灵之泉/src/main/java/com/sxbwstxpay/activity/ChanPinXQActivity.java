package com.sxbwstxpay.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jlzquan.www.R;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.provider.DataProvider;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.RecycleViewDistancaUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.ChanPinXQViewHolder;

public class ChanPinXQActivity extends ZjbBaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private View viewBar;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Integer> adapter;
    private int page = 1;
    private TextView textViewTitle;
    private int viewBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chan_pin_xq);
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
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
    }

    @Override
    protected void initViews() {
        textViewTitle.setText("产品详情");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        viewBarHeight = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        layoutParams.height = viewBarHeight;
        viewBar.setLayoutParams(layoutParams);
        viewBar.getBackground().mutate().setAlpha(0);
        textViewTitle.setAlpha(0);
        initRecycle();
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
        onRefresh();
    }

    private void initRecycle() {
        SpaceDecoration itemDecoration = new SpaceDecoration((int) DpUtils.convertDpToPixel(10f, this));
        itemDecoration.setPaddingStart(false);
        itemDecoration.setPaddingEdgeSide(false);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);
        int red = getResources().getColor(R.color.basic_color);
        recyclerView.setRefreshingColor(red);
        recyclerView.getSwipeToRefresh().setProgressViewOffset(true, 30, 220);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Integer>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_chan_pin_xq;
                return new ChanPinXQViewHolder(parent, layout);
            }

        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(adapter.obtainGridSpanSizeLookUp(2));
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private ListView listView;
            private TabLayout tablayout;

            @Override
            public View onCreateView(ViewGroup parent) {
                View header_xhan_pin_xq = LayoutInflater.from(ChanPinXQActivity.this).inflate(R.layout.header_xhan_pin_xq, null);
                tablayout = (TabLayout) header_xhan_pin_xq.findViewById(R.id.tablayout);
                for (int i = 0; i < 2; i++) {
                    View item_tablayout = LayoutInflater.from(ChanPinXQActivity.this).inflate(R.layout.item_tablayout, null);
                    TextView textTitle = (TextView) item_tablayout.findViewById(R.id.textTitle);
                    if (i == 1) {
                        textTitle.setText("宝贝详情");
                        tablayout.addTab(tablayout.newTab().setCustomView(item_tablayout));
                    }else {
                        textTitle.setText("规格参数");
                        tablayout.addTab(tablayout.newTab().setCustomView(item_tablayout));
                    }
                }
                listView = (ListView) header_xhan_pin_xq.findViewById(R.id.listView);
                listView.setAdapter(new MyAdapter());
                return header_xhan_pin_xq;
            }

            @Override
            public void onBindView(View headerView) {

            }

            class MyAdapter extends BaseAdapter {
                class ViewHolder {
                    public ImageView imageImg;
                }
                @Override
                public int getCount() {
                    return 1;
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
                        convertView = LayoutInflater.from(ChanPinXQActivity.this).inflate(R.layout.item_img, null);
                        holder.imageImg = (ImageView) convertView.findViewById(R.id.imageImg);
                        convertView.setTag(holder);
                    } else {
                        holder = (ViewHolder) convertView.getTag();
                    }
                    holder.imageImg.setImageResource(R.mipmap.imgxiangqing);
                    return convertView;
                }
            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                page++;
                adapter.addAll(DataProvider.getPersonList(page));
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
        recyclerView.setRefreshListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int scrollY = RecycleViewDistancaUtil.getDistance(recyclerView, 0);
                float guangGaoHeight = getResources().getDimension(R.dimen.chanPinXQBanner);
                if (scrollY <= guangGaoHeight - viewBarHeight && scrollY >= 0) {
                    int i = (int) ((double) scrollY / (double) (guangGaoHeight - viewBar.getHeight()) * 255);
                    viewBar.getBackground().mutate().setAlpha(i);
                    textViewTitle.setAlpha((float) i / 255f);
                } else {
                    viewBar.getBackground().mutate().setAlpha(255);
                    textViewTitle.setAlpha(1);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        page++;
        adapter.clear();
        adapter.addAll(DataProvider.getPersonList(page));
    }
}
