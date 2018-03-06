package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.UserOrderinfo;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class DDXQFooteViewHolder extends BaseViewHolder<UserOrderinfo.DesListBean> {

    private final TextView textName;
    private final TextView textDes;

    public DDXQFooteViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textName = $(R.id.textName);
        textDes = $(R.id.textDes);
    }

    @Override
    public void setData(UserOrderinfo.DesListBean data) {
        super.setData(data);
        textName.setText(data.getN());
        textDes.setText(data.getV());
    }
    
}
