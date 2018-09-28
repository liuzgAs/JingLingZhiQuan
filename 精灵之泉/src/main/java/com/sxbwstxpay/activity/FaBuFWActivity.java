package com.sxbwstxpay.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.sxbwstxpay.R;
import com.sxbwstxpay.application.MyApplication;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.AddAfter;
import com.sxbwstxpay.model.IndexCitylist;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.RespondAppimgadd;
import com.sxbwstxpay.model.Skill_Addbefore;
import com.sxbwstxpay.util.ACache;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.ImgToBase64;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class FaBuFWActivity extends ZjbBaseActivity implements View.OnClickListener {
    private View viewBar;
    private TextView textViewTitle;
    private TextView textTips;
    private LinearLayout viewTips;
    private ImageView imageDelete;
    private View viewImg;
    private ImageView imageImg;
    private TextView textPinLei;
    private TextView textDiQu;
    private EditText edit01;
    private EditText edit02;
    private EditText edit03;
    private EditText edit04;
    private EditText edit05;
    private Button btnFaBu;
    private String id;
    private String imageId;
    private Skill_Addbefore skillBefore;
    private String cid;
    private String cityId;
    private String lat;
    private String lng;
    private BroadcastReceiver reciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BROADCASTCODE.CITY_CHOOSE:
                    IndexCitylist.CityEntity.ListEntity cityBean = (IndexCitylist.CityEntity.ListEntity) intent.getSerializableExtra(Constant.INTENT_KEY.CITY);
                    cityId = cityBean.getId();
                    final ACache aCache = ACache.get(FaBuFWActivity.this, Constant.ACACHE.LOCATION);
                    aCache.put(Constant.ACACHE.CITY_ID, cityId);
                    textDiQu.setText(cityBean.getName());
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_bu_fw);
        MyApplication.getInstance().images.clear();
        init(FaBuFWActivity.class);
    }

    @Override
    protected void initSP() {
        final ACache aCache = ACache.get(this, Constant.ACACHE.LOCATION);
        String cityAcache = aCache.getAsString(Constant.ACACHE.CITY);
        if (cityAcache != null) {
            lat = aCache.getAsString(Constant.ACACHE.LAT);
            lng = aCache.getAsString(Constant.ACACHE.LNG);
        }
    }

    @Override
    protected void initIntent() {
        id = getIntent().getStringExtra(Constant.INTENT_KEY.id);
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCASTCODE.CITY_CHOOSE);
        registerReceiver(reciver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textTips = (TextView) findViewById(R.id.textTips);
        viewTips = (LinearLayout) findViewById(R.id.viewTips);
        imageDelete = (ImageView) findViewById(R.id.imageDelete);
        viewImg = findViewById(R.id.viewImg);
        imageImg = (ImageView) findViewById(R.id.imageImg);
        textPinLei = (TextView) findViewById(R.id.textPinLei);
        textDiQu = (TextView) findViewById(R.id.textDiQu);
        edit01 = (EditText) findViewById(R.id.edit01);
        edit02 = (EditText) findViewById(R.id.edit02);
        edit03 = (EditText) findViewById(R.id.edit03);
        edit04 = (EditText) findViewById(R.id.edit04);
        edit05 = (EditText) findViewById(R.id.edit05);
        btnFaBu = (Button) findViewById(R.id.btnFaBu);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        if (TextUtils.isEmpty(id)) {
            textViewTitle.setText("发布服务");
        } else {
            textViewTitle.setText("编辑服务");
        }
        requestPermissions(99, Manifest.permission.CAMERA);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.imageDelete).setOnClickListener(this);
        findViewById(R.id.viewImgAdd).setOnClickListener(this);
        findViewById(R.id.viewXuanZhePL).setOnClickListener(this);
        findViewById(R.id.viewDiQu).setOnClickListener(this);
        btnFaBu.setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.SKILL_ADDBEFORE;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("id", id);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(FaBuFWActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("WoDeDPActivity--发布", s + "");
                try {
                    skillBefore = GsonUtils.parseJSON(s, Skill_Addbefore.class);
                    if (skillBefore.getStatus() == 1) {
                        if (skillBefore.getIsRealname() == 1) {
                            new AlertDialog.Builder(FaBuFWActivity.this)
                                    .setTitle("提示")
                                    .setMessage("未实名认证，请先进行实名认证！")
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    })
                                    .setCancelable(false)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which){
                                            dialog.dismiss();
                                            Intent intent = new Intent();
                                            intent.setClass(FaBuFWActivity.this, ShiMingRZActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                        if (skillBefore.getIsFace()==1){
                            new AlertDialog.Builder(FaBuFWActivity.this)
                                    .setTitle("提示")
                                    .setMessage(skillBefore.getTips())
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    })
                                    .setCancelable(false)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which){
                                            dialog.dismiss();
                                            Intent intent = new Intent();
                                            intent.setClass(FaBuFWActivity.this, FaceLivenessExpActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                        textTips.setText(skillBefore.getNotice());
                        if (TextUtils.isEmpty(skillBefore.getImg())) {
                            viewImg.setVisibility(View.VISIBLE);
                        } else {
                            viewImg.setVisibility(View.GONE);
                            GlideApp.with(FaBuFWActivity.this)
                                    .asBitmap()
                                    .load(skillBefore.getImg())
                                    .dontAnimate()
                                    .placeholder(R.mipmap.ic_empty)
                                    .into(imageImg);
                        }
                        textPinLei.setText(skillBefore.getCidName());
                        textDiQu.setText(skillBefore.getCityName());
                        edit01.setText(skillBefore.getName());
                        edit02.setText(skillBefore.getAddress());
                        edit03.setText(skillBefore.getPrice());
                        edit04.setText(skillBefore.getContact());
                        edit05.setText(skillBefore.getIntro());
                        imageId = skillBefore.getImg_id();
                        cityId = skillBefore.getCityId();
                    } else if (skillBefore.getStatus() == 3) {
                        MyDialog.showReLoginDialog(FaBuFWActivity.this);
                    } else {
                        Toast.makeText(FaBuFWActivity.this, skillBefore.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(FaBuFWActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(FaBuFWActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.imageDelete:
                viewTips.setVisibility(View.GONE);
                break;
            case R.id.viewImgAdd:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .compress(true)// 是否压缩 true or false
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.viewXuanZhePL:
                ArrayList<String> item = new ArrayList<>();
                for (int i = 1; i < skillBefore.getCate().size(); i++) {
                    item.add(skillBefore.getCate().get(i).getName());
                }
                new MaterialDialog.Builder(this)
                        .title("选择经营品类")
                        .items(item)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                dialog.dismiss();
                                cid = skillBefore.getCate().get(position).getId() + "";
                                textPinLei.setText(skillBefore.getCate().get(position).getName());
                            }
                        })
                        .show();
                break;
            case R.id.viewDiQu:
                intent.setClass(this, ChengShiXZActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
            case R.id.btnFaBu:
                if (MyApplication.getInstance().images.size()==0){
                    new AlertDialog.Builder(FaBuFWActivity.this)
                            .setTitle("提示")
                            .setMessage(skillBefore.getTips())
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            })
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    dialog.dismiss();
                                    Intent intent = new Intent();
                                    intent.setClass(FaBuFWActivity.this, FaceLivenessExpActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .create()
                            .show();
                    return;
                }
                addAfter();
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
    private OkObject getOkObjectAdd() {
        String url = Constant.HOST + Constant.Url.SKILL_ADDAFTER;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("cid", cid);
        params.put("cityId", cityId);
        params.put("name", edit01.getText().toString());
        params.put("address", edit02.getText().toString());
        params.put("lng", lng);
        params.put("lat", lat);
        params.put("price", edit03.getText().toString());
        params.put("contact", edit04.getText().toString());
        params.put("intro", edit05.getText().toString());
        params.put("img_id", imageId);
        params.put("id", id);
        params.put("faceImgs", MyApplication.getInstance().images.toString().replace("[","").replace("]",""));
        return new OkObject(params, url);
    }

    private void addAfter() {
        showLoadingDialog();
        ApiClient.post(FaBuFWActivity.this, getOkObjectAdd(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("WoDeDPActivity--发布", s + "");
                try {
                    AddAfter addAfter = GsonUtils.parseJSON(s, AddAfter.class);
                    if (addAfter.getStatus() == 1) {
                        MyDialog.dialogFinish(FaBuFWActivity.this, addAfter.getInfo());
                    } else if (addAfter.getStatus() == 3) {
                        MyDialog.showReLoginDialog(FaBuFWActivity.this);
                    } else {
                        Toast.makeText(FaBuFWActivity.this, addAfter.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(FaBuFWActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(FaBuFWActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            // 图片选择结果回调
            List<LocalMedia> selectList1 = PictureSelector.obtainMultipleResult(data);
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
            String image = selectList1.get(0).getCompressPath();
            LogUtil.LogShitou("RenZhengFragment--onActivityResult", "" + image);
            GlideApp.with(FaBuFWActivity.this)
                    .load(image)
                    .placeholder(R.mipmap.ic_empty)
                    .into(imageImg);
            viewImg.setVisibility(View.GONE);
            showLoadingDialog();
            HashMap<String, String> params = new HashMap<>();
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
            params.put("code", "");
            params.put("brand", "android");
            params.put("img", ImgToBase64.toBase64(image));
            JSONObject jsonObject = new JSONObject(params);
            OkGo.post(Constant.HOST + Constant.Url.RESPOND_APPIMGADD)//
                    .tag(this)//
                    .upJson(jsonObject.toString())//
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            cancelLoadingDialog();
                            try {
                                RespondAppimgadd respondAppimgadd = GsonUtils.parseJSON(s, RespondAppimgadd.class);
                                if (respondAppimgadd.getStatus() == 1) {
                                    Log.e("MyHomeActivity", "MyHomeActivity--onSuccess--发布服务" + respondAppimgadd.getImg());
                                    imageId = respondAppimgadd.getImgId() + "";
                                } else if (respondAppimgadd.getStatus() == 1) {
                                    MyDialog.showReLoginDialog(FaBuFWActivity.this);
                                } else {
                                    Toast.makeText(FaBuFWActivity.this, respondAppimgadd.getInfo(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(FaBuFWActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            cancelLoadingDialog();
                            Toast.makeText(FaBuFWActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private void requestPermissions(int requestCode, String permission) {
        if (permission != null && permission.length() > 0) {
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    // 检查是否有权限
                    int hasPer = checkSelfPermission(permission);
                    if (hasPer != PackageManager.PERMISSION_GRANTED) {
                        // 是否应该显示权限请求
                        boolean isShould = shouldShowRequestPermissionRationale(permission);
                        requestPermissions(new String[]{permission}, requestCode);
                    }
                } else {

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        boolean flag = false;
        for (int i = 0; i < permissions.length; i++) {
            if (PackageManager.PERMISSION_GRANTED == grantResults[i]) {
                flag = true;
            }
        }
        if (!flag) {
            requestPermissions(99, Manifest.permission.CAMERA);
        }
    }
}
