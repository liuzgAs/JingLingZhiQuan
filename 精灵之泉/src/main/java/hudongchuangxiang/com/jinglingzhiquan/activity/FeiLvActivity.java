package hudongchuangxiang.com.jinglingzhiquan.activity;

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

import java.util.HashMap;
import java.util.List;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.base.MyDialog;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseActivity;
import hudongchuangxiang.com.jinglingzhiquan.constant.Constant;
import hudongchuangxiang.com.jinglingzhiquan.model.OkObject;
import hudongchuangxiang.com.jinglingzhiquan.model.UserRate;
import hudongchuangxiang.com.jinglingzhiquan.util.ApiClient;
import hudongchuangxiang.com.jinglingzhiquan.util.GsonUtils;
import hudongchuangxiang.com.jinglingzhiquan.util.LogUtil;
import hudongchuangxiang.com.jinglingzhiquan.util.ScreenUtils;
import hudongchuangxiang.com.jinglingzhiquan.viewholder.FeiLvViewHolder;
import okhttp3.Response;

public class FeiLvActivity extends ZjbBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerArrayAdapter<UserRate.DataBean> adapter;
    private EasyRecyclerView recyclerView;
    private View viewBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fei_lv);
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
        ((TextView) findViewById(R.id.textViewTitle)).setText("费率");
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

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_RATE;
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
                LogUtil.LogShitou("我的费率", s);
                try {
                    UserRate userRate = GsonUtils.parseJSON(s, UserRate.class);
                    if (userRate.getStatus() == 1) {
                        List<UserRate.DataBean> userRateData = userRate.getData();
                        adapter.clear();
                        adapter.addAll(userRateData);
                    } else if (userRate.getStatus() == 2) {
                        MyDialog.showReLoginDialog(FeiLvActivity.this);
                    } else {
                        showError(userRate.getInfo());
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
                View view_loaderror = LayoutInflater.from(FeiLvActivity.this).inflate(R.layout.view_loaderror, null);
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

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColor(getResources().getColor(R.color.basic_color));
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<UserRate.DataBean>(FeiLvActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_fei_lv;
                return new FeiLvViewHolder(parent, layout);
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
}
