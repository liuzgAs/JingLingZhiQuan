package com.sxbwstxpay.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.ProvinceBean;
import com.sxbwstxpay.model.RespondAppimgadd;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.model.StoreCardbefore;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GetJsonDataUtil;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.ImgToBase64;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.util.StringUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShangJiaRZActivity extends ZjbBaseActivity implements View.OnClickListener {


    private View viewBar;
    private ImageView imageDianPuLogo;
    private ImageView imageZhiZhao;
    private ImageView imageShenFenZhengZheng;
    private ImageView imageShenFenZhengFan;
    private int choosePistion;
    private String[] path;
    private int imgNum = 4;
    private TextView textTipsText;
    private TextView textCate;
    private TextView textArea;
    private EditText editAddress;
    private EditText editName;
    private EditText editContact;
    private EditText editIntro;
    private List<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private List<ProvinceBean> jsonBean;
    private List<StoreCardbefore.CateBean> cateBeanList;
    private String cateId = "";
    private ProgressDialog progressDialog;
    private StoreCardbefore storeCardbefore;
    private View viewArea;
    private View viewCate;
    private View btnTiJiao;
    private EditText editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shang_jia_rz);
        init(ShangJiaRZActivity.class);
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initSP() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 写子线程中的操作,解析省市区数据
                initJsonData();
            }
        }).start();
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        imageDianPuLogo = (ImageView) findViewById(R.id.imageDianPuLogo);
        imageZhiZhao = (ImageView) findViewById(R.id.imageZhiZhao);
        imageShenFenZhengZheng = (ImageView) findViewById(R.id.imageShenFenZhengZheng);
        imageShenFenZhengFan = (ImageView) findViewById(R.id.imageShenFenZhengFan);
        textTipsText = (TextView) findViewById(R.id.textTipsText);
        textCate = (TextView) findViewById(R.id.textCateName);
        textArea = (TextView) findViewById(R.id.textArea);
        editAddress = (EditText) findViewById(R.id.editAddress);
        editName = (EditText) findViewById(R.id.editName);
        editContact = (EditText) findViewById(R.id.editContact);
        editIntro = (EditText) findViewById(R.id.editIntro);
        viewArea = findViewById(R.id.viewArea);
        viewCate = findViewById(R.id.viewCate);
        btnTiJiao = findViewById(R.id.btnTiJiao);
        editPhone = (EditText) findViewById(R.id.editPhone);
    }

    @Override
    protected void initViews() {
        path = new String[imgNum];
        ((TextView) findViewById(R.id.textViewTitle)).setText("商家认证");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        viewArea.setOnClickListener(this);
        viewCate.setOnClickListener(this);
        btnTiJiao.setOnClickListener(this);
        imageDianPuLogo.setOnClickListener(this);
        imageZhiZhao.setOnClickListener(this);
        imageShenFenZhengZheng.setOnClickListener(this);
        imageShenFenZhengFan.setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.STORE_CARDBEFORE;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(ShangJiaRZActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ShangJiaRZActivity--onSuccess", s + "");
                try {
                    storeCardbefore = GsonUtils.parseJSON(s, StoreCardbefore.class);
                    if (storeCardbefore.getStatus() == 1) {
                        String tipsText = storeCardbefore.getTipsText();
                        if (TextUtils.isEmpty(tipsText)) {
                            textTipsText.setVisibility(View.GONE);
                        } else {
                            textTipsText.setVisibility(View.VISIBLE);
                            textTipsText.setText(tipsText);
                        }
                        cateBeanList = storeCardbefore.getCate();
                        textCate.setText(storeCardbefore.getData().getCateName());
                        textArea.setText(storeCardbefore.getData().getArea());
                        editAddress.setText(storeCardbefore.getData().getAddress());
                        editName.setText(storeCardbefore.getData().getName());
                        editContact.setText(storeCardbefore.getData().getContact());
                        editIntro.setText(storeCardbefore.getData().getIntro());
                        editPhone.setText(storeCardbefore.getData().getPhone());
                        if (!TextUtils.isEmpty(storeCardbefore.getData().getImg())) {
                            GlideApp.with(ShangJiaRZActivity.this)
                                    .load(storeCardbefore.getData().getImg())
                                    .centerCrop()
                                    .placeholder(R.mipmap.ic_empty)
                                    .into(imageDianPuLogo);
                        }
                        if (!TextUtils.isEmpty(storeCardbefore.getData().getImg2())) {
                            GlideApp.with(ShangJiaRZActivity.this)
                                    .load(storeCardbefore.getData().getImg2())
                                    .centerCrop()
                                    .placeholder(R.mipmap.ic_empty)
                                    .into(imageZhiZhao);
                        }
                        if (!TextUtils.isEmpty(storeCardbefore.getData().getImg3())) {
                            GlideApp.with(ShangJiaRZActivity.this)
                                    .load(storeCardbefore.getData().getImg3())
                                    .centerCrop()
                                    .placeholder(R.mipmap.ic_empty)
                                    .into(imageShenFenZhengZheng);
                        }
                        if (!TextUtils.isEmpty(storeCardbefore.getData().getImg4())) {
                            GlideApp.with(ShangJiaRZActivity.this)
                                    .load(storeCardbefore.getData().getImg4())
                                    .centerCrop()
                                    .placeholder(R.mipmap.ic_empty)
                                    .into(imageShenFenZhengFan);
                        }
                        if (storeCardbefore.getSubmitStatus() == 1) {
                            viewArea.setEnabled(true);
                            viewCate.setEnabled(true);
                            btnTiJiao.setVisibility(View.VISIBLE);
                            editAddress.setEnabled(true);
                            editName.setEnabled(true);
                            editContact.setEnabled(true);
                            editIntro.setEnabled(true);
                            editPhone.setEnabled(true);
                            imageDianPuLogo.setEnabled(true);
                            imageZhiZhao.setEnabled(true);
                            imageShenFenZhengZheng.setEnabled(true);
                            imageShenFenZhengFan.setEnabled(true);
                        } else {
                            viewArea.setEnabled(false);
                            viewCate.setEnabled(false);
                            btnTiJiao.setVisibility(View.GONE);
                            editAddress.setEnabled(false);
                            editName.setEnabled(false);
                            editContact.setEnabled(false);
                            editIntro.setEnabled(false);
                            editPhone.setEnabled(false);
                            imageDianPuLogo.setEnabled(false);
                            imageZhiZhao.setEnabled(false);
                            imageShenFenZhengZheng.setEnabled(false);
                            imageShenFenZhengFan.setEnabled(false);
                        }
                    } else if (storeCardbefore.getStatus() == 3) {
                        MyDialog.showReLoginDialog(ShangJiaRZActivity.this);
                    } else {
                        MyDialog.dialogFinish(ShangJiaRZActivity.this, storeCardbefore.getInfo());
                    }
                } catch (Exception e) {
                    Toast.makeText(ShangJiaRZActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(ShangJiaRZActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTiJiao:
                if (TextUtils.isEmpty(cateId)) {
                    Toast.makeText(ShangJiaRZActivity.this, "请选择经营品类", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(textArea.getText().toString().trim())) {
                    Toast.makeText(ShangJiaRZActivity.this, "请选择地区", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editAddress.getText().toString().trim())) {
                    Toast.makeText(ShangJiaRZActivity.this, "请输入商户地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editName.getText().toString().trim())) {
                    Toast.makeText(ShangJiaRZActivity.this, "请输入商户名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editContact.getText().toString().trim())) {
                    Toast.makeText(ShangJiaRZActivity.this, "请输入联系人", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!StringUtil.isMobileNO(editPhone.getText().toString().trim())) {
                    Toast.makeText(ShangJiaRZActivity.this, "请输入正确的联系人电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editIntro.getText().toString().trim())) {
                    Toast.makeText(ShangJiaRZActivity.this, "请输入商铺描述", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(path[0]) && TextUtils.isEmpty(storeCardbefore.getData().getImg())) {
                    Toast.makeText(ShangJiaRZActivity.this, "请选择店铺logo", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(path[1]) && TextUtils.isEmpty(storeCardbefore.getData().getImg2())) {
                    Toast.makeText(ShangJiaRZActivity.this, "请选择营业执照", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(path[2]) && TextUtils.isEmpty(storeCardbefore.getData().getImg3())) {
                    Toast.makeText(ShangJiaRZActivity.this, "请选择身份证正面照", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(path[3]) && TextUtils.isEmpty(storeCardbefore.getData().getImg4())) {
                    Toast.makeText(ShangJiaRZActivity.this, "请选择身份证反面照", Toast.LENGTH_SHORT).show();
                    return;
                }
                shangChuanPic();
                break;
            case R.id.viewArea:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            0);
                }
                /**
                 * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
                 */

                OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        textArea.setText(options1Items.get(options1).getPickerViewText() + "-" + options2Items.get(options1).get(options2) + "-" + options3Items.get(options1).get(options2).get(options3));
                    }
                })
                        .setTitleText("地区选择")
                        .setContentTextSize(20)//设置滚轮文字大小
                        .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                        .setSelectOptions(0, 0, 0)//默认选中项
                        .setBgColor(getResources().getColor(R.color.background))
                        .setTitleBgColor(getResources().getColor(R.color.white))
                        .setTitleColor(getResources().getColor(R.color.light_black))
                        .setCancelColor(getResources().getColor(R.color.text_gray))
                        .setSubmitColor(getResources().getColor(R.color.basic_color))
                        .setTextColorCenter(getResources().getColor(R.color.basic_color))
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setBackgroundId(0x66000000) //设置外部遮罩颜色
                        .build();

                //pvOptions.setSelectOptions(1,1);
        /*pvOptions.setPicker(options1Items);//一级选择器*/
//                pvOptions.setPicker(options1Items, options2Items);//二级选择器
                pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                pvOptions.show();
                break;
            case R.id.viewCate:
                final String[] strings = new String[cateBeanList.size()];
                for (int i = 0; i < cateBeanList.size(); i++) {
                    strings[i] = cateBeanList.get(i).getName();
                }
                new AlertDialog.Builder(this)
                        .setItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                textCate.setText(cateBeanList.get(i).getName());
                                cateId = cateBeanList.get(i).getId();
                            }
                        })
                        .show();
                break;
            case R.id.imageDianPuLogo:
                chooseTuPian(0);
                break;
            case R.id.imageZhiZhao:
                chooseTuPian(1);
                break;
            case R.id.imageShenFenZhengZheng:
                chooseTuPian(2);
                break;
            case R.id.imageShenFenZhengFan:
                chooseTuPian(3);
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
    private OkObject getTJOkObject() {
        String url = Constant.HOST + Constant.Url.STORE_CARDADD;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("cate", cateId);
        params.put("name", editName.getText().toString().trim());
        params.put("phone", editPhone.getText().toString().trim());
        params.put("contact", editContact.getText().toString().trim());
        params.put("address", editContact.getText().toString().trim());
        params.put("intro", editIntro.getText().toString().trim());
        params.put("area", textArea.getText().toString().trim());
        params.put("imgId", storeCardbefore.getData().getImgId());
        params.put("imgId2", storeCardbefore.getData().getImgId2());
        params.put("imgId3", storeCardbefore.getData().getImgId3());
        params.put("imgId4", storeCardbefore.getData().getImgId4());
        return new OkObject(params, url);
    }

    /**
     * 提交
     */
    private void tiJiao() {
        showLoadingDialog();
        ApiClient.post(ShangJiaRZActivity.this, getTJOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ShangJiaRZActivity--onSuccess", s + "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus() == 1) {
                        MyDialog.dialogFinish(ShangJiaRZActivity.this, simpleInfo.getInfo());
                    } else if (simpleInfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(ShangJiaRZActivity.this);
                    } else {
                        Toast.makeText(ShangJiaRZActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ShangJiaRZActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(ShangJiaRZActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:00
     */
    private OkObject getOkObject4(int i) {
        String url = Constant.HOST + Constant.Url.RESPOND_APPIMGADD;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("code", "card");
        params.put("img", ImgToBase64.toBase64(path[i]));
        params.put("brand", "android");
        return new OkObject(params, url);
    }

    private void shangChuanPic() {
        final boolean[] isBreak = {false};
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("上传图片");
        progressDialog.setMessage("已上传0/" + imgNum);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(imgNum);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    new AlertDialog.Builder(ShangJiaRZActivity.this)
                            .setTitle("是否取消上传")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    isBreak[0] = true;
                                    progressDialog.dismiss();
                                }
                            }).setNegativeButton("否", null)
                            .create()
                            .show();
                }
                return false;
            }
        });

        final int[] count = {0};
        final List<String> imgList = new ArrayList<>();
        for (int i = 0; i < imgNum; i++) {
            imgList.add("");
        }
        for (int i = 0; i < path.length; i++) {
            if (isBreak[0]) {
                break;
            }
            if (!TextUtils.isEmpty(path[i])) {
                final int finalI = i;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ApiClient.post(ShangJiaRZActivity.this, getOkObject4(finalI), new ApiClient.CallBack() {
                            @Override
                            public void onSuccess(String s) {
                                LogUtil.LogShitou("RenZhengFragment--单个图片上传返回", "" + s);
                                try {
                                    RespondAppimgadd respondAppimgadd = GsonUtils.parseJSON(s, RespondAppimgadd.class);
                                    if (respondAppimgadd.getStatus() == 1) {
                                        count[0]++;
                                        progressDialog.setProgress(count[0]);
                                        progressDialog.setMessage("已上传" + count[0] + "/" + imgNum);
                                        imgList.set(finalI, String.valueOf(respondAppimgadd.getImgId()));
                                        if (count[0] == imgNum) {
                                            progressDialog.dismiss();
                                            storeCardbefore.getData().setImgId(imgList.get(0));
                                            storeCardbefore.getData().setImgId2(imgList.get(1));
                                            storeCardbefore.getData().setImgId3(imgList.get(2));
                                            storeCardbefore.getData().setImgId4(imgList.get(3));
                                            LogUtil.LogShitou("ShangJiaRZActivity--onSuccess", "upimg" + storeCardbefore.getData().getImg());
                                            LogUtil.LogShitou("ShangJiaRZActivity--onSuccess", "upimg" + storeCardbefore.getData().getImg2());
                                            LogUtil.LogShitou("ShangJiaRZActivity--onSuccess", "upimg" + storeCardbefore.getData().getImg3());
                                            LogUtil.LogShitou("ShangJiaRZActivity--onSuccess", "upimg" + storeCardbefore.getData().getImg4());
                                            tiJiao();
                                        }
                                    } else if (respondAppimgadd.getStatus() == 3) {
                                        MyDialog.showReLoginDialog(ShangJiaRZActivity.this);
                                    } else {
                                        Toast.makeText(ShangJiaRZActivity.this, respondAppimgadd.getInfo(), Toast.LENGTH_SHORT).show();
                                        isBreak[0] = false;
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(ShangJiaRZActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Response response) {
                                Toast.makeText(ShangJiaRZActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                }).start();
            } else {
                switch (i) {
                    case 0:
                        imgList.set(i, storeCardbefore.getData().getImgId());
                        break;
                    case 1:
                        imgList.set(i, storeCardbefore.getData().getImgId2());
                        break;
                    case 2:
                        imgList.set(i, storeCardbefore.getData().getImgId3());
                        break;
                    case 3:
                        imgList.set(i, storeCardbefore.getData().getImgId4());
                        break;
                }
                count[0]++;
                progressDialog.setProgress(count[0]);
                progressDialog.setMessage("已上传" + count[0] + "/" + imgNum);
                if (count[0] == imgNum) {
                    progressDialog.dismiss();
                    tiJiao();
                }

            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            switch (choosePistion) {
                case 0:
                    // 图片选择结果回调
                    List<LocalMedia> selectList1 = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    path[0] = selectList1.get(0).getCompressPath();
                    LogUtil.LogShitou("RenZhengFragment--onActivityResult", "" + path[0]);
                    GlideApp.with(ShangJiaRZActivity.this)
                            .load(path[0])
                            .placeholder(R.mipmap.ic_empty)
                            .into(imageDianPuLogo);
                    break;
                case 1:
                    // 图片选择结果回调
                    List<LocalMedia> selectList2 = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    path[1] = selectList2.get(0).getCompressPath();
                    GlideApp.with(ShangJiaRZActivity.this)
                            .load(path[1])
                            .placeholder(R.mipmap.ic_empty)
                            .into(imageZhiZhao);
                    break;
                case 2:
                    // 图片选择结果回调
                    List<LocalMedia> selectList3 = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    path[2] = selectList3.get(0).getCompressPath();
                    GlideApp.with(ShangJiaRZActivity.this)
                            .load(path[2])
                            .placeholder(R.mipmap.ic_empty)
                            .into(imageShenFenZhengZheng);
                    break;
                case 3:
                    // 图片选择结果回调
                    List<LocalMedia> selectList4 = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    path[3] = selectList4.get(0).getCompressPath();
                    GlideApp.with(ShangJiaRZActivity.this)
                            .load(path[3])
                            .placeholder(R.mipmap.ic_empty)
                            .into(imageShenFenZhengFan);
                    break;
                default:
                    break;
            }
        }
    }

    private void chooseTuPian(int requestCode) {
        isChoosePic = true;
        choosePistion = requestCode;
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .compress(true)// 是否压缩 true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        Type type = new TypeToken<ArrayList<ProvinceBean>>() {
        }.getType();
        jsonBean = GsonUtils.parseJSONArray(JsonData, type);

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCity().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCity().get(c).getArea() == null
                        || jsonBean.get(i).getCity().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCity().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCity().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
        LogUtil.LogShitou("XinZengDZActivity--options1Items", "" + options1Items.size());
        LogUtil.LogShitou("XinZengDZActivity--options2Items", "" + options2Items.size());
        LogUtil.LogShitou("XinZengDZActivity--options3Items", "" + options3Items.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
