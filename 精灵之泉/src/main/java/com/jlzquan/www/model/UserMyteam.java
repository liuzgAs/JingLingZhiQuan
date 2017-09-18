package com.jlzquan.www.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/9/17 0017.
 */
public class UserMyteam {
    /**
     * data1 : [0,0,0,0]
     * data2 : [0,0,0]
     * status : 1
     * info : 返回成功！
     */

    private int status;
    private String info;
    private List<Integer> data1;
    private List<Integer> data2;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Integer> getData1() {
        return data1;
    }

    public void setData1(List<Integer> data1) {
        this.data1 = data1;
    }

    public List<Integer> getData2() {
        return data2;
    }

    public void setData2(List<Integer> data2) {
        this.data2 = data2;
    }
}
