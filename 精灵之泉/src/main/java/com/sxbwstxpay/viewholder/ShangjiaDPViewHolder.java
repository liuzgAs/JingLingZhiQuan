package com.sxbwstxpay.viewholder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.MainActivity;
import com.sxbwstxpay.activity.ShangPinScActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.IndexUpgoods;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SkillDetails;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ShangjiaDPViewHolder extends BaseViewHolder<SkillDetails.DataBean> {

    private final ImageView imageRecom_img;
    private final TextView textStock_num;
    private final TextView textTitle;
    private final TextView textPrice;
    private final TextView textGoods_money;
    private final Button buttonShangJia;
    private SkillDetails.DataBean data;

    public ShangjiaDPViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageRecom_img = $(R.id.imageRecom_img);
        textStock_num = $(R.id.textStock_num);
        textTitle = $(R.id.textTitle);
        textPrice = $(R.id.textPrice);
        textGoods_money = $(R.id.textGoods_money);
        buttonShangJia = $(R.id.buttonShangJia);
        int screenWidth = ScreenUtils.getScreenWidth(getContext());
        ViewGroup.LayoutParams layoutParams = imageRecom_img.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.height = screenWidth/2;
        imageRecom_img.setLayoutParams(layoutParams);
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
                ((MainActivity) getContext()).share(data.getId(), "goods", data.getShare());
            }
        });
        buttonShangJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).showLoadingDialog();
                ApiClient.post(getContext(), getOkObject1(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        ((MainActivity) getContext()).cancelLoadingDialog();
                        LogUtil.LogShitou("XianShiQGViewHolder--商品上架", s + "");
                        try {
                            IndexUpgoods indexUpgoods = GsonUtils.parseJSON(s, IndexUpgoods.class);
                            if (indexUpgoods.getStatus() == 1) {
                                buttonShangJia.setText("√");
                                data.setAct(1);
                                Intent intent = new Intent();
                                intent.putExtra(Constant.INTENT_KEY.value, indexUpgoods.getG_num());
                                intent.setAction(Constant.BROADCASTCODE.ShangJia01);
                                getContext().sendBroadcast(intent);
                            } else if (indexUpgoods.getStatus() == 2) {
                                MyDialog.showReLoginDialog(getContext());
                            } else {
                                Toast.makeText(getContext(), indexUpgoods.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        ((MainActivity) getContext()).cancelLoadingDialog();
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
    private OkObject getOkObject1() {
        String url = Constant.HOST + Constant.Url.INDEX_UPGOODS;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", ((MainActivity) getContext()).userInfo.getUid());
        params.put("tokenTime", ((MainActivity) getContext()).tokenTime);
        params.put("id", data.getId());
        return new OkObject(params, url);
    }

    @Override
    public void setData(SkillDetails.DataBean data) {
        super.setData(data);
        this.data = data;
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getRecom_img())
                .placeholder(R.mipmap.ic_empty)
                .into(imageRecom_img);
        textStock_num.setText("库存" + data.getStock_num());
        textTitle.setText(data.getTitle());
        textPrice.setText("¥" + data.getPrice());
        textGoods_money.setText("赚" + data.getGoods_money());
        if (data.getAct() == 1) {
            buttonShangJia.setText("√");
        } else if (data.getAct() == 2) {
            buttonShangJia.setText("－");
        } else {
            buttonShangJia.setText("＋");
        }
    }


}
