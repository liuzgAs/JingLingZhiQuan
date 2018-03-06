package com.sxbwstxpay.viewholder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ShenQingSHActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.UserOrderinfo;
import com.sxbwstxpay.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class DingDanXQViewHolder extends BaseViewHolder<UserOrderinfo.DataBean> {

    private final ImageView imageImg;
    private final TextView textTitle;
    private final TextView textGuiGe;
    private final TextView textNum;
    private final TextView textPrice;
    private final TextView textZhuan;
    private final TextView textShouHou;
    UserOrderinfo.DataBean data;

    public DingDanXQViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textTitle = $(R.id.textTitle);
        textGuiGe = $(R.id.textGuiGe);
        textNum = $(R.id.textNum);
        textPrice = $(R.id.textPrice);
        textZhuan = $(R.id.textZhuan);
        textShouHou = $(R.id.textShouHou);
        textShouHou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ShenQingSHActivity.class);
                intent.putExtra(Constant.INTENT_KEY.id,data.getId());
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void setData(UserOrderinfo.DataBean data) {
        super.setData(data);
        this.data=data;
        GlideApp.with(getContext())
                .load(data.getImg())
                .centerCrop()
                .placeholder(R.mipmap.ic_empty)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .into(imageImg);
        textTitle.setText(data.getGoods_name());
        textGuiGe.setText(data.getGoods_spe());
        textNum.setText("×"+data.getQuantity());
        textPrice.setText("¥"+data.getGoods_price());
        textZhuan.setText("赚"+data.getGoods_money());
        if (data.getAfterState()==1){
            textShouHou.setVisibility(View.VISIBLE);
        }else {
            textShouHou.setVisibility(View.GONE);
        }
    }
    
}
