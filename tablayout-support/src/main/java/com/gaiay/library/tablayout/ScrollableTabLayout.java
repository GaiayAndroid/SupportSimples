package com.gaiay.library.tablayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.gaiay.library.tablayout.adapter.TabLayoutAdapter;
import com.gaiay.library.tablayout.indicator.TabIndicator;
import com.gaiay.library.tablayout.listener.OnTabSelectedListener;
import com.gaiay.library.tablayout.utils.AnimationUtils;
import com.rent.tablayout_support.R;

/**
 * 可以左右滚动的TabLayout
 *
 * @see CommonTabLayout
 * <p>Created by RenTao on 2017/9/17.
 */
public class ScrollableTabLayout extends HorizontalScrollView implements ICommonTabLayout {
    private CommonTabLayout mTabLayout;
    private int mTabSpacing;
    private ValueAnimator mScrollAnimator;

    public ScrollableTabLayout(Context context) {
        this(context, null);
    }

    public ScrollableTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollableTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttrs(attrs);
        initViews();
    }

    private void parseAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CommonTabLayout);
        mTabSpacing = typedArray.getDimensionPixelSize(R.styleable.CommonTabLayout_tabLayoutSpacing, 0);
        typedArray.recycle();
    }

    private void initViews() {
        mTabLayout = new CommonTabLayout(getContext());
        mTabLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mTabLayout.setTabSpacing(mTabSpacing);
        setHorizontalScrollBarEnabled(false);
        addView(mTabLayout);
    }

    @Override
    public void setTabAdapter(TabLayoutAdapter adapter) {
        mTabLayout.setTabAdapter(adapter);
    }

    @Override
    public void setTabIndicator(TabIndicator tabIndicator) {
        mTabLayout.setTabIndicator(tabIndicator);
    }

    @Override
    public void setViewPager(ViewPager viewPager) {
        mTabLayout.setViewPager(viewPager);
    }

    @Override
    public void setTabSpacing(int spacing) {
        mTabLayout.setTabSpacing(spacing);
    }

    @Override
    public void setup() {
        if (mTabLayout.getViewPager() != null) {
            mTabLayout.getViewPager().addOnPageChangeListener(new ScrollPageChangeListener());
        }
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();
        initTabs();
        post(new Runnable() {

            @Override
            public void run() {
                setCurrentItem(0);
            }
        });
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
        if (mTabLayout.getViewPager() == null) {
            scrollToPosition(position);
        }
        mTabLayout.setCurrentItem(position);
    }

    @Override
    public int getCurrentItem() {
        return mTabLayout.getCurrentItem();
    }

    private class ScrollPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            scrollToPosition(position, positionOffset);
            if (mTabLayout.getIndicator() != null) {
                mTabLayout.getIndicator().scroll(position, positionOffset, mTabLayout);
            }
        }

        @Override
        public void onPageSelected(int position) {
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
        final int leftMargin = nextChild != null ? ((ViewGroup.MarginLayoutParams) nextChild.getLayoutParams()).leftMargin : 0;

        // base scroll amount: places center of tab in center of parent
        int scrollBase = selectedChild.getLeft() + selectedWidth / 2 - getWidth() / 2;
        // offset amount: fraction of the distance between centers of tabs
        int scrollOffset = (int) ((selectedWidth + nextWidth + 2 * leftMargin) * 0.5f * offset);

        return (ViewCompat.getLayoutDirection(ScrollableTabLayout.this) ==
                ViewCompat.LAYOUT_DIRECTION_LTR)
                ? scrollBase + scrollOffset : scrollBase - scrollOffset;
    }

    private void scrollToPosition(int position, float offset) {
        scrollTo(calcScrollX(position, offset), 0);
    }

    private void scrollToPosition(int position) {
        if (mScrollAnimator != null && mScrollAnimator.isRunning()) {
            return;
        }
        if (mScrollAnimator == null) {
            mScrollAnimator = new ValueAnimator();
            mScrollAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
            mScrollAnimator.setDuration(CommonTabLayout.ANIMATION_DURATION);
            mScrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    scrollTo((int) animator.getAnimatedValue(), 0);
                }
            });
        }
        final int from = getScrollX();
        final int to = calcScrollX(position, 0);
        if (from != to) {
            mScrollAnimator.setIntValues(from, to);
            mScrollAnimator.start();
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
            if (mTabLayout.getViewPager() != null) {
                mTabLayout.getViewPager().setCurrentItem(mPosition);
            }
            if (mOnTabSelectedListener != null) {
                mOnTabSelectedListener.onSelected(mPosition);
            }
        }
    }
}
