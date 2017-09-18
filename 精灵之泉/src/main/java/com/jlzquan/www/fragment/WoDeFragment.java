package com.jlzquan.www.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jlzquan.www.R;
import com.jlzquan.www.activity.BangZhuZXActivity;
import com.jlzquan.www.activity.FeiLvActivity;
import com.jlzquan.www.activity.GongGaoActivity;
import com.jlzquan.www.activity.GuanLiYHKActivity;
import com.jlzquan.www.activity.SheZhiActivity;
import com.jlzquan.www.activity.TuiGuangActivity;
import com.jlzquan.www.activity.WebActivity;
import com.jlzquan.www.activity.WoDeDDActivity;
import com.jlzquan.www.activity.WoDeSHActivity;
import com.jlzquan.www.activity.WoDeSYActivity;
import com.jlzquan.www.activity.WoDeZDActivity;
import com.jlzquan.www.activity.WoDeZLActivity;
import com.jlzquan.www.base.MyDialog;
import com.jlzquan.www.base.ZjbBaseFragment;
import com.jlzquan.www.constant.Constant;
import com.jlzquan.www.customview.HeadZoomScrollView;
import com.jlzquan.www.model.OkObject;
import com.jlzquan.www.model.UserIndex;
import com.jlzquan.www.util.ApiClient;
import com.jlzquan.www.util.DpUtils;
import com.jlzquan.www.util.GsonUtils;
import com.jlzquan.www.util.LogUtil;
import com.jlzquan.www.util.ScreenUtils;

import java.util.HashMap;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WoDeFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;
    private View mRelaTitleStatue01;
    private View relaTitleStatue;
    private View viewZoom;
    private HeadZoomScrollView zoomScrollView;
    private View viewTips;
    private ImageView imageSheZhi;
    private ImageView imageTouXiang;
    private TextView textNickName;
    private View viewHuiYuan01;
    private View viewHuiYuan02;
    private View viewHuiYuan03;
    private View viewFeiHuiYuan01;
    private View viewFeiHuiYuan02;
    private View viewFeiHuiYuan03;
    private TextView texttXName;
    private TextView textGradeName;
    private TextView textVipTime;

    public WoDeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_wo_de, container, false);
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
        zoomScrollView = (HeadZoomScrollView) mInflate.findViewById(R.id.zoomScrollView);
        mRelaTitleStatue01 = mInflate.findViewById(R.id.relaTitleStatue01);
        relaTitleStatue = mInflate.findViewById(R.id.relaTitleStatue);
        viewZoom = mInflate.findViewById(R.id.viewZoom);
        viewTips = mInflate.findViewById(R.id.viewTips);
        imageSheZhi = (ImageView) mInflate.findViewById(R.id.imageSheZhi);
        imageTouXiang = (ImageView) mInflate.findViewById(R.id.imageTouXiang);
        textNickName = (TextView) mInflate.findViewById(R.id.textNickName);
        viewHuiYuan01 = mInflate.findViewById(R.id.viewHuiYuan01);
        viewHuiYuan02 = mInflate.findViewById(R.id.viewHuiYuan02);
        viewHuiYuan03 = mInflate.findViewById(R.id.viewHuiYuan03);
        viewFeiHuiYuan01 = mInflate.findViewById(R.id.viewFeiHuiYuan01);
        viewFeiHuiYuan02 = mInflate.findViewById(R.id.viewFeiHuiYuan02);
        viewFeiHuiYuan03 = mInflate.findViewById(R.id.viewFeiHuiYuan03);
        texttXName = (TextView) mInflate.findViewById(R.id.texttXName);
        textGradeName = (TextView) mInflate.findViewById(R.id.textGradeName);
        textVipTime = (TextView) mInflate.findViewById(R.id.textVipTime);
    }

    @Override
    protected void initViews() {
        relaTitleStatue.getBackground().mutate().setAlpha(0);
        ViewGroup.LayoutParams layoutParams = relaTitleStatue.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
        relaTitleStatue.setLayoutParams(layoutParams);
        mRelaTitleStatue01.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);
        relaTitleStatue.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);
        zoomScrollView.setZoomView(viewZoom);
        texttXName.setVisibility(View.GONE);
        Glide.with(getActivity())
                .load(userInfo.getHeadImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageTouXiang);
        textNickName.setText(userInfo.getNickName());
    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.viewHuiYuan).setOnClickListener(this);
        mInflate.findViewById(R.id.viewGuanLiYHK).setOnClickListener(this);
        mInflate.findViewById(R.id.viewZhangDan).setOnClickListener(this);
        mInflate.findViewById(R.id.viewShangHu).setOnClickListener(this);
        mInflate.findViewById(R.id.viewShouYi).setOnClickListener(this);
        mInflate.findViewById(R.id.viewDingDan).setOnClickListener(this);
        mInflate.findViewById(R.id.viewZhangDan1).setOnClickListener(this);
        mInflate.findViewById(R.id.viewShangHu1).setOnClickListener(this);
        mInflate.findViewById(R.id.viewShouYi1).setOnClickListener(this);
        mInflate.findViewById(R.id.viewDingDan1).setOnClickListener(this);
        mInflate.findViewById(R.id.viewZhangDan2).setOnClickListener(this);
        mInflate.findViewById(R.id.viewShangHu2).setOnClickListener(this);
        mInflate.findViewById(R.id.viewShouYi2).setOnClickListener(this);
        mInflate.findViewById(R.id.viewDingDan2).setOnClickListener(this);
        mInflate.findViewById(R.id.viewWoDeDianPu).setOnClickListener(this);
        mInflate.findViewById(R.id.viewBangZhuZX).setOnClickListener(this);
        mInflate.findViewById(R.id.viewWoDeZL).setOnClickListener(this);
        mInflate.findViewById(R.id.viewZhanNeiGG).setOnClickListener(this);
        mInflate.findViewById(R.id.viewFeiLv).setOnClickListener(this);
        mInflate.findViewById(R.id.viewName).setOnClickListener(this);
        mInflate.findViewById(R.id.viewLianXiKF).setOnClickListener(this);
        mInflate.findViewById(R.id.viewZiZhiZS).setOnClickListener(this);
        mInflate.findViewById(R.id.relatBanLiXYK).setOnClickListener(this);
        viewFeiHuiYuan01.setOnClickListener(this);
        zoomScrollView.setOnScrollListener(new MyScrollListener());
        imageSheZhi.setOnClickListener(this);
        imageTouXiang.setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_INDEX;
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
                LogUtil.LogShitou("WoDeFragment--我的", s + "");
                try {
                    UserIndex userIndex = GsonUtils.parseJSON(s, UserIndex.class);
                    if (userIndex.getStatus() == 1) {
                        Glide.with(getActivity())
                                .load(userIndex.getHeadImg())
                                .dontAnimate()
                                .placeholder(R.mipmap.ic_empty)
                                .into(imageTouXiang);
                        textNickName.setText(userIndex.getNickName());
                        if (userIndex.getGrade() == 0) {
                            viewHuiYuan01.setVisibility(View.GONE);
                            viewHuiYuan02.setVisibility(View.GONE);
                            viewHuiYuan03.setVisibility(View.GONE);
                            viewFeiHuiYuan01.setVisibility(View.VISIBLE);
                            viewFeiHuiYuan02.setVisibility(View.VISIBLE);
                            viewFeiHuiYuan03.setVisibility(View.VISIBLE);
                        } else {
                            viewHuiYuan01.setVisibility(View.VISIBLE);
                            viewHuiYuan02.setVisibility(View.VISIBLE);
                            viewHuiYuan03.setVisibility(View.VISIBLE);
                            viewFeiHuiYuan01.setVisibility(View.GONE);
                            viewFeiHuiYuan02.setVisibility(View.GONE);
                            viewFeiHuiYuan03.setVisibility(View.GONE);
                        }
                        if (TextUtils.isEmpty(userIndex.getTxName())) {
                            texttXName.setVisibility(View.GONE);
                        } else {
                            texttXName.setText(userIndex.getTxName());
                            texttXName.setVisibility(View.VISIBLE);
                        }
                        textGradeName.setText(userIndex.getGradeName());
                        textVipTime.setText(userIndex.getVipTime());
                    } else if (userIndex.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        Toast.makeText(getActivity(), userIndex.getInfo(), Toast.LENGTH_SHORT).show();
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
            case R.id.relatBanLiXYK:
                intent.setClass(getActivity(), WebActivity.class);
                intent.putExtra(Constant.INTENT_KEY.TITLE, "办理信用卡");
                intent.putExtra(Constant.INTENT_KEY.URL, Constant.HOST + Constant.Url.INFO_CREDIT);
                startActivity(intent);
                break;
            case R.id.viewLianXiKF:
                intent.setClass(getActivity(), WebActivity.class);
                intent.putExtra(Constant.INTENT_KEY.TITLE, "联系客服");
                intent.putExtra(Constant.INTENT_KEY.URL, Constant.HOST + Constant.Url.INFO_CONTACT);
                startActivity(intent);
                break;
            case R.id.viewZiZhiZS:
                intent.setClass(getActivity(), WebActivity.class);
                intent.putExtra(Constant.INTENT_KEY.TITLE, "资质证书");
                intent.putExtra(Constant.INTENT_KEY.URL, Constant.HOST + Constant.Url.INFO_CA);
                startActivity(intent);
                break;
            case R.id.viewFeiHuiYuan01:
                intent.setClass(getContext(), TuiGuangActivity.class);
                startActivity(intent);
                break;
            case R.id.viewGuanLiYHK:
                intent.setClass(getActivity(), GuanLiYHKActivity.class);
                startActivity(intent);
                break;
            case R.id.viewFeiLv:
                intent.setClass(getActivity(), FeiLvActivity.class);
                startActivity(intent);
                break;
            case R.id.viewWoDeZL:
                ziLiao();
                break;
            case R.id.viewName:
                ziLiao();
                break;
            case R.id.imageTouXiang:
                ziLiao();
                break;
            case R.id.viewZhanNeiGG:
                gongGao();
                break;
            case R.id.imageSheZhi:
                intent.setClass(getActivity(), SheZhiActivity.class);
                startActivity(intent);
                break;
            case R.id.viewBangZhuZX:
                intent.setClass(getActivity(), BangZhuZXActivity.class);
                startActivity(intent);
                break;
            case R.id.viewWoDeDianPu:
                break;
            case R.id.viewHuiYuan:
                break;
            case R.id.viewZhangDan:
                zhangDan();
                break;
            case R.id.viewShangHu:
                woDeSH();
                break;
            case R.id.viewShouYi:
                shouYi();
                break;
            case R.id.viewDingDan:
                dingDan();
                break;
            case R.id.viewZhangDan1:
                zhangDan();
                break;
            case R.id.viewShangHu1:
                woDeSH();
                break;
            case R.id.viewShouYi1:
                shouYi();
                break;
            case R.id.viewDingDan1:
                dingDan();
                break;
            case R.id.viewZhangDan2:
                zhangDan();
                break;
            case R.id.viewShangHu2:
                woDeSH();
                break;
            case R.id.viewShouYi2:
                shouYi();
                break;
            case R.id.viewDingDan2:
                dingDan();
                break;
        }
    }

    private void shouYi() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), WoDeSYActivity.class);
        startActivity(intent);
    }

    private void gongGao() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), GongGaoActivity.class);
        startActivity(intent);
    }

    private void zhangDan() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), WoDeZDActivity.class);
        startActivity(intent);
    }

    private void woDeSH() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), WoDeSHActivity.class);
        startActivity(intent);
    }

    private void ziLiao() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), WoDeZLActivity.class);
        startActivity(intent);
    }

    private void dingDan() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), WoDeDDActivity.class);
        startActivity(intent);
    }

    class MyScrollListener implements HeadZoomScrollView.OnScrollListener {

        @Override
        public void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            float heightYingHua = viewZoom.getMeasuredHeight() - DpUtils.convertDpToPixel(45f, getActivity()) - ScreenUtils.getStatusBarHeight(getActivity());
            float heightYingHua1 = viewZoom.getMeasuredHeight() - 2 * DpUtils.convertDpToPixel(45f, getActivity()) - ScreenUtils.getStatusBarHeight(getActivity());
            if (scrollY >= 0 && scrollY <= heightYingHua) {
                float baiFenBi = (float) scrollY / heightYingHua;
                relaTitleStatue.getBackground().mutate().setAlpha((int) (255 * baiFenBi));
            } else if (scrollY > heightYingHua) {
                relaTitleStatue.getBackground().mutate().setAlpha(255);
            }
            if (scrollY >= 0 && scrollY <= heightYingHua1) {
                float baiFenBi = (float) scrollY / heightYingHua1;
                if (1 - baiFenBi < 0.8) {
                    imageSheZhi.setEnabled(false);
                } else {
                    imageSheZhi.setEnabled(true);
                }
                viewTips.setAlpha(1 - baiFenBi);
                viewTips.setVisibility(View.VISIBLE);
            } else if (scrollY > heightYingHua1) {
                viewTips.setAlpha(0);
                viewTips.setVisibility(View.GONE);
            }
        }
    }
}
