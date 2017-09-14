package hudongchuangxiang.com.jinglingzhiquan.activity;

import android.content.Intent;
import android.os.Bundle;
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
import hudongchuangxiang.com.jinglingzhiquan.model.BankCardlist;
import hudongchuangxiang.com.jinglingzhiquan.model.OkObject;
import hudongchuangxiang.com.jinglingzhiquan.util.ApiClient;
import hudongchuangxiang.com.jinglingzhiquan.util.GsonUtils;
import hudongchuangxiang.com.jinglingzhiquan.util.LogUtil;
import hudongchuangxiang.com.jinglingzhiquan.util.ScreenUtils;
import hudongchuangxiang.com.jinglingzhiquan.viewholder.XuanZeXYKViewHolder;
import okhttp3.Response;

public class XuanZeXYKActivity extends ZjbBaseActivity implements View.OnClickListener {
    private View viewBar;
    private RecyclerArrayAdapter<BankCardlist.DataBean> adapter;
    private EasyRecyclerView recyclerView;
    private String amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuan_ze_xyk);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        amount = intent.getStringExtra(Constant.INTENT_KEY.amount);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("选择信用卡");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        initRecycler();
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(getResources().getColor(R.color.line_gray), (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColor(getResources().getColor(R.color.basic_color));
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<BankCardlist.DataBean>(XuanZeXYKActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_xuan_ze_xyk;
                return new XuanZeXYKViewHolder(parent, layout);
            }
        });
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(XuanZeXYKActivity.this).inflate(R.layout.footer_xuan_ze_xyk, null);
                view.findViewById(R.id.textXinZengXYK).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(XuanZeXYKActivity.this,XinZengYHKActivity.class);
                        intent.putExtra(Constant.INTENT_KEY.TITLE,"新增信用卡");
                        intent.putExtra(Constant.INTENT_KEY.type,2);
                        startActivityForResult(intent,Constant.REQUEST_RESULT_CODE.XIN_YONG_KA);
                    }
                });
                return view;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

            }
        });
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
        String url = Constant.HOST + Constant.Url.BANK_CARDLIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("type", "2");//储蓄卡1  信用卡2
        return new OkObject(params, url);
    }

    public void onRefresh() {
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("选择支付通道", s);
                try {
                    BankCardlist bankCardlist = GsonUtils.parseJSON(s, BankCardlist.class);
                    if (bankCardlist.getStatus() == 1) {
                        adapter.clear();
                        List<BankCardlist.DataBean> bankCardlistData = bankCardlist.getData();
                        adapter.addAll(bankCardlistData);
                    } else if (bankCardlist.getStatus() == 2) {
                        MyDialog.showReLoginDialog(XuanZeXYKActivity.this);
                    } else {
                        showError(bankCardlist.getInfo());
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
                View view_loaderror = LayoutInflater.from(XuanZeXYKActivity.this).inflate(R.layout.view_loaderror, null);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Constant.REQUEST_RESULT_CODE.XIN_YONG_KA&&resultCode==Constant.REQUEST_RESULT_CODE.XIN_YONG_KA){
            onRefresh();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
