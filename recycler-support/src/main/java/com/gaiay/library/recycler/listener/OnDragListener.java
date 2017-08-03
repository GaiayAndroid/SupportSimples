package com.gaiay.library.recycler.listener;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnItemDragListener;

/**
 * <p>Created by RenTao on 2017/7/27.
 */
public interface OnDragListener extends OnItemDragListener {
    /**
     * 拖动开始时
     */
    void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos);

    /**
     * 拖动到另一个item上，顺序交换时
     */
    void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to);

    /**
     * 拖动结束时
     */
    void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos);
}
