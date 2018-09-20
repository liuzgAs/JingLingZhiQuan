package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.TestStyle;
import com.sxbwstxpay.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class test2ViewHolder extends BaseViewHolder<TestStyle.DataBean> {
    private final TextView textTitle;
    private final ImageView image;
    private final View viewSelet;

    public test2ViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textTitle = $(R.id.textTitle);
        image = $(R.id.image);
        viewSelet = $(R.id.viewSelet);

    }

    @Override
    public void setData(TestStyle.DataBean data) {
        super.setData(data);
        textTitle.setText(data.getTitle());
        GlideApp.with(getContext())
                .load(data.getImg())
                .centerCrop()
                .placeholder(R.mipmap.ic_empty)
                .into(image);
        if (data.getIsc()){
            viewSelet.setVisibility(View.VISIBLE);
        }else {
            viewSelet.setVisibility(View.GONE);
        }
    }
    
}
