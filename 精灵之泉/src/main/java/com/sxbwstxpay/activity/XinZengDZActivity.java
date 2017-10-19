package com.sxbwstxpay.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.reflect.TypeToken;
import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.ProvinceBean;
import com.sxbwstxpay.model.SimpleInfo;
import com.sxbwstxpay.model.UserAddress;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GetJsonDataUtil;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;
import com.sxbwstxpay.util.ScreenUtils;
import com.sxbwstxpay.util.StringUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class XinZengDZActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private TextView textArea;
    private List<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private List<ProvinceBean> jsonBean;
    private EditText editConsignee;
    private EditText editPhone;
    private EditText editAddress;
    private UserAddress.DataBean value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xin_zeng_dz);
        init(XinZengDZActivity.class);
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
        Intent intent = getIntent();
        value = (UserAddress.DataBean)intent.getSerializableExtra(Constant.INTENT_KEY.value);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        textArea = (TextView) findViewById(R.id.textArea);
        editConsignee = (EditText) findViewById(R.id.editConsignee);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editAddress = (EditText) findViewById(R.id.editAddress);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("新增地址");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        if (value!=null){
            textArea.setText(value.getArea());
            editConsignee.setText(value.getConsignee());
            editConsignee.setSelection(value.getConsignee().length());
            editPhone.setText(value.getPhone());
            editAddress.setText(value.getAddress());
        }
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
                        .setBackgroundId(0x66000000) //设置外部遮罩颜色
                        .build();

                //pvOptions.setSelectOptions(1,1);
        /*pvOptions.setPicker(options1Items);//一级选择器*/
//                pvOptions.setPicker(options1Items, options2Items);//二级选择器
                pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                pvOptions.show();
                break;
            case R.id.buttonBaoCun:
                if (TextUtils.isEmpty(editConsignee.getText().toString().trim())) {
                    Toast.makeText(XinZengDZActivity.this, "请输入联系人姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!StringUtil.isMobileNO(editPhone.getText().toString().trim())) {
                    Toast.makeText(XinZengDZActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(textArea.getText().toString().trim())) {
                    Toast.makeText(XinZengDZActivity.this, "请选择地区", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editAddress.getText().toString().trim())) {
                    Toast.makeText(XinZengDZActivity.this, "请输入街道地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                save();
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_SAVEADDRESS;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        if (value!=null) {
            params.put("id", value.getId());
        }
        params.put("consignee", editConsignee.getText().toString().trim());
        params.put("phone", editPhone.getText().toString().trim());
        params.put("address", editAddress.getText().toString().trim());
        params.put("area", textArea.getText().toString().trim());
        return new OkObject(params, url);
    }

    /**
     * des： 新增地址提交
     * author： ZhangJieBo
     * date： 2017/9/27 0027 下午 2:07
     */
    private void save() {
        showLoadingDialog();
        ApiClient.post(XinZengDZActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("XinZengDZActivity--地址保存", s + "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus() == 1) {
                        Intent intent = new Intent();
                        intent.setAction(Constant.BROADCASTCODE.address);
                        sendBroadcast(intent);
                        if (value!=null){
                            MyDialog.dialogFinish(XinZengDZActivity.this,"修改地址成功！");
                        }else {
                            MyDialog.dialogFinish(XinZengDZActivity.this,"新增地址成功！");
                        }
                    } else if (simpleInfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(XinZengDZActivity.this);
                    } else {
                        Toast.makeText(XinZengDZActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(XinZengDZActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(XinZengDZActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
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
