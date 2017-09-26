package com.sxbwstxpay.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.CartIndex;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.GouWuCViewHolder;

import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class GouWuCActivity extends ZjbBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerArrayAdapter<CartIndex.CartBean> adapter;
    private EasyRecyclerView recyclerView;
    private View viewBar;
    private ImageView imageQuanXuan;
    private boolean isQuanXuna = true;
    private TextView textSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gou_wu_c);
        init();
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
        imageQuanXuan = (ImageView) findViewById(R.id.imageQuanXuan);
        textSum = (TextView) findViewById(R.id.textSum);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("购物车");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        initRecycler();
        imageQuanXuan.setImageResource(R.mipmap.xuanzhong);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.buttonJieSuan).setOnClickListener(this);
        findViewById(R.id.viewQuanXuan).setOnClickListener(this);
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
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<CartIndex.CartBean>(GouWuCActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_car;
                return new GouWuCViewHolder(parent, layout);
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
        recyclerView.setRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.viewQuanXuan:
                if (isQuanXuna){
                    for (int i = 0; i < adapter.getAllData().size(); i++) {
                        adapter.getAllData().get(i).setIscheck(false);
                    }
                }else {
                    for (int i = 0; i < adapter.getAllData().size(); i++) {
                        adapter.getAllData().get(i).setIscheck(true);
                    }
                }
                adapter.notifyDataSetChanged();
                quanXuan();
                break;
            case R.id.buttonJieSuan:
                intent.setClass(this, QueRenDDActivity.class);
                intent.putExtra(Constant.INTENT_KEY.value,new CartIndex(adapter.getAllData()));
                startActivity(intent);
                break;
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
        String url = Constant.HOST + Constant.Url.CART_INDEX;
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
                LogUtil.LogShitou("购物车", s);
                try {
                    CartIndex cartIndex = GsonUtils.parseJSON(s, CartIndex.class);
                    if (cartIndex.getStatus() == 1) {
                        List<CartIndex.CartBean> cartIndexCart = cartIndex.getCart();
                        for (int i = 0; i < cartIndexCart.size(); i++) {
                            cartIndexCart.get(i).setIscheck(true);
                        }
                        textSum.setText("¥"+cartIndex.getSum());
                        adapter.clear();
                        adapter.addAll(cartIndexCart);
                    } else if (cartIndex.getStatus() == 2) {
                        MyDialog.showReLoginDialog(GouWuCActivity.this);
                    } else {
                        showError(cartIndex.getInfo());
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
                View view_loaderror = LayoutInflater.from(GouWuCActivity.this).inflate(R.layout.view_loaderror, null);
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


    public void quanXuan() {
        for (int i = 0; i < adapter.getAllData().size(); i++) {
            if (!adapter.getAllData().get(i).ischeck()) {
                imageQuanXuan.setImageResource(R.mipmap.weixuanzhong);
                isQuanXuna = false;
                return;
            }
        }
        isQuanXuna = true;
        imageQuanXuan.setImageResource(R.mipmap.xuanzhong);
    }

    public void setSum(String sum){
        textSum.setText("¥"+sum);
    }


    /**
     * des： 删除item
     * author： ZhangJieBo
     * date： 2017/9/26 0026 下午 7:41
     */
    public void remove(int position){
        adapter.remove(position);
    }
}
