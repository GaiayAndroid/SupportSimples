package com.gaiay.library.tablayout;

import android.support.v4.view.ViewPager;

import com.gaiay.library.tablayout.adapter.TabLayoutAdapter;
import com.gaiay.library.tablayout.indicator.TabIndicator;
import com.gaiay.library.tablayout.listener.OnTabSelectedListener;

/**
 * <p>Created by RenTao on 2017/9/17.
 */

interface ICommonTabLayout {
    void setTabAdapter(TabLayoutAdapter adapter);

    void setTabIndicator(TabIndicator tabIndicator);

    void setViewPager(ViewPager viewPager);

    void setup();

    void notifyDataSetChanged();

    void setCurrentItem(int position);

    int getCurrentItem();

    void setOnTabSelectedListener(OnTabSelectedListener l);
}
