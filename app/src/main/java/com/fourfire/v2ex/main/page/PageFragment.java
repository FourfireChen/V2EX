package com.fourfire.v2ex.main.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fourfire.v2ex.BaseFragment;
import com.fourfire.v2ex.R;
import com.fourfire.v2ex.data.bean.V2EXPost;
import com.fourfire.v2ex.main.detail.DetailActivity;
import com.fourfire.v2ex.main.page.PageContract.*;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import static com.fourfire.v2ex.util.ID.PAGE;
import static com.fourfire.v2ex.util.ID.POSITION;

/**
 * Created by 45089 on 2018/4/17.
 */

public class PageFragment extends BaseFragment<PagePresenter> implements IPageView
{
    private RecyclerView recyclerView;
    private IPagePresenter pagePresenter;
    private PostListAdapter postListAdapter;
    private SmartRefreshLayout refreshLayout;
    private int page;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        init();
        View view = inflater.inflate(R.layout.main_page_fragment, container, false);
        refreshLayout = view.findViewById(R.id.page_refresh);
        recyclerView = view.findViewById(R.id.latest_list);
        recyclerView.setAdapter(postListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        postListAdapter.setItemClickListener(new PostListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position)
            {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(POSITION, position);
                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout)
            {
                refreshLayout.finishRefresh();
                pagePresenter.loadMorePosts(page);
            }
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout)
            {
                refreshLayout.finishLoadMore();
                pagePresenter.inflatePosts(10, page);
            }
        });
        refreshLayout.autoRefresh();
        return view;
    }


    private void init()
    {
        Log.i("pagefragment", "oncreateView");
        page = getArguments().getInt(PAGE);
        setPresenter(new PagePresenter(this));
        pagePresenter = (IPagePresenter) getPresenter();
        postListAdapter = new PostListAdapter();
    }

    @Override
    public void showPosts(ArrayList<V2EXPost> v2EXPosts)
    {
        postListAdapter.setV2EXPosts(v2EXPosts);
        postListAdapter.notifyDataSetChanged();
    }

    @Override
    public void finishRefresh()
    {
        refreshLayout.finishRefresh();
    }

    @Override
    public void finishLoadMore()
    {
        refreshLayout.finishLoadMore();
    }

}
