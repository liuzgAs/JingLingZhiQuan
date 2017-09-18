package com.jlzquan.www.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jlzquan.www.R;
import com.jlzquan.www.base.MyDialog;
import com.jlzquan.www.base.ZjbBaseActivity;
import com.jlzquan.www.constant.Constant;
import com.jlzquan.www.model.OkObject;
import com.jlzquan.www.model.ShareShareDay;
import com.jlzquan.www.util.ApiClient;
import com.jlzquan.www.util.GsonUtils;
import com.jlzquan.www.util.LogUtil;
import com.jlzquan.www.util.ScreenUtils;
import com.jlzquan.www.viewholder.TuWenTGViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class TuWenTGActivity extends ZjbBaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<ShareShareDay.ListDataBean> adapter;
    private View viewBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tu_wen_tg);
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
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("图文推广");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        initRecycler();
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<ShareShareDay.ListDataBean>(TuWenTGActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_tu_wen_tg;
                return new TuWenTGViewHolder(parent, layout);
            }
        });
//        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
//            @Override
//            public void onMoreShow() {
//                ApiClient.post(TuWenTGActivity.this, getOkObject(), new ApiClient.CallBack() {
//                    @Override
//                    public void onSuccess(String s) {
//                        try {
//                            page++;
//                            NewsIndex newsIndex = GsonUtils.parseJSON(s, NewsIndex.class);
//                            int status = newsIndex.getStatus();
//                            if (status == 1) {
//                                List<NewsIndex.DataBean> newsIndexData = newsIndex.getData();
//                                adapter.addAll(newsIndexData);
//                            } else if (status == 3) {
//                                MyDialog.showReLoginDialog(TuWenTGActivity.this);
//                            } else {
//                                adapter.pauseMore();
//                            }
//                        } catch (Exception e) {
//                            adapter.pauseMore();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response response) {
//                        adapter.pauseMore();
//                    }
//                });
//            }
//
//            @Override
//            public void onMoreClick() {
//
//            }
//        });
//        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
//            @Override
//            public void onNoMoreShow() {
//
//            }
//
//            @Override
//            public void onNoMoreClick() {
//            }
//        });
//        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
//            @Override
//            public void onErrorShow() {
//                adapter.resumeMore();
//            }
//
//            @Override
//            public void onErrorClick() {
//                adapter.resumeMore();
//            }
//        });
//        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//            }
//        });
        recyclerView.setRefreshListener(this);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
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
        String url = Constant.HOST + Constant.Url.SHARE_SHARE_DAY;
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
                LogUtil.LogShitou("图文推广", s);
                try {
                    ShareShareDay shareShareDay = GsonUtils.parseJSON(s, ShareShareDay.class);
                    if (shareShareDay.getStatus() == 1) {
                        List<ShareShareDay.ListDataBean> shareShareDayListData = shareShareDay.getListData();
                        adapter.clear();
                        adapter.addAll(shareShareDayListData);
                    } else if (shareShareDay.getStatus() == 3) {
                        MyDialog.showReLoginDialog(TuWenTGActivity.this);
                    } else {
                        showError(shareShareDay.getInfo());
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
                View view_loaderror = LayoutInflater.from(TuWenTGActivity.this).inflate(R.layout.view_loaderror, null);
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
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
