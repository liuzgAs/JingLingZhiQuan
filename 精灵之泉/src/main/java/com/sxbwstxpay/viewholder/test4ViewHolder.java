package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.TestStyle;
import com.sxbwstxpay.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class test4ViewHolder extends BaseViewHolder<TestStyle.DataBean> {
    private final TextView textTitle;
    private final ImageView image;
    private final View viewSelet;
    private final TextView textNum;

    public test4ViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textTitle = $(R.id.textTitle);
        image = $(R.id.image);
        viewSelet = $(R.id.viewSelet);
        textNum = $(R.id.textNum);
    }

    @Override
    public void setData(TestStyle.DataBean data) {
        super.setData(data);
        textTitle.setText(data.getTitle());
        GlideApp.with(getContext())
                .load(data.getImg())
                .centerCrop()
                .placeholder(R.mipmap.ic_empty)
                .into(image);
        if (data.getIsc()) {
            viewSelet.setVisibility(View.VISIBLE);
        } else {
            viewSelet.setVisibility(View.GONE);
        }
        textNum.setText(getNum(getAdapterPosition()));
    }

    private String getNum(int pos) {
        switch (pos) {
            case 0:
                return "A、";
            case 1:
                return "B、";
            case 2:
                return "C、";
            case 3:
                return "D、";
            case 4:
                return "E、";
            case 5:
                return "F、";
            case 6:
                return "G、";
            case 7:
                return "H、";
            case 8:
                return "I、";
            case 9:
                return "J、";
            case 10:
                return "K、";
            default:
                return "L、";
        }
    }
}
