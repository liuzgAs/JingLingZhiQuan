package com.sxbwstxpay.viewholder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.BigImgActivity;
import com.sxbwstxpay.activity.WoDeSCActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.GridView4ScrollView;
import com.sxbwstxpay.customview.MyIm;
import com.sxbwstxpay.model.BigImgList;
import com.sxbwstxpay.model.UserItem;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class WoDeSCViewHolder extends BaseViewHolder<UserItem.DataBean> {

    private final TextView textDes;
    private final TextView textAddTime;
    private final GridView4ScrollView gridView;
    private UserItem.DataBean data;

    public WoDeSCViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textDes = $(R.id.textDes);
        textAddTime = $(R.id.textAddTime);
        gridView = $(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageImg = (ImageView) view.findViewById(R.id.imageImg);
                Intent intent = new Intent();
                intent.setClass(getContext(), BigImgActivity.class);
                intent.putExtra(Constant.INTENT_KEY.BIG_IMG, new BigImgList(data.getImgs()));
                intent.putExtra(Constant.INTENT_KEY.BIG_IMG_POSITION, position);
                intent.putExtra(Constant.INTENT_KEY.value,data.getTitle());
                String transitionName = getContext().getString(R.string.TransitionsBigImg);
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(((WoDeSCActivity)getContext()), imageImg, transitionName);
                getContext().startActivity(intent, transitionActivityOptions.toBundle());
            }
        });
    }

    @Override
    public void setData(UserItem.DataBean data) {
        super.setData(data);
        this.data=data;
        if (data.getRecommend() == 1) {
            SpannableString span = new SpannableString("i " + data.getTitle());
            MyIm imgspan = new MyIm(getContext(), R.mipmap.jingxuan);
            span.setSpan(imgspan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            textDes.setText(span);
        } else {
            textDes.setText(data.getTitle());
        }
        textAddTime.setText(data.getAddTime());
        if (data != null) {
            gridView.setAdapter(new MyAdapter());
        }
    }

    class MyAdapter extends BaseAdapter {
        class ViewHolder {
            public ImageView imageImg;
        }

        @Override
        public int getCount() {
            return data.getImg().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tu_wen_tg_grid, null);
                holder.imageImg = (ImageView) convertView.findViewById(R.id.imageImg);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Glide.with(getContext())
                    .load(data.getImg().get(position))
                    .asBitmap()
                    .placeholder(R.mipmap.ic_empty)
                    .into(holder.imageImg);
            return convertView;
        }
    }

}
