package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxbwstxpay.R;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.fragment.RenZhengFragment;
import com.sxbwstxpay.fragment.ShengQianCZFragment;
import com.sxbwstxpay.fragment.ShouKuanFragment;
import com.sxbwstxpay.fragment.WoDeFragment;
import com.sxbwstxpay.fragment.ZhuanQianFragment;
import com.sxbwstxpay.model.ExtraMap;
import com.sxbwstxpay.model.UserInfo;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.BackHandlerHelper;
import com.sxbwstxpay.util.UpgradeUtils;


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
            if (TextUtils.equals(action, Constant.BROADCASTCODE.EXTRAMAP)) {
                ExtraMap extraMap = (ExtraMap) intent.getSerializableExtra(Constant.INTENT_KEY.EXTRAMAP);
                try {
                    action(extraMap);
                } catch (Exception e) {
                }
            }
        }
    };
    public UserInfo userInfo;

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
                        intent1.putExtra(Constant.INTENT_KEY.URL, extramap.getUrl()+"&uid="+extramap.getUid());
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
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.MainActivityAlive = 0;
        unregisterReceiver(receiver);
    }
}
