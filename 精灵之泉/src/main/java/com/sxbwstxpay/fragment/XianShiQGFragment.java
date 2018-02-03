package com.sxbwstxpay.fragment;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.sxbwstxpay.activity.MainActivity;
import com.sxbwstxpay.activity.SouSuoActivity;
import com.sxbwstxpay.activity.StoreListActivity;
import com.sxbwstxpay.activity.WebActivity;
import com.sxbwstxpay.activity.WebHongBaoActivity;
import com.sxbwstxpay.activity.XuanPinSJActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ToLoginActivity;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.BannerBean;
import com.sxbwstxpay.model.IndexBonusbefore;
import com.sxbwstxpay.model.IndexBonusdown;
import com.sxbwstxpay.model.IndexBonusget;
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
import java.util.Random;
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
    private ImageView imageHongBaoDialog;
    private boolean isHongBaoShow = false;
    private Dialog mDialog;

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
        imageHongBaoDialog = (ImageView) mInflate.findViewById(R.id.imageHongBaoDialog);
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
        indexBannerHeight = (int) ((float) screenWidth * Constant.VALUE.IndexBannerHeight / 1080f) + (int) DpUtils.convertDpToPixel(195, getActivity());
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
                            case "score":
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
        imageHongBaoDialog.setOnClickListener(this);
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
        if (!isHongBaoShow) {
            hongBaoQingQing();
            isHongBaoShow = true;
        }
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
            case R.id.imageHongBaoDialog:
                hongBaoQingQing();
                break;
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
    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getHongBaoQQOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_BONUSDOWN;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }

    /**
     * 红包请求
     */
    private void hongBaoQingQing() {

        showLoadingDialog();
        ApiClient.post(getActivity(), getHongBaoQQOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ShengQianCZFragment--onSuccess", s + "");
                try {
                    IndexBonusdown indexBonusdown = GsonUtils.parseJSON(s, IndexBonusdown.class);
                    if (indexBonusdown.getStatus() == 1) {
                        if (indexBonusdown.getDown() == 1) {
                            hongBaoDialog();
                            imageHongBaoDialog.setVisibility(View.VISIBLE);
                        } else {
                            imageHongBaoDialog.setVisibility(View.GONE);
                        }
                    } else if (indexBonusdown.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getContext());
                    } else {
                        Toast.makeText(getActivity(), indexBonusdown.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * 红包弹窗
     */
    @SuppressLint("WrongConstant")
    private void hongBaoDialog() {
        if (mDialog == null) {
            int screenWidth = ScreenUtils.getScreenWidth(getActivity());
            int screenHeight = ScreenUtils.getScreenHeight(getActivity());


            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_hongbao, null);
            RelativeLayout relaHongBao = (RelativeLayout) inflate.findViewById(R.id.relaHongBao);


            for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.hongbao002);
                int anInt = new Random().nextInt(40);
                float hongBaoSize = DpUtils.convertDpToPixel((anInt + 60), getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) hongBaoSize, (int) hongBaoSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams.rightMargin = new Random().nextInt(screenWidth) - (int) hongBaoSize;
                layoutParams.topMargin = -(int) hongBaoSize;
                relaHongBao.addView(imageView, layoutParams);
                PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("translationY", screenHeight + hongBaoSize);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holder02);
                int duration = new Random().nextInt(3000) + 2000;
                int delay = new Random().nextInt(2500);
                animator.setDuration(duration);
                animator.setStartDelay(delay);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.INFINITE);
                animator.start();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qiangHongBao();
                    }
                });
            }
            for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.hongbao002);
                int anInt = new Random().nextInt(40);
                float hongBaoSize = DpUtils.convertDpToPixel((anInt + 60), getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) hongBaoSize, (int) hongBaoSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                layoutParams.leftMargin = new Random().nextInt(screenWidth) - (int) hongBaoSize;
                layoutParams.topMargin = -(int) hongBaoSize;
                relaHongBao.addView(imageView, layoutParams);
                PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("translationY", screenHeight + hongBaoSize);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holder02);
                int duration = new Random().nextInt(3000) + 2000;
                int delay = new Random().nextInt(2500);
                animator.setDuration(duration);
                animator.setStartDelay(delay);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.INFINITE);
                animator.start();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qiangHongBao();
                    }
                });
            }

            for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.hongbao001);
                int anInt = new Random().nextInt(40);
                float hongBaoSize = DpUtils.convertDpToPixel((anInt + 80), getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) hongBaoSize, (int) hongBaoSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams.rightMargin = new Random().nextInt(screenWidth) - (int) hongBaoSize;
                layoutParams.topMargin = -(int) hongBaoSize;
                relaHongBao.addView(imageView, layoutParams);
                PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("translationY", screenHeight + hongBaoSize);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holder02);
                int duration = new Random().nextInt(5000) + 3000;
                int delay = new Random().nextInt(4000);
                animator.setDuration(duration);
                animator.setStartDelay(delay);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.INFINITE);
                animator.start();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qiangHongBao();
                    }
                });
            }
            for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.hongbao001);
                int anInt = new Random().nextInt(40);
                float hongBaoSize = DpUtils.convertDpToPixel((anInt + 80), getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) hongBaoSize, (int) hongBaoSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                layoutParams.leftMargin = new Random().nextInt(screenWidth) - (int) hongBaoSize;
                layoutParams.topMargin = -(int) hongBaoSize;
                relaHongBao.addView(imageView, layoutParams);
                PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("translationY", screenHeight + hongBaoSize);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holder02);
                int duration = new Random().nextInt(5000) + 3000;
                int delay = new Random().nextInt(4000);
                animator.setDuration(duration);
                animator.setStartDelay(delay);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.INFINITE);
                animator.start();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qiangHongBao();
                    }
                });
            }
            for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.hongbao003);
                int anInt = new Random().nextInt(40);
                float hongBaoSize = DpUtils.convertDpToPixel((anInt + 80), getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) hongBaoSize, (int) hongBaoSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams.rightMargin = new Random().nextInt(screenWidth) - (int) hongBaoSize;
                layoutParams.topMargin = -(int) hongBaoSize;
                relaHongBao.addView(imageView, layoutParams);
                PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("translationY", screenHeight + hongBaoSize);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holder02);
                int duration = new Random().nextInt(5000) + 3000;
                int delay = new Random().nextInt(4000);
                animator.setDuration(duration);
                animator.setStartDelay(delay);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.INFINITE);
                animator.start();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qiangHongBao();
                    }
                });
            }

            for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.hongbao003);
                int anInt = new Random().nextInt(40);
                float hongBaoSize = DpUtils.convertDpToPixel((anInt + 80), getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) hongBaoSize, (int) hongBaoSize);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                layoutParams.leftMargin = new Random().nextInt(screenWidth) - (int) hongBaoSize;
                layoutParams.topMargin = -(int) hongBaoSize;
                relaHongBao.addView(imageView, layoutParams);
                PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("translationY", screenHeight + hongBaoSize);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holder02);
                int duration = new Random().nextInt(5000) + 3000;
                int delay = new Random().nextInt(4000);
                animator.setDuration(duration);
                animator.setStartDelay(delay);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.INFINITE);
                animator.start();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qiangHongBao();
                    }
                });
            }

//            float hongbao62 = DpUtils.convertDpToPixel(62f, getActivity());
//            float hongbao85 = DpUtils.convertDpToPixel(85f, getActivity());
//            float widthF09 = 0.3f;
//            float widthF08 = 1f;
//
//            ImageView imageHongBao09 = new ImageView(getActivity());
//            imageHongBao09.setImageResource(R.mipmap.hongbao02);
//            RelativeLayout.LayoutParams layoutParams09 = new RelativeLayout.LayoutParams((int) hongbao62, (int) hongbao62);
//            layoutParams09.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
//            layoutParams09.rightMargin = -(int) hongbao62;
//            layoutParams09.topMargin = (int) (screenHeight * (1 - widthF09));
//            relaHongBao.addView(imageHongBao09, layoutParams09);
//            PropertyValuesHolder holder0901 = PropertyValuesHolder.ofFloat("translationX", -screenHeight * widthF09);
//            PropertyValuesHolder holder0902 = PropertyValuesHolder.ofFloat("translationY", (screenHeight * widthF09) * 1.64f);
//            ObjectAnimator animator09 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao09, holder0901, holder0902);
//            animator09.setDuration(6000);
//            animator09.setRepeatCount(ValueAnimator.INFINITE);
//            animator09.setRepeatMode(ValueAnimator.INFINITE);
//            animator09.start();
//            imageHongBao09.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao0801 = new ImageView(getActivity());
//            imageHongBao0801.setImageResource(R.mipmap.hongbao04);
//            RelativeLayout.LayoutParams layoutParams08 = new RelativeLayout.LayoutParams((int) hongbao85, (int) hongbao85);
//            layoutParams08.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
//            layoutParams08.rightMargin = -(int) hongbao85 * 3;
//            float hongbao07 = DpUtils.convertDpToPixel(42f, getActivity());
//            float widthF07 = 0.3f;
//            layoutParams08.topMargin = -(int) hongbao85 * 3;
//            relaHongBao.addView(imageHongBao0801, layoutParams08);
//            PropertyValuesHolder holder0801 = PropertyValuesHolder.ofFloat("translationX", -screenWidth * widthF08 - hongbao85 * 3);
//            PropertyValuesHolder holder0802 = PropertyValuesHolder.ofFloat("translationY", (screenWidth * widthF08 + hongbao85 * 3) * 1.64f);
//            ObjectAnimator animator0801 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao0801, holder0801, holder0802);
//            animator0801.setDuration(6500);
//            animator0801.setStartDelay(1900);
//            animator0801.setRepeatCount(ValueAnimator.INFINITE);
//            animator0801.setRepeatMode(ValueAnimator.INFINITE);
//            animator0801.start();
//            imageHongBao0801.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao08 = new ImageView(getActivity());
//            imageHongBao08.setImageResource(R.mipmap.hongbao04);
//            relaHongBao.addView(imageHongBao08, layoutParams08);
//            ObjectAnimator animator08 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao08, holder0801, holder0802);
//            animator08.setDuration(6000);
//            animator08.setRepeatCount(ValueAnimator.INFINITE);
//            animator08.setRepeatMode(ValueAnimator.INFINITE);
//            animator08.start();
//            imageHongBao08.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao07 = new ImageView(getActivity());
//            imageHongBao07.setImageResource(R.mipmap.hongbao01);
//            RelativeLayout.LayoutParams layoutParams07 = new RelativeLayout.LayoutParams((int) hongbao07, (int) hongbao07);
//            layoutParams07.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
//            layoutParams07.rightMargin = -(int) hongbao07;
//            layoutParams07.topMargin = (int) (screenHeight * (1 - widthF07));
//            relaHongBao.addView(imageHongBao07, layoutParams07);
//            PropertyValuesHolder holder0701 = PropertyValuesHolder.ofFloat("translationX", -screenHeight * widthF07);
//            PropertyValuesHolder holder0702 = PropertyValuesHolder.ofFloat("translationY", (screenHeight * widthF07) * 1.64f);
//            ObjectAnimator animator07 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao07, holder0701, holder0702);
//            animator07.setDuration(6500);
//            animator07.setRepeatCount(ValueAnimator.INFINITE);
//            animator07.setRepeatMode(ValueAnimator.INFINITE);
//            animator07.start();
//            imageHongBao07.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao0601 = new ImageView(getActivity());
//            float hongbao06 = DpUtils.convertDpToPixel(42f, getActivity());
//            imageHongBao0601.setImageResource(R.mipmap.hongbao03);
//            RelativeLayout.LayoutParams layoutParams06 = new RelativeLayout.LayoutParams((int) hongbao06, (int) hongbao06);
//            float widthF06 = 1f;
//            layoutParams06.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
//            layoutParams06.rightMargin = -(int) hongbao06 * 3;
//            layoutParams06.topMargin = -(int) hongbao06;
//            relaHongBao.addView(imageHongBao0601, layoutParams06);
//            PropertyValuesHolder holder0601 = PropertyValuesHolder.ofFloat("translationX", -screenWidth * widthF06 - hongbao06 * 3);
//            PropertyValuesHolder holder0602 = PropertyValuesHolder.ofFloat("translationY", (screenWidth * widthF06 + hongbao06 * 3) * 1.64f);
//            ObjectAnimator animator06 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao0601, holder0601, holder0602);
//            animator06.setDuration(3000);
//            animator06.setStartDelay(1150);
//            animator06.setRepeatCount(ValueAnimator.INFINITE);
//            animator06.setRepeatMode(ValueAnimator.INFINITE);
//            animator06.start();
//            imageHongBao0601.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao06 = new ImageView(getActivity());
//            imageHongBao06.setImageResource(R.mipmap.hongbao03);
//            relaHongBao.addView(imageHongBao06, layoutParams06);
//            ObjectAnimator animator0601 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao06, holder0601, holder0602);
//            animator0601.setDuration(3500);
//            animator0601.setRepeatCount(ValueAnimator.INFINITE);
//            animator0601.setRepeatMode(ValueAnimator.INFINITE);
//            animator0601.start();
//            imageHongBao06.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao0502 = new ImageView(getActivity());
//            float hongbao05 = DpUtils.convertDpToPixel(62f, getActivity());
//            imageHongBao0502.setImageResource(R.mipmap.hongbao03);
//            RelativeLayout.LayoutParams layoutParams05 = new RelativeLayout.LayoutParams((int) hongbao05, (int) hongbao05);
//            float widthF05 = 0.8f;
//            layoutParams05.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
//            layoutParams05.leftMargin = (int) (screenWidth * widthF05);
//            layoutParams05.topMargin = -(int) hongbao05;
//            relaHongBao.addView(imageHongBao0502, layoutParams05);
//            PropertyValuesHolder holder0501 = PropertyValuesHolder.ofFloat("translationX", -screenWidth * widthF05 - hongbao05);
//            PropertyValuesHolder holder0502 = PropertyValuesHolder.ofFloat("translationY", (screenWidth * widthF05 + hongbao05) * 1.64f);
//            ObjectAnimator animator0501 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao0502, holder0501, holder0502);
//            animator0501.setDuration(6800);
//            animator06.setStartDelay(3200);
//            animator0501.setRepeatCount(ValueAnimator.INFINITE);
//            animator0501.setRepeatMode(ValueAnimator.INFINITE);
//            animator0501.start();
//            imageHongBao0502.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao0503 = new ImageView(getActivity());
//            imageHongBao0503.setImageResource(R.mipmap.hongbao03);
//            relaHongBao.addView(imageHongBao0503, layoutParams05);
//            ObjectAnimator animator05 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao0503, holder0501, holder0502);
//            animator05.setDuration(3500);
//            animator05.setRepeatCount(ValueAnimator.INFINITE);
//            animator05.setRepeatMode(ValueAnimator.INFINITE);
//            animator05.start();
//            imageHongBao0503.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao0403 = new ImageView(getActivity());
//            float hongbao04 = DpUtils.convertDpToPixel(85f, getActivity());
//            imageHongBao0403.setImageResource(R.mipmap.hongbao04);
//            RelativeLayout.LayoutParams layoutParams04 = new RelativeLayout.LayoutParams((int) hongbao04, (int) hongbao04);
//            float widthF04 = 1f;
//            layoutParams04.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
//            layoutParams04.rightMargin = -(int) hongbao04;
//            layoutParams04.topMargin = -(int) hongbao04;
//            relaHongBao.addView(imageHongBao0403, layoutParams04);
//            PropertyValuesHolder holder0401 = PropertyValuesHolder.ofFloat("translationX", -screenWidth * widthF04 - hongbao04);
//            PropertyValuesHolder holder0402 = PropertyValuesHolder.ofFloat("translationY", (screenWidth * widthF04 + hongbao04) * 1.64f);
//            ObjectAnimator animator0401 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao0403, holder0401, holder0402);
//            animator0401.setDuration(4600);
//            animator0401.setStartDelay(3100);
//            animator0401.setRepeatCount(ValueAnimator.INFINITE);
//            animator0401.setRepeatMode(ValueAnimator.INFINITE);
//            animator0401.start();
//            imageHongBao0403.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao04 = new ImageView(getActivity());
//            imageHongBao04.setImageResource(R.mipmap.hongbao04);
//            relaHongBao.addView(imageHongBao04, layoutParams04);
//            ObjectAnimator animator04 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao04, holder0401, holder0402);
//            animator04.setDuration(6000);
//            animator04.setRepeatCount(ValueAnimator.INFINITE);
//            animator04.setRepeatMode(ValueAnimator.INFINITE);
//            animator04.start();
//            imageHongBao04.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//
//            ImageView imageHongBao0301 = new ImageView(getActivity());
//            float hongbao03 = DpUtils.convertDpToPixel(62f, getActivity());
//            imageHongBao0301.setImageResource(R.mipmap.hongbao03);
//            RelativeLayout.LayoutParams layoutParams03 = new RelativeLayout.LayoutParams((int) hongbao03, (int) hongbao03);
//            float widthF03 = 1f;
//            layoutParams03.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
//            layoutParams03.rightMargin = -(int) hongbao03;
//            layoutParams03.topMargin = -(int) hongbao03;
//            relaHongBao.addView(imageHongBao0301, layoutParams03);
//            PropertyValuesHolder holder0301 = PropertyValuesHolder.ofFloat("translationX", -screenWidth * widthF03 - hongbao03);
//            PropertyValuesHolder holder0302 = PropertyValuesHolder.ofFloat("translationY", (screenWidth * widthF03 + hongbao03) * 1.64f);
//            ObjectAnimator animator0301 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao0301, holder0301, holder0302);
//            animator0301.setDuration(3000);
//            animator0301.setStartDelay(1200);
//            animator0301.setRepeatCount(ValueAnimator.INFINITE);
//            animator0301.setRepeatMode(ValueAnimator.INFINITE);
//            animator0301.start();
//            imageHongBao0301.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao03 = new ImageView(getActivity());
//            imageHongBao03.setImageResource(R.mipmap.hongbao03);
//            relaHongBao.addView(imageHongBao03, layoutParams03);
//            ObjectAnimator animator03 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao03, holder0301, holder0302);
//            animator03.setDuration(3500);
//            animator03.setRepeatCount(ValueAnimator.INFINITE);
//            animator03.setRepeatMode(ValueAnimator.INFINITE);
//            animator03.start();
//            imageHongBao03.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao02 = new ImageView(getActivity());
//            imageHongBao02.setImageResource(R.mipmap.hongbao02);
//            float hongbao02 = DpUtils.convertDpToPixel(60f, getActivity());
//            RelativeLayout.LayoutParams layoutParams02 = new RelativeLayout.LayoutParams((int) hongbao02, (int) hongbao02);
//            float widthF02 = 0.40f;
//            layoutParams02.leftMargin = (int) (screenWidth * widthF02);
//            layoutParams02.topMargin = -(int) hongbao02;
//            relaHongBao.addView(imageHongBao02, layoutParams02);
//            PropertyValuesHolder holder0201 = PropertyValuesHolder.ofFloat("translationX", -screenWidth * widthF02 - hongbao02);
//            PropertyValuesHolder holder0202 = PropertyValuesHolder.ofFloat("translationY", (screenWidth * widthF02 + hongbao02) * 1.64f);
//            ObjectAnimator animator02 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao02, holder0201, holder0202);
//            animator02.setDuration(3500);
//            animator02.setRepeatCount(ValueAnimator.INFINITE);
//            animator02.setRepeatMode(ValueAnimator.INFINITE);
//            animator02.start();
//            imageHongBao02.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });
//
//            ImageView imageHongBao01 = new ImageView(getActivity());
//            imageHongBao01.setImageResource(R.mipmap.hongbao01);
//            float hongbao01 = DpUtils.convertDpToPixel(40f, getActivity());
//            RelativeLayout.LayoutParams layoutParams01 = new RelativeLayout.LayoutParams((int) hongbao01, (int) hongbao01);
//            float widthF01 = 0.40f;
//            layoutParams01.leftMargin = (int) (screenWidth * widthF01);
//            layoutParams01.topMargin = -(int) hongbao01;
//            relaHongBao.addView(imageHongBao01, layoutParams01);
//            PropertyValuesHolder holder0101 = PropertyValuesHolder.ofFloat("translationX", -screenWidth * widthF01 - hongbao01);
//            PropertyValuesHolder holder0102 = PropertyValuesHolder.ofFloat("translationY", (screenWidth * widthF01 + hongbao01) * 1.64f);
//            ObjectAnimator animator01 = ObjectAnimator.ofPropertyValuesHolder(imageHongBao01, holder0101, holder0102);
//            animator01.setDuration(3000);
//            animator01.setRepeatCount(ValueAnimator.INFINITE);
//            animator01.setRepeatMode(ValueAnimator.INFINITE);
//            animator01.setInterpolator(new DecelerateInterpolator());
//            animator01.start();
//            imageHongBao01.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    qiangHongBao();
//                }
//            });

            mDialog = new Dialog(getActivity(), R.style.mydialog);
            mDialog.setContentView(inflate);
            mDialog.show();
        } else {
            mDialog.show();
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getQiangHongBAoOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_BONUSBEFORE;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_RESULT_CODE.HONG_BAO && resultCode == Constant.REQUEST_RESULT_CODE.HONG_BAO) {
            IndexBonusget indexBonusget = (IndexBonusget) data.getSerializableExtra(Constant.INTENT_KEY.value);
            showHongBaoDialog(indexBonusget);
        }
    }

    /**
     * 抢到红包
     *
     * @param indexBonusget
     */
    private void showHongBaoDialog(IndexBonusget indexBonusget) {
        int screenWidth = ScreenUtils.getScreenWidth(getActivity());
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_hongbaolq, null);
        TextView texthongBao = (TextView) inflate.findViewById(R.id.texthongBao);

        texthongBao.setText(indexBonusget.getMoney() + "元");
        View linearhongBao = inflate.findViewById(R.id.linearhongBao);
        ViewGroup.LayoutParams layoutParams = linearhongBao.getLayoutParams();
        int width = screenWidth - (int) DpUtils.convertDpToPixel(40 * 2, getActivity());
        layoutParams.width = width;
        layoutParams.height = (int) (width * 1.15f);
        linearhongBao.setLayoutParams(layoutParams);
        final Dialog mDialog = new Dialog(getActivity(), R.style.mydialog);
        mDialog.setContentView(inflate);
        mDialog.show();
        inflate.findViewById(R.id.viewhongBao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        Toast.makeText(getActivity(), indexBonusget.getDes(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 抢红包
     */
    private void qiangHongBao() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        if (!isLogin) {
            ToLoginActivity.toLoginActivity(getActivity());
            return;
        }
        showLoadingDialog();
        ApiClient.post(getActivity(), getQiangHongBAoOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ShengQianCZFragment--onSuccess", s + "");
                try {
                    IndexBonusbefore indexBonusbefore = GsonUtils.parseJSON(s, IndexBonusbefore.class);
                    if (indexBonusbefore.getStatus() == 1) {
                        if (indexBonusbefore.getGoRealName() == 1) {
                            MyDialog.showTipDialog(getActivity(), indexBonusbefore.getDes());
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("提示")
                                    .setMessage(indexBonusbefore.getDes())
                                    .setNegativeButton("否", null)
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            ((MainActivity) getActivity()).mTabHost.setCurrentTab(3);
                                        }
                                    })
                                    .show();
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), WebHongBaoActivity.class);
                            intent.putExtra(Constant.INTENT_KEY.TITLE, "领取红包");
                            intent.putExtra(Constant.INTENT_KEY.URL, indexBonusbefore.getUrl());
                            startActivityForResult(intent, Constant.REQUEST_RESULT_CODE.HONG_BAO);
                        }
                    } else if (indexBonusbefore.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        Toast.makeText(getActivity(), indexBonusbefore.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
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
