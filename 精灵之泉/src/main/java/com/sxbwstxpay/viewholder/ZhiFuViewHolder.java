package com.sxbwstxpay.viewholder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.jlzquan.www.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.activity.ZhiFuCGActivity;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ZhiFuViewHolder extends BaseViewHolder<Integer> {

    public ZhiFuViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        $(R.id.buttonZhiFu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ZhiFuCGActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void setData(Integer data) {
        super.setData(data);
    }
    
}
