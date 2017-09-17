package cn.gaiay.supportsimples;

import java.util.List;

import cn.gaiay.supportsimples.bean.ActivityBean;
import cn.gaiay.supportsimples.tablayout.CommonTabLayoutActivity;
import cn.gaiay.supportsimples.tablayout.ScrollableTabLayoutActivity;

/**
 * <p>Created by RenTao on 2017/8/4.
 */

public class TabLayoutActivity extends BaseListActivity {

    @Override
    protected void initData(List<ActivityBean> list) {
        list.add(new ActivityBean("CommonTabLayout", CommonTabLayoutActivity.class));
        list.add(new ActivityBean("ScrollableTabLayout", ScrollableTabLayoutActivity.class));
    }
}
