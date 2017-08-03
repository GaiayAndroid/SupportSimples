package com.gaiay.library.recycler.listener;

import com.gaiay.library.recycler.widget.RefreshLayout;

/**
 * <p> Created by RenTao on 2017/7/31.
 */
public interface RefreshListener {
    void onRefresh(RefreshLayout refreshLayout);

    void onLoadMore(RefreshLayout refreshLayout);
}
