package com.gaiay.library.tablayout.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rent.tablayout_support.R;

import java.util.List;

/**
 * <p>Created by RenTao on 2018/1/12.
 */

public class CommonTabAdapter extends TabLayoutAdapter {
    private static final int[] ATTRS = { R.attr.tabLayoutTitleTextSize, R.attr.tabLayoutTitlePadding,
            R.attr.tabLayoutTitleSelectedColor, R.attr.tabLayoutTitleUnselectedColor };

    private Context mContext;
    private SparseIntArray mActualWidth = new SparseIntArray();
    private List<Tab> mData;
    protected int mTextSelectedColor, mTextUnselectedColor, mTextSize, mTabPadding;

    public CommonTabAdapter(Context context) {
        this(context, null);
    }

    public CommonTabAdapter(Context context, List<Tab> data) {
        this.mContext = context;
        setTabs(data);

        parseAttrs();
    }

    @SuppressWarnings("ResourceType")
    private void parseAttrs() {
        final TypedArray typedArray = mContext.obtainStyledAttributes(ATTRS);
        // 获取title的textSize，并将px转换为sp
        mTextSize = typedArray.getDimensionPixelSize(0, mContext.getResources().getDimensionPixelSize(R.dimen.tab_layout_title_text_size));
        mTextSize = px2sp(mTextSize);
        mTabPadding = typedArray.getDimensionPixelSize(1, mContext.getResources().getDimensionPixelSize(R.dimen.tab_layout_title_padding));
        mTextSelectedColor = typedArray.getColor(2, ContextCompat.getColor(mContext, R.color.tab_layout_title_selected_color));
        mTextUnselectedColor = typedArray.getColor(3, ContextCompat.getColor(mContext, R.color.tab_layout_title_unselected_color));
        typedArray.recycle();
    }

    private int px2sp(float pxValue) {
        return (int) (pxValue / mContext.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }

    public void setTabs(List<Tab> data) {
        this.mData = data;
    }

    @Override
    public void onPrepare(ViewGroup parent) {}

    @Override
    public View getTabView(ViewGroup parent, int position) {
        Tab tab = mData.get(position);
        View view = null;
        if (tab.text != null && !tab.text.trim().equals("")) {
            view = buildText(position, tab);
        }
        else if (tab.imageResId != 0 || tab.image != null) {
            view = buildImage(position, tab);
        }
        else if (tab.customView != null) {
            view = buildCustom(position, tab);
        }
        if (view != null) {
            view.setTag(position);
        }
        return view;
    }

    private View buildText(int position, Tab tab) {
        TextView textView = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setPadding(mTabPadding, 0, mTabPadding, 0);
        textView.setTextSize(tab.textSize);
        textView.setTextColor(tab.textColor);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(params);
        textView.setText(tab.text);

        // 获取TextView的文字的长度
        mActualWidth.put(position, (int) textView.getPaint().measureText(tab.text));
        return textView;
    }

    private View buildImage(int position, Tab tab) {
        ImageView imageView = new ImageView(mContext);
        LinearLayout.LayoutParams params;
        if (tab.imageWidth > 0 && tab.imageHeight > 0) {
            params = new LinearLayout.LayoutParams(tab.imageWidth + mTabPadding + mTabPadding, tab.imageHeight);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
        imageView.setLayoutParams(params);
        imageView.setPadding(mTabPadding, 0, mTabPadding, 0);
        if (tab.imageResId != 0) {
            imageView.setImageResource(tab.imageResId);
        } else {
            imageView.setImageDrawable(tab.image);
        }

        mActualWidth.put(position, tab.imageWidth);
        return imageView;
    }

    private View buildCustom(int position, Tab tab) {
        tab.customView.setPadding(mTabPadding, 0, mTabPadding, 0);
        mActualWidth.put(position, tab.customViewWidth);
        return tab.customView;
    }

    @Override
    public void onSelected(View view, boolean selected) {
        Tab tab = mData.get((int) view.getTag());
        if (tab.text != null && !tab.text.trim().equals("")) {
            if (selected) {
                ((TextView) view).setTextColor(tab.textSelectedColor);
            } else {
                ((TextView) view).setTextColor(tab.textColor);
            }
        }
        else if (tab.imageResId != 0) {
            if (selected) {
                ((ImageView) view).setImageResource(tab.imageSelectedResId);
            } else {
                ((ImageView) view).setImageResource(tab.imageResId);
            }
        }
        else if (tab.image != null) {
            if (selected) {
                ((ImageView) view).setImageDrawable(tab.imageSelected);
            } else {
                ((ImageView) view).setImageDrawable(tab.image);
            }
        }
        else if (tab.customView != null) {
            tab.customView.setSelected(selected);
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

    public void setTabPadding(int padding) {
        this.mTabPadding = padding;
    }

    public Tab newTab() {
        return new Tab();
    }

    public Tab newTextTab(@StringRes int textResId) {
        return newTextTab(mContext.getString(textResId));
    }

    public Tab newTextTab(String text) {
        Tab tab = new Tab();
        tab.text = text;
        tab.textSize = mTextSize;
        tab.textColor = mTextUnselectedColor;
        tab.textSelectedColor = mTextSelectedColor;
        return tab;
    }

    public Tab newImageTab(int imageResId, int imageSelectedResId) {
        Tab tab = new Tab();
        tab.imageResId = imageResId;
        tab.imageSelectedResId = imageSelectedResId;
        return tab;
    }

    public Tab newImageTab(Drawable image, Drawable imageSelected) {
        Tab tab = new Tab();
        tab.image = image;
        tab.imageSelected = imageSelected;
        return tab;
    }

    public Tab newCustomView(View customView) {
        Tab tab = new Tab();
        tab.customView = customView;
        return tab;
    }

    public static class Tab {
        public String text;
        public int textColor;
        public int textSelectedColor;
        public int textSize;

        public int imageResId;
        public int imageSelectedResId;
        public Drawable image;
        public Drawable imageSelected;
        public int imageWidth;
        public int imageHeight;

        public View customView;
        public int customViewWidth;
    }
}