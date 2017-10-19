package com.sxbwstxpay.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.WebActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.NewsIndex;
import com.sxbwstxpay.util.LogUtil;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class GongGaoViewHolder extends BaseViewHolder<NewsIndex.DataBean> {

    private final TextView textAddTime;
    private final TextView textTitle;
    private final TextView textIntro;
    private NewsIndex.DataBean data;

    public GongGaoViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textAddTime = $(R.id.textAddTime);
        textTitle = $(R.id.textTitle);
        textIntro = $(R.id.textIntro);
    }

    @Override
    public void setData(NewsIndex.DataBean data) {
        super.setData(data);
        this.data=data;
        textAddTime.setText(data.getAddTime());
        textTitle.setText(data.getTitle());
        String intro = data.getIntro();
        final String url = data.getUrl();
        LogUtil.LogShitou("GongGaoViewHolder--intro", "" + intro);
        LogUtil.LogShitou("GongGaoViewHolder--url", "" + url);
        if (!TextUtils.isEmpty(url)) {
            int i = intro.indexOf(url);
            if (i >= 0) {
                LogUtil.LogShitou("GongGaoViewHolder--i", "" + i);
                LogUtil.LogShitou("GongGaoViewHolder--url.length", url.length() + "");
                SpannableStringBuilder ssb = new SpannableStringBuilder(intro);
                ssb.setSpan(new MyClickText(getContext()),i, i + url.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textIntro.setText(ssb);
                textIntro.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
                textIntro.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明
            } else {
                textIntro.setText(intro);
            }
        }else {
            textIntro.setText(intro);
        }
    }

    class MyClickText extends ClickableSpan {
        private Context context;

        public MyClickText(Context context) {
            this.context = context;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            //设置文本的颜色
            ds.setColor(context.getResources().getColor(R.color.basic_color));
            //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
            ds.setUnderlineText(true);
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent();
            intent.setClass(getContext(), WebActivity.class);
            intent.putExtra(Constant.INTENT_KEY.TITLE, data.getUrlTitle());
            intent.putExtra(Constant.INTENT_KEY.URL, data.getUrl());
            getContext().startActivity(intent);
        }
    }

}
