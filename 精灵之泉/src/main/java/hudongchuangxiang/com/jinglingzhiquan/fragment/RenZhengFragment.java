package hudongchuangxiang.com.jinglingzhiquan.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseFragment;
import hudongchuangxiang.com.jinglingzhiquan.util.LogUtil;
import hudongchuangxiang.com.jinglingzhiquan.util.ScreenUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class RenZhengFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;
    private View mRelaTitleStatue;
    private View viewTianXinXi;
    private ScrollView scrollView;

    public RenZhengFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_ren_zheng, container, false);
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
        viewTianXinXi = mInflate.findViewById(R.id.viewTianXinXi);
        scrollView = (ScrollView) mInflate.findViewById(R.id.scrollView);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = mRelaTitleStatue.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
        mRelaTitleStatue.setLayoutParams(layoutParams);
        mRelaTitleStatue.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);
    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.buttonNext).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonNext:
                viewTianXinXi.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        LogUtil.LogShitou("RenZhengFragment--onBackPressed", "11111");
        if (scrollView.getVisibility() == View.VISIBLE) {
            viewTianXinXi.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            return true;
        }else {
            return super.onBackPressed();
        }
    }
}
