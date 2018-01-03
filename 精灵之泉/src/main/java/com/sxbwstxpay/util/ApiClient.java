package com.sxbwstxpay.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.sxbwstxpay.model.OkObject;

import java.io.File;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/27.
 */
public class ApiClient {

    public interface CallBack {
        void onSuccess(String s);

        void onError(Response response);
    }

    public static void post(Context context, OkObject okObject, final CallBack callBack) {
        HashMap<String, String> params = okObject.getParams();
        params.put("platform", "android");
        params.put("version", VersionUtils.getCurrVersion(context) + "");
        String formatUrlMap = ASCIIUtil.formatUrlMap(params);
        String md5 = AppUtil.getMD5(formatUrlMap);
        params.put("sign", md5);
        okObject.setParams(params);
        LogUtil.LogShitou("ApiClient--发送", "" + okObject.getJson());
        OkGo.post(okObject.getUrl())//
                .tag(context)//
                .upJson(okObject.getJson())//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        callBack.onSuccess(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onError(response);
                    }
                });
    }

    public static void postJson(Context context, String url, String json, final CallBack callBack) {
        LogUtil.LogShitou("ApiClient--发送", "" + json);
        OkGo.post(url)//
                .tag(context)//
                .upJson(json)//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        callBack.onSuccess(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onError(response);
                    }
                });
    }

    /**
     * 下载文件
     *
     * @param context
     * @param url
     */
    public static void downLoadFile(final Context context, String url, String dir,String fileName,final CallBack callBack) throws Exception {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/"+dir;
            OkGo.get(url)
                    .tag(context)
                    .execute(new FileCallback(filePath, fileName) {
                        @Override
                        public void onSuccess(File file, Call call, Response response) {
                            callBack.onSuccess(file.toString());
                            // 最后通知图库更新
                            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                    Uri.fromFile(file)));
                        }
                    });
        } else {
            Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
        }

    }
}
