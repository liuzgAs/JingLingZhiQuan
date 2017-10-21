package com.sxbwstxpay.viewholder;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityOptionsCompat;
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
import com.sxbwstxpay.activity.TuWenTGActivity;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.GridView4ScrollView;
import com.sxbwstxpay.model.BigImgList;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.ShareShareDay;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.SDFileHelper;

import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class TuWenTGViewHolder extends BaseViewHolder<ShareShareDay.ListDataBean> {

    private final TextView textDate;
    private final TextView textShare_contents;
    private final GridView4ScrollView gridView;
    private ShareShareDay.ListDataBean data;
    private final TextView textXiaZai;
    private final TextView textBaoCun;
    private Bitmap[] bitmap;


    public TuWenTGViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textDate = $(R.id.textDate);
        textShare_contents = $(R.id.textShare_contents);
        gridView = $(R.id.gridView);
        textXiaZai = $(R.id.textXiaZai);
        textBaoCun = $(R.id.textBaoCun);
        $(R.id.viewBaoCun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TuWenTGActivity)getContext()).showLoadingDialog();
                ApiClient.post(getContext(), getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        ((TuWenTGActivity)getContext()).cancelLoadingDialog();
                        LogUtil.LogShitou("TuWenTGViewHolder--onSuccess",s+ "");
                        try {
                            SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                            if (simpleInfo.getStatus()==1){
                                ((TuWenTGActivity)getContext()).showLoadingDialog();
                                SDFileHelper helper = new SDFileHelper(getContext());
                                for (int i = 0; i < data.getShare_images().size(); i++) {
                                    helper.savePicture(System.currentTimeMillis()+i + ".jpg", data.getImgs().get(i));
                                }
                                ((TuWenTGActivity)getContext()).cancelLoadingDialog();
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
                        ((TuWenTGActivity)getContext()).cancelLoadingDialog();
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
                intent.putExtra(Constant.INTENT_KEY.value,data.getShare_contents());
                String transitionName = getContext().getString(R.string.TransitionsBigImg);
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(((TuWenTGActivity)getContext()), imageImg, transitionName);
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
        params.put("uid",((TuWenTGActivity)getContext()).userInfo.getUid());
        params.put("tokenTime",((TuWenTGActivity)getContext()).tokenTime);
        params.put("id",data.getId()+"");
        return new OkObject(params, url);
    }

    @Override
    public void setData(ShareShareDay.ListDataBean data) {
        super.setData(data);
        this.data = data;
        textDate.setText(data.getDate());
        textShare_contents.setText(data.getShare_contents());
        textXiaZai.setText("(" + data.getDownloads() + ")");
        textBaoCun.setText("");
        if (data != null) {
            bitmap = new Bitmap[data.getShare_images().size()];
            gridView.setAdapter(new MyAdapter());
        }
    }

    class MyAdapter extends BaseAdapter {
        class ViewHolder {
            public ImageView imageImg;
        }

        @Override
        public int getCount() {
            return data.getShare_images().size();
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
                    .load(data.getShare_images().get(position))
                    .asBitmap()
                    .placeholder(R.mipmap.ic_empty)
                    .into(holder.imageImg);
            return convertView;
        }
    }

}
