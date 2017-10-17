package com.sxbwstxpay.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ChanPinXQActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.BannerBean;
import com.sxbwstxpay.model.IndexDataBean;
import com.sxbwstxpay.model.IndexGoods;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.LocalImageHolderView;
import com.sxbwstxpay.viewholder.XianShiQGViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class XianShiQGFragment extends ZjbBaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    private View mInflate;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<IndexDataBean> adapter;
    private int page = 1;
    private String mCity;
    private String lat;
    private String lng;
    private String cityId;
    private String id;
    private List<BannerBean> indexGoodsBanner;
    private List<IndexGoods.TimesBean> indexGoodsTimes;
    private String indexGoodsImg;
    private View viewShangJiaTip;
    private Timer timer;
    private BroadcastReceiver reciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.ShangJia01:
                    String value = intent.getStringExtra(Constant.INTENT_KEY.value);
                    textNum.setText(value);
                    if (viewShangJiaTip.getVisibility() == View.VISIBLE) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideView();
                                    }
                                });
                            }
                        }, 2000, 1000);
                    } else {
                        Animation animation01 = AnimationUtils.loadAnimation(getActivity(), R.anim.push_up_in);
                        viewShangJiaTip.startAnimation(animation01);
                        viewShangJiaTip.setVisibility(View.VISIBLE);
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideView();
                                    }
                                });
                            }
                        }, 2000, 1000);
                    }
                    break;
            }
        }
    };
    private TextView textNum;

    public void hideView() {
        Animation animation02 = AnimationUtils.loadAnimation(getActivity(), R.anim.push_down_out);
        viewShangJiaTip.startAnimation(animation02);
        viewShangJiaTip.setVisibility(View.GONE);
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }

    public XianShiQGFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_xian_shi_qg, container, false);
            init();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mInflate.getParent();
        if (parent != null) {
            parent.removeView(mInflate);
        }
        return mInflate;
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initSP() {
        final ACache aCache = ACache.get(getActivity(), Constant.ACACHE.LOCATION);
        String cityAcache = aCache.getAsString(Constant.ACACHE.CITY);
        if (cityAcache != null) {
            mCity = cityAcache;
            lat = aCache.getAsString(Constant.ACACHE.LAT);
            lng = aCache.getAsString(Constant.ACACHE.LNG);
            cityId = aCache.getAsString(Constant.ACACHE.CITY_ID);
        }
    }

    @Override
    protected void findID() {
        recyclerView = (EasyRecyclerView) mInflate.findViewById(R.id.recyclerView);
        viewShangJiaTip = mInflate.findViewById(R.id.viewShangJiaTip);
        textNum = (TextView) mInflate.findViewById(R.id.textNum);
    }

    @Override
    protected void initViews() {
        viewShangJiaTip.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.marginTop), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        int red = getResources().getColor(R.color.basic_color);
        recyclerView.setRefreshingColor(red);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<IndexDataBean>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_xian_shi_qg;
                return new XianShiQGViewHolder(parent, layout,"MainActivity");
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private ImageView imageMiddle;
            private TabLayout tablayoutHeader;
            private ConvenientBanner banner;
            private boolean isSelect = false;

            @Override
            public View onCreateView(ViewGroup parent) {
                View header_xian_shi_qg = LayoutInflater.from(getActivity()).inflate(R.layout.header_xian_shi_qg, null);
                banner = (ConvenientBanner) header_xian_shi_qg.findViewById(R.id.banner);
                ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
                int screenWidth = ScreenUtils.getScreenWidth(getActivity());
                layoutParams.width = screenWidth;
                layoutParams.height = (int) ((float)screenWidth*452f/1080f);
                banner.setLayoutParams(layoutParams);
                banner.setScrollDuration(1000);
                banner.startTurning(3000);
                tablayoutHeader = (TabLayout) header_xian_shi_qg.findViewById(R.id.tablayoutHeader);
                tablayoutHeader.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if (isSelect) {
                            for (int i = 0; i < indexGoodsTimes.size(); i++) {
                                indexGoodsTimes.get(i).setAct(0);
                            }
                            indexGoodsTimes.get(tab.getPosition()).setAct(1);
                            page = 1;
                            id = indexGoodsTimes.get(tab.getPosition()).getId();
                            showLoadingDialog();
                            ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
                                @Override
                                public void onSuccess(String s) {
                                    cancelLoadingDialog();
                                    LogUtil.LogShitou("限时购时段", s);
                                    try {
                                        page++;
                                        IndexGoods indexGoods = GsonUtils.parseJSON(s, IndexGoods.class);
                                        if (indexGoods.getStatus() == 1) {
                                            List<IndexDataBean> indexGoodsData = indexGoods.getData();
                                            adapter.removeAll();
                                            adapter.addAll(indexGoodsData);
                                        } else if (indexGoods.getStatus() == 3) {
                                            MyDialog.showReLoginDialog(getActivity());
                                        } else {
                                            showError(indexGoods.getInfo());
                                        }
                                    } catch (Exception e) {
                                        showError("数据出错");
                                    }
                                }

                                @Override
                                public void onError(Response response) {
                                    cancelLoadingDialog();
                                    showError("网络出错");
                                }

                                public void showError(String msg) {
                                    View view_loaderror = LayoutInflater.from(getActivity()).inflate(R.layout.view_loaderror, null);
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

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
                imageMiddle = (ImageView) header_xian_shi_qg.findViewById(R.id.imageMiddle);
                return header_xian_shi_qg;
            }

            @Override
            public void onBindView(View headerView) {
                if (indexGoodsBanner != null) {
                    banner.setPages(new CBViewHolderCreator() {
                        @Override
                        public Object createHolder() {
                            return new LocalImageHolderView();
                        }
                    }, indexGoodsBanner);
                    banner.setPageIndicator(new int[]{R.drawable.shape_indicator_normal, R.drawable.shape_indicator_selected});
                }
                if (indexGoodsTimes != null) {
                    tablayoutHeader.removeAllTabs();
                    tablayoutHeader.setTabMode(TabLayout.MODE_SCROLLABLE);
                    for (int i = 0; i < indexGoodsTimes.size(); i++) {
                        View item_qiang_gou_sj = LayoutInflater.from(getActivity()).inflate(R.layout.item_qiang_gou_sj, null);
                        TextView textQiangGouTitle = (TextView) item_qiang_gou_sj.findViewById(R.id.textQiangGouTitle);
                        TextView textQiangGouDes = (TextView) item_qiang_gou_sj.findViewById(R.id.textQiangGouDes);
                        textQiangGouTitle.setText(indexGoodsTimes.get(i).getTimes());
                        textQiangGouDes.setText(indexGoodsTimes.get(i).getDes());
                        isSelect = false;
                        if (indexGoodsTimes.get(i).getAct() == 1) {
                            tablayoutHeader.addTab(tablayoutHeader.newTab().setCustomView(item_qiang_gou_sj), true);
                        } else {
                            tablayoutHeader.addTab(tablayoutHeader.newTab().setCustomView(item_qiang_gou_sj), false);
                        }
                        isSelect = true;
                    }
                }
                Glide.with(getActivity())
                        .load(indexGoodsImg)
                        .asBitmap()
                        .placeholder(R.mipmap.ic_empty)
                        .into(imageMiddle);
            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            page++;
                            IndexGoods indexGoods = GsonUtils.parseJSON(s, IndexGoods.class);
                            int status = indexGoods.getStatus();
                            if (status == 1) {
                                List<IndexDataBean> indexGoodsData = indexGoods.getData();
                                adapter.addAll(indexGoodsData);
                            } else if (status == 3) {
                                MyDialog.showReLoginDialog(getActivity());
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
                intent.setClass(getActivity(), ChanPinXQActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.imageCancle).setOnClickListener(this);
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
        String url = Constant.HOST + Constant.Url.INDEX_GOODS;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("p", page + "");
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("id", id);
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        id = "0";
        page = 1;
        ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("限时购", s);
                try {
                    page++;
                    IndexGoods indexGoods = GsonUtils.parseJSON(s, IndexGoods.class);
                    if (indexGoods.getStatus() == 1) {
                        indexGoodsBanner = indexGoods.getBanner();
                        indexGoodsTimes = indexGoods.getTimes();
                        indexGoodsImg = indexGoods.getImg();
                        List<IndexDataBean> indexGoodsData = indexGoods.getData();
                        adapter.clear();
                        adapter.addAll(indexGoodsData);
                    } else if (indexGoods.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        showError(indexGoods.getInfo());
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
                View view_loaderror = LayoutInflater.from(getActivity()).inflate(R.layout.view_loaderror, null);
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
        filter.addAction(Constant.BROADCASTCODE.ShangJia01);
        getActivity().registerReceiver(reciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(reciver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageCancle:
                hideView();
                break;
        }
    }
}
