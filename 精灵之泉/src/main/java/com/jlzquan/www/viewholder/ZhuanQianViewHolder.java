package com.jlzquan.www.viewholder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.jlzquan.www.activity.TuWenTGActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jlzquan.www.R;
import com.jlzquan.www.activity.TuiGuangActivity;
import com.jlzquan.www.activity.TuiGuangEWMActivity;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ZhuanQianViewHolder extends BaseViewHolder<Integer> {

    public ZhuanQianViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        $(R.id.view01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), TuiGuangActivity.class);
                getContext().startActivity(intent);
            }
        });
        $(R.id.view02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        $(R.id.view03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), TuiGuangEWMActivity.class);
                getContext().startActivity(intent);
            }
        });
        $(R.id.view04).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), TuWenTGActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void setData(Integer data) {
        super.setData(data);
    }

}
