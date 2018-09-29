package com.sxbwstxpay.viewholder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ChanPinXQActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.MyEasyRecyclerView;
import com.sxbwstxpay.model.IndexStyleMy;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ZhuanShuMYViewHolder extends BaseViewHolder<IndexStyleMy.DataBean> {
    private TextView textStyle;
    private MyEasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<IndexStyleMy.DataBean.DesBean> adapter;
    private ImageView imageImg[]=new ImageView[5];
    public ZhuanShuMYViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg[0] = $(R.id.imageImg1);
        imageImg[1] = $(R.id.imageImg2);
        imageImg[2] = $(R.id.imageImg3);
        imageImg[3] = $(R.id.imageImg4);
        imageImg[4] = $(R.id.imageImg5);
        textStyle = $(R.id.textStyle);
        recyclerView = $(R.id.recyclerView);
        initRecycler();
    }

    @Override
    public void setData(final IndexStyleMy.DataBean data) {
        super.setData(data);
        for (int i=0;i<data.getImgs().size();i++){
            GlideApp.with(getContext())
                    .load(data.getImgs().get(i).getImg())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_empty)
                    .into(imageImg[i]);
        }
        textStyle.setText(data.getTitle());
        adapter.clear();
        adapter.addAll(data.getDes());
        for (int i=0;i<imageImg.length;i++){
            final int j=i;
            imageImg[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(Constant.INTENT_KEY.id,data.getImgs().get(j).getGoods_id());
                    intent.setClass(getContext(), ChanPinXQActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    getContext().startActivity(intent);
                }
            });
        }
    }
    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter = new RecyclerArrayAdapter<IndexStyleMy.DataBean.DesBean>(getContext()) {

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_cd_style;
                return new MYStyleViewHolder(parent, layout);
            }
        });
        SpaceDecoration spaceDecoration = new SpaceDecoration((int) DpUtils.convertDpToPixel(12f, getContext()));
        spaceDecoration.setPaddingEdgeSide(false);
        recyclerView.addItemDecoration(spaceDecoration);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recycler, int dx, int dy) {
                super.onScrolled(recycler, dx, dy);
                recyclerView.setScroll(true);
            }
        });
        recyclerView.setOnDaoDiLeListener(new MyEasyRecyclerView.OnDaoDiLeListener() {
            @Override
            public void daoDiLe() {
            }
        });
    }
}
