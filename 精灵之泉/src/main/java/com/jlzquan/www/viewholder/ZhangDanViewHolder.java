package com.jlzquan.www.viewholder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import com.jlzquan.www.R;
import com.jlzquan.www.model.UserMoneylog;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ZhangDanViewHolder extends BaseViewHolder<UserMoneylog.DataBean> {
    private final ImageView imageZhanKai;
    private UserMoneylog.DataBean data;
    private final View viewDetail;
    private final View viewDuoYu;
    private final TextView textTitle;
    private final TextView textDate;
    private final TextView textText;
    private final TextView textMoney;
    private final TextView textBlance;
    private final TextView textToMoney;
    private final TextView textPaymen;
    private final TextView textFee;
    private final TextView textRate;
    private final TextView textOrderSn;

    public ZhangDanViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageZhanKai = $(R.id.imageZhanKai);
        viewDetail = $(R.id.viewDetail);
        viewDuoYu = $(R.id.viewDuoYu);
        textTitle = $(R.id.textTitle);
        textDate = $(R.id.textDate);
        textText = $(R.id.textText);
        textMoney = $(R.id.textMoney);
        textBlance = $(R.id.textBlance);
        textToMoney = $(R.id.textToMoney);
        textPaymen = $(R.id.textPaymen);
        textFee =  $(R.id.textFee);
        textRate = $(R.id.textRate);
        textOrderSn = $(R.id.textOrderSn);

        imageZhanKai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!data.isZhanKai()) {
                    data.setZhanKai(true);
                    viewDuoYu.setVisibility(View.VISIBLE);
                    imageZhanKai.setImageResource(R.mipmap.shousuo);
                    viewDetail.setBackgroundResource(R.mipmap.mingxi_item_bg2);
                } else {
                    data.setZhanKai(false);
                    viewDuoYu.setVisibility(View.GONE);
                    imageZhanKai.setImageResource(R.mipmap.zhankai);
                    viewDetail.setBackgroundResource(R.mipmap.mingxi_item_bg1);
                }
            }
        });
    }

    @Override
    public void setData(UserMoneylog.DataBean data) {
        super.setData(data);
        this.data = data;
        textTitle.setText(data.getTitle());
        textDate.setText(data.getDate());
        textText.setText(data.getText());
        textMoney.setText("¥"+data.getMoney());
        textBlance.setText("¥"+data.getBlance());
        textToMoney.setText("¥"+data.getToMoney());
        textPaymen.setText(data.getPaymen());
        textFee.setText("¥"+data.getFee());
        textRate.setText(data.getRate());
        textOrderSn.setText(data.getOrderSn());
    }

}
