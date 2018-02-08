package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.MapMarkerBean;
import com.sxbwstxpay.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class HeaderSerarchViewHolder extends BaseViewHolder<MapMarkerBean> {

    private final ImageView imageImg;
    private final TextView textName;
    private final TextView textDes;
    private final TextView textDistance;

    public HeaderSerarchViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textName = $(R.id.textName);
        textDes = $(R.id.textDes);
        textDistance = $(R.id.textDistance);
    }

    @Override
    public void setData(MapMarkerBean data) {
        super.setData(data);
        GlideApp.with(getContext())
                .load(data.getHeadImg())
                .centerCrop()
                .circleCrop()
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textName.setText(data.getNickName());
        textDistance.setText(data.getDistance());
        textDes.setText(data.getDes());
    }
    
}
