package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jlzquan.www.R;
import com.sxbwstxpay.model.NewsIndex;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class BangZhuZXViewHolder extends BaseViewHolder<NewsIndex.DataBean> {

    private final TextView textTitle;
    private final TextView textIntro;

    public BangZhuZXViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textTitle = $(R.id.textTitle);
        textIntro = $(R.id.textIntro);
    }

    @Override
    public void setData(NewsIndex.DataBean data) {
        super.setData(data);
        textTitle.setText(data.getTitle());
        textIntro.setText(data.getIntro());
    }

}
