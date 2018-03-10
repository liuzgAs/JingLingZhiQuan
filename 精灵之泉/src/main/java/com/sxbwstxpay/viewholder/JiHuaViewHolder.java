package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.HkBill;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class JiHuaViewHolder extends BaseViewHolder<HkBill.DataBean> {

    private final TextView textTitle;
    private final TextView textDes;
    private final TextView textOrderSn;
    private final TextView textTimeStr;
    private final TextView textTab0001;
    private final TextView textTab0002;
    private final TextView textTab0101;
    private final TextView textTab0102;
    private final TextView textTab0201;
    private final TextView textTab0202;

    public JiHuaViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textTitle = $(R.id.textTitle);
        textDes = $(R.id.textDes);
        textOrderSn = $(R.id.textOrderSn);
        textTimeStr = $(R.id.textTimeStr);
        textTab0001 = $(R.id.textTab0001);
        textTab0002 = $(R.id.textTab0002);
        textTab0101 = $(R.id.textTab0101);
        textTab0102 = $(R.id.textTab0102);
        textTab0201 = $(R.id.textTab0201);
        textTab0202 = $(R.id.textTab0202);
    }

    @Override
    public void setData(HkBill.DataBean data) {
        super.setData(data);
        textTitle.setText(data.getTitle());
        textDes.setText(data.getDes());
        textOrderSn.setText(data.getOrderSn());
        textTimeStr.setText(data.getTimeStr());
        textTab0001.setText(data.getTab().get(0).getN());
        textTab0002.setText(data.getTab().get(0).getV());
        textTab0101.setText(data.getTab().get(1).getN());
        textTab0102.setText(data.getTab().get(1).getV());
        textTab0201.setText(data.getTab().get(2).getN());
        textTab0202.setText(data.getTab().get(2).getV());
    }

}
