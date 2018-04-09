package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.HkConfirm;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ZhiFuZXJinEViewHolder extends BaseViewHolder<HkConfirm.DataBean> {

    private final TextView zhifuleix;
    private final TextView textZongJinE;

    public ZhiFuZXJinEViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        zhifuleix = $(R.id.zhifuleix);
        textZongJinE = $(R.id.textZongJinE);
    }

    @Override
    public void setData(HkConfirm.DataBean data) {
        super.setData(data);
        zhifuleix.setText(data.getN());
        textZongJinE.setText(data.getV());
    }

}
