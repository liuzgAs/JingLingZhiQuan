package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.BannerBean;
import com.sxbwstxpay.model.IndexGoods;
import com.sxbwstxpay.model.IndexStore;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.BenDiYouDianViewHolder;
import com.sxbwstxpay.viewholder.LocalImageHolderView;

import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class StoreListActivity extends ZjbBaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<IndexStore.DataBean> adapter;
    private String lat;
    private String lng;
    private int page = 1;
    private int type;
    private List<BannerBean> indexStoreBanner;
    private String sort;
    private BroadcastReceiver reciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.ShaiXuan01:
                    sort = intent.getStringExtra(Constant.INTENT_KEY.value);
                    recyclerView.showProgress();
                    onRefresh();
                    break;
                default:
                    break;
            }
        }
    };
    private String cityId;
    private IndexGoods.CateBean cateBean;
    private View viewBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        init(StoreListActivity.class);
    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        cateBean = (IndexGoods.CateBean) intent.getSerializableExtra(Constant.INTENT_KEY.value);
        switch (cateBean.getJump()) {
            case "product":
                type = 0;
                break;
            case "store":
                type = 1;
                break;
            default:
                break;
        }
    }

    @Override
    protected void initSP() {
        final ACache aCache = ACache.get(StoreListActivity.this, Constant.ACACHE.LOCATION);
        String cityAcache = aCache.getAsString(Constant.ACACHE.CITY);
        if (cityAcache != null) {
            lat = aCache.getAsString(Constant.ACACHE.LAT);
            lng = aCache.getAsString(Constant.ACACHE.LNG);
            cityId = aCache.getAsString(Constant.ACACHE.CITY_ID);
        }
    }

    @Override
    protected void findID() {
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        viewBar = findViewById(R.id.viewBar);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        ((TextView) findViewById(R.id.textViewTitle)).setText(cateBean.getName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(StoreListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.marginTop), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        int red = getResources().getColor(R.color.basic_color);
        recyclerView.setRefreshingColor(red);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<IndexStore.DataBean>(StoreListActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_ben_di_yd;
                return new BenDiYouDianViewHolder(parent, layout);
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private ConvenientBanner banner;

            @Override
            public View onCreateView(ViewGroup parent) {
                View header_ben_di = LayoutInflater.from(StoreListActivity.this).inflate(R.layout.header_ben_di, null);
                banner = (ConvenientBanner) header_ben_di.findViewById(R.id.banner);
                ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
                int screenWidth = ScreenUtils.getScreenWidth(StoreListActivity.this);
                layoutParams.width = screenWidth;
                layoutParams.height = (int) ((float) screenWidth * 452f / 1080f);
                banner.setLayoutParams(layoutParams);
                banner.setScrollDuration(1000);
                banner.startTurning(3000);
                return header_ben_di;
            }

            @Override
            public void onBindView(View headerView) {
                if (indexStoreBanner != null) {
                    banner.setPages(new CBViewHolderCreator() {
                        @Override
                        public Object createHolder() {
                            return new LocalImageHolderView();
                        }
                    }, indexStoreBanner);
                    banner.setPageIndicator(new int[]{R.drawable.shape_indicator_normal, R.drawable.shape_indicator_selected});
                }
            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                ApiClient.post(StoreListActivity.this, getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            page++;
                            IndexStore indexStore = GsonUtils.parseJSON(s, IndexStore.class);
                            int status = indexStore.getStatus();
                            if (status == 1) {
                                List<IndexStore.DataBean> indexStoreData = indexStore.getData();
                                adapter.addAll(indexStoreData);
                            } else if (status == 3) {
                                MyDialog.showReLoginDialog(StoreListActivity.this);
                            } else {
                                adapter.pauseMore();
                            }
                        } catch (Exception e) {
                            adapter.pauseMore();
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        adapter.pauseMore();
                    }
                });
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
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.id, adapter.getItem(position).getId());
                intent.putExtra(Constant.INTENT_KEY.type, 1);
                intent.setClass(StoreListActivity.this, GuanLiWDDPActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        TextView textTitleEmpty = (TextView) recyclerView.getEmptyView().findViewById(R.id.textTitle);
        switch (type) {
            case 0:
                textTitleEmpty.setText("本地优质商家火热招募中");
                break;
            case 1:
                textTitleEmpty.setText("本地优质商家火热招募中");
                break;
            default:
                break;
        }
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
        String url;
        if (type == 0) {
            url = Constant.HOST + Constant.Url.INDEX_PRODUCT;
        } else {
            url = Constant.HOST + Constant.Url.INDEX_STORE;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("p", page + "");
        params.put("sort", sort);
        params.put("cityId", cityId);
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        page = 1;
        ApiClient.post(StoreListActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("本地优店", s);
                try {
                    page++;
                    IndexStore indexStore = GsonUtils.parseJSON(s, IndexStore.class);
                    if (indexStore.getStatus() == 1) {
                        indexStoreBanner = indexStore.getBanner();
                        List<IndexStore.DataBean> indexStoreData = indexStore.getData();
                        adapter.clear();
                        adapter.addAll(indexStoreData);
                        if (indexStoreData.size() == 0) {
                            recyclerView.showEmpty();
                        }
                    } else if (indexStore.getStatus() == 3) {
                        MyDialog.showReLoginDialog(StoreListActivity.this);
                    } else {
                        showError(indexStore.getInfo());
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
                View view_loaderror = LayoutInflater.from(StoreListActivity.this).inflate(R.layout.view_loaderror, null);
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
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.ShaiXuan01);
        filter.addAction(Constant.BROADCASTCODE.CITY_CHOOSE);
        registerReceiver(reciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }
}
