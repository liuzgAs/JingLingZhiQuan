package com.sxbwstxpay.viewholder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.DiZhiGLActivity;
import com.sxbwstxpay.activity.EditActivity;
import com.sxbwstxpay.activity.WeiXinMPMaActivity;
import com.sxbwstxpay.activity.WoDeZLActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ToLoginActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.model.UserProfile;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;

import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class WoDeZLViewHolder extends BaseViewHolder<UserProfile> {

    private final ImageView imageHeadImg;
    private final ImageView imageWx;
    private final TextView textNickName;
    private final TextView textBirthday;
    private final TextView textSex;
    private final TextView textArea;
    private final TextView textMobile;
    private final TextView textWx;
    private UserProfile data;

    public WoDeZLViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageHeadImg = $(R.id.imageHeadImg);
        imageWx = $(R.id.imageWx);
        textNickName = $(R.id.textNickName);
        textBirthday = $(R.id.textBirthday);
        textSex = $(R.id.textSex);
        textArea = $(R.id.textArea);
        textMobile = $(R.id.textMobile);
        textWx = $(R.id.textWx);
        $(R.id.viewDiZhiGL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), DiZhiGLActivity.class);
                getContext().startActivity(intent);
            }
        });
        $(R.id.buttonExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("确定要退出吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            /**
                             * des： 网络请求参数
                             * author： ZhangJieBo
                             * date： 2017/8/28 0028 上午 9:55
                             */
                            private OkObject getOkObject() {
                                String url = Constant.HOST + Constant.Url.LOGIN_LOGOUT;
                                HashMap<String, String> params = new HashMap<>();
                                params.put("uid",((WoDeZLActivity)getContext()).userInfo.getUid());
                                params.put("tokenTime",((WoDeZLActivity)getContext()).tokenTime);
                                return new OkObject(params, url);
                            }
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((WoDeZLActivity)getContext()).showLoadingDialog();
                                ApiClient.post(getContext(), getOkObject(), new ApiClient.CallBack() {
                                    @Override
                                    public void onSuccess(String s) {
                                        ((WoDeZLActivity)getContext()).cancelLoadingDialog();
                                        LogUtil.LogShitou("WoDeZLViewHolder--注销", s+"");
                                        try {
                                            ToLoginActivity.toLoginActivity(getContext());
                                            SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                                            if (simpleInfo.getStatus()==1){
                                            }else if (simpleInfo.getStatus()==2){
                                                MyDialog.showReLoginDialog(getContext());
                                            }else {
                                                Toast.makeText(getContext(), simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception e) {
                                            ToLoginActivity.toLoginActivity(getContext());
                                            Toast.makeText(getContext(),"数据出错", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(Response response) {
                                        ((WoDeZLActivity)getContext()).cancelLoadingDialog();
                                        ToLoginActivity.toLoginActivity(getContext());
                                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .create()
                        .show();
            }
        });
        $(R.id.viewTouXiang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(data.getHeadImg())){
                    ((WoDeZLActivity) getContext()).chooseHead();
                }else {
                    View dialog_tu_pian = LayoutInflater.from(getContext()).inflate(R.layout.dialog_tu_pian, null);
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.dialog)
                            .setView(dialog_tu_pian)
                            .create();
                    dialog_tu_pian.findViewById(R.id.textChaKan).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            MyDialog.showPicDialog(getContext(),data.getHeadImg());
                        }
                    });dialog_tu_pian.findViewById(R.id.textShangChuan).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            ((WoDeZLActivity) getContext()).chooseHead();
                        }
                    });dialog_tu_pian.findViewById(R.id.textQuXiao).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                    Window dialogWindow = alertDialog.getWindow();
                    dialogWindow.setGravity(Gravity.BOTTOM);
                    dialogWindow.setWindowAnimations(R.style.dialogFenXiang);
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    DisplayMetrics d = getContext().getResources().getDisplayMetrics(); // 获取屏幕宽、高用
                    lp.width = (int) (d.widthPixels * 1); // 高度设置为屏幕的0.6
                    dialogWindow.setAttributes(lp);
                }
            }
        });
        $(R.id.viewWX).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(data.getWx())) {
                    ((WoDeZLActivity) getContext()).chooseWX();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), WeiXinMPMaActivity.class);
                    intent.putExtra(Constant.INTENT_KEY.img, data.getWx());
                    intent.putExtra(Constant.INTENT_KEY.TITLE,"我的微信名片");
                    getContext().startActivity(intent);
                }
            }
        });
        $(R.id.viewNickName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), EditActivity.class);
                intent.putExtra(Constant.INTENT_KEY.type, 1);
                intent.putExtra(Constant.INTENT_KEY.value,data.getNickName());
                ((WoDeZLActivity)getContext()).startActivityForResult(intent);
            }
        });
        $(R.id.viewShengRi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), EditActivity.class);
                intent.putExtra(Constant.INTENT_KEY.type, 2);
                intent.putExtra(Constant.INTENT_KEY.value,data.getBirthday());
                ((WoDeZLActivity)getContext()).startActivityForResult(intent);
            }
        });
        $(R.id.viewSex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), EditActivity.class);
                intent.putExtra(Constant.INTENT_KEY.type, 3);
                intent.putExtra(Constant.INTENT_KEY.value,data.getSex());
                ((WoDeZLActivity)getContext()).startActivityForResult(intent);
            }
        });
        $(R.id.viewDiQu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), EditActivity.class);
                intent.putExtra(Constant.INTENT_KEY.type, 4);
                intent.putExtra(Constant.INTENT_KEY.value,data.getArea());
                ((WoDeZLActivity)getContext()).startActivityForResult(intent);
            }
        });
    }

    @Override
    public void setData(UserProfile data) {
        super.setData(data);
        this.data = data;
        Glide.with(getContext())
                .load(data.getHeadImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageHeadImg);
        if (TextUtils.isEmpty(data.getWx())) {
            textWx.setVisibility(View.VISIBLE);
            imageWx.setVisibility(View.GONE);
        } else {
            imageWx.setVisibility(View.VISIBLE);
            textWx.setVisibility(View.GONE);
            Glide.with(getContext())
                    .load(data.getWx())
                    .placeholder(R.mipmap.ic_empty)
                    .into(imageWx);
        }
        textNickName.setText(data.getNickName());
        textBirthday.setText(data.getBirthday());
        textSex.setText(data.getSex());
        textArea.setText(data.getArea());
        textMobile.setText(data.getMobile());
    }

}
