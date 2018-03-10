package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.HkDetails;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ZhangDanMXViewHolder extends BaseViewHolder<HkDetails.DataBean> {

    private final TextView textTypeDes;
    private final TextView textMoney;
    private final TextView textAdd_time;
    private TextView textTips;
    private ImageView imageShangSanJiao;
    int viewType;

    public ZhangDanMXViewHolder(ViewGroup parent, @LayoutRes int res, int viewType) {
        super(parent, res);
        this.viewType = viewType;
        textTypeDes = $(R.id.textTypeDes);
        textMoney = $(R.id.textMoney);
        textAdd_time = $(R.id.textAdd_time);
        if (viewType == 1) {
            textTips = $(R.id.textTips);
            imageShangSanJiao = $(R.id.imageShangSanJiao);
        }
    }

    @Override
    public void setData(HkDetails.DataBean data) {
        super.setData(data);
        if (viewType == 1) {
            textTips.setText(data.getTips());
            if (data.getType() == 1) {
                textTips.setTextColor(ContextCompat.getColor(getContext(), R.color.basic_color));
                textTips.setBackgroundResource(R.drawable.shape_zhangdanmxtip);
                imageShangSanJiao.setImageResource(R.mipmap.shangsanjiao);
            } else {
                textTips.setTextColor(ContextCompat.getColor(getContext(), R.color.textYellow));
                textTips.setBackgroundResource(R.drawable.shape_zhangdanmxtip1);
                imageShangSanJiao.setImageResource(R.mipmap.shangsanjiao1);
            }
        }
        if (data.getType() == 1) {
            textTypeDes.setTextColor(ContextCompat.getColor(getContext(), R.color.basic_color));
        } else {
            textTypeDes.setTextColor(ContextCompat.getColor(getContext(), R.color.textYellow));
        }
        textTypeDes.setText(data.getTypeDes());
        textMoney.setText(data.getMoney());
        textAdd_time.setText(data.getAdd_time());
    }

}
