package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.customview.MyIm;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class WoDeSCViewHolder extends BaseViewHolder<Integer> {

    private final TextView textDes;

    public WoDeSCViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textDes = $(R.id.textDes);
    }

    @Override
    public void setData(Integer data) {
        super.setData(data);
        SpannableString span = new SpannableString("i "+"2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋2017新款女鞋");
        MyIm imgspan = new MyIm(getContext(), R.mipmap.jingxuan);
        span.setSpan(imgspan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        textDes.setText(span);
    }
    
}
