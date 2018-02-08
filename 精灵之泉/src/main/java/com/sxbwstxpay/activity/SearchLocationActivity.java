package com.sxbwstxpay.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.MapMarkerBean;
import com.sxbwstxpay.model.MapSearchstore;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.HeaderSerarchViewHolder;
import com.sxbwstxpay.viewholder.SearchLocationViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class SearchLocationActivity extends ZjbBaseActivity implements View.OnClickListener{

    private View viewBar;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Tip> adapter;
    private String city;
    private EditText editSouSuo;
    private LatLng latLng;
    private List<MapMarkerBean> mapMarkerBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        init(SearchLocationActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        city = intent.getStringExtra(Constant.INTENT_KEY.CITY);
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
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Tip>(SearchLocationActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_search_location;
                return new SearchLocationViewHolder(parent, layout);
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private RecyclerArrayAdapter<MapMarkerBean> adapterHead;
            private EasyRecyclerView recyclerViewHead;
            private TextView textNoData;

            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(SearchLocationActivity.this).inflate(R.layout.header_sousuo_location, null);
                textNoData = (TextView) view.findViewById(R.id.textNoData);
                recyclerViewHead = (EasyRecyclerView) view.findViewById(R.id.recyclerView);
                textNoData.setVisibility(View.GONE);
                initRecyclerHeader();
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                if (mapMarkerBeanList!=null){
                    adapterHead.clear();
                    if (mapMarkerBeanList.size()>0){
                        textNoData.setVisibility(View.GONE);
                        adapterHead.addAll(mapMarkerBeanList);
                    }else {
                        adapterHead.notifyDataSetChanged();
                        textNoData.setVisibility(View.VISIBLE);
                    }
                }
            }

            /**
             * 初始化recyclerview
             */
            private void initRecyclerHeader() {
                recyclerViewHead.setLayoutManager(new LinearLayoutManager(SearchLocationActivity.this));
                DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.line_width), 0, 0);
                itemDecoration.setDrawLastItem(false);
                recyclerViewHead.addItemDecoration(itemDecoration);
                recyclerViewHead.setRefreshingColorResources(R.color.basic_color);
                recyclerViewHead.setAdapterWithProgress(adapterHead = new RecyclerArrayAdapter<MapMarkerBean>(SearchLocationActivity.this) {
                    @Override
                    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                        int layout = R.layout.item_header_sousuo_location;
                        return new HeaderSerarchViewHolder(parent, layout);
                    }
                });
                adapterHead.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                                    0);
                        }
                        Intent intent = new Intent();
                        intent.putExtra(Constant.INTENT_KEY.Store,adapterHead.getItem(position));
                        setResult(Constant.REQUEST_RESULT_CODE.address,intent);
                        finish();
                    }
                });
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
                InputtipsQuery inputquery = new InputtipsQuery(editable.toString().trim(), city);
                inputquery.setCityLimit(false);//限制在当前城市
                Inputtips inputTips = new Inputtips(SearchLocationActivity.this, inputquery);
                inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
                    @Override
                    public void onGetInputtips(List<Tip> list, int i) {
                        adapter.clear();
                        for (int j = 0; j < list.size(); j++) {
                            if (list.get(j).getPoint()!=null){
                                adapter.add(list.get(j));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                inputTips.requestInputtipsAsyn();
                ApiClient.post(SearchLocationActivity.this, getOkObject(editable.toString().trim()), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.LogShitou("SearchLocationActivity--onSuccess",s+ "");
                        try {
                            MapSearchstore mapSearchstore = GsonUtils.parseJSON(s, MapSearchstore.class);
                            if (mapSearchstore.getStatus()==1){
                                mapMarkerBeanList = mapSearchstore.getData();
                                adapter.notifyDataSetChanged();
                            }else if (mapSearchstore.getStatus()==3){
                                MyDialog.showReLoginDialog(SearchLocationActivity.this);
                            }else {
                                Toast.makeText(SearchLocationActivity.this, mapSearchstore.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(SearchLocationActivity.this,"数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        Toast.makeText(SearchLocationActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
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
        String url = Constant.HOST + Constant.Url.MAP_SEARCHSTORE;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        params.put("keywords",address);
        params.put("lat",String.valueOf(latLng.latitude));
        params.put("lng",String.valueOf(latLng.longitude));
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        adapter.clear();
        adapter.addAll(new ArrayList<Tip>());
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
