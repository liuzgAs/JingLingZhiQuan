package com.sxbwstxpay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.AfterAddbefore;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.Picture;
import com.sxbwstxpay.model.RespondAppimgadd;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.DpUtils;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.ImgToBase64;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.viewholder.PictureViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class ShenQingSHActivity extends ZjbBaseActivity implements View.OnClickListener {

    private String id;
    private View viewBar;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Picture> adapter;
    private int selectMax = 9;
    private AfterAddbefore afterAddbefore;
    private int shouHouLieXingID =-1;
    private int shouHouYuanYinID = -1;
    private int maxNum;
    private EditText editShuoMing;
    List<Integer> imgsList = new ArrayList<>();
    int imgSum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shen_qing_sh);
        init(ShenQingSHActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        id = intent.getStringExtra(Constant.INTENT_KEY.id);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("申请售后");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        initRecycler();
    }

    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        SpaceDecoration itemDecoration = new SpaceDecoration((int) DpUtils.convertDpToPixel(12f, ShenQingSHActivity.this));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Picture>(ShenQingSHActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.gv_filter_image;
                PictureViewHolder pictureViewHolder = new PictureViewHolder(parent, layout, viewType);
                pictureViewHolder.setOnRemoveListener(new PictureViewHolder.OnRemoveListener() {
                    @Override
                    public void remove(int position) {
                        adapter.remove(position);
                        if (adapter.getAllData().size() == selectMax - 1) {
                            if (adapter.getItem(selectMax - 2).getType() != 1) {
                                adapter.add(new Picture(1, new LocalMedia()));
                            }
                        }
                    }
                });
                return pictureViewHolder;
            }

            @Override
            public int getViewType(int position) {
                return getItem(position).getType();
            }
        });
        manager.setSpanSizeLookup(adapter.obtainGridSpanSizeLookUp(4));
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            private TextView textMaxMoney;
            private TextView textMaxNum;
            private TextView textShouHouYuanYin;
            private TextView textShouHouLeiXing;
            private ImageView imageImg;
            private TextView textTitle;
            private TextView textGuiGe;
            private TextView textNum;
            private TextView textPrice;
            private TextView textZhuan;
            private TextView textShouHou;

            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(ShenQingSHActivity.this).inflate(R.layout.header_shenqingshouhou, null);
                imageImg = (ImageView) view.findViewById(R.id.imageImg);
                textTitle = (TextView) view.findViewById(R.id.textTitle);
                textGuiGe = (TextView) view.findViewById(R.id.textGuiGe);
                textNum = (TextView) view.findViewById(R.id.textNum);
                textPrice = (TextView) view.findViewById(R.id.textPrice);
                textZhuan = (TextView) view.findViewById(R.id.textZhuan);
                textShouHou = (TextView) view.findViewById(R.id.textShouHou);
                textShouHouLeiXing = (TextView) view.findViewById(R.id.textShouHouLeiXing);
                textShouHouYuanYin = (TextView) view.findViewById(R.id.textShouHouYuanYin);
                textMaxNum = (TextView) view.findViewById(R.id.textMaxNum);
                textMaxMoney = (TextView) view.findViewById(R.id.textMaxMoney);
                editShuoMing = (EditText) view.findViewById(R.id.editShuoMing);
                textShouHou.setVisibility(View.GONE);
                view.findViewById(R.id.viewShouHouLeiXing).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        List<AfterAddbefore.TypeBean> typeBeanList = afterAddbefore.getType();
                        final String[] strings = new String[typeBeanList.size()];
                        for (int i = 0; i < typeBeanList.size(); i++) {
                            strings[i] = typeBeanList.get(i).getTitle();
                        }
                        new AlertDialog.Builder(ShenQingSHActivity.this)
                                .setItems(strings, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, final int i) {
                                        textShouHouLeiXing.setText(strings[i]);
                                        shouHouLieXingID = afterAddbefore.getType().get(i).getId();
                                    }
                                })
                                .show();
                    }
                });
                view.findViewById(R.id.viewShouHouYuanYin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final List<AfterAddbefore.ReasonBean> reasonBeanList = afterAddbefore.getReason();
                        final String[] strings = new String[reasonBeanList.size()];
                        for (int i = 0; i < reasonBeanList.size(); i++) {
                            strings[i] = reasonBeanList.get(i).getTitle();
                        }
                        new AlertDialog.Builder(ShenQingSHActivity.this)
                                .setItems(strings, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, final int i) {
                                        textShouHouYuanYin.setText(strings[i]);
                                        shouHouYuanYinID = reasonBeanList.get(i).getId();
                                    }
                                })
                                .show();
                    }
                });
                view.findViewById(R.id.imageJian).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (maxNum > 1) {
                            maxNum--;
                            textMaxNum.setText(maxNum + "");
                        }

                    }
                });
                view.findViewById(R.id.imageJia).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (maxNum < afterAddbefore.getMaxNum()) {
                            maxNum++;
                            textMaxNum.setText(maxNum + "");
                        }
                    }
                });
                view.findViewById(R.id.imageZuiDa).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(ShenQingSHActivity.this, WebActivity.class);
                        intent.putExtra(Constant.INTENT_KEY.TITLE, afterAddbefore.getUrlTitle());
                        intent.putExtra(Constant.INTENT_KEY.URL, afterAddbefore.getUrl());
                        startActivity(intent);
                    }
                });
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                if (afterAddbefore != null) {
                    AfterAddbefore.DataBean data = afterAddbefore.getData();
                    GlideApp.with(ShenQingSHActivity.this)
                            .load(data.getImg())
                            .centerCrop()
                            .placeholder(R.mipmap.ic_empty)
                            .transition(new DrawableTransitionOptions().crossFade(500))
                            .into(imageImg);
                    textTitle.setText(data.getGoods_name());
                    textGuiGe.setText(data.getGoods_spe());
                    textNum.setText("×" + data.getQuantity());
                    textPrice.setText("¥" + data.getGoods_price());
                    textZhuan.setText("赚" + data.getGoods_money());
                    textMaxNum.setText(maxNum + "");
                    textMaxMoney.setText("¥"+afterAddbefore.getMaxMoney());
                }
            }
        });
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            private TextView textDes2;

            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(ShenQingSHActivity.this).inflate(R.layout.foot_shenqingshouhou, null);
                textDes2 = (TextView) view.findViewById(R.id.textDes2);
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                if (afterAddbefore != null) {
                    textDes2.setText("最多上传9张图片");
                }
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (adapter.getItem(position).getType() == 1) {
                    addPicture();
                } else {
                    LogUtil.LogShitou("PingJiaActivity--onItemClick", "预览");
                    LocalMedia media = adapter.getItem(position).getLocalMedia();
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    List<LocalMedia> localMediaList = new ArrayList<>();
                    for (int i = 0; i < adapter.getAllData().size(); i++) {
                        if (adapter.getItem(i).getType() == 0) {
                            localMediaList.add(adapter.getItem(i).getLocalMedia());
                        }
                    }
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(ShenQingSHActivity.this).externalPicturePreview(position, localMediaList);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    /**
     * 添加照片
     */
    private void addPicture() {
        List<LocalMedia> localMediaList = new ArrayList<>();
        for (int i = 0; i < adapter.getAllData().size(); i++) {
            if (adapter.getItem(i).getType() == 0) {
                localMediaList.add(adapter.getItem(i).getLocalMedia());
            }
        }
        PictureSelector.create(this)
                /*全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()*/
                .openGallery(PictureMimeType.ofImage())
                /* 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE*/
                .selectionMode(PictureConfig.MULTIPLE)
                /*最大选择数量*/
                .maxSelectNum(selectMax)
                /*是否显示拍照按钮 true or false*/
                .isCamera(true)
                /*拍照保存图片格式后缀,默认jpeg*/
                .imageFormat(PictureMimeType.PNG)
                /*glide 加载图片大小 0~1之间 如设置 .glideOverride()无效*/
                .sizeMultiplier(0.5f)
                /*是否压缩 true or false*/
                .compress(true)
                /*裁剪压缩质量 默认90 int*/
                .cropCompressQuality(100)
                /*小于100kb的图片不压缩*/
                .minimumCompressSize(100)
                /*同步true或异步false 压缩 默认同步*/
                .synOrAsy(true)
                /*裁剪是否可旋转图片 true or false*/
                .rotateEnabled(true)
                /*裁剪是否可放大缩小图片 true or false*/
                .scaleEnabled(true)
                /*传入已选择的照片*/
                .selectionMedia(localMediaList)
                /*结果回调onActivityResult code*/
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.btnTiJiao).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.AFTER_ADDBEFORE;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("id", id);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("售后前", s);
                try {
                    afterAddbefore = GsonUtils.parseJSON(s, AfterAddbefore.class);
                    if (afterAddbefore.getStatus() == 1) {
                        maxNum = afterAddbefore.getMaxNum();
                        adapter.clear();
                        adapter.add(new Picture(1, new LocalMedia()));
                        adapter.notifyDataSetChanged();
                    } else if (afterAddbefore.getStatus() == 3) {
                        MyDialog.showReLoginDialog(ShenQingSHActivity.this);
                    } else {
                        showError(afterAddbefore.getInfo());
                    }
                } catch (Exception e) {
                    showError("数据出错");
                }
            }

            @Override
            public void onError(Response response) {
                showError("网络出错");
            }

            /**
             * 错误显示
             * @param msg
             */
            private void showError(String msg) {
                try {
                    View viewLoader = LayoutInflater.from(ShenQingSHActivity.this).inflate(R.layout.view_loaderror, null);
                    TextView textMsg = (TextView) viewLoader.findViewById(R.id.textMsg);
                    textMsg.setText(msg);
                    viewLoader.findViewById(R.id.buttonReLoad).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recyclerView.showProgress();
                            initData();
                        }
                    });
                    recyclerView.setErrorView(viewLoader);
                    recyclerView.showError();
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> localMediaList = PictureSelector.obtainMultipleResult(data);
                    adapter.clear();
                    for (int i = 0; i < localMediaList.size(); i++) {
                        adapter.add(new Picture(0, localMediaList.get(i)));
                    }
                    if (localMediaList.size() < selectMax) {
                        adapter.add(new Picture(1, new LocalMedia()));
                    }
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTiJiao:
                imgsList.clear();
                imgSum = 0;
                if (adapter.getAllData().size() > 1) {
                    for (int i = 0; i < adapter.getAllData().size(); i++) {
                        if (adapter.getItem(i).getType() == 0) {
                            imgSum++;
                            upImg(adapter.getItem(i).getLocalMedia().getCompressPath());
                        }
                    }
                } else {
                    tiJiao();
                }
                break;
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
    private OkObject getTPOkObject(String path) {
        String url = Constant.HOST + Constant.Url.RESPOND_APPIMGADD;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("code", "headimg");
        params.put("brand", "android");
        params.put("img", ImgToBase64.toBase64(path));
        return new OkObject(params, url);
    }

    /**
     * 上传图片
     */
    private void upImg(String path) {
        showLoadingDialog();
        ApiClient.post(ShenQingSHActivity.this, getTPOkObject(path), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("GeRenXXActivity--上传图片", s + "");
                try {
                    RespondAppimgadd respondAppimgadd = GsonUtils.parseJSON(s, RespondAppimgadd.class);
                    if (respondAppimgadd.getStatus() == 1) {
                        imgsList.add(respondAppimgadd.getImgId());
                        if (imgsList.size() == imgSum) {
                            tiJiao();
                        }
                    } else if (respondAppimgadd.getStatus() == 3) {
                        MyDialog.showReLoginDialog(ShenQingSHActivity.this);
                    } else {
                        Toast.makeText(ShenQingSHActivity.this, respondAppimgadd.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ShenQingSHActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(ShenQingSHActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getTJOkObject() {
        String imgs ="";
        for (int i = 0; i < imgsList.size(); i++) {
            if (i<imgsList.size()-1){
                imgs = imgs+imgsList.get(i)+",";
            }else {
                imgs = imgs+imgsList.get(i);
            }
        }
        String url = Constant.HOST + Constant.Url.AFTER_ADDSUBMIT;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        params.put("num",maxNum+"");
        params.put("type",shouHouLieXingID+"");
        params.put("reason",shouHouYuanYinID+"");
        params.put("id",id);
        params.put("msg",editShuoMing.getText().toString().trim());
        params.put("imgs",imgs);
        return new OkObject(params, url);
    }

    private void tiJiao() {
        if (shouHouLieXingID == -1) {
            Toast.makeText(ShenQingSHActivity.this, "请选择服务类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (shouHouYuanYinID== -1) {
            Toast.makeText(ShenQingSHActivity.this, "请选择服务原因", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(editShuoMing.getText().toString().trim())) {
            Toast.makeText(ShenQingSHActivity.this, "请输入售后说明", Toast.LENGTH_SHORT).show();
            return;
        }
        showLoadingDialog();
        ApiClient.post(ShenQingSHActivity.this, getTJOkObject() , new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ShenQingSHActivity--onSuccess", s + "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus() == 1) {
                        Intent intent = new Intent();
                        intent.setAction(Constant.BROADCASTCODE.SHUA_XIN_SHOW_HOU);
                        sendBroadcast(intent);
                        MyDialog.dialogFinish(ShenQingSHActivity.this, simpleInfo.getInfo());
                    } else if (simpleInfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(ShenQingSHActivity.this);
                    } else {
                        Toast.makeText(ShenQingSHActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ShenQingSHActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(ShenQingSHActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
