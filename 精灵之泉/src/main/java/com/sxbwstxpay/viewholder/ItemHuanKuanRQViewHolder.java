package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.RiQi;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ItemHuanKuanRQViewHolder extends BaseViewHolder<RiQi> {

    private final TextView textRiQi;

    public ItemHuanKuanRQViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textRiQi = $(R.id.textRiQi);
    }

    @Override
    public void setData(RiQi data) {
        super.setData(data);
        if (TextUtils.equals(data.getRiQi(),"0")||TextUtils.isEmpty(data.getRiQi())){
            textRiQi.setText("");
            textRiQi.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.white));
        }else {
            textRiQi.setText(data.getRiQi());
            if (data.isSelect()){
                textRiQi.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.basic));
            }else {
                textRiQi.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.white));
            }
        }
    }
    
}
