package com.sxbwstxpay.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.OrderVipbefore;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

import java.util.HashMap;

import okhttp3.Response;


public class TuiGuangActivity extends ZjbBaseActivity implements View.OnClickListener {

    private ImageView imageImg;
    private View viewBar;
    private TextView textText1;
    private TextView textText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_guang);
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
        imageImg = (ImageView) findViewById(R.id.imageImg);
        viewBar = findViewById(R.id.viewBar);
        textText1 = (TextView) findViewById(R.id.textText1);
        textText2 = (TextView) findViewById(R.id.textText2);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("成为VIP精灵推广商");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        textText2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
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
        String url = Constant.HOST + Constant.Url.ORDER_VIPBEFORE;
        HashMap<String, String> params = new HashMap<>();
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(TuiGuangActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("TuiGuangActivity--推广商", s + "");
                try {
                    OrderVipbefore orderVipbefore = GsonUtils.parseJSON(s, OrderVipbefore.class);
                    if (orderVipbefore.getStatus() == 1) {
                        Glide.with(TuiGuangActivity.this)
                                .load(orderVipbefore.getImg())
                                .placeholder(R.mipmap.ic_empty)
                                .into(imageImg);
                        textText1.setText(orderVipbefore.getText1());
                        textText2.setText(orderVipbefore.getText2());
                    } else if (orderVipbefore.getStatus() == 2) {
                        MyDialog.showReLoginDialog(TuiGuangActivity.this);
                    } else {
                        Toast.makeText(TuiGuangActivity.this, orderVipbefore.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(TuiGuangActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(TuiGuangActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
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
}
