package com.sxbwstxpay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseNotLeftActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.BigImgList;
import com.sxbwstxpay.photoview.HackyViewPager;
import com.sxbwstxpay.util.SDFileHelper;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * @author zhangjiebo
 * @des 相册大图显示
 * @date 2017/7/6 0006 上午 11:57
 */
public class BigImgActivity extends ZjbBaseNotLeftActivity {

    //    private ImageView mImageBigImg;
    private HackyViewPager mViewpager;
    private int mPosition;//外部点击传进相应的position
    private TextView mTextPosition;
    private TextView mTextCount;
    private List<String> imgList = new ArrayList<>();
    private SamplePagerAdapter pagerAdapter;
    private String pid;
    private ImageView imageDelete;
    private TextView textDes;
    private String des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img);
        init(BigImgActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        BigImgList bigImgList = (BigImgList) intent.getSerializableExtra(Constant.INTENT_KEY.BIG_IMG);
        imgList = bigImgList.getImgList();
        mPosition = intent.getIntExtra(Constant.INTENT_KEY.BIG_IMG_POSITION, 0);
        des = intent.getStringExtra(Constant.INTENT_KEY.value);
    }

    @Override
    protected void findID() {
//        mImageBigImg = (ImageView) findViewById(R.id.imageBigImg);
        mViewpager = (HackyViewPager) findViewById(R.id.viewpager);
        mTextPosition = (TextView) findViewById(R.id.textPosition);
        mTextCount = (TextView) findViewById(R.id.textCount);
        textDes = (TextView) findViewById(R.id.textDes);
        imageDelete = (ImageView) findViewById(R.id.imageDelete);
    }

    @Override
    protected void initViews() {
//        int screenWidth = ScreenUtils.getScreenWidth(getApplicationContext());
//        ViewGroup.LayoutParams lp = mImageBigImg.getLayoutParams();
//        lp.width = screenWidth;
//        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        mImageBigImg.setLayoutParams(lp);
//        mImageBigImg.setMaxWidth(screenWidth);
//        mImageBigImg.setMaxHeight(screenWidth * 5);
//        AppUtil.showPic(mBigImgUrl, mImageBigImg);
        mTextPosition.setText((mPosition + 1) + "");
        textDes.setText(des + "");
        mTextCount.setText(imgList.size() + "");
        pagerAdapter = new SamplePagerAdapter(this);
        mViewpager.setAdapter(pagerAdapter);
        mViewpager.setCurrentItem(mPosition);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageViewBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        findViewById(R.id.imageDownLoad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                SDFileHelper helper = new SDFileHelper(BigImgActivity.this);
                for (int i = 0; i < imgList.size(); i++) {
                    helper.savePicture(System.currentTimeMillis() + i + ".jpg", imgList.get(i));
                }
                cancelLoadingDialog();
                Toast.makeText(BigImgActivity.this, "已保存到本地相册", Toast.LENGTH_SHORT).show();
            }
        });
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                mTextPosition.setText((position + 1) + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void back() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onBackPressed() {
        back();
    }

    /**
     * des： 图片adapter
     * author： ZhangJieBo
     * date： 2017/7/6 0006 下午 2:18
     */
    class SamplePagerAdapter extends PagerAdapter {

        private Context mContext;

        public SamplePagerAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            final PhotoView photoView = new PhotoView(container.getContext());
            Glide.with(mContext)
                    .load(imgList.get(position))
                    .placeholder(R.mipmap.default_image)
                    .into(photoView);
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

}
