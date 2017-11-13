package com.sxbwstxpay.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;


/**
 * des： 金额输入控制器
 * author： ZhangJieBo
 * date： 2017/7/7 0007 上午 9:35
 */
public class MoneyInputFilter {
    public static void init(EditText editText){
        editText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if ((source.equals(".") && dest.toString().length() == 0)) {
                    return "0.";
                }
                if (source.equals("0") && dest.toString().equals("0")) {
                    return ".";
                }
                LogUtil.LogShitou("MoneyInputFilter--filter", ""+dest.toString());
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int mlength = dest.toString().substring(index).length();
                    if (mlength == 3) {
                        return "";
                    }
                }
                return null;
            }
        }});
    }

//    if (TextUtils.equals(mEditMoney.getText().toString().trim(), "")) {
//        Toast.makeText(ChongZhiActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
//        return;
//    }
//    if (TextUtils.equals(mEditMoney.getText().toString().trim(), "0.")) {
//        Toast.makeText(ChongZhiActivity.this, "请输入正确的金额", Toast.LENGTH_SHORT).show();
//        return;
//    }
//    if (Double.parseDouble(mEditMoney.getText().toString().trim()) == 0) {
//        Toast.makeText(ChongZhiActivity.this, "金额必须大于0", Toast.LENGTH_SHORT).show();
//        return;
//    }
}
