package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.CouponIndex;


/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class YouHuiQuanViewHolder extends BaseViewHolder<CouponIndex.DataBean> {

    private final TextView textAmount;
    private final TextView textTitle;
    private final TextView textDes;
    private final TextView textE_time;
    private final TextView textMoneyDes;

    public YouHuiQuanViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textAmount = $(R.id.textAmount);
        textTitle = $(R.id.textTitle);
        textDes = $(R.id.textDes);
        textE_time = $(R.id.textE_time);
        textMoneyDes = $(R.id.textMoneyDes);
    }

    @Override
    public void setData(CouponIndex.DataBean data) {
        super.setData(data);
        SpannableString span = new SpannableString("¥"+data.getMoney());
        span.setSpan(new RelativeSizeSpan(0.4f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textAmount.setText(span);
        textTitle.setText(data.getName());
        textDes.setText(data.getDes());
        textE_time.setText(data.getE_time());
        textMoneyDes.setText(data.getMoneyDes());
    }
    
}
