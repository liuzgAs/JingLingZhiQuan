package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.fragment.RenZhengFragment;
import com.sxbwstxpay.fragment.ShengQianCZFragment;
import com.sxbwstxpay.fragment.ShouKuanFragment;
import com.sxbwstxpay.fragment.WoDeFragment;
import com.sxbwstxpay.fragment.ZhuanQianFragment;
import com.sxbwstxpay.model.ExtraMap;
import com.sxbwstxpay.model.IndexGoods;
import com.sxbwstxpay.model.UserInfo;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.BackHandlerHelper;
import com.sxbwstxpay.util.UpgradeUtils;
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


public class MainActivity extends AppCompatActivity {
    private String[] tabsItem = new String[5];
    private Class[] fragment = new Class[]{
            ShengQianCZFragment.class,
//            ShengQianFragment.class,
            ZhuanQianFragment.class,
            ShouKuanFragment.class,
            RenZhengFragment.class,
            WoDeFragment.class
    };
    private int[] imgRes = new int[]{
            R.drawable.selector_shengqian_item,
            R.drawable.selector_zhuanqian_item,
            R.drawable.selector_shoukuan_item,
            R.drawable.selector_renzheng_item,
            R.drawable.selector_mine_item
    };
    public FragmentTabHost mTabHost;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.WX_SHARE:
                    MyDialog.showTipDialog(MainActivity.this, "分享成功");
                    break;
                case Constant.BROADCASTCODE.WX_SHARE_FAIL:
                    MyDialog.showTipDialog(MainActivity.this, "取消分享");
                    break;
                case Constant.BROADCASTCODE.EXTRAMAP:
                    ExtraMap extraMap = (ExtraMap) intent.getSerializableExtra(Constant.INTENT_KEY.EXTRAMAP);
                    try {
                        action(extraMap);
                    } catch (Exception e) {
                    }
                    break;
            }
        }
    };
    public UserInfo userInfo;
    private IWXAPI api = WXAPIFactory.createWXAPI(MainActivity.this, Constant.WXAPPID, true);
    private Tencent mTencent;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        Constant.MainActivityAlive = 1;
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mTencent = Tencent.createInstance(Constant.QQ_ID, this.getApplicationContext());
        //检查更新
        UpgradeUtils.checkUpgrade(MainActivity.this, Constant.HOST + Constant.Url.INDEX_VERSION);
        ACache aCache = ACache.get(this, Constant.ACACHE.App);
        userInfo = (UserInfo) aCache.getAsObject(Constant.ACACHE.USER_INFO);
        tabsItem[0] = "省钱";
        tabsItem[1] = "赚钱";
        tabsItem[2] = "收款";
        tabsItem[3] = "认证";
        tabsItem[4] = "我的";

        mTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtab);
        for (int i = 0; i < tabsItem.length; i++) {
            View inflate = getLayoutInflater().inflate(R.layout.tabs_item, null);
            TextView tabs_text = (TextView) inflate.findViewById(R.id.tabs_text);
            ImageView tabs_img = (ImageView) inflate.findViewById(R.id.tabs_img);
            tabs_text.setText(tabsItem[i]);
            tabs_img.setImageResource(imgRes[i]);
            mTabHost.addTab(mTabHost.newTabSpec(tabsItem[i]).setIndicator(inflate), fragment[i], null);
        }
        mTabHost.setCurrentTab(1);
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    private void action(ExtraMap extramap) {
        if (extramap != null) {
            Intent intent = new Intent();
            switch (extramap.getCode()) {
                case "app_pay":
                    mTabHost.setCurrentTab(2);
                    break;
                case "app_user":
                    mTabHost.setCurrentTab(4);
                    break;
                case "app_account":
                    intent.setClass(this, WoDeZDActivity.class);
                    startActivity(intent);
                    break;
                case "app_vip":
                    intent.setClass(this, TuiGuangActivity.class);
                    startActivity(intent);
                    break;
                case "web":
                    if (!TextUtils.isEmpty(extramap.getUrl())) {
                        Intent intent1 = new Intent();
                        intent1.setClass(MainActivity.this, WebActivity.class);
                        intent1.putExtra(Constant.INTENT_KEY.TITLE, extramap.getTitle());
                        intent1.putExtra(Constant.INTENT_KEY.URL, extramap.getUrl() + "&uid=" + extramap.getUid());
                        startActivity(intent1);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.EXTRAMAP);
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE);
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE_FAIL);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.MainActivityAlive = 0;
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
    public void share(final IndexGoods.DataBean.ShareBean share) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
////                bitmap = netPicToBmp(share.getShareImg());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View dialog_shengji = inflater.inflate(R.layout.dianlog_index_share, null);
        final AlertDialog alertDialog1 = new AlertDialog.Builder(MainActivity.this, R.style.dialog)
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
                    Toast.makeText(MainActivity.this, "您暂未安装微信,请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MainActivity.this, "您暂未安装微信,请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
                    return;
                }
                wxShare(1, share);
                alertDialog1.dismiss();
            }
        });
        dialog_shengji.findViewById(R.id.viewErWeiMa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "此功能暂未开放", Toast.LENGTH_SHORT).show();
                alertDialog1.dismiss();
            }
        });
//                        dialog_shengji.findViewById(R.id.relaShouCang).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                wxShare(2, share);
//                                alertDialog1.dismiss();
//                                alertDialog1.dismiss();
//                            }
//                        });
//                        dialog_shengji.findViewById(R.id.relatQQ).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                final Bundle params = new Bundle();
//                                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//                                params.putString(QQShare.SHARE_TO_QQ_TITLE, share.getTitle());
//                                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, share.getShareDes());
//                                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, share.getShareUrl());
//                                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, share.getShareImg());
//                                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getResources().getString(R.string.app_name));
////                        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, );
//                                mTencent.shareToQQ(MainActivity.this, params,new BaseUiListener());
//                                alertDialog1.dismiss();
//                            }
//                        });
//                        dialog_shengji.findViewById(R.id.relaQzone).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                final Bundle params = new Bundle();
//                                params.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_KEY_TYPE);
//                                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, share.getTitle());
//                                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, share.getShareDes());
//                                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, share.getShareUrl());
//                                params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, share.getShareImg());
//                                params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, getResources().getString(R.string.app_name));
//                                ArrayList<String> value = new ArrayList<String>();
//                                value.add(share.getShareImg());
//                                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, value);
//                                mTencent.shareToQzone(MainActivity.this, params, new BaseUiListener());
//                                alertDialog1.dismiss();
//                            }
//                        });
        Window dialogWindow = alertDialog1.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogFenXiang);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = MainActivity.this.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 1); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
//                    }
//                });
//            }
//        }).start();
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

    private void wxShare(final int flag, final IndexGoods.DataBean.ShareBean share) {
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
