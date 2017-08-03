package com.gaiay.library.recycler.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.gaiay.library.recycler.listener.RefreshListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

/**
 * <p> 1. {@link #autoRefresh()} / {@link #finishRefresh()} 开始/结束刷新
 * <p> 2. {@link #autoLoadmore()} / {@link #finishLoadmore()} 开始/结束加载
 * <p> 3. {@link #setEnableRefresh(boolean)} / {@link #setEnableLoadmore(boolean)} 禁用/启用刷新和加载更多
 * <p> <a href="https://github.com/scwang90/SmartRefreshLayout">reference<a/>
 * <p> Created by RenTao on 2017/7/25.
 */
public class RefreshLayout extends SmartRefreshLayout {
    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if (getRefreshHeader() == null) {
            setRefreshHeader(new ClassicsHeader(getContext())
                    .setSpinnerStyle(SpinnerStyle.Translate));
        }
        if (getRefreshFooter() == null) {
            setRefreshFooter(new ClassicsFooter(getContext()));
        }
    }

    // ---------------------------- RefreshListener start ---------------------------- //

    private RefreshListener mRefreshListener;

    public RefreshListener getRefreshListener() {
        return mRefreshListener;
    }

    public void setRefreshListener(RefreshListener l) {
        this.mRefreshListener = l;
        if (mRefreshListener == null) {
            super.setOnRefreshLoadmoreListener(null);
        } else {
            super.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
                @Override
                public void onLoadmore(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                    mRefreshListener.onLoadMore(RefreshLayout.this);
                }

                @Override
                public void onRefresh(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                    mRefreshListener.onRefresh(RefreshLayout.this);
                }
            });
        }
    }

    @Override
    @Deprecated
    public SmartRefreshLayout setOnRefreshListener(OnRefreshListener listener) {
        return super.setOnRefreshListener(listener);
    }

    @Override
    @Deprecated
    public SmartRefreshLayout setOnLoadmoreListener(OnLoadmoreListener listener) {
        return super.setOnLoadmoreListener(listener);
    }

    @Override
    @Deprecated
    public SmartRefreshLayout setOnRefreshLoadmoreListener(OnRefreshLoadmoreListener listener) {
        return super.setOnRefreshLoadmoreListener(listener);
    }

    // ---------------------------- RefreshListener end ---------------------------- //
}
