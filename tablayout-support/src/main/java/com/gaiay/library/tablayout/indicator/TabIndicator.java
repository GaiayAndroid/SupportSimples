package com.gaiay.library.tablayout.indicator;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;

import com.gaiay.library.tablayout.CommonTabLayout;

/**
 * 当View滑动的时候，会回调{@link #onScrolled(int, float, CommonTabLayout)}方法，假如返回true则会对页面进行刷新，
 * 回调{@link #onDraw(Canvas)}方法，对View重新进行绘制；返回false则不进行变化
 * <p>Created by RenTao on 2017/9/17.
 */
public abstract class TabIndicator {

    /**
     * 绘制指示器
     */
    public abstract void onDraw(Canvas c);

    public void scroll(int position, float positionOffset, CommonTabLayout tabLayout) {
        if (onScrolled(position, positionOffset, tabLayout)) {
            ViewCompat.postInvalidateOnAnimation(tabLayout);
        }
    }

    /**
     * 页面滑动的时候，指示器需要对应的进行移动
     * @return View是否需要进行重新绘制
     */
    protected abstract boolean onScrolled(int position, float positionOffset, CommonTabLayout tabLayout);
}
