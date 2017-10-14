package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.UserMoneylog;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ZhangDanViewHolder extends BaseViewHolder<List<UserMoneylog.DataBean>> {

    private final ListView listView;
    private List<UserMoneylog.DataBean> data;

    public ZhangDanViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        listView = $(R.id.listView);
    }

    @Override
    public void setData(List<UserMoneylog.DataBean> data) {
        super.setData(data);
        this.data = data;
        listView.setAdapter(new MyAdapter());
    }

    class MyAdapter extends BaseAdapter {
        class ViewHolder {
            public TextView textDes;
            public TextView textName;
            public TextView textLine;
        }

        @Override
        public int getCount() {
            return data.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                if (position == 0) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_zhang_dan01, null);
                } else if (position == 1) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_zhang_dan02, null);
                } else {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_zhang_dan03, null);
                }
                holder.textDes = (TextView) convertView.findViewById(R.id.textDes);
                holder.textName = (TextView) convertView.findViewById(R.id.textName);
                holder.textLine = (TextView) convertView.findViewById(R.id.textLine);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == data.size() - 1) {
                holder.textLine.setVisibility(View.GONE);
            }else {
                holder.textLine.setVisibility(View.VISIBLE);
            }
            holder.textDes.setText(data.get(position).getRight());
            holder.textName.setText(data.get(position).getLeft());
            return convertView;
        }
    }

}
