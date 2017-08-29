package hudongchuangxiang.com.jinglingzhiquan.base;


import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.constant.Constant;
import hudongchuangxiang.com.jinglingzhiquan.model.UserInfoBean;
import hudongchuangxiang.com.jinglingzhiquan.util.ACache;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class ZjbBaseFragment extends Fragment {
    public boolean isLogin = false;
    public int changeControl = 2016;
    private AlertDialog mAlertDialog;
    public UserInfoBean mMyUserInfo;
    public String tokenTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止横屏
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void init() {
        //添加当前界面到容器中
        changeControl = Constant.changeControl - 1;
        ACache aCache = ACache.get(getActivity(), Constant.ACACHE.App);
        mMyUserInfo = (UserInfoBean) aCache.getAsObject(Constant.ACACHE.USER_INFO);
        tokenTime = aCache.getAsString(Constant.ACACHE.TOKENTIME);
        initSP();
        initIntent();
        findID();
        initViews();
        setListeners();
    }

    protected abstract void initIntent();

    protected abstract void initSP();

    protected abstract void findID();

    protected abstract void initViews();

    protected abstract void setListeners();

    protected abstract void initData();

    private void initLogin() {
        if (mMyUserInfo != null) {
            isLogin = true;
        } else {
            isLogin = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initLogin();
        if (changeControl != Constant.changeControl) {
            initData();
            changeControl++;
        }
    }

    public void showLoadingDialog() {
        if (mAlertDialog == null) {
            View dialog_progress = getLayoutInflater(getArguments()).inflate(R.layout.view_progress, null);
            mAlertDialog = new AlertDialog.Builder(getActivity(), R.style.dialog)
                    .setView(dialog_progress)
                    .setCancelable(false)
                    .create();
            mAlertDialog.show();
            mAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                        cancelLoadingDialog();
                        getActivity().finish();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
