package cn.gaiay.supportsimples.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.gaiay.library.recycler.widget.DividerItemDecoration;

import cn.gaiay.supportsimples.BaseActivity;
import cn.gaiay.supportsimples.adapter.adapter.HorizontalAdapter;
import cn.gaiay.supportsimples.utils.DataProvider;

/**
 * <p> Created by RenTao on 2017/8/3.
 */
public class HorizontalRecyclerActivity extends BaseActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
        HorizontalAdapter adapter = new HorizontalAdapter(DataProvider.provideString(20));
        recyclerView.setAdapter(adapter);

        setContentView(recyclerView);
    }
}
