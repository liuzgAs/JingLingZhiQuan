package com.sxbwstxpay.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.sxbwstxpay.activity.ChengShiXZActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.IndexCate;
import com.sxbwstxpay.model.IndexCitylist;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShengQianCZFragment extends ZjbBaseFragment implements View.OnClickListener {
    private View mInflate;
    private View mRelaTitleStatue;
    private TabLayout tablayout;
    private ViewPager viewPager;
    private TextView textCity;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.CITY_CHOOSE:
                    IndexCitylist.CityEntity.ListEntity cityBean = (IndexCitylist.CityEntity.ListEntity) intent.getSerializableExtra(Constant.INTENT_KEY.CITY);
                    textCity.setText(cityBean.getName());
                    break;
            }
        }
    };
    private String mCity;
    private List<IndexCate.CateBean> indexCateCate;

    public ShengQianCZFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_sheng_qian, container, false);
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
    protected void initSP() {
        final ACache aCache = ACache.get(getActivity(), Constant.ACACHE.LOCATION);
        String cityAcache = aCache.getAsString(Constant.ACACHE.CITY);
        if (cityAcache != null) {
            mCity = cityAcache;
        }
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        mRelaTitleStatue = mInflate.findViewById(R.id.relaTitleStatue);
        tablayout = (TabLayout) mInflate.findViewById(R.id.tablayout);
        viewPager = (ViewPager) mInflate.findViewById(R.id.viewPager);
        textCity = (TextView) mInflate.findViewById(R.id.textCity);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = mRelaTitleStatue.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
        mRelaTitleStatue.setLayoutParams(layoutParams);
        mRelaTitleStatue.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);
        textCity.setText(mCity);
    }

    @Override
    protected void setListeners() {
        textCity.setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_CATE;
        HashMap<String, String> params = new HashMap<>();
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
       showLoadingDialog();
       ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
           @Override
           public void onSuccess(String s) {
               cancelLoadingDialog();
               LogUtil.LogShitou("ShengQianCZFragment--限时购分类", s+"");
               try {
                   IndexCate indexCate = GsonUtils.parseJSON(s, IndexCate.class);
                   if (indexCate.getStatus()==1){
                       indexCateCate = indexCate.getCate();
                       viewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager()));
                       tablayout.setupWithViewPager(viewPager);
                       tablayout.removeAllTabs();
                       for (int i = 0; i < indexCateCate.size(); i++) {
                           View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_tablayout, null);
                           TextView textTitle = (TextView) view.findViewById(R.id.textTitle);
                           textTitle.setText(indexCateCate.get(i).getName());
                           if (i==0){
                               tablayout.addTab(tablayout.newTab().setCustomView(view),true);
                           }else {
                               tablayout.addTab(tablayout.newTab().setCustomView(view),false);
                           }
                       }
                   }else if (indexCate.getStatus()==2){
                       MyDialog.showReLoginDialog(getActivity());
                   }else {
                       Toast.makeText(getActivity(), indexCate.getInfo(), Toast.LENGTH_SHORT).show();
                   }
               } catch (Exception e) {
                   Toast.makeText(getActivity(),"数据出错", Toast.LENGTH_SHORT).show();
               }
           }
       
           @Override
           public void onError(Response response) {
               cancelLoadingDialog();
               Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
           }
       });
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new XianShiQGFragment();
            } else {
                return new XuanPinSJFragment();
            }
        }

        @Override
        public int getCount() {
            return indexCateCate.size();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.textCity:
                intent.setClass(getActivity(), ChengShiXZActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.CITY_CHOOSE);
        getActivity().registerReceiver(reciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(reciver);
    }

}
