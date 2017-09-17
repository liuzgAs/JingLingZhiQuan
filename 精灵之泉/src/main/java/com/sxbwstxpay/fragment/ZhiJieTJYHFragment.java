package com.sxbwstxpay.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ShangHuLBActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.UserMyteam;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;

import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhiJieTJYHFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;
    private TextView text01;
    private TextView text02;
    private TextView text03;
    private TextView text04;

    public ZhiJieTJYHFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_zhi_jie_tjyh, container, false);
            init();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mInflate.getParent();
        if (parent != null) {
            parent.removeView(mInflate);
        }
        return mInflate;
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void findID() {
        text01 = (TextView) mInflate.findViewById(R.id.text01);
        text02 = (TextView) mInflate.findViewById(R.id.text02);
        text03 = (TextView) mInflate.findViewById(R.id.text03);
        text04 = (TextView) mInflate.findViewById(R.id.text04);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.viewWoDeSH01).setOnClickListener(this);
        mInflate.findViewById(R.id.viewWoDeSH02).setOnClickListener(this);
        mInflate.findViewById(R.id.viewWoDeSH03).setOnClickListener(this);
        mInflate.findViewById(R.id.viewWoDeSH04).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_MYTEAM;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("WoDeSHActivity--我的商户", s + "");
                try {
                    UserMyteam userMyteam = GsonUtils.parseJSON(s, UserMyteam.class);
                    if (userMyteam.getStatus() == 1) {
                        List<Integer> data1 = userMyteam.getData1();
                        text01.setText("人数：" + data1.get(0));
                        text02.setText("人数：" + data1.get(1));
                        text03.setText("人数：" + data1.get(2));
                        text04.setText("人数：" + data1.get(3));
                    } else if (userMyteam.getStatus() == 2) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        Toast.makeText(getActivity(), userMyteam.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.viewWoDeSH01:
                intent.setClass(getActivity(), ShangHuLBActivity.class);
                intent.putExtra(Constant.INTENT_KEY.type, 10);
                startActivity(intent);
                break;
            case R.id.viewWoDeSH02:
                intent.setClass(getActivity(), ShangHuLBActivity.class);
                intent.putExtra(Constant.INTENT_KEY.type, 11);
                startActivity(intent);
                break;
            case R.id.viewWoDeSH03:
                intent.setClass(getActivity(), ShangHuLBActivity.class);
                intent.putExtra(Constant.INTENT_KEY.type, 12);
                startActivity(intent);
                break;
            case R.id.viewWoDeSH04:
                intent.setClass(getActivity(), ShangHuLBActivity.class);
                intent.putExtra(Constant.INTENT_KEY.type, 13);
                startActivity(intent);
                break;
        }
    }
}
