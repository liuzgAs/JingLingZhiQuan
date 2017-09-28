package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.BannerBean;
import com.sxbwstxpay.model.IndexDataBean;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.ShareBean;
import com.sxbwstxpay.model.StoreGoods;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.RecycleViewDistancaUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.LocalImageGuanLiWDDPHolderView;
import com.sxbwstxpay.viewholder.WoDeDPGLViewHolder;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class GuanLiWDDPActivity extends ZjbBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    public RecyclerArrayAdapter<IndexDataBean> adapter;
    private EasyRecyclerView recyclerView;
    private View viewBar;
    private int page = 1;
    private List<BannerBean> storeGoodsBanner;
    private String storeLogo;
    private String storeNmae;
    private String storeDes;
    private int viewBarHeight;
    private TextView textTitle;
    private IWXAPI api = WXAPIFactory.createWXAPI(GuanLiWDDPActivity.this, Constant.WXAPPID, true);
    private Tencent mTencent;
    private Bitmap bitmap;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.WX_SHARE:
                    MyDialog.showTipDialog(GuanLiWDDPActivity.this, "分享成功");
                    break;
                case Constant.BROADCASTCODE.WX_SHARE_FAIL:
                    MyDialog.showTipDialog(GuanLiWDDPActivity.this, "取消分享");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_li_wddp);
        mTencent = Tencent.createInstance(Constant.QQ_ID, this.getApplicationContext());
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
        textTitle = (TextView) findViewById(R.id.textTitle);
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
        String url = Constant.HOST + Constant.Url.STORE_GOODS;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("p", page + "");
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        page = 1;
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("管理我的店铺", s);
                try {
                    page++;
                    StoreGoods storeGoods = GsonUtils.parseJSON(s, StoreGoods.class);
                    if (storeGoods.getStatus() == 1) {
                        storeLogo = storeGoods.getStoreLogo();
                        storeNmae = storeGoods.getStoreNmae();
                        storeDes = storeGoods.getStoreDes();
                        storeGoodsBanner = storeGoods.getBanner();
                        textTitle.setText(storeNmae);
                        List<IndexDataBean> storeGoodsData = storeGoods.getData();
                        adapter.clear();
                        adapter.addAll(storeGoodsData);
                    } else if (storeGoods.getStatus() == 3) {
                        MyDialog.showReLoginDialog(GuanLiWDDPActivity.this);
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
                View view_loaderror = LayoutInflater.from(GuanLiWDDPActivity.this).inflate(R.layout.view_loaderror, null);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColor(getResources().getColor(R.color.basic_color));
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<IndexDataBean>(GuanLiWDDPActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_xian_shi_qg;
                return new WoDeDPGLViewHolder(parent, layout);
            }

        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private TextView textStoreDes;
            private TextView textStoreNmae;
            private ImageView imageStoreLogo;
            private ConvenientBanner banner;

            @Override
            public View onCreateView(ViewGroup parent) {
                View header_xian_shi_qg = LayoutInflater.from(GuanLiWDDPActivity.this).inflate(R.layout.header_guan_li_dp, null);
                banner = (ConvenientBanner) header_xian_shi_qg.findViewById(R.id.banner);
                banner.setScrollDuration(1000);
                banner.startTurning(3000);
                imageStoreLogo = (ImageView) header_xian_shi_qg.findViewById(R.id.imageStoreLogo);
                textStoreNmae = (TextView) header_xian_shi_qg.findViewById(R.id.textStoreNmae);
                textStoreDes = (TextView) header_xian_shi_qg.findViewById(R.id.textStoreDes);
                return header_xian_shi_qg;
            }

            @Override
            public void onBindView(View headerView) {
                if (storeGoodsBanner != null) {
                    banner.setPages(new CBViewHolderCreator() {
                        @Override
                        public Object createHolder() {
                            return new LocalImageGuanLiWDDPHolderView();
                        }
                    }, storeGoodsBanner);
                    banner.setPageIndicator(new int[]{R.drawable.shape_indicator_normal, R.drawable.shape_indicator_selected});
                }
                textStoreDes.setText(storeDes);
                textStoreNmae.setText(storeNmae);
                Glide.with(GuanLiWDDPActivity.this)
                        .load(storeLogo)
                        .dontAnimate()
                        .placeholder(R.mipmap.ic_empty)
                        .into(imageStoreLogo);
            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                ApiClient.post(GuanLiWDDPActivity.this, getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            page++;
                            StoreGoods storeGoods = GsonUtils.parseJSON(s, StoreGoods.class);
                            int status = storeGoods.getStatus();
                            if (status == 1) {
                                List<IndexDataBean> storeGoodsData = storeGoods.getData();
                                adapter.addAll(storeGoodsData);
                            } else if (status == 3) {
                                MyDialog.showReLoginDialog(GuanLiWDDPActivity.this);
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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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
    public void share(final ShareBean share) {
        LayoutInflater inflater = LayoutInflater.from(GuanLiWDDPActivity.this);
        View dialog_shengji = inflater.inflate(R.layout.dianlog_index_share, null);
        TextView textDes1 = (TextView) dialog_shengji.findViewById(R.id.textDes1);
        TextView textDes2 = (TextView) dialog_shengji.findViewById(R.id.textDes2);
        textDes1.setText(share.getTitle());
        SpannableString span = new SpannableString(share.getDes1() + share.getDesMoney() + share.getDes2());
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.basic_color)), share.getDes1().length(), share.getDes1().length() + share.getDesMoney().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textDes2.setText(span);
        final AlertDialog alertDialog1 = new AlertDialog.Builder(GuanLiWDDPActivity.this, R.style.dialog)
                .setView(dialog_shengji)
                .create();
        alertDialog1.show();
        dialog_shengji.findViewById(R.id.imageCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1.dismiss();
            }
        });
        dialog_shengji.findViewById(R.id.viewWeiXin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkIsSupportedWeachatPay()) {
                    Toast.makeText(GuanLiWDDPActivity.this, "您暂未安装微信,请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
                    return;
                }
                wxShare(0, share);
                alertDialog1.dismiss();
            }
        });
        dialog_shengji.findViewById(R.id.viewPengYouQuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkIsSupportedWeachatPay()) {
                    Toast.makeText(GuanLiWDDPActivity.this, "您暂未安装微信,请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
                    return;
                }
                wxShare(1, share);
                alertDialog1.dismiss();
            }
        });
        dialog_shengji.findViewById(R.id.viewErWeiMa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GuanLiWDDPActivity.this, "此功能暂未开放", Toast.LENGTH_SHORT).show();
                alertDialog1.dismiss();
            }
        });
        Window dialogWindow = alertDialog1.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogFenXiang);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = GuanLiWDDPActivity.this.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 1); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    /**
     * qq回调
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            Log.e("BaseUiListener", "BaseUiListener--onComplete--QQ分享" + o.toString());
        }

        @Override
        public void onError(UiError e) {
            Log.e("BaseUiListener", "code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
        }

        @Override
        public void onCancel() {
            Log.e("BaseUiListener", "BaseUiListener--onCancel--");
        }
    }

    private void wxShare(final int flag, final ShareBean share) {
        api.registerApp(Constant.WXAPPID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = share.getShareUrl();
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = share.getShareTitle();
        msg.description = share.getShareDes();
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = netPicToBmp(share.getShareImg());
                msg.setThumbImage(bitmap);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                switch (flag) {
                    case 0:
                        req.scene = SendMessageToWX.Req.WXSceneSession;
                        break;
                    case 1:
                        req.scene = SendMessageToWX.Req.WXSceneTimeline;
                        break;
                    case 2:
                        req.scene = SendMessageToWX.Req.WXSceneFavorite;
                        break;
                }
                api.sendReq(req);
            }
        }).start();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 检查微信版本是否支付支付或是否安装可支付的微信版本
     */
    private boolean checkIsSupportedWeachatPay() {
        boolean isPaySupported = api.getWXAppSupportAPI() >= com.tencent.mm.opensdk.constants.Build.PAY_SUPPORTED_SDK_INT;
        return isPaySupported;
    }

    public Bitmap netPicToBmp(String src) {
        try {
            Log.d("FileUtil", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            //设置固定大小
            //需要的大小
            float newWidth = 200f;
            float newHeigth = 200f;

            //图片大小
            int width = myBitmap.getWidth();
            int height = myBitmap.getHeight();

            //缩放比例
            float scaleWidth = newWidth / width;
            float scaleHeigth = newHeigth / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeigth);

            Bitmap bitmap = Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true);
            return bitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
