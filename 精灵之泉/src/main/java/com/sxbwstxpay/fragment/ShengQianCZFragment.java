package com.sxbwstxpay.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ChengShiXZActivity;
import com.sxbwstxpay.activity.GouWuCActivity;
import com.sxbwstxpay.activity.SouSuoActivity;
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
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

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
                case Constant.BROADCASTCODE.GouWuCheNum:
                    LogUtil.LogShitou("ShengQianCZFragment--onReceive", "刷新购物车");
                    gouWuCheNum(true);
                    break;
            }
        }
    };
    private String mCity;
    private List<IndexCate.CateBean> indexCateCate;
    private Badge badge;
    private View viewVip;
    private ImageView imageShaiXuan;
    private int positionX;
    private View viewShaiXuan;
    private boolean isShaiXuan = false;
    private ListView listViewShaiXuan;
    private MyAdapter myAdapter;
    private List<IndexCate.SortBean> indexCateSort;

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
        viewVip = mInflate.findViewById(R.id.viewVip);
        badge = new QBadgeView(getActivity())
                .setBadgeTextColor(Color.WHITE)
                .setBadgeTextSize(8f, true)
                .setBadgeBackgroundColor(getResources().getColor(R.color.red))
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(3f, 3f, true);
        imageShaiXuan = (ImageView) mInflate.findViewById(R.id.imageShaiXuan);
        viewShaiXuan = mInflate.findViewById(R.id.viewShaiXuan);
        listViewShaiXuan = (ListView) mInflate.findViewById(R.id.listViewShaiXuan);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = mRelaTitleStatue.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
        mRelaTitleStatue.setLayoutParams(layoutParams);
        mRelaTitleStatue.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);
        textCity.setText(mCity);
        imageShaiXuan.setVisibility(View.GONE);
        viewShaiXuan.setVisibility(View.GONE);
    }

    @Override
    protected void setListeners() {
        textCity.setOnClickListener(this);
        viewVip.setOnClickListener(this);
        mInflate.findViewById(R.id.textSouSuo).setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                positionX = position;
                switch (indexCateCate.get(position).getJump()){
                    case "time":
                        imageShaiXuan.setVisibility(View.GONE);
                        break;
                    case "product":
                        imageShaiXuan.setVisibility(View.GONE);
                        break;
                    case "store":
                        imageShaiXuan.setVisibility(View.GONE);
                        break;
                    default:
                        imageShaiXuan.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        imageShaiXuan.setOnClickListener(this);
        listViewShaiXuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isShaiXuan = false;
                viewShaiXuan.setVisibility(View.GONE);
                for (int i = 0; i < indexCateSort.size(); i++) {
                    indexCateSort.get(i).setSelect(false);
                }
                indexCateSort.get(position).setSelect(true);
                myAdapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.setAction(Constant.BROADCASTCODE.ShaiXuan);
                intent.putExtra(Constant.INTENT_KEY.value,indexCateSort.get(position).getV());
                getActivity().sendBroadcast(intent);
            }
        });
        viewShaiXuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShaiXuan = false;
                viewShaiXuan.setVisibility(View.GONE);
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_CATE;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        gouWuCheNum(false);
    }

    private void gouWuCheNum(final boolean isRefresh) {
        ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ShengQianCZFragment--限时购分类", s + "");
                try {
                    IndexCate indexCate = GsonUtils.parseJSON(s, IndexCate.class);
                    if (indexCate.getStatus() == 1) {
                        if (isRefresh) {
                            badge.setBadgeNumber(indexCate.getVipNum()).bindTarget(viewVip);
                            return;
                        }
                        indexCateSort = indexCate.getSort();
                        for (int i = 0; i < indexCateSort.size(); i++) {
                            indexCateSort.get(i).setSelect(false);
                        }
                        indexCateSort.get(0).setSelect(true);
                        myAdapter = new MyAdapter();
                        listViewShaiXuan.setAdapter(myAdapter);
                        badge.setBadgeNumber(indexCate.getVipNum()).bindTarget(viewVip);
                        indexCateCate = indexCate.getCate();
                        viewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager()));
                        tablayout.setupWithViewPager(viewPager);
                        tablayout.removeAllTabs();
                        LogUtil.LogShitou("ShengQianCZFragment--indexCate.getVipNum()", "" + indexCate.getVipNum());
                        for (int i = 0; i < indexCateCate.size(); i++) {
                            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_tablayout, null);
                            TextView textTitle = (TextView) view.findViewById(R.id.textTitle);
                            textTitle.setText(indexCateCate.get(i).getName());
                            if (i == 0) {
                                tablayout.addTab(tablayout.newTab().setCustomView(view), true);
                            } else {
                                tablayout.addTab(tablayout.newTab().setCustomView(view), false);
                            }
                        }
                    } else if (indexCate.getStatus() == 2) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        Toast.makeText(getActivity(), indexCate.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "数据出错", Toast.LENGTH_SHORT).show();
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
            switch (indexCateCate.get(position).getJump()){
                case "time":
                    return new XianShiQGFragment(0);
                case "product":
                    return new XianShiQGFragment(1);
                case "store":
                    return new BenDiYDFragment();
                default:
                    IndexCate.CateBean cateBean = indexCateCate.get(position);
                    return new XuanPinSJFragment(position, cateBean);
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
            case R.id.imageShaiXuan:
                isShaiXuan = !isShaiXuan;
                if (isShaiXuan) {
                    viewShaiXuan.setVisibility(View.VISIBLE);
                } else {
                    viewShaiXuan.setVisibility(View.GONE);
                }
                break;
            case R.id.textSouSuo:
                intent.setClass(getActivity(), SouSuoActivity.class);
                startActivity(intent);
                break;
            case R.id.viewVip:
                intent.setClass(getActivity(), GouWuCActivity.class);
                startActivity(intent);
                break;
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
        filter.addAction(Constant.BROADCASTCODE.GouWuCheNum);
        getActivity().registerReceiver(reciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(reciver);
    }

    class MyAdapter extends BaseAdapter {
        class ViewHolder {
            public TextView textName;
        }

        @Override
        public int getCount() {
            return (indexCateSort.size());
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_shai_xuan, null);
                holder.textName = (TextView) convertView.findViewById(R.id.textName);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            int light_black = getActivity().getResources().getColor(R.color.light_black);
            int basic_color = getActivity().getResources().getColor(R.color.basic_color);
            if (indexCateSort.get(position).isSelect()) {
                holder.textName.setTextColor(basic_color);
            } else {
                holder.textName.setTextColor(light_black);
            }
            holder.textName.setText(indexCateSort.get(position).getName());
            return convertView;
        }
    }
}
