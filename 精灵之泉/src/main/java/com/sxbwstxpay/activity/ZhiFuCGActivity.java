package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.OrderPays;
import com.sxbwstxpay.model.RecommBean;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.ChanPinXQViewHolder;

import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class ZhiFuCGActivity extends ZjbBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private View viewBar;
    private EasyRecyclerView recyclerView;
    private TextView textViewTitle;
    private RecyclerArrayAdapter<RecommBean> adapter;
    private int oid;
    private String statusText;
    private String vipText;
    private int isVip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_fu_cg);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        oid = intent.getIntExtra(Constant.INTENT_KEY.id,0);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.include2);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
    }

    @Override
    protected void initViews() {
        textViewTitle.setText("支付成功");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        initRecycle();
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
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

            private TextView textLiJiLiaoJie;
            private TextView textVipText;
            private TextView textStatusText;

            @Override
            public View onCreateView(ViewGroup parent) {
                View header_zhi_fu_cg = LayoutInflater.from(ZhiFuCGActivity.this).inflate(R.layout.header_zhi_fu_cg, null);
                textStatusText = (TextView) header_zhi_fu_cg.findViewById(R.id.textStatusText);
                textVipText = (TextView) header_zhi_fu_cg.findViewById(R.id.textVipText);
                textLiJiLiaoJie = (TextView) header_zhi_fu_cg.findViewById(R.id.textLiJiLiaoJie);
                header_zhi_fu_cg.findViewById(R.id.textFanHuiSC).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                return header_zhi_fu_cg;
            }

            @Override
            public void onBindView(View headerView) {
                textStatusText.setText(statusText);
                if (isVip == 0) {
                    textVipText.setText(vipText);
                    textVipText.setVisibility(View.VISIBLE);
                    textLiJiLiaoJie.setVisibility(View.VISIBLE);
                } else {
                    textVipText.setVisibility(View.GONE);
                    textLiJiLiaoJie.setVisibility(View.GONE);
                }
            }

        });
        recyclerView.setRefreshListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.ORDER_PAYS;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("oid", oid+"");
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("支付成功", s);
                try {
                    OrderPays orderPays = GsonUtils.parseJSON(s, OrderPays.class);
                    if (orderPays.getStatus() == 1) {
                        statusText = orderPays.getStatusText();
                        vipText = orderPays.getVipText();
                        isVip = orderPays.getIsVip();
                        List<RecommBean> recomm = orderPays.getRecomm();
                        adapter.clear();
                        adapter.addAll(recomm);
                    } else if (orderPays.getStatus() == 3) {
                        MyDialog.showReLoginDialog(ZhiFuCGActivity.this);
                    } else {
                        showError(orderPays.getInfo());
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
                View view_loaderror = LayoutInflater.from(ZhiFuCGActivity.this).inflate(R.layout.view_loaderror, null);
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
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
            case R.id.viewGouWuChe:
                intent.setClass(this, GouWuCActivity.class);
                startActivity(intent);
                break;
        }
    }

}
