package hudongchuangxiang.com.jinglingzhiquan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.HashMap;
import java.util.List;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.activity.XinZengYHKActivity;
import hudongchuangxiang.com.jinglingzhiquan.base.MyDialog;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseFragment;
import hudongchuangxiang.com.jinglingzhiquan.constant.Constant;
import hudongchuangxiang.com.jinglingzhiquan.model.BankCardlist;
import hudongchuangxiang.com.jinglingzhiquan.model.OkObject;
import hudongchuangxiang.com.jinglingzhiquan.util.ApiClient;
import hudongchuangxiang.com.jinglingzhiquan.util.GsonUtils;
import hudongchuangxiang.com.jinglingzhiquan.util.LogUtil;
import hudongchuangxiang.com.jinglingzhiquan.viewholder.XuanZeXYKViewHolder;
import okhttp3.Response;

public class GuanLiYHKFragment extends ZjbBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private View mInflate;
    private RecyclerArrayAdapter<BankCardlist.DataBean> adapter;
    private EasyRecyclerView recyclerView;
    private int type = 0;

    public GuanLiYHKFragment() {
        // Required empty public constructor
    }

    public GuanLiYHKFragment(int type) {
        // Required empty public constructor
        this.type = type;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_guan_li_yhk, container, false);
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
        initRecycler();
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
        onRefresh();
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final DividerDecoration itemDecoration = new DividerDecoration(getResources().getColor(R.color.line_gray), (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColor(getResources().getColor(R.color.basic_color));
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<BankCardlist.DataBean>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_xuan_ze_xyk;
                return new XuanZeXYKViewHolder(parent, layout);

            }
        });
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.footer_xuan_ze_xyk, null);
                TextView textXinZengXYK = (TextView) view.findViewById(R.id.textXinZengXYK);
                switch (type) {
                    case 1:
                        textXinZengXYK.setText("新增银行卡");
                        break;
                    case 2:
                        textXinZengXYK.setText("新增信用卡");
                        break;
                }
                textXinZengXYK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), XinZengYHKActivity.class);
                        switch (type) {
                            case 1:
                                intent.putExtra(Constant.INTENT_KEY.TITLE, "新增银行卡");
                                intent.putExtra(Constant.INTENT_KEY.type, 1);
                                break;
                            case 2:
                                intent.putExtra(Constant.INTENT_KEY.TITLE, "新增信用卡");
                                intent.putExtra(Constant.INTENT_KEY.type, 2);
                                break;
                        }
                        startActivityForResult(intent, Constant.REQUEST_RESULT_CODE.XIN_YONG_KA);
                    }
                });
                return view;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        recyclerView.setRefreshListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {


            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_RESULT_CODE.XIN_YONG_KA && resultCode == Constant.REQUEST_RESULT_CODE.XIN_YONG_KA) {
            onRefresh();
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.BANK_CARDLIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("type", type + "");//储蓄卡1  信用卡2
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("选择信用卡", s);
                try {
                    BankCardlist bankCardlist = GsonUtils.parseJSON(s, BankCardlist.class);
                    if (bankCardlist.getStatus() == 1) {
                        adapter.clear();
                        List<BankCardlist.DataBean> bankCardlistData = bankCardlist.getData();
                        adapter.addAll(bankCardlistData);
                    } else if (bankCardlist.getStatus() == 2) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        showError(bankCardlist.getInfo());
                    }
                } catch (Exception e) {
                    showError("数据出错");
                }
            }

            @Override
            public void onError(Response response) {
                showError("网络出错");
            }

            public void showError(String msg) {
                View view_loaderror = LayoutInflater.from(getActivity()).inflate(R.layout.view_loaderror, null);
                TextView textMsg = (TextView) view_loaderror.findViewById(R.id.textMsg);
                textMsg.setText(msg);
                view_loaderror.findViewById(R.id.buttonReLoad).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initData();
                    }
                });
                recyclerView.setErrorView(view_loaderror);
                recyclerView.showError();
            }
        });
    }
}
