package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.SideLetterBar;
import com.sxbwstxpay.model.IndexCitylist;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.ListViewUtility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ChengShiXZActivity extends ZjbBaseActivity implements View.OnClickListener {

    private SideLetterBar mLetterBar;
    private TextView mOverlay;
    private ListView mListview_all_star;
    private MyAdapter mAdapter;
    private List<IndexCitylist.CityEntity> indexCitylistCity = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheng_shi_xz);
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
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mOverlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mListview_all_star = (ListView) findViewById(R.id.listview_all_Star);
        mAdapter = new MyAdapter();
        mListview_all_star.setAdapter(mAdapter);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("城市选择");
        mLetterBar.setOverlay(mOverlay);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {

            @Override
            public void onLetterChanged(String letter, int position) {
                mListview_all_star.setSelection(position);
            }
        });
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        JSONObject jsonObject = new JSONObject(params);
        OkGo.post(Constant.HOST + Constant.Url.INDEX_CITYLIST)//
                .tag(this)//
                .upJson(jsonObject.toString())//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("ChengShiXZActivity", "ChengShiXZActivity--onSuccess--城市选择" + s);
                        cancelLoadingDialog();
                        try {
                            IndexCitylist indexCitylist = GsonUtils.parseJSON(s, IndexCitylist.class);
                            if (indexCitylist.getStatus() == 1) {
                                indexCitylistCity = indexCitylist.getCity();
                                mAdapter.notifyDataSetChanged();
                            } else if (indexCitylist.getStatus() == 3) {
                                MyDialog.showReLoginDialog(ChengShiXZActivity.this);
                            } else {
                                Toast.makeText(ChengShiXZActivity.this, indexCitylist.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(ChengShiXZActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        cancelLoadingDialog();
                        Toast.makeText(ChengShiXZActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
        }
    }

    /**
     * des： 嵌套双重城市adapter
     * author： ZhangJieBo
     * date： 2017/7/6 0006 下午 2:21
     */
    class MyAdapter extends BaseAdapter {


        class ViewHolder {
            public ListView listViewAStarLetter;
            public TextView textViewLetter;
            public TextView textViewLine2;
        }

        @Override
        public int getCount() {
            return indexCitylistCity.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.star_listitem, null);
                holder.listViewAStarLetter = (ListView) convertView.findViewById(R.id.listViewAStarLetter);
                holder.textViewLetter = (TextView) convertView.findViewById(R.id.textViewLetter);
                holder.textViewLine2 = (TextView) convertView.findViewById(R.id.textViewLine2);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final List<IndexCitylist.CityEntity.ListEntity> listEntityList = indexCitylistCity.get(position).getList();
            holder.listViewAStarLetter.setAdapter(new MyStarAdapter(listEntityList));
            ListViewUtility.setListViewHeightBasedOnChildren(holder.listViewAStarLetter);
            holder.listViewAStarLetter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    IndexCitylist.CityEntity.ListEntity listEntity = listEntityList.get(i);
                    Intent data = new Intent();
                    data.putExtra(Constant.INTENT_KEY.CITY, listEntity);
                    data.setAction(Constant.BROADCASTCODE.CITY_CHOOSE);
                    sendBroadcast(data);
                    finish();
                }
            });

            holder.textViewLetter.setText(indexCitylistCity.get(position).getTitle());
            if (listEntityList.size() == 0) {
                holder.textViewLetter.setVisibility(View.GONE);
                holder.textViewLine2.setVisibility(View.GONE);
            } else {
                holder.textViewLetter.setVisibility(View.VISIBLE);
                holder.textViewLine2.setVisibility(View.VISIBLE);
            }
            return convertView;
        }

        class MyStarAdapter extends BaseAdapter {
            private List<IndexCitylist.CityEntity.ListEntity> listEntityList = new ArrayList<>();

            public MyStarAdapter(List<IndexCitylist.CityEntity.ListEntity> listEntityList) {
                this.listEntityList = listEntityList;
            }

            class ViewHolder {
                public TextView textCity;
            }

            @Override
            public int getCount() {
                return listEntityList.size();
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
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = getLayoutInflater().inflate(R.layout.item_city, null);
                    holder.textCity = (TextView) convertView.findViewById(R.id.textCity);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.textCity.setText(listEntityList.get(position).getName());
                return convertView;
            }
        }
    }
}
