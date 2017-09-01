package hudongchuangxiang.com.jinglingzhiquan.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.activity.BangZhuZXActivity;
import hudongchuangxiang.com.jinglingzhiquan.activity.GongGaoActivity;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseFragment;
import hudongchuangxiang.com.jinglingzhiquan.customview.HeadZoomScrollView;
import hudongchuangxiang.com.jinglingzhiquan.util.DpUtils;
import hudongchuangxiang.com.jinglingzhiquan.util.ScreenUtils;

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
    private ImageView imageGongGao;

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
        imageGongGao = (ImageView) mInflate.findViewById(R.id.imageGongGao);
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
    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.viewHuiYuan).setOnClickListener(this);
        mInflate.findViewById(R.id.viewZhangDan).setOnClickListener(this);
        mInflate.findViewById(R.id.viewShangHu).setOnClickListener(this);
        mInflate.findViewById(R.id.viewZiLiao).setOnClickListener(this);
        mInflate.findViewById(R.id.viewDingDan).setOnClickListener(this);
        mInflate.findViewById(R.id.viewZhangDan1).setOnClickListener(this);
        mInflate.findViewById(R.id.viewShangHu1).setOnClickListener(this);
        mInflate.findViewById(R.id.viewZiLiao1).setOnClickListener(this);
        mInflate.findViewById(R.id.viewDingDan1).setOnClickListener(this);
        mInflate.findViewById(R.id.viewWoDeDianPu).setOnClickListener(this);
        mInflate.findViewById(R.id.viewBangZhuZX).setOnClickListener(this);
        zoomScrollView.setOnScrollListener(new MyScrollListener());
        imageGongGao.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.imageGongGao:
                intent.setClass(getActivity(), GongGaoActivity.class);
                startActivity(intent);
                break;
            case R.id.viewBangZhuZX:
                intent.setClass(getActivity(), BangZhuZXActivity.class);
                startActivity(intent);
                break;
            case R.id.viewWoDeDianPu:
                break;
            case R.id.viewHuiYuan:
                Toast.makeText(getActivity(), "会员详情", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewZhangDan:
                Toast.makeText(getActivity(), "我的账单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewShangHu:
                Toast.makeText(getActivity(), "我的商户", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewZiLiao:
                Toast.makeText(getActivity(), "我的资料", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewDingDan:
                Toast.makeText(getActivity(), "商城订单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewZhangDan1:
                Toast.makeText(getActivity(), "我的账单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewShangHu1:
                Toast.makeText(getActivity(), "我的商户", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewZiLiao1:
                Toast.makeText(getActivity(), "我的资料", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewDingDan1:
                Toast.makeText(getActivity(), "商城订单", Toast.LENGTH_SHORT).show();
                break;
        }
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
                viewTips.setAlpha(1 - baiFenBi);
                viewTips.setVisibility(View.VISIBLE);
            } else if (scrollY > heightYingHua) {
                viewTips.setAlpha(0);
                viewTips.setVisibility(View.GONE);
            }
        }
    }
}
