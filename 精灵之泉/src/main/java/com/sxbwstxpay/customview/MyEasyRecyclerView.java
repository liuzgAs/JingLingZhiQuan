package com.sxbwstxpay.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jude.easyrecyclerview.EasyRecyclerView;

/**
 * Created by zhangjiebo on 2018/1/31/031.
 *
 * @author ZhangJieBo
 */

public class MyEasyRecyclerView extends EasyRecyclerView {

    public boolean isScroll;

    private float downX;
    private float upX;

    public MyEasyRecyclerView(Context context) {
        super(context);
    }

    public MyEasyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_DOWN:
                isScroll = false;
                //获取屏幕上点击的坐标
                downX = ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                upX = ev.getX();
                if (!isScroll) {
                    if (downX - upX > 200) {
                        onDaoDiLeListener.daoDiLe();
                    }
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public interface OnDaoDiLeListener{
        void daoDiLe();
    }

    public OnDaoDiLeListener onDaoDiLeListener;

    public void setOnDaoDiLeListener(OnDaoDiLeListener onDaoDiLeListener){
        this.onDaoDiLeListener=onDaoDiLeListener;
    }

    public boolean isScroll() {
        return isScroll;
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}

