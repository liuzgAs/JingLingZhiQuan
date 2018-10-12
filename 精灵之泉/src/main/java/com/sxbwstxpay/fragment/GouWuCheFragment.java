package com.sxbwstxpay.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ChanPinXQActivity;
import com.sxbwstxpay.activity.QueRenDDActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.CartIndex;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.Arith;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.GouWuCXViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GouWuCheFragment extends ZjbBaseFragment implements View.OnClickListener {
    private RecyclerArrayAdapter<CartIndex.CartBean> adapter;
    private EasyRecyclerView recyclerView;
    private View viewBar;
    private ImageView imageQuanXuan;
    private boolean isQuanXuan = true;
    private TextView textSum;
    private View viewQuJieSuan;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.GouWuCheNum:
                    onRefresh();
                    break;
                default:
                    break;
            }
        }
    };

    private View mInflate;

    public GouWuCheFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_gou_wu_che, container, false);
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

    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        recyclerView = (EasyRecyclerView) mInflate.findViewById(R.id.recyclerView);
        viewBar = mInflate.findViewById(R.id.viewBar);
        imageQuanXuan = (ImageView) mInflate.findViewById(R.id.imageQuanXuan);
        textSum = (TextView) mInflate.findViewById(R.id.textSum);
        viewQuJieSuan = mInflate.findViewById(R.id.viewQuJieSuan);
    }

    @Override
    protected void initViews() {
        ((TextView) mInflate.findViewById(R.id.textViewTitle)).setText("购物车");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
        viewBar.setLayoutParams(layoutParams);
        initRecycler();
        imageQuanXuan.setImageResource(R.mipmap.xuanzhong);
        viewQuJieSuan.setVisibility(View.INVISIBLE);
        mInflate.findViewById(R.id.imageBack).setVisibility(View.GONE);
    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.buttonJieSuan).setOnClickListener(this);
        mInflate.findViewById(R.id.viewQuanXuan).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColor(getResources().getColor(R.color.basic_color));
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<CartIndex.CartBean>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_car;
                return new GouWuCXViewHolder(parent, layout,GouWuCheFragment.this);
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY.id, adapter.getItem(position).getGoods_id());
                intent.setClass(getActivity(), ChanPinXQActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        TextView textTitle = (TextView) recyclerView.getEmptyView().findViewById(R.id.textTitle);
        textTitle.setText("购物车空空如也");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.viewQuanXuan:
                if (isQuanXuan) {
                    for (int i = 0; i < adapter.getAllData().size(); i++) {
                        adapter.getAllData().get(i).setIscheck(false);
                    }
                } else {
                    for (int i = 0; i < adapter.getAllData().size(); i++) {
                        adapter.getAllData().get(i).setIscheck(true);
                    }
                }
                adapter.notifyDataSetChanged();
                quanXuan();
                break;
            case R.id.buttonJieSuan:
                List<CartIndex.CartBean> allData = adapter.getAllData();
                if (allData.size() > 0) {
                    List<String> cart = new ArrayList<>();
                    for (int i = 0; i < allData.size(); i++) {
                        if (allData.get(i).ischeck()) {
                            cart.add(allData.get(i).getId());
                        }
                    }
                    if (cart.size() == 0) {
                        Toast.makeText(getActivity(), "请选择要结算的商品", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(getActivity(), "购物车为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.setClass(getActivity(), QueRenDDActivity.class);
                intent.putExtra(Constant.INTENT_KEY.value, new CartIndex(adapter.getAllData()));
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.CART_INDEX;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    public void onRefresh() {
        ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("购物车", s);
                try {
                    CartIndex cartIndex = GsonUtils.parseJSON(s, CartIndex.class);
                    if (cartIndex.getStatus() == 1) {
                        List<CartIndex.CartBean> cartIndexCart = new ArrayList<>();
                        if (cartIndex.getCart() == null) {
                        } else {
                            cartIndexCart = cartIndex.getCart();
                        }
                        for (int i = 0; i < cartIndexCart.size(); i++) {
                            cartIndexCart.get(i).setIscheck(true);
                        }
                        if (cartIndex.getSum() == null) {
                            textSum.setText("¥0.00");
                        } else {
                            textSum.setText("¥" + cartIndex.getSum());
                        }
                        isQuanXuan = true;
                        imageQuanXuan.setImageResource(R.mipmap.xuanzhong);
                        adapter.clear();
                        adapter.addAll(cartIndexCart);
                        if (cartIndexCart.size()>0){
                            viewQuJieSuan.setVisibility(View.VISIBLE);
                        }
                    } else if (cartIndex.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        showError(cartIndex.getInfo());
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
                        recyclerView.showProgress();
                        initData();
                    }
                });
                recyclerView.setErrorView(view_loaderror);
                recyclerView.showError();
            }
        });
    }


    public void quanXuan() {
        double sum = 0;
        isQuanXuan = true;
        for (int i = 0; i < adapter.getAllData().size(); i++) {
            if (!adapter.getAllData().get(i).ischeck()) {
                isQuanXuan = false;
            } else {
                Double mul = Arith.mul(Double.parseDouble(adapter.getAllData().get(i).getGoods_price()), (double) adapter.getAllData().get(i).getNum());
                sum = Arith.add(mul, sum);
            }
        }
        if (isQuanXuan) {
            imageQuanXuan.setImageResource(R.mipmap.xuanzhong);
        } else {
            imageQuanXuan.setImageResource(R.mipmap.weixuanzhong);
        }
        setSum(sum + "");
    }

    public void setSum(String sum) {
        textSum.setText("¥" + sum);
    }


    /**
     * des： 删除item
     * author： ZhangJieBo
     * date： 2017/9/26 0026 下午 7:41
     */
    public void remove(int position) {
        adapter.remove(position);
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.GouWuCheNum);
        getActivity().registerReceiver(reciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(reciver);
    }
}
