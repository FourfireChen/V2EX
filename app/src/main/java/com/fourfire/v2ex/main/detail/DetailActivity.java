package com.fourfire.v2ex.main.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.fourfire.v2ex.R;
import com.fourfire.v2ex.data.bean.V2EXPost;
import com.fourfire.v2ex.main.detail.DetailContract.IPostDetailView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import static com.fourfire.v2ex.util.ID.POSITION;

/**
 * Created by 45089 on 2018/4/18.
 */

public class DetailActivity extends AppCompatActivity implements IPostDetailView {
    private Toolbar detailToolbar;
    private RecyclerView detailRecycleView;
    private DetailListAdapter detailListAdapter;
    private DetailPresenter detailPresenter;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        init();
    }

    private void init() {
        refreshLayout = findViewById(R.id.detail_refresh);
        detailPresenter = new DetailPresenter(this);
        detailRecycleView = findViewById(R.id.detail_list);
        detailToolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(detailToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        detailListAdapter = new DetailListAdapter(this);
        detailRecycleView.setLayoutManager(new LinearLayoutManager(this));
        detailRecycleView.setAdapter(detailListAdapter);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                detailPresenter.loadMorePosts(getIntent().getIntExtra(POSITION, -1));
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
                detailPresenter.inflatePost(10, getIntent().getIntExtra(POSITION, -1));
            }
        });
        refreshLayout.autoRefresh();
    }

    @Override
    public void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPost(V2EXPost v2EXPost) {
        detailListAdapter.setV2EXPost(v2EXPost);
        detailListAdapter.notifyDataSetChanged();
    }

    @Override
    public void finishRefresh() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void finishLoadMore() {
        refreshLayout.finishLoadMore();
    }
}
