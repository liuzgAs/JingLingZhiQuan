package com.sxbwstxpay.activity.imagepicker;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.R;
import com.lzy.imagepicker.view.SystemBarTintManager;
import com.sxbwstxpay.application.MyApplication;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.UserInfo;
import com.sxbwstxpay.util.ACache;
import com.umeng.analytics.MobclickAgent;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class ImageBaseActivity extends AppCompatActivity {

    protected SystemBarTintManager tintManager;
    public int changeControl = 2016;
    private AlertDialog mAlertDialog;
    public UserInfo userInfo;
    public boolean isLogin = false;
    private AlertDialog reLoginDialog;
    public String tokenTime;
    public boolean isBackground;
    public String paintPassword;
    private Class cls;
    public boolean isChoosePic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.ip_color_primary_dark);  //设置上方状态栏的颜色
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public boolean checkPermission(@NonNull String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void showToast(String toastText) {
        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ImagePicker.getInstance().restoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ImagePicker.getInstance().saveInstanceState(outState);
    }

    public void init(Class cls) {
        this.cls =cls;
        MyApplication.getInstance().addActivity(this);
        changeControl = Constant.changeControl - 1;
        ACache aCache = ACache.get(this, Constant.ACACHE.App);
        userInfo = (UserInfo) aCache.getAsObject(Constant.ACACHE.USER_INFO);
        tokenTime = aCache.getAsString(Constant.ACACHE.TOKENTIME);
        paintPassword = aCache.getAsString(Constant.ACACHE.PAINT_PASSWORD);
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
            boolean activityTop = isActivityTop(cls, this);
            if (!TextUtils.isEmpty(paintPassword)&&activityTop&&!isChoosePic){
//                Intent intent = new Intent();
//                intent.setClass(this, LockActivity.class);
//                startActivity(intent);
            }
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (!isChoosePic){
            /**
             * 记录当前已经进入后台
             */
            isBackground = true;
        }
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


    /**
     *
     * 判断某activity是否处于栈顶
     * @return  true在栈顶 false不在栈顶
     */
    private boolean isActivityTop(Class cls,Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(cls.getName());
    }
}
