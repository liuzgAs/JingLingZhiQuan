package hudongchuangxiang.com.jinglingzhiquan.viewholder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import hudongchuangxiang.com.jinglingzhiquan.R;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ZhangDanViewHolder extends BaseViewHolder<Boolean> {
    private final ImageView imageZhanKai;
    private boolean data;
    private final View viewDetail;
    private final View viewDuoYu;

    public ZhangDanViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageZhanKai = $(R.id.imageZhanKai);
        viewDetail = $(R.id.viewDetail);
        viewDuoYu = $(R.id.viewDuoYu);
        imageZhanKai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!data) {
                    data = true;
                    viewDuoYu.setVisibility(View.VISIBLE);
                    imageZhanKai.setImageResource(R.mipmap.shousuo);
                    viewDetail.setBackgroundResource(R.mipmap.mingxi_item_bg2);
                } else {
                    data = false;
                    viewDuoYu.setVisibility(View.GONE);
                    imageZhanKai.setImageResource(R.mipmap.zhankai);
                    viewDetail.setBackgroundResource(R.mipmap.mingxi_item_bg1);
                }
            }
        });
    }

    @Override
    public void setData(Boolean data) {
        super.setData(data);
        this.data = data;
    }

}
