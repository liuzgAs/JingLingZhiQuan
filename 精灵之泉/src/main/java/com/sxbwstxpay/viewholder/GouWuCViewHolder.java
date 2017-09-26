package com.sxbwstxpay.viewholder;

import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.GouWuCActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.CartIndex;
import com.sxbwstxpay.model.CartUpdatecart;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;

import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class GouWuCViewHolder extends BaseViewHolder<CartIndex.CartBean> {

    private final ImageView imageXuanZhong;
    private final ImageView imageDelete;
    private final ImageView imageGoods_img;
    private final TextView textGoods_title;
    private final TextView textSpe_name;
    private final TextView textGoods_price;
    private final TextView textNum;
    private CartIndex.CartBean data;

    public GouWuCViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageXuanZhong = $(R.id.imageXuanZhong);
        imageGoods_img = $(R.id.imageGoods_img);
        imageDelete = $(R.id.imageDelete);
        textGoods_title = $(R.id.textGoods_title);
        textSpe_name = $(R.id.textSpe_name);
        textGoods_price = $(R.id.textGoods_price);
        textNum = $(R.id.textNum);
        imageXuanZhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.ischeck()) {
                    data.setIscheck(false);
                    imageXuanZhong.setImageResource(R.mipmap.weixuanzhong);
                } else {
                    data.setIscheck(true);
                    imageXuanZhong.setImageResource(R.mipmap.xuanzhong);
                }
                ((GouWuCActivity) getContext()).quanXuan();
            }
        });
        $(R.id.textDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = data.getNum();
                if (num >1){
                    num--;
                    gengXinNum(num);
                }
            }
        });
        $(R.id.textAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = data.getNum();
                if (num <5000){
                    num++;
                    gengXinNum(num);
                }
            }
        });
        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }

    @Override
    public void setData(CartIndex.CartBean data) {
        super.setData(data);
        this.data = data;
        if (data.ischeck()) {
            imageXuanZhong.setImageResource(R.mipmap.xuanzhong);
        } else {
            imageXuanZhong.setImageResource(R.mipmap.weixuanzhong);
        }
        Glide.with(getContext())
                .load(data.getGoods_img())
                .placeholder(R.mipmap.ic_empty)
                .into(imageGoods_img);
        textGoods_title.setText(data.getGoods_title());
        textSpe_name.setText(data.getSpe_name());
        textGoods_price.setText("¥" + data.getGoods_price());
        textNum.setText(data.getNum()+"");
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject1() {
        String url = Constant.HOST + Constant.Url.CART_DELCART;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid",((GouWuCActivity) getContext()).userInfo.getUid());
        params.put("tokenTime",((GouWuCActivity) getContext()).tokenTime);
        params.put("cartId",data.getId());
        return new OkObject(params, url);
    }

    /**
     * des： 购物车删除
     * author： ZhangJieBo
     * date： 2017/9/26 0026 下午 7:38
     */
    private void delete() {
        new AlertDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage("确定删除该商品吗？")
                .setNegativeButton("否",null)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((GouWuCActivity) getContext()).showLoadingDialog();
                        ApiClient.post(getContext(), getOkObject1(), new ApiClient.CallBack() {
                            @Override
                            public void onSuccess(String s) {
                                ((GouWuCActivity) getContext()).cancelLoadingDialog();
                                LogUtil.LogShitou("GouWuCViewHolder--删除购物车", s+"");
                                try {
                                    CartUpdatecart cartUpdatecart = GsonUtils.parseJSON(s, CartUpdatecart.class);
                                    if (cartUpdatecart.getStatus()==1){
                                        ((GouWuCActivity) getContext()).remove(getDataPosition());
                                        ((GouWuCActivity) getContext()).setSum(cartUpdatecart.getSum());
                                    }else if (cartUpdatecart.getStatus()==2){
                                        MyDialog.showReLoginDialog(getContext());
                                    }else {
                                        Toast.makeText(getContext(), cartUpdatecart.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getContext(),"数据出错", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Response response) {
                                ((GouWuCActivity) getContext()).cancelLoadingDialog();
                                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .create()
                .show();
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject(int num) {
        String url = Constant.HOST + Constant.Url.CART_UPDATECART;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid",((GouWuCActivity) getContext()).userInfo.getUid());
        params.put("tokenTime",((GouWuCActivity) getContext()).tokenTime);
        params.put("cartId",data.getId());
        params.put("num",num+"");
        return new OkObject(params, url);
    }

    /**
     * des：更新购物车商品数量
     * author： ZhangJieBo
     * date： 2017/9/26 0026 下午 6:09
     */
    private void gengXinNum(final int num){
        ((GouWuCActivity) getContext()).showLoadingDialog();
        ApiClient.post(getContext(), getOkObject(num), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                ((GouWuCActivity) getContext()).cancelLoadingDialog();
                LogUtil.LogShitou("GouWuCViewHolder--购物车更新",s+ "");
                try {
                    CartUpdatecart cartUpdatecart = GsonUtils.parseJSON(s, CartUpdatecart.class);
                    if (cartUpdatecart.getStatus()==1){
                        data.setNum(num);
                        textNum.setText(num+"");
                        ((GouWuCActivity) getContext()).setSum(cartUpdatecart.getSum());
                    }else if (cartUpdatecart.getStatus()==2){
                        MyDialog.showReLoginDialog(getContext());
                    }else {
                        Toast.makeText(getContext(), cartUpdatecart.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(),"数据出错", Toast.LENGTH_SHORT).show();
                }
            }
        
            @Override
            public void onError(Response response) {
                ((GouWuCActivity) getContext()).cancelLoadingDialog();
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
