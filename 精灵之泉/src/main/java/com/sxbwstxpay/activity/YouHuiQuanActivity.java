package com.sxbwstxpay.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.CouponIndex;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.viewholder.YouHuiQuanViewHolder;

import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class YouHuiQuanActivity extends ZjbBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<CouponIndex.DataBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_hui_quan);
        init(YouHuiQuanActivity.class);
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
    }

    @Override
    protected void initViews() {
        ((TextView)findViewById(R.id.textViewTitle)).setText("优惠券");
        initRecycler();
    }

    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<CouponIndex.DataBean>(YouHuiQuanActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_youhuiquan;
                return new YouHuiQuanViewHolder(parent, layout);
            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                ApiClient.post(YouHuiQuanActivity.this, getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.LogShitou("DingDanGLActivity--加载更多", s+"");
                        try {
                            page++;
                            CouponIndex couponIndex = GsonUtils.parseJSON(s, CouponIndex.class);
                            int status = couponIndex.getStatus();
                            if (status == 1) {
                                List<CouponIndex.DataBean> dataBeanList = couponIndex.getData();
                                adapter.addAll(dataBeanList);
                            } else if (status == 3) {
                                MyDialog.showReLoginDialog(YouHuiQuanActivity.this);
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
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }

    int page =1;

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.COUPON_INDEX;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        params.put("p", String.valueOf(page));
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
      page =1;
      ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
          @Override
          public void onSuccess(String s) {
              LogUtil.LogShitou("", s);
              try {
                  page++;
                  CouponIndex couponIndex = GsonUtils.parseJSON(s, CouponIndex.class);
                  if (couponIndex.getStatus() == 1) {
                      List<CouponIndex.DataBean> dataBeanList = couponIndex.getData();
                      adapter.clear();
                      adapter.addAll(dataBeanList);
                  } else if (couponIndex.getStatus()== 3) {
                      MyDialog.showReLoginDialog(YouHuiQuanActivity.this);
                  } else {
                      showError(couponIndex.getInfo());
                  }
              } catch (Exception e) {
                  showError("数据出错");
              }
          }

          @Override
          public void onError(Response response) {
              showError("网络出错");
          }
          /**
           * 错误显示
           * @param msg
           */
          private void showError(String msg) {
              try {
                  View viewLoader = LayoutInflater.from(YouHuiQuanActivity.this).inflate(R.layout.view_loaderror, null);
                  TextView textMsg = (TextView) viewLoader.findViewById(R.id.textMsg);
                  textMsg.setText(msg);
                  viewLoader.findViewById(R.id.buttonReLoad).setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          recyclerView.showProgress();
                          initData();
                      }
                  });
                  recyclerView.setErrorView(viewLoader);
                  recyclerView.showError();
              } catch (Exception e) {
              }
          }
      });
    }
}
