package com.sxbwstxpay.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sxbwstxpay.activity.CeShiSYActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.BannerBean;
import com.sxbwstxpay.model.IndexStyle;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.RecycleViewDistancaUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.LocalImageHolderView;
import com.sxbwstxpay.viewholder.ZhuanShuCDViewHolder;

import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ZhuanShuCDFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZhuanShuCDFragment extends ZjbBaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private View mInflate;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<IndexStyle.DataBean> adapter;
    private String cid;
    private FloatingActionButton top;

    public ZhuanShuCDFragment() {
    }

    private List<IndexStyle.CateBean> cateBeanList;
    private int page = 1;
    private List<BannerBean> indexGoodsBanner;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.STYLE:
                    cid = (String) intent.getSerializableExtra(Constant.INTENT_KEY.STYLE);
                    onRefresh();
                    break;
                default:
                    break;
            }
        }
    };
    public static ZhuanShuCDFragment newInstance(String param1) {
        ZhuanShuCDFragment fragment = new ZhuanShuCDFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_zhuan_shu_cd, container, false);
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

    }
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.STYLE);
        mContext.registerReceiver(receiver, filter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(receiver);
    }
    @Override
    protected void findID() {
        recyclerView = mInflate.findViewById(R.id.recyclerView);
        top = mInflate.findViewById(R.id.top);
    }

    @Override
    protected void initViews() {
        initRecycler();
    }

    @Override
    protected void setListeners() {
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(mContext,R.color.background), (int) getResources().getDimension(R.dimen.line_width3), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<IndexStyle.DataBean>(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_zhuanshu;
                return new ZhuanShuCDViewHolder(parent, layout);
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private MyAdapter adapterGrid;
            private GridView gridView;
            private ConvenientBanner banner;
            private TextView textTitle;

            @Override
            public View onCreateView(ViewGroup parent) {
                View header_xian_shi_qg = LayoutInflater.from(mContext).inflate(R.layout.header_shou_ye, null);
                banner = (ConvenientBanner) header_xian_shi_qg.findViewById(R.id.banner);
                ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
                int screenWidth = ScreenUtils.getScreenWidth(mContext);
                layoutParams.width = screenWidth;
                layoutParams.height = (int) ((float) screenWidth * 936f / 1080f);
                banner.setLayoutParams(layoutParams);
                banner.setScrollDuration(1000);
                banner.startTurning(3000);
                gridView = (GridView) header_xian_shi_qg.findViewById(R.id.gridView);
                gridView.setNumColumns(5);
                adapterGrid = new MyAdapter();
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i==2){
                            Intent intent=new Intent();
                            intent.setClass(mContext, CeShiSYActivity.class);
                            startActivity(intent);
                        }else {
                            cid=cateBeanList.get(i).getId();
                            onRefresh();
                        }
                    }
                });
                textTitle = (TextView) header_xian_shi_qg.findViewById(R.id.textTitle);
                textTitle.setText("专属潮搭");
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
                        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_shouye, null);
                        holder.textTitle = (TextView) convertView.findViewById(R.id.textTitle);
                        holder.imageImg = (ImageView) convertView.findViewById(R.id.imageImg);
                        convertView.setTag(holder);
                    } else {
                        holder = (ViewHolder) convertView.getTag();
                    }
                    holder.textTitle.setText(cateBeanList.get(position).getName());
                    if (cateBeanList.get(position).getAct()==1){
                        holder.textTitle.setTextColor(ContextCompat.getColor(mContext,R.color.basic_color));
                    }else {
                        holder.textTitle.setTextColor(ContextCompat.getColor(mContext,R.color.light_black));

                    }
                    GlideApp.with(mContext)
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
                ApiClient.post(mContext, getXianShiQGOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.LogShitou("XianShiQGFragment--限时抢购更多", s + "");
                        try {
                            page++;
                            IndexStyle indexGoods = GsonUtils.parseJSON(s, IndexStyle.class);
                            int status = indexGoods.getStatus();
                            if (status == 1) {
                                List<IndexStyle.DataBean> indexGoodsData = indexGoods.getData();
                                adapter.addAll(indexGoodsData);
                            } else if (status == 3) {
                                MyDialog.showReLoginDialog(mContext);
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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int scrollY = RecycleViewDistancaUtil.getDistance(recyclerView, 0);
                float guangGaoHeight = getResources().getDimension(R.dimen.guanLiDianPuTop);
                if (scrollY <= guangGaoHeight - 0 && scrollY >= 0) {
                    top.setVisibility(View.GONE);
                } else {
                    top.setVisibility(View.VISIBLE);
                }
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Intent intent = new Intent();
//                intent.putExtra(Constant.INTENT_KEY.id, adapter.getItem(position).getGoods_id());
//                intent.setClass(mContext, ChanPinXQActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent);
            }
        });
        recyclerView.setRefreshListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getXianShiQGOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_STYLE;
        HashMap<String, String> params = new HashMap<>();
        if (userInfo!=null){
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("p", page + "");
        params.put("cid", cid);
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        page = 1;
        ApiClient.post(mContext, getXianShiQGOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("限时购", s);
                try {
                    page++;
                    IndexStyle indexStyle = GsonUtils.parseJSON(s, IndexStyle.class);
                    if (indexStyle.getStatus() == 1) {
                        cateBeanList = indexStyle.getCate();
                        indexGoodsBanner = indexStyle.getBanner();
                        List<IndexStyle.DataBean> indexGoodsData = indexStyle.getData();
                        adapter.clear();
                        adapter.addAll(indexGoodsData);
                    } else if (indexStyle.getStatus() == 3) {
                        MyDialog.showReLoginDialog(mContext);
                    } else {
                        showError(indexStyle.getInfo());
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
                    View view_loaderror = LayoutInflater.from(mContext).inflate(R.layout.view_loaderror, null);
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
}
