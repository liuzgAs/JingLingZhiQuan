package hudongchuangxiang.com.jinglingzhiquan.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;

import hudongchuangxiang.com.jinglingzhiquan.R;
import hudongchuangxiang.com.jinglingzhiquan.base.MyDialog;
import hudongchuangxiang.com.jinglingzhiquan.base.ZjbBaseFragment;
import hudongchuangxiang.com.jinglingzhiquan.constant.Constant;
import hudongchuangxiang.com.jinglingzhiquan.model.Index_Makemoney;
import hudongchuangxiang.com.jinglingzhiquan.model.OkObject;
import hudongchuangxiang.com.jinglingzhiquan.util.ApiClient;
import hudongchuangxiang.com.jinglingzhiquan.util.GsonUtils;
import hudongchuangxiang.com.jinglingzhiquan.util.LogUtil;
import hudongchuangxiang.com.jinglingzhiquan.util.ScreenUtils;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhuanQianFragment extends ZjbBaseFragment {

    //    private EasyRecyclerView recyclerView;
    private View mInflate;
    private View mRelaTitleStatue;
    //    private RecyclerArrayAdapter<Integer> adapter;
//    private Handler handler = new Handler();
//    private int page = 1;
//    private WebView mWebView;
    private WebSettings mSettings;
    private ProgressBar pb1;
    private LinearLayout viewLinear;
    private WebView webView;

    public ZhuanQianFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_zhuan_qian, container, false);
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
        mRelaTitleStatue = mInflate.findViewById(R.id.relaTitleStatue);
//        recyclerView = (EasyRecyclerView) mInflate.findViewById(R.id.recyclerView);
//        mWebView = (WebView) mInflate.findViewById(R.id.webView);
        pb1 = (ProgressBar) mInflate.findViewById(R.id.progressBar2);
        viewLinear = (LinearLayout) mInflate.findViewById(R.id.viewLinear);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = mRelaTitleStatue.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(getActivity()));
        mRelaTitleStatue.setLayoutParams(layoutParams);
        mRelaTitleStatue.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);
        webView = new WebView(getActivity());
        webView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewLinear.addView(webView);
//        initRecycle();
        webView.setWebViewClient(new WebViewClient());//覆盖第三方浏览器
        mSettings = webView.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setUseWideViewPort(true);
        mSettings.setLoadWithOverviewMode(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pb1.setProgress(newProgress);
                if (newProgress == 100) {
                    pb1.setVisibility(View.GONE);
                } else {
                    pb1.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void setListeners() {

    }
    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_MAKEMONEY;
        HashMap<String, String> params = new HashMap<>();
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
//        onRefresh();
        showLoadingDialog();
        ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ZhuanQianFragment--onSuccess", "");
                try {
                    Index_Makemoney index_makemoney = GsonUtils.parseJSON(s, Index_Makemoney.class);
                    if (index_makemoney.getStatus()==1){
                        webView.loadUrl(index_makemoney.getUrl());
                    }else if (index_makemoney.getStatus()==2){
                        MyDialog.showReLoginDialog(getActivity());
                    }else {
                        Toast.makeText(getActivity(), index_makemoney.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void initRecycle() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.marginTop), 0, 0);
//        itemDecoration.setDrawLastItem(false);
//        recyclerView.addItemDecoration(itemDecoration);
//        int red = getResources().getColor(R.color.basic_color);
//        recyclerView.setRefreshingColor(red);
//        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Integer>(getActivity()) {
//            @Override
//            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
//                int layout = R.layout.item_zhuan_qian;
//                return new ZhuanQianViewHolder(parent, layout);
//            }
//
//            @Override
//            public int getViewType(int position) {
//                Integer item = getItem(position);
//                return item;
//            }
//        });
//        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                View header_zahun_qian = LayoutInflater.from(getActivity()).inflate(R.layout.header_zahun_qian, null);
//                return header_zahun_qian;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//
//            }
//        });
//        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
//            @Override
//            public void onMoreShow() {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.addAll(DataProvider.getPersonList(page));
//                        page++;
//                    }
//                }, 500);
//            }
//
//            @Override
//            public void onMoreClick() {
//
//            }
//        });
//        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
//            @Override
//            public void onNoMoreShow() {
//
//            }
//
//            @Override
//            public void onNoMoreClick() {
//            }
//        });
//        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
//            @Override
//            public void onErrorShow() {
//                adapter.resumeMore();
//            }
//
//            @Override
//            public void onErrorClick() {
//                adapter.resumeMore();
//            }
//        });
//        recyclerView.setRefreshListener(this);
//        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//            }
//        });
//    }

//    @Override
//    public void onRefresh() {
//        page = 1;
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                adapter.clear();
//                adapter.addAll(DataProvider.getPersonList(page));
//                page++;
//            }
//        }, 0);
//    }


    @Override
    public boolean onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            return super.onBackPressed();
        }
    }
}
