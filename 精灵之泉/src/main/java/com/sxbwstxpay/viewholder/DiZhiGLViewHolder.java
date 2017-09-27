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
import com.sxbwstxpay.activity.DiZhiGLActivity;
import com.sxbwstxpay.activity.XinZengDZActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.model.UserAddress;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;

import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class DiZhiGLViewHolder extends BaseViewHolder<UserAddress.DataBean> {

    private final TextView textConsignee;
    private final TextView textPhone;
    private final TextView textAreaAddress;
    private final ImageView imageDefa;
    private UserAddress.DataBean data;

    public DiZhiGLViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textConsignee = $(R.id.textConsignee);
        textPhone = $(R.id.textPhone);
        textAreaAddress = $(R.id.textAreaAddress);
        imageDefa = $(R.id.imageDefa);
        $(R.id.viewDefa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getDefa() != 1) {
                    moRen();
                }
            }
        });
        $(R.id.buttonBianJi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.value, data);
                intent.setClass(getContext(), XinZengDZActivity.class);
                getContext().startActivity(intent);
            }
        });
        $(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("确定删除该收货地址吗?")
                        .setNegativeButton("否",null)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delete();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObjectDelete() {
        String url = Constant.HOST + Constant.Url.USER_DELADDRESS;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid",((DiZhiGLActivity) getContext()).userInfo.getUid());
        params.put("tokenTime",((DiZhiGLActivity) getContext()).tokenTime);
        params.put("id",data.getId());
        return new OkObject(params, url);
    }

    /**
     * des： 删除收货地址
     * author： ZhangJieBo
     * date： 2017/9/27 0027 下午 3:27
     */
    private void delete() {
        ((DiZhiGLActivity) getContext()).showLoadingDialog();
       ApiClient.post(getContext(), getOkObjectDelete(), new ApiClient.CallBack() {
           @Override
           public void onSuccess(String s) {
               ((DiZhiGLActivity) getContext()).cancelLoadingDialog();
               LogUtil.LogShitou("DiZhiGLViewHolder--删除地址", s+"");
               try {
                   SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                   if (simpleInfo.getStatus()==1){
                       ((DiZhiGLActivity) getContext()).adapter.remove(getDataPosition());
                   }else if (simpleInfo.getStatus()==2){
                       MyDialog.showReLoginDialog(getContext());
                   }else {
                       Toast.makeText(getContext(), simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                   }
               } catch (Exception e) {
                   Toast.makeText(getContext(),"数据出错", Toast.LENGTH_SHORT).show();
               }
           }
       
           @Override
           public void onError(Response response) {
               ((DiZhiGLActivity) getContext()).cancelLoadingDialog();
               Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
           }
       }); 
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_ADDRESSDEFAULT;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", ((DiZhiGLActivity) getContext()).userInfo.getUid());
        params.put("tokenTime", ((DiZhiGLActivity) getContext()).tokenTime);
        params.put("id", data.getId());
        return new OkObject(params, url);
    }

    /**
     * des： 设置默认地址
     * author： ZhangJieBo
     * date： 2017/9/27 0027 下午 2:33
     */
    private void moRen() {
        ((DiZhiGLActivity) getContext()).showLoadingDialog();
        ApiClient.post(getContext(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                ((DiZhiGLActivity) getContext()).cancelLoadingDialog();
                LogUtil.LogShitou("DiZhiGLViewHolder--设置默认地址", s + "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus() == 1) {
                        ((DiZhiGLActivity) getContext()).onRefresh();
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
                ((DiZhiGLActivity) getContext()).cancelLoadingDialog();
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setData(UserAddress.DataBean data) {
        super.setData(data);
        this.data = data;
        textConsignee.setText(data.getConsignee());
        textPhone.setText(data.getPhone());
        textAreaAddress.setText(data.getArea() + "-" + data.getAddress());
        if (data.getDefa() == 1) {
            imageDefa.setImageResource(R.mipmap.xuanzhong);
        } else {
            imageDefa.setImageResource(R.mipmap.weixuanzhong);
        }
    }

}
