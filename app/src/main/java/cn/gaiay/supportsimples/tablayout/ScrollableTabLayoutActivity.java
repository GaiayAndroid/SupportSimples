package cn.gaiay.supportsimples.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.gaiay.library.tablayout.ScrollableTabLayout;
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
public class ScrollableTabLayoutActivity extends AppCompatActivity {
    ScrollableTabLayout tabLayout;
    ViewPager pager;
    List<String> titles = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_activity_scrollable);

        tabLayout = (ScrollableTabLayout) this.findViewById(R.id.tab_layout);
        pager = (ViewPager) this.findViewById(R.id.pager);

        int size = 10;
        for (int i = 0; i < size; i++) {
            titles.add("tab " + (i % 2 == 0 ? 100 + i : i));
            fragments.add(new TempListFragment(i + " Scrollable"));
        }
        pager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragments));

        tabLayout.setTabAdapter(new TitleTabAdapter(this, titles));
        tabLayout.setTabIndicator(new StripTabIndicator(this, StripTabIndicator.MODE_WRAP));
        tabLayout.setViewPager(pager);
        tabLayout.setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tablayout_scrollable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_add:
                titles.add("tab new");
                fragments.add(new TempListFragment("new Scrollable"));
                tabLayout.notifyDataSetChanged();
                pager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragments));
                break;
        }
        return true;
    }
}
