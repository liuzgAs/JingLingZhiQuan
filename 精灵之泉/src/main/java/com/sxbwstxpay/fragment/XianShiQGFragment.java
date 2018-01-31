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
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ChanPinXQActivity;
import com.sxbwstxpay.activity.ChengShiXZActivity;
import com.sxbwstxpay.activity.GouWuCActivity;
import com.sxbwstxpay.activity.SouSuoActivity;
import com.sxbwstxpay.activity.StoreListActivity;
import com.sxbwstxpay.activity.WebActivity;
import com.sxbwstxpay.activity.XuanPinSJActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.BannerBean;
import com.sxbwstxpay.model.IndexCitylist;
import com.sxbwstxpay.model.IndexDataBean;
import com.sxbwstxpay.model.IndexGoods;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.RecycleViewDistancaUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.LocalImageHolderView;
import com.sxbwstxpay.viewholder.XianShiQGViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Response;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

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
                case Constant.BROADCASTCODE.VIP:
                    onRefresh();
                    break;
                case Constant.BROADCASTCODE.CITY_CHOOSE:
                    IndexCitylist.CityEntity.ListEntity cityBean = (IndexCitylist.CityEntity.ListEntity) intent.getSerializableExtra(Constant.INTENT_KEY.CITY);
                    cityId = cityBean.getId();
                    final ACache aCache = ACache.get(getActivity(), Constant.ACACHE.LOCATION);
                    aCache.put(Constant.ACACHE.CITY_ID, cityId);
                    textCity.setText(cityBean.getName());
                    onRefresh();
                    break;
                default:
                    break;
            }
        }
    };
    private TextView textNum;
    private TabLayout tablayoutHeaderX;
    private boolean isSelect = false;
    private int indexBannerHeight;
    private float tabHeight;
    private TabLayout tablayoutHeader;
    private View tabCarview;
    private Badge badge;
    private TextView textCity;
    private View viewBar;
    private List<IndexGoods.CateBean> cateBeanList;

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
        viewBar = mInflate.findViewById(R.id.viewBar);
        recyclerView = (EasyRecyclerView) mInflate.findViewById(R.id.recyclerView);
        viewShangJiaTip = mInflate.findViewById(R.id.viewShangJiaTip);
        textNum = (TextView) mInflate.findViewById(R.id.textNum);
        tablayoutHeaderX = (TabLayout) mInflate.findViewById(R.id.tablayoutHeaderX);
        tabCarview = mInflate.findViewById(R.id.tabCarview);
        textCity = (TextView) mInflate.findViewById(R.id.textCity);
        badge = new QBadgeView(getActivity())
                .setBadgeTextColor(Color.WHITE)
                .setBadgeTextSize(8f, true)
                .setBadgeBackgroundColor(getResources().getColor(R.color.red))
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(3f, 3f, true);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) DpUtils.convertDpToPixel(70, getActivity()) + ScreenUtils.getStatusBarHeight(getActivity());
        viewBar.setLayoutParams(layoutParams);
        viewBar.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);
        textCity.setText(mCity);
        tablayoutHeaderX.setVisibility(View.GONE);
        tabCarview.setVisibility(View.GONE);
        viewShangJiaTip.setVisibility(View.GONE);
        int screenWidth = ScreenUtils.getScreenWidth(getActivity());
        indexBannerHeight = (int) ((float) screenWidth * Constant.VALUE.IndexBannerHeight / 1080f) + (int) DpUtils.convertDpToPixel(45, getActivity());
        tabHeight = getActivity().getResources().getDimension(R.dimen.tabHeight);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.marginTop), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        int red = getResources().getColor(R.color.basic_color);
        recyclerView.setRefreshingColor(red);
        recyclerView.getSwipeToRefresh().setProgressViewOffset(true, 30, 220);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<IndexDataBean>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_xian_shi_qg;
                return new XianShiQGViewHolder(parent, layout, "MainActivity");
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private MyAdapter adapterGrid;
            private GridView gridView;
            private ConvenientBanner banner;

            @Override
            public View onCreateView(ViewGroup parent) {
                View header_xian_shi_qg = LayoutInflater.from(getActivity()).inflate(R.layout.header_xian_shi_qg, null);
                banner = (ConvenientBanner) header_xian_shi_qg.findViewById(R.id.banner);
                ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
                int screenWidth = ScreenUtils.getScreenWidth(getActivity());
                layoutParams.width = screenWidth;
                layoutParams.height = (int) ((float) screenWidth * Constant.VALUE.IndexBannerHeight / 1080f);
                banner.setLayoutParams(layoutParams);
                banner.setScrollDuration(1000);
                banner.startTurning(3000);
                tablayoutHeader = (TabLayout) header_xian_shi_qg.findViewById(R.id.tablayoutHeader);
                tablayoutHeader.addOnTabSelectedListener(new MyTabSelectListener(tablayoutHeader, 0));
                gridView = (GridView) header_xian_shi_qg.findViewById(R.id.gridView);
                adapterGrid = new MyAdapter();
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent();
                        switch (cateBeanList.get(i).getJump()) {
                            case "list":
                                intent.putExtra(Constant.INTENT_KEY.value, cateBeanList.get(i));
                                intent.setClass(getActivity(), XuanPinSJActivity.class);
                                startActivity(intent);
                                break;
                            case "product":
                                intent.putExtra(Constant.INTENT_KEY.value, cateBeanList.get(i));
                                intent.setClass(getActivity(), StoreListActivity.class);
                                startActivity(intent);
                                break;
                            case "store":
                                intent.putExtra(Constant.INTENT_KEY.value, cateBeanList.get(i));
                                intent.setClass(getActivity(), StoreListActivity.class);
                                startActivity(intent);
                                break;
                            case "web":
                                intent.setClass(getContext(), WebActivity.class);
                                intent.putExtra(Constant.INTENT_KEY.TITLE, cateBeanList.get(i).getName());
                                intent.putExtra(Constant.INTENT_KEY.URL, cateBeanList.get(i).getUrl());
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                    }
                });
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
                initTablayout(tablayoutHeader);
                if (cateBeanList != null) {
                    gridView.setAdapter(adapterGrid);
                }
            }

            class MyAdapter extends BaseAdapter {
                class ViewHolder {
                    public TextView textTitle;
                    public ImageView imageImg;
                }

                @Override
                public int getCount() {
                    return cateBeanList.size();
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
                        convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_grid_shouye, null);
                        holder.textTitle = (TextView) convertView.findViewById(R.id.textTitle);
                        holder.imageImg = (ImageView) convertView.findViewById(R.id.imageImg);
                        convertView.setTag(holder);
                    } else {
                        holder = (ViewHolder) convertView.getTag();
                    }
                    holder.textTitle.setText(cateBeanList.get(position).getName());
                    GlideApp.with(getActivity())
                            .load(cateBeanList.get(position).getImg())
                            .centerCrop()
                            .placeholder(R.mipmap.ic_empty)
                            .into(holder.imageImg);
                    return convertView;
                }
            }

        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                XianShiQGMore();
            }

            private void XianShiQGMore() {
                ApiClient.post(getActivity(), getXianShiQGOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.LogShitou("XianShiQGFragment--限时抢购更多", s + "");
                        try {
                            page++;
                            IndexGoods indexGoods = GsonUtils.parseJSON(s, IndexGoods.class);
                            int status = indexGoods.getStatus();
                            if (status == 1) {
                                List<IndexDataBean> indexGoodsData = indexGoods.getData();
                                if (indexGoodsData.size() == 0) {
                                    int index = 0;
                                    for (int i = 0; i < indexGoodsTimes.size(); i++) {
                                        if (indexGoodsTimes.get(i).getAct() == 1) {
                                            index = i;
                                            break;
                                        }
                                    }
                                    if (index < indexGoodsTimes.size() - 1) {
                                        index++;
                                        tablayoutHeaderX.setScrollPosition(index, 0, true);
                                        tablayoutHeaderX.getTabAt(index).select();
                                    } else {
                                        adapter.addAll(indexGoodsData);
                                    }
                                } else {
                                    adapter.addAll(indexGoodsData);
                                }
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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int distance = RecycleViewDistancaUtil.getDistance(recyclerView, 0);
//                onScrollListener.scroll(distance);
                if (distance >= indexBannerHeight || distance == -1) {
                    tablayoutHeaderX.setVisibility(View.VISIBLE);
                    tabCarview.setVisibility(View.VISIBLE);
                } else {
                    tablayoutHeaderX.setVisibility(View.GONE);
                    tabCarview.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.imageCancle).setOnClickListener(this);
        tablayoutHeaderX.addOnTabSelectedListener(new MyTabSelectListener(tablayoutHeaderX, 1));
        textCity.setOnClickListener(this);
        mInflate.findViewById(R.id.textSouSuo).setOnClickListener(this);
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
    private OkObject getXianShiQGOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_GOODS;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("p", page + "");
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("id", id);
        params.put("cityId", cityId);
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        xianShiQiangGou();
    }


    private void xianShiQiangGou() {
        id = "0";
        page = 1;
        ApiClient.post(getActivity(), getXianShiQGOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("限时购", s);
                try {
                    page++;
                    IndexGoods indexGoods = GsonUtils.parseJSON(s, IndexGoods.class);
                    if (indexGoods.getStatus() == 1) {
                        cateBeanList = indexGoods.getCate();
                        indexGoodsBanner = indexGoods.getBanner();
                        indexGoodsTimes = indexGoods.getTimes();
                        initTablayout(tablayoutHeaderX);
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
                try {
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
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.ShangJia01);
        filter.addAction(Constant.BROADCASTCODE.VIP);
        filter.addAction(Constant.BROADCASTCODE.CITY_CHOOSE);
        getActivity().registerReceiver(reciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(reciver);
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.textSouSuo:
                intent.putExtra(Constant.INTENT_KEY.type, 0);
                intent.setClass(getActivity(), SouSuoActivity.class);
                startActivity(intent);
                break;
            case R.id.viewVip:
                intent.setClass(getActivity(), GouWuCActivity.class);
                startActivity(intent);
                break;
            case R.id.textCity:
                intent.setClass(getActivity(), ChengShiXZActivity.class);
                startActivity(intent);
                break;
            case R.id.imageCancle:
                hideView();
                break;
            default:
        }
    }

//    public OnScrollListener onScrollListener;
//
//    public void setOnScrollListener(OnScrollListener onScrollListener) {
//        this.onScrollListener = onScrollListener;
//    }

    interface OnScrollListener {
        void scroll(int distance);
    }

    private void initTablayout(TabLayout tabLayout) {
        if (indexGoodsTimes != null) {
            tabLayout.removeAllTabs();
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            for (int i = 0; i < indexGoodsTimes.size(); i++) {
                View item_qiang_gou_sj = LayoutInflater.from(getActivity()).inflate(R.layout.item_qiang_gou_sj, null);
                TextView textQiangGouTitle = (TextView) item_qiang_gou_sj.findViewById(R.id.textQiangGouTitle);
                TextView textQiangGouDes = (TextView) item_qiang_gou_sj.findViewById(R.id.textQiangGouDes);
                textQiangGouTitle.setText(indexGoodsTimes.get(i).getTimes());
                textQiangGouDes.setText(indexGoodsTimes.get(i).getDes());
                isSelect = false;
                TextPaint paint = textQiangGouTitle.getPaint();
                if (indexGoodsTimes.get(i).getAct() == 1) {
                    paint.setFakeBoldText(true);
                    tabLayout.addTab(tabLayout.newTab().setCustomView(item_qiang_gou_sj), true);
                } else {
                    paint.setFakeBoldText(false);
                    tabLayout.addTab(tabLayout.newTab().setCustomView(item_qiang_gou_sj), false);
                }
                isSelect = true;
            }
        }
    }

    class MyTabSelectListener implements TabLayout.OnTabSelectedListener {
        private TabLayout tabLayout;
        private int type;

        public MyTabSelectListener(TabLayout tabLayout, int type) {
            this.tabLayout = tabLayout;
            this.type = type;
        }

        @Override
        public void onTabSelected(final TabLayout.Tab tab) {
            select(tab);

        }

        private void select(final TabLayout.Tab tab) {
            LogUtil.LogShitou("MyTabSelectListener--onTabSelected isSelect", "" + isSelect);
            if (isSelect) {
                for (int i = 0; i < indexGoodsTimes.size(); i++) {
                    TextView textQiangGouTitle = (TextView) tabLayout.getTabAt(i).getCustomView().findViewById(R.id.textQiangGouTitle);
                    TextPaint paint = textQiangGouTitle.getPaint();
                    paint.setFakeBoldText(false);
                    indexGoodsTimes.get(i).setAct(0);
                }
                TextView textQiangGouTitle = (TextView) tab.getCustomView().findViewById(R.id.textQiangGouTitle);
                TextPaint paint = textQiangGouTitle.getPaint();
                paint.setFakeBoldText(true);
                indexGoodsTimes.get(tab.getPosition()).setAct(1);
                page = 1;
                id = indexGoodsTimes.get(tab.getPosition()).getId();

                showLoadingDialog();
                ApiClient.post(getActivity(), getXianShiQGOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        cancelLoadingDialog();
                        LogUtil.LogShitou("限时购时段", s);
                        try {
                            page++;
                            IndexGoods indexGoods = GsonUtils.parseJSON(s, IndexGoods.class);
                            if (indexGoods.getStatus() == 1) {
                                List<IndexDataBean> indexGoodsData = indexGoods.getData();
                                if (type == 0) {
                                    isSelect = false;
                                    tablayoutHeaderX.setScrollPosition(tab.getPosition(), 0, true);
                                    tablayoutHeaderX.getTabAt(tab.getPosition()).select();
                                    for (int i = 0; i < indexGoodsTimes.size(); i++) {
                                        TextView textQiangGouTitle = (TextView) tablayoutHeaderX.getTabAt(i).getCustomView().findViewById(R.id.textQiangGouTitle);
                                        TextPaint paint = textQiangGouTitle.getPaint();
                                        paint.setFakeBoldText(false);
                                    }
                                    TextView textQiangGouTitle = (TextView) tablayoutHeaderX.getTabAt(tab.getPosition()).getCustomView().findViewById(R.id.textQiangGouTitle);
                                    TextPaint paint = textQiangGouTitle.getPaint();
                                    paint.setFakeBoldText(true);
                                    isSelect = true;
                                } else {
                                    isSelect = false;
                                    tablayoutHeader.setScrollPosition(tab.getPosition(), 0, true);
                                    tablayoutHeader.getTabAt(tab.getPosition()).select();
                                    for (int i = 0; i < indexGoodsTimes.size(); i++) {
                                        TextView textQiangGouTitle = (TextView) tablayoutHeader.getTabAt(i).getCustomView().findViewById(R.id.textQiangGouTitle);
                                        TextPaint paint = textQiangGouTitle.getPaint();
                                        paint.setFakeBoldText(false);
                                    }
                                    TextView textQiangGouTitle = (TextView) tablayoutHeader.getTabAt(tab.getPosition()).getCustomView().findViewById(R.id.textQiangGouTitle);
                                    TextPaint paint = textQiangGouTitle.getPaint();
                                    paint.setFakeBoldText(true);
                                    isSelect = true;
                                }
                                adapter.removeAll();
                                adapter.addAll(indexGoodsData);
                                if (tablayoutHeaderX.getVisibility() == View.VISIBLE) {
                                    recyclerView.getRecyclerView().scrollBy(0, indexBannerHeight);
                                }
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
            select(tab);
        }
    }


    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_CATE;
        HashMap<String, String> params = new HashMap<>();
        try {
            params.put("uid", userInfo.getUid());
        } catch (Exception e) {
        }
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

}
