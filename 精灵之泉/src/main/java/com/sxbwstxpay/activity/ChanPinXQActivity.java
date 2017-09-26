package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.bumptech.glide.Glide;
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
import com.sxbwstxpay.model.CartAddcart;
import com.sxbwstxpay.model.GoodsInfo;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.ShareBean;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.RecycleViewDistancaUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.util.StringUtil;
import com.sxbwstxpay.viewholder.ChanPinXQViewHolder;
import com.sxbwstxpay.viewholder.LocalImageChanPinHolderView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Response;

public class ChanPinXQActivity extends ZjbBaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private View viewBar;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<GoodsInfo.RecommBean> adapter;
    private TextView textViewTitle;
    private int viewBarHeight;
    private String id;
    private GoodsInfo.AdBean goodsInfoAd;
    private List<GoodsInfo.AdBean.DesBean> goodsInfoAdDes;
    private int num = 1;
    private AlertDialog alertDialog1;
    private TagAdapter tagAdapter;
    private IWXAPI api = WXAPIFactory.createWXAPI(ChanPinXQActivity.this, Constant.WXAPPID, true);
    private Tencent mTencent;
    private Bitmap bitmap;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.WX_SHARE:
                    MyDialog.showTipDialog(ChanPinXQActivity.this, "分享成功");
                    break;
                case Constant.BROADCASTCODE.WX_SHARE_FAIL:
                    MyDialog.showTipDialog(ChanPinXQActivity.this, "取消分享");
                    break;
            }
        }
    };
    private Timer timer;
    private TextView textDaoJiShi;
    private int countdown;
    private View viewKeGouMai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chan_pin_xq);
        mTencent = Tencent.createInstance(Constant.QQ_ID, this.getApplicationContext());
        init();
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
        findViewById(R.id.viewGouWuChe).setOnClickListener(this);
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.textMai1).setOnClickListener(this);
        findViewById(R.id.textMai2).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        LogUtil.LogShitou("ChanPinXQActivity--initData", "" + userInfo.getUid());
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
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<GoodsInfo.RecommBean>(this) {
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
                    LogUtil.LogShitou("ChanPinXQActivity--countdown", "" + countdown);
                    if (timer!=null){
                        timer.cancel();
                    }
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LogUtil.LogShitou("ChanPinXQActivity--run", "" + countdown);
                                    if (countdown >= 1) {
                                        countdown--;
                                        textCountdown.setText(StringUtil.TimeFormat(countdown));
                                        if (textDaoJiShi!=null&&viewKeGouMai!=null){
                                            textDaoJiShi.setText("倒计时 "+StringUtil.TimeFormat(countdown));
                                            if (countdown>0){
                                                textDaoJiShi.setVisibility(View.VISIBLE);
                                                viewKeGouMai.setVisibility(View.GONE);
                                            }else {
                                                textDaoJiShi.setVisibility(View.GONE);
                                                viewKeGouMai.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }else {
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
                    Glide.with(ChanPinXQActivity.this)
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


    /**
     * des： 加入购物车弹窗
     * author： ZhangJieBo
     * date： 2017/9/26 0026 下午 4:04
     */

    private void mai() {
        for (int i = 0; i < goodsInfoAdDes.size(); i++) {
            goodsInfoAdDes.get(i).setSelect(false);
        }
        LayoutInflater inflater = LayoutInflater.from(ChanPinXQActivity.this);
        View dialog_chan_pin = inflater.inflate(R.layout.dialog_chan_pin, null);
        textDaoJiShi = (TextView) dialog_chan_pin.findViewById(R.id.textDaoJiShi);
        viewKeGouMai = dialog_chan_pin.findViewById(R.id.viewKeGouMai);
        if (countdown>0){
            textDaoJiShi.setVisibility(View.VISIBLE);
            viewKeGouMai.setVisibility(View.GONE);
        }else {
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
        FlowTagLayout flowTagLayout = (FlowTagLayout) dialog_chan_pin.findViewById(R.id.flowTagLayout);
        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tagAdapter = new TagAdapter(ChanPinXQActivity.this);
        flowTagLayout.setAdapter(tagAdapter);
        tagAdapter.clearAndAddAll(goodsInfoAdDes);
        flowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                for (int i = 0; i < goodsInfoAdDes.size(); i++) {
                    goodsInfoAdDes.get(i).setSelect(false);
                }
                for (int i = 0; i < selectedList.size(); i++) {
                    LogUtil.LogShitou("DaiYingYaoFragment--onItemSelect", "" + selectedList.get(i));
                    goodsInfoAdDes.get(selectedList.get(i)).setSelect(true);
                }
            }
        });
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

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObjectAddCar() {
        String url = Constant.HOST + Constant.Url.CART_ADDCART;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("num", num + "");
        params.put("id", id);
        String speId = "";
        for (int i = 0; i < goodsInfoAdDes.size(); i++) {
            if (goodsInfoAdDes.get(i).isSelect()) {
                speId = goodsInfoAdDes.get(i).getId();
            }
        }
        params.put("speId", speId);
        return new OkObject(params, url);
    }

    /**
     * des： 购物车新增
     * author： ZhangJieBo
     * date： 2017/9/26 0026 下午 2:20
     */
    private void addCar(final boolean quick) {
        boolean hasId = false;
        for (int i = 0; i < goodsInfoAdDes.size(); i++) {
            if (goodsInfoAdDes.get(i).isSelect()) {
                hasId = true;
            }
        }
        if (!hasId) {
            Toast.makeText(ChanPinXQActivity.this, "请选择规格", Toast.LENGTH_SHORT).show();
            return;
        }
        showLoadingDialog();
        ApiClient.post(ChanPinXQActivity.this, getOkObjectAddCar(), new ApiClient.CallBack() {
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
                            intent.setClass(ChanPinXQActivity.this, GouWuCActivity.class);
                            startActivity(intent);
                        }
                    } else if (cartAddcart.getStatus() == 2) {
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
                        goodsInfoAdDes = goodsInfoAd.getDes();
                        for (int i = 0; i < goodsInfoAdDes.size(); i++) {
                            goodsInfoAdDes.get(i).setSelect(false);
                        }
                        List<GoodsInfo.RecommBean> goodsInfoRecomm = goodsInfo.getRecomm();
                        adapter.clear();
                        adapter.addAll(goodsInfoRecomm);
                    } else if (goodsInfo.getStatus() == 2) {
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
                        initData();
                    }
                });
                recyclerView.setErrorView(view_loaderror);
                recyclerView.showError();
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
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE);
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE_FAIL);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        if (timer!=null){
            timer.cancel();
        }
    }

    /**
     * des： 分享
     * author： ZhangJieBo
     * date： 2017/9/25 0025 上午 11:54
     */
    public void share(final ShareBean share) {
        LayoutInflater inflater = LayoutInflater.from(ChanPinXQActivity.this);
        View dialog_shengji = inflater.inflate(R.layout.dianlog_index_share, null);
        TextView textDes1 = (TextView) dialog_shengji.findViewById(R.id.textDes1);
        TextView textDes2 = (TextView) dialog_shengji.findViewById(R.id.textDes2);
        textDes1.setText(share.getTitle());
        SpannableString span = new SpannableString(share.getDes1() + share.getDesMoney() + share.getDes2());
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.basic_color)), share.getDes1().length(), share.getDes1().length() + share.getDesMoney().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textDes2.setText(span);
        final AlertDialog alertDialog1 = new AlertDialog.Builder(ChanPinXQActivity.this, R.style.dialog)
                .setView(dialog_shengji)
                .create();
        alertDialog1.show();
        dialog_shengji.findViewById(R.id.imageCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1.dismiss();
            }
        });
        dialog_shengji.findViewById(R.id.viewWeiXin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkIsSupportedWeachatPay()) {
                    Toast.makeText(ChanPinXQActivity.this, "您暂未安装微信,请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
                    return;
                }
                wxShare(0, share);
                alertDialog1.dismiss();
            }
        });
        dialog_shengji.findViewById(R.id.viewPengYouQuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkIsSupportedWeachatPay()) {
                    Toast.makeText(ChanPinXQActivity.this, "您暂未安装微信,请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
                    return;
                }
                wxShare(1, share);
                alertDialog1.dismiss();
            }
        });
        dialog_shengji.findViewById(R.id.viewErWeiMa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChanPinXQActivity.this, "此功能暂未开放", Toast.LENGTH_SHORT).show();
                alertDialog1.dismiss();
            }
        });
        Window dialogWindow = alertDialog1.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogFenXiang);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = ChanPinXQActivity.this.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 1); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    /**
     * qq回调
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            Log.e("BaseUiListener", "BaseUiListener--onComplete--QQ分享" + o.toString());
        }

        @Override
        public void onError(UiError e) {
            Log.e("BaseUiListener", "code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
        }

        @Override
        public void onCancel() {
            Log.e("BaseUiListener", "BaseUiListener--onCancel--");
        }
    }

    private void wxShare(final int flag, final ShareBean share) {
        api.registerApp(Constant.WXAPPID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = share.getShareUrl();
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = share.getShareTitle();
        msg.description = share.getShareDes();
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = netPicToBmp(share.getShareImg());
                msg.setThumbImage(bitmap);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                switch (flag) {
                    case 0:
                        req.scene = SendMessageToWX.Req.WXSceneSession;
                        break;
                    case 1:
                        req.scene = SendMessageToWX.Req.WXSceneTimeline;
                        break;
                    case 2:
                        req.scene = SendMessageToWX.Req.WXSceneFavorite;
                        break;
                }
                api.sendReq(req);
            }
        }).start();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 检查微信版本是否支付支付或是否安装可支付的微信版本
     */
    private boolean checkIsSupportedWeachatPay() {
        boolean isPaySupported = api.getWXAppSupportAPI() >= com.tencent.mm.opensdk.constants.Build.PAY_SUPPORTED_SDK_INT;
        return isPaySupported;
    }

    public Bitmap netPicToBmp(String src) {
        try {
            Log.d("FileUtil", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            //设置固定大小
            //需要的大小
            float newWidth = 200f;
            float newHeigth = 200f;

            //图片大小
            int width = myBitmap.getWidth();
            int height = myBitmap.getHeight();

            //缩放比例
            float scaleWidth = newWidth / width;
            float scaleHeigth = newHeigth / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeigth);

            Bitmap bitmap = Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true);
            return bitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
