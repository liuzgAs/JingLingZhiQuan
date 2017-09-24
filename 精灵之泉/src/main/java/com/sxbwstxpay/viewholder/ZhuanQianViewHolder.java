package com.sxbwstxpay.viewholder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sxbwstxpay.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.activity.TuWenTGActivity;
import com.sxbwstxpay.activity.TuiGuangActivity;
import com.sxbwstxpay.activity.TuiGuangEWMActivity;
import com.sxbwstxpay.model.IndexMakemoney;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ZhuanQianViewHolder extends BaseViewHolder<IndexMakemoney> {

    private final TextView textTitle1;
    private final TextView textTitle2;
    private final TextView textTitle3;
    private final TextView textTitle4;
    private final TextView textDes1;
    private final TextView textDes2;
    private final TextView textDes3;
    private final TextView textDes4;

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
        textTitle1 = $(R.id.textTitle1);
        textTitle2 = $(R.id.textTitle2);
        textTitle3 = $(R.id.textTitle3);
        textTitle4 = $(R.id.textTitle4);
        textDes1 = $(R.id.textDes1);
        textDes2 = $(R.id.textDes2);
        textDes3 = $(R.id.textDes3);
        textDes4 = $(R.id.textDes4);
    }

    @Override
    public void setData(IndexMakemoney data) {
        super.setData(data);
        List<IndexMakemoney.ListBean> listBeanList = data.getList();
        textTitle1.setText(listBeanList.get(0).getTitle());
        textTitle2.setText(listBeanList.get(1).getTitle());
        textTitle3.setText(listBeanList.get(2).getTitle());
        textTitle4.setText(listBeanList.get(3).getTitle());
        textDes1.setText(listBeanList.get(0).getDes());
        textDes2.setText(listBeanList.get(1).getDes());
        textDes3.setText(listBeanList.get(2).getDes());
        textDes4.setText(listBeanList.get(3).getDes());
    }

}
