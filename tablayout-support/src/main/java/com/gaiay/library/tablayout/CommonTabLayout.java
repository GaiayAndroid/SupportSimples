package com.gaiay.library.tablayout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * <p>Created by RenTao on 2017/9/17.
 */

public class CommonTabLayout extends LinearLayout implements ICommonTabLayout {
    private TabLayoutAdapter mAdapter;
    private ViewPager mViewPager;

    public CommonTabLayout(Context context) {
        this(context, null);
    }

    public CommonTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    public void setTabAdapter(TabLayoutAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public void setTabIndicator() {

    }

    @Override
    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
    }

    public TabLayoutAdapter getAdapter() {
        return mAdapter;
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    public void setup() {
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(new TabPageChangeListener());
        }
        invalidateTabs();
    }

    @Override
    public void invalidateTabs() {
        removeAllViews();
        initTabs();
        setCurrentItem(0);
    }

    private void initTabs() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View v = mAdapter.getTabView(this, i);
            LayoutParams params = (LayoutParams) v.getLayoutParams();
            if (params == null) {
                params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                params.width = 0;
                params.weight = 1;
            }
            v.setLayoutParams(params);
            v.setPadding(0, getPaddingTop(), 0, getPaddingBottom());
            v.setOnClickListener(new OnTabClickListener(i));
            addView(v);
        }
    }

    @Override
    public void setCurrentItem(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            mAdapter.onSelected(getChildAt(i), position == i);
        }
    }

    private class TabPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setCurrentItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
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
            setCurrentItem(mPosition);
            if (mViewPager != null) {
                mViewPager.setCurrentItem(mPosition);
            }
            if (mOnTabSelectedListener != null) {
                mOnTabSelectedListener.onSelected(mPosition);
            }
        }
    }
}
