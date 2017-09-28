package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.RecommBean;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ChanPinXQViewHolder extends BaseViewHolder<RecommBean> {

    private final ImageView imageImg;
    private final TextView textTitle;
    private final TextView textPrice;

    public ChanPinXQViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textTitle = $(R.id.textTitle);
        textPrice = $(R.id.textPrice);
    }

    @Override
    public void setData(RecommBean data) {
        super.setData(data);
        Glide.with(getContext())
                .load(data.getImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textTitle.setText(data.getTitle());
        textPrice.setText("¥"+data.getPrice());
    }
    
}
