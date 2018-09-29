package com.sxbwstxpay.viewholder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.MainActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.fragment.GouWuCheFragment;
import com.sxbwstxpay.model.CartIndex;
import com.sxbwstxpay.model.CartUpdatecart;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;

import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class GouWuCXViewHolder extends BaseViewHolder<CartIndex.CartBean> {

    private final ImageView imageXuanZhong;
    private final ImageView imageDelete;
    private final ImageView imageGoods_img;
    private final TextView textGoods_title;
    private final TextView textSpe_name;
    private final TextView textGoods_price;
    private final TextView textNum;
    private CartIndex.CartBean data;
    private GouWuCheFragment fragment;
    public GouWuCXViewHolder(ViewGroup parent, @LayoutRes int res,GouWuCheFragment fragmentt) {
        super(parent, res);
        this.fragment=fragmentt;
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
                fragment.quanXuan();
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
        GlideApp.with(getContext())
                .asBitmap()
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
        params.put("uid",fragment.userInfo.getUid());
        params.put("tokenTime",fragment.tokenTime);
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
                        ((MainActivity) getContext()).showLoadingDialog();
                        ApiClient.post(getContext(), getOkObject1(), new ApiClient.CallBack() {
                            @Override
                            public void onSuccess(String s) {
                                fragment.cancelLoadingDialog();
                                LogUtil.LogShitou("GouWuCViewHolder--删除购物车", s+"");
                                try {
                                    CartUpdatecart cartUpdatecart = GsonUtils.parseJSON(s, CartUpdatecart.class);
                                    if (cartUpdatecart.getStatus()==1){
                                        fragment.remove(getDataPosition());
                                        fragment.setSum(cartUpdatecart.getSum());
                                        Intent intent = new Intent();
                                        intent.setAction(Constant.BROADCASTCODE.GouWuCheNum);
                                        getContext().sendBroadcast(intent);
                                    }else if (cartUpdatecart.getStatus()==3){
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
                                fragment.cancelLoadingDialog();
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
        params.put("uid",fragment.userInfo.getUid());
        params.put("tokenTime",fragment.tokenTime);
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
        fragment.showLoadingDialog();
        ApiClient.post(getContext(), getOkObject(num), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                fragment.cancelLoadingDialog();
                LogUtil.LogShitou("GouWuCViewHolder--购物车更新",s+ "");
                try {
                    CartUpdatecart cartUpdatecart = GsonUtils.parseJSON(s, CartUpdatecart.class);
                    if (cartUpdatecart.getStatus()==1){
                        data.setNum(num);
                        textNum.setText(num+"");
                        fragment.quanXuan();
                    }else if (cartUpdatecart.getStatus()==3){
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
                fragment.cancelLoadingDialog();
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
