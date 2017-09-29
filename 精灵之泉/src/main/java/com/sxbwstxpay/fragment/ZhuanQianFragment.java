package com.sxbwstxpay.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
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

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.sxbwstxpay.R;
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
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.constants.Build;
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
import java.util.ArrayList;
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
    private IWXAPI api ;
    private Tencent mTencent;
    private ShareIndex shareIndex;
    private Bitmap bitmap;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.WX_SHARE:
                    if (isShow){
                        MyDialog.showTipDialog(getActivity(), "分享成功");
                    }
                    break;
                case Constant.BROADCASTCODE.WX_SHARE_FAIL:
                    if (isShow){
                        MyDialog.showTipDialog(getActivity(), "取消分享");
                    }
                    break;
                case Constant.BROADCASTCODE.FenXiangZCLJ:
                    share();
                    break;
            }
        }
    };
    private boolean isShow;

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

    }

    @Override
    protected void initData() {
        onRefresh();
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
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
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
                        Glide.with(getActivity())
                                .load(indexMakemoney.getImg())
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
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                bitmap = netPicToBmp(shareIndex.getShare_icon());
                                            }
                                        }).start();
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

    private void wxShare(int flag, String url) {
        api.registerApp(Constant.WXAPPID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareIndex.getShare_title();
        msg.description = shareIndex.getShare_description();
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

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 检查微信版本是否支付支付或是否安装可支付的微信版本
     */
    private boolean checkIsSupportedWeachatPay() {
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
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

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE);
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE_FAIL);
        filter.addAction(Constant.BROADCASTCODE.FenXiangZCLJ);
        getActivity().registerReceiver(reciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(reciver);
    }

    public void share() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialog_shengji = inflater.inflate(R.layout.dianlog_share, null);
        final AlertDialog alertDialog1 = new AlertDialog.Builder(getActivity(), R.style.dialog)
                .setView(dialog_shengji)
                .create();
        alertDialog1.show();
        dialog_shengji.findViewById(R.id.textViewCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1.dismiss();
            }
        });
        dialog_shengji.findViewById(R.id.weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkIsSupportedWeachatPay()) {
                    Toast.makeText(getActivity(), "您暂未安装微信,请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
                    return;
                }
                wxShare(0, shareIndex.getShare_register_url());
                alertDialog1.dismiss();
            }
        });
        dialog_shengji.findViewById(R.id.pengyouquan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkIsSupportedWeachatPay()) {
                    Toast.makeText(getActivity(), "您暂未安装微信,请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
                    return;
                }
                wxShare(1, shareIndex.getShare_register_url());
                alertDialog1.dismiss();
            }
        });
        dialog_shengji.findViewById(R.id.relaShouCang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wxShare(2, shareIndex.getShare_register_url());
                alertDialog1.dismiss();
                alertDialog1.dismiss();
            }
        });
        dialog_shengji.findViewById(R.id.relatQQ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, shareIndex.getShare_title());
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareIndex.getShare_description());
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareIndex.getShare_register_url());
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareIndex.getShare_icon());
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getResources().getString(R.string.app_name));
//                        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, );
                mTencent.shareToQQ(getActivity(), params, new BaseUiListener());
                alertDialog1.dismiss();
            }
        });
        dialog_shengji.findViewById(R.id.relaQzone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bundle params = new Bundle();
                params.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_KEY_TYPE);
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareIndex.getShare_title());
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareIndex.getShare_description());
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareIndex.getShare_register_url());
                params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, shareIndex.getShare_icon());
                params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, getResources().getString(R.string.app_name));
                ArrayList<String> value = new ArrayList<String>();
                value.add(shareIndex.getShare_icon());
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, value);
                mTencent.shareToQzone(getActivity(), params, new BaseUiListener());
                alertDialog1.dismiss();
            }
        });
        Window dialogWindow = alertDialog1.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogFenXiang);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getActivity().getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 1); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onResume() {
        super.onResume();
        isShow = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isShow=false;
    }
}
