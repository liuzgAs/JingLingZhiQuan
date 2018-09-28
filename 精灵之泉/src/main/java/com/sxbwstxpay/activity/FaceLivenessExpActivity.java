package com.sxbwstxpay.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.sxbwstxpay.R;
import com.sxbwstxpay.application.MyApplication;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.DefaultDialog;
import com.sxbwstxpay.model.RespondAppimgadd;
import com.sxbwstxpay.model.UserInfo;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.GsonUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class FaceLivenessExpActivity extends FaceLivenessActivity {

    private DefaultDialog mDefaultDialog;
    private ProgressDialog progressDialog;
    private AlertDialog mAlertDialog;
    private UserInfo userInfo;
    HashMap<String, String> ImageMap=new HashMap<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setFace();
    }
    private void setFace(){
        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        config.setLivenessRandom(true);
        FaceSDKManager.getInstance().setFaceConfig(config);
    }
    @Override
    public void onLivenessCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        super.onLivenessCompletion(status, message, base64ImageMap);
        if (status == FaceStatusEnum.OK && mIsCompletion) {
//            showMessageDialog("活体检测", "检测成功");
            ImageMap.clear();
            ImageMap.putAll(base64ImageMap);
            MyApplication.getInstance().images.clear();
            ACache aCache = ACache.get(this, Constant.ACACHE.App);
            userInfo = (UserInfo) aCache.getAsObject(Constant.ACACHE.USER_INFO);
            for (Map.Entry<String, String> entry : base64ImageMap.entrySet()) {
                upImg( entry.getValue());
            }
        } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
            showMessageDialog("活体检测", "采集超时");
        }
    }

    private void showMessageDialog(String title, String message) {
        if (mDefaultDialog == null) {
            DefaultDialog.Builder builder = new DefaultDialog.Builder(this);
            builder.setTitle(title).
                    setMessage(message).
                    setNegativeButton("确认",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mDefaultDialog.dismiss();
                                    finish();
                                }
                            });
            mDefaultDialog = builder.create();
            mDefaultDialog.setCancelable(true);
        }
        mDefaultDialog.dismiss();
        mDefaultDialog.show();
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void upImg(String img){
        showLoadingDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
//        params.put("tokenTime", tokenTime);
        params.put("code", "face");
        params.put("brand", "android");
        params.put("img", img);
        JSONObject jsonObject = new JSONObject(params);
        OkGo.post(Constant.HOST + Constant.Url.RESPOND_APPIMGADD)//
                .tag(this)//
                .upJson(jsonObject.toString())//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        cancelLoadingDialog();
                        try {
                            RespondAppimgadd respondAppimgadd = GsonUtils.parseJSON(s, RespondAppimgadd.class);
                            if (respondAppimgadd.getStatus() == 1) {
                                MyApplication.getInstance().images.add(respondAppimgadd.getImgId());
                                if (ImageMap.size()==MyApplication.getInstance().images.size()){
                                    finish();
                                }
                            } else if (respondAppimgadd.getStatus() == 1) {
                                MyDialog.showReLoginDialog(FaceLivenessExpActivity.this);
                            } else {
                                Toast.makeText(FaceLivenessExpActivity.this, respondAppimgadd.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(FaceLivenessExpActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        cancelLoadingDialog();
                        Toast.makeText(FaceLivenessExpActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void showLoadingDialog() {
        if (mAlertDialog == null) {
            View dialog_progress = getLayoutInflater().inflate(R.layout.view_progress01, null);
            mAlertDialog = new AlertDialog.Builder(this, R.style.dialog)
                    .setView(dialog_progress)
                    .setCancelable(false)
                    .create();
            mAlertDialog.show();
            mAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                        cancelLoadingDialog();
                        finish();
                    }
                    return false;
                }
            });
        } else {
            mAlertDialog.show();
        }
    }

    public void cancelLoadingDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }
}
