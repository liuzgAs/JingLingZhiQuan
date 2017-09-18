package com.jlzquan.www.util;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import com.jlzquan.www.model.OkObject;
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
}
