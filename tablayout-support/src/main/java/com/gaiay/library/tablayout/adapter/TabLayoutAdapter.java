package com.gaiay.library.tablayout.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * <p>Created by RenTao on 2017/9/17.
 */

public abstract class TabLayoutAdapter {

    public abstract View getTabView(ViewGroup parent, int position);

    public void onSelected(View tab, boolean selected) {
        tab.setSelected(selected);
    }

    public abstract int getCount();

    public abstract int getActualWidth(int position);
}
