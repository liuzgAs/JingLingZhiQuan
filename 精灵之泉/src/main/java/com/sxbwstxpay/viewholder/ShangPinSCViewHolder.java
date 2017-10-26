package com.sxbwstxpay.viewholder;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxbwstxpay.R;
import com.sxbwstxpay.activity.BigImgActivity;
import com.sxbwstxpay.activity.ShangPinScActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.GridView4ScrollView;
import com.sxbwstxpay.customview.MyIm;
import com.sxbwstxpay.model.BigImgList;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.model.UserItem;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.SDFileHelper;

import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ShangPinSCViewHolder extends BaseViewHolder<UserItem.DataBean> {
    private final TextView textDate;
    private final TextView textShare_contents;
    private final GridView4ScrollView gridView;
    private UserItem.DataBean data;
    private final TextView textXiaZai;
    private final TextView textBaoCun;
    public ShangPinSCViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textDate = $(R.id.textDate);
        textShare_contents = $(R.id.textShare_contents);
        gridView = $(R.id.gridView);
        textXiaZai = $(R.id.textXiaZai);
        textBaoCun = $(R.id.textBaoCun);
        $(R.id.viewBaoCun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ShangPinScActivity)getContext()).showLoadingDialog();
                ApiClient.post(getContext(), getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        ((ShangPinScActivity)getContext()).cancelLoadingDialog();
                        LogUtil.LogShitou("TuWenTGViewHolder--onSuccess",s+ "");
                        try {
                            SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                            if (simpleInfo.getStatus()==1){
                                ((ShangPinScActivity)getContext()).showLoadingDialog();
                                SDFileHelper helper = new SDFileHelper(getContext());
                                for (int i = 0; i < data.getImgs().size(); i++) {
                                    helper.savePicture(System.currentTimeMillis()+i + ".jpg", data.getImgs().get(i));
                                }
                                ((ShangPinScActivity)getContext()).cancelLoadingDialog();
                                Toast.makeText(getContext(), "已保存到本地相册", Toast.LENGTH_SHORT).show();
                            }else if (simpleInfo.getStatus()==3){
                                MyDialog.showReLoginDialog(getContext());
                            }else {
                                Toast.makeText(getContext(), simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(),"数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        ((ShangPinScActivity)getContext()).cancelLoadingDialog();
                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        textShare_contents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(textShare_contents.getText().toString().trim()); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
                Toast.makeText(getContext(), "复制文本成功", Toast.LENGTH_SHORT).show();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageImg = (ImageView) view.findViewById(R.id.imageImg);
                Intent intent = new Intent();
                intent.setClass(getContext(), BigImgActivity.class);
                intent.putExtra(Constant.INTENT_KEY.BIG_IMG, new BigImgList(data.getImgs()));
                intent.putExtra(Constant.INTENT_KEY.BIG_IMG_POSITION, position);
                intent.putExtra(Constant.INTENT_KEY.value,data.getTitle());
                String transitionName = getContext().getString(R.string.TransitionsBigImg);
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(((ShangPinScActivity)getContext()), imageImg, transitionName);
                getContext().startActivity(intent, transitionActivityOptions.toBundle());
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_ITEM;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid",((ShangPinScActivity)getContext()).userInfo.getUid());
        params.put("tokenTime",((ShangPinScActivity)getContext()).tokenTime);
        params.put("id",data.getId()+"");
        return new OkObject(params, url);
    }

    @Override
    public void setData(UserItem.DataBean data) {
        super.setData(data);
        this.data = data;
        textDate.setText(data.getAddTime());
        if (data.getRecommend() == 1) {
            SpannableString span = new SpannableString("i " + data.getTitle());
            MyIm imgspan = new MyIm(getContext(), R.mipmap.jingxuan);
            span.setSpan(imgspan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            textShare_contents.setText(span);
        } else {
            textShare_contents.setText(data.getTitle());
        }
        textXiaZai.setText("(" + data.getDownloads() + ")");
        textBaoCun.setText("");
        if (data != null) {
            gridView.setAdapter(new MyAdapter());
        }
    }

    class MyAdapter extends BaseAdapter {
        class ViewHolder {
            public ImageView imageImg;
        }

        @Override
        public int getCount() {
            return data.getImg().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tu_wen_tg_grid, null);
                holder.imageImg = (ImageView) convertView.findViewById(R.id.imageImg);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Glide.with(getContext())
                    .load(data.getImg().get(position))
                    .asBitmap()
                    .placeholder(R.mipmap.ic_empty)
                    .into(holder.imageImg);
            return convertView;
        }
    }


}
