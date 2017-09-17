package hudongchuangxiang.com.jinglingzhiquan.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.model.UserRate;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class FeiLvViewHolder extends BaseViewHolder<UserRate.DataBean> {

    private final TextView textName;
    private final TextView textDes;
    private final TextView textNameDes;
    private final TextView textRate;
    private final TextView textFee;
    private final TextView textMaxDay;
    private final TextView textMaxAmount;

    public FeiLvViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textName = $(R.id.textName);
        textDes = $(R.id.textDes);
        textNameDes = $(R.id.textNameDes);
        textRate = $(R.id.textNameDes);
        textFee = $(R.id.textNameDes);
        textMaxDay = $(R.id.textMaxDay);
        textMaxAmount = $(R.id.textMaxAmount);
    }

    @Override
    public void setData(UserRate.DataBean data) {
        super.setData(data);
        textName.setText(data.getName());
        textDes.setText(data.getDes());
        textNameDes.setText(data.getNameDes());
        textRate.setText(data.getRate());
        textFee.setText(data.getFee());
        textMaxDay.setText(data.getMaxDay());
        textMaxAmount.setText(data.getMaxAmount());
    }
    
}
