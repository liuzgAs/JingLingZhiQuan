package com.sxbwstxpay.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.jlzquan.www.R;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.activity.ChanPinXQActivity;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.provider.DataProvider;
import com.sxbwstxpay.viewholder.LocalImageHolderView;
import com.sxbwstxpay.viewholder.XianShiQGViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class XianShiQGFragment extends ZjbBaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    private View mInflate;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Integer> adapter;
    private int page = 1;

    public XianShiQGFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_xian_shi_qg, container, false);
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
        recyclerView = (EasyRecyclerView) mInflate.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.marginTop), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        int red = getResources().getColor(R.color.basic_color);
        recyclerView.setRefreshingColor(red);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Integer>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_xian_shi_qg;
                return new XianShiQGViewHolder(parent, layout);
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private TabLayout tablayoutHeader;
            private List<String> imgBanner;
            private ConvenientBanner banner;

            @Override
            public View onCreateView(ViewGroup parent) {
                View header_xian_shi_qg = LayoutInflater.from(getActivity()).inflate(R.layout.header_xian_shi_qg, null);
                banner = (ConvenientBanner) header_xian_shi_qg.findViewById(R.id.banner);
                banner.setScrollDuration(1000);
                banner.startTurning(3000);
                imgBanner = new ArrayList<>();
                imgBanner.add("11111111");
                imgBanner.add("22222222");
                imgBanner.add("33333333");
                imgBanner.add("44444444");
                imgBanner.add("55555555");
                tablayoutHeader = (TabLayout) header_xian_shi_qg.findViewById(R.id.tablayoutHeader);
                tablayoutHeader.removeAllTabs();
                tablayoutHeader.setTabMode(TabLayout.MODE_SCROLLABLE);
                for (int i = 0; i < 12; i++) {
                    View item_qiang_gou_sj = LayoutInflater.from(getActivity()).inflate(R.layout.item_qiang_gou_sj, null);
                    if (i==2){
                        tablayoutHeader.addTab(tablayoutHeader.newTab().setCustomView(item_qiang_gou_sj), true);
                    }else {
                        tablayoutHeader.addTab(tablayoutHeader.newTab().setCustomView(item_qiang_gou_sj), false);
                    }
                }
                return header_xian_shi_qg;
            }

            @Override
            public void onBindView(View headerView) {
                banner.setPages(new CBViewHolderCreator() {
                    @Override
                    public Object createHolder() {
                        return new LocalImageHolderView();
                    }
                },imgBanner);
                banner.setPageIndicator(new int[]{R.drawable.shape_indicator_normal,R.drawable.shape_indicator_selected});
            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                page++;
                adapter.addAll(DataProvider.getPersonList(page));
            }

            @Override
            public void onMoreClick() {

            }
        });
        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {

            }

            @Override
            public void onNoMoreClick() {
            }
        });
        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
        recyclerView.setRefreshListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ChanPinXQActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
        onRefresh();
    }


    @Override
    public void onRefresh() {
        page = 1;
        adapter.clear();
        adapter.addAll(DataProvider.getPersonList(page));
    }
}
