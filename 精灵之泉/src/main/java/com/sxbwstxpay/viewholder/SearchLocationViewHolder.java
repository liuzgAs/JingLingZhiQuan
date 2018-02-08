package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.help.Tip;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class SearchLocationViewHolder extends BaseViewHolder<Tip> {

    private final TextView textName;
    private final TextView textDes;

    public SearchLocationViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textName = $(R.id.textName);
        textDes = $(R.id.textDes);
    }

    @Override
    public void setData(Tip data) {
        super.setData(data);
        textName.setText(data.getName());
        textDes.setText(data.getAddress());
    }
    
}
