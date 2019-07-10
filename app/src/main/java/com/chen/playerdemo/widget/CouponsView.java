package com.chen.playerdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.chen.playerdemo.R;

/**
 * 自定义优惠券样式
 */

public class CouponsView extends LinearLayout {

    private Paint mPaint;
    /**
     * 圆间距
     */
    private float gap = 8;
    /**
     * 半径
     */
    private float radius = 10;
    private float radiusBig = 30;
    /**
     * 圆数量
     */
    private int circleNum;

    private float remain;
    private static final int DOUBLE = 0;
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private int direction = DOUBLE;

    public CouponsView(Context context) {
        super(context);
    }

    public CouponsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CouponsView);
        direction = typedArray.getInteger(R.styleable.CouponsView_direction, DOUBLE);
        int circle_color = typedArray.getColor(R.styleable.CouponsView_circle_color, Color.WHITE);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(circle_color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (remain == 0) {
            remain = (int) (h - gap) % (2 * radius + gap);
        }
        circleNum = (int) ((h - gap) / (2 * radius + gap));
    }

    public CouponsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (direction == DOUBLE) {
            for (int i = 0; i < circleNum; i++) {
                float y = gap + radius + remain / 2 + ((gap + radius * 2) * i);
                canvas.drawCircle(0, y, radius, mPaint);
                canvas.drawCircle(getWidth(), y, radius, mPaint);
            }
        } else if (direction == LEFT) {
            for (int i = 0; i < circleNum; i++) {
                float y = gap + radius + remain / 2 + ((gap + radius * 2) * i);
                canvas.drawCircle(0, y, radius, mPaint);
                if (i == 0) {
                    canvas.drawCircle(getWidth(), 0, radiusBig, mPaint);
                } else if (i == circleNum - 1) {
                    canvas.drawCircle(getWidth(), y + radiusBig, radiusBig, mPaint);
                }
            }
        } else if (direction == RIGHT) {
            for (int i = 0; i < circleNum; i++) {
                float y = gap + radius + remain / 2 + ((gap + radius * 2) * i);
                canvas.drawCircle(getWidth(), y, radius, mPaint);
                if (i == 0) {
                    canvas.drawCircle(0, 0, radiusBig, mPaint);
                } else if (i == circleNum - 1) {
                    canvas.drawCircle(0, y + radiusBig, radiusBig, mPaint);
                }
            }
        }
    }
}
