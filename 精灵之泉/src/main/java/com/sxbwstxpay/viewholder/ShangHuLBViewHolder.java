package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.UserTeam;
import com.sxbwstxpay.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ShangHuLBViewHolder extends BaseViewHolder<UserTeam.DataBean> {

    private final ImageView imageHeadImg;
    private final TextView textNickName;
    private final TextView textRegTime;

    public ShangHuLBViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageHeadImg = $(R.id.imageHeadImg);
        textNickName = $(R.id.textNickName);
        textRegTime = $(R.id.textRegTime);
    }

    @Override
    public void setData(UserTeam.DataBean data) {
        super.setData(data);
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getHeadImg())
                .placeholder(R.mipmap.ic_empty01)
                .into(imageHeadImg);
        textNickName.setText(data.getNickName());
        textRegTime.setText(data.getRegTime());
    }

}
