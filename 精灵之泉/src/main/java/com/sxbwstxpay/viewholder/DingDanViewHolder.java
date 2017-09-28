package com.sxbwstxpay.viewholder;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.model.UserOrder;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class DingDanViewHolder extends BaseViewHolder<UserOrder.ListBean> {


    private final TextView textOrderSn;
    private final TextView textStatus_text;
    private final TextView textSum;
    private final TextView textGoods_money;
    private final TextView textOrderAmount;
    private final TextView textSumDes;
    private final TextView textBtn;
    private final Button buttonSure;
    private final ListView listView;
    private UserOrder.ListBean data;
    private final View viewBtn;

    public DingDanViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textOrderSn = $(R.id.textOrderSn);
        textStatus_text = $(R.id.textStatus_text);
        textSum = $(R.id.textSum);
        textGoods_money = $(R.id.textGoods_money);
        textOrderAmount = $(R.id.textOrderAmount);
        textSumDes = $(R.id.textSumDes);
        textBtn = $(R.id.textBtn);
        buttonSure = $(R.id.buttonSure);
        listView = $(R.id.listView);
        viewBtn = $(R.id.viewBtn);
    }

    @Override
    public void setData(UserOrder.ListBean data) {
        super.setData(data);
        this.data = data;
        textOrderSn.setText(data.getOrderSn());
        textStatus_text.setText(data.getStatus_text());
        textSum.setText("¥" + data.getSum());
        textGoods_money.setText("¥" + data.getGoods_money());
        textOrderAmount.setText("¥" + data.getOrderAmount());
        textSumDes.setText(data.getSumDes());
        if (data.getIs_cancle() == 1) {
            textBtn.setText("取消订单");
        }
        if (data.getIs_pay() == 1) {
            buttonSure.setText("确认付款");
        }
        if (data.getIs_confirm() == 1) {
            buttonSure.setText("确认收货");
        }
        if (data.getIs_del() == 1) {
            textBtn.setText("删除订单");
        }
        if (data.getIs_del() == 0 && data.getIs_del() == 0) {
            textBtn.setVisibility(View.GONE);
        }
        if (data.getIs_confirm() == 0 && data.getIs_pay() == 0) {
            buttonSure.setVisibility(View.GONE);
        }
        if (data.getIs_confirm() == 0 && data.getIs_pay() == 0 && data.getIs_del() == 0 && data.getIs_del() == 0) {
            viewBtn.setVisibility(View.GONE);
        }
        listView.setAdapter(new MyAdapter());
    }

    class MyAdapter extends BaseAdapter {
        class ViewHolder {
            public ImageView imageGoods_img;
            public TextView textGoods_title;
            public TextView textSpe_name;
            public TextView textNum;
            public TextView textGoods_price;
            public TextView textGoods_money;
        }

        @Override
        public int getCount() {
            return data.getOg().size();
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_dingdan, null);
                holder.imageGoods_img = (ImageView) convertView.findViewById(R.id.imageGoods_img);
                holder.textGoods_title = (TextView) convertView.findViewById(R.id.textGoods_title);
                holder.textSpe_name = (TextView) convertView.findViewById(R.id.textSpe_name);
                holder.textNum = (TextView) convertView.findViewById(R.id.textNum);
                holder.textGoods_price = (TextView) convertView.findViewById(R.id.textGoods_price);
                holder.textGoods_money = (TextView) convertView.findViewById(R.id.textGoods_money);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Glide.with(getContext())
                    .load(data.getOg().get(position).getGoods_img())
                    .placeholder(R.mipmap.ic_empty)
                    .into(holder.imageGoods_img);
            holder.textGoods_title.setText(data.getOg().get(position).getGoods_name());
            holder.textSpe_name.setText(data.getOg().get(position).getGoods_desc());
            holder.textNum.setText("×" + data.getOg().get(position).getQuantity());
            holder.textGoods_price.setText("¥" + data.getOg().get(position).getGoods_price());
            holder.textGoods_money.setText("赚" + data.getOg().get(position).getGoods_money());
            return convertView;
        }
    }

}
