package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.StoreSalesmanage;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class XiaoShouGLViewHolder extends BaseViewHolder<StoreSalesmanage.DataBean> {

    private final TextView textName;
    private final TextView textGrade;
    private final TextView textSum;

    public XiaoShouGLViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textName = $(R.id.textName);
        textGrade = $(R.id.textGrade);
        textSum = $(R.id.textSum);
    }

    @Override
    public void setData(StoreSalesmanage.DataBean data) {
        super.setData(data);
        textName.setText(data.getAddTime());
        textGrade.setText(data.getAmount());
        textSum.setText(data.getMoney());
    }
    
}
