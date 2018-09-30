package com.sxbwstxpay.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SkillIndex;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.HeaderSerarchXViewHolder;

import java.util.HashMap;

import okhttp3.Response;

public class SearchDPActivity extends ZjbBaseActivity implements View.OnClickListener{

    private View viewBar;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<SkillIndex.DataBean> adapter;
    private String cityId;
    private EditText editSouSuo;
    private LatLng latLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        init(SearchDPActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        cityId = intent.getStringExtra(Constant.INTENT_KEY.CITY);
        latLng = intent.getParcelableExtra(Constant.INTENT_KEY.position);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        editSouSuo = (EditText) findViewById(R.id.editSouSuo);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("搜索");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
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
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<SkillIndex.DataBean>(SearchDPActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_header_sousuo_location;
                return new HeaderSerarchXViewHolder(parent, layout);
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            0);
                }
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.value,adapter.getItem(position));
                setResult(Constant.REQUEST_RESULT_CODE.address,intent);
                finish();
            }
        });
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        editSouSuo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                recyclerView.showProgress();
                ApiClient.post(SearchDPActivity.this, getOkObject(editable.toString().trim()), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.LogShitou("SearchLocationActivity--onSuccess",s+ "");
                        try {
                            SkillIndex skillIndex = GsonUtils.parseJSON(s, SkillIndex.class);
                            if (skillIndex.getStatus()==1){
                                adapter.clear();
                                adapter.addAll(skillIndex.getData());
                                adapter.notifyDataSetChanged();
                            }else if (skillIndex.getStatus()==3){
                                MyDialog.showReLoginDialog(SearchDPActivity.this);
                            }else {
                                Toast.makeText(SearchDPActivity.this, skillIndex.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(SearchDPActivity.this,"数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        Toast.makeText(SearchDPActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject(String address) {
        String url = Constant.HOST + Constant.Url.SKILL_INDEX;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("lat", String.valueOf(latLng.latitude));
        params.put("lng", String.valueOf(latLng.longitude));
        params.put("cid", "0");
        params.put("cityId", cityId+"");
        params.put("keywords",address);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        ApiClient.post(SearchDPActivity.this, getOkObject(""), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("SearchLocationActivity--onSuccess",s+ "");
                try {
                    SkillIndex skillIndex = GsonUtils.parseJSON(s, SkillIndex.class);
                    if (skillIndex.getStatus()==1){
                        adapter.clear();
                        adapter.addAll(skillIndex.getData());
                        adapter.notifyDataSetChanged();
                    }else if (skillIndex.getStatus()==3){
                        MyDialog.showReLoginDialog(SearchDPActivity.this);
                    }else {
                        Toast.makeText(SearchDPActivity.this, skillIndex.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(SearchDPActivity.this,"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                Toast.makeText(SearchDPActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }

}
