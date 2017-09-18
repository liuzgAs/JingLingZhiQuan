package com.sxbwstxpay.viewholder;

import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jlzquan.www.R;
import com.sxbwstxpay.customview.GridView4ScrollView;
import com.sxbwstxpay.model.ShareShareDay;
import com.sxbwstxpay.util.SDFileHelper;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class TuWenTGViewHolder extends BaseViewHolder<ShareShareDay.ListDataBean> {

    private final TextView textDate;
    private final TextView textShare_contents;
    private final GridView4ScrollView gridView;
    private ShareShareDay.ListDataBean data;
    private final TextView textXiaZai;
    private final TextView textBaoCun;
    private Bitmap[] bitmap;


    public TuWenTGViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textDate = $(R.id.textDate);
        textShare_contents = $(R.id.textShare_contents);
        gridView = $(R.id.gridView);
        textXiaZai = $(R.id.textXiaZai);
        textBaoCun = $(R.id.textBaoCun);
        $(R.id.viewBaoCun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDFileHelper helper = new SDFileHelper(getContext());
                for (int i = 0; i < data.getShare_images().size(); i++) {
                    helper.savePicture(System.currentTimeMillis()+i + ".jpg", data.getShare_images().get(i));
                }
                Toast.makeText(getContext(), "已保存到本地相册", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setData(ShareShareDay.ListDataBean data) {
        super.setData(data);
        this.data = data;
        textDate.setText(data.getDate());
        textShare_contents.setText(data.getShare_contents());
        textXiaZai.setText("(" + data.getDownloads() + ")");
        textBaoCun.setText("");
        if (data != null) {
            bitmap = new Bitmap[data.getShare_images().size()];
            gridView.setAdapter(new MyAdapter());
        }
    }

    class MyAdapter extends BaseAdapter {
        class ViewHolder {
            public ImageView imageImg;
        }

        @Override
        public int getCount() {
            return data.getShare_images().size();
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
                    .load(data.getShare_images().get(position))
                    .placeholder(R.mipmap.ic_empty)
                    .into(holder.imageImg);
            return convertView;
        }
    }

}
