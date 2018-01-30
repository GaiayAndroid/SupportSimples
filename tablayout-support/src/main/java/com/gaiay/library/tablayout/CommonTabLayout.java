package com.gaiay.library.tablayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gaiay.library.tablayout.adapter.TabLayoutAdapter;
import com.gaiay.library.tablayout.indicator.TabIndicator;
import com.gaiay.library.tablayout.listener.OnTabSelectedListener;
import com.gaiay.library.tablayout.utils.AnimationUtils;
import com.rent.tablayout_support.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
 * 自定义属性：
 * tabLayoutSpacing：tab的间距
 *
 * <p>Created by RenTao on 2017/9/17.
 */
public class CommonTabLayout extends LinearLayout implements ICommonTabLayout {
    public static final int ANIMATION_DURATION = 200;

    /**
     * Tab平分空间
     */
    public static final int MODE_FILL = 0;
    /**
     * Tab包裹内容
     */
    public static final int MODE_WRAP = 1;

    @IntDef(value = {MODE_FILL, MODE_WRAP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TabMode {}

    private TabLayoutAdapter mAdapter;
    private TabIndicator mIndicator;
    private ViewPager mViewPager;
    private int mCurrentItem;
    private @TabMode int mTabMode;
    private int mTabSpacing, mTabBorderHeight, mTabBorderColor;
    private ValueAnimator mAnimator;

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
        mTabMode = typedArray.getInt(R.styleable.CommonTabLayout_tabLayoutMode, MODE_FILL);
        mTabBorderHeight = typedArray.getDimensionPixelSize(R.styleable.CommonTabLayout_tabLayoutBorderHeight, 0);
        mTabBorderColor = typedArray.getColor(R.styleable.CommonTabLayout_tabLayoutBorderColor, 0);
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

    public void setTabMode(@TabMode int mode) {
        this.mTabMode = mode;
    }

    @Override
    public void setTabBorder(int height, int color) {
        this.mTabBorderHeight = height;
        this.mTabBorderColor = color;
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

    public @TabMode int getTabMode() {
        return mTabMode;
    }

    @Override
    public void setup() {
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(new TabPageChangeListener());
        }
        notifyDataSetChanged(mCurrentItem);
    }

    private void notifyDataSetChanged(final int index) {
        removeAllViews();
        initTabs();
        post(new Runnable() {

            @Override
            public void run() {
                setCurrentItem(index);
            }
        });
    }

    @Override
    public void notifyDataSetChanged() {
        notifyDataSetChanged(0);
    }

    private void initTabs() {
        mAdapter.onPrepare(this);
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View v = mAdapter.getTabView(this, i);
            LayoutParams params = (LayoutParams) v.getLayoutParams();
            if (params == null) {
                params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            buildLayoutParams(params);
            if (i > 0 && mTabSpacing != 0) {
                params.leftMargin = mTabSpacing;
            }
            v.setLayoutParams(params);
            v.setPadding(0, getPaddingTop(), 0, getPaddingBottom());
            v.setOnClickListener(new OnTabClickListener(i));
            addView(v);
        }
    }

    private void buildLayoutParams(LayoutParams params) {
        if (mTabMode == MODE_FILL) {
            params.width = 0;
            params.weight = 1;
        }
        else if (mTabMode == MODE_WRAP) {
            // do nothing
        }
    }

    /**
     * @param position <0：表示清空所有的选中状态；>=0：表示选中对应的tab
     */
    @Override
    public void setCurrentItem(int position) {
        if (mIndicator != null && mViewPager == null && getChildCount() > 0) {
            onSelected(position);
        }
        this.mCurrentItem = position;
        for (int i = 0; i < getChildCount(); i++) {
            mAdapter.onSelected(getChildAt(i), position == i);
        }
    }

    private void onSelected(final int position) {
        if (mAnimator != null && mAnimator.isRunning()) {
            return;
        }
        if (mCurrentItem == position) {
            mIndicator.scroll(position, 0, CommonTabLayout.this);
            return;
        }
        if (mAnimator == null) {
            mAnimator = new ValueAnimator();
            mAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
            mAnimator.setDuration(ANIMATION_DURATION);
        }
        // 这里是模拟ViewPager的OnPageChangeListener#onPageScrolled回调方法
        // 当4滑动到2，position是2，positionOffset是从1到0
        // 当2滑动到4，position是4 - 1，positionOffset是从0到1
        if (mCurrentItem > position) {
            mAnimator.setFloatValues(1, 0);
            mAnimator.addUpdateListener(new IndicatorAnimatorListener(position));
        } else {
            mAnimator.setFloatValues(0, 1);
            mAnimator.addUpdateListener(new IndicatorAnimatorListener(position - 1));
        }
        mAnimator.start();
    }

    private class IndicatorAnimatorListener implements ValueAnimator.AnimatorUpdateListener {
        int mPosition;

        IndicatorAnimatorListener(int position) {
            this.mPosition = position;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mIndicator.scroll(mPosition, (float) animation.getAnimatedValue(), CommonTabLayout.this);
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

    private Paint mBorderPaint;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mTabBorderHeight > 0 && mTabBorderColor != 0) {
            if (mBorderPaint == null) {
                mBorderPaint = new Paint();
                mBorderPaint.setStyle(Paint.Style.FILL);
                mBorderPaint.setStrokeWidth(1);
                mBorderPaint.setColor(mTabBorderColor);
            }
            canvas.drawRect(0, getMeasuredHeight() - 1, getMeasuredWidth(), getMeasuredHeight(), mBorderPaint);
        }
        if (mIndicator != null) {
            mIndicator.onDraw(canvas);
        }
    }
}
