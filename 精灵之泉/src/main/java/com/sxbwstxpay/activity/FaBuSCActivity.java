package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.GridView4ScrollView;
import com.sxbwstxpay.util.PicassoImageLoader;
import com.sxbwstxpay.util.ScreenUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FaBuSCActivity extends ZjbBaseActivity implements View.OnClickListener {

    private GridView4ScrollView mGridView;
    private View viewBar;
    private GridAdapter mAdapter;
    private ImagePicker mImagePicker;
    private List<ImageItem> mResults = new ArrayList<>();

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
        init();
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
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("发布素材");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        mAdapter = new GridAdapter();
        mGridView.setAdapter(mAdapter);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.viewTuiJian).setOnClickListener(this);
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
                if (mResults.size() < 9) {
                    mResults.add(new ImageItem());
                }
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewTuiJian:
                
                break;
        }
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
                        Intent intent = new Intent();
                        mImagePicker.setSelectLimit(6 - (mResults.size() - 1));    //选中数量限制
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
                        if (mResults.size() == 8) {
                            if (mResults.get(7).path != null) {
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
}
