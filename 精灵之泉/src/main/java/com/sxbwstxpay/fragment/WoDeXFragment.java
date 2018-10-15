package com.sxbwstxpay.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.CeShiSYActivity;
import com.sxbwstxpay.activity.GongGaoActivity;
import com.sxbwstxpay.activity.JingLingKTActivity;
import com.sxbwstxpay.activity.JingLingLCActivity;
import com.sxbwstxpay.activity.ShangChengDDActivity;
import com.sxbwstxpay.activity.SheZhiActivity;
import com.sxbwstxpay.activity.ShiMingRZActivity;
import com.sxbwstxpay.activity.TuiGuangActivity;
import com.sxbwstxpay.activity.WebActivity;
import com.sxbwstxpay.activity.WoDeDPXActivity;
import com.sxbwstxpay.activity.WoDeSHActivity;
import com.sxbwstxpay.activity.WoDeSYActivity;
import com.sxbwstxpay.activity.WoDeZDActivity;
import com.sxbwstxpay.activity.WoDeZLActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.Test_result;
import com.sxbwstxpay.model.UserIndex;
import com.sxbwstxpay.model.WoDe;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.RecycleViewDistancaUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.WoDeViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WoDeXFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WoDeXFragment extends ZjbBaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private TextView textTitleX;

    private String mParam1;
    private View mInflate;
    private View relaTitleStatue;
    private EasyRecyclerView recyclerView;
    RecyclerArrayAdapter<WoDe> adapter;
    ArrayList<WoDe> woDes = new ArrayList<>();
    private UserIndex userIndex;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.CHANGEWODE:
                    onRefresh();
                    break;
                case Constant.BROADCASTCODE.VIP:
                    onRefresh();
                    break;
                case Constant.BROADCASTCODE.MINE:
                    onRefresh();
                    break;
                default:
                    break;
            }
        }
    };
    public WoDeXFragment() {
    }

    private int viewBarHeight;

    public static WoDeXFragment newInstance(String param1) {
        WoDeXFragment fragment = new WoDeXFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_wo_de_x, container, false);
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
        relaTitleStatue = mInflate.findViewById(R.id.relaTitleStatue);
        recyclerView = mInflate.findViewById(R.id.recyclerView);
        textTitleX = mInflate.findViewById(R.id.textTitleX);
    }

    @Override
    protected void initViews() {
        woDes.clear();
        woDes.add(new WoDe("商城订单", R.mipmap.wode_scdd));
        woDes.add(new WoDe("我的店铺", R.mipmap.wode_wddp));
        woDes.add(new WoDe("我的商户", R.mipmap.wode_wdzl));
        woDes.add(new WoDe("我的风格", R.mipmap.wode_wdfg));
        woDes.add(new WoDe("风格测试", R.mipmap.wode_fgcs));
        woDes.add(new WoDe("联系客服", R.mipmap.wode_lxkf));
        woDes.add(new WoDe("站内公告", R.mipmap.wode_zngg));
        woDes.add(new WoDe("资质证书", R.mipmap.wode_zzzs));
        woDes.add(new WoDe("精灵课堂", R.mipmap.wode_bzzx));
        textTitleX.setText("个人中心");
        viewBarHeight = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
        relaTitleStatue.getBackground().mutate().setAlpha(0);
        ViewGroup.LayoutParams layoutParams = relaTitleStatue.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
        relaTitleStatue.setLayoutParams(layoutParams);
        initRecycler();
    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.imageSheZhi).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(manager);
        SpaceDecoration spaceDecoration = new SpaceDecoration((int) DpUtils.convertDpToPixel(1f, getActivity()));
        recyclerView.addItemDecoration(spaceDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.getSwipeToRefresh().setProgressViewOffset(true, 30, 220);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<WoDe>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_wode;
                return new WoDeViewHolder(parent, layout);
            }
        });
        manager.setSpanSizeLookup(adapter.obtainGridSpanSizeLookUp(3));
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            private View viewTitle;
            private ImageView imageTouXiang;
            private TextView textNickName;
            private TextView textTest;
            private TextView texttXName;
            private View viewNoVip;
            private Button btnSure;
            private View viewVip;
            private TextView textVipTime;
            private TextView textVipText;
            private TextView textVipType;
            private View viewSmrz;
            private View viewJllc;
            private View viewWdzd;
            private View viewWdsy;

            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.header_wo_de, null);
                viewTitle = view.findViewById(R.id.viewTitle);
                imageTouXiang = view.findViewById(R.id.imageTouXiang);
                textNickName = view.findViewById(R.id.textNickName);
                textTest = view.findViewById(R.id.textTest);
                texttXName = view.findViewById(R.id.texttXName);
                viewNoVip = view.findViewById(R.id.viewNoVip);
                btnSure = view.findViewById(R.id.btnSure);
                viewVip = view.findViewById(R.id.viewVip);
                textVipTime = view.findViewById(R.id.textVipTime);
                textVipText = view.findViewById(R.id.textVipText);
                textVipType = view.findViewById(R.id.textVipType);

                viewSmrz = view.findViewById(R.id.viewSmrz);
                viewJllc = view.findViewById(R.id.viewJllc);
                viewWdzd = view.findViewById(R.id.viewWdzd);
                viewWdsy = view.findViewById(R.id.viewWdsy);

                ViewGroup.LayoutParams layoutParams = viewTitle.getLayoutParams();
                layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
                viewTitle.setLayoutParams(layoutParams);

                viewSmrz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), ShiMingRZActivity.class);
                        startActivity(intent);
                    }
                });
                viewJllc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), JingLingLCActivity.class);
                        startActivity(intent);
                    }
                });
                viewWdzd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), WoDeZDActivity.class);
                        startActivity(intent);
                    }
                });
                viewWdsy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), WoDeSYActivity.class);
                        startActivity(intent);
                    }
                });
                imageTouXiang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), WoDeZLActivity.class);
                        startActivity(intent);
                    }
                });
                texttXName.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(userInfo.getHeadImg())) {
                    GlideApp.with(getActivity())
                            .asBitmap()
                            .load(userInfo.getHeadImg())
                            .placeholder(R.mipmap.ic_empty)
                            .into(imageTouXiang);
                }
                textNickName.setText(userInfo.getNickName());
                textTest.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                textVipType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (userIndex != null) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), WebActivity.class);
                            intent.putExtra(Constant.INTENT_KEY.TITLE, userIndex.getBtnText());
                            intent.putExtra(Constant.INTENT_KEY.URL, userIndex.getBtnUrl());
                            startActivity(intent);
                        }
                    }
                });
                btnSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), TuiGuangActivity.class);
                        startActivity(intent);
                    }
                });
                textTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (userIndex!=null){
                            if (userIndex.getIs_test()==1){
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), CeShiSYActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                });
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                if (userIndex != null) {
                    GlideApp.with(getActivity())
                            .asBitmap()
                            .load(userIndex.getHeadImg())
                            .dontAnimate()
                            .placeholder(R.mipmap.ic_empty)
                            .into(imageTouXiang);
                    if (!TextUtils.isEmpty(userIndex.getStyle_text())){
                        textTest.setText(userIndex.getStyle_text());
                    }else {
                        textTest.setText("风格未知，请测试");
                    }
                    textNickName.setText(userIndex.getNickName());
                    textVipText.setText(userIndex.getGradeName());
                    textVipTime.setText(userIndex.getVipTime());
                    textVipType.setText(userIndex.getBtnText());
                    if (TextUtils.isEmpty(userIndex.getTxName())) {
                        texttXName.setVisibility(View.GONE);
                    } else {
                        texttXName.setText(userIndex.getTxName());
                        texttXName.setVisibility(View.VISIBLE);
                    }
                    if (userIndex.getGrade() == 0) {
                        viewNoVip.setVisibility(View.VISIBLE);
                        viewVip.setVisibility(View.GONE);
                    } else {
                        viewNoVip.setVisibility(View.GONE);
                        viewVip.setVisibility(View.VISIBLE);
                    }
                    if (userIndex.getIs_btn() == 1) {
                        textVipType.setVisibility(View.VISIBLE);
                    } else {
                        textVipType.setVisibility(View.GONE);
                    }
                }
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
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(getActivity(), ShangChengDDActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        if (userIndex==null){
                            return;
                        }
                        if (TextUtils.isEmpty(userIndex.getStoreTips())) {
                            intent.setClass(getActivity(), WoDeDPXActivity.class);
                            startActivity(intent);
                        } else {
                            MyDialog.showTipDialog(getActivity(), userIndex.getStoreTips());
                        }
                        break;
                    case 2:
                        intent.setClass(getActivity(), WoDeSHActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        getRez();
                        break;
                    case 4:
                        intent.setClass(getActivity(), CeShiSYActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent.setClass(getActivity(), WebActivity.class);
                        intent.putExtra(Constant.INTENT_KEY.TITLE, "联系客服");
                        intent.putExtra(Constant.INTENT_KEY.URL, Constant.HOST + Constant.Url.INFO_CONTACT);
                        startActivity(intent);
                        break;
                    case 6:
                        intent.setClass(getActivity(), GongGaoActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent.setClass(getActivity(), WebActivity.class);
                        intent.putExtra(Constant.INTENT_KEY.TITLE, "资质证书");
                        intent.putExtra(Constant.INTENT_KEY.URL, Constant.HOST + Constant.Url.INFO_CA);
                        startActivity(intent);
                        break;
                    case 8:
                        intent.setClass(getActivity(), JingLingKTActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
        
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int scrollY = RecycleViewDistancaUtil.getDistance(recyclerView, 0);
                float guangGaoHeight = getResources().getDimension(R.dimen.chanPinXQBanner1);
                if (scrollY <= guangGaoHeight - viewBarHeight && scrollY >= 0) {
                    int i = (int) ((double) scrollY / (double) (guangGaoHeight - relaTitleStatue.getHeight()) * 255);
                    relaTitleStatue.getBackground().mutate().setAlpha(i);
                    LogUtil.LogShitou("i", i + "");
                    textTitleX.setTextColor(Color.argb(i, 255, 255, 255));

                } else {
                    relaTitleStatue.getBackground().mutate().setAlpha(255);
                    textTitleX.setTextColor(Color.argb(255, 255, 255, 255));
                }
            }
        });
        recyclerView.setRefreshListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_INDEX;
        HashMap<String, String> params = new HashMap<>();
        if (userInfo!=null){
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("WoDeFragment--我的", s + "");
                try {
                    userIndex = GsonUtils.parseJSON(s, UserIndex.class);
                    if (userIndex.getStatus() == 1) {
                        adapter.clear();
                        adapter.addAll(woDes);
                    } else if (userIndex.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        Toast.makeText(getActivity(), userIndex.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    try {
                        Toast.makeText(getActivity(), R.string.dataErrer, Toast.LENGTH_SHORT).show();
                    } catch (Exception e1) {
                    }
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.imageSheZhi:
                intent.setClass(getActivity(), SheZhiActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.CHANGEWODE);
        filter.addAction(Constant.BROADCASTCODE.VIP);
        filter.addAction(Constant.BROADCASTCODE.MINE);
        getActivity().registerReceiver(reciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(reciver);
    }
    private void getRez(){
        showLoadingDialog();
        ApiClient.post(getActivity(), getOkObjectRez(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("TestResultActivity--测试结果", "");
                try {
                    Test_result test_result = GsonUtils.parseJSON(s, Test_result.class);
                    if (test_result.getStatus() == 1) {
                        Intent intent=new Intent();
                        intent.setClass(getActivity(), WebActivity.class);
                        intent.putExtra(Constant.INTENT_KEY.TITLE, test_result.getStyle());
                        intent.putExtra(Constant.INTENT_KEY.URL,test_result.getUrl());
                        intent.putExtra(Constant.INTENT_KEY.CID,test_result.getCid());
                        startActivity(intent);
                    } else if (test_result.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        Toast.makeText(getActivity(), test_result.getInfo(), Toast.LENGTH_SHORT).show();
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
    }
    private OkObject getOkObjectRez() {
        String url = Constant.HOST + Constant.Url.TEST_RESULT;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("style", "");
        return new OkObject(params, url);
    }
}
