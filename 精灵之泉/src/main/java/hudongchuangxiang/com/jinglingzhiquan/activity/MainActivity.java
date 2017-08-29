package hudongchuangxiang.com.jinglingzhiquan.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.fragment.ShengQianFragment;


public class MainActivity extends AppCompatActivity {
    private String[] tabsItem = new String[5];
    private Class[] fragment = new Class[]{
            ShengQianFragment.class,
            ShengQianFragment.class,
            ShengQianFragment.class,
            ShengQianFragment.class,
            ShengQianFragment.class
    };
    private int[] imgRes = new int[]{
            R.drawable.selector_shengqian_item,
            R.drawable.selector_zhuanqian_item,
            R.drawable.selector_shoukuan_item,
            R.drawable.selector_renzheng_item,
            R.drawable.selector_mine_item
    };
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
    }
}
