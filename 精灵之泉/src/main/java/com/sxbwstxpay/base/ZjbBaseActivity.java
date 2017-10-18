package com.sxbwstxpay.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.LockActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.UserInfo;
import com.sxbwstxpay.util.ACache;
import com.umeng.analytics.MobclickAgent;

import me.imid.swipebacklayout.lib.SwipeBackLayout;


public abstract class ZjbBaseActivity extends SwipeBackActivity {

    private SwipeBackLayout mSwipeBackLayout;
    public int changeControl = 2016;
    private AlertDialog mAlertDialog;
    public UserInfo userInfo;
    public boolean isLogin = false;
    private AlertDialog reLoginDialog;
    public String tokenTime;
    public boolean isBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    public void init() {
        changeControl = Constant.changeControl - 1;
        ACache aCache = ACache.get(this, Constant.ACACHE.App);
        userInfo = (UserInfo) aCache.getAsObject(Constant.ACACHE.USER_INFO);
        tokenTime = aCache.getAsString(Constant.ACACHE.TOKENTIME);
        initSP();
        initIntent();
        findID();
        initViews();
        setListeners();
    }

    @Override
    public void onStart() {
        if (isBackground) {
            //app 从后台唤醒，进入前台
            isBackground = false;
            Log.e("ACTIVITY", "程序从后台唤醒");
            Intent intent = new Intent();
            intent.setClass(this, LockActivity.class);
            startActivity(intent);
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        isBackground = true;//记录当前已经进入后台
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract void initSP();

    protected abstract void initIntent();

    protected abstract void findID();

    protected abstract void initViews();

    protected abstract void setListeners();

    protected abstract void initData();

    @Override
    protected void onResume() {
        super.onResume();
        initLogin();
        if (changeControl != Constant.changeControl) {
            initData();
            changeControl++;
        }
        MobclickAgent.onResume(this);
    }

    private void initLogin() {
        if (userInfo != null) {
            isLogin = true;
        } else {
            isLogin = false;
        }
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
