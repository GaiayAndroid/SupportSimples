package com.gaiay.library.tablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gaiay.library.tablayout.adapter.TabLayoutAdapter;
import com.gaiay.library.tablayout.indicator.TabIndicator;
import com.gaiay.library.tablayout.listener.OnTabSelectedListener;

/**
 * <pre>
 * tabLayout.setTabAdapter(TabAdapter);     // 设置Adapter，必须设置
 * tabLayout.setTabIndicator(TabIndicator); // 设置指示器，非必须
 * tabLayout.setViewPager(ViewPager);       // 设置ViewPager，非必须
 * tabLayout.setup();
 * </pre>
 * <p>Created by RenTao on 2017/9/17.
 */
public class CommonTabLayout extends LinearLayout implements ICommonTabLayout {
    private TabLayoutAdapter mAdapter;
    private TabIndicator mIndicator;
    private ViewPager mViewPager;
    private int mCurrentItem;

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
    public void setTabIndicator(TabIndicator tabIndicator) {
        this.mIndicator = tabIndicator;
    }

    @Override
    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
    }

    public TabLayoutAdapter getAdapter() {
        return mAdapter;
    }

    public TabIndicator getIndicator() {
        return mIndicator;
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    public void setup() {
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(new TabPageChangeListener());
        }
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
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
        this.mCurrentItem = position;
        for (int i = 0; i < getChildCount(); i++) {
            mAdapter.onSelected(getChildAt(i), position == i);
        }
    }

    @Override
    public int getCurrentItem() {
        return mCurrentItem;
    }

    private class TabPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mIndicator != null) {
                mIndicator.scroll(position, positionOffset, CommonTabLayout.this);
            }
        }

        @Override
        public void onPageSelected(int position) {
            setCurrentItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
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

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mIndicator != null) {
            mIndicator.onDraw(canvas);
        }
    }
}
