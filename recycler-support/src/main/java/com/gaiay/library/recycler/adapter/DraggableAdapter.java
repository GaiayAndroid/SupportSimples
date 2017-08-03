package com.gaiay.library.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.gaiay.library.recycler.listener.OnDragListener;

import java.util.List;

/**
 * 可以进行拖动排序的Adapter<br/>
 * 默认是不可以进行拖动的，可以调用{@link #setDraggable(boolean)}设置为可以拖动，默认通过长按item拖动。<br/>
 * 更多配置：<br/>
 * 1. {@link #setDraggableViewId(int)} 设置拖动的viewId<br/>
 * 2. {@link #setDragOnLongPress(boolean)} 设置长按拖动<br/>
 * 3. {@link #setOnDragListener(OnDragListener)} 对拖动过程进行监听<br/>
 * Created by RenTao on 2017/7/27.
 */
public abstract class DraggableAdapter<T> extends BaseItemDraggableAdapter<T, ViewHolder> {
    private RecyclerView mRecyclerView;
    private int mDraggableViewId = 0;
    private boolean mDragOnLongPress = true;

    public DraggableAdapter(RecyclerView recyclerView, int layoutResId, List<T> data) {
        super(layoutResId, data);

        this.mRecyclerView = recyclerView;
    }

    /**
     * 设置按住可以进行拖动的viewId<br/>
     * 设置后则只能按住view进行拖动，按住item不能拖动；如果不设置，则按住item进行拖动
     */
    public void setDraggableViewId(int draggableViewId) {
        this.mDraggableViewId = draggableViewId;
    }

    /**
     * true 长按进行拖动；false 按住即可拖动
     */
    public void setDragOnLongPress(boolean dragOnLongPress) {
        this.mDragOnLongPress = dragOnLongPress;
    }

    /**
     * 设置是否可以进行拖动
     */
    public void setDraggable(boolean draggable) {
        if (draggable) {
            ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(this);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
            itemTouchHelper.attachToRecyclerView(mRecyclerView);
            enableDragItem(itemTouchHelper, mDraggableViewId, mDragOnLongPress);
        } else {
            disableDragItem();
        }
    }

    /**
     * use {@link #setOnDragListener(OnDragListener)}
     */
    @Deprecated
    @Override
    public void setOnItemDragListener(OnItemDragListener onItemDragListener) {
        super.setOnItemDragListener(onItemDragListener);
    }

    public void setOnDragListener(OnDragListener l) {
        super.setOnItemDragListener(l);
    }
}
