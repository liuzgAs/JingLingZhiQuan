package com.sxbwstxpay.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.ClassroomItem;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.Subpwd;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.AppUtil;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.JingLingKTViewHolder;

import java.util.HashMap;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import okhttp3.Response;

public class JingLingKTActivity extends ZjbBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View viewBar;
    private TextView textViewTitle;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<ClassroomItem.DataBean> adapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jing_ling_kt);
        init(JingLingLCActivity.class);
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
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        textViewTitle.setText("精灵课堂");
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
     * 初始化recyclerview
     */
    private void initRecycler() {
        GridLayoutManager manager = new GridLayoutManager(JingLingKTActivity.this, 2);
        recyclerView.setLayoutManager(manager);
        SpaceDecoration spaceDecoration = new SpaceDecoration((int) DpUtils.convertDpToPixel(10f, JingLingKTActivity.this));
        recyclerView.addItemDecoration(spaceDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<ClassroomItem.DataBean>(JingLingKTActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_jingling_kt;
                return new JingLingKTViewHolder(parent, layout);
            }
        });
        manager.setSpanSizeLookup(adapter.obtainGridSpanSizeLookUp(2));
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                ApiClient.post(JingLingKTActivity.this, getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            page++;
                            ClassroomItem skillMy = GsonUtils.parseJSON(s, ClassroomItem.class);
                            int status = skillMy.getStatus();
                            if (status == 1) {
                                List<ClassroomItem.DataBean> dataBeans = skillMy.getData();
                                adapter.addAll(dataBeans);
                            } else if (status == 3) {
                                MyDialog.showReLoginDialog(JingLingKTActivity.this);
                            } else {
                                adapter.pauseMore();
                            }
                        } catch (Exception e) {
                            adapter.pauseMore();
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        adapter.pauseMore();
                    }
                });
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
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                if (adapter.getItem(position).getIs_pwd() == 1) {
                    new MaterialDialog.Builder(JingLingKTActivity.this)
                            .title("提示")
                            .iconRes(R.mipmap.logo)
                            .content(skillMy.getPwdBgTxt())
                            .inputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                            .widgetColor(ContextCompat.getColor(JingLingKTActivity.this, R.color.basic_color))
                            .input("请输入密码", "", new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                }
                            })
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (dialog.getInputEditText().length() >0) {
                                        dialog.dismiss();
                                        subpwd(adapter.getItem(position).getId(),dialog.getInputEditText().getText().toString());
                                    }else {
                                        dialog.dismiss();
                                    }
                                }
                            })
                            .show();

                }else {
                    subpwd(adapter.getItem(position).getId(),"");
                }
            }
        });
        recyclerView.setRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
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
        String url = Constant.HOST + Constant.Url.CLASSROOMITEM_INDEX;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("p", page + "");
        return new OkObject(params, url);
    }

    private ClassroomItem skillMy;

    @Override
    public void onRefresh() {
        page = 1;
        ApiClient.post(JingLingKTActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("我的订单", s);
                try {
                    page++;
                    skillMy = GsonUtils.parseJSON(s, ClassroomItem.class);
                    if (skillMy.getStatus() == 1) {
                        List<ClassroomItem.DataBean> userOrderList = skillMy.getData();
                        adapter.clear();
                        adapter.addAll(userOrderList);
                    } else if (skillMy.getStatus() == 3) {
                        MyDialog.showReLoginDialog(JingLingKTActivity.this);
                    } else {
                        showError(skillMy.getInfo());
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
                View view_loaderror = LayoutInflater.from(JingLingKTActivity.this).inflate(R.layout.view_loaderror, null);
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

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getokobjectsubpwd(String id,String pwd) {
        String url = Constant.HOST + Constant.Url.CLASSROOMITEM_SUBPWD;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("pwd", AppUtil.getMD5(AppUtil.getMD5(pwd) + "ad"));
        params.put("id", id);

        return new OkObject(params, url);
    }


    private void subpwd(String id,String pwd) {
        showLoadingDialog();
        ApiClient.post(JingLingKTActivity.this, getokobjectsubpwd(id,pwd), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("提交密码", s);
                try {
                    Subpwd subpwd = GsonUtils.parseJSON(s, Subpwd.class);
                    if (subpwd.getStatus() == 1) {
                        JzvdStd.startFullscreen(JingLingKTActivity.this, JzvdStd.class,subpwd.getVideo_url(), "精灵课堂");
                    } else if (subpwd.getStatus() == 3) {
                        MyDialog.showReLoginDialog(JingLingKTActivity.this);
                    } else {
                        Toast.makeText(JingLingKTActivity.this, subpwd.getInfo(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    Toast.makeText(JingLingKTActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                Toast.makeText(JingLingKTActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }

        });
    }
    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
