package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import com.sxbwstxpay.R;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ShouYiMXViewHolder extends BaseViewHolder<Boolean> {
    private final ImageView imageZhanKai;
    private boolean data;
    private final View viewDetail;

    public ShouYiMXViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageZhanKai = $(R.id.imageZhanKai);
        viewDetail = $(R.id.viewDetail);
        imageZhanKai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!data) {
                    data = true;
                    imageZhanKai.setImageResource(R.mipmap.shousuo);
                    viewDetail.setBackgroundResource(R.mipmap.mingxi_item_bg2);
                } else {
                    data = false;
                    imageZhanKai.setImageResource(R.mipmap.zhankai);
                    viewDetail.setBackgroundResource(R.mipmap.mingxi_item_bg1);
                }
            }
        });
    }

    @Override
    public void setData(Boolean data) {
        super.setData(data);
        this.data = data;
    }

}
