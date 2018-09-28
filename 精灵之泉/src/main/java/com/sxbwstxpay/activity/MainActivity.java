package com.sxbwstxpay.activity;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.application.MyApplication;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.TabFragmentHost;
import com.sxbwstxpay.fragment.GouWuCheFragment;
import com.sxbwstxpay.fragment.ShouYeFragment;
import com.sxbwstxpay.fragment.WoDeXFragment;
import com.sxbwstxpay.fragment.YouDianXFragment;
import com.sxbwstxpay.fragment.ZhuanQianFragment;
import com.sxbwstxpay.interfacepage.OnPatchLister;
import com.sxbwstxpay.model.ExtraMap;
import com.sxbwstxpay.model.ShareBean;
import com.sxbwstxpay.model.UserInfo;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.BackHandlerHelper;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.UpgradeUtils;
import com.taobao.sophix.SophixManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;


public class MainActivity extends AppCompatActivity {
    private String[] tabsItem = new String[5];
    private Class[] fragment = new Class[]{
            ShouYeFragment.class,
            YouDianXFragment.class,
            ZhuanQianFragment.class,
            GouWuCheFragment.class,
            WoDeXFragment.class
    };
    private int[] imgRes = new int[]{
            R.drawable.selector_shengqian_item,
            R.drawable.selector_youdian_item,
            R.drawable.selector_zhuanqian_item,
            R.drawable.selector_renzheng_item,
            R.drawable.selector_mine_item
    };
    public TabFragmentHost mTabHost;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.WX_SHARE:
                    if (isShow) {
                        MyDialog.showTipDialog(MainActivity.this, "分享成功");
                    }
                    break;
                case Constant.BROADCASTCODE.WX_SHARE_FAIL:
                    if (isShow) {
                        MyDialog.showTipDialog(MainActivity.this, "取消分享");
                    }
                    break;
                case Constant.BROADCASTCODE.STYLE:
                    mTabHost.setCurrentTab(0);
                    break;
                case Constant.BROADCASTCODE.EXTRAMAP:
                    ExtraMap extraMap = (ExtraMap) intent.getSerializableExtra(Constant.INTENT_KEY.EXTRAMAP);
                    try {
                        action(extraMap);
                    } catch (Exception e) {
                    }
                    break;
                default:
                    break;
            }
        }
    };
    public UserInfo userInfo;
    private IWXAPI api = WXAPIFactory.createWXAPI(MainActivity.this, Constant.WXAPPID, true);
    private Tencent mTencent;
    private AlertDialog mAlertDialog;
    public String tokenTime;
    private boolean isShow;
    public boolean isBackground;
    private String paintPassword;
    public boolean isChoosePic = false;
    public boolean isJiFen =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        SophixManager.getInstance().queryAndLoadNewPatch();
        MyApplication.getInstance().addActivity(this);
        Constant.MainActivityAlive = 1;
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mTencent = Tencent.createInstance(Constant.QQ_ID, this.getApplicationContext());
        //检查更新
        UpgradeUtils.checkUpgrade(MainActivity.this, Constant.HOST + Constant.Url.INDEX_VERSION);
        ACache aCache = ACache.get(this, Constant.ACACHE.App);
        userInfo = (UserInfo) aCache.getAsObject(Constant.ACACHE.USER_INFO);
        tokenTime = aCache.getAsString(Constant.ACACHE.TOKENTIME);
        paintPassword = aCache.getAsString(Constant.ACACHE.PAINT_PASSWORD);
        tabsItem[0] = "省钱";
        tabsItem[1] = "优店";
        tabsItem[2] = "收款";
        tabsItem[3] = "赚钱";
        tabsItem[4] = "我的";

        mTabHost = (TabFragmentHost) findViewById(R.id.tabHost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtab);
        for (int i = 0; i < tabsItem.length; i++) {
            View inflate = getLayoutInflater().inflate(R.layout.tabs_item, null);
            TextView tabs_text = (TextView) inflate.findViewById(R.id.tabs_text);
            ImageView tabs_img = (ImageView) inflate.findViewById(R.id.tabs_img);
            tabs_text.setText(tabsItem[i]);
            tabs_img.setImageResource(imgRes[i]);
            mTabHost.addTab(mTabHost.newTabSpec(tabsItem[i]).setIndicator(inflate), fragment[i], null);
        }
        /*mTabHost.setCurrentTab(1);*/
        MyApplication.setOnPatchLister(new OnPatchLister() {
            @Override
            public void patchSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("提示")
                                .setMessage("发现新的补丁，是否立即生效,需重启应用")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent mStartActivity = new Intent(MainActivity.this, HuanYingActivity.class);
                                        int mPendingIntentId = 123456;
                                        PendingIntent mPendingIntent = PendingIntent.getActivity(MainActivity.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                                        AlarmManager mgr = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
                                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                                        SophixManager.getInstance().killProcessSafely();
                                    }
                                })
                                .setNegativeButton("否", null)
                                .create()
                                .show();
                    }
                });
            }
        });
    }

    /**
     * 双击退出应用
     */
    private long currentTime = 0;

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            if (System.currentTimeMillis() - currentTime > 1000) {
                Toast toast = Toast.makeText(this, "双击退出应用", Toast.LENGTH_SHORT);
                toast.show();
                currentTime = System.currentTimeMillis();
            } else {
                LogUtil.LogShitou("MainActivity--onBackPressed", "退出app");
                MyApplication.getInstance().exit();
                android.os.Process.killProcess(android.os.Process.myPid());   //获取PID
                System.exit(0);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        ACache aCache = ACache.get(MainActivity.this, Constant.ACACHE.App);
        paintPassword = aCache.getAsString(Constant.ACACHE.PAINT_PASSWORD);
        ExtraMap extramap = (ExtraMap) intent.getSerializableExtra(Constant.INTENT_KEY.EXTRAMAP);
        try {
            action(extramap);
        } catch (Exception e) {
        }
        isJiFen = intent.getBooleanExtra(Constant.INTENT_KEY.isJiFen, false);
        if (isJiFen){
            mTabHost.setCurrentTab(0);
        }
    }

    private void action(ExtraMap extramap) {
        if (extramap != null) {
            Intent intent = new Intent();
            switch (extramap.getCode()) {
                case "app_notice":
                    intent.setClass(this, WoDeZDActivity.class);
                    startActivity(intent);
                    break;
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
                case "storeIndex":
                    intent.putExtra(Constant.INTENT_KEY.id, extramap.getItemId());
                    intent.putExtra(Constant.INTENT_KEY.type, 1);
                    intent.setClass(this, GuanLiWDDPActivity.class);
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
                default:
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShow = true;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.EXTRAMAP);
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE);
        filter.addAction(Constant.BROADCASTCODE.WX_SHARE_FAIL);
        filter.addAction(Constant.BROADCASTCODE.STYLE);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStart() {
        if (isBackground) {
            //app 从后台唤醒，进入前台
            isBackground = false;
            Log.e("ACTIVITY", "程序从后台唤醒");
            boolean activityTop = isActivityTop(MainActivity.class, this);
            if (!TextUtils.isEmpty(paintPassword) && activityTop && !isChoosePic) {
                Intent intent = new Intent();
                intent.setClass(this, LockActivity.class);
                startActivity(intent);
            }
        }
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShow = false;
        if (!isChoosePic) {
            /**
             * 记录当前已经进入后台
             */
            isBackground = true;//
        }
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

    public void share(String id, String type, ShareBean share) {
        MyDialog.share(MainActivity.this, "MainActivity", api, id, type, share);
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


    /**
     * 判断某activity是否处于栈顶
     *
     * @return true在栈顶 false不在栈顶
     */
    private boolean isActivityTop(Class cls, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(cls.getName());
    }
}
