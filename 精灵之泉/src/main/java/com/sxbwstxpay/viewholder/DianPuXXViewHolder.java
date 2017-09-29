package com.sxbwstxpay.viewholder;

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

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.DianPuXXActivity;
import com.sxbwstxpay.activity.EditActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.StoreStoreinfo;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class DianPuXXViewHolder extends BaseViewHolder<StoreStoreinfo> {

    private final TextView textName;
    private final TextView textIntro;
    private final ImageView imageLogo;
    private final ImageView imageBanner;
    private final ImageView imageWx;
    private StoreStoreinfo data;

    public DianPuXXViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textName = $(R.id.textName);
        textIntro = $(R.id.textIntro);
        imageLogo = $(R.id.imageLogo);
        imageBanner = $(R.id.imageBanner);
        imageWx = $(R.id.imageWx);
        $(R.id.viewDianMing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), EditActivity.class);
                intent.putExtra(Constant.INTENT_KEY.type, 5);
                intent.putExtra(Constant.INTENT_KEY.value,data.getName() );
                ((DianPuXXActivity) getContext()).startActivityForResult(intent, Constant.REQUEST_RESULT_CODE.BaoCun);
            }
        });
        $(R.id.viewDianPuJS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), EditActivity.class);
                intent.putExtra(Constant.INTENT_KEY.type, 6);
                intent.putExtra(Constant.INTENT_KEY.value, data.getIntro());
                ((DianPuXXActivity) getContext()).startActivityForResult(intent, Constant.REQUEST_RESULT_CODE.BaoCun);
            }
        });
        $(R.id.viewDianPuLOGO).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(data.getLogo())){
                    ((DianPuXXActivity) getContext()).chooseHead();
                }else {
                    View dialog_tu_pian = LayoutInflater.from(getContext()).inflate(R.layout.dialog_tu_pian, null);
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.dialog)
                            .setView(dialog_tu_pian)
                            .create();
                    dialog_tu_pian.findViewById(R.id.textChaKan).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            MyDialog.showPicDialog(getContext(),data.getLogo());
                        }
                    });dialog_tu_pian.findViewById(R.id.textShangChuan).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            ((DianPuXXActivity) getContext()).chooseHead();
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
        $(R.id.viewDianZhao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(data.getWx())) {
                    ((DianPuXXActivity) getContext()).chooseDianZhao();
                } else {
                    View dialog_tu_pian = LayoutInflater.from(getContext()).inflate(R.layout.dialog_tu_pian, null);
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.dialog)
                            .setView(dialog_tu_pian)
                            .create();
                    dialog_tu_pian.findViewById(R.id.textChaKan).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            MyDialog.showPicDialog(getContext(),data.getBanner());
                        }
                    });dialog_tu_pian.findViewById(R.id.textShangChuan).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            ((DianPuXXActivity) getContext()).chooseDianZhao();
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
                    ((DianPuXXActivity) getContext()).chooseWX();
                } else {
                    View dialog_tu_pian = LayoutInflater.from(getContext()).inflate(R.layout.dialog_tu_pian, null);
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.dialog)
                            .setView(dialog_tu_pian)
                            .create();
                    dialog_tu_pian.findViewById(R.id.textChaKan).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            MyDialog.showPicDialog(getContext(),data.getWx());
                        }
                    });dialog_tu_pian.findViewById(R.id.textShangChuan).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            ((DianPuXXActivity) getContext()).chooseWX();
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
    }

    @Override
    public void setData(StoreStoreinfo data) {
        super.setData(data);
        this.data = data;
        textName.setText(data.getName());
        textIntro.setText(data.getIntro());
        Glide.with(getContext())
                .load(data.getLogo())
                .placeholder(R.mipmap.ic_empty)
                .into(imageLogo);
        Glide.with(getContext())
                .load(data.getBanner())
                .placeholder(R.mipmap.ic_empty)
                .into(imageBanner);
        Glide.with(getContext())
                .load(data.getWx())
                .placeholder(R.mipmap.ic_empty)
                .into(imageWx);
    }

}
