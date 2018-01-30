package com.gaiay.library.tablayout.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.gaiay.library.tablayout.CommonTabLayout;
import com.rent.tablayout_support.R;

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

    private static final int[] ATTRS = { R.attr.tabLayoutIndicatorStripColor, R.attr.tabLayoutIndicatorStripHeight };

    @IntDef({ MODE_FILL, MODE_WRAP, MODE_EXACTLY })
    @Retention(RetentionPolicy.SOURCE)
    @interface Mode {}

    private Paint mPaint;
    private RectF mRect;
    private int mMode, mIndicatorColor, mIndicatorHeight, mIndicatorWidth, mIndicatorMargin;
    private float mIndicatorHeightHalf;

    public StripTabIndicator(Context context) {
        this(context, MODE_FILL);
    }

    public StripTabIndicator(Context context, @Mode int mode) {
        this.mMode = mode;

        parseAttrs(context);
    }

    @SuppressWarnings("ResourceType")
    private void parseAttrs(Context context) {
        final TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        mIndicatorColor = typedArray.getColor(0, ContextCompat.getColor(context, R.color.tab_layout_indicator_strip_color));
        mIndicatorHeight = typedArray.getDimensionPixelSize(1, context.getResources().getDimensionPixelSize(R.dimen.tab_layout_indicator_strip_height));
        setIndicatorHeight(mIndicatorHeight);
        typedArray.recycle();
    }

    @Override
    public void onDraw(Canvas c) {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(mIndicatorColor);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(mIndicatorHeight);
        }
        if (mRect != null) {
            c.drawLine(mRect.left, mRect.bottom, mRect.right, mRect.bottom, mPaint);
        }
    }

    @Override
    protected boolean onScrolled(int position, float positionOffset, CommonTabLayout tabLayout) {
        RectF rect = buildRect(position, positionOffset, tabLayout);
        if (mRect != null && rect != null && mRect.equals(rect)) {
            return false;
        }
        this.mRect = rect;
        return true;
    }

    private RectF buildRect(int position, float positionOffset, CommonTabLayout tabLayout) {
        RectF rect = new RectF();
        if (position < 0) {
            return rect;
        }
        final View selectedChild = tabLayout.getChildAt(position);
        final View nextChild = position + 1 < tabLayout.getChildCount() ? tabLayout.getChildAt(position + 1) : null;

        final int selectedWidth = selectedChild.getWidth();
        final int nextWidth = nextChild != null ? nextChild.getWidth() : 0;

        // 改成了drawLine，不需要top属性
//        rect.top = tabLayout.getHeight() - mIndicatorHeight - mIndicatorMargin;
        // 之所以减去mIndicatorHeightHalf，是因为画线的时候，是从线的中间坐标开始画
        rect.bottom = tabLayout.getHeight() - mIndicatorMargin - mIndicatorHeightHalf;

        final int leftMargin = nextChild != null ? ((ViewGroup.MarginLayoutParams) nextChild.getLayoutParams()).leftMargin : 0;
        // tab.left = 左侧的边距 + tab宽度变化的偏移量 * 0.5
        // ((selectedWidth + nextWidth + 2 * leftMargin) * positionOffset * 0.5f = ((selectedWidth + nextWidth) * positionOffset * 0.5f + leftMargin * positionOffset
        final int left = selectedChild.getLeft() + (int) ((selectedWidth + nextWidth + 2 * leftMargin) * positionOffset * 0.5f);

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

    public void setMode(@Mode int mode) {
        this.mMode = mode;
    }

    public void setIndicatorColor(int color) {
        this.mIndicatorColor = color;
    }

    public void setIndicatorHeight(int height) {
        this.mIndicatorHeight = height;
        this.mIndicatorHeightHalf = (float) mIndicatorHeight / 2;
    }

    public void setIndicatorWidth(int width) {
        this.mIndicatorWidth = width;
    }

    public void setIndicatorMargin(int size) {
        this.mIndicatorMargin = size;
    }
}
