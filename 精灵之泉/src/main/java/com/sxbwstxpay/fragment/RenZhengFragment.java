package com.sxbwstxpay.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.imagepicker.ImagePicker;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.ShiMingRZActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseFragment;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.RotateTransformation;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.RespondAppimgadd;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.model.UserCardbefore;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.CheckIdCard;
import com.sxbwstxpay.util.GlideApp;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.ImgToBase64;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.PicassoImageLoader;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class RenZhengFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;
    private View mRelaTitleStatue;
    private View viewTianXinXi;
    private ScrollView scrollView;
    private View viewShiMingRZ;
    private Button buttonSms;
    private Button buttonNext;
    private EditText editPhone;
    private TextView textTip;
    private UserCardbefore userCardbefore;
    private View viewKaiHuYH;
    private EditText editName;
    private EditText editCard;
    private TextView textBankName;
    private EditText editBankCard;
    private EditText editCode;
    private Runnable mR;
    private int[] mI;
    private String mPhone_sms;
    private ImageView image01;
    private ImageView image02;
    private ImageView image04;
    private ImageView image05;
    private ImagePicker mImagePicker;
    private String[] path;
    private Button buttonTiJiao;
    private ImageView imageRight;
    private View viewYanZhengMa;
    //是否认证
    private int verify = 0;
    //能否点击下一步
    private int submitStatus;
    private EditText editWXHao;
    private EditText editYouXiang;
    private EditText editZhiHang;
    private ProgressDialog progressDialog;
    //上传的图片数量
    private int imgNum = 4;
    private int choosePistion;

    public RenZhengFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_ren_zheng, container, false);
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
        path = new String[imgNum];
        mRelaTitleStatue = mInflate.findViewById(R.id.relaTitleStatue);
        viewTianXinXi = mInflate.findViewById(R.id.viewTianXinXi);
        scrollView = (ScrollView) mInflate.findViewById(R.id.scrollView);
        viewShiMingRZ = mInflate.findViewById(R.id.viewShiMingRZ);
        buttonSms = (Button) mInflate.findViewById(R.id.buttonSms);
        buttonNext = (Button) mInflate.findViewById(R.id.buttonNext);
        editPhone = (EditText) mInflate.findViewById(R.id.editPhone);
        textTip = (TextView) mInflate.findViewById(R.id.textTip);
        viewKaiHuYH = mInflate.findViewById(R.id.viewKaiHuYH);
        editName = (EditText) mInflate.findViewById(R.id.editName);
        editCard = (EditText) mInflate.findViewById(R.id.editCard);
        textBankName = (TextView) mInflate.findViewById(R.id.textBankName);
        editBankCard = (EditText) mInflate.findViewById(R.id.editBankCard);
        editCode = (EditText) mInflate.findViewById(R.id.editCode);
        image01 = (ImageView) mInflate.findViewById(R.id.image01);
        image02 = (ImageView) mInflate.findViewById(R.id.image02);
        image04 = (ImageView) mInflate.findViewById(R.id.image04);
        image05 = (ImageView) mInflate.findViewById(R.id.image05);
        buttonTiJiao = (Button) mInflate.findViewById(R.id.buttonTiJiao);
        imageRight = (ImageView) mInflate.findViewById(R.id.imageRight);
        viewYanZhengMa = mInflate.findViewById(R.id.viewYanZhengMa);
        editWXHao = (EditText) mInflate.findViewById(R.id.editWXHao);
        editYouXiang = (EditText) mInflate.findViewById(R.id.editYouXiang);
        editZhiHang = (EditText) mInflate.findViewById(R.id.editZhiHang);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = mRelaTitleStatue.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(mContext));
        mRelaTitleStatue.setLayoutParams(layoutParams);
        mRelaTitleStatue.setPadding(0, ScreenUtils.getStatusBarHeight(mContext), 0, 0);
        mImagePicker = ImagePicker.getInstance();
        mImagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        mImagePicker.setShowCamera(true);  //显示拍照按钮
        mImagePicker.setSaveRectangle(true); //是否按矩形区域保存
        mImagePicker.setMultiMode(false);
    }

    @Override
    protected void setListeners() {
        buttonNext.setOnClickListener(this);
        buttonSms.setOnClickListener(this);
        viewKaiHuYH.setOnClickListener(this);
        image01.setOnClickListener(this);
        image02.setOnClickListener(this);
        image04.setOnClickListener(this);
        image05.setOnClickListener(this);
        buttonTiJiao.setOnClickListener(this);
        mInflate.findViewById(R.id.imageBack).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:00
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_CARDBEFORE;
        HashMap<String, String> params = new HashMap<>();
        if (userInfo!=null){
            params.put("uid", userInfo.getUid() + "");
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * 刷新第一面界面里的view是不是可操作
         */
        if (verify != 1 && scrollView.getVisibility() != View.VISIBLE) {
            showLoadingDialog();
            ApiClient.post(mContext, getOkObject(), new ApiClient.CallBack() {
                @Override
                public void onSuccess(String s) {
                    cancelLoadingDialog();
                    LogUtil.LogShitou("RenZhengFragment--会员身份认证请求", "" + s);
                    try {
                        UserCardbefore userCardbefore1 = GsonUtils.parseJSON(s, UserCardbefore.class);
                        if (userCardbefore1.getStatus() == 1) {
                            textTip.setText(userCardbefore1.getTipsText());
                            submitStatus = userCardbefore1.getSubmitStatus();
                            if (submitStatus == 1) {
                                editName.setEnabled(true);
                                editCard.setEnabled(true);
                                viewKaiHuYH.setEnabled(true);
                                editBankCard.setEnabled(true);
                                editPhone.setEnabled(true);
                                editCode.setEnabled(true);
                                editWXHao.setEnabled(true);
                                editYouXiang.setEnabled(true);
                                editZhiHang.setEnabled(true);
                                buttonSms.setEnabled(true);
                                image01.setEnabled(true);
                                image02.setEnabled(true);
                                image04.setEnabled(true);
                                image05.setEnabled(true);
                            } else {
                                editName.setEnabled(false);
                                editCard.setEnabled(false);
                                viewKaiHuYH.setEnabled(false);
                                editBankCard.setEnabled(false);
                                editPhone.setEnabled(false);
                                editCode.setEnabled(false);
                                editWXHao.setEnabled(false);
                                editYouXiang.setEnabled(false);
                                editZhiHang.setEnabled(false);
                                buttonSms.setEnabled(false);
                                image01.setEnabled(false);
                                image02.setEnabled(false);
                                image04.setEnabled(false);
                                image05.setEnabled(false);
                            }
                            verify = userCardbefore1.getVerify();
                            if (verify == 1) {
                                imageRight.setVisibility(View.GONE);
                                viewYanZhengMa.setVisibility(View.GONE);
                                buttonNext.setVisibility(View.GONE);
                            } else {
                                if (!TextUtils.isEmpty(userCardbefore1.getTipsText())) {
                                    MyDialog.showTipDialog(mContext, userCardbefore1.getTipsText());
                                }
                                imageRight.setVisibility(View.VISIBLE);
                                viewYanZhengMa.setVisibility(View.VISIBLE);
                                buttonNext.setVisibility(View.VISIBLE);
                            }
                        } else if (userCardbefore1.getStatus() == 3) {
                            MyDialog.showReLoginDialog(mContext);
                        } else {
                            Toast.makeText(mContext, userCardbefore1.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        try {
                            Toast.makeText(mContext, "数据出错", Toast.LENGTH_SHORT).show();
                        } catch (Exception e1) {
                        }
                    }
                }

                @Override
                public void onError(Response response) {
                    try {
                        cancelLoadingDialog();
                        Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                }
            });
        }
        ((ShiMingRZActivity) mContext).isChoosePic = false;
    }

    @Override
    protected void initData() {
        /**
         * 刷新
         */
        showLoadingDialog();
        ApiClient.post(mContext, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("RenZhengFragment--会员身份认证请求", "" + s);
                try {
                    userCardbefore = GsonUtils.parseJSON(s, UserCardbefore.class);
                    if (userCardbefore.getStatus() == 1) {
                        textTip.setText(userCardbefore.getTipsText());
                        editName.setText(userCardbefore.getData().getName());
                        editCard.setText(userCardbefore.getData().getCard());
                        textBankName.setText(userCardbefore.getData().getBankName());
                        editBankCard.setText(userCardbefore.getData().getBankCard());
                        editPhone.setText(userCardbefore.getData().getPhone());
                        editWXHao.setText(userCardbefore.getData().getWeixin());
                        editYouXiang.setText(userCardbefore.getData().getEmail());
                        editZhiHang.setText(userCardbefore.getData().getSubbranch());
                        GlideApp.with(mContext)
                                .load(userCardbefore.getData().getImg())
                                .transform(new RotateTransformation(mContext, -90))
                                .placeholder(R.mipmap.shenfenzhengmian)
                                .into(image01);
                        GlideApp.with(mContext)
                                .load(userCardbefore.getData().getImg2())
                                .transform(new RotateTransformation(mContext, -90))
                                .placeholder(R.mipmap.shenfenbeimain)
                                .into(image02);
                        GlideApp.with(mContext)
                                .load(userCardbefore.getData().getImg4())
                                .transform(new RotateTransformation(mContext, -90))
                                .placeholder(R.mipmap.yinhangkazhengmian)
                                .into(image04);
                        GlideApp.with(mContext)
                                .load(userCardbefore.getData().getImg5())
                                .transform(new RotateTransformation(mContext, -90))
                                .placeholder(R.mipmap.shouchibanshen)
                                .into(image05);
                        submitStatus = userCardbefore.getSubmitStatus();
//                        if (submitStatus == 1) {
//                            editName.setEnabled(true);
//                            editCard.setEnabled(true);
//                            viewKaiHuYH.setEnabled(true);
//                            editBankCard.setEnabled(true);
//                            editPhone.setEnabled(true);
//                            editCode.setEnabled(true);
//                            buttonSms.setEnabled(true);
//                            image01.setEnabled(true);
//                            image02.setEnabled(true);
//                            image04.setEnabled(true);
//                            image05.setEnabled(true);
//                        } else {
//                            editName.setEnabled(false);
//                            editCard.setEnabled(false);
//                            viewKaiHuYH.setEnabled(false);
//                            editBankCard.setEnabled(false);
//                            editPhone.setEnabled(false);
//                            editCode.setEnabled(false);
//                            buttonSms.setEnabled(false);
//                            image01.setEnabled(false);
//                            image02.setEnabled(false);
//                            image04.setEnabled(false);
//                            image05.setEnabled(false);
//                        }
//                        verify = userCardbefore.getVerify();
//                        if (verify == 1) {
//                            imageRight.setVisibility(View.GONE);
//                            viewYanZhengMa.setVisibility(View.GONE);
//                            buttonNext.setVisibility(View.GONE);
//                        } else {
//                            if (!TextUtils.isEmpty(userCardbefore.getTipsText())) {
//                                MyDialog.showTipDialog(mContext, userCardbefore.getTipsText());
//                            }
//                            imageRight.setVisibility(View.VISIBLE);
//                            viewYanZhengMa.setVisibility(View.VISIBLE);
//                            buttonNext.setVisibility(View.VISIBLE);
//                        }
                    } else {
                        Toast.makeText(mContext, userCardbefore.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
//            LogUtil.LogShitou("RenZhengFragment--onActivityResult", "111111111111" + ((MainActivity) mContext).isBackground);
//            ((MainActivity) mContext).isChoosePic = false;
//            switch (requestCode) {
//                case Constant.REQUEST_RESULT_CODE.IMG01:
//                    ArrayList<ImageItem> images01 = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                    path[0] = images01.get(0).path;
//                    GlideApp.with(RenZhengFragment.this)
//                            .load(path[0])
//                            .transform(new RotateTransformation(mContext, -90))
//                            .placeholder(R.mipmap.ic_empty)
//                            .into(image01);
//                    break;
//                case Constant.REQUEST_RESULT_CODE.IMG02:
//                    ArrayList<ImageItem> images02 = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                    path[1] = images02.get(0).path;
//                    GlideApp.with(RenZhengFragment.this)
//                            .load(path[1])
//                            .transform(new RotateTransformation(mContext, -90))
//                            .placeholder(R.mipmap.ic_empty)
//                            .into(image02);
//                    break;
//                case Constant.REQUEST_RESULT_CODE.IMG04:
//                    ArrayList<ImageItem> images04 = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                    path[2] = images04.get(0).path;
//                    GlideApp.with(RenZhengFragment.this)
//                            .load(path[2])
//                            .transform(new RotateTransformation(mContext, -90))
//                            .placeholder(R.mipmap.ic_empty)
//                            .into(image04);
//                    break;
//                case Constant.REQUEST_RESULT_CODE.IMG05:
//                    ArrayList<ImageItem> images05 = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                    path[3] = images05.get(0).path;
//                    GlideApp.with(RenZhengFragment.this)
//                            .load(path[3])
//                            .transform(new RotateTransformation(mContext, -90))
//                            .placeholder(R.mipmap.ic_empty)
//                            .into(image05);
//                    break;
//                default:
//
//                    break;
//            }
//        }
        LogUtil.LogShitou("RenZhengFragment--onActivityResult", "000000");
        if (resultCode == RESULT_OK&&requestCode==PictureConfig.CHOOSE_REQUEST) {
            LogUtil.LogShitou("RenZhengFragment--onActivityResult", "11111");
            switch (choosePistion) {
                case 0:
                    // 图片选择结果回调
                    List<LocalMedia> selectList1 = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    path[0] = selectList1.get(0).getCutPath();
                    LogUtil.LogShitou("RenZhengFragment--onActivityResult", ""+path[0]);
                    GlideApp.with(RenZhengFragment.this)
                            .load(path[0])
                            .transform(new RotateTransformation(mContext, -90))
                            .placeholder(R.mipmap.ic_empty)
                            .into(image01);
                    break;
                case 1:
                    // 图片选择结果回调
                    List<LocalMedia> selectList2 = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    path[1] = selectList2.get(0).getCutPath();
                    GlideApp.with(RenZhengFragment.this)
                            .load(path[1])
                            .transform(new RotateTransformation(mContext, -90))
                            .placeholder(R.mipmap.ic_empty)
                            .into(image02);
                    break;
                case 2:
                    // 图片选择结果回调
                    List<LocalMedia> selectList3 = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    path[2] = selectList3.get(0).getCutPath();
                    GlideApp.with(RenZhengFragment.this)
                            .load(path[2])
                            .transform(new RotateTransformation(mContext, -90))
                            .placeholder(R.mipmap.ic_empty)
                            .into(image04);
                    break;
                case 3:
                    // 图片选择结果回调
                    List<LocalMedia> selectList4 = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    path[3] = selectList4.get(0).getCutPath();
                    GlideApp.with(RenZhengFragment.this)
                            .load(path[3])
                            .transform(new RotateTransformation(mContext, -90))
                            .placeholder(R.mipmap.ic_empty)
                            .into(image05);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                getActivity().finish();
                break;
            case R.id.buttonTiJiao:
                if (submitStatus != 1) {
                    Toast.makeText(mContext, textTip.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(path[0]) && TextUtils.isEmpty(userCardbefore.getData().getImg())) {
                    Toast.makeText(mContext, "请选择身份证正面照", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(path[1]) && TextUtils.isEmpty(userCardbefore.getData().getImg2())) {
                    Toast.makeText(mContext, "请选择身份证背面照", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(path[2]) && TextUtils.isEmpty(userCardbefore.getData().getImg4())) {
                    Toast.makeText(mContext, "请选择银行卡正面照", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(path[3]) && TextUtils.isEmpty(userCardbefore.getData().getImg5())) {
                    Toast.makeText(mContext, "请选择手持银行卡和身份证半身照", Toast.LENGTH_SHORT).show();
                    return;
                }
                shangChuanPic();
                break;
            case R.id.image01:
                chooseTuPian(0);
                break;
            case R.id.image02:
                chooseTuPian(1);
                break;
            case R.id.image04:
                chooseTuPian(2);
                break;
            case R.id.image05:
                chooseTuPian(3);
                break;
            case R.id.viewKaiHuYH:
                CharSequence[] charSequence = new CharSequence[userCardbefore.getBank().size()];
                for (int i = 0; i < userCardbefore.getBank().size(); i++) {
                    charSequence[i] = userCardbefore.getBank().get(i).getName();
                }
                new AlertDialog.Builder(mContext).setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserCardbefore.BankBean bankBean = userCardbefore.getBank().get(which);
                        textBankName.setText(bankBean.getName());
                        userCardbefore.getData().setBankName(bankBean.getName());
                        userCardbefore.getData().setBank(bankBean.getId());
                    }
                }).show();
                break;
            case R.id.buttonSms:
                sendSMS();
                break;
            case R.id.buttonNext:
                if (submitStatus != 1) {
                    if (!TextUtils.isEmpty(userCardbefore.getTipsText())){
                        MyDialog.showTipDialog(mContext,userCardbefore.getTipsText());
                    }
                    return;
                }
                if (TextUtils.isEmpty(editName.getText().toString().trim())) {
                    Toast.makeText(mContext, "请输入真实姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                CheckIdCard checkIdCard = new CheckIdCard(editCard.getText().toString().trim());
                if (!checkIdCard.validate()) {
                    Toast.makeText(mContext, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(editYouXiang.getText().toString().toString().trim())){
                    if (!StringUtil.checkEmail(editYouXiang.getText().toString().toString().trim())) {
                        Toast.makeText(mContext, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (TextUtils.isEmpty(userCardbefore.getData().getBankName()) || userCardbefore.getData().getBank() <= 0) {
                    Toast.makeText(mContext, "请选择开户银行", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editBankCard.getText().toString().trim())) {
                    Toast.makeText(mContext, "请输入本人提现储蓄卡号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!StringUtil.isMobileNO(editPhone.getText().toString().trim())) {
                    Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editCode.getText().toString().trim())) {
                    Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                userCardbefore.getData().setName(editName.getText().toString().trim());
                userCardbefore.getData().setCard(editCard.getText().toString().trim());
                userCardbefore.getData().setBankCard(editBankCard.getText().toString().trim());
                userCardbefore.getData().setPhone(editPhone.getText().toString().trim());
                userCardbefore.getData().setWeixin(editWXHao.getText().toString().trim());
                userCardbefore.getData().setEmail(editYouXiang.getText().toString().trim());
                userCardbefore.getData().setSubbranch(editZhiHang.getText().toString().trim());
                next();
//                viewShiMingRZ.setBackgroundResource(R.mipmap.shimingtop2);
//                viewTianXinXi.setVisibility(View.GONE);
//                scrollView.setVisibility(View.VISIBLE);
                break;
            default:

                break;
        }
    }

    private void shangChuanPic() {
        final boolean[] isBreak = {false};
        progressDialog = new ProgressDialog(mContext);
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
                    new AlertDialog.Builder(mContext)
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
        final List<Integer> imgList = new ArrayList<>();
        for (int i = 0; i < imgNum; i++) {
            imgList.add(0);
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
                        ApiClient.post(mContext, getOkObject4(finalI), new ApiClient.CallBack() {
                            @Override
                            public void onSuccess(String s) {
                                LogUtil.LogShitou("RenZhengFragment--单个图片上传返回", "" + s);
                                try {
                                    RespondAppimgadd respondAppimgadd = GsonUtils.parseJSON(s, RespondAppimgadd.class);
                                    if (respondAppimgadd.getStatus() == 1) {
                                        count[0]++;
                                        progressDialog.setProgress(count[0]);
                                        progressDialog.setMessage("已上传" + count[0] + "/" + imgNum);
                                        imgList.set(finalI, respondAppimgadd.getImgId());
                                        if (count[0] == imgNum) {
                                            progressDialog.dismiss();
                                            userCardbefore.getData().setImgId(imgList.get(0));
                                            userCardbefore.getData().setImgId2(imgList.get(1));
                                            userCardbefore.getData().setImgId4(imgList.get(2));
                                            userCardbefore.getData().setImgId5(imgList.get(3));
                                            tiJiao();
                                        }
                                    } else if (respondAppimgadd.getStatus() == 3) {
                                        MyDialog.showReLoginDialog(mContext);
                                    } else {
                                        Toast.makeText(mContext, respondAppimgadd.getInfo(), Toast.LENGTH_SHORT).show();
                                        isBreak[0] = false;
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(mContext, "数据出错", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Response response) {
                                Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                }).start();
            } else {
                switch (i) {
                    case 0:
                        imgList.set(i, userCardbefore.getData().getImgId());
                        break;
                    case 1:
                        imgList.set(i, userCardbefore.getData().getImgId2());
                        break;
                    case 2:
                        imgList.set(i, userCardbefore.getData().getImgId4());
                        break;
                    case 3:
                        imgList.set(i, userCardbefore.getData().getImgId5());
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

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:00
     */
    private OkObject getOkObject3() {
        String url = Constant.HOST + Constant.Url.USER_CARDADD;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid() + "");
        params.put("tokenTime", tokenTime);
        params.put("card", userCardbefore.getData().getCard());
        params.put("phone", userCardbefore.getData().getPhone());
        params.put("name", userCardbefore.getData().getName());
        params.put("bankCard", userCardbefore.getData().getBankCard());
        params.put("bank", userCardbefore.getData().getBank() + "");
        params.put("email", userCardbefore.getData().getEmail());
        params.put("weixin", userCardbefore.getData().getWeixin());
        params.put("subbranch", userCardbefore.getData().getSubbranch());
        params.put("imgId", userCardbefore.getData().getImgId() + "");
        params.put("imgId2", userCardbefore.getData().getImgId2() + "");
        params.put("imgId4", userCardbefore.getData().getImgId4() + "");
        params.put("imgId5", userCardbefore.getData().getImgId5() + "");
        return new OkObject(params, url);
    }

    private void tiJiao() {
        showLoadingDialog();
        ApiClient.post(mContext, getOkObject3(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("RenZhengFragment--提交", s + "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    Toast.makeText(mContext, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    if (simpleInfo.getStatus() == 1) {
                        viewTianXinXi.setVisibility(View.VISIBLE);
                        scrollView.setVisibility(View.GONE);
                        viewShiMingRZ.setBackgroundResource(R.mipmap.shimingtop1);
                        //// TODO: 2017/9/12 0012 弹窗
                        onResume();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:00
     */
    private OkObject getOkObject2() {
        String url = Constant.HOST + Constant.Url.LOGIN_BINDNEXT;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid() + "");
        params.put("tokenTime", tokenTime);
        params.put("userName", mPhone_sms);
        params.put("code", editCode.getText().toString().trim());
        return new OkObject(params, url);
    }

    private void next() {
        showLoadingDialog();
        ApiClient.post(mContext, getOkObject2(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("RenZhengFragment--实名认证下一步", "" + s);
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus() == 1) {
                        viewShiMingRZ.setBackgroundResource(R.mipmap.shimingtop2);
                        viewTianXinXi.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(mContext, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void chooseTuPian(int requestCode) {
        ((ShiMingRZActivity) mContext).isChoosePic = true;
//        mImagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
//        mImagePicker.setCrop(true);        //允许裁剪（单选才有效）
//        float width = ScreenUtils.getScreenWidth(mContext);
//        int focusWidth = (int) width - (int) DpUtils.convertDpToPixel(60f, mContext);
//        mImagePicker.setFocusWidth(focusWidth);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        mImagePicker.setFocusHeight((int) (focusWidth * 1.6f));  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        mImagePicker.setOutPutX(500);//保存文件的宽度。单位像素
//        mImagePicker.setOutPutY(800);//保存文件的高度。单位像素
//        Intent intent = new Intent();
//        intent.setClass(mContext, ImageGridActivity.class);
//        startActivityForResult(intent, requestCode);
        choosePistion = requestCode;
        PictureSelector.create(RenZhengFragment.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .withAspectRatio(5, 8)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .cropCompressQuality(100)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * des： 短信发送按钮状态
     * author： ZhangJieBo
     * date： 2017/8/22 0022 上午 10:26
     */
    private void sendSMS() {
        buttonSms.removeCallbacks(mR);
        boolean mobileNO = StringUtil.isMobileNO(editPhone.getText().toString().trim());
        if (mobileNO) {
            mPhone_sms = editPhone.getText().toString().trim();
            buttonSms.setEnabled(false);
            mI = new int[]{60};

            mR = new Runnable() {
                @Override
                public void run() {
                    buttonSms.setText((mI[0]--) + "秒后重发");
                    if (mI[0] == 0) {
                        buttonSms.setEnabled(true);
                        buttonSms.setText("重新发送");
                        return;
                    } else {

                    }
                    buttonSms.postDelayed(mR, 1000);
                }
            };
            buttonSms.postDelayed(mR, 0);
            getSms();
        } else {
            Toast.makeText(mContext, "输入正确的手机号", Toast.LENGTH_SHORT).show();
            editPhone.setText("");
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:00
     */
    private OkObject getOkObject1() {
        String url = Constant.HOST + Constant.Url.LOGIN_BINDSMS;
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", mPhone_sms);
        params.put("type", "0");
        return new OkObject(params, url);
    }


    /**
     * des： 获取短信
     * author： ZhangJieBo
     * date： 2017/9/11 0011 下午 4:32
     */
    private void getSms() {
        showLoadingDialog();
        ApiClient.post(mContext, getOkObject1(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("RenZhengFragment--获取短信", "" + s);
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    Toast.makeText(mContext, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    if (simpleInfo.getStatus() == 1) {

                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        if (scrollView.getVisibility() == View.VISIBLE) {
            viewTianXinXi.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            viewShiMingRZ.setBackgroundResource(R.mipmap.shimingtop1);
            return true;
        } else {
            return super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
