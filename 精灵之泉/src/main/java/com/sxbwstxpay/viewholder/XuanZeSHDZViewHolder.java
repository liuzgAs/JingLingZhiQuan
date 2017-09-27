package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.UserAddress;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class XuanZeSHDZViewHolder extends BaseViewHolder<UserAddress.DataBean> {
    private final TextView textConsignee;
    private final TextView textPhone;
    private final TextView textAreaAddress;
    private final TextView textDefa;
    public XuanZeSHDZViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textConsignee = $(R.id.textConsignee);
        textPhone = $(R.id.textPhone);
        textAreaAddress = $(R.id.textAreaAddress);
        textDefa = $(R.id.textDefa);
    }

    @Override
    public void setData(UserAddress.DataBean data) {
        super.setData(data);
        textConsignee.setText(data.getConsignee());
        textPhone.setText(data.getPhone());
        textAreaAddress.setText(data.getArea() + "-" + data.getAddress());
        if (data.getDefa() == 1) {
            textDefa.setVisibility(View.VISIBLE);
        } else {
            textDefa.setVisibility(View.INVISIBLE);
        }
    }
    
}
