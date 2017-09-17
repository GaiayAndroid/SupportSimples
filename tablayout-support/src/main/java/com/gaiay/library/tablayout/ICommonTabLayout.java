package com.gaiay.library.tablayout;

import android.support.v4.view.ViewPager;

/**
 * <p>Created by RenTao on 2017/9/17.
 */

interface ICommonTabLayout {
    void setTabAdapter(TabLayoutAdapter adapter);

    void setTabIndicator();

    void setViewPager(ViewPager viewPager);

    void setup();

    void invalidateTabs();

    void setCurrentItem(int position);

    void setOnTabSelectedListener(OnTabSelectedListener l);
}
