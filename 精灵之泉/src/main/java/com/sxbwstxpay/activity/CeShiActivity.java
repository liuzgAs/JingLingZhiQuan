package com.sxbwstxpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import com.sxbwstxpay.R;
import com.sxbwstxpay.base.MyDialog;
import com.sxbwstxpay.base.ZjbBaseNotLeftActivity;
import com.sxbwstxpay.constant.Constant;
import com.sxbwstxpay.customview.NoScrollViewPager;
import com.sxbwstxpay.fragment.CeShi1Fragment;
import com.sxbwstxpay.fragment.CeShi2Fragment;
import com.sxbwstxpay.fragment.CeShi3Fragment;
import com.sxbwstxpay.fragment.CeShi4Fragment;
import com.sxbwstxpay.interfacepage.CeShiInterface;
import com.sxbwstxpay.model.OkObject;
import com.sxbwstxpay.model.TestStyle;
import com.sxbwstxpay.model.Test_result;
import com.sxbwstxpay.util.ApiClient;
import com.sxbwstxpay.util.GsonUtils;
import com.sxbwstxpay.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Response;

public class CeShiActivity extends ZjbBaseNotLeftActivity implements CeShiInterface {
    private NoScrollViewPager viewPager;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ce_shi);
        init(CeShiActivity.class);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        viewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void initViews() {
        fragments = new ArrayList<>();
        viewPager.setNoScroll(true);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(CeShiActivity.this, getOkObjectSave(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("CeShiActivity--测试题", "");
                try {
                    TestStyle testStyle = GsonUtils.parseJSON(s, TestStyle.class);
                    if (testStyle.getStatus() == 1) {
                        ArrayList<TestStyle.DataBean> dataBeans1 = new ArrayList<>();
                        ArrayList<TestStyle.DataBean> dataBeans2 = new ArrayList<>();
                        ArrayList<TestStyle.DataBean> dataBeans3 = new ArrayList<>();
                        ArrayList<TestStyle.DataBean> dataBeans4 = new ArrayList<>();
                        dataBeans1.clear();
                        dataBeans2.clear();
                        dataBeans3.clear();
                        dataBeans4.clear();
                        for (int i = 0; i < testStyle.getData().size(); i++) {
                            if ("1".equals(testStyle.getData().get(i).getType())) {
                                dataBeans1.add(testStyle.getData().get(i));
                            } else if ("2".equals(testStyle.getData().get(i).getType())) {
                                dataBeans2.add(testStyle.getData().get(i));
                            } else if ("3".equals(testStyle.getData().get(i).getType())) {
                                dataBeans3.add(testStyle.getData().get(i));
                            } else if ("4".equals(testStyle.getData().get(i).getType())) {
                                dataBeans4.add(testStyle.getData().get(i));
                            }
                        }
                        CeShi1Fragment fragment1 = CeShi1Fragment.newInstance(dataBeans1);
                        CeShi2Fragment fragment2 = CeShi2Fragment.newInstance(dataBeans2);
                        CeShi3Fragment fragment3 = CeShi3Fragment.newInstance(dataBeans3);
                        CeShi4Fragment fragment4 = CeShi4Fragment.newInstance(dataBeans4);
                        fragments.add(fragment1);
                        fragments.add(fragment2);
                        fragments.add(fragment3);
                        fragments.add(fragment4);
                        viewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
                        viewPager.setCurrentItem(0);
                        fragment1.setCallBack(CeShiActivity.this);
                        fragment2.setCallBack(CeShiActivity.this);
                        fragment3.setCallBack(CeShiActivity.this);
                        fragment4.setCallBack(CeShiActivity.this);

                    } else if (testStyle.getStatus() == 3) {
                        MyDialog.showReLoginDialog(CeShiActivity.this);
                    } else {
                        Toast.makeText(CeShiActivity.this, testStyle.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(CeShiActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(CeShiActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObjectSave() {
        String url = Constant.HOST + Constant.Url.TESTSTYLE;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    public void setPostion(int postion) {
        viewPager.setCurrentItem(postion, false);
    }
    ArrayList<String> ids1=new ArrayList<>();
    ArrayList<String> ids2=new ArrayList<>();
    ArrayList<String> ids3=new ArrayList<>();
    ArrayList<String> ids4=new ArrayList<>();

    @Override
    public void setIds(int postion,ArrayList<String> ids) {
        if (postion==0){
            ids1.clear();
            ids1.addAll(ids);
        }else if (postion==1){
            ids2.clear();
            ids2.addAll(ids);
        }else if (postion==2){
            ids3.clear();
            ids3.addAll(ids);
        }else if (postion==3){
            ids4.clear();
            ids4.addAll(ids);
            ArrayList<String> allIds=new ArrayList<>();
            allIds.addAll(ids1);
            allIds.addAll(ids2);
            allIds.addAll(ids3);
            allIds.addAll(ids4);
            getRez(allIds);
        }
    }

    class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
    private void getRez(ArrayList<String> allIds){
        showLoadingDialog();
        ApiClient.post(CeShiActivity.this, getOkObjectRez(allIds), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("CeShiActivity--测试结果", "");
                try {
                    Test_result test_result = GsonUtils.parseJSON(s, Test_result.class);
                    if (test_result.getStatus() == 1) {
                        Toast.makeText(CeShiActivity.this, test_result.getStyle(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(CeShiActivity.this,TestResultActivity.class);
                        intent.putExtra("test_result",test_result);
                        startActivity(intent);
                        Intent intent1=new Intent();
                        intent1.setAction(Constant.BROADCASTCODE.CHANGEWODE);
                        sendBroadcast(intent1);
                        finish();
                    } else if (test_result.getStatus() == 3) {
                        MyDialog.showReLoginDialog(CeShiActivity.this);
                    } else {
                        Toast.makeText(CeShiActivity.this, test_result.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(CeShiActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response) {
                cancelLoadingDialog();
                Toast.makeText(CeShiActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private OkObject getOkObjectRez(ArrayList<String> allIds) {
        String url = Constant.HOST + Constant.Url.TEST_RESULT;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        params.put("style", allIds.toString().replace("[","").replace("]",""));
        return new OkObject(params, url);
    }
}
