package com.sxbwstxpay.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ChanPinXQActivity;
import com.sxbwstxpay.activity.MainActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.GoodsEwm;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.ShareBean;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.FileUtil;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import okhttp3.Response;


/**
 * Created by Administrator on 2017/8/27.
 */
public class MyDialog {
    public static void showReLoginDialog(final Context context) {
        final AlertDialog reLoginDialog = new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("您的账号在其他设备上登录，请重新登录")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToLoginActivity.toLoginActivity(context);
                    }
                })
                .create();
        reLoginDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    reLoginDialog.dismiss();
                    ToLoginActivity.toLoginActivity(context);
                }
                return false;
            }
        });
        reLoginDialog.show();
    }

    /**
     * 单个按钮无操作
     *
     * @param msg
     */
    public static void showTipDialog(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setPositiveButton("是", null)
                .create()
                .show();
    }

    public static void showPicDialog(Context context, String img) {
        View dialog_img_show = LayoutInflater.from(context).inflate(R.layout.dialog_img_show, null);
        ImageView imageView = (ImageView) dialog_img_show.findViewById(R.id.imageView);
        Glide.with(context)
                .load(img)
                .dontAnimate()
                .placeholder(R.mipmap.ic_empty)
                .into(imageView);
        final AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.dialog)
                .setView(dialog_img_show)
                .create();
        alertDialog.show();
        Window dialogWindow = alertDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setWindowAnimations(R.style.AnimFromTopToButtom);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.6); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    public static void dialogResultFinish(final Context context, String msg, final int code) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((ZjbBaseActivity) context).setResult(code);
                        ((ZjbBaseActivity) context).finish();
                    }
                })
                .create();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    dialog.dismiss();
                    ((ZjbBaseActivity) context).setResult(code);
                    ((ZjbBaseActivity) context).finish();
                }
                return false;
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void dialogFinish(final Activity activity, String msg) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage(msg)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        activity.finish();
                    }
                })
                .create();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    dialog.dismiss();
                    activity.finish();
                }
                return false;
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private static OkObject getOkObjectShareEWM(Context context, String activity, String id, String type) {
        String url = Constant.HOST + Constant.Url.GOODS_EWM;
        HashMap<String, String> params = new HashMap<>();
        switch (activity) {
            case "MainActivity":
                params.put("uid", ((MainActivity) context).userInfo.getUid());
                params.put("tokenTime", ((MainActivity) context).tokenTime);
                break;
            case "ChanPinXQActivity":
                params.put("uid", ((ChanPinXQActivity) context).userInfo.getUid());
                params.put("tokenTime", ((ChanPinXQActivity) context).tokenTime);
                break;
        }
        params.put("id", id);
        params.put("type", type);
        return new OkObject(params, url);
    }

    /**
     * des： 分享二维码
     * author： ZhangJieBo
     * date： 2017/9/29 0029 下午 2:57
     */
    public static void erWeiMa(final Context context, final IWXAPI api, String id, String type, final String activity) {
        switch (activity) {
            case "MainActivity":
                ((MainActivity) context).showLoadingDialog();
                break;
            case "ChanPinXQActivity":
                ((ChanPinXQActivity) context).showLoadingDialog();
                break;
        }
        ApiClient.post(context, getOkObjectShareEWM(context, activity, id, type), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                switch (activity) {
                    case "MainActivity":
                        ((MainActivity) context).cancelLoadingDialog();
                        break;
                    case "ChanPinXQActivity":
                        ((ChanPinXQActivity) context).cancelLoadingDialog();
                        break;
                }
                LogUtil.LogShitou("MyDialog--图文二维码", s + "");
                try {
                    GoodsEwm goodsEwm = GsonUtils.parseJSON(s, GoodsEwm.class);
                    if (goodsEwm.getStatus() == 1) {
                        View dialog_fen_xiang_erm = LayoutInflater.from(context).inflate(R.layout.dialog_fen_xiang_erm, null);
                        final View viewJieTu = dialog_fen_xiang_erm.findViewById(R.id.viewJieTu);
                        ImageView imageImg = (ImageView) dialog_fen_xiang_erm.findViewById(R.id.imageImg);
                        ImageView imageEwmImg = (ImageView) dialog_fen_xiang_erm.findViewById(R.id.imageEwmImg);
                        TextView text1 = (TextView) dialog_fen_xiang_erm.findViewById(R.id.text1);
                        TextView text2 = (TextView) dialog_fen_xiang_erm.findViewById(R.id.text2);
                        TextView text3 = (TextView) dialog_fen_xiang_erm.findViewById(R.id.text3);
                        Glide.with(context)
                                .load(goodsEwm.getImg())
                                .placeholder(R.mipmap.ic_empty)
                                .into(imageImg);
                        Glide.with(context)
                                .load(goodsEwm.getEwmImg())
                                .placeholder(R.mipmap.ic_empty01)
                                .into(imageEwmImg);
                        text1.setText(goodsEwm.getText1());
                        text2.setText(goodsEwm.getText2());
                        text3.setText(goodsEwm.getText3());
                        final AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.dialog)
                                .setView(dialog_fen_xiang_erm)
                                .create();
                        dialog_fen_xiang_erm.findViewById(R.id.textBaoCun).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                viewJieTu.setDrawingCacheEnabled(true);
                                viewJieTu.buildDrawingCache();  //启用DrawingCache并创建位图
                                final Bitmap bitmap = Bitmap.createBitmap(viewJieTu.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
                                View dialog_img_show = LayoutInflater.from(context).inflate(R.layout.dialog_img_show, null);
                                ImageView imageView = (ImageView) dialog_img_show.findViewById(R.id.imageView);
                                imageView.setImageBitmap(bitmap);
                                Toast toast = new Toast(context);
                                toast.setView(dialog_img_show);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            FileUtil.saveMyBitmap(context, "分享二维码" + System.currentTimeMillis(), bitmap);
                                            switch (activity) {
                                                case "MainActivity":
                                                    ((MainActivity) context).runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(context, "图片保存在\"/sdcard/精灵之泉/\"目录下", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                    break;
                                                case "ChanPinXQActivity":
                                                    ((ChanPinXQActivity) context).runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(context, "图片保存在\"/sdcard/精灵之泉/\"目录下", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                    break;
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        });
                        dialog_fen_xiang_erm.findViewById(R.id.textWeiXin).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                shareImg(api,viewJieTu,0);
                            }


                        });
                        dialog_fen_xiang_erm.findViewById(R.id.textPengYouQuan).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                shareImg(api,viewJieTu,1);
                            }
                        });
                        alertDialog.show();
                        Window dialogWindow = alertDialog.getWindow();
                        dialogWindow.setGravity(Gravity.CENTER);
                        dialogWindow.setWindowAnimations(R.style.AnimFromTopToButtom);
                        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
                        lp.width = (int) (d.widthPixels - DpUtils.convertDpToPixel(20, context)); // 高度设置为屏幕的0.6
                        dialogWindow.setAttributes(lp);
                    } else if (goodsEwm.getStatus() == 3) {
                        MyDialog.showReLoginDialog(context);
                    } else {
                        Toast.makeText(context, goodsEwm.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                switch (activity) {
                    case "MainActivity":
                        ((MainActivity) context).cancelLoadingDialog();
                        break;
                    case "ChanPinXQActivity":
                        ((ChanPinXQActivity) context).cancelLoadingDialog();
                        break;
                }
                Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
            }

            private void shareImg(IWXAPI api,View viewJieTu,int flag) {
                api.registerApp(Constant.WXAPPID);
                viewJieTu.setDrawingCacheEnabled(true);
                viewJieTu.buildDrawingCache();  //启用DrawingCache并创建位图
                final Bitmap bitmap = Bitmap.createBitmap(viewJieTu.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
                //初始化
                WXImageObject imgObj = new WXImageObject(bitmap);
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = imgObj;
                //设置缩略图
                Bitmap.createScaledBitmap(bitmap,500,500,true);
                bitmap.recycle();
                msg.setThumbImage(bitmap);
                //构造一个req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("img");
                req.message =msg;
                switch (flag) {
                    case 0:
                        req.scene = SendMessageToWX.Req.WXSceneSession;
                        break;
                    case 1:
                        req.scene = SendMessageToWX.Req.WXSceneTimeline;
                        break;
                }
                api.sendReq(req);
            }
        });
    }

    /**
     * des： 分享
     * author： ZhangJieBo
     * date： 2017/9/25 0025 上午 11:54
     */
    public static void share(final Context context, final String activity, final IWXAPI api, final String id, final String type, final ShareBean share) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialog_shengji = inflater.inflate(R.layout.dianlog_index_share, null);
        TextView textDes1 = (TextView) dialog_shengji.findViewById(R.id.textDes1);
        TextView textDes2 = (TextView) dialog_shengji.findViewById(R.id.textDes2);
        textDes1.setText(share.getTitle());
        SpannableString span = new SpannableString(share.getDes1() + share.getDesMoney() + share.getDes2());
        span.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.basic_color)), share.getDes1().length(), share.getDes1().length() + share.getDesMoney().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textDes2.setText(span);
        final AlertDialog alertDialog1 = new AlertDialog.Builder(context, R.style.dialog)
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
                if (!checkIsSupportedWeachatPay(api)) {
                    Toast.makeText(context, "您暂未安装微信,请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
                    return;
                }
                wxShare(api, 0, share);
                alertDialog1.dismiss();
            }
        });
        dialog_shengji.findViewById(R.id.viewPengYouQuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkIsSupportedWeachatPay(api)) {
                    Toast.makeText(context, "您暂未安装微信,请下载安装最新版本的微信", Toast.LENGTH_SHORT).show();
                    return;
                }
                wxShare(api, 1, share);
                alertDialog1.dismiss();
            }
        });
        dialog_shengji.findViewById(R.id.viewErWeiMa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog.erWeiMa(context,api, id, type, activity);
                alertDialog1.dismiss();
            }
        });
        Window dialogWindow = alertDialog1.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogFenXiang);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
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

    private static void wxShare(final IWXAPI api, final int flag, final ShareBean share) {
        api.registerApp(Constant.WXAPPID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = share.getShareUrl();
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = share.getShareTitle();
        msg.description = share.getShareDes();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = netPicToBmp(share.getShareImg());
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

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 检查微信版本是否支付支付或是否安装可支付的微信版本
     */
    public static boolean checkIsSupportedWeachatPay(IWXAPI api) {
        boolean isPaySupported = api.getWXAppSupportAPI() >= com.tencent.mm.opensdk.constants.Build.PAY_SUPPORTED_SDK_INT;
        return isPaySupported;
    }

    public static Bitmap netPicToBmp(String src) {
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
