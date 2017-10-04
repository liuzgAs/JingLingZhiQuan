package com.sxbwstxpay.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.GuanLiWDDPActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.BannerBean;
import com.sxbwstxpay.model.ExtraMap;

/**
 * des： banner image
 * author： ZhangJieBo
 * date： 2017/7/7 0007 上午 9:46
 */
public class LocalImageGuanLiWDDPHolderView implements Holder<BannerBean> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(final Context context, int position, final BannerBean data) {
        Glide.with(context)
                .load(data.getImg())
                .asBitmap()
                .placeholder(R.mipmap.ic_empty)
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtraMap extraMapBean = new ExtraMap(((GuanLiWDDPActivity)context).userInfo.getUid(), data.getCode(),  data.getTitle(),data.getUrl());
                Intent intent = new Intent();
                intent.setAction(Constant.BROADCASTCODE.EXTRAMAP);
                intent.putExtra(Constant.INTENT_KEY.EXTRAMAP, extraMapBean);
                context.sendBroadcast(intent);
            }
        });
    }
}