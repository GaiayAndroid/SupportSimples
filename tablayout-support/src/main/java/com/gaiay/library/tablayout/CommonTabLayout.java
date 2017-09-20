package com.gaiay.library.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
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
import com.rent.tablayout_support.R;

/**
 * <pre>
 *      tabLayout.setTabAdapter(TabAdapter);     // 设置Adapter，必须设置
 *      tabLayout.setTabIndicator(TabIndicator); // 设置指示器，非必须
 *      tabLayout.setViewPager(ViewPager);       // 设置ViewPager，非必须
 *      tabLayout.setup();
 * </pre>
 *
 * <!--
 * 如果在工程中使用了{@link com.gaiay.library.tablayout.adapter.TitleTabAdapter}或者{@link com.gaiay.library.tablayout.indicator.StripTabIndicator}，
 * 并想要自定义样式，可以Application的theme增加如下配置
 * <style name="AppTheme" parent="xxx">
 *     <item name="tabLayoutTitleTextSize">14sp</item>              // 文字大小
 *     <item name="tabLayoutTitlePadding">8dp</item>                // tab左右的padding
 *     <item name="tabLayoutTitleSelectedColor">#ba0404</item>      // 选中时文字的颜色
 *     <item name="tabLayoutTitleUnselectedColor">#323232</item>    // 未选中时文字的颜色
 *
 *     <item name="tabLayoutIndicatorStripColor">#ba0404</item>     // 指示器的颜色
 *     <item name="tabLayoutIndicatorStripHeight">2dp</item>        // 指示器的高度
 * </style>
 * -->
 *
 * <p>Created by RenTao on 2017/9/17.
 */
public class CommonTabLayout extends LinearLayout implements ICommonTabLayout {
    private TabLayoutAdapter mAdapter;
    private TabIndicator mIndicator;
    private ViewPager mViewPager;
    private int mCurrentItem;
    private int mTabSpacing;

    public CommonTabLayout(Context context) {
        this(context, null);
    }

    public CommonTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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

    @Override
    public void setTabSpacing(int spacing) {
        this.mTabSpacing = spacing;
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

    public int getTabSpacing() {
        return mTabSpacing;
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
            if (i > 0 && mTabSpacing != 0) {
                params.leftMargin = mTabSpacing;
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
        if (mIndicator != null && mViewPager == null) {
            mIndicator.scroll(getCurrentItem(), 0, this);
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
