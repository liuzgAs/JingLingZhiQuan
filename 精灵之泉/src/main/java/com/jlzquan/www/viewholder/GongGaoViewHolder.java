package com.jlzquan.www.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jlzquan.www.R;
import com.jlzquan.www.model.NewsIndex;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class GongGaoViewHolder extends BaseViewHolder<NewsIndex.DataBean> {

    private final TextView textAddTime;
    private final TextView textTitle;
    private final TextView textIntro;

    public GongGaoViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textAddTime = $(R.id.textAddTime);
        textTitle = $(R.id.textTitle);
        textIntro = $(R.id.textIntro);
    }

    @Override
    public void setData(NewsIndex.DataBean data) {
        super.setData(data);
        textAddTime.setText(data.getAddTime());
        textTitle.setText(data.getTitle());
        textIntro.setText(data.getIntro());
    }

}
