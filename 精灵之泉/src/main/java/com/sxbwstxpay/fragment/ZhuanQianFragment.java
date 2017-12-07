package com.sxbwstxpay.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.TuiGuangActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.IndexMakemoney;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.ShareIndex;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.ZhuanQianViewHolder;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import java.util.HashMap;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhuanQianFragment extends ZjbBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private EasyRecyclerView recyclerView;
    private View mInflate;
    private View mRelaTitleStatue;
    private RecyclerArrayAdapter<IndexMakemoney> adapter;
    private ImageView viewImg;
    private IWXAPI api;
    private Tencent mTencent;
    private ShareIndex shareIndex;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.WX_SHARE:
                    if (isShow) {
                        MyDialog.showTipDialog(getActivity(), "分享成功");
                    }
                    break;
                case Constant.BROADCASTCODE.WX_SHARE_FAIL:
                    if (isShow) {
                        MyDialog.showTipDialog(getActivity(), "取消分享");
                    }
                    break;
                case Constant.BROADCASTCODE.FenXiangZCLJ:
                    MyDialog.share01(context, api, mTencent, "MainActivity", shareIndex.getShare_register_url(), shareIndex.getShare_title(), shareIndex.getShare_description(), shareIndex.getShare_icon());
                    break;
                case Constant.BROADCASTCODE.VIP:
                    onRefresh();
                    break;
                case Constant.BROADCASTCODE.FenXiangXiaZaiLJ:
                    MyDialog.share01(context, api, mTencent, "MainActivity", shareIndex.getDownUrl(), shareIndex.getDownTitle(), shareIndex.getDownDes(), shareIndex.getDownIco());
                    break;
            }
        }
    };
    private boolean isShow;
    private int grade = 0;
    private boolean isFrist = true;

    public ZhuanQianFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_zhuan_qian, container, false);
            mTencent = Tencent.createInstance(Constant.QQ_ID, getActivity());
            api = WXAPIFactory.createWXAPI(getActivity(), Constant.WXAPPID, true);
            init();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mInflate.getParent();
        if (parent != null) {
            parent.removeView(mInflate);
        }
        return mInflate;
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void findID() {
        mRelaTitleStatue = mInflate.findViewById(R.id.relaTitleStatue);
        recyclerView = (EasyRecyclerView) mInflate.findViewById(R.id.recyclerView);
        viewImg = (ImageView) mInflate.findViewById(R.id.viewImg);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = mRelaTitleStatue.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
        mRelaTitleStatue.setLayoutParams(layoutParams);
        mRelaTitleStatue.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);
        initRecycle();
    }

    @Override
    protected void setListeners() {
        viewImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), TuiGuangActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
    }

    private void initRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        int red = getResources().getColor(R.color.basic_color);
        recyclerView.setRefreshingColor(red);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<IndexMakemoney>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_zhuan_qian;
                return new ZhuanQianViewHolder(parent, layout);
            }
        });
//        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                View header_zahun_qian = LayoutInflater.from(getActivity()).inflate(R.layout.header_zahun_qian, null);
//                return header_zahun_qian;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//
//            }
//        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject1() {
        String url = Constant.HOST + Constant.Url.SHARE_INDEX;
        HashMap<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(userInfo.getUid())) {
            params.put("uid", userInfo.getUid());
        }
        if (!TextUtils.isEmpty(tokenTime)) {
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_MAKEMONEY;
        HashMap<String, String> params = new HashMap<>();
        try {
            params.put("uid", userInfo.getUid());
        } catch (Exception e) {
        }
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("赚钱", s);
                try {
                    IndexMakemoney indexMakemoney = GsonUtils.parseJSON(s, IndexMakemoney.class);
                    if (indexMakemoney.getStatus() == 1) {
                        grade = indexMakemoney.getGrade();
                        Glide.with(getActivity())
                                .load(indexMakemoney.getImg())
                                .asBitmap()
                                .placeholder(R.mipmap.ic_empty)
                                .into(viewImg);
                        adapter.clear();
                        adapter.add(indexMakemoney);
                        adapter.notifyDataSetChanged();
                        showLoadingDialog();
                        ApiClient.post(getActivity(), getOkObject1(), new ApiClient.CallBack() {
                            @Override
                            public void onSuccess(String s) {
                                cancelLoadingDialog();
                                LogUtil.LogShitou("TuiGuangEWMActivity--我的推广二维码", s + "");
                                try {
                                    shareIndex = GsonUtils.parseJSON(s, ShareIndex.class);
                                    if (shareIndex.getStatus() == 1) {
                                    } else if (shareIndex.getStatus() == 3) {
                                        MyDialog.showReLoginDialog(getActivity());
                                    } else {
                                        Toast.makeText(getActivity(), shareIndex.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), "数据出错", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Response response) {
                                cancelLoadingDialog();
                                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (indexMakemoney.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        showError(indexMakemoney.getInfo());
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
                View view_loaderror = LayoutInflater.from(getActivity()).inflate(R.layout.view_loaderror, null);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTencent.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE);
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE_FAIL);
        filter.addAction(Constant.BROADCASTCODE.FenXiangZCLJ);
        filter.addAction(Constant.BROADCASTCODE.FenXiangXiaZaiLJ);
        filter.addAction(Constant.BROADCASTCODE.VIP);
        getActivity().registerReceiver(reciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(reciver);
    }

    @Override
    public void onResume() {
        super.onResume();
        isShow = true;
        if (grade == 0 || isFrist) {
            isFrist = false;
            onRefresh();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isShow = false;
    }
}
