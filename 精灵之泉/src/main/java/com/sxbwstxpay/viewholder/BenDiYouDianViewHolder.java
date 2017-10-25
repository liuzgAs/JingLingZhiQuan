package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.IndexStore;
import com.sxbwstxpay.util.ScreenUtils;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class BenDiYouDianViewHolder extends BaseViewHolder<IndexStore.DataBean> {

    private final ImageView imageRecom_img;
    private final TextView textTitle;

    public BenDiYouDianViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageRecom_img = $(R.id.imageRecom_img);
        textTitle = $(R.id.textTitle);
        int screenWidth = ScreenUtils.getScreenWidth(getContext());
        ViewGroup.LayoutParams layoutParams = imageRecom_img.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.height = screenWidth / 2;
        imageRecom_img.setLayoutParams(layoutParams);
    }

    @Override
    public void setData(IndexStore.DataBean data) {
        super.setData(data);
        Glide.with(getContext())
                .load(data.getImg())
                .asBitmap()
                .placeholder(R.mipmap.ic_empty)
                .into(imageRecom_img);
        textTitle.setText(data.getName());
    }

}
