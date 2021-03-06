package com.sxbwstxpay.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.interfacepage.CeShiInterface;
import com.sxbwstxpay.model.TestStyle;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.test3ViewHolder;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CeShi3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CeShi3Fragment extends ZjbBaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "DataBean";
    private ArrayList<TestStyle.DataBean> dataBeans;

    private View mInflate;
    private View viewBar;
    private TextView textTitle;
    private EasyRecyclerView recyclerView;
    private CeShiInterface ceShiInterface;
    private Button buttonLeft;
    private Button buttonRight;
    RecyclerArrayAdapter<TestStyle.DataBean> adapter;

    public CeShi3Fragment() {
        // Required empty public constructor
    }

    public static CeShi3Fragment newInstance(ArrayList<TestStyle.DataBean> dataBeans) {
        CeShi3Fragment fragment = new CeShi3Fragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, dataBeans);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataBeans = (ArrayList<TestStyle.DataBean>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_ce_shi1, container, false);
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
        viewBar = mInflate.findViewById(R.id.viewBar);
        textTitle = mInflate.findViewById(R.id.textTitle);
        recyclerView = mInflate.findViewById(R.id.recyclerView);
        buttonLeft = mInflate.findViewById(R.id.buttonLeft);
        buttonRight = mInflate.findViewById(R.id.buttonRight);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
        viewBar.setLayoutParams(layoutParams);
        textTitle.setText("以下的饰品，哪些是你喜欢的风格？（可多选）");
        buttonLeft.setText("上一题");
        buttonRight.setText("下一题");
        LinearLayout.LayoutParams layoutParams1=(LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        layoutParams1.leftMargin=10;
        layoutParams1.rightMargin=10;
        recyclerView.setLayoutParams(layoutParams1);
        initRecycler();
    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.imageBack).setOnClickListener(this);
        buttonLeft.setOnClickListener(this);
        buttonRight.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        adapter.clear();
        adapter.addAll(dataBeans);
    }
    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(manager);
        SpaceDecoration spaceDecoration = new SpaceDecoration((int) DpUtils.convertDpToPixel(15f, getActivity()));
        recyclerView.addItemDecoration(spaceDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<TestStyle.DataBean>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_test3;
                return new test3ViewHolder(parent, layout);
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (dataBeans.get(position).getIsc()){
                    dataBeans.get(position).setIsc(false);
                }else {
                    dataBeans.get(position).setIsc(true);
                }
                adapter.clear();
                adapter.addAll(dataBeans);
            }
        });
    }
    ArrayList<String> ids=new ArrayList<>();
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                ceShiInterface.setPostion(1);
                break;
            case R.id.buttonLeft:
                ceShiInterface.setPostion(1);
                break;
            case R.id.buttonRight:
                ids.clear();
                for (int i=0;i<dataBeans.size();i++){
                    if (dataBeans.get(i).getIsc()){
                        ids.add(dataBeans.get(i).getId());
                    }
                }
                if (ids.size()==0){
                    Toast.makeText(getActivity(),"请至少选择一项！",Toast.LENGTH_SHORT).show();
                    return;
                }
                ceShiInterface.setIds(2,ids);
                ceShiInterface.setPostion(3);
                break;
            default:
                break;
        }
    }
    public void setCallBack(CeShiInterface callBack) {
        this.ceShiInterface = callBack;
    }
}
