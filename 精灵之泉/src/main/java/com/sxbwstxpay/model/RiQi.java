package com.sxbwstxpay.model;

/**
 * Created by zhangjiebo on 2018/3/8/008.
 *
 * @author ZhangJieBo
 */

public class RiQi {
    private String riQi;
    private boolean isSelect;

    public RiQi(String riQi, boolean isSelect) {
        this.riQi = riQi;
        this.isSelect = isSelect;
    }

    public String getRiQi() {
        return riQi;
    }

    public void setRiQi(String riQi) {
        this.riQi = riQi;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
