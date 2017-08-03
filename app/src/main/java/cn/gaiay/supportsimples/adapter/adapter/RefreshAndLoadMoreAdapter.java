package cn.gaiay.supportsimples.adapter.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaiay.library.recycler.adapter.CommonAdapter;
import com.gaiay.library.recycler.adapter.ViewHolder;

import java.util.List;

import cn.gaiay.supportsimples.R;
import cn.gaiay.supportsimples.utils.Util;

/**
 * <p>Created by RenTao on 2017/6/27.
 */

public class RefreshAndLoadMoreAdapter extends CommonAdapter<String> {

    public RefreshAndLoadMoreAdapter(@Nullable List<String> data) {
        super(R.layout.simple_item_button, data);

        setClick();
    }

    @Override
    protected void convert(ViewHolder helper, String item) {
        helper.setText(R.id.text, item);
        helper.addOnClickListener(R.id.delete);
    }

    private void setClick() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Util.toast("onItemClick position: " + position);
            }
        });
        setOnItemChildClickListener(new OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Util.toast("onItemChildClick position: " + position);
                getData().remove(position);
                notifyItemRemoved(position);
            }
        });
    }
}
