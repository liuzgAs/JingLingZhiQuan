package hudongchuangxiang.com.jinglingzhiquan.base;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.constant.Constant;
import hudongchuangxiang.com.jinglingzhiquan.model.UserInfoBean;
import hudongchuangxiang.com.jinglingzhiquan.util.ACache;


public abstract class ZjbBaseNotLeftActivity extends AppCompatActivity {

    public int changeControl = 2016;
    private AlertDialog mAlertDialog;
    public UserInfoBean mMyUserInfo;
    public boolean isLogin = false;
    public String tokenTime;

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
    }

    public void init() {
        ACache aCache = ACache.get(this, Constant.ACACHE.App);
        mMyUserInfo = (UserInfoBean) aCache.getAsObject(Constant.ACACHE.USER_INFO);
        tokenTime = aCache.getAsString(Constant.ACACHE.TOKENTIME);
        changeControl = Constant.changeControl-1;
        initSP();
        initIntent();
        findID();
        initViews();
        setListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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
        if (changeControl!= Constant.changeControl){
            initData();
            changeControl++;
        }
        MobclickAgent.onResume(this);
    }

    private void initLogin() {
        if (mMyUserInfo != null) {
            isLogin = true;
        } else {
            isLogin = false;
        }
    }

    public void showLoadingDialog() {
        if (mAlertDialog==null){
            View dialog_progress = getLayoutInflater().inflate(R.layout.view_progress, null);
            mAlertDialog = new AlertDialog.Builder(this,R.style.dialog)
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
        }else {
            mAlertDialog.show();
        }
    }

    public void cancelLoadingDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            try {
                mAlertDialog.dismiss();
                mAlertDialog = null;
            } catch (Exception e) {
            }
        }
    }

}
