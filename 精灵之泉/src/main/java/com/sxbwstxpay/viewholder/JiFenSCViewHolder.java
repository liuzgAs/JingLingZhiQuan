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
import com.sxbwstxpay.activity.ChanPinXQActivity;
import com.sxbwstxpay.activity.MainActivity;
import com.sxbwstxpay.activity.ShangPinScActivity;
import com.sxbwstxpay.activity.SouSuoActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.IndexDataBean;
import com.sxbwstxpay.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class JiFenSCViewHolder extends BaseViewHolder<IndexDataBean> {

    private final ImageView imageImg;
    private final TextView textTitle;
    private final TextView textStock_num;
    private final Button buttonAct;
    private final TextView textScore;
    private IndexDataBean data;

    public JiFenSCViewHolder(ViewGroup parent, @LayoutRes int res, final String activity) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textTitle = $(R.id.textTitle);
        textScore = $(R.id.textScore);
        textStock_num = $(R.id.textStock_num);
        buttonAct = $(R.id.buttonAct);
        $(R.id.imageSuCai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.id, data.getId());
                intent.setClass(getContext(), ShangPinScActivity.class);
                getContext().startActivity(intent);
            }
        });
        $(R.id.imageShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (activity) {
                    case "MainActivity":
                        ((MainActivity) getContext()).share(data.getId(), "goods", data.getShare());
                        break;
                    case "SouSuoActivity":
                        ((SouSuoActivity) getContext()).share(data.getId(), "goods", data.getShare());
                        break;
                }
            }
        });
        buttonAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.id, data.getId());
                intent.setClass(getContext(), ChanPinXQActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void setData(IndexDataBean data) {
        super.setData(data);
        this.data = data;
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getImg())
                .placeholder(R.mipmap.ic_empty01)
                .into(imageImg);
        textTitle.setText(data.getTitle());
        textScore.setText(data.getScore() + "积分");
        textStock_num.setText("库存" + data.getStock_num());
    }

}
