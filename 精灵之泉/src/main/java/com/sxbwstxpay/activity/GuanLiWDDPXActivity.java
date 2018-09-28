package com.sxbwstxpay.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.BannerBean;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.ShareBean;
import com.sxbwstxpay.model.SkillDetails;
import com.sxbwstxpay.model.StoreGoods;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.RecycleViewDistancaUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.DianPuFWViewHolder;
import com.sxbwstxpay.viewholder.LocalImageGuanLiWDDPHolderView;
import com.sxbwstxpay.viewholder.ShangjiaDPViewHolder;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import java.util.HashMap;
import java.util.List;

import okhttp3.Response;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class GuanLiWDDPXActivity extends ZjbBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, EasyPermissions.PermissionCallbacks {
    public RecyclerArrayAdapter<SkillDetails.DataBean> adapter;
    private EasyRecyclerView recyclerView;
    private View viewBar;
    private int page = 1;
    private List<BannerBean> storeGoodsBanner;
    private String storeLogo;
    private List<SkillDetails.SkillBean> skillBeans;
    private String storeNmae;
    private String storeDes;
    private int viewBarHeight;
    private TextView textTitle;
    private IWXAPI api = WXAPIFactory.createWXAPI(GuanLiWDDPXActivity.this, Constant.WXAPPID, true);
    private Tencent mTencent;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.WX_SHARE:
                    MyDialog.showTipDialog(GuanLiWDDPXActivity.this, "分享成功");
                    break;
                case Constant.BROADCASTCODE.WX_SHARE_FAIL:
                    MyDialog.showTipDialog(GuanLiWDDPXActivity.this, "取消分享");
                    break;
                default:
                    break;
            }
        }
    };
    private String previewUrl;
    private StoreGoods.ShareBean share;
    private String id;
    private int type;
    private String lat;
    private String lng;
    private boolean isSuccess = true;
    private String tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_li_wddp);
        mTencent = Tencent.createInstance(Constant.QQ_ID, this.getApplicationContext());
        init(GuanLiWDDPXActivity.class);
    }

    @Override
    protected void initSP() {
        final ACache aCache = ACache.get(this, Constant.ACACHE.LOCATION);
        String cityAcache = aCache.getAsString(Constant.ACACHE.CITY);
        if (cityAcache != null) {
            lat = aCache.getAsString(Constant.ACACHE.LAT);
            lng = aCache.getAsString(Constant.ACACHE.LNG);
        }
    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        id = intent.getStringExtra(Constant.INTENT_KEY.id);
        type = intent.getIntExtra(Constant.INTENT_KEY.type, 0);
    }

    @Override
    protected void findID() {
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        viewBar = findViewById(R.id.viewBar);
        textTitle = (TextView) findViewById(R.id.textName);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        viewBarHeight = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        layoutParams.height = viewBarHeight;
        viewBar.setLayoutParams(layoutParams);
        viewBar.getBackground().mutate().setAlpha(0);
        textTitle.setAlpha(0);
        initRecycler();
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.textYuLan).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
    }


    @Override
    public void onRefresh() {
        page = 1;
        benDiYD();
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getBenDiYDOkObject() {
        String url = Constant.HOST + Constant.Url.SKILL_DETAILS;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("p", page + "");
        params.put("sid", id);
        return new OkObject(params, url);
    }

    private void benDiYD() {
        ApiClient.post(this, getBenDiYDOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("本地优店店铺", s);
                try {
                    page++;
                    SkillDetails storeGoods = GsonUtils.parseJSON(s, SkillDetails.class);
                    if (storeGoods.getStatus() == 1) {
                        storeLogo = storeGoods.getStore().getLogo();
                        storeNmae = storeGoods.getStore().getName();
                        storeDes = storeGoods.getStore().getDes();
                        tel = storeGoods.getStore().getPhone();
                        skillBeans=storeGoods.getSkill();
                        textTitle.setText(storeNmae);
                        LogUtil.LogShitou("GuanLiWDDPActivity--onSuccess textTitle", "" + textTitle.getText().toString().trim());
                        List<SkillDetails.DataBean> storeGoodsData = storeGoods.getData();
                        adapter.clear();
                        adapter.addAll(storeGoodsData);
                    } else if (storeGoods.getStatus() == 3) {
                        MyDialog.showReLoginDialog(GuanLiWDDPXActivity.this);
                    } else {
                        showError(storeGoods.getInfo());
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
                isSuccess = false;
                LogUtil.LogShitou("GuanLiWDDPActivity--showError", "11111111111");
                viewBar.getBackground().mutate().setAlpha(255);
                View view_loaderror = LayoutInflater.from(GuanLiWDDPXActivity.this).inflate(R.layout.view_loaderror, null);
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


    private void initRecycler() {
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        SpaceDecoration spaceDecoration = new SpaceDecoration((int) DpUtils.convertDpToPixel(5f, this));
        recyclerView.addItemDecoration(spaceDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<SkillDetails.DataBean>(GuanLiWDDPXActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_xian_shi_qg;
                return new ShangjiaDPViewHolder(parent, layout);
            }

        });
        manager.setSpanSizeLookup(adapter.obtainGridSpanSizeLookUp(2));
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private TextView textTel;
            private TextView textDes;
            private TextView textStoreNmae;
            private ImageView imageStoreLogo;
            private ConvenientBanner banner;
            private EasyRecyclerView hrecyclerView;
            private RecyclerArrayAdapter<SkillDetails.SkillBean> hadapter;
            @Override
            public View onCreateView(ViewGroup parent) {
                View header_xian_shi_qg = LayoutInflater.from(GuanLiWDDPXActivity.this).inflate(R.layout.header_guan_li_dp, null);
                View viewJingPinTuiJian = header_xian_shi_qg.findViewById(R.id.viewJingPinTuiJian);
                banner = (ConvenientBanner) header_xian_shi_qg.findViewById(R.id.banner);
                if (type == 0) {
                    viewJingPinTuiJian.setVisibility(View.VISIBLE);
                    banner.setVisibility(View.VISIBLE);
                    banner.setScrollDuration(1000);
                    banner.startTurning(3000);
                } else {
                    viewJingPinTuiJian.setVisibility(View.GONE);
                    banner.setVisibility(View.GONE);
                }

                imageStoreLogo = (ImageView) header_xian_shi_qg.findViewById(R.id.imageStoreLogo);
                textStoreNmae = (TextView) header_xian_shi_qg.findViewById(R.id.textStoreNmae);
                textDes = (TextView) header_xian_shi_qg.findViewById(R.id.textDes);
                textTel = (TextView) header_xian_shi_qg.findViewById(R.id.textTel);
                hrecyclerView = (EasyRecyclerView) header_xian_shi_qg.findViewById(R.id.hrecyclerView);
                initRecyclerh();
//                header_xian_shi_qg.findViewById(R.id.viewShare).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        MyDialog.share01(GuanLiWDDPActivity.this, api, mTencent, "GuanLiWDDPActivity", share.getShareUrl(), share.getShareTitle(), share.getShareDes(), share.getShareImg());
//                    }
//                });
                header_xian_shi_qg.findViewById(R.id.buttonGuanLi).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requiresPermission(tel);
                    }
                });
                return header_xian_shi_qg;
            }
            /**
             * 初始化recyclerview
             */
            private void initRecyclerh() {
                hrecyclerView.setLayoutManager(new LinearLayoutManager(GuanLiWDDPXActivity.this));
                DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.line_width), 0, 0);
                itemDecoration.setDrawLastItem(false);
                hrecyclerView.addItemDecoration(itemDecoration);
                hrecyclerView.setRefreshingColorResources(R.color.basic_color);
                hrecyclerView.setAdapterWithProgress(hadapter = new RecyclerArrayAdapter<SkillDetails.SkillBean>(GuanLiWDDPXActivity.this) {
                    @Override
                    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                        int layout = R.layout.item_dianpu_fw;
                        return new DianPuFWViewHolder(parent, layout);
                    }
                });
                hadapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                    }
                });
            }

            @Override
            public void onBindView(View headerView) {
                if (storeGoodsBanner != null && type == 0) {
                    banner.setPages(new CBViewHolderCreator() {
                        @Override
                        public Object createHolder() {
                            return new LocalImageGuanLiWDDPHolderView();
                        }
                    }, storeGoodsBanner);
                    banner.setPageIndicator(new int[]{R.drawable.shape_indicator_normal, R.drawable.shape_indicator_selected});
                }
                if (skillBeans!=null){
                    hadapter.clear();
                    hadapter.addAll(skillBeans);
                }
                textStoreNmae.setText(storeNmae);
                GlideApp.with(GuanLiWDDPXActivity.this)
                        .asBitmap()
                        .load(storeLogo)
                        .dontAnimate()
                        .placeholder(R.mipmap.ic_empty)
                        .into(imageStoreLogo);
                textDes.setText(storeDes);
                textTel.setText("商家电话：" + tel);
            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                    benDiYDMore();
            }

            private void benDiYDMore() {
                ApiClient.post(GuanLiWDDPXActivity.this, getBenDiYDOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            page++;
                            SkillDetails storeGoods = GsonUtils.parseJSON(s, SkillDetails.class);
                            int status = storeGoods.getStatus();
                            if (status == 1) {
                                List<SkillDetails.DataBean> storeGoodsData = storeGoods.getData();
                                adapter.addAll(storeGoodsData);
                            } else if (status == 3) {
                                MyDialog.showReLoginDialog(GuanLiWDDPXActivity.this);
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
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.id, adapter.getItem(position).getId());
                intent.setClass(GuanLiWDDPXActivity.this, ChanPinXQActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        recyclerView.setRefreshListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LogUtil.LogShitou("GuanLiWDDPActivity--onScrolled", "22222222222");
                if (isSuccess) {
                    int scrollY = RecycleViewDistancaUtil.getDistance(recyclerView, 0);
                    float guangGaoHeight = getResources().getDimension(R.dimen.guanLiDianPuTop);
                    if (scrollY <= guangGaoHeight - viewBarHeight && scrollY >= 0) {
                        int i = (int) ((double) scrollY / (double) (guangGaoHeight - viewBar.getHeight()) * 255);
                        viewBar.getBackground().mutate().setAlpha(i);
                        textTitle.setAlpha((float) i / 255f);
                    } else {
                        viewBar.getBackground().mutate().setAlpha(255);
                        textTitle.setAlpha(1);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textYuLan:
                Intent intent = new Intent();
                intent.setClass(this, WebActivity.class);
                intent.putExtra(Constant.INTENT_KEY.TITLE, "我的店铺预览");
                intent.putExtra(Constant.INTENT_KEY.URL, previewUrl);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE);
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE_FAIL);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTencent.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * des： 分享
     * author： ZhangJieBo
     * date： 2017/9/25 0025 上午 11:54
     */
    public void share(String id, String type, ShareBean share) {
        MyDialog.share(GuanLiWDDPXActivity.this, "GuanLiWDDPActivity", api, id, type, share);
    }


    private static final int CALL_PHONE = 1991;
    private String phone;

    /**
     * 检查权限
     */
    private void requiresPermission(String phone) {
        this.phone = phone;
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            call();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "需要拨打电话权限",
                    CALL_PHONE, perms);
        }
    }

    /**
     * 拨打电话
     */
    private void call() {
        /*跳转到拨号界面，同时传递电话号码*/
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "电话号码为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(dialIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        call();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("为了您能使用拨打电话功能，请开启打电话权限！")
                    .setPositiveButton("去设置")
                    .setNegativeButton("取消")
                    .setRequestCode(CALL_PHONE)
                    .build()
                    .show();
        }
    }
}
