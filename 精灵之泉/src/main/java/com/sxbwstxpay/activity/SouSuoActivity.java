package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.adapter.TagHotAdapter;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.FlowTagLayout;
import com.sxbwstxpay.customview.OnTagClickListener;
import com.sxbwstxpay.model.IndexDataBean;
import com.sxbwstxpay.model.IndexGoods;
import com.sxbwstxpay.model.IndexSearch;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.ShareBean;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.TuiJianViewHolder;
import com.sxbwstxpay.viewholder.XuanPinSJViewHolder;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Response;

public class SouSuoActivity extends ZjbBaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private FlowTagLayout flowTagLayout01;
    private FlowTagLayout flowTagLayout02;
    private TagHotAdapter tagHotAdapter01;
    private TagHotAdapter tagHotAdapter02;
    private String mCity;
    private String lat;
    private String lng;
    private String cityId;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<IndexDataBean> adapter;
    private int page = 0;
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideView();
                                    }
                                });
                            }
                        }, 2000, 1000);
                    } else {
                        Animation animation01 = AnimationUtils.loadAnimation(SouSuoActivity.this, R.anim.push_up_in);
                        viewShangJiaTip.startAnimation(animation01);
                        viewShangJiaTip.setVisibility(View.VISIBLE);
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideView();
                                    }
                                });
                            }
                        }, 2000, 1000);
                    }
                    break;
                case Constant.BROADCASTCODE.WX_SHARE:
                    if (isShow) {
                        MyDialog.showTipDialog(SouSuoActivity.this, "分享成功");
                    }
                    break;
                case Constant.BROADCASTCODE.WX_SHARE_FAIL:
                    if (isShow) {
                        MyDialog.showTipDialog(SouSuoActivity.this, "取消分享");
                    }
                    break;
            }
        }
    };
    private TextView textNum;
    private String keywords;
    private EditText editSouSuo;
    private ImageView imageSouSuo;
    private ScrollView scrollHot;
    private View include3;
    private boolean isShow;
    private IWXAPI api = WXAPIFactory.createWXAPI(SouSuoActivity.this, Constant.WXAPPID, true);
    private Tencent mTencent;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou_suo);
        mTencent = Tencent.createInstance(Constant.QQ_ID, this.getApplicationContext());
        init(SouSuoActivity.class);
    }

    @Override
    protected void initSP() {
        final ACache aCache = ACache.get(this, Constant.ACACHE.LOCATION);
        String cityAcache = aCache.getAsString(Constant.ACACHE.CITY);
        if (cityAcache != null) {
            mCity = cityAcache;
            lat = aCache.getAsString(Constant.ACACHE.LAT);
            lng = aCache.getAsString(Constant.ACACHE.LNG);
            cityId = aCache.getAsString(Constant.ACACHE.CITY_ID);
        }
    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        type = intent.getIntExtra(Constant.INTENT_KEY.type, 0);
    }

    @Override
    protected void findID() {
        include3 = findViewById(R.id.include3);
        flowTagLayout01 = (FlowTagLayout) findViewById(R.id.flowTagLayout01);
        flowTagLayout02 = (FlowTagLayout) findViewById(R.id.flowTagLayout02);
        viewShangJiaTip = findViewById(R.id.viewShangJiaTip);
        textNum = (TextView) findViewById(R.id.textNum);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        editSouSuo = (EditText) findViewById(R.id.editSouSuo);
        imageSouSuo = (ImageView) findViewById(R.id.imageSouSuo);
        scrollHot = (ScrollView) findViewById(R.id.scrollHot);
    }

    @Override
    protected void initViews() {
        ((TextView) include3.findViewById(R.id.textViewTitle)).setText("搜索");
        viewShangJiaTip.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        ViewGroup.LayoutParams layoutParams = include3.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        include3.setLayoutParams(layoutParams);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.marginTop), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        int red = getResources().getColor(R.color.basic_color);
        recyclerView.setRefreshingColor(red);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<IndexDataBean>(SouSuoActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                if (type==0){
                    int layout = R.layout.item_xuan_pin_sj;
                    return new XuanPinSJViewHolder(parent, layout, "SouSuoActivity");
                }else {
                    int layout = R.layout.item_tui_jian;
                    return new TuiJianViewHolder(parent, layout);
                }
            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                ApiClient.post(SouSuoActivity.this, getOkObjectSouSuo(), new ApiClient.CallBack() {
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
                                MyDialog.showReLoginDialog(SouSuoActivity.this);
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
                if (type==0){
                    Intent intent = new Intent();
                    intent.putExtra(Constant.INTENT_KEY.id, adapter.getItem(position).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.setClass(SouSuoActivity.this, ChanPinXQActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageCancle).setOnClickListener(this);
        editSouSuo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    scrollHot.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    scrollHot.setVisibility(View.GONE);
                }
            }
        });
        imageSouSuo.setOnClickListener(this);
        include3.findViewById(R.id.imageBack).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_SEARCH;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(SouSuoActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("SouSuoActivity--首页搜索", s + "");
                try {
                    IndexSearch indexSearch = GsonUtils.parseJSON(s, IndexSearch.class);
                    if (indexSearch.getStatus() == 1) {
                        final List<IndexSearch.DataBean> indexSearchData01 = indexSearch.getData();
                        final List<IndexSearch.DataBean> indexSearchData2 = indexSearch.getData2();
                        flowTagLayout01.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);
                        tagHotAdapter01 = new TagHotAdapter(SouSuoActivity.this);
                        flowTagLayout01.setAdapter(tagHotAdapter01);
                        tagHotAdapter01.clearAndAddAll(indexSearchData01);
                        flowTagLayout01.setOnTagClickListener(new OnTagClickListener() {
                            @Override
                            public void onItemClick(FlowTagLayout parent, View view, int position) {
                                keywords = indexSearchData01.get(position).getTitle();
                                SouSuo();
                            }
                        });
                        flowTagLayout02.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);
                        tagHotAdapter02 = new TagHotAdapter(SouSuoActivity.this);
                        flowTagLayout02.setAdapter(tagHotAdapter02);
                        tagHotAdapter02.clearAndAddAll(indexSearchData2);
                        flowTagLayout02.setOnTagClickListener(new OnTagClickListener() {
                            @Override
                            public void onItemClick(FlowTagLayout parent, View view, int position) {
                                keywords = indexSearchData2.get(position).getTitle();
                                SouSuo();
                            }
                        });
                    } else if (indexSearch.getStatus() == 3) {
                        MyDialog.showReLoginDialog(SouSuoActivity.this);
                    } else {
                        Toast.makeText(SouSuoActivity.this, indexSearch.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(SouSuoActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(SouSuoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {

    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObjectSouSuo() {
        String url = Constant.HOST + Constant.Url.GOODS_INDEX;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("p", page + "");
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("keywords", keywords);
        return new OkObject(params, url);
    }

    private void SouSuo() {
        editSouSuo.setText(keywords);
        editSouSuo.setSelection(keywords.length());
        page = 1;
        ApiClient.post(this, getOkObjectSouSuo(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("限时购", s);
                try {
                    page++;
                    IndexGoods indexGoods = GsonUtils.parseJSON(s, IndexGoods.class);
                    if (indexGoods.getStatus() == 1) {
                        List<IndexDataBean> indexGoodsData = indexGoods.getData();
                        adapter.clear();
                        adapter.addAll(indexGoodsData);
                    } else if (indexGoods.getStatus() == 3) {
                        MyDialog.showReLoginDialog(SouSuoActivity.this);
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
                View view_loaderror = LayoutInflater.from(SouSuoActivity.this).inflate(R.layout.view_loaderror, null);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageSouSuo:
                keywords = editSouSuo.getText().toString().trim();
                SouSuo();
                break;
            case R.id.imageCancle:
                hideView();
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }

    public void hideView() {
        Animation animation02 = AnimationUtils.loadAnimation(this, R.anim.push_down_out);
        viewShangJiaTip.startAnimation(animation02);
        viewShangJiaTip.setVisibility(View.GONE);
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }

    public void share(String id, String type, ShareBean share) {
        MyDialog.share(SouSuoActivity.this, "SouSuoActivity", api, id, type, share);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShow = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShow = false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTencent.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.ShangJia01);
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE);
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE_FAIL);
        registerReceiver(reciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }
}
