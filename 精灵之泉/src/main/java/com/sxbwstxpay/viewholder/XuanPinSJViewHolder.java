package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.MainActivity;
import com.sxbwstxpay.model.IndexDataBean;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class XuanPinSJViewHolder extends BaseViewHolder<IndexDataBean> {

    private final ImageView imageImg;
    private final TextView textTitle;
    private final TextView textPrice;
    private final TextView textGoods_money;
    private final TextView textStock_num;
    private final Button buttonAct;
    private IndexDataBean data;

    public XuanPinSJViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textTitle = $(R.id.textTitle);
        textPrice = $(R.id.textPrice);
        textGoods_money = $(R.id.textGoods_money);
        textStock_num = $(R.id.textStock_num);
        buttonAct = $(R.id.buttonAct);
        $(R.id.imageShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).share(data.getShare());
            }
        });
    }

    @Override
    public void setData(IndexDataBean data) {
        super.setData(data);
        this.data=data;
        Glide.with(getContext())
                .load(data.getImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textTitle.setText(data.getTitle());
        textPrice.setText("¥"+data.getPrice());
        textGoods_money.setText("赚"+data.getGoods_money());
        textStock_num.setText("库存"+data.getStock_num());
        if (data.getAct()==1){
            buttonAct.setText("√");
        }else {
            buttonAct.setText("+");
        }
    }
    
}
