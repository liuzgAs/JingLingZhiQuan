package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.StoreViews;
import com.sxbwstxpay.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class FangKeGLViewHolder extends BaseViewHolder<StoreViews.GoodsBean> {

    private final ImageView imageImg;
    private final TextView textTitle;
    private final TextView textUser;
    private final TextView textView;

    public FangKeGLViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textTitle = $(R.id.textTitle);
        textUser = $(R.id.textUser);
        textView = $(R.id.textView);
    }

    @Override
    public void setData(StoreViews.GoodsBean data) {
        super.setData(data);
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textTitle.setText(data.getTitle());
        textUser.setText(data.getUser()+"");
        textView.setText(data.getView()+"");
    }
    
}
