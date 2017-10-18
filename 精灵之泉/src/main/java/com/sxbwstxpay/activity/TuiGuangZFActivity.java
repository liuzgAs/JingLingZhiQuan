package com.sxbwstxpay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OrderVipbefore;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.TuiGuangZhiFuViewHolder;

public class TuiGuangZFActivity extends ZjbBaseActivity implements View.OnClickListener {
    private RecyclerArrayAdapter<OrderVipbefore> adapter;
    private EasyRecyclerView recyclerView;
    private View viewBar;
    private BroadcastReceiver recevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.PAY_RECEIVER:
                    cancelLoadingDialog();
                    int error = intent.getIntExtra("error", -1);
                    if (error == 0) {
                        paySuccess();
                    } else if (error == -1) {
                        MyDialog.showTipDialog(TuiGuangZFActivity.this, "支付失败");
                    } else if (error == -2) {
                        MyDialog.showTipDialog(TuiGuangZFActivity.this, "支付失败");
                    }
                    break;
            }
        }
    };
    private OrderVipbefore orderVipbefore;

    /**
     * des： 支付成功提示
     * author： ZhangJieBo
     * date： 2017/7/6 0006 下午 2:34
     */
    public void paySuccess() {
        Intent intent = new Intent();
        intent.setAction(Constant.BROADCASTCODE.VIP);
        sendBroadcast(intent);
        MyDialog.dialogFinish(this, "成功开通VIP");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_fu);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        orderVipbefore = (OrderVipbefore) intent.getSerializableExtra(Constant.INTENT_KEY.value);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("支付");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        initRecycle();
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        adapter.clear();
        adapter.add(orderVipbefore);
        adapter.notifyDataSetChanged();
    }

    private void initRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<OrderVipbefore>(TuiGuangZFActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.view_youhui_md;
                return new TuiGuangZhiFuViewHolder(parent, layout);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("订单未支付，确定要退出吗？")
                        .setNegativeButton("否", null)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .create()
                        .show();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.PAY_RECEIVER);
        registerReceiver(recevier, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(recevier);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("订单未支付，确定要退出吗？")
                .setNegativeButton("否", null)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create()
                .show();
    }
}
