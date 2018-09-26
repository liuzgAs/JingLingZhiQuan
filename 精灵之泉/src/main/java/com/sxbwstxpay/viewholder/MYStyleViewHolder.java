package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.IndexStyleMy;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class MYStyleViewHolder extends BaseViewHolder<IndexStyleMy.DataBean.DesBean> {
    private ImageView imageIs;
    private TextView textTitle;
    public MYStyleViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageIs = $(R.id.imageIs);
        textTitle = $(R.id.textTitle);
    }

    @Override
    public void setData(IndexStyleMy.DataBean.DesBean data) {
        super.setData(data);
        if (data.getIs_seleteed()==1){
            imageIs.setImageResource(R.mipmap.chaoda_yes);
        }else {
            imageIs.setImageResource(R.mipmap.chaoda_no);
        }
        textTitle.setText(data.getName());
    }
    
}
