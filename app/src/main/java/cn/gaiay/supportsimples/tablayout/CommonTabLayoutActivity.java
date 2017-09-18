package cn.gaiay.supportsimples.tablayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.gaiay.library.tablayout.CommonTabLayout;
import com.gaiay.library.tablayout.adapter.TitleTabAdapter;
import com.gaiay.library.tablayout.indicator.StripTabIndicator;

import java.util.ArrayList;
import java.util.List;

import cn.gaiay.supportsimples.R;
import cn.gaiay.supportsimples.temp.TempListFragment;
import cn.gaiay.supportsimples.widget.ViewPagerFragmentAdapter;

/**
 * <p>Created by RenTao on 2017/9/17.
 */

public class CommonTabLayoutActivity extends AppCompatActivity {
    CommonTabLayout tabLayout1;
    CommonTabLayout tabLayout2;
    CommonTabLayout tabLayout3;
    ViewPager pager;
    final int size = 5;
    List<String> titles1 = new ArrayList<>(),
            titles2 = new ArrayList<>(),
            titles3 = new ArrayList<>();
    Fragment[] fragments = new Fragment[size];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_activity_common);

        tabLayout1 = (CommonTabLayout) this.findViewById(R.id.tab_layout1);
        tabLayout2 = (CommonTabLayout) this.findViewById(R.id.tab_layout2);
        tabLayout3 = (CommonTabLayout) this.findViewById(R.id.tab_layout3);
        pager = (ViewPager) this.findViewById(R.id.pager);

        for (int i = 0; i < size; i++) {
            titles1.add("FILL " + i);
            titles2.add("WRAP " + (i % 2 == 0 ? 100 + i : i));
            titles3.add("EXACTLY " + (i % 2 == 0 ? 10 + i : i));
            fragments[i] = new TempListFragment(i + " Common");
        }
        pager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragments));

        initFillTabLayout();
        initWrapTabLayout();
        initExactlyTabLayout();
    }

    private void initFillTabLayout() {
        tabLayout1.setTabAdapter(new TitleTabAdapter(this, titles1));
        StripTabIndicator indicator = new StripTabIndicator(StripTabIndicator.MODE_FILL);
        indicator.setIndicatorHeight(5);
        tabLayout1.setTabIndicator(indicator);
        tabLayout1.setViewPager(pager);
        tabLayout1.setup();
    }

    private void initWrapTabLayout() {
        TitleTabAdapter adapter = new TitleTabAdapter(this, titles2);
        adapter.setSelectedColor(Color.BLUE);
        tabLayout2.setTabAdapter(adapter);
        StripTabIndicator indicator = new StripTabIndicator(StripTabIndicator.MODE_WRAP);
        indicator.setIndicatorColor(Color.BLUE);
        tabLayout2.setTabIndicator(indicator);
        tabLayout2.setViewPager(pager);
        tabLayout2.setup();
    }

    private void initExactlyTabLayout() {
        TitleTabAdapter adapter = new TitleTabAdapter(this, titles3);
        adapter.setTitleSize(11);
        tabLayout3.setTabAdapter(adapter);
        StripTabIndicator indicator = new StripTabIndicator(StripTabIndicator.MODE_EXACTLY);
        indicator.setIndicatorWidth(50);
        tabLayout3.setTabIndicator(indicator);
        tabLayout3.setViewPager(pager);
        tabLayout3.setup();
    }
}
