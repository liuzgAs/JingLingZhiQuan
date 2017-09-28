package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import com.sxbwstxpay.R;
import com.sxbwstxpay.model.UserMoneylog;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ZhangDanViewHolder extends BaseViewHolder<UserMoneylog.DataBean> {
    private final ImageView imageZhanKai;
    private UserMoneylog.DataBean data;
    private final View viewDetail;
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
    private int yingCangNum = 0;

    public ZhangDanViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageZhanKai = $(R.id.imageZhanKai);
        viewDetail = $(R.id.viewDetail);
        textTitle = $(R.id.textTitle);
        textDate = $(R.id.textDate);
        textText = $(R.id.textText);
        textMoney = $(R.id.textMoney);
        textBlance = $(R.id.textBlance);
        textToMoney = $(R.id.textToMoney);
        textPaymen = $(R.id.textPaymen);
        textFee = $(R.id.textFee);
        textRate = $(R.id.textRate);
        textOrderSn = $(R.id.textOrderSn);

        imageZhanKai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!data.isZhanKai()) {
                    data.setZhanKai(true);
                    imageZhanKai.setImageResource(R.mipmap.shousuo);
                    switch (yingCangNum) {
                        case 0:
                            viewDetail.setBackgroundResource(R.mipmap.dingdan8);
                            break;
                        case 1:
                            viewDetail.setBackgroundResource(R.mipmap.dingdan7);
                            break;
                        case 2:
                            viewDetail.setBackgroundResource(R.mipmap.dingdan6);
                            break;
                        case 3:
                            viewDetail.setBackgroundResource(R.mipmap.dingdan5);
                            break;
                    }
                } else {
                    data.setZhanKai(false);
                    imageZhanKai.setImageResource(R.mipmap.zhankai);
                    viewDetail.setBackgroundResource(R.mipmap.dingdan4);
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
        textMoney.setText("¥" + data.getMoney());
        textBlance.setText("¥" + data.getBlance());
        textToMoney.setText("¥" + data.getToMoney());
        if (TextUtils.isEmpty(data.getPaymen())) {
            yingCangNum++;
        }
        if (TextUtils.isEmpty(data.getFee())) {
            yingCangNum++;
        }
        if (TextUtils.isEmpty(data.getRate())) {
            yingCangNum++;
        }
        if (TextUtils.isEmpty(data.getOrderSn())) {
            yingCangNum++;
        }
        textPaymen.setText(data.getPaymen());
        textFee.setText("¥" + data.getFee());
        textRate.setText(data.getRate());
        textOrderSn.setText(data.getOrderSn());
    }

}
