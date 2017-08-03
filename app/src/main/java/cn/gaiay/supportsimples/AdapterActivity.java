package cn.gaiay.supportsimples;

import java.util.List;

import cn.gaiay.supportsimples.adapter.DragActivity;
import cn.gaiay.supportsimples.adapter.HorizontalRecyclerActivity;
import cn.gaiay.supportsimples.adapter.MultiItemActivity;
import cn.gaiay.supportsimples.adapter.RefreshAndLoadMoreActivity;
import cn.gaiay.supportsimples.bean.ActivityBean;

/**
 * <p>Created by RenTao on 2017/8/4.
 */

public class AdapterActivity extends BaseListActivity {

    @Override
    protected void initData(List<ActivityBean> list) {
        list.add(new ActivityBean("refresh and load", RefreshAndLoadMoreActivity.class));
        list.add(new ActivityBean("multi item", MultiItemActivity.class));
        list.add(new ActivityBean("drag", DragActivity.class));
        list.add(new ActivityBean("horizontal", HorizontalRecyclerActivity.class));
    }
}
