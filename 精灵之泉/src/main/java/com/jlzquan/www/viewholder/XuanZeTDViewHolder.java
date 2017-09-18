package com.jlzquan.www.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import com.jlzquan.www.R;
import com.jlzquan.www.model.BankPayment;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class XuanZeTDViewHolder extends BaseViewHolder<BankPayment.DataBean> {

    private final TextView textName;
    private final TextView textDes;
    private final TextView textRateFee;

    public XuanZeTDViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textName = $(R.id.textName);
        textDes = $(R.id.textDes);
        textRateFee = $(R.id.textRateFee);
    }

    @Override
    public void setData(BankPayment.DataBean data) {
        super.setData(data);
        textName.setText(data.getName());
        textDes.setText(data.getDes());
        textRateFee.setText(data.getRate()+"   "+data.getFee());
    }
    
}
