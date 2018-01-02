package com.sxbwstxpay.viewholder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.SouSuoActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.IndexDataBean;
import com.sxbwstxpay.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class TuiJianViewHolder extends BaseViewHolder<IndexDataBean> {

    private final ImageView imageImg;
    private final TextView textTitle;
    private final TextView textPrice;
    private final TextView textGoods_money;
    private final TextView textStock_num;
    private final Button buttonAct;
    private IndexDataBean data;

    public TuiJianViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textTitle = $(R.id.textTitle);
        textPrice = $(R.id.textPrice);
        textGoods_money = $(R.id.textGoods_money);
        textStock_num = $(R.id.textStock_num);
        buttonAct = $(R.id.buttonAct);
        buttonAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.value,data);
                ((SouSuoActivity)getContext()).setResult(Constant.REQUEST_RESULT_CODE.TuiJian,intent);
                ((SouSuoActivity)getContext()).finish();
            }
        });
    }


    @Override
    public void setData(IndexDataBean data) {
        super.setData(data);
        this.data=data;
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getImg())
                .placeholder(R.mipmap.ic_empty01)
                .into(imageImg);
        textTitle.setText(data.getTitle());
        textPrice.setText("¥"+data.getPrice());
        textGoods_money.setText("赚"+data.getGoods_money());
        textStock_num.setText("库存"+data.getStock_num());
    }
    
}
