package cn.gaiay.supportsimples.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * FragmentPagerAdapter
 * Created by RenT on 2016/8/22.
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles;
    private Fragment[] mFragments;

    public ViewPagerFragmentAdapter(FragmentManager fm, Fragment[] fragments) {
        this(fm, null, fragments);
    }

    public ViewPagerFragmentAdapter(FragmentManager fm, String[] titles, Fragment[] fragments) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles == null ? null : mTitles[position];
    }
}
