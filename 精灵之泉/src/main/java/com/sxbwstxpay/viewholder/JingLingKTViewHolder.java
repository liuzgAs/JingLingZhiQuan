package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.ClassroomItem;
import com.sxbwstxpay.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class JingLingKTViewHolder extends BaseViewHolder<ClassroomItem.DataBean> {
    private final ImageView imageImg;
    private final TextView textTitle;
    private final TextView textNum;
    private final TextView textDes;

    public JingLingKTViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textTitle = $(R.id.textTitle);
        textNum = $(R.id.textNum);
        textDes = $(R.id.textDes);
    }

    @Override
    public void setData(final ClassroomItem.DataBean data) {
        super.setData(data);
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textTitle.setText(data.getTitle());
        textDes.setText(data.getDes());
        textNum.setText(data.getNum());
    }

}
