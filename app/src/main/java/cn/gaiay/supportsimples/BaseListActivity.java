package cn.gaiay.supportsimples;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaiay.library.recycler.adapter.CommonAdapter;
import com.gaiay.library.recycler.adapter.ViewHolder;
import com.gaiay.library.recycler.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.gaiay.supportsimples.bean.ActivityBean;

/**
 * <p>Created by RenTao on 2017/8/4.
 */

public abstract class BaseListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        List<ActivityBean> list = new ArrayList<>();

        initData(list);

        RecyclerView recycler = (RecyclerView) this.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new DividerItemDecoration(this));
        Adapter adapter = new Adapter(list);
        recycler.setAdapter(adapter);
    }

    protected abstract void initData(List<ActivityBean> list);

    private static class Adapter extends CommonAdapter<ActivityBean> implements BaseQuickAdapter.OnItemClickListener {

        Adapter(@Nullable List<ActivityBean> data) {
            super(R.layout.simple_item_text, data);
            setOnItemClickListener(this);
        }

        @Override
        protected void convert(ViewHolder helper, ActivityBean item) {
            helper.setText(R.id.text, item.title);
        }

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            if (getItem(position) != null) {
                mContext.startActivity(new Intent(mContext, getItem(position).activity)
                        .putExtra("title", getItem(position).title));
            }
        }
    }
}
