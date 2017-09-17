package cn.gaiay.supportsimples.temp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaiay.library.recycler.adapter.CommonAdapter;
import com.gaiay.library.recycler.adapter.ViewHolder;
import com.gaiay.library.recycler.widget.DividerItemDecoration;

import java.util.List;

import cn.gaiay.supportsimples.utils.DataProvider;

/**
 * <p>Created by RenTao on 2017/9/18.
 */

public class TempListFragment extends Fragment {
    private String text;

    public TempListFragment() {
        this("TempListFragment");
    }

    public TempListFragment(String text) {
        this.text = text;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(new TempListAdapter(DataProvider.provideString(text, 50)));
        return recyclerView;
    }

    private class TempListAdapter extends CommonAdapter<String> {

        public TempListAdapter(@Nullable List<String> data) {
            super(android.R.layout.simple_list_item_1, data);
        }

        @Override
        protected void convert(ViewHolder helper, String item) {
            helper.setText(android.R.id.text1, item);
        }
    }
}
