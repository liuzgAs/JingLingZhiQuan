package hudongchuangxiang.com.jinglingzhiquan.viewholder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.activity.DiZhiGLActivity;
import hudongchuangxiang.com.jinglingzhiquan.base.ToLoginActivity;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class WoDeZLViewHolder extends BaseViewHolder<Integer> {

    public WoDeZLViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
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
    }

    @Override
    public void setData(Integer data) {
        super.setData(data);
    }

}
