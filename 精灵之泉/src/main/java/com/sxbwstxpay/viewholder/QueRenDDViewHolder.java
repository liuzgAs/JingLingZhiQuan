package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.CartOrder;
import com.sxbwstxpay.util.GlideApp;

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
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getGoods_img())
                .placeholder(R.mipmap.ic_empty)
                .into(imageGoods_img);
        textGoods_title.setText(data.getGoods_title());
        textSpe_name.setText(data.getSpe_name());
        textNum.setText("×"+data.getNum());
        if (data.getIs_dbb()==1){
            textGoods_price.setText(data.getGoods_score()+"积分");
            textGoods_money.setVisibility(View.GONE);
        }else {
            textGoods_price.setText("¥"+data.getGoods_price());
            if (!TextUtils.isEmpty(data.getGoods_money())){
                textGoods_money.setText("赚"+data.getGoods_money());
                textGoods_money.setVisibility(View.VISIBLE);
            }else {
                textGoods_money.setVisibility(View.GONE);
            }
        }
    }
    
}
