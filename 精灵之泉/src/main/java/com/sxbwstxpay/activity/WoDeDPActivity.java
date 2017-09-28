package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.StoreMystore;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;

import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class WoDeDPActivity extends ZjbBaseActivity implements View.OnClickListener {

    private TextView textNum01;
    private TextView textNum02;
    private TextView textNum03;
    private TextView textNum04;
    private TextView textStoreNmae;
    private ImageView imageStoreLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_dp);
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
        textNum01 = (TextView) findViewById(R.id.textNum01);
        textNum02 = (TextView) findViewById(R.id.textNum02);
        textNum03 = (TextView) findViewById(R.id.textNum03);
        textNum04 = (TextView) findViewById(R.id.textNum04);
        textStoreNmae = (TextView) findViewById(R.id.textStoreNmae);
        imageStoreLogo = (ImageView) findViewById(R.id.imageStoreLogo);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("我的店铺");
    }

    @Override
    protected void setListeners() {
findViewById(R.id.buttonGuanLi).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.STORE_MYSTORE;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(WoDeDPActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("WoDeDPActivity--我的店铺", s + "");
                try {
                    StoreMystore storeMystore = GsonUtils.parseJSON(s, StoreMystore.class);
                    if (storeMystore.getStatus() == 1) {
                        List<String> storeMystoreNum = storeMystore.getNum();
                        textNum01.setText(storeMystoreNum.get(0));
                        textNum02.setText(storeMystoreNum.get(1));
                        textNum03.setText(storeMystoreNum.get(2));
                        textNum04.setText(storeMystoreNum.get(3));
                        textStoreNmae.setText(storeMystore.getStoreNmae());
                        Glide.with(WoDeDPActivity.this)
                                .load(storeMystore.getStoreLogo())
                                .dontAnimate()
                                .placeholder(R.mipmap.ic_empty)
                                .into(imageStoreLogo);
                    } else if (storeMystore.getStatus() == 3) {
                        MyDialog.showReLoginDialog(WoDeDPActivity.this);
                    } else {
                        Toast.makeText(WoDeDPActivity.this, storeMystore.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(WoDeDPActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(WoDeDPActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonGuanLi:
                Intent intent = new Intent();
                intent.setClass(this,GuanLiWDDPActivity.class);
                startActivity(intent);
                break;
        }
    }
}
