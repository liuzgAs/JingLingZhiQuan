package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.SkillIndex;


/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class PcateViewHolder extends BaseViewHolder<SkillIndex.CateBean> {

    private final TextView textPcate;

    public PcateViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textPcate = $(R.id.textPcate);
    }

    @Override
    public void setData(SkillIndex.CateBean data) {
        super.setData(data);
        textPcate.setText(data.getName());
        if (Constant.CID==data.getId()){
            textPcate.setTextColor(ContextCompat.getColor(getContext(),R.color.light_black));
        }else {
            textPcate.setTextColor(ContextCompat.getColor(getContext(),R.color.text_gray));
        }
    }

}
