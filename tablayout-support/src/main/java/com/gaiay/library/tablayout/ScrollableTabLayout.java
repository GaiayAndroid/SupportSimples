package com.gaiay.library.tablayout;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

/**
 * <p>Created by RenTao on 2017/9/17.
 */

public class ScrollableTabLayout extends HorizontalScrollView implements ICommonTabLayout {
    private CommonTabLayout mTabLayout;

    public ScrollableTabLayout(Context context) {
        this(context, null);
    }

    public ScrollableTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollableTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        mTabLayout = new CommonTabLayout(getContext());
        mTabLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setHorizontalScrollBarEnabled(false);
        addView(mTabLayout);
    }

    @Override
    public void setTabAdapter(TabLayoutAdapter adapter) {
        mTabLayout.setTabAdapter(adapter);
    }

    @Override
    public void setTabIndicator() {
        mTabLayout.setTabIndicator();
    }

    @Override
    public void setViewPager(ViewPager viewPager) {
        mTabLayout.setViewPager(viewPager);
    }

    @Override
    public void setup() {
        if (mTabLayout.getViewPager() != null) {
            mTabLayout.getViewPager().addOnPageChangeListener(new ScrollPageChangeListener());
        }
        invalidateTabs();
    }

    @Override
    public void invalidateTabs() {
        mTabLayout.removeAllViews();
        initTabs();
        setCurrentItem(0);
    }

    private void initTabs() {
        for (int i = 0; i < mTabLayout.getAdapter().getCount(); i++) {
            View v = mTabLayout.getAdapter().getTabView(this, i);
            v.setOnClickListener(new OnTabClickListener(i));
            mTabLayout.addView(v);
        }
    }

    @Override
    public void setCurrentItem(int position) {
        mTabLayout.setCurrentItem(position);
    }

    private class ScrollPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            scrollToPosition(position, positionOffset);
        }

        @Override
        public void onPageSelected(int position) {
            scrollToPosition(position, 0);
            setCurrentItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    }

    private int calcScrollX(int position, float offset) {
        final View selectedChild = mTabLayout.getChildAt(position);
        final View nextChild = position + 1 < mTabLayout.getChildCount() ? mTabLayout.getChildAt(position + 1) : null;

        final int selectedWidth = selectedChild.getWidth();
        final int nextWidth = nextChild != null ? nextChild.getWidth() : 0;

        // base scroll amount: places center of tab in center of parent
        int scrollBase = selectedChild.getLeft() + selectedWidth / 2 - getWidth() / 2;
        // offset amount: fraction of the distance between centers of tabs
        int scrollOffset = (int) ((selectedWidth + nextWidth) * 0.5f * offset);

        return (ViewCompat.getLayoutDirection(ScrollableTabLayout.this) == ViewCompat.LAYOUT_DIRECTION_LTR)
                ? scrollBase + scrollOffset : scrollBase - scrollOffset;
    }

    private void scrollToPosition(int position, float offset) {
        scrollTo(calcScrollX(position, offset), 0);
    }

    private OnTabSelectedListener mOnTabSelectedListener;

    @Override
    public void setOnTabSelectedListener(OnTabSelectedListener l) {
        this.mOnTabSelectedListener = l;
    }

    private class OnTabClickListener implements OnClickListener {
        private int mPosition;

        OnTabClickListener(int position) {
            this.mPosition = position;
        }

        @Override
        public void onClick(View v) {
            scrollToPosition(mPosition, 0);
            setCurrentItem(mPosition);
            if (mTabLayout.getViewPager() != null) {
                mTabLayout.getViewPager().setCurrentItem(mPosition);
            }
            if (mOnTabSelectedListener != null) {
                mOnTabSelectedListener.onSelected(mPosition);
            }
        }
    }
}
