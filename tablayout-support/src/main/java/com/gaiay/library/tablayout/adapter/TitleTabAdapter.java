package com.gaiay.library.tablayout.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rent.tablayout_support.R;

import java.util.List;

/**
 * <p>Created by RenTao on 2017/9/17.
 */

public class TitleTabAdapter extends TabLayoutAdapter {
    private static final int[] ATTRS = { R.attr.tabLayoutTitleTextSize, R.attr.tabLayoutTitlePadding,
            R.attr.tabLayoutTitleSelectedColor, R.attr.tabLayoutTitleUnselectedColor };
    private Context mContext;
    private SparseIntArray mActualWidth = new SparseIntArray();
    private List<String> mData;
    private int mSelectedColor, mUnselectedColor, mTitleSize, mTabPadding;

    public TitleTabAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;

        parseAttrs();
    }

    @SuppressWarnings("ResourceType")
    private void parseAttrs() {
        final TypedArray typedArray = mContext.obtainStyledAttributes(ATTRS);
        mTitleSize = typedArray.getDimensionPixelSize(0, 14);
        mTabPadding = typedArray.getDimensionPixelSize(1, mContext.getResources().getDimensionPixelSize(R.dimen.tab_layout_title_padding));
        mSelectedColor = typedArray.getColor(2, ContextCompat.getColor(mContext, R.color.tab_layout_title_selected_color));
        mUnselectedColor = typedArray.getColor(3, ContextCompat.getColor(mContext, R.color.tab_layout_title_unselected_color));
        typedArray.recycle();
    }

    @Override
    public View getTabView(ViewGroup parent, int position) {
        TextView textView = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setPadding(mTabPadding, 0, mTabPadding, 0);
        textView.setTextSize(mTitleSize);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(params);
        textView.setText(mData.get(position));

        // 获取TextView的文字的长度
        mActualWidth.put(position, (int) textView.getPaint().measureText(mData.get(position)));
        return textView;
    }

    @Override
    public void onSelected(View tab, boolean selected) {
        if (selected) {
            ((TextView) tab).setTextColor(mSelectedColor);
        } else {
            ((TextView) tab).setTextColor(mUnselectedColor);
        }
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getActualWidth(int position) {
        return mActualWidth.get(position);
    }

    public void setSelectedColor(int color) {
        this.mSelectedColor = color;
    }

    public void setUnselectedColor(int color) {
        this.mUnselectedColor = color;
    }

    public void setTitleSize(int size) {
        this.mTitleSize = size;
    }

    public void setTabPadding(int padding) {
        this.mTabPadding = padding;
    }
}
