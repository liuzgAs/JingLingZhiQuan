package com.sxbwstxpay.viewholder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.DingDanXQActivity;
import com.sxbwstxpay.activity.ShangChengDDActivity;
import com.sxbwstxpay.activity.WebActivity;
import com.sxbwstxpay.activity.ZhiFuXActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.model.UserOrder;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;

import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class DingDanViewHolder extends BaseViewHolder<UserOrder.ListBean> {


    private final TextView textOrderSn;
    private final TextView textStatus_text;
    private final TextView textOrderAmount;
    private final TextView textSumDes;
    private final TextView textBtn;
    private final Button buttonSure;
    private final ListView listView;
    private final ListView listView1;
    private UserOrder.ListBean data;
    private final View viewBtn;

    public DingDanViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textOrderSn = $(R.id.textOrderSn);
        textStatus_text = $(R.id.textStatus_text);
        textOrderAmount = $(R.id.textOrderAmount);
        textSumDes = $(R.id.textSumDes);
        textBtn = $(R.id.textBtn);
        buttonSure = $(R.id.buttonSure);
        listView = $(R.id.listView);
        listView1 = $(R.id.listView1);
        viewBtn = $(R.id.viewBtn);
        buttonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getIs_pay() == 1) {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), ZhiFuXActivity.class);
                    intent.putExtra(Constant.INTENT_KEY.id, Integer.parseInt(data.getId()));
                    getContext().startActivity(intent);
                }
                if (data.getIs_confirm() == 1) {
                    dingDanCaoZuo("confirm");
                }
            }
        });
        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getIs_cancle() == 1) {
                    dingDanCaoZuo("cancle");
                }
                if (data.getIs_del() == 1) {
                    dingDanCaoZuo("del");
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.id, data.getId());
                intent.setClass(getContext(), DingDanXQActivity.class);
                getContext().startActivity(intent);
            }
        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.id, data.getId());
                intent.setClass(getContext(), DingDanXQActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject(String type) {
        String url = Constant.HOST + Constant.Url.USER_ORDERDONE;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", ((ShangChengDDActivity) getContext()).userInfo.getUid());
        params.put("tokenTime", ((ShangChengDDActivity) getContext()).tokenTime);
        params.put("id", data.getId());
        params.put("type", type);
        return new OkObject(params, url);
    }

    /**
     * des： 订单操作
     * author： ZhangJieBo
     * date： 2017/9/28 0028 上午 11:11
     */
    private void dingDanCaoZuo(final String type) {
        String message = "";
        switch (type) {
            case "confirm":
                message = "是否确认订单？";
                break;
            case "cancle":
                message = "确认取消订单吗？";
                break;
            case "del":
                message = "确认删除订单吗？";
                break;
        }
        new AlertDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage(message)
                .setNegativeButton("取消", null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((ShangChengDDActivity) getContext()).showLoadingDialog();
                        ApiClient.post(getContext(), getOkObject(type), new ApiClient.CallBack() {
                            @Override
                            public void onSuccess(String s) {
                                ((ShangChengDDActivity) getContext()).cancelLoadingDialog();
                                LogUtil.LogShitou("DingDanViewHolder--订单操作", s + "");
                                try {
                                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                                    if (simpleInfo.getStatus() == 1) {
                                        Intent intent = new Intent();
                                        intent.setAction(Constant.BROADCASTCODE.ShuaXinDingDan);
                                        getContext().sendBroadcast(intent);
                                    } else if (simpleInfo.getStatus() == 3) {
                                        MyDialog.showReLoginDialog(getContext());
                                    } else {
                                        Toast.makeText(getContext(), simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), "数据出错", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Response response) {
                                ((ShangChengDDActivity) getContext()).cancelLoadingDialog();
                                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .create()
                .show();
    }

    @Override
    public void setData(UserOrder.ListBean data) {
        super.setData(data);
        this.data = data;
        textOrderSn.setText(data.getOrderSn());
        textStatus_text.setText(data.getStatus_text());
        textOrderAmount.setText("¥" + data.getOrderAmount());
        textSumDes.setText(data.getSumDes());
        if (data.getIs_cancle() == 1) {
            textBtn.setText("取消订单");
        }
        if (data.getIs_pay() == 1) {
            buttonSure.setText("去付款");
        }
        if (data.getIs_confirm() == 1) {
            buttonSure.setText("确认收货");
        }
        if (data.getIs_del() == 1) {
            textBtn.setText("删除订单");
        }
        if (data.getIs_del() == 0 && data.getIs_cancle() == 0) {
            textBtn.setVisibility(View.GONE);
        }
        if (data.getIs_confirm() == 0 && data.getIs_pay() == 0) {
            buttonSure.setVisibility(View.GONE);
        }
        if (data.getIs_confirm() == 0 && data.getIs_pay() == 0 && data.getIs_del() == 0 && data.getIs_cancle() == 0) {
            viewBtn.setVisibility(View.GONE);
        }
        if (data.getOg()!=null){
            listView.setAdapter(new MyAdapter());
        }
        listView1.setAdapter(new MyAdapter1());

    }

    class MyAdapter extends BaseAdapter {
        class ViewHolder {
            public ImageView imageGoods_img;
            public TextView textGoods_title;
            public TextView textSpe_name;
            public TextView textNum;
            public TextView textGoods_price;
            public TextView textGoods_money;
            public TextView buttonWuLiu;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
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
                holder.buttonWuLiu = (TextView) convertView.findViewById(R.id.buttonWuLiu);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            GlideApp.with(getContext())
                    .asBitmap()
                    .load(data.getOg().get(position).getGoods_img())
                    .placeholder(R.mipmap.ic_empty)
                    .into(holder.imageGoods_img);
            holder.textGoods_title.setText(data.getOg().get(position).getGoods_name());
            holder.textSpe_name.setText(data.getOg().get(position).getGoods_desc());
            holder.textNum.setText("×" + data.getOg().get(position).getQuantity());
            holder.textGoods_price.setText("¥" + data.getOg().get(position).getGoods_price());
            holder.textGoods_money.setText("赚" + data.getOg().get(position).getGoods_money());
            holder.buttonWuLiu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(getContext(), WuLiuXQActivity.class);
//                getContext().startActivity(intent);
                    Intent intent = new Intent();
                    intent.setClass(getContext(), WebActivity.class);
                    intent.putExtra(Constant.INTENT_KEY.TITLE, data.getOg().get(position).getShip_title());
                    intent.putExtra(Constant.INTENT_KEY.URL, data.getOg().get(position).getShip_url());
                    getContext().startActivity(intent);
                }
            });
            if (data.getOg().get(position).getIs_ship()==1){
                holder.buttonWuLiu.setVisibility(View.VISIBLE);
            }else {
                holder.buttonWuLiu.setVisibility(View.GONE);
            }
            return convertView;
        }
    }

    class MyAdapter1 extends BaseAdapter {
        class ViewHolder {
            public TextView textN;
            public TextView textV;
        }

        @Override
        public int getCount() {
            return data.getDes().size();
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
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_dingdanmx, null);
                holder.textN = (TextView) convertView.findViewById(R.id.textN);
                holder.textV = (TextView) convertView.findViewById(R.id.textV);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textN.setText( data.getDes().get(position).getN());
            holder.textV.setText( data.getDes().get(position).getV());
            return convertView;
        }
    }
}
