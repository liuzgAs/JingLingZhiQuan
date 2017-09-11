package hudongchuangxiang.com.jinglingzhiquan.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.activity.XuanZeTDActivity;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseFragment;
import hudongchuangxiang.com.jinglingzhiquan.util.ScreenUtils;
import hudongchuangxiang.com.jinglingzhiquan.util.StringUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShouKuanFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;
    private View mRelaTitleStatue;
    private TextView textAmount;
    private View[] tabView = new View[3];
    private View viewTabBg;
    private int[] textKeyId = new int[]{
            R.id.textKey00,
            R.id.textKey01,
            R.id.textKey02,
            R.id.textKey03,
            R.id.textKey04,
            R.id.textKey05,
            R.id.textKey06,
            R.id.textKey07,
            R.id.textKey08,
            R.id.textKey09,
    };
    private String amount ="";

    public ShouKuanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_shou_kuan, container, false);
            init();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mInflate.getParent();
        if (parent != null) {
            parent.removeView(mInflate);
        }
        return mInflate;
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void findID() {
        mRelaTitleStatue = mInflate.findViewById(R.id.relaTitleStatue);
        textAmount = (TextView) mInflate.findViewById(R.id.textAmount);
        tabView[0] = mInflate.findViewById(R.id.viewYinLian);
        tabView[1] = mInflate.findViewById(R.id.viewZhiFuBao);
        tabView[2] = mInflate.findViewById(R.id.viewWeiXin);
        viewTabBg = mInflate.findViewById(R.id.viewTabBg);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = mRelaTitleStatue.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
        mRelaTitleStatue.setLayoutParams(layoutParams);
        mRelaTitleStatue.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);
        SpannableString span = new SpannableString("¥");
        span.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textAmount.setText(span);
    }

    @Override
    protected void setListeners() {
        for (int i = 0; i < tabView.length; i++) {
            tabView[i].setOnClickListener(this);
        }
        for (int i = 0; i < textKeyId.length; i++) {
            final int finalI = i;
            mInflate.findViewById(textKeyId[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (amount.length()<9){
                        amount =amount+ finalI;
                        checkAmount();
                    }
                }
            });
        }
        mInflate.findViewById(R.id.textKeyDian).setOnClickListener(this);
        mInflate.findViewById(R.id.textKeyDelete).setOnClickListener(this);
        mInflate.findViewById(R.id.buttonShouKuan).setOnClickListener(this);
        mInflate.findViewById(R.id.textKeyDelete).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                amount="";
                setAmount();
                return false;
            }
        });
    }

    private void checkAmount() {
        if (StringUtil.isAmount(amount)){
            setAmount();
        }else {
            amount = amount.substring(0,amount.length()-1);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textKeyDelete:
                if (amount.length()>0){
                    amount = amount.substring(0,amount.length()-1);
                    setAmount();
                }
                break;
            case R.id.textKeyDian:
                if (amount.length()<9&&StringUtil.isAmount(amount)){
                    amount =amount+ ".";
                    checkAmount();
                }
                break;
            case R.id.viewYinLian:
                viewTabBg.setBackgroundResource(R.mipmap.zuobian);
                break;
            case R.id.viewZhiFuBao:
                viewTabBg.setBackgroundResource(R.mipmap.zhongjian);
                break;
            case R.id.viewWeiXin:
                viewTabBg.setBackgroundResource(R.mipmap.youbian);
                break;
            case R.id.buttonShouKuan:
                Intent intent = new Intent();
                intent.setClass(getActivity(), XuanZeTDActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void setAmount() {
        SpannableString span = new SpannableString("¥"+amount);
        span.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textAmount.setText(span);
    }

}
