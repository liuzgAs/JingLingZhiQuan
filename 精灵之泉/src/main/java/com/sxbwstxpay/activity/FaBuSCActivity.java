package com.sxbwstxpay.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.GridView4ScrollView;
import com.sxbwstxpay.model.IndexDataBean;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.RespondAppimgadd;
import com.sxbwstxpay.model.ShangChuanImg;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.ImgToBase64;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.PicassoImageLoader;
import com.sxbwstxpay.util.ScreenUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class FaBuSCActivity extends ZjbBaseActivity implements View.OnClickListener {

    private GridView4ScrollView mGridView;
    private View viewBar;
    private GridAdapter mAdapter;
    private ImagePicker mImagePicker;
    private List<ImageItem> mResults = new ArrayList<>();
    private int photoNum = 9;
    private View viewTuiJian;
    private View viewTuiJianEmpty;
    private ImageView imageImg;
    private TextView textTitle;
    private IndexDataBean indexDataBean;
    private EditText editXinDe;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_bu_sc);
        mImagePicker = ImagePicker.getInstance();
        mImagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        mImagePicker.setShowCamera(true);  //显示拍照按钮
        mImagePicker.setCrop(false);        //允许裁剪（单选才有效）
        mImagePicker.setSaveRectangle(true); //是否按矩形区域保存
        mImagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        mImagePicker.setFocusWidth(500);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        mImagePicker.setFocusHeight(500);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        mImagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        mImagePicker.setOutPutY(1000);//保存文件的高度。单位像素
        mImagePicker.setMultiMode(true);
        init(FaBuSCActivity.class);
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
        mGridView = (GridView4ScrollView) findViewById(R.id.gridView);
        viewTuiJian = findViewById(R.id.viewTuiJian);
        viewTuiJianEmpty = findViewById(R.id.viewTuiJianEmpty);
        imageImg = (ImageView) findViewById(R.id.imageImg);
        textTitle = (TextView) findViewById(R.id.textTitle);
        editXinDe = (EditText) findViewById(R.id.editXinDe);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("发布素材");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        mAdapter = new GridAdapter();
        mGridView.setAdapter(mAdapter);
        viewTuiJian.setVisibility(View.GONE);
        viewTuiJianEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setListeners() {
        viewTuiJian.setOnClickListener(this);
        viewTuiJianEmpty.setOnClickListener(this);
        findViewById(R.id.buttonFaBu).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mResults.add(new ImageItem());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constant.REQUEST_RESULT_CODE.IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mResults.remove(mResults.size() - 1);
                mResults.addAll(images);
                if (mResults.size() < photoNum) {
                    mResults.add(new ImageItem());
                }
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == Constant.REQUEST_RESULT_CODE.TuiJian && requestCode == Constant.REQUEST_RESULT_CODE.TuiJian) {
            viewTuiJian.setVisibility(View.VISIBLE);
            viewTuiJianEmpty.setVisibility(View.GONE);
            indexDataBean = (IndexDataBean) data.getSerializableExtra(Constant.INTENT_KEY.value);
            Glide.with(FaBuSCActivity.this)
                    .load(indexDataBean.getImg())
                    .asBitmap()
                    .placeholder(R.mipmap.ic_empty)
                    .into(imageImg);
            textTitle.setText(indexDataBean.getTitle());
        }
    }

    @Override
    public void onClick(View v) {
        final Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.buttonFaBu:
                if (indexDataBean == null) {
                    Toast.makeText(FaBuSCActivity.this, "请选择推荐商品", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editXinDe.getText().toString().trim())) {
                    Toast.makeText(FaBuSCActivity.this, "请填写您的心得", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mResults.size() == 1) {
                    Toast.makeText(FaBuSCActivity.this, "请选择素材照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                final int progress;
                if (mResults.size() == 9) {
                    if (mResults.get(8).path == null) {
                        progress = 8;
                    } else {
                        progress = 9;
                    }
                } else {
                    progress = mResults.size() - 1;
                }
                final boolean[] isBreak = {false};
                progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("上传素材中……");
                progressDialog.setMessage("上传" + "0/" + progress);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMax(progress);
                progressDialog.setCancelable(false);
                progressDialog.show();
                progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                            new AlertDialog.Builder(FaBuSCActivity.this)
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final int[] count = {0};
                        final List<String> imgList = new ArrayList<>();
                        for (int i = 0; i < mResults.size(); i++) {
                            if (isBreak[0]) {
                                break;
                            }
                            if (mResults.get(i).path != null) {
                                ApiClient.post(FaBuSCActivity.this, getPicObject(ImgToBase64.toBase64(mResults.get(i).path)), new ApiClient.CallBack() {
                                    @Override
                                    public void onSuccess(String s) {
                                        Log.e("ShangChuanPicActivity", "ShangChuanPicActivity--onSuccess--单个图片上传" + s);
                                        try {
                                            RespondAppimgadd respondAppimgadd = GsonUtils.parseJSON(s, RespondAppimgadd.class);
                                            if (respondAppimgadd.getStatus() == 1) {
                                                count[0]++;
                                                progressDialog.setProgress(count[0]);
                                                progressDialog.setMessage("已上传" + count[0] + "/" + progress);
                                                imgList.add(respondAppimgadd.getImgId() + "");
                                                if (count[0] == progress) {
                                                    progressDialog.dismiss();
                                                    showLoadingDialog();
                                                    ShangChuanImg shangChuanImg = new ShangChuanImg(editXinDe.getText().toString().trim(), indexDataBean.getId(), userInfo.getUid(), tokenTime, imgList);
                                                    ApiClient.postJson(FaBuSCActivity.this, Constant.HOST + Constant.Url.ITEM_ADDAFTER, GsonUtils.parseObject(shangChuanImg), new ApiClient.CallBack() {
                                                        @Override
                                                        public void onSuccess(String s) {
                                                            LogUtil.LogShitou("FaBuSCActivity--发布素材提交", s + "");
                                                            cancelLoadingDialog();
                                                            try {
                                                                SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                                                                if (simpleInfo.getStatus() == 1) {
                                                                    setResult(Constant.REQUEST_RESULT_CODE.BaoCun);
                                                                    finish();
                                                                } else if (simpleInfo.getStatus() == 3) {
                                                                    MyDialog.showReLoginDialog(FaBuSCActivity.this);
                                                                } else {
                                                                    Toast.makeText(FaBuSCActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            } catch (Exception e) {
                                                                Toast.makeText(FaBuSCActivity.this, "数据出错222", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }

                                                        @Override
                                                        public void onError(Response response) {
                                                            cancelLoadingDialog();
                                                            Toast.makeText(FaBuSCActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            } else if (respondAppimgadd.getStatus() == 3) {
                                                MyDialog.showReLoginDialog(FaBuSCActivity.this);
                                            } else {
                                                Toast.makeText(FaBuSCActivity.this, respondAppimgadd.getInfo(), Toast.LENGTH_SHORT).show();
                                                isBreak[0] = false;
                                            }
                                        } catch (Exception e) {
                                            Toast.makeText(FaBuSCActivity.this, "数据出错11", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(Response response) {
                                        Toast.makeText(FaBuSCActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                }).start();
                break;
            case R.id.viewTuiJian:
                intent.setClass(this, SouSuoActivity.class);
                intent.putExtra(Constant.INTENT_KEY.type, 1);
                startActivityForResult(intent, Constant.REQUEST_RESULT_CODE.TuiJian);
                break;
            case R.id.viewTuiJianEmpty:
                intent.setClass(this, SouSuoActivity.class);
                intent.putExtra(Constant.INTENT_KEY.type, 1);
                startActivityForResult(intent, Constant.REQUEST_RESULT_CODE.TuiJian);
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
    private OkObject getPicObject(String img) {
        String url = Constant.HOST + Constant.Url.RESPOND_APPIMGADD;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("code", "item");
        params.put("brand", "android");
        params.put("img", img);
        return new OkObject(params, url);
    }

    /**
     * des： 上传图片9宫格
     * author： ZhangJieBo
     * date： 2017/7/6 0006 下午 2:38
     */
    private class GridAdapter extends BaseAdapter {
        class ViewHolder {
            public ImageView imageView;
            public ImageView imageView2;
            public ImageView imageViewDel;
        }

        @Override
        public int getCount() {
            return mResults.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.item_zhengfangxing, null);
                holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
                holder.imageView2 = (ImageView) convertView.findViewById(R.id.icon2);
                holder.imageViewDel = (ImageView) convertView.findViewById(R.id.imageViewDel);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String path = mResults.get(position).path;
            if (path == null) {
                path = "";
                holder.imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isChoosePic = true;
                        Intent intent = new Intent();
                        mImagePicker.setSelectLimit(photoNum - (mResults.size() - 1));    //选中数量限制
                        intent.setClass(FaBuSCActivity.this, ImageGridActivity.class);
                        startActivityForResult(intent, Constant.REQUEST_RESULT_CODE.IMAGE_PICKER);
                    }
                });
                holder.imageViewDel.setVisibility(View.GONE);
                holder.imageView.setVisibility(View.GONE);
                holder.imageView2.setVisibility(View.VISIBLE);
            } else {
                holder.imageViewDel.setVisibility(View.VISIBLE);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView2.setVisibility(View.GONE);
                holder.imageViewDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mResults.remove(position);
                        if (mResults.size() == photoNum - 1) {
                            if (mResults.get(photoNum - 2).path != null) {
                                mResults.add(new ImageItem());
                            }
                        } else {

                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
            Glide.with(FaBuSCActivity.this)
                    .load(new File(path))
                    .placeholder(R.mipmap.addphoto)
                    .into(holder.imageView);
            return convertView;
        }
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

    @Override
    public void onStart() {
        super.onStart();
        isChoosePic = false;
    }
}
