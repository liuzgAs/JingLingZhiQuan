package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.SkillDetails;
import com.sxbwstxpay.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class DianPuFWViewHolder extends BaseViewHolder<SkillDetails.SkillBean> {
    private final ImageView imageImg;
    private final TextView textTitle;
    private final TextView textPrice;
    private final TextView textDes;
    private final TextView textCateName;

    public DianPuFWViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textTitle = $(R.id.textTitle);
        textPrice = $(R.id.textPrice);
        textDes = $(R.id.textDes);
        textCateName = $(R.id.textCateName);
    }

    @Override
    public void setData(final SkillDetails.SkillBean data) {
        super.setData(data);
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textTitle.setText(data.getTitle());
        textPrice.setText(data.getPrice());
        textCateName.setText(data.getCateName());
        textDes.setText(data.getDes());
    }

}
