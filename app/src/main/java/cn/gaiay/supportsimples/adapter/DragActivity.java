package cn.gaiay.supportsimples.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gaiay.library.recycler.widget.DividerItemDecoration;

import cn.gaiay.supportsimples.BaseActivity;
import cn.gaiay.supportsimples.adapter.adapter.DragDemoAdapter;
import cn.gaiay.supportsimples.utils.DataProvider;

/**
 * <p> Created by RenTao on 2017/7/28.
 */
public class DragActivity extends BaseActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        DragDemoAdapter adapter = new DragDemoAdapter(recyclerView, DataProvider.provideString(20));
        recyclerView.setAdapter(adapter);

        setContentView(recyclerView);
    }
}
