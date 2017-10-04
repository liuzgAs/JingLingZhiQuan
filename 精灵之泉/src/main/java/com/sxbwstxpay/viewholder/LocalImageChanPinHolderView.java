package com.sxbwstxpay.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.sxbwstxpay.R;

/**
 * des： banner image
 * author： ZhangJieBo
 * date： 2017/7/7 0007 上午 9:46
 */
public class LocalImageChanPinHolderView implements Holder<String> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(final Context context, int position, final String data) {
        Glide.with(context)
                .load(data)
                .asBitmap()
                .placeholder(R.mipmap.ic_empty)
                .into(imageView);
    }
}