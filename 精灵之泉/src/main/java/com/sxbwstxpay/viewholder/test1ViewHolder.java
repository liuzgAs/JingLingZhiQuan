package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.TestStyle;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class test1ViewHolder extends BaseViewHolder<TestStyle.DataBean> {
    private final TextView textTitle;
    private final ImageView image;

    public test1ViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textTitle = $(R.id.textTitle);
        image = $(R.id.image);

    }

    @Override
    public void setData(TestStyle.DataBean data) {
        super.setData(data);
        textTitle.setText(data.getTitle());
        if (data.getIsc()){
            image.setImageResource(R.mipmap.ceshi_xz);
        }else {
            image.setImageResource(R.mipmap.ceshi_weixz);
        }
    }
    
}
