package com.jlzquan.www.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jlzquan.www.R;
import com.jlzquan.www.activity.DengLuActivity;
import com.jlzquan.www.base.ZjbBaseFragment;
import com.jlzquan.www.constant.Constant;
import com.jlzquan.www.util.ACache;


/**
 * des： 引导页
 * author： zhangjiebo
 * date： 2017/7/6 0006 下午 1:54
 */
public class GuideFragment extends ZjbBaseFragment {

    private int guideImg_length;
    private int position;
    //    private ButtonRectangle mEnter;
    private Button mEnter;
    private View mInflate;
    private ImageView mGuide_img;
    private int img;

    public GuideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_guide, container, false);
            init();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mInflate.getParent();
        if (parent != null) {
            parent.removeView(mInflate);
        }
        return mInflate;
    }

    /**
     * des： 设置图片
     * author： ZhangJieBo
     * date： 2017/7/6 0006 下午 2:08
     */
    public void setImg(int img, int guideImg_length, int position) {
        this.img = img;
        this.guideImg_length = guideImg_length;
        this.position = position;
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void findID() {
        mGuide_img = (ImageView) mInflate.findViewById(R.id.guide_img);
//        mEnter = (ButtonRectangle) mInflate.findViewById(R.id.button_guide);
        mEnter = (Button) mInflate.findViewById(R.id.button_guide);
    }

    @Override
    protected void initViews() {
        if (position == guideImg_length) {
            mEnter.setVisibility(View.VISIBLE);
        } else {
            mEnter.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setListeners() {
        mEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACache aCache = ACache.get(getActivity(), Constant.ACACHE.FRIST);
                aCache.put(Constant.ACACHE.FRIST, "0");
                Intent intent = new Intent(getActivity(), DengLuActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        mGuide_img.setImageResource(img);
    }

    @Override
    protected void initData() {

    }
}
