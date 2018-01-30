package com.sxbwstxpay.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.BangZhuZXActivity;
import com.sxbwstxpay.activity.FeiLvActivity;
import com.sxbwstxpay.activity.GongGaoActivity;
import com.sxbwstxpay.activity.GuanLiYHKActivity;
import com.sxbwstxpay.activity.ShangChengDDActivity;
import com.sxbwstxpay.activity.SheZhiActivity;
import com.sxbwstxpay.activity.TuiGuangActivity;
import com.sxbwstxpay.activity.TuiGuangYJActivity;
import com.sxbwstxpay.activity.WebActivity;
import com.sxbwstxpay.activity.WoDeDPActivity;
import com.sxbwstxpay.activity.WoDeSHActivity;
import com.sxbwstxpay.activity.WoDeSYActivity;
import com.sxbwstxpay.activity.WoDeZDActivity;
import com.sxbwstxpay.activity.WoDeZLActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.HeadZoomScrollView;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.UserIndex;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

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
    private View viewHuiYuan03;
    private View viewFeiHuiYuan01;
    private View viewFeiHuiYuan03;
    private TextView texttXName;
    private TextView textGradeName;
    private TextView textVipTime;
    private int grade;
    private View viewWoDeDianPu;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.VIP:
                    initData();
                    break;
                case Constant.BROADCASTCODE.MINE:
                    initData();
                    break;
                default:
                    break;
            }
        }
    };
    private String storeTips;
    private String dbb;
    private TextView textScore;
    private TextView textScore1;
    private View viewJiFen;
    private View viewJiFen01;
    private View cardViewHuiYuan;
    private View cardViewFeiHuiYuan;
    private TextView textHuiYuanBtn;
    private TextView textHuiYuanBtn01;
    private String btnText;
    private String btnUrl;

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
        viewHuiYuan03 = mInflate.findViewById(R.id.viewHuiYuan03);
        viewFeiHuiYuan01 = mInflate.findViewById(R.id.viewFeiHuiYuan01);
        viewFeiHuiYuan03 = mInflate.findViewById(R.id.viewFeiHuiYuan03);
        texttXName = (TextView) mInflate.findViewById(R.id.texttXName);
        textGradeName = (TextView) mInflate.findViewById(R.id.textGradeName);
        textVipTime = (TextView) mInflate.findViewById(R.id.textVipTime);
        viewWoDeDianPu = mInflate.findViewById(R.id.viewWoDeDianPu);
        viewJiFen = mInflate.findViewById(R.id.viewJiFen);
        viewJiFen01 = mInflate.findViewById(R.id.viewJiFen01);
        cardViewHuiYuan = mInflate.findViewById(R.id.cardViewHuiYuan);
        cardViewFeiHuiYuan = mInflate.findViewById(R.id.cardViewFeiHuiYuan);
        textScore = (TextView) cardViewHuiYuan.findViewById(R.id.textScore);
        textScore1 = (TextView) cardViewFeiHuiYuan.findViewById(R.id.textScore);
        textHuiYuanBtn = (TextView) mInflate.findViewById(R.id.textHuiYuanBtn);
        textHuiYuanBtn01 = (TextView) mInflate.findViewById(R.id.textHuiYuanBtn01);
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
        if (!TextUtils.isEmpty(userInfo.getHeadImg())) {
            GlideApp.with(getActivity())
                    .asBitmap()
                    .load(userInfo.getHeadImg())
                    .placeholder(R.mipmap.ic_empty)
                    .into(imageTouXiang);
        }
        textNickName.setText(userInfo.getNickName());
    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.viewGuanLiYHK).setOnClickListener(this);
        mInflate.findViewById(R.id.viewShiMingRZ).setOnClickListener(this);
        mInflate.findViewById(R.id.viewJuHeZF).setOnClickListener(this);
        mInflate.findViewById(R.id.viewZhiNengHK).setOnClickListener(this);
        mInflate.findViewById(R.id.viewWoDeZD).setOnClickListener(this);
        viewWoDeDianPu.setOnClickListener(this);
        mInflate.findViewById(R.id.viewBangZhuZX).setOnClickListener(this);
        mInflate.findViewById(R.id.viewWoDeZL).setOnClickListener(this);
        mInflate.findViewById(R.id.viewZhanNeiGG).setOnClickListener(this);
        mInflate.findViewById(R.id.viewFeiLv).setOnClickListener(this);
        mInflate.findViewById(R.id.viewName).setOnClickListener(this);
        mInflate.findViewById(R.id.viewLianXiKF).setOnClickListener(this);
        mInflate.findViewById(R.id.viewZiZhiZS).setOnClickListener(this);
        mInflate.findViewById(R.id.viewWoDeSY).setOnClickListener(this);
        mInflate.findViewById(R.id.relatBanLiXYK).setOnClickListener(this);
        mInflate.findViewById(R.id.viewShangChengDD).setOnClickListener(this);
        mInflate.findViewById(R.id.viewWoDeSH).setOnClickListener(this);
        viewJiFen.setOnClickListener(this);
        viewJiFen01.setOnClickListener(this);
        viewFeiHuiYuan01.setOnClickListener(this);
        zoomScrollView.setOnScrollListener(new MyScrollListener());
        imageSheZhi.setOnClickListener(this);
        imageTouXiang.setOnClickListener(this);
        textHuiYuanBtn.setOnClickListener(this);
        textHuiYuanBtn01.setOnClickListener(this);
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
                        GlideApp.with(getActivity())
                                .asBitmap()
                                .load(userIndex.getHeadImg())
                                .dontAnimate()
                                .placeholder(R.mipmap.ic_empty)
                                .into(imageTouXiang);
                        textNickName.setText(userIndex.getNickName());
                        grade = userIndex.getGrade();
                        storeTips = userIndex.getStoreTips();
                        if (grade == 0) {
                            viewHuiYuan03.setVisibility(View.GONE);
                            viewFeiHuiYuan01.setVisibility(View.VISIBLE);
                            viewFeiHuiYuan03.setVisibility(View.VISIBLE);
                        } else {
                            viewHuiYuan03.setVisibility(View.VISIBLE);
                            viewFeiHuiYuan01.setVisibility(View.GONE);
                            viewFeiHuiYuan03.setVisibility(View.GONE);
                        }
                        if (TextUtils.isEmpty(userIndex.getTxName())) {
                            texttXName.setVisibility(View.GONE);
                        } else {
                            texttXName.setText(userIndex.getTxName());
                            texttXName.setVisibility(View.VISIBLE);
                        }
                        if (userIndex.getIs_db() == 1) {
                            viewJiFen.setVisibility(View.VISIBLE);
                            viewJiFen01.setVisibility(View.VISIBLE);
                            ViewGroup.LayoutParams layoutParams1 = cardViewHuiYuan.getLayoutParams();
                            layoutParams1.height = (int) DpUtils.convertDpToPixel(210f, getActivity());
                            cardViewHuiYuan.setLayoutParams(layoutParams1);
                            ViewGroup.LayoutParams layoutParams = cardViewFeiHuiYuan.getLayoutParams();
                            layoutParams.height = (int) DpUtils.convertDpToPixel(260f, getActivity());
                            cardViewFeiHuiYuan.setLayoutParams(layoutParams);
                        } else {
                            viewJiFen.setVisibility(View.GONE);
                            viewJiFen01.setVisibility(View.GONE);
                            ViewGroup.LayoutParams layoutParams1 = cardViewHuiYuan.getLayoutParams();
                            layoutParams1.height = (int) DpUtils.convertDpToPixel(150f, getActivity());
                            cardViewHuiYuan.setLayoutParams(layoutParams1);
                            ViewGroup.LayoutParams layoutParams = cardViewFeiHuiYuan.getLayoutParams();
                            layoutParams.height = (int) DpUtils.convertDpToPixel(200f, getActivity());
                            cardViewFeiHuiYuan.setLayoutParams(layoutParams);
                        }
                        if (userIndex.getIs_btn() == 1 && grade != 0) {
                            textHuiYuanBtn.setVisibility(View.VISIBLE);
                            textHuiYuanBtn01.setVisibility(View.VISIBLE);
                            btnText = userIndex.getBtnText();
                            btnUrl = userIndex.getBtnUrl();
                            textHuiYuanBtn.setText(btnText);
                        } else {
                            textHuiYuanBtn.setVisibility(View.GONE);
                            textHuiYuanBtn01.setVisibility(View.GONE);
                        }
                        textGradeName.setText(userIndex.getGradeName());
                        textVipTime.setText(userIndex.getVipTime());
                        dbb = userIndex.getDbb();
                        textScore.setText(dbb);
                        textScore1.setText(dbb);
                    } else if (userIndex.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        Toast.makeText(getActivity(), userIndex.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    try {
                        Toast.makeText(getActivity(), R.string.dataErrer, Toast.LENGTH_SHORT).show();
                    } catch (Exception e1) {
                    }
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
            case R.id.textHuiYuanBtn01:
                quYuYongJin(intent);
                break;
            case R.id.textHuiYuanBtn:
                quYuYongJin(intent);
                break;
            case R.id.viewJiFen:
                intent.setClass(getActivity(), TuiGuangYJActivity.class);
                intent.putExtra(Constant.INTENT_KEY.id, 4);
                intent.putExtra(Constant.INTENT_KEY.value, dbb);
                startActivity(intent);
                break;
            case R.id.viewJiFen01:
                intent.setClass(getActivity(), TuiGuangYJActivity.class);
                intent.putExtra(Constant.INTENT_KEY.id, 4);
                intent.putExtra(Constant.INTENT_KEY.value, dbb);
                startActivity(intent);
                break;
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
                if (TextUtils.isEmpty(storeTips)) {
                    intent.setClass(getActivity(), WoDeDPActivity.class);
                    startActivity(intent);
                } else {
                    MyDialog.showTipDialog(getActivity(), storeTips);
                }
                break;
            case R.id.viewShiMingRZ:

                break;
            case R.id.viewJuHeZF:
                break;
            case R.id.viewZhiNengHK:

                break;
            case R.id.viewWoDeZD:
                zhangDan();
                break;
            case R.id.viewWoDeSY:
                shouYi();
                break;
            case R.id.viewShangChengDD:
                dingDan();
                break;
            case R.id.viewWoDeSH:
                woDeSH();
                break;
            default:
                break;
        }
    }

    private void quYuYongJin(Intent intent) {
        intent.setClass(getActivity(), WebActivity.class);
        intent.putExtra(Constant.INTENT_KEY.TITLE, btnText);
        intent.putExtra(Constant.INTENT_KEY.URL, btnUrl);
        startActivity(intent);
    }

    private void shouYi() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), WoDeSYActivity.class);
        intent.putExtra(Constant.INTENT_KEY.value, dbb);
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
        intent.setClass(getActivity(), ShangChengDDActivity.class);
        startActivity(intent);
    }

    class MyScrollListener implements HeadZoomScrollView.OnScrollListener {

        @Override
        public void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            float heightYingHua = viewZoom.getMeasuredHeight() / 2 - DpUtils.convertDpToPixel(45f, getActivity()) - ScreenUtils.getStatusBarHeight(getActivity());
            float heightYingHua1 = viewZoom.getMeasuredHeight() / 2 - 2 * DpUtils.convertDpToPixel(45f, getActivity()) - ScreenUtils.getStatusBarHeight(getActivity());
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

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.VIP);
        filter.addAction(Constant.BROADCASTCODE.MINE);
        getActivity().registerReceiver(reciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(reciver);
    }
}
