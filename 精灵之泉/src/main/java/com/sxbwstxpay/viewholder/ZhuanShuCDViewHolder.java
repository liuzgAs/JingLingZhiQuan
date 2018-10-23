package com.sxbwstxpay.viewholder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ChanPinXQActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.MyEasyRecyclerView;
import com.sxbwstxpay.fragment.ZhuanShuCDFragment;
import com.sxbwstxpay.model.CommonlyUsed;
import com.sxbwstxpay.model.IndexStyle;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;

import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ZhuanShuCDViewHolder extends BaseViewHolder<IndexStyle.DataBean> {
    private ImageView imageImg[]=new ImageView[5];
    private TextView textStyle;
    private TextView textIntro;
    private ImageView imageCollect;
    private MyEasyRecyclerView recyclerView;
    private IndexStyle.DataBean dataBean;
    private RecyclerArrayAdapter<IndexStyle.DataBean.DesBean> adapter;
    private ZhuanShuCDFragment fragment;
    public ZhuanShuCDViewHolder(ViewGroup parent, @LayoutRes int res, ZhuanShuCDFragment fragment) {
        super(parent, res);
        this.fragment=fragment;
        imageImg[0] = $(R.id.imageImg1);
        imageImg[1] = $(R.id.imageImg2);
        imageImg[2] = $(R.id.imageImg3);
        imageImg[3] = $(R.id.imageImg4);
        imageImg[4] = $(R.id.imageImg5);
        imageCollect= $(R.id.imageCollect);
        textStyle = $(R.id.textStyle);
        textIntro = $(R.id.textIntro);
        recyclerView = $(R.id.recyclerView);
        initRecycler();
        imageCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBean.getIs_collect()==1){
                    qxshoucang(dataBean);
                }else {
                    shoucang(dataBean);
                }
            }
        });
    }

    @Override
    public void setData(final IndexStyle.DataBean data) {
        super.setData(data);
        dataBean=data;
        for (int i=0;i<imageImg.length;i++){
            imageImg[i].setVisibility(View.INVISIBLE);
        }
        for (int i=0;i<data.getImgs().size();i++){
            imageImg[i].setVisibility(View.VISIBLE);
            GlideApp.with(getContext())
                    .load(data.getImgs().get(i).getImg())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_empty)
                    .into(imageImg[i]);
        }
        if (data.getIs_collect()==1){
            imageCollect.setImageResource(R.mipmap.shouc);
        }else {
            imageCollect.setImageResource(R.mipmap.shouc_no);
        }
        textIntro.setText(data.getIntro());
        textStyle.setText(data.getTitle());
        adapter.clear();
        adapter.addAll(data.getDes());
        for (int i=0;i<data.getImgs().size();i++){
            final int j=i;
            imageImg[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(Constant.INTENT_KEY.id,data.getImgs().get(j).getGoods_id());
                    intent.setClass(getContext(), ChanPinXQActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    getContext().startActivity(intent);
                }
            });
        }
    }
    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter = new RecyclerArrayAdapter<IndexStyle.DataBean.DesBean>(getContext()) {

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_cd_style;
                return new CDStyleViewHolder(parent, layout);
            }
        });
        SpaceDecoration spaceDecoration = new SpaceDecoration((int) DpUtils.convertDpToPixel(6f, getContext()));
        spaceDecoration.setPaddingEdgeSide(false);
        recyclerView.addItemDecoration(spaceDecoration);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recycler, int dx, int dy) {
                super.onScrolled(recycler, dx, dy);
                recyclerView.setScroll(true);
            }
        });
        recyclerView.setOnDaoDiLeListener(new MyEasyRecyclerView.OnDaoDiLeListener() {
            @Override
            public void daoDiLe() {
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject(IndexStyle.DataBean dataBean) {
        String url = Constant.HOST + Constant.Url.COLLECT;
        HashMap<String, String> params = new HashMap<>();
        if (fragment.userInfo!=null){
            params.put("uid", fragment.userInfo.getUid());
            params.put("tokenTime", fragment.tokenTime);
        }
        params.put("type", "2");
        params.put("itemId", dataBean.getId());
        return new OkObject(params, url);
    }

    public void shoucang(final IndexStyle.DataBean dataBean) {
        fragment.showLoadingDialog();
        ApiClient.post(getContext(), getOkObject(dataBean), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                fragment.cancelLoadingDialog();
                LogUtil.LogShitou("WoDeFragment--我的", s + "");
                try {
                    CommonlyUsed commonlyUsed = GsonUtils.parseJSON(s, CommonlyUsed.class);
                    if (commonlyUsed.getStatus() == 1) {
                        Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                        imageCollect.setImageResource(R.mipmap.shouc);
                        dataBean.setIs_collect(1);
                    } else if (commonlyUsed.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getContext());
                    } else {
                        Toast.makeText(getContext(), commonlyUsed.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    try {
                        Toast.makeText(getContext(), R.string.dataErrer, Toast.LENGTH_SHORT).show();
                    } catch (Exception e1) {
                    }
                }
            }

            @Override
            public void onError(Response response) {
                fragment.cancelLoadingDialog();
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject1(IndexStyle.DataBean dataBean) {
        String url = Constant.HOST + Constant.Url.CANCLECOLLECT;
        HashMap<String, String> params = new HashMap<>();
        if (fragment.userInfo!=null){
            params.put("uid", fragment.userInfo.getUid());
            params.put("tokenTime", fragment.tokenTime);
        }
        params.put("type", "2");
        params.put("itemId", dataBean.getId());
        return new OkObject(params, url);
    }

    public void qxshoucang(final IndexStyle.DataBean dataBean) {
        fragment.showLoadingDialog();
        ApiClient.post(getContext(), getOkObject1(dataBean), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                fragment.cancelLoadingDialog();
                LogUtil.LogShitou("WoDeFragment--我的", s + "");
                try {
                    CommonlyUsed commonlyUsed = GsonUtils.parseJSON(s, CommonlyUsed.class);
                    if (commonlyUsed.getStatus() == 1) {
                        Toast.makeText(getContext(), "取消收藏", Toast.LENGTH_SHORT).show();
                        imageCollect.setImageResource(R.mipmap.shouc_no);
                        dataBean.setIs_collect(0);
                    } else if (commonlyUsed.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getContext());
                    } else {
                        Toast.makeText(getContext(), commonlyUsed.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    try {
                        Toast.makeText(getContext(), R.string.dataErrer, Toast.LENGTH_SHORT).show();
                    } catch (Exception e1) {
                    }
                }
            }

            @Override
            public void onError(Response response) {
                fragment.cancelLoadingDialog();
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
