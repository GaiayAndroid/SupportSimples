package cn.gaiay.supportsimples.adapter.adapter;

import android.support.annotation.Nullable;

import com.gaiay.library.recycler.adapter.MultiItemAdapter;
import com.gaiay.library.recycler.adapter.ViewHolder;

import java.util.List;

import cn.gaiay.supportsimples.R;

/**
 * <p> Created by RenTao on 2017/7/28.
 */
public class MultiItemDemoAdapter extends MultiItemAdapter<String> {
    private static final int TEXT = 1;
    private static final int BUTTON = 2;

    public MultiItemDemoAdapter(@Nullable List<String> data) {
        super(data);
        addViewType(TEXT, R.layout.simple_item_text);
        addViewType(BUTTON, R.layout.simple_item_button);
    }

    @Override
    protected int getViewType(String entity) {
        int num = Integer.parseInt(entity);
        if (num % 3 == 0) {
            return TEXT;
        }
        return BUTTON;
    }

    @Override
    protected void convert(ViewHolder helper, String item) {
        switch (helper.getItemViewType()) {
            case TEXT:
                helper.setText(R.id.text, "label: " + item);
                break;

            case BUTTON:
                helper.setText(R.id.text, "text " + item);
                break;
        }
    }
}
