package com.sxbwstxpay.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.BoXingTu;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.StoreViews;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.FangKeGLViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class FangKeGLActivity extends ZjbBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private EasyRecyclerView recyclerView;
    private View viewBar;
    private RecyclerArrayAdapter<StoreViews.GoodsBean> adapter;
    private int user;
    private int view;
    private List<StoreViews.DtBean> storeViewsDt = new ArrayList<>();
    private int maxHeight;
    private int dayUser;
    private int dayView;
    private List<StoreViews.CateBean> storeViewsCate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fang_ke_gl);
        init(FangKeGLActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        viewBar = findViewById(R.id.viewBar);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("访客管理");
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
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColor(getResources().getColor(R.color.basic_color));
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<StoreViews.GoodsBean>(FangKeGLActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_guan_li_fk;
                return new FangKeGLViewHolder(parent, layout);
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private TabLayout tablayoutHeader;
            private TextView textDayView;
            private TextView textDayUser;
            private BoXingTu boXingTu;
            private TextView textView;
            private TextView textUser;

            @Override
            public View onCreateView(ViewGroup parent) {
                View header_fang_ke_gl = LayoutInflater.from(FangKeGLActivity.this).inflate(R.layout.header_fang_ke_gl, null);
                textUser = (TextView) header_fang_ke_gl.findViewById(R.id.textUser);
                textView = (TextView) header_fang_ke_gl.findViewById(R.id.textView);
                textDayUser = (TextView) header_fang_ke_gl.findViewById(R.id.textDayUser);
                textDayView = (TextView) header_fang_ke_gl.findViewById(R.id.textDayView);
                boXingTu = (BoXingTu) header_fang_ke_gl.findViewById(R.id.boXingTu);
                tablayoutHeader = (TabLayout) header_fang_ke_gl.findViewById(R.id.tablayoutHeader);
                return header_fang_ke_gl;
            }

            @Override
            public void onBindView(View headerView) {
                textUser.setText("访客数:" + user);
                textView.setText("访问量:" + view);
                textDayUser.setText(dayUser + "");
                textDayView.setText(dayView + "");
                String[] text = new String[7];
                float[] line01 = new float[7];
                float[] line02 = new float[7];
                for (int i = 0; i < storeViewsDt.size(); i++) {
                    text[i] = storeViewsDt.get(i).getDateStr();
                    line01[i] = (float) storeViewsDt.get(i).getUser() / (float) maxHeight;
                    line02[i] = (float) storeViewsDt.get(i).getView() / (float) maxHeight;
                }
                boXingTu.setTextArr(text);
                boXingTu.setValueArr01(line01);
                boXingTu.setValueArr02(line02);
                if (storeViewsCate != null) {
                    tablayoutHeader.removeAllTabs();
                    tablayoutHeader.setTabMode(TabLayout.MODE_SCROLLABLE);
                    for (int i = 0; i < storeViewsCate.size(); i++) {
                        View item_zuireleimu = LayoutInflater.from(FangKeGLActivity.this).inflate(R.layout.item_zuireleimu, null);
                        ImageView imageImg = (ImageView) item_zuireleimu.findViewById(R.id.imageImg);
                        TextView textName = (TextView) item_zuireleimu.findViewById(R.id.textName);
                        Glide.with(FangKeGLActivity.this)
                                .load(storeViewsCate.get(i).getImg())
                                .asBitmap()
                                .placeholder(R.mipmap.ic_empty)
                                .into(imageImg);
                        textName.setText(storeViewsCate.get(i).getName());
                        tablayoutHeader.addTab(tablayoutHeader.newTab().setCustomView(item_zuireleimu));
                    }
                }
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

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.STORE_VIEWS;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("访客管理", s);
                try {
                    StoreViews storeViews = GsonUtils.parseJSON(s, StoreViews.class);
                    if (storeViews.getStatus() == 1) {
                        user = storeViews.getUser();
                        view = storeViews.getView();
                        dayUser = storeViews.getDayUser();
                        dayView = storeViews.getDayView();
                        storeViewsDt = storeViews.getDt();
                        maxHeight = storeViews.getMaxHeight();
                        storeViewsCate = storeViews.getCate();
                        List<StoreViews.GoodsBean> storeViewsGoods = storeViews.getGoods();
                        adapter.clear();
                        adapter.addAll(storeViewsGoods);
                    } else if (storeViews.getStatus() == 3) {
                        MyDialog.showReLoginDialog(FangKeGLActivity.this);
                    } else {
                        showError(storeViews.getInfo());
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
                View view_loaderror = LayoutInflater.from(FangKeGLActivity.this).inflate(R.layout.view_loaderror, null);
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
