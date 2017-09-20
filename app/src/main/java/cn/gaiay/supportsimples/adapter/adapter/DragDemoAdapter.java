package cn.gaiay.supportsimples.adapter.adapter;

import android.support.v7.widget.RecyclerView;

import com.gaiay.library.recycler.adapter.DraggableAdapter;
import com.gaiay.library.recycler.adapter.ViewHolder;
import com.gaiay.library.recycler.listener.OnDragListener;

import java.util.List;

import cn.gaiay.supportsimples.R;

/**
 * <p> Created by RenTao on 2017/7/28.
 */
public class DragDemoAdapter extends DraggableAdapter<String> {

    public DragDemoAdapter(RecyclerView recyclerView, List<String> data) {
        super(recyclerView, R.layout.simple_item_drag, data);

        setDraggableViewId(R.id.drag);
        setDragOnLongPress(true);
        setDraggable(true);

        // demo演示效果用，非必须
        setOnDragListener(new OnDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                viewHolder.itemView.setBackgroundColor(0xff999999);
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                viewHolder.itemView.setBackgroundColor(0xffffffff);
            }
        });
    }

    @Override
    protected void convert(ViewHolder helper, String item) {
        if (isDraggedItemDraggable(helper.getLayoutPosition())) {
            helper.setText(R.id.text, item);
        } else {
            helper.setText(R.id.text, item + " cannot drag");
        }
    }

    @Override
    public boolean isDraggedItemDraggable(int position) {
        return position >= 2;
    }
}
