package cn.gaiay.supportsimples;

import android.os.Bundle;

import java.util.List;

import cn.gaiay.supportsimples.bean.ActivityBean;

public class MainActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
    }

    @Override
    protected void initData(List<ActivityBean> list) {
        list.add(new ActivityBean("Adapter", AdapterActivity.class));
        list.add(new ActivityBean("TabLayout", TabLayoutActivity.class));
    }
}
