package com.sxbwstxpay.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.HkBill;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class XinYongKaZDFragment extends ZjbBaseFragment {


    private View mInflate;
    private ViewPager viewPager;
    private TabLayout tablayout;
    private TextView textTab0001;
    private TextView textTab0002;
    private TextView textTab0101;
    private TextView textTab0102;
    private TextView textTab0201;
    private TextView textTab0202;
    private TextView textTitle;
    private TextView textTitleDes;

    public XinYongKaZDFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_xin_yong_ka_zd, container, false);
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
        viewPager = (ViewPager) mInflate.findViewById(R.id.viewPager);
        tablayout = (TabLayout) mInflate.findViewById(R.id.tablayout);
        textTitle = (TextView) mInflate.findViewById(R.id.textTitle);
        textTitleDes = (TextView) mInflate.findViewById(R.id.textTitleDes);
        textTab0001 = (TextView) mInflate.findViewById(R.id.textTab0001);
        textTab0002 = (TextView) mInflate.findViewById(R.id.textTab0002);
        textTab0101 = (TextView) mInflate.findViewById(R.id.textTab0101);
        textTab0102 = (TextView) mInflate.findViewById(R.id.textTab0102);
        textTab0201 = (TextView) mInflate.findViewById(R.id.textTab0201);
        textTab0202 = (TextView) mInflate.findViewById(R.id.textTab0202);
    }

    @Override
    protected void initViews() {
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        tablayout.setupWithViewPager(viewPager);
        tablayout.removeAllTabs();
        List<String> list = new ArrayList<>();
        list.add("全部计划");
        list.add("已定计划");
        list.add("待定计划");
        list.add("失败计划");
        for (int i = 0; i < list.size(); i++) {
            View item_tablayout = LayoutInflater.from(getActivity()).inflate(R.layout.item_tablayout, null);
            TextView textTitle = (TextView) item_tablayout.findViewById(R.id.textTitle);
            if (i == 0) {
                textTitle.setText(list.get(i));
                tablayout.addTab(tablayout.newTab().setCustomView(item_tablayout), true);
            } else {
                textTitle.setText(list.get(i));
                tablayout.addTab(tablayout.newTab().setCustomView(item_tablayout), false);
            }
        }
    }

    @Override
    protected void setListeners() {

    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.HK_BILL;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        params.put("p",String.valueOf(1));
        params.put("type",String.valueOf(0));
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("XinYongKaZDFragment--onSuccess",s+ "");
                try {
                    HkBill hkBill = GsonUtils.parseJSON(s, HkBill.class);
                    if (hkBill.getStatus()==1){
                        textTitle.setText(hkBill.getTitle());
                        textTitleDes.setText(hkBill.getTitleDes());
                        textTab0001.setText(hkBill.getTab().get(0).getN());
                        textTab0002.setText(hkBill.getTab().get(0).getV());
                        textTab0101.setText(hkBill.getTab().get(1).getN());
                        textTab0102.setText(hkBill.getTab().get(1).getV());
                        textTab0201.setText(hkBill.getTab().get(2).getN());
                        textTab0202.setText(hkBill.getTab().get(2).getV());
                    }else if (hkBill.getStatus()==3){
                        MyDialog.showReLoginDialog(getActivity());
                    }else {
                        Toast.makeText(getActivity(), hkBill.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new XinYongKaZDJHFragment(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
