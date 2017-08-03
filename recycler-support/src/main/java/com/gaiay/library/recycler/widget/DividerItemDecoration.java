package com.gaiay.library.recycler.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * DividerItemDecoration is a {@link RecyclerView.ItemDecoration} that can be used as a divider
 * between items of a {@link LinearLayoutManager}. It supports both {@link #HORIZONTAL} and
 * {@link #VERTICAL} orientations.
 *
 * <pre>
 *     mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
 *             mLayoutManager.getOrientation());
 *     recyclerView.addItemDecoration(mDividerItemDecoration);
 * </pre>
 *
 * <p> {@link RecyclerView.ItemDecoration}
 *
 * <p> Created by RenTao on 2017/7/26.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private static final int[] ATTRS = new int[]{ android.R.attr.listDivider };

    private static final int DEFAULT_DIVIDER_SIZE = (int) (0.5f + 1 * Resources.getSystem().getDisplayMetrics().density);

    private Context mContext;
    private Drawable mDivider;
    private int mDividerSize;

    /**
     * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    private int mOrientation;

    private final Rect mBounds = new Rect();

    public DividerItemDecoration(Context context) {
        this(context, VERTICAL);
    }

    /**
     * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
     * {@link LinearLayoutManager}.
     *
     * @param context Current context, it will be used to access resources.
     * @param orientation Divider orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    public DividerItemDecoration(Context context, int orientation) {
        this.mContext = context;

        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();

        setOrientation(orientation);
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager} changes orientation.
     *
     * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    /**
     * Sets the {@link Drawable} for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    public DividerItemDecoration setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
        return this;
    }

    public DividerItemDecoration setColor(@ColorInt int color) {
        return setDrawable(createColorDrawable(color));
    }

    public DividerItemDecoration setColorRes(@ColorRes int colorRes) {
        return setDrawable(createColorDrawable(ContextCompat.getColor(mContext, colorRes)));
    }

    private ColorDrawable createColorDrawable(@ColorInt int color) {
        return new ColorDrawable(color);
    }

    /**
     * if (VERTICAL) { dividerHeight = size }
     * else if (HORIZONTAL) { dividerWidth = size }
     */
    public DividerItemDecoration setDividerSize(@Px int size) {
        this.mDividerSize = size;
        return this;
    }

    private int mActualDividerSize;

    /**
     * <p> 获取真正的分隔线高度
     * <p> 1. 优先获取Drawable的宽或者高
     * <p> 2. 然后获取mDividerSize
     * <p> 3. 最后设置为默认高度{@link #DEFAULT_DIVIDER_SIZE}
     */
    private int getActualDividerSize() {
        if (mActualDividerSize > 0) {
            return mActualDividerSize;
        }
        int size = mOrientation == VERTICAL ? mDivider.getIntrinsicHeight() : mDivider.getIntrinsicWidth();
        if (size <= 0) {
            size = mDividerSize <= 0 ? DEFAULT_DIVIDER_SIZE : mDividerSize;
        }
        mActualDividerSize = size;
        return size;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        // 当版本为Build.VERSION_CODES.LOLLIPOP 5.0以上版本
        if (Build.VERSION.SDK_INT >= 21 && parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
            final int top = bottom - getActualDividerSize();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top;
        final int bottom;
        // 当版本为Build.VERSION_CODES.LOLLIPOP 5.0以上版本
        if (Build.VERSION.SDK_INT >= 21 && parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(ViewCompat.getTranslationX(child));
            final int left = right - getActualDividerSize();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, getActualDividerSize());
        } else {
            outRect.set(0, 0, getActualDividerSize(), 0);
        }
    }
}

