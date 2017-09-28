package com.sxbwstxpay.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseNotLeftActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.fragment.DingDanFragment;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.UserOrder;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class WoDeDDActivity extends ZjbBaseNotLeftActivity implements View.OnClickListener{
    private TabLayout tablayout;
    private ViewPager viewPager;
    private View viewBar;
    private List<UserOrder.TypeBean> userOrderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shang_cheng_dd);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
    }

    @Override
    protected void findID() {
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewBar = findViewById(R.id.viewBar);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("我的订单");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_ORDER;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid",userInfo.getUid());
        params.put("tokenTime",tokenTime);
        params.put("p","1");
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(WoDeDDActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("WoDeDDActivity--我的订单", s+"");
                try {
                    UserOrder userOrder = GsonUtils.parseJSON(s, UserOrder.class);
                    if (userOrder.getStatus()==1){
                        userOrderType = userOrder.getType();
                        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
                        tablayout.setupWithViewPager(viewPager);
                        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                        for (int i = 0; i < userOrderType.size(); i++) {
                            tablayout.getTabAt(i).setText(userOrderType.get(i).getN());
                        }
                    }else if (userOrder.getStatus()==3){
                        MyDialog.showReLoginDialog(WoDeDDActivity.this);
                    }else {
                        Toast.makeText(WoDeDDActivity.this, userOrder.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(WoDeDDActivity.this,"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(WoDeDDActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
        }
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new DingDanFragment(userOrderType.get(position).getV());
        }

        @Override
        public int getCount() {
            return userOrderType.size();
        }
    }
}
