package com.sxbwstxpay.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.HashMap;
import java.util.List;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.BankCardlist;
import com.sxbwstxpay.model.BankPayment;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.XuanZeTDViewHolder;
import okhttp3.Response;

public class XuanZeTDActivity extends ZjbBaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private View viewBar;
    private RecyclerArrayAdapter<BankPayment.DataBean> adapter;
    private EasyRecyclerView recyclerView;
    private AlertDialog XuanZeYHKDialog;
    private String amount;
    private List<BankCardlist.DataBean> bankCardlistData;
    private String tongDaoId;

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
        Intent intent = getIntent();
        amount = intent.getStringExtra(Constant.INTENT_KEY.amount);
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

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.BANK_PAYMENT;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid() + "");
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("选择支付通道", s);
                try {
                    BankPayment bankPayment = GsonUtils.parseJSON(s, BankPayment.class);
                    if (bankPayment.getStatus() == 1) {
                        adapter.clear();
                        List<BankPayment.DataBean> dataBeanList = bankPayment.getData();
                        adapter.addAll(dataBeanList);
                    } else if (bankPayment.getStatus() == 2) {
                        MyDialog.showReLoginDialog(XuanZeTDActivity.this);
                    } else {
                        showError(bankPayment.getInfo());
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
                View view_loaderror = LayoutInflater.from(XuanZeTDActivity.this).inflate(R.layout.view_loaderror, null);
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

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.diver), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColor(getResources().getColor(R.color.basic_color));
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<BankPayment.DataBean>(XuanZeTDActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_xuan_ze_td;
                return new XuanZeTDViewHolder(parent, layout);
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            private TextView textAmount;

            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(XuanZeTDActivity.this).inflate(R.layout.header_xuan_ze_td, null);
                textAmount = (TextView) view.findViewById(R.id.textAmount);
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                textAmount.setText("¥" + amount);
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
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
                params.put("type", "1");//储蓄卡1  信用卡2
                return new OkObject(params, url);
            }

            @Override
            public void onItemClick(final int position) {
                if (Double.parseDouble(amount) < adapter.getItem(position).getMinAmount()) {
                    MyDialog.showTipDialog(XuanZeTDActivity.this, "单次最少金额" + adapter.getItem(position).getMinAmount());
                    return;
                }
                if (Double.parseDouble(amount) > adapter.getItem(position).getMaxAmount()) {
                    MyDialog.showTipDialog(XuanZeTDActivity.this, "单次最大金额" + adapter.getItem(position).getMaxAmount());
                    return;
                }
                showLoadingDialog();
                ApiClient.post(XuanZeTDActivity.this, getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        cancelLoadingDialog();
                        LogUtil.LogShitou("XuanZeTDActivity--选择银行卡", s + "");
                        tongDaoId = adapter.getItem(position).getId();
                        try {
                            BankCardlist bankCardlist = GsonUtils.parseJSON(s, BankCardlist.class);
                            if (bankCardlist.getStatus() == 1) {
                                bankCardlistData = bankCardlist.getData();
                                xuanZeYHK();
                            } else if (bankCardlist.getStatus() == 2) {
                                MyDialog.showReLoginDialog(XuanZeTDActivity.this);
                            } else {
                                Toast.makeText(XuanZeTDActivity.this, bankCardlist.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(XuanZeTDActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        cancelLoadingDialog();
                        Toast.makeText(XuanZeTDActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
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
        view.findViewById(R.id.imageCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XuanZeYHKDialog.dismiss();
            }
        });
        ListView listView = (ListView) view.findViewById(R.id.listView);
        if (bankCardlistData.size() <= 3) {
            ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
            layoutParams.height = (int) DpUtils.convertDpToPixel(70 * bankCardlistData.size(), XuanZeTDActivity.this);
            listView.setLayoutParams(layoutParams);
        }
        listView.setAdapter(new MyAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(XuanZeTDActivity.this, XuanZeXYKActivity.class);
                intent.putExtra(Constant.INTENT_KEY.id, bankCardlistData.get(position).getId());
                intent.putExtra(Constant.INTENT_KEY.tongDaoId, tongDaoId);
                intent.putExtra(Constant.INTENT_KEY.amount, amount);
                startActivity(intent);
                finish();
                XuanZeYHKDialog.dismiss();
            }
        });
        XuanZeYHKDialog = builder.setView(view)
                .create();
        XuanZeYHKDialog.show();
        view.findViewById(R.id.viewXinZengYHK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(XuanZeTDActivity.this, XinZengYHKActivity.class);
                intent.putExtra(Constant.INTENT_KEY.TITLE, "新增银行卡");
                intent.putExtra(Constant.INTENT_KEY.type, 1);
                startActivity(intent);
                XuanZeYHKDialog.dismiss();
            }
        });
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
            public TextView textBankNameBankCard;
            public ImageView imageImg;
            public TextView textBank;
        }

        @Override
        public int getCount() {
            return bankCardlistData.size();
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
                holder.textBankNameBankCard = (TextView) convertView.findViewById(R.id.textBankNameBankCard);
                holder.textBank = (TextView) convertView.findViewById(R.id.textBank);
                holder.imageImg = (ImageView) convertView.findViewById(R.id.imageImg);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textBankNameBankCard.setText(bankCardlistData.get(position).getBankName() + "(" + bankCardlistData.get(position).getBankCard() + ")");
            holder.textBank.setText(bankCardlistData.get(position).getBank());
            Glide.with(XuanZeTDActivity.this)
                    .load(bankCardlistData.get(position).getImg())
                    .placeholder(R.mipmap.ic_empty)
                    .into(holder.imageImg);
            return convertView;
        }
    }
}
