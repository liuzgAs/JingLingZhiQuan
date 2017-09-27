package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.CartOrder;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class QueRenDDViewHolder extends BaseViewHolder<CartOrder.CartBean> {

    private final ImageView imageGoods_img;
    private final TextView textGoods_title;
    private final TextView textSpe_name;
    private final TextView textNum;
    private final TextView textGoods_price;
    private final TextView textGoods_money;

    public QueRenDDViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageGoods_img = $(R.id.imageGoods_img);
        textGoods_title = $(R.id.textGoods_title);
        textSpe_name = $(R.id.textSpe_name);
        textNum = $(R.id.textNum);
        textGoods_price = $(R.id.textGoods_price);
        textGoods_money = $(R.id.textGoods_money);
    }

    @Override
    public void setData(CartOrder.CartBean data) {
        super.setData(data);
        Glide.with(getContext())
                .load(data.getGoods_img())
                .placeholder(R.mipmap.ic_empty)
                .into(imageGoods_img);
        textGoods_title.setText(data.getGoods_title());
        textSpe_name.setText(data.getSpe_name());
        textNum.setText("×"+data.getNum());
        textGoods_price.setText("¥"+data.getGoods_price());
        textGoods_money.setText("赚"+data.getGoods_money());
    }
    
}
