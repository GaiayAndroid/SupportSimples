package cn.gaiay.supportsimples.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.gaiay.library.tablayout.CommonTabLayout;
import com.gaiay.library.tablayout.TitleTabAdapter;

import cn.gaiay.supportsimples.R;
import cn.gaiay.supportsimples.temp.TempListFragment;
import cn.gaiay.supportsimples.widget.ViewPagerFragmentAdapter;

/**
 * <p>Created by RenTao on 2017/9/17.
 */

public class CommonTabLayoutActivity extends AppCompatActivity {
    CommonTabLayout tabLayout;
    ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_activity_common);

        tabLayout = (CommonTabLayout) this.findViewById(R.id.tab_layout);
        pager = (ViewPager) this.findViewById(R.id.pager);

        int size = 5;
        Fragment[] fragments = new Fragment[size];
        String[] titles = new String[size];
        for (int i = 0; i < size; i++) {
            titles[i] = "tab " + i;
            fragments[i] = new TempListFragment(i + " Common");
        }
        pager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragments));

        tabLayout.setTabAdapter(new TitleTabAdapter(this, titles));
        tabLayout.setViewPager(pager);
        tabLayout.setup();
    }
}
