package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.OrderVipbefore;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class VipBuyViewHolder extends BaseViewHolder<OrderVipbefore.SelectValueBean> {

    private final TextView textName;

    public VipBuyViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textName = $(R.id.textName);
    }

    @Override
    public void setData(OrderVipbefore.SelectValueBean data) {
        super.setData(data);
        textName.setText(data.getName());
    }
    
}
