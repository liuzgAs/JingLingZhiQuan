package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
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
            textPcate.setBackgroundResource(R.drawable.youdian_bg2);
        }else {
            textPcate.setBackgroundResource(R.drawable.youdian_bg1);
        }
    }

}
