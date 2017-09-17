package com.sxbwstxpay.viewholder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.DiZhiGLActivity;
import com.sxbwstxpay.activity.EditActivity;
import com.sxbwstxpay.activity.WeiXinMPMaActivity;
import com.sxbwstxpay.activity.WoDeZLActivity;
import com.sxbwstxpay.base.ToLoginActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.UserProfile;

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
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ToLoginActivity.toLoginActivity(getContext());
                            }
                        })
                        .create()
                        .show();
            }
        });
        $(R.id.viewTouXiang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WoDeZLActivity) getContext()).chooseHead();
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
