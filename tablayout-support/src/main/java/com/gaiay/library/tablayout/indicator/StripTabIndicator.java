package com.gaiay.library.tablayout.indicator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.view.View;

import com.gaiay.library.tablayout.CommonTabLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p> Created by RenTao on 2017/9/18.
 */
public class StripTabIndicator extends TabIndicator {
    /** 和tab等宽 */
    public static final int MODE_FILL = 1;
    /** tab内容的实际宽度 */
    public static final int MODE_WRAP = 2;
    /** 指定宽度 */
    public static final int MODE_EXACTLY = 3;

    @IntDef({ MODE_FILL, MODE_WRAP, MODE_EXACTLY })
    @Retention(RetentionPolicy.SOURCE)
    @interface Mode {}

    private Paint mPaint;
    private Rect mRect;
    private int mIndicatorColor = Color.RED,
                mIndicatorHeight = 10,
                mIndicatorWidth;
    private int mMode;

    public StripTabIndicator() {
        this(MODE_FILL);
    }

    public StripTabIndicator(@Mode int mode) {
        this.mMode = mode;
    }

    public void setMode(@Mode int mode) {
        this.mMode = mode;
    }

    public void setIndicatorColor(int color) {
        this.mIndicatorColor = color;
    }

    public void setIndicatorHeight(int height) {
        this.mIndicatorHeight = height;
    }

    public void setIndicatorWidth(int width) {
        this.mIndicatorWidth = width;
    }

    @Override
    public void onDraw(Canvas c) {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setColor(mIndicatorColor);
            mPaint.setStrokeWidth(mIndicatorHeight);
        }
        c.drawRect(mRect, mPaint);
    }

    @Override
    protected boolean onScrolled(int position, float positionOffset, CommonTabLayout tabLayout) {
        Rect rect =  buildRect(position, positionOffset, tabLayout);
        if (mRect != null && rect != null && mRect.equals(rect)) {
            return false;
        }
        this.mRect = rect;
        return true;
    }

    private Rect buildRect(int position, float positionOffset, CommonTabLayout tabLayout) {
        final View selectedChild = tabLayout.getChildAt(position);
        final View nextChild = position + 1 < tabLayout.getChildCount() ? tabLayout.getChildAt(position + 1) : null;

        final int selectedWidth = selectedChild.getWidth();
        final int nextWidth = nextChild != null ? nextChild.getWidth() : 0;

        Rect rect = new Rect();
        rect.top = selectedChild.getHeight() - mIndicatorHeight;
        rect.bottom = selectedChild.getHeight();

        // tab.left = 左侧的边距 + tab宽度变化的偏移量 * 0.5
        final int left = selectedChild.getLeft() + (int) ((selectedWidth + nextWidth) * positionOffset * 0.5f);

        if (mMode == MODE_FILL) {
            rect.left = left;
            rect.right = rect.left + selectedWidth;
        }
        else if (mMode == MODE_EXACTLY) {
            // tab.left + （当前tab宽度 - 指示器宽度） * 0.5
            rect.left = (int) (left + (selectedWidth - mIndicatorWidth) * 0.5f);
            rect.right = rect.left + mIndicatorWidth;
        }
        else if (mMode == MODE_WRAP) {
            final int currentActualWidth = tabLayout.getAdapter().getActualWidth(position);
            final int nextActualWidth = nextChild != null ? tabLayout.getAdapter().getActualWidth(position + 1) : 0;
            // 实际宽度的偏移量
            final float actualOffset = (nextActualWidth - currentActualWidth) * positionOffset;

            // tab.left + （当前tab宽度 - 实际的宽度 - tab实际宽度变化的偏移量） * 0.5
            rect.left = (int) (left + (selectedWidth - currentActualWidth - actualOffset) * 0.5f);
            rect.right = (int) (rect.left + currentActualWidth + actualOffset);
        }
        return rect;
    }
}
