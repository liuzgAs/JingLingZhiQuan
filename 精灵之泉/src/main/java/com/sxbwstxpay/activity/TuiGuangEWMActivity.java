package com.sxbwstxpay.activity;

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
import com.sxbwstxpay.model.ShareIndex;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

import java.util.HashMap;

import okhttp3.Response;

public class TuiGuangEWMActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private ImageView imageImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_guang_ewm);
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
        viewBar = findViewById(R.id.viewBar);
        imageImg = (ImageView) findViewById(R.id.imageImg);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("推广二维码");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.imageShare).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.SHARE_INDEX;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid",userInfo.getUid());
        params.put("tokenTime",tokenTime);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(TuiGuangEWMActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("TuiGuangEWMActivity--我的推广二维码",s+ "");
                try {
                    ShareIndex shareIndex = GsonUtils.parseJSON(s, ShareIndex.class);
                    if (shareIndex.getStatus()==1){
                        Glide.with(TuiGuangEWMActivity.this)
                                .load(shareIndex.getQrcode())
                                .placeholder(R.mipmap.ic_empty)
                                .into(imageImg);
                    }else if (shareIndex.getStatus()==2){
                        MyDialog.showReLoginDialog(TuiGuangEWMActivity.this);
                    }else {
                        Toast.makeText(TuiGuangEWMActivity.this, shareIndex.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(TuiGuangEWMActivity.this,"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(TuiGuangEWMActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageShare:

                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
