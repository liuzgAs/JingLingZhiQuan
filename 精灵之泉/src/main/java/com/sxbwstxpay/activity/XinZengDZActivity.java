package com.sxbwstxpay.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.reflect.TypeToken;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.model.ProvinceBean;
import com.sxbwstxpay.util.GetJsonDataUtil;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class XinZengDZActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private TextView textArea;
    private List<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private List<ProvinceBean> jsonBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xin_zeng_dz);
        init();
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
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        textArea = (TextView) findViewById(R.id.textArea);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("新增地址");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.buttonBaoCun).setOnClickListener(this);
        findViewById(R.id.viewChooseDiQu).setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewChooseDiQu:
                /**
                 * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
                 */

                OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        textArea.setText(options1Items.get(options1).getPickerViewText()+options2Items.get(options2)+options3Items.get(options2).get(options3));
                    }
                })
                        .setTitleText("城市选择")
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
                        .setLabels("省", "市", "区")
                        .setBackgroundId(0x66000000) //设置外部遮罩颜色
                        .build();

                //pvOptions.setSelectOptions(1,1);
        /*pvOptions.setPicker(options1Items);//一级选择器*/
//                pvOptions.setPicker(options1Items, options2Items);//二级选择器
                pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                pvOptions.show();
                break;
            case R.id.buttonBaoCun:

                break;
            case R.id.imageBack:
                finish();
                break;
        }
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

}
