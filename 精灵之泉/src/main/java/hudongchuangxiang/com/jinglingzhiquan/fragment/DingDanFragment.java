package hudongchuangxiang.com.jinglingzhiquan.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseFragment;
import hudongchuangxiang.com.jinglingzhiquan.provider.DataProvider;
import hudongchuangxiang.com.jinglingzhiquan.util.DpUtils;
import hudongchuangxiang.com.jinglingzhiquan.viewholder.DingDanViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DingDanFragment extends ZjbBaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    private View mInflate;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Integer> adapter;
    private Handler handler = new Handler();
    private int page = 1;

    public DingDanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_ping_jia, container, false);
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
        initRecycle();
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
        onRefresh();
    }

    private void initRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) DpUtils.convertDpToPixel(10,getActivity()), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        int red = getResources().getColor(R.color.basic_color);
        recyclerView.setRefreshingColor(red);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Integer>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_dingdan;
                return new DingDanViewHolder(parent, layout);
            }

            @Override
            public int getViewType(int position) {
                Integer item = getItem(position);
                return item;
            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addAll(DataProvider.getPersonList(page));
                        page++;
                    }
                }, 500);
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
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View view = new View(getContext());
                view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) DpUtils.convertDpToPixel(10,getActivity())));
                return view;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }

    @Override
    public void onRefresh() {
        page = 1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                adapter.addAll(DataProvider.getPersonList(page));
                page++;
            }
        }, 0);
    }
}
