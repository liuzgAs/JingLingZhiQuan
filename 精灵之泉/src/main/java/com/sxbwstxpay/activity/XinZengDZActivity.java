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
                OptionsPickerView pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1).getName()
                                + options2Items.get(options1).get(option2)
                                + options3Items.get(options1).get(option2).get(options3);
                        textArea.setText(tx);
                    }
                })
                        .setSubmitText("确定")//确定按钮文字
                        .setCancelText("取消")//取消按钮文字
                        .setTitleText("城市选择")//标题
                        .setSubCalSize(18)//确定和取消文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                        .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                        .setContentTextSize(18)//滚轮文字大小
                        .setLinkage(false)//设置是否联动，默认true
                        .setLabels("省", "市", "区")//设置选择的三级单位
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setCyclic(false, false, false)//循环与否
                        .setSelectOptions(1, 1, 1)  //设置默认选中项
                        .setOutSideCancelable(false)//点击外部dismiss default true
                        .isDialog(true)//是否显示为对话框样式
                        .build();

                pvOptions.setPicker(options1Items, options2Items, options3Items);//添加数据源
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
        String JsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据
        Type type = new TypeToken<ArrayList<ProvinceBean>>() {
        }.getType();
        List<ProvinceBean> provinceBeanList = GsonUtils.parseJSONArray(JsonData, type);

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = provinceBeanList;
        for (int i=0;i<provinceBeanList.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<provinceBeanList.get(i).getCity().size(); c++){//遍历该省份的所有城市
                String CityName = provinceBeanList.get(i).getCity().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (provinceBeanList.get(i).getCity().get(c).getArea() == null
                        ||provinceBeanList.get(i).getCity().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < provinceBeanList.get(i).getCity().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = provinceBeanList.get(i).getCity().get(c).getArea().get(d);

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
    }

}
