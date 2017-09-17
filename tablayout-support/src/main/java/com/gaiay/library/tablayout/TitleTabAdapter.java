package com.gaiay.library.tablayout;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * <p>Created by RenTao on 2017/9/17.
 */

public class TitleTabAdapter extends TabLayoutAdapter {
    private Context mContext;
    private String[] mData;
    private int mSelectedColor = Color.RED,
                mUnselectedColor = Color.BLACK;
    private int mTitleSize = 14;
    private int mTabPadding = 50;

    public TitleTabAdapter(Context context, String[] data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public View getTabView(ViewGroup parent, int position) {
        TextView textView = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setPadding(mTabPadding, 0, mTabPadding, 0);
        textView.setTextSize(mTitleSize);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(params);
        textView.setText(mData[position]);
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
        return mData.length;
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
