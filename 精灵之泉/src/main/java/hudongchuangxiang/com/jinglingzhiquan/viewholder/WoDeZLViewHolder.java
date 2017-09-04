package hudongchuangxiang.com.jinglingzhiquan.viewholder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.activity.DiZhiGLActivity;

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
    }

    @Override
    public void setData(Integer data) {
        super.setData(data);
    }
    
}
