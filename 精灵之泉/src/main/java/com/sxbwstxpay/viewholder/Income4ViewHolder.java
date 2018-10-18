package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.Income4;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class Income4ViewHolder extends BaseViewHolder<Income4.DataBean> {

    private final TextView textName;
    private final TextView textGrade;
    private final TextView textSum;

    public Income4ViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textName = $(R.id.textName);
        textGrade = $(R.id.textGrade);
        textSum = $(R.id.textSum);
    }

    @Override
    public void setData(Income4.DataBean data) {
        super.setData(data);
        textName.setText(data.getName());
        textGrade.setText(data.getDes());
        textSum.setText(data.getMoney());
    }

}
