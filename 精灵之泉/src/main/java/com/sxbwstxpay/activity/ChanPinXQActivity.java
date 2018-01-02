package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.adapter.TagAdapter;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.FlowTagLayout;
import com.sxbwstxpay.customview.OnTagSelectListener;
import com.sxbwstxpay.model.AddCar;
import com.sxbwstxpay.model.CartAddcart;
import com.sxbwstxpay.model.CartIndex;
import com.sxbwstxpay.model.GoodsInfo;
import com.sxbwstxpay.model.IndexCate;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.RecommBean;
import com.sxbwstxpay.model.ShareBean;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.RecycleViewDistancaUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.util.StringUtil;
import com.sxbwstxpay.viewholder.ChanPinXQViewHolder;
import com.sxbwstxpay.viewholder.LocalImageChanPinHolderView;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Response;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class ChanPinXQActivity extends ZjbBaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private View viewBar;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<RecommBean> adapter;
    private TextView textViewTitle;
    private int viewBarHeight;
    private String id;
    private GoodsInfo.AdBean goodsInfoAd;
    private int num = 1;
    private AlertDialog alertDialog1;
    private TagAdapter tagAdapter;
    private IWXAPI api = WXAPIFactory.createWXAPI(ChanPinXQActivity.this, Constant.WXAPPID, true);
    private Tencent mTencent;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.WX_SHARE:
                    if (isShow) {
                        MyDialog.showTipDialog(ChanPinXQActivity.this, "分享成功");
                    }
                    break;
                case Constant.BROADCASTCODE.WX_SHARE_FAIL:
                    if (isShow) {
                        MyDialog.showTipDialog(ChanPinXQActivity.this, "取消分享");
                    }
                    break;
                case Constant.BROADCASTCODE.zhiFuGuanBi:
                    finish();
                    break;
                case Constant.BROADCASTCODE.GouWuCheNum:
                    gouWuCheNum();
                    break;
            }
        }
    };
    private Timer timer;
    private TextView textDaoJiShi;
    private int countdown;
    private View viewKeGouMai;
    private View viewMaiMaiMai;
    private boolean isShow;
    private ImageView imageBack;
    private Badge badge;
    private View viewGouWuChe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chan_pin_xq);
        mTencent = Tencent.createInstance(Constant.QQ_ID, this.getApplicationContext());
        init(ChanPinXQActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        id = intent.getStringExtra(Constant.INTENT_KEY.id);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        viewMaiMaiMai = findViewById(R.id.viewMaiMaiMai);
        imageBack = (ImageView) findViewById(R.id.imageBack);
        viewGouWuChe = findViewById(R.id.viewGouWuChe);
        badge = new QBadgeView(this)
                .setBadgeTextColor(Color.WHITE)
                .setBadgeTextSize(8f, true)
                .setBadgeBackgroundColor(getResources().getColor(R.color.red))
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(3f, 0f, true);
    }

    @Override
    protected void initViews() {
        textViewTitle.setText("产品详情");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        viewBarHeight = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        layoutParams.height = viewBarHeight;
        viewBar.setLayoutParams(layoutParams);
        viewBar.getBackground().mutate().setAlpha(0);
        imageBack.getBackground().mutate().setAlpha(255);
        textViewTitle.setAlpha(0);
        viewMaiMaiMai.setVisibility(View.GONE);
        initRecycle();
    }

    @Override
    protected void setListeners() {
        viewGouWuChe.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        findViewById(R.id.textMai1).setOnClickListener(this);
        findViewById(R.id.textMai2).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    private void initRecycle() {
        SpaceDecoration itemDecoration = new SpaceDecoration((int) DpUtils.convertDpToPixel(8f, this));
        itemDecoration.setPaddingStart(false);
        itemDecoration.setPaddingEdgeSide(false);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);
        int red = getResources().getColor(R.color.basic_color);
        recyclerView.setRefreshingColor(red);
        recyclerView.getSwipeToRefresh().setProgressViewOffset(true, 30, 220);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<RecommBean>(this) {
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
            private TextView textItem_num;
            private TextView textCountdownDes;
            private TextView textCountdown;
            private TextView textStock_num;
            private TextView textSale_add;
            private TextView textNum;
            private TextView textGoods_money;
            private TextView textPrice;
            private TextView textTitleChanPin;
            private int screenWidth;
            private TextView textIntro;
            private ListView listView02;
            private ConvenientBanner banner;
            private ListView listView01;
            private TabLayout tablayout;

            @Override
            public View onCreateView(ViewGroup parent) {
                View header_xhan_pin_xq = LayoutInflater.from(ChanPinXQActivity.this).inflate(R.layout.header_xhan_pin_xq, null);
                textTitleChanPin = (TextView) header_xhan_pin_xq.findViewById(R.id.textTitle);
                textPrice = (TextView) header_xhan_pin_xq.findViewById(R.id.textPrice);
                textGoods_money = (TextView) header_xhan_pin_xq.findViewById(R.id.textGoods_money);
                textNum = (TextView) header_xhan_pin_xq.findViewById(R.id.textNum);
                textSale_add = (TextView) header_xhan_pin_xq.findViewById(R.id.textSale_add);
                textStock_num = (TextView) header_xhan_pin_xq.findViewById(R.id.textStock_num);
                screenWidth = ScreenUtils.getScreenWidth(ChanPinXQActivity.this);
                banner = (ConvenientBanner) header_xhan_pin_xq.findViewById(R.id.banner);
                textItem_num = (TextView) header_xhan_pin_xq.findViewById(R.id.textItem_num);
                ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
                layoutParams.width = screenWidth;
                layoutParams.height = screenWidth;
                banner.setLayoutParams(layoutParams);
                banner.setScrollDuration(1000);
                banner.startTurning(3000);
                textIntro = (TextView) header_xhan_pin_xq.findViewById(R.id.textIntro);
                final View viewXiangQing = header_xhan_pin_xq.findViewById(R.id.viewXiangQing);
                listView01 = (ListView) header_xhan_pin_xq.findViewById(R.id.listView01);
                listView02 = (ListView) header_xhan_pin_xq.findViewById(R.id.listView02);
                tablayout = (TabLayout) header_xhan_pin_xq.findViewById(R.id.tablayout);
                for (int i = 0; i < 2; i++) {
                    View item_tablayout = LayoutInflater.from(ChanPinXQActivity.this).inflate(R.layout.item_tablayout, null);
                    TextView textTitle = (TextView) item_tablayout.findViewById(R.id.textTitle);
                    if (i == 0) {
                        textTitle.setText("宝贝详情");
                        this.tablayout.addTab(this.tablayout.newTab().setCustomView(item_tablayout), true);
                    } else {
                        textTitle.setText("规格参数");
                        this.tablayout.addTab(this.tablayout.newTab().setCustomView(item_tablayout), false);
                    }
                }
                viewXiangQing.setVisibility(View.VISIBLE);
                listView02.setVisibility(View.GONE);
                this.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if (tab.getPosition() == 0) {
                            viewXiangQing.setVisibility(View.VISIBLE);
                            listView02.setVisibility(View.GONE);
                        } else {
                            viewXiangQing.setVisibility(View.GONE);
                            listView02.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
                header_xhan_pin_xq.findViewById(R.id.viewGuiGe).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mai();
                    }
                });
                textCountdown = (TextView) header_xhan_pin_xq.findViewById(R.id.textCountdown);
                textCountdownDes = (TextView) header_xhan_pin_xq.findViewById(R.id.textCountdownDes);
                header_xhan_pin_xq.findViewById(R.id.viewChaKanSC).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra(Constant.INTENT_KEY.id,id);
                        intent.setClass(ChanPinXQActivity.this,ShangPinScActivity.class);
                        startActivity(intent);
                    }
                });
                return header_xhan_pin_xq;
            }

            @Override
            public void onBindView(View headerView) {
                if (goodsInfoAd != null) {
                    banner.setPages(new CBViewHolderCreator() {
                        @Override
                        public Object createHolder() {
                            return new LocalImageChanPinHolderView();
                        }
                    }, goodsInfoAd.getBanner());
                    banner.setPageIndicator(new int[]{R.drawable.shape_indicator_normal, R.drawable.shape_indicator_selected});
                    if (TextUtils.isEmpty(goodsInfoAd.getIntro())) {
                        textIntro.setVisibility(View.GONE);
                    } else {
                        textIntro.setVisibility(View.VISIBLE);
                        textIntro.setText(goodsInfoAd.getIntro());
                    }
                    listView01.setAdapter(new MyAdapter(goodsInfoAd.getImgs()));
                    listView02.setAdapter(new MyAdapter(goodsInfoAd.getImgs2()));
                    textTitleChanPin.setText(goodsInfoAd.getTitle());
                    textPrice.setText("¥" + goodsInfoAd.getPrice());
                    textGoods_money.setText("赚" + goodsInfoAd.getGoods_money());
                    textNum.setText("浏览" + goodsInfoAd.getNum() + "次");
                    textSale_add.setText(goodsInfoAd.getSale_add() + "人在售");
                    textStock_num.setText("剩余" + goodsInfoAd.getStock_num() + "库存");
                    countdown = goodsInfoAd.getCountdown();
                    textCountdownDes.setText(goodsInfoAd.getCountdownDes());
                    textItem_num.setText(goodsInfoAd.getItem_num()+"组");
                    LogUtil.LogShitou("ChanPinXQActivity--countdown", "" + countdown);
                    if (timer != null) {
                        timer.cancel();
                    }
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (countdown >= 1) {
                                        countdown--;
                                        textCountdown.setText(StringUtil.TimeFormat(countdown));
                                        if (textDaoJiShi != null && viewKeGouMai != null) {
                                            textDaoJiShi.setText("倒计时 " + StringUtil.TimeFormat(countdown));
                                            if (countdown > 0) {
                                                textDaoJiShi.setVisibility(View.VISIBLE);
                                                viewKeGouMai.setVisibility(View.GONE);
                                            } else {
                                                textDaoJiShi.setVisibility(View.GONE);
                                                viewKeGouMai.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    } else {
                                        textCountdown.setText("00:00:00");
                                        timer.cancel();
                                    }
                                }
                            });
                        }
                    }, 0, 1000);
                }
            }

            class MyAdapter extends BaseAdapter {
                private List<GoodsInfo.AdBean.ImgsBean> goodsInfoAdImgs = new ArrayList<>();

                public MyAdapter(List<GoodsInfo.AdBean.ImgsBean> goodsInfoAdImgs) {
                    this.goodsInfoAdImgs = goodsInfoAdImgs;
                }

                class ViewHolder {
                    public ImageView imageImg;
                }

                @Override
                public int getCount() {
                    return goodsInfoAdImgs.size();
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
                        convertView = LayoutInflater.from(ChanPinXQActivity.this).inflate(R.layout.item_img_size, null);
                        holder.imageImg = (ImageView) convertView.findViewById(R.id.imageImg);
                        convertView.setTag(holder);
                    } else {
                        holder = (ViewHolder) convertView.getTag();
                    }
                    GlideApp.with(ChanPinXQActivity.this)
                            .asBitmap()
                            .load(goodsInfoAdImgs.get(position).getImg())
                            .placeholder(R.mipmap.ic_empty)
                            .override(screenWidth, (int) ((float) screenWidth * ((float) goodsInfoAdImgs.get(position).getH() / (float) goodsInfoAdImgs.get(position).getW())))
                            .into(holder.imageImg);
                    return convertView;
                }
            }
        });
        recyclerView.setRefreshListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.id, adapter.getItem(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setClass(ChanPinXQActivity.this, ChanPinXQActivity.class);
                startActivity(intent);
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
                    imageBack.getBackground().mutate().setAlpha(255 - i);
                    textViewTitle.setAlpha((float) i / 255f);
                } else {
                    viewBar.getBackground().mutate().setAlpha(255);
                    textViewTitle.setAlpha(1);
                }
            }
        });
    }


    /**
     * des： 加入购物车弹窗
     * author： ZhangJieBo
     * date： 2017/9/26 0026 下午 4:04
     */

    private void mai() {
        for (int i = 0; i < goodsInfoAd.getSize_str().size(); i++) {
            List<Boolean> isSelect = new ArrayList<>();
            for (int j = 0; j < goodsInfoAd.getSize_str().get(i).getContent().size(); j++) {
                isSelect.add(false);
            }
            goodsInfoAd.getSize_str().get(i).setIsSelect(isSelect);
        }
        LayoutInflater inflater = LayoutInflater.from(ChanPinXQActivity.this);
        View dialog_chan_pin = inflater.inflate(R.layout.dialog_chan_pin, null);
        textDaoJiShi = (TextView) dialog_chan_pin.findViewById(R.id.textDaoJiShi);
        viewKeGouMai = dialog_chan_pin.findViewById(R.id.viewKeGouMai);
        ImageView imageImg = (ImageView) dialog_chan_pin.findViewById(R.id.imageImg);
        TextView textTitleDialog = (TextView) dialog_chan_pin.findViewById(R.id.textTitleDialog);
        TextView textPriceDialog = (TextView) dialog_chan_pin.findViewById(R.id.textPriceDialog);
        TextView textZhuan = (TextView) dialog_chan_pin.findViewById(R.id.textZhuan);
        textTitleDialog.setText(goodsInfoAd.getTitle());
        textPriceDialog.setText("¥" + goodsInfoAd.getPrice());
        textZhuan.setText("赚" + goodsInfoAd.getGoods_money());
        GlideApp.with(ChanPinXQActivity.this)
                .asBitmap()
                .load(goodsInfoAd.getImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);

        if (countdown > 0) {
            textDaoJiShi.setVisibility(View.VISIBLE);
            viewKeGouMai.setVisibility(View.GONE);
        } else {
            textDaoJiShi.setVisibility(View.GONE);
            viewKeGouMai.setVisibility(View.VISIBLE);
        }
        final TextView textNum = (TextView) dialog_chan_pin.findViewById(R.id.textNum);
        textNum.setText(num + "");
        dialog_chan_pin.findViewById(R.id.textAddCar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCar(false);
            }
        });
        dialog_chan_pin.findViewById(R.id.textQuick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCar(true);
            }
        });
        dialog_chan_pin.findViewById(R.id.textAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num <= goodsInfoAd.getStock_num()) {
                    num++;
                    textNum.setText(num + "");
                } else {
                    Toast.makeText(ChanPinXQActivity.this, "库存不足", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog_chan_pin.findViewById(R.id.textDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num > 1) {
                    num--;
                    textNum.setText(num + "");
                }
            }
        });
        ListView listGuiZe = (ListView) dialog_chan_pin.findViewById(R.id.listGuiZe);
        if (goodsInfoAd.getSize_str().size() > 0) {
            listGuiZe.setVisibility(View.VISIBLE);
            listGuiZe.setAdapter(new MySizeAdapter());
        } else {
            listGuiZe.setVisibility(View.GONE);
        }
        alertDialog1 = new AlertDialog.Builder(ChanPinXQActivity.this, R.style.dialog)
                .setView(dialog_chan_pin)
                .create();
        alertDialog1.show();
        Window dialogWindow = alertDialog1.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogFenXiang);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = ChanPinXQActivity.this.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 1); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    class MySizeAdapter extends BaseAdapter {
        class ViewHolder {
            public TextView textName;
            public FlowTagLayout flowTagLayout;
        }

        @Override
        public int getCount() {
            return goodsInfoAd.getSize_str().size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(ChanPinXQActivity.this).inflate(R.layout.item_guige_list, null);
                holder.textName = (TextView) convertView.findViewById(R.id.textName);
                holder.flowTagLayout = (FlowTagLayout) convertView.findViewById(R.id.flowTagLayout);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            List<GoodsInfo.AdBean.SizeStrBean> sizeStrBeanList = goodsInfoAd.getSize_str();
            holder.textName.setText(sizeStrBeanList.get(position).getName());
            holder.flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
            tagAdapter = new TagAdapter(ChanPinXQActivity.this);
            holder.flowTagLayout.setAdapter(tagAdapter);
            List<String> content = sizeStrBeanList.get(position).getContent();
            tagAdapter.clearAndAddAll(content);
            holder.flowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
                @Override
                public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                    for (int i = 0; i < goodsInfoAd.getSize_str().get(position).getIsSelect().size(); i++) {
                        goodsInfoAd.getSize_str().get(position).getIsSelect().set(i, false);
                    }
                    for (int i = 0; i < selectedList.size(); i++) {
                        LogUtil.LogShitou("DaiYingYaoFragment--onItemSelect", "" + selectedList.get(i));
                        goodsInfoAd.getSize_str().get(position).getIsSelect().set(selectedList.get(i), true);
                    }
                }
            });
            return convertView;
        }
    }


    /**
     * des： 购物车新增
     * author： ZhangJieBo
     * date： 2017/9/26 0026 下午 2:20
     */
    private void addCar(final boolean quick) {
        List<String> spe_name = new ArrayList<>();
        if (goodsInfoAd.getSize_str().size() > 0) {
            for (int i = 0; i < goodsInfoAd.getSize_str().size(); i++) {
                for (int j = 0; j < goodsInfoAd.getSize_str().get(i).getIsSelect().size(); j++) {
                    if (goodsInfoAd.getSize_str().get(i).getIsSelect().get(j)) {
                        spe_name.add(goodsInfoAd.getSize_str().get(i).getContent().get(j));
                    }
                }
            }
        }
        if (spe_name.size() < goodsInfoAd.getSize_str().size()) {
            Toast.makeText(ChanPinXQActivity.this, "请选择规格", Toast.LENGTH_SHORT).show();
            return;
        }
        showLoadingDialog();
        String url = Constant.HOST + Constant.Url.CART_ADDCART;
        AddCar addCar ;
        if (quick){
            addCar = new AddCar(userInfo.getUid(), tokenTime, num + "", id,"1" ,spe_name);
        }else {
            addCar = new AddCar(userInfo.getUid(), tokenTime, num + "", id,"0", spe_name);
        }
        ApiClient.postJson(ChanPinXQActivity.this, url, GsonUtils.parseObject(addCar), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ChanPinXQActivity-购物车新增", s + "");
                try {
                    CartAddcart cartAddcart = GsonUtils.parseJSON(s, CartAddcart.class);
                    if (cartAddcart.getStatus() == 1) {
                        alertDialog1.dismiss();
                        if (quick) {

                            Intent intent = new Intent();
                            intent.setClass(ChanPinXQActivity.this, QueRenDDActivity.class);
                            List<CartIndex.CartBean> cartBeanList = new ArrayList<CartIndex.CartBean>();
                            cartBeanList.add(new CartIndex.CartBean(cartAddcart.getCartId()+""));
                            intent.putExtra(Constant.INTENT_KEY.value, new CartIndex(cartBeanList));
                            startActivity(intent);


//                            Intent intent = new Intent();
//                            intent.setClass(ChanPinXQActivity.this, GouWuCActivity.class);
//                            startActivity(intent);
                        }
                        Intent intent = new Intent();
                        intent.setAction(Constant.BROADCASTCODE.GouWuCheNum);
                        sendBroadcast(intent);
                    } else if (cartAddcart.getStatus() == 3) {
                        MyDialog.showReLoginDialog(ChanPinXQActivity.this);
                    } else {
                        Toast.makeText(ChanPinXQActivity.this, cartAddcart.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ChanPinXQActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(ChanPinXQActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.GOODS_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("id", id);
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("产品详情", s);
                try {
                    GoodsInfo goodsInfo = GsonUtils.parseJSON(s, GoodsInfo.class);
                    if (goodsInfo.getStatus() == 1) {
                        goodsInfoAd = goodsInfo.getAd();
                        List<RecommBean> goodsInfoRecomm = goodsInfo.getRecomm();
                        adapter.clear();
                        adapter.addAll(goodsInfoRecomm);
                        viewMaiMaiMai.setVisibility(View.VISIBLE);
                    } else if (goodsInfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(ChanPinXQActivity.this);
                    } else {
                        showError(goodsInfo.getInfo());
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
                View view_loaderror = LayoutInflater.from(ChanPinXQActivity.this).inflate(R.layout.view_loaderror, null);
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
        gouWuCheNum();
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObjectGouWuChe() {
        String url = Constant.HOST + Constant.Url.INDEX_CATE;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    private void gouWuCheNum() {
        ApiClient.post(ChanPinXQActivity.this, getOkObjectGouWuChe(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ChanPinXQActivity--onSuccess", "");
                try {
                    IndexCate indexCate = GsonUtils.parseJSON(s, IndexCate.class);
                    if (indexCate.getStatus() == 1) {
                        badge.setBadgeNumber(indexCate.getVipNum()).bindTarget(viewGouWuChe);
                    } else if (indexCate.getStatus() == 3) {
                        MyDialog.showReLoginDialog(ChanPinXQActivity.this);
                    } else {
                        Toast.makeText(ChanPinXQActivity.this, indexCate.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ChanPinXQActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(ChanPinXQActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.textMai1:
                mai();
                break;
            case R.id.textMai2:
                share(goodsInfoAd.getShare());
                break;
            case R.id.imageBack:
                finish();
                break;
            case R.id.viewGouWuChe:
                intent.setClass(this, GouWuCActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTencent.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShow = true;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE);
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE_FAIL);
        filter.addAction(Constant.BROADCASTCODE.zhiFuGuanBi);
        filter.addAction(Constant.BROADCASTCODE.zhiFuGuanBi);
        filter.addAction(Constant.BROADCASTCODE.GouWuCheNum);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShow = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * des： 分享
     * author： ZhangJieBo
     * date： 2017/9/25 0025 上午 11:54
     */
    public void share(final ShareBean share) {
        MyDialog.share(this, "ChanPinXQActivity", api, id, "goods", share);
    }

}
