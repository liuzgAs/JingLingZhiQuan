package com.sxbwstxpay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.adapter.TagHotAdapter;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.FlowTagLayout;
import com.sxbwstxpay.customview.OnTagClickListener;
import com.sxbwstxpay.model.IndexSearch;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;

import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class SouSuoActivity extends ZjbBaseActivity {

    private FlowTagLayout flowTagLayout01;
    private FlowTagLayout flowTagLayout02;
    private TagHotAdapter tagHotAdapter01;
    private TagHotAdapter tagHotAdapter02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou_suo);
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
        flowTagLayout01 = (FlowTagLayout) findViewById(R.id.flowTagLayout01);
        flowTagLayout02 = (FlowTagLayout) findViewById(R.id.flowTagLayout02);
    }

    @Override
    protected void initViews() {
//        FlowTagLayout flowTagLayout = (FlowTagLayout) dialog_chan_pin.findViewById(R.id.flowTagLayout);
//        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
//        tagAdapter = new TagAdapter(ChanPinXQActivity.this);
//        flowTagLayout.setAdapter(tagAdapter);
//        tagAdapter.clearAndAddAll(goodsInfoAdDes);
//        flowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
//            @Override
//            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
//                for (int i = 0; i < goodsInfoAdDes.size(); i++) {
//                    goodsInfoAdDes.get(i).setSelect(false);
//                }
//                for (int i = 0; i < selectedList.size(); i++) {
//                    LogUtil.LogShitou("DaiYingYaoFragment--onItemSelect", "" + selectedList.get(i));
//                    goodsInfoAdDes.get(selectedList.get(i)).setSelect(true);
//                }
//            }
//        });
    }

    @Override
    protected void setListeners() {

    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_SEARCH;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(SouSuoActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("SouSuoActivity--首页搜索", s + "");
                try {
                    IndexSearch indexSearch = GsonUtils.parseJSON(s, IndexSearch.class);
                    if (indexSearch.getStatus() == 1) {
                        final List<IndexSearch.DataBean> indexSearchData01 = indexSearch.getData();
                        final List<IndexSearch.DataBean> indexSearchData2 = indexSearch.getData2();
                        flowTagLayout01.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);
                        tagHotAdapter01 = new TagHotAdapter(SouSuoActivity.this);
                        flowTagLayout01.setAdapter(tagHotAdapter01);
                        tagHotAdapter01.clearAndAddAll(indexSearchData01);
                        flowTagLayout01.setOnTagClickListener(new OnTagClickListener() {
                            @Override
                            public void onItemClick(FlowTagLayout parent, View view, int position) {
                                Toast.makeText(SouSuoActivity.this, indexSearchData01.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        flowTagLayout02.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);
                        tagHotAdapter02 = new TagHotAdapter(SouSuoActivity.this);
                        flowTagLayout02.setAdapter(tagHotAdapter02);
                        tagHotAdapter02.clearAndAddAll(indexSearchData2);
                        flowTagLayout02.setOnTagClickListener(new OnTagClickListener() {
                            @Override
                            public void onItemClick(FlowTagLayout parent, View view, int position) {
                                Toast.makeText(SouSuoActivity.this, indexSearchData2.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (indexSearch.getStatus() == 3) {
                        MyDialog.showReLoginDialog(SouSuoActivity.this);
                    } else {
                        Toast.makeText(SouSuoActivity.this, indexSearch.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(SouSuoActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(SouSuoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
