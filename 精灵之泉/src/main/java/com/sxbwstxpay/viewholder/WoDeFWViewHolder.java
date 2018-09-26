package com.sxbwstxpay.viewholder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.FaBuFWActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.CommonlyUsed;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SkillMy;
import com.sxbwstxpay.model.UserInfo;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;

import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class WoDeFWViewHolder extends BaseViewHolder<SkillMy.DataBean> {
    private final ImageView imageImg;
    private final TextView textTitle;
    private final TextView textPrice;
    private final ImageView imageState;
    private final TextView text1;
    private final TextView text2;
    private final TextView text3;

    public WoDeFWViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textTitle = $(R.id.textTitle);
        textPrice = $(R.id.textPrice);
        imageState = $(R.id.imageState);
        text1 = $(R.id.text1);
        text2 = $(R.id.text2);
        text3 = $(R.id.text3);
    }

    @Override
    public void setData(final SkillMy.DataBean data) {
        super.setData(data);
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textTitle.setText(data.getName());
        textPrice.setText(data.getDes());
        if ("0".equals(data.getState())) {
            imageState.setImageResource(R.mipmap.fuwu_shz);
        } else if ("10".equals(data.getState())) {
            imageState.setImageResource(R.mipmap.fuwu_tg);
        } else if ("20".equals(data.getState())) {
            imageState.setImageResource(R.mipmap.fuwu_sb);
        }
        text2.setText(data.getCloseText());
        if (data.getIsDel() == 1) {
            text1.setVisibility(View.VISIBLE);
        } else {
            text1.setVisibility(View.GONE);
        }
        if (data.getIsEdit() == 1) {
            text3.setVisibility(View.VISIBLE);
        } else {
            text3.setVisibility(View.GONE);
        }
        if (data.getIsClose() == 1) {
            text2.setVisibility(View.VISIBLE);
        } else {
            text2.setVisibility(View.GONE);
        }
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(data.getId());
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnOff(data.getId());
            }
        });
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FaBuFWActivity.class);
                intent.putExtra(Constant.INTENT_KEY.id, data.getId());
                getContext().startActivity(intent);
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObjectD(String id) {
        String url = Constant.HOST + Constant.Url.DELSKILL;
        HashMap<String, String> params = new HashMap<>();
        ACache aCache = ACache.get(getContext(), Constant.ACACHE.App);
        UserInfo userInfo = (UserInfo) aCache.getAsObject(Constant.ACACHE.USER_INFO);
        String tokenTime = aCache.getAsString(Constant.ACACHE.TOKENTIME);
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("id", id);
        return new OkObject(params, url);
    }

    protected void delete(String id) {
        ApiClient.post(getContext(), getOkObjectD(id), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("WoDeDPActivity--我的店铺", s + "");
                try {
                    CommonlyUsed commonlyUsed = GsonUtils.parseJSON(s, CommonlyUsed.class);
                    if (commonlyUsed.getStatus() == 1) {
                        Intent data = new Intent();
                        data.setAction(Constant.BROADCASTCODE.CHANGEFW);
                        getContext().sendBroadcast(data);
                    } else if (commonlyUsed.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getContext());
                    } else {
                        Toast.makeText(getContext(), commonlyUsed.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObjectO(String id) {
        String url = Constant.HOST + Constant.Url.ONOFF;
        HashMap<String, String> params = new HashMap<>();
        ACache aCache = ACache.get(getContext(), Constant.ACACHE.App);
        UserInfo userInfo = (UserInfo) aCache.getAsObject(Constant.ACACHE.USER_INFO);
        String tokenTime = aCache.getAsString(Constant.ACACHE.TOKENTIME);
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("id", id);
        return new OkObject(params, url);
    }

    protected void OnOff(String id) {
        ApiClient.post(getContext(), getOkObjectO(id), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("WoDeDPActivity--我的店铺", s + "");
                try {
                    CommonlyUsed commonlyUsed = GsonUtils.parseJSON(s, CommonlyUsed.class);
                    if (commonlyUsed.getStatus() == 1) {
                        Intent data = new Intent();
                        data.setAction(Constant.BROADCASTCODE.CHANGEFW);
                        getContext().sendBroadcast(data);
                    } else if (commonlyUsed.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getContext());
                    } else {
                        Toast.makeText(getContext(), commonlyUsed.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
