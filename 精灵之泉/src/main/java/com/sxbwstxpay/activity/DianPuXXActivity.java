package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.model.StoreStoreinfo;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.DianPuXXViewHolder;

import java.util.HashMap;

import okhttp3.Response;

public class DianPuXXActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private RecyclerArrayAdapter<StoreStoreinfo> adapter;
    private EasyRecyclerView recyclerView;
    private StoreStoreinfo storeStoreinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shang_hu_xx);
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
        viewBar = findViewById(R.id.viewBar);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("店铺信息");
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<StoreStoreinfo>(DianPuXXActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.view_shanghu_xx;
                return new DianPuXXViewHolder(parent, layout);
            }

        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.STORE_STOREINFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    public void onRefresh() {
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("店铺信息", s);
                try {
                    storeStoreinfo = GsonUtils.parseJSON(s, StoreStoreinfo.class);
                    if (storeStoreinfo.getStatus() == 1) {
                        Intent intent = new Intent();
                        intent.setAction(Constant.BROADCASTCODE.ShuaXinWoDeDP);
                        sendBroadcast(intent);
                        adapter.clear();
                        adapter.add(storeStoreinfo);
                        adapter.notifyDataSetChanged();
                    } else if (storeStoreinfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(DianPuXXActivity.this);
                    } else {
                        showError(storeStoreinfo.getInfo());
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
                View view_loaderror = LayoutInflater.from(DianPuXXActivity.this).inflate(R.layout.view_loaderror, null);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_RESULT_CODE.BaoCun && resultCode == Constant.REQUEST_RESULT_CODE.BaoCun) {
            int type = data.getIntExtra("type", 0);
            String key = data.getStringExtra("key");
            String value = data.getStringExtra("value");
            switch (type) {
                case 5:
                    storeStoreinfo.setName(value);
                    break;
                case 6:
                    storeStoreinfo.setIntro(value);
                    break;
            }
            baoCun(key, value);
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObjectSave(String key, String value) {
        String url = Constant.HOST + Constant.Url.STORE_STORESAVE;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid",userInfo.getUid());
        params.put("tokenTime",tokenTime);
        params.put("key",key);
        params.put("value",value);
        return new OkObject(params, url);
    }

    /**
     * des： 我的店铺信息保存
     * author： ZhangJieBo
     * date： 2017/9/29 0029 上午 10:02
     */
    private void baoCun(String key, String value) {
        showLoadingDialog();
        ApiClient.post(DianPuXXActivity.this, getOkObjectSave(key,value), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("DianPuXXActivity--我的店铺信息保存", "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus()==1){
                        adapter.clear();
                        adapter.add(storeStoreinfo);
                        adapter.notifyDataSetChanged();
                    }else if (simpleInfo.getStatus()==3){
                        MyDialog.showReLoginDialog(DianPuXXActivity.this);
                    }else {
                        Toast.makeText(DianPuXXActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(DianPuXXActivity.this,"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(DianPuXXActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
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
