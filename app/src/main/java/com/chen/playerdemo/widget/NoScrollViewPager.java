package com.chen.playerdemo.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁用水平滑动的ViewPager
 * Created by chenbin on 2018/11/16.
 */

public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 不拦截这个事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    // 不处理这个事件
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
