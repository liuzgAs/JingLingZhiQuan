package com.sxbwstxpay.viewholder;

import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.GuanLiWDDPActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.IndexDataBean;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;

import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class WoDeDPGLViewHolder extends BaseViewHolder<IndexDataBean> {

    private final ImageView imageRecom_img;
    private final TextView textStock_num;
    private final TextView textTitle;
    private final TextView textPrice;
    private final TextView textGoods_money;
    private final Button buttonShangJia;
    private IndexDataBean data;

    public WoDeDPGLViewHolder(ViewGroup parent, @LayoutRes int res,int type) {
        super(parent, res);
        imageRecom_img = $(R.id.imageRecom_img);
        textStock_num = $(R.id.textStock_num);
        textTitle = $(R.id.textTitle);
        textPrice = $(R.id.textPrice);
        textGoods_money = $(R.id.textGoods_money);
        buttonShangJia = $(R.id.buttonShangJia);
        $(R.id.imageShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GuanLiWDDPActivity) getContext()).share(data.getId(),"goods",data.getShare());
            }
        });
        if (type==0){
            buttonShangJia.setVisibility(View.VISIBLE);
            buttonShangJia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("提示")
                            .setMessage("确定要下架该商品吗？")
                            .setNegativeButton("取消",null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((GuanLiWDDPActivity) getContext()).showLoadingDialog();
                                    ApiClient.post(getContext(), getOkObject1(), new ApiClient.CallBack() {
                                        @Override
                                        public void onSuccess(String s) {
                                            ((GuanLiWDDPActivity) getContext()).cancelLoadingDialog();
                                            LogUtil.LogShitou("XianShiQGViewHolder--商品下架", s + "");
                                            try {
                                                SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                                                if (simpleInfo.getStatus() == 1) {
                                                    ((GuanLiWDDPActivity) getContext()).adapter.remove(getDataPosition());
                                                } else if (simpleInfo.getStatus() == 2) {
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
                                            ((GuanLiWDDPActivity) getContext()).cancelLoadingDialog();
                                            Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .create()
                            .show();
                }
            });
        }else {
            buttonShangJia.setVisibility(View.GONE);
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject1() {
        String url = Constant.HOST + Constant.Url.INDEX_DOWNGOODS;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", ((GuanLiWDDPActivity) getContext()).userInfo.getUid());
        params.put("tokenTime", ((GuanLiWDDPActivity) getContext()).tokenTime);
        params.put("id", data.getId());
        return new OkObject(params, url);
    }

    @Override
    public void setData(IndexDataBean data) {
        super.setData(data);
        this.data = data;
        Glide.with(getContext())
                .load(data.getRecom_img())
                .asBitmap()
                .placeholder(R.mipmap.ic_empty)
                .into(imageRecom_img);
        textStock_num.setText("库存" + data.getStock_num());
        textTitle.setText(data.getTitle());
        textPrice.setText("¥" + data.getPrice());
        textGoods_money.setText("赚" + data.getGoods_money());
        buttonShangJia.setText("一");
    }


}
