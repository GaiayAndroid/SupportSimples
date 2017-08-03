package cn.gaiay.supportsimples.adapter.adapter;

import android.support.annotation.Nullable;

import com.gaiay.library.recycler.adapter.CommonAdapter;
import com.gaiay.library.recycler.adapter.ViewHolder;

import java.util.List;

import cn.gaiay.supportsimples.R;

/**
 * <p> Created by RenTao on 2017/8/3.
 */
public class HorizontalAdapter extends CommonAdapter<String> {
    public HorizontalAdapter(@Nullable List<String> data) {
        super(R.layout.simple_item_text_horizontal, data);
    }

    @Override
    protected void convert(ViewHolder helper, String item) {
        helper.setText(R.id.text, item);
    }
}
