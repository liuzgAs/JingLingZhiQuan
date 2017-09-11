package hudongchuangxiang.com.jinglingzhiquan.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseActivity;
import hudongchuangxiang.com.jinglingzhiquan.provider.DataProvider;
import hudongchuangxiang.com.jinglingzhiquan.util.DpUtils;
import hudongchuangxiang.com.jinglingzhiquan.util.ScreenUtils;
import hudongchuangxiang.com.jinglingzhiquan.viewholder.FeiLvViewHolder;

public class XuanZeTDActivity extends ZjbBaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private View viewBar;
    private RecyclerArrayAdapter<Integer> adapter;
    private Handler handler = new Handler();
    private int page = 1;
    private EasyRecyclerView recyclerView;
    private AlertDialog XuanZeYHKDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuan_ze_td);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("选择通道");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        initRecycler();
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
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
        }, 500);
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.diver), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColor(getResources().getColor(R.color.basic_color));
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Integer>(XuanZeTDActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_xuan_ze_td;
                return new FeiLvViewHolder(parent, layout);
            }

            @Override
            public int getViewType(int position) {
                Integer item = getItem(position);
                return item;
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(XuanZeTDActivity.this).inflate(R.layout.header_xuan_ze_td, null);
                return view;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                xuanZeYHK();
            }
        });
    }

    private void xuanZeYHK() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.mydialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_xuan_ze_yhk, null);
        view.findViewById(R.id.viewCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XuanZeYHKDialog.dismiss();
            }
        });
        ListView listView = (ListView) view.findViewById(R.id.listView);
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = (int) DpUtils.convertDpToPixel(70*2,XuanZeTDActivity.this);
        listView.setLayoutParams(layoutParams);
        listView.setAdapter(new MyAdapter());
        XuanZeYHKDialog = builder.setView(view)
                .create();
        XuanZeYHKDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
        }
    }

    class MyAdapter extends BaseAdapter {
        class ViewHolder {
//            public TextView title;
        }
        @Override
        public int getCount() {
            return 3;
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
                convertView = LayoutInflater.from(XuanZeTDActivity.this).inflate(R.layout.item_xuan_ze_yhk, null);
//                holder.title = (TextView) convertView.findViewById(R.id.textView408);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//            holder.title.setText("Hello");
            return convertView;
        }
    }
}
