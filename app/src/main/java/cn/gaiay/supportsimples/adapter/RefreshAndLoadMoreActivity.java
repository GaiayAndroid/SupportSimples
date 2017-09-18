package cn.gaiay.supportsimples.adapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gaiay.library.recycler.adapter.CommonAdapter;
import com.gaiay.library.recycler.widget.DividerItemDecoration;
import com.gaiay.library.recycler.widget.RefreshLayout;

import java.util.List;

import cn.gaiay.supportsimples.BaseActivity;
import cn.gaiay.supportsimples.R;
import cn.gaiay.supportsimples.adapter.adapter.RefreshAndLoadMoreAdapter;
import cn.gaiay.supportsimples.utils.DataProvider;
import cn.gaiay.supportsimples.utils.Util;

public class RefreshAndLoadMoreActivity extends BaseActivity {
    RefreshLayout mRefreshLayout;
    RecyclerView mRecyclerView;
    List<String> mData;
    CommonAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adapter_activity_refresh_and_load_more);

        mRefreshLayout = (RefreshLayout) this.findViewById(R.id.layout_refresh);

        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));

        mData = DataProvider.provideString(20);
        mAdapter = new RefreshAndLoadMoreAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);

        core();
    }

    private void core() {
        mRefreshLayout.setRefreshListener(new RefreshLayout.RefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getData(true);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getData(false);
            }
        });
    }

    private void getData(final boolean isRefresh) {
        new AsyncTask<Void, Void, List<String>>() {

            @Override
            protected List<String> doInBackground(Void... params) {
                Util.sleep(1000);
                if (isRefresh) {
                    return DataProvider.provideString(20);
                } else {
                    return DataProvider.provideString(2);
                }
            }

            @Override
            protected void onPostExecute(List<String> list) {
                if (isRefresh) {
                    mData.clear();
                    mData.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    mRefreshLayout.finishRefresh();
                } else {
                    mData.addAll(list);
                    mAdapter.notifyItemRangeAppend(list.size());
                    mAdapter.notifyDataSetChanged();
                    mRefreshLayout.finishLoadmore();
                }
            }
        }.execute();
    }
}
