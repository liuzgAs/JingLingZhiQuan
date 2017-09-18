package com.sxbwstxpay.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jlzquan.www.R;
import com.sxbwstxpay.base.ZjbBaseActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.util.ScreenUtils;

import java.util.Calendar;

public class EditActivity extends ZjbBaseActivity implements View.OnClickListener {

    private View viewBar;
    private int type;
    private String value;
    private TextView textViewTitle;
    private EditText editNickName;
    private View viewShengRi;
    private TextView textShengRi;
    private View viewSex;
    private EditText editDiQu;
    private RadioButton radioNan;
    private RadioButton radioNv;
    private TextView textViewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        type = intent.getIntExtra(Constant.INTENT_KEY.type, 0);
        value = intent.getStringExtra(Constant.INTENT_KEY.value);
    }

    @Override
    protected void findID() {
        textViewRight = (TextView) findViewById(R.id.textViewRight);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        viewBar = findViewById(R.id.viewBar);
        editNickName = (EditText) findViewById(R.id.editNickName);
        viewShengRi = findViewById(R.id.viewShengRi);
        textShengRi = (TextView) findViewById(R.id.textShengRi);
        viewSex = findViewById(R.id.viewSex);
        editDiQu = (EditText) findViewById(R.id.editDiQu);
        radioNan = (RadioButton) findViewById(R.id.radioNan);
        radioNv = (RadioButton) findViewById(R.id.radioNv);
    }

    @Override
    protected void initViews() {
        textViewRight.setText("保存");
        switch (type) {
            case 1:
                textViewTitle.setText("修改昵称");
                editNickName.setText(value);
                editNickName.setSelection(value.length());
                editNickName.setVisibility(View.VISIBLE);
                viewShengRi.setVisibility(View.GONE);
                viewSex.setVisibility(View.GONE);
                editDiQu.setVisibility(View.GONE);
                break;
            case 2:
                textViewTitle.setText("修改生日");
                textShengRi.setText(value);
                editNickName.setVisibility(View.GONE);
                viewShengRi.setVisibility(View.VISIBLE);
                viewSex.setVisibility(View.GONE);
                editDiQu.setVisibility(View.GONE);
                break;
            case 3:
                textViewTitle.setText("修改性别");
                if (TextUtils.equals("女", value)) {
                    radioNv.setChecked(true);
                } else {
                    radioNan.setChecked(true);
                }
                editNickName.setVisibility(View.GONE);
                viewShengRi.setVisibility(View.GONE);
                viewSex.setVisibility(View.VISIBLE);
                editDiQu.setVisibility(View.GONE);
                break;
            case 4:
                textViewTitle.setText("修改地区");
                editDiQu.setText(value);
                editNickName.setVisibility(View.GONE);
                viewShengRi.setVisibility(View.GONE);
                viewSex.setVisibility(View.GONE);
                editDiQu.setVisibility(View.VISIBLE);
                break;
        }
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        textViewRight.setOnClickListener(this);
        viewShengRi.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewShengRi:
                Calendar c = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textShengRi.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                break;
            case R.id.textViewRight:
                Intent intent = new Intent();
                switch (type) {
                    case 1:
                        if (TextUtils.isEmpty(editNickName.getText().toString().trim())){
                            Toast.makeText(EditActivity.this, "请输入昵称", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        intent.putExtra("type",type);
                        intent.putExtra("key", "nickName");
                        intent.putExtra("value", editNickName.getText().toString().trim());
                        break;
                    case 2:
                        if (TextUtils.isEmpty(textShengRi.getText().toString().trim())){
                            Toast.makeText(EditActivity.this, "请选择生日", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        intent.putExtra("type",type);
                        intent.putExtra("key", "birthday");
                        intent.putExtra("value", textShengRi.getText().toString().trim());
                        break;
                    case 3:
                        intent.putExtra("type",type);
                        intent.putExtra("key", "sex");
                        if (radioNan.isChecked()) {
                            intent.putExtra("value", "1");
                        } else {
                            intent.putExtra("value", "2");
                        }
                        break;
                    case 4:
                        if (TextUtils.isEmpty(editDiQu.getText().toString().trim())){
                            Toast.makeText(EditActivity.this, "请输入地区", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        intent.putExtra("type",type);
                        intent.putExtra("key", "area");
                        intent.putExtra("value", editDiQu.getText().toString().trim());
                        break;
                }
                setResult(Constant.REQUEST_RESULT_CODE.BaoCun, intent);
                finish();
                break;
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
