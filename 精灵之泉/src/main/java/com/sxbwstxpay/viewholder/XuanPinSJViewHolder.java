package com.sxbwstxpay.viewholder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.MainActivity;
import com.sxbwstxpay.activity.ShangPinScActivity;
import com.sxbwstxpay.activity.SouSuoActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.IndexDataBean;
import com.sxbwstxpay.model.IndexUpgoods;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;

import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class XuanPinSJViewHolder extends BaseViewHolder<IndexDataBean> {

    private final ImageView imageImg;
    private final TextView textTitle;
    private final TextView textPrice;
    private final TextView textGoods_money;
    private final TextView textStock_num;
    private final Button buttonAct;
    private IndexDataBean data;

    public XuanPinSJViewHolder(ViewGroup parent, @LayoutRes int res,final String activity) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textTitle = $(R.id.textTitle);
        textPrice = $(R.id.textPrice);
        textGoods_money = $(R.id.textGoods_money);
        textStock_num = $(R.id.textStock_num);
        buttonAct = $(R.id.buttonAct);
        $(R.id.imageSuCai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.id,data.getId());
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
                switch (activity) {
                    case "MainActivity":
                        ((MainActivity) getContext()).showLoadingDialog();
                        break;
                    case "SouSuoActivity":
                        ((SouSuoActivity) getContext()).showLoadingDialog();
                        break;
                }
                ApiClient.post(getContext(), getOkObject1(activity), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        switch (activity) {
                            case "MainActivity":
                                ((MainActivity) getContext()).cancelLoadingDialog();
                                break;
                            case "SouSuoActivity":
                                ((SouSuoActivity) getContext()).cancelLoadingDialog();
                                break;
                        }
                        LogUtil.LogShitou("XianShiQGViewHolder--商品上架", s+"");
                        try {
                            IndexUpgoods indexUpgoods = GsonUtils.parseJSON(s, IndexUpgoods.class);
                            if (indexUpgoods.getStatus()==1){
                                buttonAct.setText("√");
                                data.setAct(1);
                                Intent intent = new Intent();
                                intent.putExtra(Constant.INTENT_KEY.value,indexUpgoods.getG_num());
                                intent.setAction(Constant.BROADCASTCODE.ShangJia02);
                                getContext().sendBroadcast(intent);
                            }else if (indexUpgoods.getStatus()==2){
                                MyDialog.showReLoginDialog(getContext());
                            }else {
                                Toast.makeText(getContext(), indexUpgoods.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(),"数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        switch (activity) {
                            case "MainActivity":
                                ((MainActivity) getContext()).cancelLoadingDialog();
                                break;
                            case "SouSuoActivity":
                                ((SouSuoActivity) getContext()).cancelLoadingDialog();
                                break;
                        }
                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject1(String activity) {
        String url = Constant.HOST + Constant.Url.INDEX_UPGOODS;
        HashMap<String, String> params = new HashMap<>();
        switch (activity) {
            case "MainActivity":
                params.put("uid", ((MainActivity) getContext()).userInfo.getUid());
                params.put("tokenTime", ((MainActivity) getContext()).tokenTime);
                break;
            case "SouSuoActivity":
                params.put("uid", ((SouSuoActivity) getContext()).userInfo.getUid());
                params.put("tokenTime", ((SouSuoActivity) getContext()).tokenTime);
                break;
        }
        params.put("id",data.getId());
        return new OkObject(params, url);
    }

    @Override
    public void setData(IndexDataBean data) {
        super.setData(data);
        this.data=data;
        Glide.with(getContext())
                .load(data.getImg())
                .asBitmap()
                .placeholder(R.mipmap.ic_empty01)
                .into(imageImg);
        textTitle.setText(data.getTitle());
        textPrice.setText("¥"+data.getPrice());
        textGoods_money.setText("赚"+data.getGoods_money());
        textStock_num.setText("库存"+data.getStock_num());
        if (data.getAct()==1){
            buttonAct.setText("√");
        }else {
            buttonAct.setText("+");
        }
    }
    
}
